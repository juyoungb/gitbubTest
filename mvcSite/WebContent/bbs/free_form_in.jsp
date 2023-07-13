<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<h2>자유게시판 글 등록 폼</h2>
<form name="frm" action="freeProcIn" method="post">
<table width="600" cellpadding="5">
<% if (!isLogin) {//비회원이 글을 등록할 경우 (작성자와 비밀번호 입력란이 보임)%>
<tr>
<th width="15%">작성자</th>
<td width="35%"><input type="text" name="fl_writer"></td>
<th width="15%">비밀번호</th>
<td width="35%"><input type="password" name="fl_pw"></td>
</tr>
<%} %>
<tr>
<th width="15%">글제목</th>
<td colspan="3"><input type="text" name="fl_title" size="60"></td>
</tr>
<tr>
<th>글내용</th>
<td colspan="3"><textarea name="fl_content" rows="10" cols="65"></textarea></td>
</tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="글 등록">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력">
</td></tr>
</table>
</form>
</body>
</html>