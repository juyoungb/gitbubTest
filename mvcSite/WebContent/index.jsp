<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<a href="freeList">자유 게시판</a>
<hr />
<h2>상품 목록</h2>
<form name="frm" action="product_list" method="get">
<input type="text" name="keyword" placeholder="상품명 검색" size="8" />
<input type="submit" value="검색 ">
</form>
<hr />
<a href="comboCtgr?pcb=AA">상품 분류</a>
<hr />
