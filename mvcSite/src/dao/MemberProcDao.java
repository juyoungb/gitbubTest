package dao;

import static db.JdbcUtil.*; 
import java.util.*;
import java.sql.*;
import vo.*;

public class MemberProcDao {
// ȸ�� ���� �� ���� ���� �� ����  ���� �۾��� �ϴ� Dao Ŭ����
	private static MemberProcDao memberProcDao;
	private Connection conn;
	private MemberProcDao() {}
	
	public static MemberProcDao getInstance() {
		if(memberProcDao == null) memberProcDao = new MemberProcDao();
		return memberProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int memberProcIn(MemberInfo memberInfo, MemberAddr memberAddr) {
	//����ڰ� �Է��� �����͵�� ȸ�������� ��Ű�� �޼ҵ�
		PreparedStatement pstms = null;
		int result = 0;
		
		try {
			String sql = "insert into t_member_info(mi_id, mi_pw, mi_name, mi_gender, mi_birth, mi_phone, mi_email, mi_isad, mi_point) "+
						 " values(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstms = conn.prepareStatement(sql);
			pstms.setString(1, memberInfo.getMi_id());						pstms.setString(2, memberInfo.getMi_pw());
			pstms.setString(3, memberInfo.getMi_name());					pstms.setString(4, memberInfo.getMi_gender());
			pstms.setString(5, memberInfo.getMi_birth());					pstms.setString(6, memberInfo.getMi_phone());
			pstms.setString(7, memberInfo.getMi_email());					pstms.setString(8, memberInfo.getMi_isad());
			pstms.setInt(9, memberInfo.getMi_point());
			result = pstms.executeUpdate();
		
			
			if(result == 1 ) { // t_member_addr ���̺� insert�� ���������� ����Ǿ��� ���
				sql = "insert into t_member_addr(mi_id, ma_name, ma_rname, ma_phone, ma_zip, ma_addr1, ma_addr2) values(?, ?, ?, ?, ?, ?, ?)";
				pstms = conn.prepareStatement(sql);
				pstms.setString(1, memberAddr.getMi_id());					pstms.setString(2, memberAddr.getMa_name());
				pstms.setString(3, memberAddr.getMa_rname());				pstms.setString(4, memberAddr.getMa_phone());
				pstms.setString(5, memberAddr.getMa_zip());					pstms.setString(6, memberAddr.getMa_addr1());
				pstms.setString(7, memberAddr.getMa_addr2());			
				result += pstms.executeUpdate();
			
				if(result == 2) {// t_member_info ���̺� insert�� ���������� ����Ǿ��� ���
					sql = "insert into t_member_point(mi_id, mp_point, mp_desc) values(?, ?, '�������ϱ�')";
					pstms = conn.prepareStatement(sql);
					pstms.setString(1, memberInfo.getMi_id());
					pstms.setInt(2, memberInfo.getMi_point());
					result += pstms.executeUpdate();
				
				}
			}
		
		}
		catch(Exception e) {
			System.out.println("MemberProcDaoŬ������ memberProcIn() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(pstms);
		}
		return result;
	}
}
