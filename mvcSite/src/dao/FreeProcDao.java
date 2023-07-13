package dao;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import vo.*;

public class FreeProcDao {
	//�����Խ��� ���� �����۾�(���, ���, ���� ,���� ��)���� ��� ó���ϴ� Ŭ����
		private static FreeProcDao freeProcDao;
		private Connection conn;
		private FreeProcDao() {}
		
		public static FreeProcDao getInstance() {
			if(freeProcDao == null) freeProcDao = new FreeProcDao();
			
			return freeProcDao;
		}
		
		public void setConnection(Connection conn) {
		// �� Dao Ŭ�������� ����� Ŀ�ؼ� ��ü�� �޾ƿͼ� �����ϴ� �޼ҵ�
			this.conn = conn;
		}

		public int getFreeListCount(String where ) {
			//�����Խ��ǿ��� �˻��� ����� ���ڵ�(�����Խ��� �Խñ�) ������ �����ϴ� �޼ҵ�
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
				System.out.println("FreeProcDaoŬ������ getFreeListCount() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return rcnt;
		}
		public ArrayList<FreeList> getFreeList(String where, int cpage, int psize) {
		//�����Խ��ǿ��� �˻��� ����� ���ڵ�(�Խù�) �����  ArrayList<FreeList>�� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<FreeList> freeList = new ArrayList<FreeList>();
			FreeList fl = null;	//freeList�� ������ �Խñ� �ϳ��� ������ ������ �ν��Ͻ�
			
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
				System.out.println("FreeProcDao Ŭ������ getFreeList() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return freeList;
		}

		public int freeInsert(FreeList freeList) {
		// �����Խ��� �Խñ� ��� ó�� �޼ҵ�(�۹�ȣ�� ����)
			int flidx = 1, result = 0;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "select max(fl_idx) fl_idx from t_free_list";			
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next())   flidx = rs.getInt(1) + 1 ; //���Ӱ� ����� �Խñ��� �۹�ȣ�� �����Ͽ� flidx�� ����
			
				sql ="insert into t_free_list (fl_idx, fl_ismem, fl_writer, fl_pw, fl_title,fl_content, fl_ip) "+ 
						" values("+flidx+", '"+freeList.getFl_ismem()+"', '"+freeList.getFl_writer()+"', '"+freeList.getFl_pw()+"', '"+freeList.getFl_title()+"', '"+freeList.getFl_content()+"', '"+freeList.getFl_ip()+"')";
				
				 result = stmt.executeUpdate(sql);
				if(result == 0)  flidx = 0; // return 0;
				// ���� �߻��� �۹�ȣ�� �ƴ� 0�� �����Ͽ� svc���� rollback�� ��Ŵ
				
			}
			catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ freeInsert() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return flidx;
		}

		public int readUpdate(int flidx) {
		// ������ �Խñ��� ��ȸ���� ������Ű�� �޼ҵ�
			int result = 0;
			Statement stmt = null;
		
			try {
					
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_read = fl_read + 1 where fl_idx ="+ flidx;
				result = stmt.executeUpdate(sql);	
				
			}
			catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ readUpdate() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}
			
			return result;
		}

		public FreeList getFreeInfo(int flidx) {
		// ������ �Խñ��� �������� FreeList�� �ν��Ͻ��� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			FreeList freeList = null;
			
			try {
				String sql = "select *  from t_free_list where fl_isview = 'y' and fl_idx = " + flidx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					freeList = new FreeList();//�Խñ� ������ ������ �ν��Ͻ� ����
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
				System.out.println("FreeProcDao Ŭ������ getFreeInfo() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return freeList;
		}

		public String getIsMem(int flidx) {
		// ������ ���� ��ȸ�� ������ ���θ� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			String ismem = null;
			
			try {
				
				String sql = "select fl_ismem from t_free_list where fl_isview = 'y' and fl_idx = " + flidx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) ismem = rs.getString("fl_ismem");
				
			} catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ getIsMem() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return ismem;
		}

		public FreeList getFreeInfoUp(String where) {
		// ������ Ư�� �Խñ��� �������� FreeList�� �ν��Ͻ��� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			FreeList freeList = null;
			try {
				
				String sql = "select * from t_free_list where fl_isview = 'y' "+ where ;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if(rs.next()) {
					freeList = new FreeList();//�Խñ� ������ ������ �ν��Ͻ� ����
					freeList.setFl_idx(rs.getInt("fl_idx"));
					freeList.setFl_ismem(rs.getString("fl_ismem"));
					freeList.setFl_writer(rs.getString("fl_writer"));		
					freeList.setFl_title(rs.getString("fl_title"));
					freeList.setFl_content(rs.getString("fl_content"));
					freeList.setFl_date(rs.getString("fl_date"));
							
				}
					
			} catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ getFreeInfoUp() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(rs);		 close(stmt);
			}
			return freeList;
		}

		public int freeUpdate(FreeList freeList) {
		// ������ Ư�� �Խñ��� �������� FreeList�� �ν��Ͻ��� �޾� �����۾��� ó���ϴ�  �޼ҵ�
			Statement stmt = null;
			int result = 0;
			try {
				
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_title ='"+freeList.getFl_title()+"', fl_content ='"+freeList.getFl_content()+"' where fl_idx = "+freeList.getFl_idx()+ " and fl_isview ='y' ";
				result = stmt.executeUpdate(sql);		
			}
			catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ freeUpdate() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}		
			return result;	
		}

		public int freeDelete(String where) {
			// ������ Ư�� �Խñ��� ������ �޾� ���� �۾��� ó���ϴ�  �޼ҵ�
			Statement stmt = null;
			int result = 0;
			
			try {				
				stmt = conn.createStatement();					
				String sql =" update t_free_list set fl_isview='n' " + where;
				result = stmt.executeUpdate(sql);		
			}
			catch(Exception e) {
				System.out.println("FreeProcDao Ŭ������ freeDelete() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}		
			return result;	
		}	
}
