<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%
request.setCharacterEncoding("utf-8");
String isChk = request.getParameter("isChk");
String uid = request.getParameter("uid");
String msg = "중복 검사할 아이디를 입력하세요.";
//아이디 중복 여부에 따라 보여줄 메시지르 저장할 변수
boolean isDup = true; //중복여부를 저장할 변수

if(isChk == null) { //검사를 하지 않고 처음 열릴 경우
	uid = "";	
} else { //중복검사를 할 경우
/*
1. DB연결 
2. 쿼리 생성
3. 쿼리 실행
4. 실행 결과에 따른 작업
*/
	String driver = "com.mysql.cj.jdbc.Driver";
	String dbURL = "jdbc:mysql://localhost:3306/mall?useUnicode=true&characterEncoding=UTF8&verifyServerCertificate=false&useSSL=false&serverTimezone=UTC";
	// 위와 같이 한번 사용하고 버리는 변수의 경우에는 단순한 이름이 아닌 복잡한 이름으로 한다
	
	Connection conn = null;		Statement stmt = null;	 	ResultSet rs = null;	
	try {
		Class.forName(driver);
		conn = DriverManager.getConnection(dbURL, "root", "1234");
		stmt = conn.createStatement();
		String sql = "select 1 from t_member_info where mi_id = '" + uid + "' ";
		rs = stmt.executeQuery(sql);
		if(rs.next()) {
			isDup = true;
			msg = "<span style='color:red; font-weight:bold;'>이미 사용중인 아이디 입니다.</span>";
		} else {
			isDup = false;
			msg = "<span style='color:blue; font-weight:bold;'>사용 가능한 아이디 입니다.</span>";		
		}
	} catch(Exception e) {
		out.println("DB연결에 문제가 발생했습니다.");
		e.printStackTrace();
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function trans(id) {
	opener.document.frmJoin.mi_id.value = id;	
	self.close();
}

function chkVal(frm) {
	var uid = frm.uid.value;
	if(uid =="") {
		alert("검사할 아이디를 입력하세요");
		frm.uid.focus(); return false;
	}
	return true;
}
</script>
</head>
<body onload="document.frmId.uid.select();">
<h2 align="center">아이디 중복 체크</h2>
<form name="frmId" method="post" onsubmit="return chkVal(this);">
<input type="hidden" name="isChk" value="y">
<p align="center">아이디 : <input type="text" name="uid" value="<%=uid %>"></p>
<p align="center"><%=msg %></p>
<p align="center">
	<input type="submit" value="중복 검사">
<% if(!isDup){ //검사한 아이디가 사용가능한 아이디일 경우%>
	<input type="button" value="아이디 적용" onclick="trans('<%=uid %>');">
<%} %>	
</p>
</form>
</body>
</html>