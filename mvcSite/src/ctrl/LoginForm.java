package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
 

@WebServlet("/login_form")
public class LoginForm extends HttpServlet {
// 로그인 폼으로 이동시키는 서블릿
	private static final long serialVersionUID = 1L;
       

    public LoginForm() { super(); }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		// JSP가 아니므로 session을 사용하려면 직접 HttpSession 클래스의 인스턴스를 생성해야 함
		if (session.getAttribute("loginInfo") != null) {
		// 현재 로그인이 되어 있는 상태라면
			
			response.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			// JSP가 아니므로 out을 사용하려면 직접 PrintWriter 클래스의 인스턴스를 생성해야 함
			out.println("<script>");
			out.println("alert('이미 로그인이 되어 있습니다.');");	
			out.println("location.href='/mvcSite/';");	
			out.println("</script>");
			out.close();
			
		}		
		// url은 바뀌지 않고 보내는 방법 RequestDispatcher 
		RequestDispatcher dispatcher = request.getRequestDispatcher("login_form.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
