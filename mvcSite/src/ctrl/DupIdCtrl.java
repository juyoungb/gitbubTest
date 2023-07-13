package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;

@WebServlet("/dupId")
public class DupIdCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    public DupIdCtrl() { super(); }
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		
		DupIdSvc dupIdSvc = new DupIdSvc();
		int result = dupIdSvc.chkDupId(uid);
		
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.println(result);
	}
}
