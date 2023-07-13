package ctrl;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;

@WebServlet("/freeFormPw")
public class FreeFormPwCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public FreeFormPwCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype"); 
		String keyword = request.getParameter("keyword"); 
		String args = "?cpage=" + cpage;
		
		if (schtype != null && !schtype.equals("") && keyword != null && !keyword.equals("")) {
			URLEncoder.encode(keyword, "UTF-8");
			args += "&schtype=" + schtype + "&keyword=" + keyword; 
		}
		
		int flidx = Integer.parseInt(request.getParameter("flidx")); 
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		String ismem = freeProcSvc.getIsMem(flidx);
		
		if (ismem != null && ismem.equals("n")) { // 해당게시글이 비회원글일 경우
			request.setAttribute("args", args);
		
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_form_pw.jsp");
			dispatcher.forward(request, response);
			
		} else { // 회원글이거나 결과가 없을(존재하지 않는 게시글) 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();"); 
			out.println("</script>");
			out.close();
		}
	}
}
