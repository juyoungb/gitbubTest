package dao;

import static db.JdbcUtil.*;  
import java.time.*;
import java.util.*;
import java.sql.*;
import vo.*;
// import java.time.*; ��¥�� ���������� import
public class OrderProcDao {
//�ֹ����� �۾�(��, ���, ����)���� ö���ϴ� Ŭ����
	private static OrderProcDao orderProcDao;
	private Connection conn;
	private OrderProcDao() {}

    public static OrderProcDao getInstance() {
      if(orderProcDao == null) orderProcDao = new OrderProcDao();
      
      return orderProcDao;
   }
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   public ArrayList<OrderCart> getBuyList(String kind, String sql) {
	// �ֹ� ������ ������ ������ ��ǰ����� ArrayList<OrderCart>������ �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrderCart> pdtList = new ArrayList<OrderCart>();
		OrderCart oc = null;
		try {
			 stmt = conn.createStatement();
			 //System.out.println(sql);
	         rs = stmt.executeQuery(sql);
			 while (rs.next()) {
				oc = new OrderCart();
				if (kind.equals("c"))		oc.setOc_idx(rs.getInt("oc_idx"));
				//��ٱ��ϸ� ���� ������ ��쿡�� ��ٱ��� �ε����� �߰� ������
				else 						oc.setOc_idx(0);
				// �ٷ� ������ ��� �⺻������ ä����
				oc.setPi_id(rs.getString("pi_id"));
				oc.setPi_img1(rs.getString("pi_img1"));
				oc.setPi_name(rs.getString("pi_name"));
				oc.setPi_price(rs.getInt("price"));
				oc.setOc_cnt(rs.getInt("cnt"));
				oc.setPs_size(rs.getInt("ps_size"));
				pdtList.add(oc);
			 }
			 
		} catch(Exception e) {
         System.out.println("OrderProcDaoŬ������ getBuyList() �޼ҵ� ����");
         e.printStackTrace();
      } finally {
    	  close(rs);  close(stmt);
      }
		return pdtList;
	}


	public ArrayList<MemberAddr> getAddrList(String miid) {
		// �ֹ� ������ ������ ���� �α����� ȸ���� �ּҸ���� ArrayList<MemberAddr>���� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberAddr> addrList = new ArrayList<MemberAddr>();
		MemberAddr ma = null;
		try {
			 stmt = conn.createStatement();
			 String sql ="select * from t_member_addr where mi_id='" + miid + "' order by  ma_basic desc ";
	         rs = stmt.executeQuery(sql);
			 while (rs.next()) {
				ma = new MemberAddr();
				ma.setMa_idx(rs.getInt("ma_idx"));
				ma.setMa_name(rs.getString("ma_name"));
				ma.setMa_rname(rs.getString("ma_rname"));
				ma.setMa_phone(rs.getString("ma_phone"));
				ma.setMa_zip(rs.getString("ma_zip"));
				ma.setMa_addr1(rs.getString("ma_addr1"));
				ma.setMa_addr2(rs.getString("ma_addr2"));
				ma.setMa_basic(rs.getString("ma_basic"));
				addrList.add(ma);
			 }
			 
		} catch(Exception e) {
         System.out.println("OrderProcDaoŬ������ getAddrList() �޼ҵ� ����");
         e.printStackTrace();
      } finally {
    	  close(rs);  close(stmt);
      }
		return addrList;
	}
	
