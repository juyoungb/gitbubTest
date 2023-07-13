package svc;

import static db.JdbcUtil.*;  // JdbcUtil 클래스의 모든 멤버들을 자유롭게 사용
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class LoginProcSvc {
	public MemberInfo getLoginInfo(String uid, String pwd) {
		MemberInfo loginInfo = null;
		Connection conn = getConnection();
		LoginProcDao loginProcDao = LoginProcDao.getInstance();  // singleton(객체의 인스턴스가 오직 1개만 생성되는 패턴)방식
		loginProcDao.setConnection(conn); // 여기 까지 db연결 
		
		loginInfo = loginProcDao.getLoginInfo(uid, pwd);
		 close(conn);
		 
		
		return loginInfo;
	}
}
