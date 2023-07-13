package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class CartProcDao {
// ��ٱ��� ���� �۾�(��ٱ��Ͽ� ����, ���, ����, ����)���� ó���ϴ� Ŭ����
	private static CartProcDao cartProcDao;
    private Connection conn;
    private CartProcDao() {}
   
    public static CartProcDao getInstance() {
      if(cartProcDao == null) cartProcDao = new CartProcDao();
      
      return cartProcDao;
   }
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   
   public int cartInsert(OrderCart oc) {
   // ����ڰ� ������ ��ǰ(�ɼ�, ���� ����)�� ��ٱ��Ͽ� ��� �޼ҵ�   
      Statement stmt = null;
      ResultSet rs = null;
      int result = 0;
      
      try {
         stmt = conn.createStatement();
         String sql = "select oc_idx from t_order_cart where mi_id = '"+ oc.getMi_id() +"' and pi_id = '"+ oc.getPi_id() +"' "
                                           + "and ps_idx = "+ oc.getPs_idx();
         rs = stmt.executeQuery(sql);
         if (rs.next()) {   // ������ �ɼ��� ���� ��ǰ�� �̹� ��ٱ��Ͽ� ���� ���
            sql = "update t_order_cart set oc_cnt = oc_cnt + "+ oc.getOc_cnt() +" where oc_idx = "+ rs.getInt("oc_idx");
         } else {   // ó�� ��ٱ��Ͽ� ��� ��ǰ�� ���
            sql = "insert into t_order_cart (mi_id, pi_id, ps_idx, oc_cnt) "
                  + "values ('"+ oc.getMi_id() +"', '"+ oc.getPi_id() +"', "+ oc.getPs_idx() +", "+ oc.getOc_cnt() +") ";
         }
         // System.out.println(sql);
         result = stmt.executeUpdate(sql);
         
      }catch(Exception e) {
         System.out.println("CartProcDaoŬ������ cartInset() �޼ҵ� ����");
         e.printStackTrace();
      } finally {
    	  close(rs);  close(stmt);
      }
      
      return result;
    }

	public ArrayList<OrderCart> getCartList(String miid) {
	// ���� �α����� ȸ���� ��ٱ��Ͽ��� ������ ��ǰ �������� ArryList�� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrderCart> cartList = new ArrayList<OrderCart>();
		OrderCart oc = null;
		try {
			ProductProcDao ppd = ProductProcDao.getInstance();
			ppd.setConnection(conn);
			
			
			oc = new OrderCart();
			stmt = conn.createStatement();
		 	String sql = "select a.*, b.pi_name, b.pi_img1 , if (b.pi_dc > 0, round(1 - b.pi_dc) * b.pi_price, b.pi_price) price "
		 			+ "from t_order_cart a, t_product_info b where b.pi_isview ='y' and a.pi_id =b.pi_id and  a.mi_id='" + miid + "' order by a.pi_id,a.ps_idx";		 		
		 	rs = stmt.executeQuery(sql);
		 	while (rs.next()) {oc = new OrderCart();
            oc.setOc_idx(rs.getInt("oc_idx"));
            oc.setMi_id(rs.getString("mi_id"));
            oc.setPi_id(rs.getString("pi_id"));
            oc.setPs_idx(rs.getInt("ps_idx"));
            oc.setOc_cnt(rs.getInt("oc_cnt"));
            oc.setOc_date(rs.getString("oc_date"));
            oc.setPi_name(rs.getString("pi_name"));
            oc.setPi_img1(rs.getString("pi_img1"));
            oc.setPi_price(rs.getInt("price"));
            oc.setStockList(ppd.getStockList(oc.getPi_id()));
            //���� ��ǰ�� �ɼ� �� ��� �����  ProductProcDaoŬ������ getStockList()�� �۾�����
            cartList.add(oc);
		 	}
		 	
		} catch(Exception e) {
		    System.out.println("CartProcDaoŬ������ getCartList() �޼ҵ� ����");
		    e.printStackTrace();
		 } finally {
			  close(rs);  close(stmt);
		 }
		return cartList;
	}

	public int cartDelete(String where) {
	// ������ ��ǰ �Ǵ� ��ǰ���� ��ٱ��Ͽ��� �����ϴ� �޼ҵ�
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "delete from t_order_cart  " + where ;
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
		    System.out.println("CartProcDaoŬ������ cartDelete() �޼ҵ� ����");
		    e.printStackTrace();
		 } finally {
			  close(stmt);
		 }
		return result;
	}

	public int CartUpdate(OrderCart oc) {
	// ������ ��ǰ�� �ɼ� �Ǵ� ������ �����ϴ� �޼ҵ�	
		Statement stmt = null;	
		ResultSet rs = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "";
			if (oc.getOc_cnt() == 0) {  // �ɼǺ����� ���
				sql = "select oc_cnt, oc_idx from t_order_cart where mi_id='" + oc.getMi_id() + "'  and ps_idx=" + oc.getPs_idx();
				System.out.println(sql);
				//�����Ϸ��� �ɼǰ� ������ �ɼ��� ��ǰ�� ��ٱ��Ͽ� �̹� �����ϴ��� ���θ� �˻��� ����
				rs = stmt.executeQuery(sql);	
				if (rs.next()) { 
				// �����Ϸ��� �ɼǰ� ������ �ɼ��� ��ǰ�� ��ٱ��Ͽ� �̹� �����ϴ� ���
				// ������ ��ǰ�� ������ �߰��� �� ����
					int idx =rs.getInt("oc_idx");
					// stmt�� �ٸ� ������ �����ϱ� ���� ����� ���� �̸� rs���� �޾Ƴ���
					sql = "update t_order_cart set ps_idx=" + oc.getPs_idx() + ",  oc_cnt = oc_cnt + " + rs.getInt("oc_cnt") + 
							" where mi_id='" + oc.getMi_id() + "' and oc_idx=" + oc.getOc_idx();
					System.out.println(sql);
					// �ɼǺ���� ������ �ɼ��� ������ǰ������ �� ��ǰ�� �߰��ϴ� ����
					result = stmt.executeUpdate(sql);
					
					sql = "delete from t_order_cart where oc_idx = " + idx;
					// ������ �ɼ��� ���� ��ǰ�� ��ٱ��Ͽ��� �����ϴ� ����
				
				} else {
				// �����Ϸ��� �ɼǰ� ������ �ɼ��� ��ǰ�� ��ٱ��Ͽ� ���� ���
				// �ش��ǰ�� �ɼǸ� ����
					sql = "update t_order_cart set ps_idx=" + oc.getPs_idx() + "   where mi_id='" + oc.getMi_id() + "' and oc_idx=" + oc.getOc_idx();
				}
				close(rs);
				
			} else { // ���������� ���
				sql = "update t_order_cart set oc_cnt=" + oc.getOc_cnt() + "   where mi_id='" + oc.getMi_id() + "' and oc_idx=" + oc.getOc_idx();
			}
			
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
		    System.out.println("CartProcDaoŬ������ CartUpdate() �޼ҵ� ����");
		    e.printStackTrace();
		 } finally {
			  close(stmt);
		 }
		return result;
	}
}