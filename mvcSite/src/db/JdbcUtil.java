package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class JdbcUtil {
 // DB관련 연결 및 공용 메소드들을 정의하는 클래스(사이트 전체에서 쓰는 DB연결) - 모든 메소드는 public static으로 지정
	public static Connection getConnection() {
	// DB에 연결하여 컨넥션 객체를 리턴하는 메소드
		Connection conn = null;	
		try {
			 Context initCtx = new InitialContext();
	         Context envCtx = (Context)initCtx.lookup("java:comp/env");
	         DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
	         conn = ds.getConnection();
	         conn.setAutoCommit(false);
	         // 쿼리가 자동으로 commit 되는 막는 명령으로 트랜젝션(쿼리문의 변경이 있는경우(update,insert,delete))을 시작시킴
	         
		} catch(Exception e) {
			System.out.println("DB 연결 실패!!!!!!!!!!");
			e.printStackTrace();		
		}
		return conn;
	}  
	//오버로딩 
	public static void close(Connection conn) {
	// Connection 객체를 닫아 DB와의 연결을 끊어주는 메소드	
		try { conn.close();} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void close(Statement stmt) {
	// PreparedStatement와 CallableStatement는 둘 다 Statement를 상속받으므로 따로 close() 메소드가 필요없음	
		try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void close(ResultSet rs) {
	// ResultSet 객체를 닫아 DB와의 연결을 끊어주는 메소드	
		try { rs.close(); } catch(Exception e) { e.printStackTrace(); }
	} 
	
	public static void commit(Connection conn) {
	// transaction을 commit 시키는 메소드
		try {
			conn.commit();
			System.out.println("쿼리 성공");
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void rollback(Connection conn) {
	// transaction을 rollback 시키는 메소드
		try {
			conn.rollback();
			System.out.println("쿼리 실패");
		} catch(Exception e) { e.printStackTrace(); }
	}
}
