package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class FreeReplyProcDao {
	//�����Խ��� ��� ���� ���� �۾�(���, ���, ����,����/�Ⱦ�) ���� ��� ó���ϴ� Ŭ����
	private static FreeReplyProcDao freeReplyProcDao;
	private Connection conn;
	private FreeReplyProcDao() {}
	
	public static FreeReplyProcDao getInstance() {
		if(freeReplyProcDao == null) freeReplyProcDao = new FreeReplyProcDao();	
		return freeReplyProcDao;
	}
	
	public void setConnection(Connection conn) {
	// �� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �����ϴ� �޼ҵ�
		this.conn = conn;
	}

	public ArrayList<FreeReply> getReplyList(int flidx) {
	// ������ �Խñ��� ���ϴ� ��۵��� ����� ArrayList<FreeReply>������ �����ϴ� �޼ҵ�	
		Statement stmt = null;
		ResultSet rs = null ;
		ArrayList<FreeReply> replyList = new  ArrayList<FreeReply>();
		//��۵��� ����� ������ ArrayList ��ü
		FreeReply freeReply = null;
		//replyList�� ������ �ϳ��� ��� ������ ���� �ν��Ͻ�
		try {
			
			stmt = conn.createStatement();
			String sql= "select * from t_free_reply where fr_isview = 'y' and fl_idx =" + flidx;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				freeReply = new FreeReply();
				//�ϳ��� ��� �������� ������ FreeReply�� �ν��Ͻ� ����
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
			System.out.println("FreeReplyProcDaoŬ������ getReplyList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}
		return replyList;
	}

	public int replyInsert(FreeReply freeReply) {
	// ����ڰ� �Է��� ����� ���̺� �����Ű�� �޼ҵ�
		Statement stmt = null; //������ �����Statement 
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "insert into t_free_reply (fl_idx, mi_id, fr_content, fr_ip) "
					+ "values (" + freeReply.getFl_idx() + ",'" + freeReply.getMi_id() + "','" + freeReply.getFr_content() + "','" + freeReply.getFr_ip() + "')";
			result = stmt.executeUpdate(sql); // ��� ��� ���� ���� 
			
			sql = "update t_free_list set fl_reply = fl_reply + 1 where fl_idx = "+ freeReply.getFl_idx() ;
			stmt.executeUpdate(sql); //��� ���� ���� ���� 
			
		}catch(Exception e) {
			System.out.println("FreeReplyProcDaoŬ������ replyInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			 close(stmt);
		}
		return result;
	}

	public int replyGnb(FreeReplyGnb freeReplyGnb) {
	// ������ ��ۿ� ���ƿ�/�Ⱦ�並 ó���ϴ� �޼ҵ�
	// �̹� �����ߴ� ����� ��� -1��, ����ó�� ������2��, ó���� �ȉ����� 0�̳� 1 �� ����
		Statement stmt = null; 
		ResultSet rs = null;  // �̹������ߴ��� ���θ� �˱����� select ������ ����������
		int result = 0;
		try {
			stmt = conn.createStatement();
			String sql = "select frg_gnb from t_free_reply_gnb where mi_id = '" + freeReplyGnb.getMi_id() + "' and fr_idx=" + freeReplyGnb.getFr_idx();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {// �̹� ���ƿ�/�Ⱦ�信 ����������
				result = -1;
			} else {
				sql = "update t_free_reply set fr_" + freeReplyGnb.getFrg_gnb() + " = fr_" + freeReplyGnb.getFrg_gnb() + " +1 where fr_idx =" + freeReplyGnb.getFr_idx();
				result = stmt.executeUpdate(sql); 
				// ����� ���ƿ�/�Ⱦ�� ���� �߰� ������ ����
				
				sql = "insert into t_free_reply_gnb (mi_id, fr_idx, frg_gnb) "
						+ "values('" + freeReplyGnb.getMi_id() + "', " + freeReplyGnb.getFr_idx() + ", '" + freeReplyGnb.getFrg_gnb().charAt(0) + "')";	
			
				result += stmt.executeUpdate(sql); 
				// ����� ���ƿ�/�Ⱦ��  ���� ����
			}

		}catch(Exception e) {
			System.out.println("FreeReplyProcDaoŬ������ replyGnb() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			 close(rs); close(stmt);
		}
		return result;
	}

	public int replyDelete(FreeReply freeReply) {
	//������ ����� ������Ű�� �޼ҵ�
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_free_reply set fr_isview = 'n' where fr_idx =" + freeReply.getFr_idx() + " and mi_id='" + freeReply.getMi_id() + "' ";
			result = stmt.executeUpdate(sql); // ��� ���� ����
	
			sql = "update t_free_list set fl_reply = fl_reply -1 where fl_idx =" + freeReply.getFl_idx();		
			stmt.executeUpdate(sql); // ��� ���� ���� ����(��� ���� ������ �߿��Ѱ��� �ƴϹǷ� result�� �����Ű���� ����)

		} catch(Exception e) {
			System.out.println("FreeReplyProcDaoŬ������ replyDelete() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}		
		return result;
	}

}
