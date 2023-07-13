<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ProductInfo productInfo = (ProductInfo)request.getAttribute("productInfo"); //화면에서 보여줄 상품 정보들을 저장한 productInfo형 인스턴스를 받아옴
ArrayList<ProductStock> stockList = productInfo.getStockList(); // productInfo에 들어있는 옵션별 재고량 목록을 받아옴 
ArrayList<ReviewList> reviewList = (ArrayList<ReviewList>)request.getAttribute("reviewList");//해당상품의 구매 후기 목록을 받아옴

long realPrice = productInfo.getPi_price();	// 수량 변경에 따른 가격 연산을 위한 변수
String price = productInfo.getPi_price() + "원"; // 가격 출력을 위한 변수
if (productInfo.getPi_dc() > 0) { //할인율이 있으면
	realPrice = Math.round(realPrice * (1- productInfo.getPi_dc()));
	price = "<del>" + productInfo.getPi_price() + "</del>" + "&nbsp;&nbsp;&nbsp;" + realPrice + "원";
}

%>
<style>
#info td { font-size:1.2em; }
#cnt { width:20px; text-ailgn:right;}
.smt { width:150px; height:30px;}
#review { display:none; }
</style>
<script>
function swapIng(img){
	var big = document.getElementById("bigImg"); 
	//큰이미지를 보여주는 img태그를 big이라는 이름의 객체로 받아줌
	big.src ="/mvcSite/product/pdt_img/"+img ;
}
//function setCnt(cnt) {
//	var result = document.getElementById('cnt');
	
//	var num = result.value;
	
//	 if(cnt == '+') {
//		 if(num != 50)  num = parseInt(num) + 1;
//		 else 			alert("최대 숫자 입니다.");
		
//	 } else if (cnt == '-')  {
//		 if(num != 1)   num = parseInt(num) - 1;
//		 else 			alert("최소 숫자 입니다.");
//		 }
//	 result.value = num;
  
//}
function setCnt(op) {
	var price = <%=realPrice %>;
	var frm = document.frm;
	var size = frm.size.value; 	//10:150 ->  ps_idx:ps_stock
	
	if (size != "") {	
		var cnt = parseInt(frm.cnt.value);
		var max = size.substring(size.indexOf(":") + 1);
		if(max >=10) max =10;  // 재고가 10이상일 경우10을 최대값으로 지정
		
		if (op == "+" && cnt < max) frm.cnt.value = cnt + 1;	
		else if (op == "-" && cnt > 1)  frm.cnt.value = cnt - 1;
		
		var total =  document.getElementById('total');
		total.innerHTML = price * frm.cnt.value;
	
	} else {
		alert("옵션을 먼저 선택하세요.");
	}
}
function buy(kind) {
	<% if (isLogin) {%>
		var frm = document.frm;
		var size = frm.size.value;
		var cnt = frm.cnt.value;
		
		 if(size == ""){
			 alert("옵션(사이즈)을 선택하세요.");  
			 return;
		 }
		 if (kind == "c") {//장바구니 담기일 경우
			$.ajax({
				type :"POST" , url:"/mvcSite/cartProcIn",
				data:{"piid" : "<%=productInfo.getPi_id() %>", "psidx" : size, "cnt": cnt},
				success :function(chkRs){//장바구니 담기에 실패했을 경우
					if(chkRs == 0){
						alert("장바구니 담기에 실패하였습니다.\n다시 시도해 주세요.");
						return;
				} else { //장바구니 담기에 성공했을 경우
					if(confirm("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?")){
						location.href="cartView"
					}
				}
			}
		  }); 
		 } else {// 바로 구매하기일 경우
			frm.action="orderForm";
			frm.submit(); 
		 }
		 
	<% } else {%> 
		location.href = "login_form?url=/mvcSite/productView?piid=<%=productInfo.getPi_id() %>";
	<%}%>
	}

