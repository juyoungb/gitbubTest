package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/freeProcUp")
public class FreeProcUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public FreeProcUpCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		String fl_title = request.getParameter("fl_title").trim().replace("<", "&lt;");
		String fl_content = request.getParameter("fl_content").trim().replace("<", "&lt;");
		
		FreeList freeList = new FreeList(); // ������ �Խñ� ������ ������ �ν��Ͻ�		
		freeList.setFl_idx(flidx);
		freeList.setFl_title(fl_title);
		freeList.setFl_content(fl_content);

		FreeProcSvc freeProcSvc = new FreeProcSvc();
		//������ ���ڵ� ��
		int result = freeProcSvc.freeUpdate(freeList);
		String args = request.getParameter("args");
		if (result == 1) { // ���������� ������ �Ǿ��� ���
			response.sendRedirect("freeView" + args + "&flidx=" + flidx);
		} else { // ������ �ȉ��� ���
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();"); 
			out.println("</script>");
			out.close();
		}
	}

}
