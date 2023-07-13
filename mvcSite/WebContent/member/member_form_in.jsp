<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
LocalDate today = LocalDate.now();
int cyear = today.getYear();
int cmonth = today.getMonthValue();
int cday = today.getDayOfMonth();
int last = today.lengthOfMonth();

String[] arrDomain = {"naver.com", "nate.com", "gmail.com"};
%>
<style>
#agreement {width:400px; height:100px; overflow:auto;}
</style>
<script src="/mvcSite/js/date_change.js"></script>
<script>
$(document).ready(function(){
	$("#e2").change(function(){
		if ($(this).val() == ""){
			$("#e3").val("");
		}else if ($(this).val() == "direct"){
			$("#e3").val("");
			$("#e3").focus();
		} else {
			$("#e3").val($(this).val());
		}
	});
});
function chkDupId(uid) {
// ajax(요청을 하지 않아도 동작함)를 이용한 아이디 중복체크를 위한 함수

	if(uid.length >= 4) {// 사용자가 입력한 아이디가 4자 이상이면
		$.ajax({
			type : "POST", 				//데이터 전송 방법
			url: "/mvcSite/dupId" , 	//전송한 데이터를 받을 서버의 url (dupId ->서블릿)
			data :{"uid" : uid}, 		// 지정한 url로 보낼 데이터의 이름과 값(GET방식으로 전송할 경우 비워둠)
			success : function(chkRs) {	//데이터를 보내 실행 결과를 chRs로 받아옴
				if(chkRs == 0) { 		//사용할 수 있는 아이디 이면
				$("#idMsg").html("<span style='color:blue;'>사용할 수 있는 아이디 입니다.</span>");
				$("#idChk").val("y"); 	//아이디 중복체크를 성공한 상태로 변경
			} else{	//중복된 아이디 이면
				$("#idMsg").html("<span style='color:red;'>이미 사용중인 아이디 입니다.</span>");
				$("#idChk").val("n"); 	//아이디 중복체크를 실패한 상태로 변경
			}	
		}
	  });		
	} else { // 사용자가 입력한 아이디가 4자 미만이면
		$("#idMsg").text("아이디는 4~20자 이내로 입력세요.");
		$("#idChk").val("n");
		// 아이디 중복 체크를 하지 않거나 실패한 상태로 변경
	}
	//alert($("#idChk").val());
}
function chkDupId1() {
// 팝업창을 이용한 아이디 중복  체크를 위한 함수
	awin = window.open("dup_id_form.jsp", "aa", "width=300,height=200,left=50,top=50");	// 팝업창 경로, 이름, 팝업창 설정
}
</script>
<h2>회원 가입 폼</h2>
<form name="frmJoin" action="memberProcIn" method="post">
<input type="hidden" name="idChk" id="idChk" value="n" >
<div id="agreement">
약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 
약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 
약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 
약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 
약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 약관 내용약관 내용약관 내용약관 내용약관 내용약관<br /> 내용약관 내용약관 내용약관 내용약관 내용약관 내용약관 내용<br /> 
</div>
<input type="radio" name="agree" value="y" id="agreeY">
<label for="agreeY">동의함</label>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="agree" value="n" id="agreeN" checked="checked">
<label for="agreeN">동의 안함</label>
<hr>
<div>
	<label for="mi_id">아이디 : </label>
<!-- 
	<input type="text" name="mi_id" id="mi_id" maxlength="20" readonly="readonly">
	<input type="button" value="중복 검사" onclick="chkDupId();">
-->
	<input type="text" name="mi_id" id="mi_id" maxlength="20" onkeyup="chkDupId(this.value);">
	<span id="idMsg">아이디는 4~20자 이내로 입력세요.</span>
	
	<br />
	<lable for="mi_pw">비밀번호 :</lable>
	<input type="password" name="mi_pw" id="mi_pw" maxlength="20" />
	<br />
	<lable for="mi_pw2">비밀번호 확인 :</lable>
	<input type="password" name="mi_pw2" id="mi_pw2" maxlength="20" />
	<br />
	<lable for="mi_name">이름 :</lable>
	<input type="text" name="mi_name" id="mi_name" maxlength="20" />
	<br />성별 : 
	<input type="radio" name="mi_gender" value="남" id="genderM" checked="checked"/>
	<label for="genderM" >남자</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="mi_gender" value="여" id="genderF" checked="checked"/>
	<label for="genderF" >여자</label>
	<br />생년월일 : 
	<select name="by" onchange="resetday(this.value, this.form.bm.value, this.form.bd);">
<% for (int i = 1930; i <= cyear ; i++){ %>
		<option <% if (i == cyear) {%> selected="selected" <%} %>><%=i %></option>
<%} %>
	</select> 
	<select name="bm" onchange="resetday(this.form.by.value, this.value, this.form.bd);">
<% for (int i = 1; i <= 12 ; i++){
		String bm = i + "";	
		if (i < 10) bm = "0" + i;
%>
		<option <% if (i == cmonth) {%> selected="selected" <%} %>><%=bm %></option>
<%} %>
	</select>월
	<select name="bd">
<% for (int i = 1; i <= last ; i++){
		String bd = i + "";	
		if (i < 10) bd = "0" + i;
%>
		<option <% if (i == cday) {%> selected="selected" <%} %>><%=bd %></option>
<%} %>
	</select>일
	<br />휴대폰 : 010 - 
	<input type="text" name="p2" size="4" maxlength="4" onkeyup="onlyNum(this);" />
	<input type="text" name="p3" size="4" maxlength="4" onkeyup="onlyNum(this);" />
	<br />이메일 : 
	<input type="text" name="e1" size="10" /> @ 
	<select name="e2" id="e2">
		<option value="">도메인 선택</option>
<% for (int i = 0 ; i < arrDomain.length ; i++) { %>		
		<option><%=arrDomain[i]%></option>
<% } %>
		<option value="direct">직접입력</option>
	</select>
	<input type="text" name="e3" id="e3" size="10" />
	<br />광고수신 : 
	<input type="radio" name="mi_isad" value="y" id="adY" checked="checked"/>
	<label for="adY" >수신</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="mi_isad" value="n" id="adN" />
	<label for="adN" >미수신</label>
	<br /><hr />
	우편번호 : <input type="text" name="ma_zip" size="5" maxlength="5" />
	<br /> 주소1 : <input type="text" name="ma_addr1" size="40" />
	<br /> 주소2 : <input type="text" name="ma_addr2" size="40" />
	<br /><hr />
	<input type="submit" value="회원가입" />
</div>
</form>
</body>
</html>