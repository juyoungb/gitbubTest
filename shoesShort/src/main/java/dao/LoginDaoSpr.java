package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class LoginDaoSpr {
	private JdbcTemplate jdbcTemplate;
	
	public LoginDaoSpr(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public MemberInfo getLoginInfo(String uid, String pwd) {
		String sql = "select * from t_member_info where mi_id = ? and mi_pw = ? ";
		
		List<MemberInfo> results = jdbcTemplate.query(sql, new RowMapper<MemberInfo>() {			    	
				public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException{
					MemberInfo  mi = new MemberInfo();
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
					return mi;
		      }		
		}, uid, pwd);
		
		return results.isEmpty() ? null : results.get(0);
	} 
}
