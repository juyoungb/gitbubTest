package svc;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MemberProcSvc {
// ȸ�� ���� �� ���� ���� �� ���� Service Ŭ����
	public int memberProcIn(MemberInfo memberInfo, MemberAddr memberAddr) {
		int result = 0;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);		
		result = memberProcDao.memberProcIn(memberInfo, memberAddr);
		if(result == 3) 	commit(conn);
		else 				rollback(conn);
		//����� ������ insert, update, delete �� ��� �ݵ�� Ʈ�������� �Ϸ��ؾ� ��
		close(conn);
		
		return result;
	}
}
