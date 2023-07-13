package dao;


import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;


public class LoginProcDao {
// �α��ο� ���õ� ���� �۾��� ó���ϴ� Ŭ����
	private static LoginProcDao LoginProcDao;
	private Connection conn;
	private LoginProcDao() {}//�⺻�����ڸ� private���� �����Ͽ� �ܺο��� �Ժη� �ν��Ͻ��� �������� ���ϰ� ����(������ �Ұ����ϵ��� �����ڸ� ���� )
	
	public static LoginProcDao getInstance() {
	// LoginProcDao Ŭ������ �ν��Ͻ��� �������ִ� �޼ҵ�� �̹� �ν��Ͻ��� ������ ������ �ν��Ͻ� ����
	// LoginProcDao Ŭ������ �ν��Ͻ��� �ϳ��� �����Ͽ� ����ϰ� �ϴ� �̱��� ���(�ν��Ͻ� ���� ������ ���ؼ� ���)
		if(LoginProcDao == null) LoginProcDao = new LoginProcDao();
		// �̹� ������ LoginProcDao Ŭ������ �ν��Ͻ��� ������ ���� �ν��Ͻ� ����
		
		return LoginProcDao;
	}
	public void setConnection(Connection conn) {
	// �� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �����ϴ� �޼ҵ�
		this.conn = conn;
	}
	public MemberInfo getLoginInfo(String uid, String pwd) {
	// �޾ƿ� ���̵�� ��ȣ�� �α��� �۾��� ó���� �� ȸ������ MemberInfo�� �ν��Ͻ��� ����
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo loginInfo = null;
		
		try {
			String sql = "select * from  t_member_info where mi_status <> 'c' and mi_id = '" + uid + "' and mi_pw = '" + pwd + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) { // rs�� �����Ͱ� ������ (�α����� ���� ������)
				loginInfo = new MemberInfo(); // ȸ�������� ������ �ν��Ͻ� ����
				
				loginInfo.setMi_id(rs.getString("mi_id"));
	            loginInfo.setMi_pw(rs.getString("mi_pw"));
	            loginInfo.setMi_name(rs.getString("mi_name"));
	            loginInfo.setMi_gender(rs.getString("mi_gender"));
	            loginInfo.setMi_birth(rs.getString("mi_birth"));
	            loginInfo.setMi_phone(rs.getString("mi_phone"));
	            loginInfo.setMi_email(rs.getString("mi_email"));
	            loginInfo.setMi_isad(rs.getString("mi_isad"));
	            loginInfo.setMi_point(rs.getInt("mi_point"));
	            loginInfo.setMi_status(rs.getString("mi_status"));
	            loginInfo.setMi_date(rs.getString("mi_date"));
	            loginInfo.setMi_lastlogin(rs.getString("mi_lastlogin"));
	            // �޾ƿ� ������ MemberInfo�� �ν��Ͻ��� ���� ��
			}  // rs�� ������� else ���� loginInfo �ν��Ͻ��� null�� �ִ� ���·� �����ϰ� ��
			
		} catch(Exception e) {
			System.out.println("LoginProcDao Ŭ������ getLoginInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return loginInfo;
	}
}