	private String getOrderId() { // ���ο� �ֹ���ȣ(yymmdd+��������2�ڸ�+�Ϸù�ȣ4�ڸ�(1001)+��������2�ڸ� )�� �����Ͽ� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String oi_id = null;
		
		try {
			 stmt = conn.createStatement();
			 LocalDate today = LocalDate.now();		// yyyy-mm-dd
			 String td = (today +"").substring(2).replace("-", "");  // yymmdd
			 
			 String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			 Random rnd = new Random();
			 String eng1 = alpha.charAt(rnd.nextInt(26)) + "";
			 eng1 += alpha.charAt(rnd.nextInt(26)) + "";
			 
			 String eng2 = alpha.charAt(rnd.nextInt(26)) + "";		 
			 eng2 += alpha.charAt(rnd.nextInt(26)) + "";
			 
			//������ �Էµ� �ֹ���ȣ���� ���� �ֱٰ��� �������� ����
			 String sql ="select mid(oi_id, 9, 4) seq from t_order_info where left(oi_id, 6) = '" + td + "' order by oi_date desc limit 0, 1";
	         rs = stmt.executeQuery(sql);
	         
	         if (rs.next())  {	// ���� ������ �ֹ���ȣ�� ������
	        	 int num = Integer.parseInt(rs.getString("seq")) +1;
	        	 oi_id = td + eng1 + num + eng2 ;
	         } else {  //���� ù ������ ���
	        	 oi_id = td + eng1 + "1001" + eng2 ;
	         }
			 
		} catch(Exception e) {
			System.out.println("OrderProcDaoŬ������ getOrderId() �޼ҵ� ����");
			e.printStackTrace();
	     } finally {
	   	  	close(rs);  close(stmt);
	     }
		return oi_id;
	}
	
