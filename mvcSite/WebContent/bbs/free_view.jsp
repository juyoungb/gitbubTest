<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String args = (String)request.getAttribute("args");
FreeList freeList = (FreeList)request.getAttribute("freeList");
int flidx = freeList.getFl_idx();
%>
<h2>자유게시판 글 보기 폼</h2>
<table width="600" cellpadding="5">
<tr>
<th width="10%">작성자</th><td width="15%"><%=freeList.getFl_writer() %></td>
<th width="10%">조회수</th><td width="10%"><%=freeList.getFl_read() %></td>
<th width="10%">작성일</th><td width="*"><%=freeList.getFl_date() %></td>
</tr>
<tr><th>제목</th><td colspan="5"><%=freeList.getFl_title() %></td></tr>
<tr><th>내용</th><td colspan="5"><%=freeList.getFl_content().replace("\r\n", "<br>") %></td></tr>
<tr><td colspan="6" align="center">
<%
// 회원글 : 작성자만 수정, 삭제 버튼을 보여줌  -수정은 수정폼으로 ,삭제는 삭제여부를 물은 뒤  삭제시킴
// 비회원글 : 모두에게 수정, 삭제 버튼을 보여줌 - 클릭시 비밀번호 입력폼으로 이동
boolean isPms = false; //수정, 삭제버튼을 보여줄지 여부를  권한을 저장할 변수
String upLink = "" , delLink = ""; // 수정 삭제용 링크를 저장할 변수
if (freeList.getFl_ismem().equals("n")) { //현재 게시글이 비회원 글이면
	isPms = true; 	
	upLink = "location.href='freeFormPw" + args + "&kind=up&flidx=" + flidx + "' ";
	delLink = "location.href='freeFormPw" + args + "&kind=del&flidx=" + flidx + "' ";
} else { //현재 게시글이 회원 글이면
	if (isLogin && loginInfo.getMi_id().equals(freeList.getFl_writer())) {
	// 현재 사용자가 로그인이 되어있고 현 게시글의 작성자일 경우
		isPms = true;
		upLink = "location.href='freeFormUp" + args + "&flidx=" + flidx + "' ";
		delLink = "realDel();";
	
	}
}

if(isPms){
%>
<script>
function realDel() {
	if(confirm("정말 삭제하시겠습니까?\n삭제된 글은 다시 복구 할 수 없습니다."))	{
		location.href = "freeProcDel?flidx=<%=flidx %>";
	}
}
</script>
		<input type="button" value="수정" onclick="<%=upLink %>">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="삭제" onclick="<%=delLink %>">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<% } %>	
	<input type="button" value="목록" onclick="location.href='freeList<%=args %>'">
</td>
</tr>
</table>
<br><hr width="700" align="left"><br>
<%
loginUrl = "/mvcSite/freeView" + args.replace('&','~') + "~flidx="+flidx;
String msg = " placeholder='로그인 후에 사용하실 수 있습니다.'";
String dis = " disabled='disabled'";	// 로그인을 안했으면 버튼 클릭이 안되도록함
String login = " onclick='goLogin(\"댓글등록\");'";	// 로그인한사람에게는 로그인 폼으로 이동하지 않게함
if (isLogin) {
	msg = "";	dis = "";	login = "";
}
%>
<style>
.txt { width:530px; height:60px; }
.btn { width:120px; height:60px; }
.frmUp { display:none; }
.reWriter { width:700px; display:flex; padding:5px; justify-content:space-between; border:1px solid black;}
.reContent { width:700px; padding:5px; border:1px solid black; border-top:none; margin-bottom:5px;}
</style>
<script >
function goLogin(msg) {
	if (confirm(msg + " 로그인이 필요합니다.\n로그인 화면으로 이동하시겠습니까?")) {
		location.href = "login_form?url=<%=loginUrl%>";
	}
}

