package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

@WebServlet("/orderForm")
public class OrderFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public OrderFormCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String kind= request.getParameter("kind");	// 장바구니를 통한구매 (c)인지, 바로구매(d)인지 여부를 판단할 데이터
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if (loginInfo == null) { 
			out.println("<script>");
			out.println("alert('다시 로그인 하세요.')");
			out.println("location.href='login_form';");
			out.println("</script>");
			out.close();
		}				
		String miid = loginInfo.getMi_id();
		
		String select = "select a.pi_id, a.pi_name, a.pi_img1, b.ps_size, if(a.pi_dc > 0, round((1 - a.pi_dc) * a.pi_price), a.pi_price) price, ";
		String from = " from t_product_info a, t_product_stock b ";
		String where = " where a.pi_id = b.pi_id and a.pi_isview = 'y' and b.ps_isview = 'y' ";
		
		if (kind.equals("c")) { // 장바구니를 통한 구매(c)일 경우
			String[] arr = request.getParameterValues("chk");
			select += " c.oc_cnt cnt, c.oc_idx ";
			from += ", t_order_cart c ";
			where += " and a.pi_id = c.pi_id and b.ps_idx = c.ps_idx and c.mi_id ='" + miid + "' and(";
			for (int i = 1; i < arr.length; i++) {
				if (i == 1) where +="c.oc_idx=" + arr[i];
				else		where +=" or c.oc_idx=" + arr[i];
			}
			where +=") order by a.pi_id, c.ps_idx";
			
		} else { // 바로구매를 통한 구매(d)일 경우
			String piid = request.getParameter("piid");
			String tmp = request.getParameter("size");
			int size = Integer.parseInt(tmp.substring(0, tmp.indexOf(':')));
			int cnt = Integer.parseInt(request.getParameter("cnt"));
			select += cnt + " cnt ";
			where += " and a.pi_id ='" + piid + "' and b.ps_idx =" + size;
		}
		OrderProcSvc orderProcSvc = new OrderProcSvc();
		ArrayList<OrderCart> pdtList = orderProcSvc.getBuyList(kind, select + from + where);
		ArrayList<MemberAddr> addrList = orderProcSvc.getAddrList(miid);// 주소를 받아오는 List
		
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("addrList", addrList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/order/order_form.jsp");
		dispatcher.forward(request, response);
	}
}
