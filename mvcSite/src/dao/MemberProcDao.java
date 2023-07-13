package dao;

import static db.JdbcUtil.*; 
import java.util.*;
import java.sql.*;
import vo.*;

public class MemberProcDao {
// 회원 가입 및 정보 수정 을 위한  쿼리 작업을 하는 Dao 클래스
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
	//사용자가 입력한 데이터들로 회원가입을 시키는 메소드
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
		
			
			if(result == 1 ) { // t_member_addr 테이블에 insert가 정상적으로 실행되었을 경우
				sql = "insert into t_member_addr(mi_id, ma_name, ma_rname, ma_phone, ma_zip, ma_addr1, ma_addr2) values(?, ?, ?, ?, ?, ?, ?)";
				pstms = conn.prepareStatement(sql);
				pstms.setString(1, memberAddr.getMi_id());					pstms.setString(2, memberAddr.getMa_name());
				pstms.setString(3, memberAddr.getMa_rname());				pstms.setString(4, memberAddr.getMa_phone());
				pstms.setString(5, memberAddr.getMa_zip());					pstms.setString(6, memberAddr.getMa_addr1());
				pstms.setString(7, memberAddr.getMa_addr2());			
				result += pstms.executeUpdate();
			
				if(result == 2) {// t_member_info 테이블에 insert가 정상적으로 실행되었을 경우
					sql = "insert into t_member_point(mi_id, mp_point, mp_desc) values(?, ?, '가입축하금')";
					pstms = conn.prepareStatement(sql);
					pstms.setString(1, memberInfo.getMi_id());
					pstms.setInt(2, memberInfo.getMi_point());
					result += pstms.executeUpdate();
				
				}
			}
		
		}
		catch(Exception e) {
			System.out.println("MemberProcDao클래스의 memberProcIn() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(pstms);
		}
		return result;
	}
}
