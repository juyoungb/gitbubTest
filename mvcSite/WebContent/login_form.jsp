<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String url= request.getParameter("url"); //로그인 후 이동할 페이지 주소
if(url == null) url = ROOT_URL;
else 			url = url.replace('~', '&');
%>
<script>
function chkVal(frm) {
	var uid = frm.uid.value;  var pwd = frm.pwd.value;
	if(uid =="") {
		alert("아이디를 입력하세요."); frm.uid.focuse();  return false;
	}
	if(pwd =="") {
		alert("비밀번호를 입력하세요."); frm.pwd.focuse();  return false;
	}
	 return true;
}
</script>
<h2>로그인 폼</h2>
<form name="frmLogin" action="loginProc" method="post" onsubmit="return chkVal(this)">
<input type="hidden" name="url" value="<%=url %>">
아이디 : <input type="text" name="uid" value="test1"><br>
비밀번호 : <input type="password" name="pwd" value="1234"><br>
<input type="submit" value="로그인"><br>
</form>
</body>
</html>