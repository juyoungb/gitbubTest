package svc;

import static db.JdbcUtil.*;  
import java.util.*;

import java.sql.*;
import dao.*;
import vo.*;

public class ProductProcSvc {
	public int getProductCount(String where) {
		int rcnt = 0;
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		rcnt = productProcDao.getProductCount(where);
		close(conn);
		
		return rcnt;
	}

	public ArrayList<ProductInfo> getProductList(int cpage, int psize, String where, String orderBy) {
		ArrayList<ProductInfo> ProcductList =new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		ProcductList = productProcDao.getProductList(cpage, psize, where, orderBy);
		close(conn);
		
		return ProcductList;
	}

	public ArrayList<ProductCtgrSmall> getCtgrSmallList(String pcb) {
		ArrayList<ProductCtgrSmall> smallList = new ArrayList<ProductCtgrSmall>();
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		smallList = productProcDao.getCtgrSmallList(pcb);
		close(conn);
		
		return smallList;
	}

	public ArrayList<ProductBrand> getBrandList() {
		ArrayList<ProductBrand> brandList = new ArrayList<ProductBrand>();
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		brandList = productProcDao.getBrandList();
		close(conn);
		
		return brandList;
	}

	public int readUpdate(String piid) {
		int result = 0;

		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		result = productProcDao.readUpdate(piid);
		if (result == 1)		commit(conn);
		else					rollback(conn);
		
		close(conn);
		
		return result;
	}

	public ProductInfo getProductInfo(String piid) {
		ProductInfo pi = null;

		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		pi = productProcDao.getProductInfo(piid);	
		close(conn);
		
		return pi;
	}

	public ArrayList<ReviewList> getReviewList(String piid) {
		ArrayList<ReviewList> reviewList = new ArrayList<ReviewList>();
		
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		reviewList = productProcDao.getReviewList(piid);	
		close(conn);
		
		return reviewList;
	}
	
}
