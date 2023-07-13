package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;
import java.util.*;

@WebServlet("/cartProcDel")
public class CartProcDelCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CartProcDelCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String ocidx = request.getParameter("ocidx");//삭제할 상품의 장바구니 인덱스번호(들)
		
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo =(MemberInfo)session.getAttribute("loginInfo");
		if (loginInfo == null) {
			out.println("<script>");
			out.println("alert('다시 로그인하세요.');");	
			out.println("location.href='login_form?url=cartView';");	
			out.println("</script>");
			out.close();
		}
		
		String where = " where mi_id = '" + loginInfo.getMi_id() + "' ";
		if (ocidx.indexOf(',') >= 0) { //여러개의 상품을 삭제할 경우
		// and (oc_idx=? or...or oc_idx=?)
			String[] arr = ocidx.split(",");
			for (int i = 0; i < arr.length ; i++) {
				if (i == 0) {
				where += " and (oc_idx = " + arr[i];
				} else {
					where +=" or oc_idx = " + arr[i];
				}
			}
			where += ")";
		} else {// 하나의 상품을 삭제할 경우
			where += " and oc_idx = " + ocidx;
		}
		System.out.println(where);
		CartProcSvc cartProcSvc = new CartProcSvc();
		int result = cartProcSvc.cartDelete(where);
		
		out.println(result);
	}
}
