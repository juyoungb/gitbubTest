package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class FreeProcDao {
	//자유게시판 관련 쿼리작업(목록, 등록, 수정 ,삭제 등)들을 모두 처리하는 클래스
		private static FreeProcDao freeProcDao;
		private Connection conn;
		private FreeProcDao() {}
		
		public static FreeProcDao getInstance() {
			if(freeProcDao == null) freeProcDao = new FreeProcDao();
			
			return freeProcDao;
		}
		
		public void setConnection(Connection conn) {
		// 현 Dao 클래스에서 사용할 커넥션 객체를 받아와서 저장하는 메소드
			this.conn = conn;
		}

		public int getFreeListCount(String where ) {
			//자유게시판에서 검색된 결과의 레코드(자유게시판 게시글) 개수를 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			int rcnt = 0;
		
			try {
				String sql="select count(*) from t_free_list " + where;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) rcnt = rs.getInt(1);
			}
			catch(Exception e) {
				System.out.println("FreeProcDao클래스의 getFreeListCount() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return rcnt;
		}
		public ArrayList<FreeList> getFreeList(String where, int cpage, int psize) {
		//자유게시판에서 검색된 결과의 레코드(게시물) 목록을  ArrayList<FreeList>로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<FreeList> freeList = new ArrayList<FreeList>();
			FreeList fl = null;	//freeList에 저장할 게시글 하나의 정보를 저장할 인스턴스
			
			try {
				String sql = "select fl_idx, fl_ismem, fl_writer, fl_title, fl_reply, fl_read, fl_ip, if(curdate() = date(fl_date), right(fl_date,8),"+
						 " replace(mid(fl_date, 3, 8), '-', '.')) wdate from t_free_list " + where + " order by fl_idx desc limit " + ((cpage -1) * psize) + ", " + psize ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					fl = new FreeList();
					fl.setFl_idx(rs.getInt("fl_idx"));
					fl.setFl_ismem(rs.getString("fl_ismem"));
					fl.setFl_writer(rs.getString("fl_writer"));
					fl.setFl_title(rs.getString("fl_title"));
					fl.setFl_reply(rs.getInt("fl_reply"));
					fl.setFl_read(rs.getInt("fl_read"));
					fl.setFl_ip(rs.getString("fl_ip"));
					fl.setFl_date(rs.getString("wdate"));
					freeList.add(fl);
				}
			}
			catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 getFreeList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return freeList;
		}

		public int freeInsert(FreeList freeList) {
		// 자유게시판 게시글 등록 처리 메소드(글번호를 리턴)
			int flidx = 1, result = 0;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "select max(fl_idx) fl_idx from t_free_list";			
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next())   flidx = rs.getInt(1) + 1 ; //새롭게 등록할 게시글의 글번호를 생성하여 flidx에 저장
			
				sql ="insert into t_free_list (fl_idx, fl_ismem, fl_writer, fl_pw, fl_title,fl_content, fl_ip) "+ 
						" values("+flidx+", '"+freeList.getFl_ismem()+"', '"+freeList.getFl_writer()+"', '"+freeList.getFl_pw()+"', '"+freeList.getFl_title()+"', '"+freeList.getFl_content()+"', '"+freeList.getFl_ip()+"')";
				
				 result = stmt.executeUpdate(sql);
				if(result == 0)  flidx = 0; // return 0;
				// 오류 발생시 글번호가 아닌 0을 리턴하여 svc에서 rollback을 시킴
				
			}
			catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 freeInsert() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return flidx;
		}

		public int readUpdate(int flidx) {
		// 지정한 게시글의 조회수를 증가시키는 메소드
			int result = 0;
			Statement stmt = null;
		
			try {
					
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_read = fl_read + 1 where fl_idx ="+ flidx;
				result = stmt.executeUpdate(sql);	
				
			}
			catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 readUpdate() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}
			
			return result;
		}

		public FreeList getFreeInfo(int flidx) {
		// 지정한 게시글의 정보들을 FreeList형 인스턴스로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			FreeList freeList = null;
			
			try {
				String sql = "select *  from t_free_list where fl_isview = 'y' and fl_idx = " + flidx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					freeList = new FreeList();//게시글 정보를 저장할 인스턴스 생성
					freeList.setFl_idx(flidx);
					freeList.setFl_ismem(rs.getString("fl_ismem"));
					freeList.setFl_writer(rs.getString("fl_writer"));
					freeList.setFl_pw(rs.getString("fl_pw"));
					freeList.setFl_title(rs.getString("fl_title"));
					freeList.setFl_content(rs.getString("fl_content"));
					freeList.setFl_date(rs.getString("fl_date"));
					freeList.setFl_read(rs.getInt("fl_read"));
					freeList.setFl_ip(rs.getString("fl_ip"));					
				}
								
			}catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 getFreeInfo() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return freeList;
		}

		public String getIsMem(int flidx) {
		// 지정한 글이 비회원 글인지 여부를 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			String ismem = null;
			
			try {
				
				String sql = "select fl_ismem from t_free_list where fl_isview = 'y' and fl_idx = " + flidx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) ismem = rs.getString("fl_ismem");
				
			} catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 getIsMem() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return ismem;
		}

		public FreeList getFreeInfoUp(String where) {
		// 수정할 특정 게시글의 정보들을 FreeList형 인스턴스로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			FreeList freeList = null;
			try {
				
				String sql = "select * from t_free_list where fl_isview = 'y' "+ where ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					freeList = new FreeList();//게시글 정보를 저장할 인스턴스 생성
					freeList.setFl_idx(rs.getInt("fl_idx"));
					freeList.setFl_ismem(rs.getString("fl_ismem"));
					freeList.setFl_writer(rs.getString("fl_writer"));		
					freeList.setFl_title(rs.getString("fl_title"));
					freeList.setFl_content(rs.getString("fl_content"));
					freeList.setFl_date(rs.getString("fl_date"));
							
				}
					
			} catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 getFreeInfoUp() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return freeList;
		}

		public int freeUpdate(FreeList freeList) {
		// 수정할 특정 게시글의 정보들을 FreeList형 인스턴스로 받아 수정작업을 처리하는  메소드
			Statement stmt = null;
			int result = 0;
			try {
				
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_title ='"+freeList.getFl_title()+"', fl_content ='"+freeList.getFl_content()+"' where fl_idx = "+freeList.getFl_idx()+ " and fl_isview ='y' ";
				result = stmt.executeUpdate(sql);		
			}
			catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 freeUpdate() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}		
			return result;	
		}

		public int freeDelete(String where) {
			// 삭제할 특정 게시글의 정보를 받아 삭제 작업을 처리하는  메소드
			Statement stmt = null;
			int result = 0;
			
			try {				
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_isview='n' " + where;
				result = stmt.executeUpdate(sql);		
			}
			catch(Exception e) {
				System.out.println("FreeProcDao 클래스의 freeDelete() 메소드 오류");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}		
			return result;	
		}	
}
