package svc;

import static db.JdbcUtil.*;  
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderProcSvc {
	public ArrayList<OrderCart> getBuyList(String kind, String sql) {
		ArrayList<OrderCart> pdtList = new ArrayList<OrderCart>();
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		pdtList = orderProcDao.getBuyList(kind, sql);			
		close(conn);
	
		return pdtList;
	}

	public ArrayList<MemberAddr> getAddrList(String miid) {
		ArrayList<MemberAddr> addrList = new ArrayList<MemberAddr>();
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		addrList = orderProcDao.getAddrList(miid);			
		close(conn);
		return addrList;
	}

	public String orderInsert(String kind, OrderInfo oi, String ocidxs) {
		String result =null;
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		result = orderProcDao.orderInsert(kind, oi, ocidxs);			
		// result  : �ֹ���ȣ[0],����ȷ��ڵ��[1],����Ǿ���ҷ��ڵ��[2]
		String[] arr = result.split(",");
		if (arr[1].equals(arr[2]))  commit(conn);
		// ���� ����ȷ��ڵ� ������ ����Ǿ���� ���ڵ尳���� ������
		else						rollback(conn);
		close(conn);
		
		return result;
	}

	public OrderInfo getOrderInfo(String miid, String oiid) {
		OrderInfo orderInfo = null;
		Connection conn = getConnection();
		OrderProcDao orderProcDao = OrderProcDao.getInstance();
		orderProcDao.setConnection(conn);
		
		orderInfo = orderProcDao.orderInsert(miid, oiid);	
		close(conn);
		
		return orderInfo;
	}
}
