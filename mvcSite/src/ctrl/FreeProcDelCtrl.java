package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.FreeList;
import vo.MemberInfo; 

@WebServlet("/freeProcDel")
public class FreeProcDelCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FreeProcDelCtrl() { super();}
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		String ismem = request.getParameter("ismem");
		String where = " where fl_idx = " + flidx;
		if (ismem == null ) { // 삭제하려는글이 회원글이면
			HttpSession session = request.getSession();
			MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
			where += " and fl_writer = '" + loginInfo.getMi_id() + "' ";
			
		} else {// 삭제하려는글이 비회원글이면
			where += " and fl_pw = '" + request.getParameter("fl_pw") + "' ";
		}
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int result  = freeProcSvc.freeDelete(where);

		if (result == 1) { // 정상적으로 삭제가 되었을 경우
			response.sendRedirect("freeList");
		} else { //  삭제가 안됬을 경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");	
			if (ismem == null)	{	// 삭제하려는 게시글이 회원글일 경우 
				out.println("alert('잘못된 경로로 들어오셨습니다.');");
			} else {	// 삭제하려는 게시글이 비회원글일 경우 
				out.println("alert('비밀번호가 틀렸습니다.');");
			}
			out.println("history.back();"); 
			out.println("</script>");
			out.close();
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