function showTap(chk) {
	var obj1 = document.getElementById("desc");
	obj1.style.display="none"
	var obj2 = document.getElementById("review");
	obj2.style.display="none"
	
	var obj3 = document.getElementById(chk);
	obj3.style.display="block"

}
</script>
<h2>상품 상세화면</h2>
<table width="800" cellpadding="5">
<tr align="center">
<td width="35%">
<!-- 이미지 관련 영역 -->
	<table width="100%" cellpadding="5">
	<tr><td colspan="3" align="center">
		<img src="/mvcSite/product/pdt_img/<%=productInfo.getPi_img1() %>" width="260" height="230" id="bigImg">
		</td></tr>
		<tr align="center">
		<td width="33.33%">
			<img src="/mvcSite/product/pdt_img/<%=productInfo.getPi_img1() %>" width="80" height="80" onclick="swapIng('<%=productInfo.getPi_img1() %>')" class="hand">
		</td>
		<td width="33.33%">
<% if(productInfo.getPi_img2() !=null && !productInfo.getPi_img2().equals("")) {%>		
			<img src="/mvcSite/product/pdt_img/<%=productInfo.getPi_img2() %>" width="80" height="80" onclick="swapIng('<%=productInfo.getPi_img2() %>')" class="hand">
<%} %>		
		</td>
		<td width="33.33%">
<% if(productInfo.getPi_img3() !=null && !productInfo.getPi_img3().equals("")) {%>		
			<img src="/mvcSite/product/pdt_img/<%=productInfo.getPi_img3() %>" width="80" height="80" onclick="swapIng('<%=productInfo.getPi_img3() %>')" class="hand">
<%} %>				
		</td>
		</tr>	
		</table>
<td width="*" valign="top">
<!-- 상품 정보 관련 영역 -->
	<form name="frm" method="post">
	<input type="hidden" name="kind" value="d">
	<input type="hidden" name="piid" value="<%=productInfo.getPi_id() %>">
	<table width="100%" cellpadding="5" border="1" id="info">	
		<tr><td colspan="2">&nbsp;&nbsp;&nbsp;
			<a href="productList?pcb=<%=productInfo.getPcs_id().substring(0, 2) %>"><%=productInfo.getPcb_name() %></a> ->
			<a href="productList?pcb=<%=productInfo.getPcs_id().substring(0, 2) %>&pcs=<%=productInfo.getPcs_id() %>"><%=productInfo.getPcs_name() %></a>
	</td></tr>
	<tr>
	<td width="20%" align="right">상품명</td>
	<td width="*"><%=productInfo.getPi_name() %></td>
	</tr>
	<tr><td align="right">브랜드</td><td><%=productInfo.getPb_name() %></td></tr>
	<tr><td align="right">제조사</td><td><%=productInfo.getPi_com() %></td></tr>
	<tr><td align="right">가격</td><td><%=price %></td></tr>
	<tr>
	<td align="right">옵션</td>
	<td>
		<select name="size">
			<option value="">사이즈 선택</option>	
<%
for (ProductStock ps : stockList) { 
	String opt = ps.getPs_size() + "mm (재고 : " + ps.getPs_stock() + "개)"; 
	String disabled = "";
	if (ps.getPs_stock() <= 0) {
		disabled = " disabled=\"disabled\"";
		opt = ps.getPs_size()  + "mm(재고없음 : 품절)";
	}
	out.println("<option value='" + ps.getPs_idx() + ":" + ps.getPs_stock() + "'" + disabled + ">" + opt + "</option>");
}
%>		
		</select>
	</td>
	</tr>
	<tr>
	<td align="right">수량</td>
	<td>
	<input type="button" value="-" onclick="setCnt(this.value);">
	<input type="text" name="cnt" id="cnt"  value="1" readonly="readonly" >
	<input type="button" value="+" onclick="setCnt(this.value);">
	</td>
	</tr>
	<tr><td colspan="2" align="right">
		구매 가격 : <span id="total"><%=realPrice %></span>원
	</td></tr>
	<tr><td colspan="2" align="center">
	<input type="button" value="장바구니 담기" class="smt" onclick="buy('c');">
	<input type="button" value="바로 구매하기" class="smt" onclick="buy('d');">
	</table>
	</form>
</td>
</tr>
<tr>
</table>
<hr>
<input type="button" value="상품설명" onclick="showTap('desc');">
<input type="button" value="구매후기" onclick="showTap('review');"><br>
<div id="desc" >
	<img src="/mvcSite/product/pdt_img/"<%=productInfo.getPi_date() %>" width="700" height="1000">
</div>
<div id="review" >
	구매후기
</div>
</body>
</html>