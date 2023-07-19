package dao;

import java.sql.*;
import javax.sql.*;
import vo.*;

public class LoginDaoMvc {
	private DataSource dataSource;
	public LoginDaoMvc(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public MemberInfo getLoginInfo(String uid, String pwd) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; 
		MemberInfo mi = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from t_member_info where mi_id = '" + uid + "' and mi_pw = '" + pwd + "' ";
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				mi = new MemberInfo();
			     mi.setMi_id(rs.getString("mi_id"));
		            mi.setMi_pw(rs.getString("mi_pw"));
		            mi.setMi_name(rs.getString("mi_name"));
		            mi.setMi_gender(rs.getString("mi_gender"));
		            mi.setMi_birth(rs.getString("mi_birth"));
		            mi.setMi_phone(rs.getString("mi_phone"));
		            mi.setMi_email(rs.getString("mi_email"));
		            mi.setMi_isad(rs.getString("mi_isad"));
		            mi.setMi_point(rs.getInt("mi_point"));
		            mi.setMi_status(rs.getString("mi_status"));
		            mi.setMi_date(rs.getString("mi_date"));
		            mi.setMi_lastlogin(rs.getString("mi_lastlogin"));		
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					rs.close();
					stmt.close();
					conn.close();
				
			} catch(Exception e) {
			e.printStackTrace();
		
			}
		}
	}
		return mi;
	}

}
