package svc;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MemberProcSvc {
// 회원 가입 및 정보 수정 을 위한 Service 클래스
	public int memberProcIn(MemberInfo memberInfo, MemberAddr memberAddr) {
		int result = 0;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);		
		result = memberProcDao.memberProcIn(memberInfo, memberAddr);
		if(result == 3) 	commit(conn);
		else 				rollback(conn);
		//사용한 쿼리가 insert, update, delete 일 경우 반드시 트랜젝션을 완료해야 함
		close(conn);
		
		return result;
	}
}
