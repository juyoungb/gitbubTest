package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
 

@WebServlet("/login_form")
public class LoginForm extends HttpServlet {
// �α��� ������ �̵���Ű�� ����
	private static final long serialVersionUID = 1L;
       

    public LoginForm() { super(); }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		// JSP�� �ƴϹǷ� session�� ����Ϸ��� ���� HttpSession Ŭ������ �ν��Ͻ��� �����ؾ� ��
		if (session.getAttribute("loginInfo") != null) {
		// ���� �α����� �Ǿ� �ִ� ���¶��
			
			response.setContentType("text/html; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			// JSP�� �ƴϹǷ� out�� ����Ϸ��� ���� PrintWriter Ŭ������ �ν��Ͻ��� �����ؾ� ��
			out.println("<script>");
			out.println("alert('�̹� �α����� �Ǿ� �ֽ��ϴ�.');");	
			out.println("location.href='/mvcSite/';");	
			out.println("</script>");
			out.close();
			
		}		
		// url�� �ٲ��� �ʰ� ������ ��� RequestDispatcher 
		RequestDispatcher dispatcher = request.getRequestDispatcher("login_form.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
