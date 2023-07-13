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
		String ocidx = request.getParameter("ocidx");//������ ��ǰ�� ��ٱ��� �ε�����ȣ(��)
		
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo =(MemberInfo)session.getAttribute("loginInfo");
		if (loginInfo == null) {
			out.println("<script>");
			out.println("alert('�ٽ� �α����ϼ���.');");	
			out.println("location.href='login_form?url=cartView';");	
			out.println("</script>");
			out.close();
		}
		
		String where = " where mi_id = '" + loginInfo.getMi_id() + "' ";
		if (ocidx.indexOf(',') >= 0) { //�������� ��ǰ�� ������ ���
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
		} else {// �ϳ��� ��ǰ�� ������ ���
			where += " and oc_idx = " + ocidx;
		}
		System.out.println(where);
		CartProcSvc cartProcSvc = new CartProcSvc();
		int result = cartProcSvc.cartDelete(where);
		
		out.println(result);
	}
}
