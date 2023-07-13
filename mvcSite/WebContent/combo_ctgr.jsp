<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String schPcb = request.getParameter("pcb");
ArrayList<ProductCtgrBig> bigList = (ArrayList<ProductCtgrBig>)request.getAttribute("bigList");
ArrayList<ProductCtgrSmall> smallList = (ArrayList<ProductCtgrSmall>)request.getAttribute("smallList");
%>
<script>
<%
for(ProductCtgrBig pcb : bigList) {
 String arr = "arr" + pcb.getPcb_id();
 %>
 var <%=arr %> = new Array();
 <%=arr %>[0] = new Option("", "소분류 선택");
 <%
 for (int i = 0, j = 1; i< smallList.size(); i++, j++) {
	 ProductCtgrSmall pcs = smallList.get(i);
	 if(pcs.getPcb_id().equals(pcb.getPcb_id())) {
%>
<%=arr %>[<%=j %>] = new Option("<%=pcs.getPcs_id() %>", "<%=pcs.getPcs_name() %>");
<%		 
	 }else {
		 j = 0;
	 }
   }
 }
 %>
 
function setCategory(x, target) {
	for (var i = target.options.length - 1 ; i > 0 ; i--){
		target.options[i] = null;
	}
	
	if (x != ""){ 
		var arr = eval("arr" + x);		
		
		for (var i = 0 ; i < arr.length ; i++){
			target.options[i] = new Option(arr[i].value, arr[i].text);					
		}
	
		target.options[0].selected = true;
	}
}
</script>
<form name="frm" >
	<select name="pcb" onchange="setCategory(this.value, this.form.pcs);">
		<option value="">대분류 선택</option>
<%
for(ProductCtgrBig pcb : bigList) { %>
	<option value="<%=pcb.getPcb_id() %>" <% if (schPcb.equals(pcb.getPcb_id())) { %>selected="selected"<% } %>><%=pcb.getPcb_name() %></option>
<%} %>
	</select>
	<select name="pcs">
		<option value="">소분류 선택</option>
	</select>
</form>
<script>
setCategory(document.frm.pcb.value, document.frm.pcs);//admin 상품등록 시 사용 
</script>
</body>
</html>