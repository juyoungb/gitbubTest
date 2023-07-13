package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class FreeReplyProcDao {
	//자유게시판 댓글 관련 쿼리 작업(목록, 등록, 삭제,좋아/싫어) 들을 모두 처리하는 클래스
	private static FreeReplyProcDao freeReplyProcDao;
	private Connection conn;
	private FreeReplyProcDao() {}
	
	public static FreeReplyProcDao getInstance() {
		if(freeReplyProcDao == null) freeReplyProcDao = new FreeReplyProcDao();	
		return freeReplyProcDao;
	}
	
	public void setConnection(Connection conn) {
	// 현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 저장하는 메소드
		this.conn = conn;
	}

	public ArrayList<FreeReply> getReplyList(int flidx) {
	// 지정한 게시글의 속하는 댓글들의 목록을 ArrayList<FreeReply>형으로 리턴하는 메소드	
		Statement stmt = null;
		ResultSet rs = null ;
		ArrayList<FreeReply> replyList = new  ArrayList<FreeReply>();
		//댓글들의 목록을 저장할 ArrayList 객체
		FreeReply freeReply = null;
		//replyList에 저장할 하나의 댓글 정보를 담을 인스턴스
		try {
			
			stmt = conn.createStatement();
			String sql= "select * from t_free_reply where fr_isview = 'y' and fl_idx =" + flidx;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				freeReply = new FreeReply();
				//하나의 댓글 정보들을 저장할 FreeReply형 인스턴스 생성
				freeReply.setFr_idx(rs.getInt("fr_idx"));
				freeReply.setFl_idx(flidx);
				freeReply.setMi_id(rs.getString("Mi_id"));
				freeReply.setFr_content(rs.getString("fr_content"));
				freeReply.setFr_good(rs.getInt("fr_good"));
				freeReply.setFr_bad(rs.getInt("fr_bad"));
				freeReply.setFr_ip(rs.getString("fr_ip"));
				freeReply.setFr_date(rs.getString("fr_date"));
				replyList.add(freeReply);			
			}
		} catch(Exception e) {
			System.out.println("FreeReplyProcDao클래스의 getReplyList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}
		return replyList;
	}

	public int replyInsert(FreeReply freeReply) {
	// 사용자가 입력한 댓글을 테이블에 저장시키는 메소드
		Statement stmt = null; //쿼리를 움반할Statement 
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "insert into t_free_reply (fl_idx, mi_id, fr_content, fr_ip) "
					+ "values (" + freeReply.getFl_idx() + ",'" + freeReply.getMi_id() + "','" + freeReply.getFr_content() + "','" + freeReply.getFr_ip() + "')";
			result = stmt.executeUpdate(sql); // 댓글 등록 쿼리 실행 
			
			sql = "update t_free_list set fl_reply = fl_reply + 1 where fl_idx = "+ freeReply.getFl_idx() ;
			stmt.executeUpdate(sql); //댓글 개수 증가 쿼리 
			
		}catch(Exception e) {
			System.out.println("FreeReplyProcDao클래스의 replyInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			 close(stmt);
		}
		return result;
	}

	public int replyGnb(FreeReplyGnb freeReplyGnb) {
	// 지정한 댓글에 좋아요/싫어요를 처리하는 메소드
	// 이미 참여했던 댓글일 경우 -1을, 정상처리 됬으면2를, 처리가 안됬으면 0이나 1 을 리턴
		Statement stmt = null; 
		ResultSet rs = null;  // 이미참여했는지 여부를 알기위해 select 쿼리를 돌려봐야함
		int result = 0;
		try {
			stmt = conn.createStatement();
			String sql = "select frg_gnb from t_free_reply_gnb where mi_id = '" + freeReplyGnb.getMi_id() + "' and fr_idx=" + freeReplyGnb.getFr_idx();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {// 이미 좋아요/싫어요에 참여했으면
				result = -1;
			} else {
				sql = "update t_free_reply set fr_" + freeReplyGnb.getFrg_gnb() + " = fr_" + freeReplyGnb.getFrg_gnb() + " +1 where fr_idx =" + freeReplyGnb.getFr_idx();
				result = stmt.executeUpdate(sql); 
				// 댓글의 좋아요/싫어요 개수 추가 쿼리를 실행
				
				sql = "insert into t_free_reply_gnb (mi_id, fr_idx, frg_gnb) "
						+ "values('" + freeReplyGnb.getMi_id() + "', " + freeReplyGnb.getFr_idx() + ", '" + freeReplyGnb.getFrg_gnb().charAt(0) + "')";	
			
				result += stmt.executeUpdate(sql); 
				// 댓글의 좋아요/싫어요  실행 쿼리
			}

		}catch(Exception e) {
			System.out.println("FreeReplyProcDao클래스의 replyGnb() 메소드 오류");
			e.printStackTrace();
		} finally {
			 close(rs); close(stmt);
		}
		return result;
	}

	public int replyDelete(FreeReply freeReply) {
	//지정한 댓글을 삭제시키는 메소드
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_free_reply set fr_isview = 'n' where fr_idx =" + freeReply.getFr_idx() + " and mi_id='" + freeReply.getMi_id() + "' ";
			result = stmt.executeUpdate(sql); // 댓글 삭제 쿼리
	
			sql = "update t_free_list set fl_reply = fl_reply -1 where fl_idx =" + freeReply.getFl_idx();		
			stmt.executeUpdate(sql); // 댓글 개수 감소 쿼리(댓글 감소 쿼리는 중요한것이 아니므로 result에 적용시키지는 않음)

		} catch(Exception e) {
			System.out.println("FreeReplyProcDao클래스의 replyDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}		
		return result;
	}

}
