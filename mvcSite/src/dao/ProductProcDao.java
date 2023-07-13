package dao;

import static db.JdbcUtil.*; 
import java.util.*;
import java.sql.*;
import vo.*;

public class ProductProcDao {
// 상품 관련 작업(목록 및 상세보기 화면)들을 처리하는 클래스
	private static ProductProcDao productProcDao;
	private Connection conn;
	private ProductProcDao() {}
	
	public static ProductProcDao getInstance() {
		if(productProcDao == null) productProcDao = new ProductProcDao();
		return productProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getProductCount(String where) {
		// 검색되는 상품의 개수(select 쿼리 실행 결과)를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int rcnt = 0;
		try {
			stmt = conn.createStatement();
			String sql = "select count(*) from t_product_info a where a.pi_isview = 'y' "+ where ;
			rs = stmt.executeQuery(sql);
			if(rs.next())  rcnt = rs.getInt(1);
		
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getProductCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);  close(stmt);
		}
		return rcnt;
	}
	public ArrayList<ProductInfo> getProductList(int cpage, int psize, String where, String orderBy) {
	//검색되는 상품들의 목록을 지정한 페이지에 맞춰  ArrayList<ProductInfo>()형으로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		ProductInfo pi = null;
		try {
			stmt = conn.createStatement();
			String sql = "select a.pi_id, a.pi_name, a.pi_img1, a.pi_price, a.pi_dc, a.pi_special, a.pi_review, a.pi_sale, pi_score, sum(b.ps_stock) stock " + 
					" from t_product_info a, t_product_stock b where a.pi_id = b.pi_id and "
					+ "a.pi_isview = 'y' "+  where +" group by a.pi_id " + orderBy +" limit " + ((cpage - 1) * psize) + "," + psize ;
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_price(rs.getInt("pi_price"));
				pi.setPi_dc(rs.getDouble("pi_dc"));
				pi.setPi_special(rs.getString("pi_special"));
				pi.setPi_review(rs.getInt("pi_review"));			
				pi.setPi_sale(rs.getInt("pi_sale"));
				pi.setPi_score(rs.getDouble("pi_score"));
				pi.setStock(rs.getInt("stock"));
				productList.add(pi);
			}
		
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getProductCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);  close(stmt);
		}
		return productList;
 }

	public ArrayList<ProductCtgrSmall> getCtgrSmallList(String pcb) {
		// 특정 대분류에속하는 소분류 목록을 ArrayList<ProductCtgrSmall>형으로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductCtgrSmall> productList = new ArrayList<ProductCtgrSmall>();
		ProductCtgrSmall pcs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_product_ctgr_small where pcb_id ='"+ pcb + "' ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pcs = new ProductCtgrSmall();
				pcs.setPcb_id(pcb);
				pcs.setPcs_id(rs.getString("pcs_id"));
				pcs.setPcs_name(rs.getString("pcs_name"));
				productList.add(pcs);
			}
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getCtgrSmallList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);  close(stmt);
		}
		return productList;
	}

	public ArrayList<ProductBrand> getBrandList() {
		//검색조건에서 보여줄 브랜드 목록을  ArrayList<ProductBrand>형으로 리턴하는 메소드	
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductBrand> brandList = new ArrayList<ProductBrand>();
		ProductBrand br = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_product_brand order by pb_name asc";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				br = new ProductBrand();
				br.setPb_id(rs.getString("pb_id"));
				br.setPb_name(rs.getString("pb_name"));
				brandList.add(br);
			}
		
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getBrandList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);  close(stmt);
		}
		return brandList;
	}

	public int readUpdate(String piid) {
	// 특정 상품의 조회수를 1증가 시키는 메소드
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_product_info set pi_read  = pi_read + 1 where pi_isview = 'y' and pi_id ='" + piid + "' ";
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 readUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}

	public ProductInfo getProductInfo(String piid) {
	// 사용자가 선택한 상품의 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs= null;
		ProductInfo pi = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select a.*, b.pcb_name, c.pcs_name, d.pb_name from t_product_info a, t_product_ctgr_big b, t_product_ctgr_small c, "
					+ " t_product_brand d where a.pcs_id = c.pcs_id and b.pcb_id = c.pcb_id and a.pb_id = d.pb_id " + 
					"and a.pi_isview = 'y' and a.pi_id ='" + piid + "' ";
		//	System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pi = new ProductInfo();		// 상품정보를 저장할 인스턴스 생성
				pi.setPi_id(rs.getString("pi_id"));
	            pi.setPcs_id(rs.getString("pcs_id"));
	            pi.setPb_id(rs.getString("pb_id"));
	            pi.setPi_name(rs.getString("pi_name"));
	            pi.setPi_price(rs.getInt("pi_price"));
	            pi.setPi_cost(rs.getInt("pi_cost"));
	            pi.setPi_dc(rs.getDouble("pi_dc"));
	            pi.setPi_com(rs.getString("pi_com"));
	            pi.setPi_img1(rs.getString("pi_img1"));
	            pi.setPi_img2(rs.getString("pi_img2"));
	            pi.setPi_img3(rs.getString("pi_img3"));
	            pi.setPi_desc(rs.getString("pi_desc"));
	            pi.setPi_special(rs.getString("pi_special"));
	            pi.setPi_read(rs.getInt("pi_read"));
	            pi.setPi_score(rs.getDouble("pi_score"));
	            pi.setPi_review(rs.getInt("pi_review"));
	            pi.setPi_sale(rs.getInt("pi_sale"));
	            pi.setPi_isview(rs.getString("pi_isview"));
	            pi.setPi_date(rs.getString("pi_date"));
	            pi.setPi_last(rs.getString("pi_last"));
	            pi.setPcb_name(rs.getString("pcb_name"));
	            pi.setPcs_name(rs.getString("pcs_name"));
	            pi.setPb_name(rs.getString("pb_name"));		
	            pi.setStockList(getStockList(piid));
	            // 현상품의 사이즈와 재고량을 목록을 ArrayList로 받아 pi에 저장
			}
			
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getProductInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return pi;
	}
	public ArrayList<ProductStock> getStockList(String piid) {
		//지정한 상품의 옵션별 재고량 목록을 ArrayList형으로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductStock> stockList = new ArrayList<ProductStock>();
		ProductStock pds = null;
		
		try {
			stmt = conn.createStatement();
			String sql= "select ps_idx, ps_size, ps_stock from t_product_stock where ps_isview = 'y' and pi_id = '" + piid + "' order by ps_size ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pds = new ProductStock();	
				pds.setPs_idx(rs.getInt("ps_idx"));
				pds.setPs_size(rs.getInt("ps_size"));
				pds.setPi_id(piid);
				pds.setPs_stock(rs.getInt("ps_stock"));
				stockList.add(pds);				
			}
			
		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getStockList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return stockList;
	}

	public ArrayList<ReviewList> getReviewList(String piid) {
	// 특정 상품의 후기 목록을 ArrayList<ReviewList>형으로 리턴하는 메소드 
		Statement stmt = null; 
		ResultSet rs = null;
		ArrayList<ReviewList> reviewList =new ArrayList<ReviewList>();
		ReviewList rl = null;
		
		try {
			stmt = conn.createStatement();
			String sql ="select * from t_review_list where rl_isview='y' and pi_id = '" + piid + "' order by rl_date desc";
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				rl = new ReviewList();
				rl.setRl_idx(rs.getInt("rl_idx"));
				rl.setMi_id(rs.getString("mi_id"));
				rl.setOi_id(rs.getString("oi_id"));
				rl.setPi_id(piid);
				rl.setPs_idx(rs.getInt("ps_idx"));
				rl.setRl_name(rs.getString("rl_name"));
				rl.setRl_title(rs.getString("rl_title"));
				rl.setRl_content(rs.getString("rl_content"));
				rl.setRl_img(rs.getString("rl_img"));
				rl.setRl_score(rs.getDouble("rl_score"));
				rl.setRl_read(rs.getInt("rl_read"));
				rl.setRl_good(rs.getInt("rl_good"));
				rl.setRl_ip(rs.getString("rl_ip"));
				rl.setRl_isview(rs.getString("rl_isview"));
				rl.setRl_date(rs.getString("rl_date"));
				reviewList.add(rl);
			}

		} catch(Exception e) {
			System.out.println("ProductProcDao클래스의 getReviewList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}	
		return reviewList;
	}
}