package svc;

import static db.JdbcUtil.*;  // JdbcUtil Ŭ������ ��� ������� �����Ӱ� ���
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class LoginProcSvc {
	public MemberInfo getLoginInfo(String uid, String pwd) {
		MemberInfo loginInfo = null;
		Connection conn = getConnection();
		LoginProcDao loginProcDao = LoginProcDao.getInstance();  // singleton(��ü�� �ν��Ͻ��� ���� 1���� �����Ǵ� ����)���
		loginProcDao.setConnection(conn); // ���� ���� db���� 
		
		loginInfo = loginProcDao.getLoginInfo(uid, pwd);
		 close(conn);
		 
		
		return loginInfo;
	}
}