	public String orderInsert(String kind, OrderInfo oi, String ocidxs) {
	// �ֹ�ó���� �ϴ� �޼ҵ�(��ٱ��ϳ� �ٷα��Ÿ� ���� �ֹ�)
		Statement stmt = null;
		ResultSet rs = null;
		String oi_id = getOrderId();
		String result = oi_id + ",";
		int rcount = 0 , target = 0;
		// rcount : ���� ���� �������� ����Ǵ� ���ڵ� ������ ���� ������ ����
		// target : insert, update, delete ���� ���� ���� Ƚ���� ����Ǿ�� �� ���ڵ��� �� ����
		
		try {
			 stmt = conn.createStatement();
			 // t_order_info ���̺��� ����� insert ��
			 String sql = "insert into  t_order_info values('" + oi_id + "', '" + oi.getMi_id() + "', '" + oi.getOi_name() + "',"
			 		+ " '" + oi.getOi_phone() + "', '" + oi.getOi_zip() + "', '" + oi.getOi_addr1() + "', '" + oi.getOi_addr2() + "', '" + oi.getOi_memo() + "', "
			 		+ " '" + oi.getOi_payment() +"', '" + oi.getOi_pay() + "', 0, 0, null, '" + oi.getOi_status() + "', now())";
			 target++;	 rcount = stmt.executeUpdate(sql);
			 
			 if (kind.equals("c")) { // ��ٱ��ϸ� ���� ������ ���
			// ��ٱ��Ͽ��� t_order_detail ���̺���insert�� ��ǰ������ ����
			
				sql = "select a.pi_id, a.ps_id, a.oc_cnt, b.pi_name, b.pi_img1, c.ps_size, if(b.pi_dc > 0, round((1 - b.pi_dc) * b.pi_price), b.pi_price) price " + 
					  " from t_order_cart a, t_produc_info b, t_product_stock c where a.pi_id = b.pi_id " +
					  " and a.ps_idx = c.ps_idx and a.mi_id = '" + oi.getMi_id() + "' and (";
				String delwhere = " where mi_id = '" + oi.getMi_id() + "' and ("; 
				String[] arr = ocidxs.split(","); // ��ٱ��� ���̺��� index��ȣ��� �迭�� ����
				for (int i = 0 ; i < arr.length; i++) {
					if (i == 0) {
						sql += "a.oc_idx = " + arr[i];
						delwhere += "a.oc_idx = " + arr[i];
					}else {
						sql += " or a.oc_idx = " + arr[i];
						delwhere += " or a.oc_idx = " + arr[i];					
					}
				}
				sql += ")";
				delwhere += ")";
				rs = stmt.executeQuery(sql);
				if (rs.next()) { // ��ٱ��Ͽ� ������ ��ǰ������ ������
					do {
						Statement stmt2 = conn.createStatement();
						// t_order_detail ���̺��� ����� insert��
						sql = "insert into  t_order_detail values (null, '" + oi_id + "', '" + rs.getString("pi_id") + "', '" + rs.getInt("ps_idx")+ "' ,"+
							  " '" + rs.getInt("oc_cnt") + "', '" + rs.getInt("price")+ "', '" + rs.getString("pi_name") + "', '" + rs.getString("pi_img1") + "', '" + rs.getInt("ps_size") + "')";
						target++;  rcount += stmt2.executeUpdate(sql);
						
						// t_product_info ���̺��� �Ǹż� ���� update ��
						sql = "update t_product_info set pi_sale = pi_sale + " + rs.getInt("oc_cnt") + " where pi_id = '" + rs.getString("pi_id") + "' ";
						target++;  rcount += stmt2.executeUpdate(sql);
						
						// t_product_stock ���̺��� �Ǹ� �� ��� ���� update��
						sql = "update t_product_stock set ps_stock = ps_stock - " + rs.getInt("oc_cnt") + ", ps_sale = ps_sale + " + rs.getInt("oc_cnt") + " "+
							  "where ps_idx = " + rs.getInt("ps_idx");
						target++;  rcount += stmt2.executeUpdate(sql);	
						close(stmt2);
						
					} while(rs.next());
					close(rs);
					

					// t_order_cart ���̺��� ���� �� ���� delete ��
					sql = "delete t_order_cart " + delwhere;
					stmt.executeUpdate(sql); // ����� ������ �߻��ص� ���ſʹ� ���������� rcount�� ������Ű�� ����
					
				} else { // ��ٱ��Ͽ� ������ ��ǰ������ ������
					return result + "1,4"; // �̷̹� �ٸ� �ΰ��� ���ڸ� ����
				}
				
			 } else { // �ٷ� ������ ���
				 
			 }
			 
			 if (oi.getOi_spoint() > 0) { // �����Ǵ� ����Ʈ�� ������ 
					// t_member_info ���̺� ȸ���� ���� ����Ʈ update��
					sql = "update t_member_info set mi_point = mi_point + " + oi.getOi_spoint() + " where mi_id='" + oi.getMi_id() + "' ";
					target++;  rcount += stmt.executeUpdate(sql);
					
					//t_member_point ȸ���� ����Ʈ ��� ���� insert��
					sql = "insert into t_member_point (mi_id, mp_point, mp_desc, mp_detail) "+
						   "values('" + oi.getMi_id() + "',  " + oi.getOi_spoint() + ", '��ǰ����', '" + oi_id + "')";
					target++;  rcount += stmt.executeUpdate(sql);
				}
				if (oi.getOi_spoint() > 0) { // ���Ǵ� ����Ʈ�� ������
					// t_member_info ���̺� ȸ���� ���� ����Ʈ update��
					sql = "update t_member_info set mi_point = mi_point - " + oi.getOi_upoint() + " where mi_id='" + oi.getMi_id() + "' ";
					target++;  rcount += stmt.executeUpdate(sql);
					
					//t_member_point ȸ���� ����Ʈ ��� ���� insert��
					sql = "insert into t_member_point (mi_id, mp_su, mp_point, mp_desc, mp_detail) "+
						   "values('" + oi.getMi_id() + "', 'u', -" + oi.getOi_upoint() + ", '��ǰ����', '" + oi_id + "')";
					target++;  rcount += stmt.executeUpdate(sql);
				}	 
			
		} catch(Exception e) {
			System.out.println("OrderProcDaoŬ������ orderInsert() �޼ҵ� ����");
			e.printStackTrace();
	     } finally {
	   	  	  close(stmt);
	     }
		
		return result + rcount + "," + target;
	}

	public OrderInfo orderInsert(String miid, String oiid) {
	// �޾ƿ� ȸ�����̵�� �ֹ����̵� �ش��ϴ� �ֹ� �������� OrderInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		Statement stmt =null;
		ResultSet rs = null;
		OrderInfo oi = null;
		ArrayList<OrderDetail> detatilList = null;
		try {
			stmt = conn.createStatement();
			String sql = "select a.*,b.*,c.pi_isview from t_order_info a, t_order_detail b, t_product_info c "
					+ "where a.oi_id = b.oi_id and b.pi_id = c.pi_id and a.mi_id = '" + miid + "' and a.oi_id='" + oiid + "' ";
			rs = stmt.executeQuery(sql);

		} catch(Exception e) {
			System.out.println("OrderProcDaoŬ������ orderInsert() �޼ҵ� ����");
			e.printStackTrace();
	     } finally {
	    	 close(rs);	  close(stmt);
	     }
		return oi;
	}
	
}
