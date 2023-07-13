<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<OrderCart> pdtList = (ArrayList<OrderCart>)request.getAttribute("pdtList");
ArrayList<MemberAddr> addrList =(ArrayList<MemberAddr>)request.getAttribute("addrList");

if (pdtList.size() == 0 || addrList.size() == 0){
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.')");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}
%>
<script>
function chAddr(val) {
	var frm = document.frmOrder;
	var arr = val.split("|");
	var phone = arr[2].split("-");
	frm.ma_name.value = arr[0];		frm.oi_name.value = arr[1];
	frm.p2.value = phone[1];	frm.p3.value = phone[2];
	frm.oi_zip.value = arr[3];		frm.oi_addr1.value = arr[4];
	frm.oi_addr2.value = arr[5];
}
</script>
<h2>주문 폼</h2>
<h3>구매할 상품목록</h3>
<table width="800" cellpadding="5">
<%
int total = 0; // 총 결제 금액을 저장할 변수
String ocidxs = ""; // 장바구니 인덱스 번호들을 누적 저장할 변수
for (OrderCart oc : pdtList) {
	total += oc.getPi_price();
	ocidxs += "," + oc.getOc_idx();
%>
<tr align="center">
<td width="15%">
	<a href="productView?piid=<%=oc.getPi_id() %>">
		<img src="/mvcSite/product/pdt_img/<%=oc.getPi_img1() %>" width="100" height="90">
	</a>
</td>
<td width="25%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="productView?piid=<%=oc.getPi_id() %>"><%=oc.getPi_name() %></a>
</td>
<td width="20%"><%=oc.getPs_size() %>mm</td>
<td width="15%"><%=oc.getOc_cnt() %>개</td>
<td width="*"><%=oc.getPi_price() %>원</td>
</tr>
<%
}
ocidxs = ocidxs.substring(1);
%>
<tr><td colspan="5" align="right">총 결제 금액 :<%=total %></td></tr>
</table>
<hr>
<form name="frmOrder" action="orderProcIn" method="post">
<input type="hidden" name="kind" value="<%=request.getParameter("kind") %>">
<input type="hidden" name="ocidxs" value="<%=ocidxs %>">
<input type="hidden" name="total" value="<%=total %>">
<h3>배송지 정보</h3>
<table width="800" cellpadding="5">
<tr>
<th>배송지 선택</th>
<td colspan="3">
<select style="width:500px; height:30px;" onchange="chAddr(this.value)">
<%
String maname= "", marname= "", maphone= "", mazip = "", maaddr1 = "",maaddr2 = ""; //처음 페이지 로딩시 보여줄 기본주소의 값들을 저장할 변수

for (MemberAddr ma :addrList){
	if (ma.getMa_basic().equals("y")) { // 기본주소이면
		maname = ma.getMa_name();		marname = ma.getMa_rname();	
		maphone = ma.getMa_phone();		mazip = ma.getMa_zip();	
		maaddr1 = ma.getMa_addr1();		maaddr2 = ma.getMa_addr2();	
	}
	String var = "", txt = "";
	var = ma.getMa_name() + "|" + ma.getMa_rname() + "|" + ma.getMa_phone() + "|" + ma.getMa_zip() + "|" + ma.getMa_addr1() + "|" + ma.getMa_addr2();
	txt = " [" + ma.getMa_zip() + "] "  + ma.getMa_addr1() + " " + ma.getMa_addr2();
	out.println("<option value='" + var + "'>" + txt + "</option>;");
}	
%>
</select>
</td>
</tr>
<tr>
<th width="15%">주소명</th>
<td width="35%">
	<input type="text" name="ma_name" value="<%=maname %>" style="border:none;" readonly="readonly" onfocus="this.blur();"><!-- focus와 반대 blur(커서가 잡히지 않도록 하는기능) -->
</td>
<th width="15%">수취인 명</th>
<td width="35%">
	<input type="text" name="oi_name" value="<%=marname %>" >
</td>
</tr>
<tr>
<th>전화번호</th>
<td>
<%
String[] arr = maphone.split("-");
%>
	010- <input type="text" name="p2" value="<%=arr[1] %>" size="4" maxlength="4"> -
		<input type="text" name="p3" value="<%=arr[2] %>" size="4" maxlength="4">
</td>
<th>우편번호</th>
<td>
	<input type="text" name="oi_zip" value="<%=mazip %>" size="5" maxlength="5">
</td>
</tr>
<tr>
<th>주소</th>
<td colspan="3">
	<input type="text" name="oi_addr1" value="<%=maaddr1 %>" size="40">
	<input type="text" name="oi_addr2" value="<%=maaddr2 %>" size="40">
</td>
</tr>
</table>
<hr>
<h3>결제 정보</h3>
<p style="width:800px">
	<input type="radio" name="oi_payment" value="a" id="payA" checked="checked">
	<label for="payA">카드결제</label>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="oi_payment" value="b" id="payB">
	<label for="payB">휴대폰 결제</label>&nbsp;&nbsp;&nbsp;&nbsp;	
	<input type="radio" name="oi_payment" value="c" id="payC">
	<label for="payC">계좌 이체</label>
</p>
<hr>
<p style="width:800px; text-align:center">
<input type="submit" value="결제하기">
</p>
</form>
<br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>