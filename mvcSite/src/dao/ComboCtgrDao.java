package dao;


import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class ComboCtgrDao {
		private static ComboCtgrDao comboCtgrDao;
		private Connection conn;
		private ComboCtgrDao() {}
		
		public static ComboCtgrDao getInstance() {
		if(comboCtgrDao == null) comboCtgrDao = new ComboCtgrDao();	
		return comboCtgrDao;
		}
	public void setConnection(Connection conn) {	
		this.conn = conn;
	}

	public ArrayList<ProductCtgrBig> getBigList() {
		//��ǰ ��з� ����� ArrayList<ProductCtgrBig>������ �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductCtgrBig> bigList  = new ArrayList<ProductCtgrBig>();
		ProductCtgrBig ctgrBig = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_product_ctgr_big";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ctgrBig = new ProductCtgrBig();
				ctgrBig.setPcb_id(rs.getString("pcb_id"));
				ctgrBig.setPcb_name(rs.getString("pcb_name"));
				bigList.add(ctgrBig);
			}
				
		} catch(Exception e) {
			System.out.println("FreeProcDaoŬ������ getBigList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}		
		return bigList;
	}
	
	public ArrayList<ProductCtgrSmall> getSmallList() {
		//��ǰ ��з� ����� ArrayList<ProductCtgrBig>������ �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductCtgrSmall> smallList  = new ArrayList<ProductCtgrSmall>();
		ProductCtgrSmall ctgrsmall = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_product_ctgr_small";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				ctgrsmall = new ProductCtgrSmall();
				ctgrsmall.setPcb_id(rs.getString("pcb_id"));			
				ctgrsmall.setPcs_id(rs.getString("pcs_id"));
				ctgrsmall.setPcs_name(rs.getString("pcs_name"));			
				smallList.add(ctgrsmall);
			}
				
		} catch(Exception e) {
			System.out.println("FreeProcDaoŬ������ getBigList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}		
		return smallList;
	}
}



