package svc;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartProcSvc {	
	public int cartInsert(OrderCart oc) {
		int result = 0;
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
		
		result = cartProcDao.cartInsert(oc);			
		if(result == 1)     commit(conn);
		else 				rollback(conn);
		close(conn);
		
		return result;
	}

	public ArrayList<OrderCart> getCartList(String miid) {
		ArrayList<OrderCart> cartList = new ArrayList<OrderCart>();		
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
		
		cartList = cartProcDao.getCartList(miid);			
		close(conn);
		
		return cartList;
	}

	public int cartDelete(String where) {
		int result = 0;
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
		
		result = cartProcDao.cartDelete(where);		
		if(result >= 1)  commit(conn);		//여러상품을 삭제할 경우 적용된(삭제)된 레코드가 1이 넘을 수 있으므로 1이상으로 조건을 줌 
		else 			rollback(conn);
		close(conn);
		
		return result;
	}

	public int CartUpdate(OrderCart oc) {
		int result = 0;
		Connection conn = getConnection();
		CartProcDao cartProcDao = CartProcDao.getInstance();
		cartProcDao.setConnection(conn);
		
		result = cartProcDao.CartUpdate(oc);		
		if(result == 1)  commit(conn);		
		else 			rollback(conn);
		close(conn);
		
		return result;
	}
}
