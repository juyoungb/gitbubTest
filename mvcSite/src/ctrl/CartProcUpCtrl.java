package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/cartProcUp")
public class CartProcUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;       
    public CartProcUpCtrl() { super(); }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int ocidx = Integer.parseInt(request.getParameter("ocidx"));
		// 변경시 where절에서 조건으로 사용될 장바구니 테이블의 pk
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		int opt = Integer.parseInt(request.getParameter("opt"));
		//opt : 변경할 옵션 인덱스 번호, cnt: 변경할 수량
		
		response.setContentType("text/html charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if (loginInfo == null) { // 로그인이 풀렸으면
			out.println("<script>");
			out.println("alert('다시 로그인 하세요.')");
			out.println("location.href='login_form?url=/mvcSite/cartView';");
			out.println("</script>");
			out.close();
		}		

		String where = "where mi_id='"+loginInfo.getMi_id()+"' and oc_idx=" + ocidx;
		OrderCart oc =new OrderCart();
		oc.setOc_idx(ocidx);					oc.setMi_id(loginInfo.getMi_id());
		oc.setPs_idx(opt);						oc.setOc_cnt(cnt);
		
		
		CartProcSvc cartProcSvc = new CartProcSvc();
		int result = cartProcSvc.CartUpdate(oc);
		
		out.println(result);
	}

}
