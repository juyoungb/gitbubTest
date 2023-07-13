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
		// ����� where������ �������� ���� ��ٱ��� ���̺��� pk
		int cnt = Integer.parseInt(request.getParameter("cnt"));
		int opt = Integer.parseInt(request.getParameter("opt"));
		//opt : ������ �ɼ� �ε��� ��ȣ, cnt: ������ ����
		
		response.setContentType("text/html charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		if (loginInfo == null) { // �α����� Ǯ������
			out.println("<script>");
			out.println("alert('�ٽ� �α��� �ϼ���.')");
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
