package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class JdbcUtil {
 // DB���� ���� �� ���� �޼ҵ���� �����ϴ� Ŭ����(����Ʈ ��ü���� ���� DB����) - ��� �޼ҵ�� public static���� ����
	public static Connection getConnection() {
	// DB�� �����Ͽ� ���ؼ� ��ü�� �����ϴ� �޼ҵ�
		Connection conn = null;	
		try {
			 Context initCtx = new InitialContext();
	         Context envCtx = (Context)initCtx.lookup("java:comp/env");
	         DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
	         conn = ds.getConnection();
	         conn.setAutoCommit(false);
	         // ������ �ڵ����� commit �Ǵ� ���� ������� Ʈ������(�������� ������ �ִ°��(update,insert,delete))�� ���۽�Ŵ
	         
		} catch(Exception e) {
			System.out.println("DB ���� ����!!!!!!!!!!");
			e.printStackTrace();		
		}
		return conn;
	}  
	//�����ε� 
	public static void close(Connection conn) {
	// Connection ��ü�� �ݾ� DB���� ������ �����ִ� �޼ҵ�	
		try { conn.close();} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void close(Statement stmt) {
	// PreparedStatement�� CallableStatement�� �� �� Statement�� ��ӹ����Ƿ� ���� close() �޼ҵ尡 �ʿ����	
		try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void close(ResultSet rs) {
	// ResultSet ��ü�� �ݾ� DB���� ������ �����ִ� �޼ҵ�	
		try { rs.close(); } catch(Exception e) { e.printStackTrace(); }
	} 
	
	public static void commit(Connection conn) {
	// transaction�� commit ��Ű�� �޼ҵ�
		try {
			conn.commit();
			System.out.println("���� ����");
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void rollback(Connection conn) {
	// transaction�� rollback ��Ű�� �޼ҵ�
		try {
			conn.rollback();
			System.out.println("���� ����");
		} catch(Exception e) { e.printStackTrace(); }
	}
}
