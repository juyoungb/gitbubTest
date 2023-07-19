package dao;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import vo.*;

public class MemberDao {
	private JdbcTemplate jdbc;
	
	public MemberDao(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
	public int memberInsert(MemberInfo mi) {
		String sql="insert into t_member_info values (?, ?, ?, ?, ?, ?, ?, ?, 1000, 'a', now(), null)";
		int result = jdbc.update(sql,mi.getMi_id(), mi.getMi_pw(),mi.getMi_name(),mi.getMi_gender(), mi.getMi_birth(),mi.getMi_phone(),mi.getMi_email(),mi.getMi_isad());
		
		
		return result;
	}
}