function setCharCount(con, fr_idx) {
	var cnt = con.value.length;
	var obj = document.getElementById("charCnt" + fr_idx);	// 작성한 글자수 보여줌
	obj.innerHTML = cnt;
	
	if (cnt > 200) {	// 댓글을 200자이상 입력하지 못하게 함
		alert("댓글은 200자 까지만 입력가능합니다.");
		con.value = con.value.substring(0, 200);
		obj.innerHTML = 200;	// 글자수를 최대 200자 까지만 보여줌
	}
}
function replyIn() { // ajax를 이용한 댓글 등록 함수
	var fr_content = document.frmReply.fr_content.value;
	if(fr_content != "") {
		$.ajax({
			type : "POST", url : "/mvcSite/freeReplyProcIn", data: {"flidx" : "<%=flidx %>", "fr_content" : fr_content},
			success : function(chkRs){
				if(chkRs == 0){
					alert("댓글 등록에 실패했습니다.\n다시 시도해 보세요.")
				} else {
					location.reload();
				}
			}
		});
	}else{
		alert("댓글 내용을 입력하세요.");
		document.frmReply.fr_content.focus();
	}
}

function replyGnB(gnb, fridx) { // ajax를 이용한 댓글 좋아요/싫어요 등록 함수
	$.ajax({
		type : "POST" , url : "/mvcSite/freeReplyProcGnb" , data : {"gnb" : gnb , "fridx" :fridx },
		success : function (chkRs) {
			if (chkRs == 2) {		
				location.reload();
			} else if (chkRs == -1) { // 이미 참여 했을 경우
				alert("이미 참여했습니다.");
			} else {
				alert("처리에 실패햇습니다.\n 다시 시도해 보세요.")
			}
			
		}
	
	});
}

function replyDel(fridx) {
// 댓글번호를 받아 해당 댓글을 삭제하는 함수
	if (confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			type : "POST" , url :"/mvcSite/freeReplyProcDel", data : {"fridx" : fridx, "flidx": <%=flidx %>},
			success : function (chkps) {
				if(chkps == 0) {
					alert("댓글삭제에 실패하였습니다.\n 다시 시도해 보세요."); 
				} else {
					location.reload();
				}
			}
		});
	}
}
</script>
<!-- 댓글 영역 시작 -->
<form name="frmReply">
<table width="600" cellpadding="5">
<tr>
<td width="550" align="right">
	<textarea name="fr_content" class="txt" <%=msg + login %> onkeyup="setCharCount(this, '');"></textarea>
	<br />200자 이내로 입력하세요. (<span id="charCnt">0</span> / 200)	<!-- 0부분을 span태그로 특정지음 -->
</td>
<td width="*" valign="top">
		<input type="button" value="댓글 등록" class="btn" <%=dis %> onclick="replyIn();">
</td>
</tr>
</table>
<br><hr width="700" align="left"><br>
<%
ArrayList<FreeReply> replyList = (ArrayList<FreeReply>)request.getAttribute("replyList");//현재 게시글이 속해있는 list
if (replyList.size() > 0) { // 보여줄 댓글 목록이 있으면
	for (FreeReply fr : replyList){
	      int fridx = fr.getFr_idx();
	      String lnkG = "", lnkB = "";   // 좋아요, 싫어요 링크
	      if (isLogin){   // 현재 로그인한 상태
	         lnkG = "replyGnB('good', " + fridx + ");";
	         lnkB = "replyGnB('bad', " + fridx + ");";
	      } else {   // 로그인하지 않은 상태
	         lnkG = lnkB = "goLogin('댓글의 좋아요/싫어요는');";
	      }
	      boolean isDel = false; //삭제 권한
	      if(isLogin && loginInfo.getMi_id().equals(fr.getMi_id())){
	       //현재 로그인한 회원이 현 댓글을 입력한 회원이면(삭제권한이 있음)
	    	  isDel = true;
	      }
%>
<div class="reWriter">
	<%=fr.getMi_id() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=fr.getFr_date() %>	
	<img src="img/thumbs-up.png" class="hand" onclick="<%=lnkG %>" width="20" style="margin-left:380px;">
	<%=fr.getFr_good() %>
	<img src="img/thumbs-down.png" class="hand" onclick="<%=lnkB %>" width="20" style="margin-left:10px;">
	<%=fr.getFr_bad() %>
	<% if (isDel) { %>
	<img src="img/close.png" width="20" class="hand"  onclick="replyDel(<%=fridx %>);">
	<% } %>
</div>
<div class="reContent"><pre><%=fr.getFr_content() %></pre></div>  <!-- 댓글안에 enter를 사용하려고 할때 <pre>(약관에서 많이 사용)</pre>/<xmp></xmp> 안에서 html태그를 쓰는 보임 -->
<% 
	}
} else { // 보여줄 댓글 목록이 없으면
	out.println("<p>아직 댓글이 없습니다.</p>");
}
%>
</form>
</body>
</html>