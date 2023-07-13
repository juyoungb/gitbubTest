<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.time.*" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
final String ROOT_URL = "/mvcSite/";

MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
boolean isLogin = false;
if (loginInfo != null) isLogin = true;
// 로그인 여부를 판단할 변수 isLogin 생성

String loginUrl = request.getRequestURI();
if (request.getQueryString() != null)
	loginUrl += "?" + URLEncoder.encode(request.getQueryString().replace('&', '~'), "UTF-8");
// 현재 화면의 url로 로그인 폼 등에서 사용할 값
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
a:link { color:black; text-decoration:none;}
a:visited { color:black; text-decoration:none;}
a:hover { color:red; text-decoration:underline;}

#list th, #list td { padding:8px 3px; }
#list th { border-bottom:double black 3px; }
#list td { border-bottom:dotted black 1px; }

.hand { cursor:pointer; }
.bold { font-weight:bold; }
</style>
<script src="<%=ROOT_URL %>js/jquery-3.6.4.js"></script>
<script>
function onlyNum(obj) {
	if (isNaN(obj.value)) {
		// isNaN = 숫자가 아니면
		obj.value = "";
	}
}
</script>
</head>
<body>
<a href="<%=ROOT_URL %>">HOME</a>&nbsp;&nbsp;&nbsp;&nbsp;
<% if(isLogin) { %>
<a href="<%=ROOT_URL %>logout.jsp">로그아웃</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="memberFormUp">정보수정</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="cartView">장바구니</a>&nbsp;&nbsp;&nbsp;&nbsp;
<% } else {%>
<a href="login_form">로그인</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="memberFormIn">회원가입</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="login_form?url=cartView">장바구니</a>&nbsp;&nbsp;&nbsp;&nbsp;
<% }%> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="productList?pcb=AA" class="bold">구두</a>&nbsp;&nbsp;&nbsp;
<a href="productList?pcb=BB" class="bold">운동화</a>
<hr />

