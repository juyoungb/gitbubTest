package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/memberFormIn")
public class MemberFormInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public MemberFormInCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginInfo") != null) {
		// 현재 로그인이 되어 있는 상태라면
			response.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('이미 로그인이 되어 있습니다.');");	
			out.println("location.href='/mvcSite/';");	
			out.println("</script>");
			out.close();
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/member/member_form_in.jsp");
		dispatcher.forward(request, response);
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}
