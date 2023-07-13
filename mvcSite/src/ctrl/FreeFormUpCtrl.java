package ctrl;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/freeFormUp")
public class FreeFormUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FreeFormUpCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String args = request.getParameter("args");
		String ismem = request.getParameter("ismem");
		int flidx = Integer.parseInt(request.getParameter("flidx"));
		
		String where = " and fl_idx = " + flidx; // 회원, 비회원 공통 조건
		if (ismem == null) {// 수정하려는 게시글이 회원글일 경우 
			HttpSession session = request.getSession();
			MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
			where += " and fl_writer = '" + loginInfo.getMi_id() + "' ";
			
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String schtype = request.getParameter("schtype"); 
			String keyword = request.getParameter("keyword"); 
			args = "?cpage=" + cpage;
			
			if (schtype != null && !schtype.equals("") && keyword != null && !keyword.equals("")) {
				URLEncoder.encode(keyword, "UTF-8");
				args += "&schtype=" + schtype + "&keyword=" + keyword; 
			}
		} else { // 수정하려는 게시글이 비회원글일 경우 
			where += " and fl_pw = '" + request.getParameter("fl_pw") + "' ";
		}

		FreeProcSvc freeProcSvc = new FreeProcSvc();
		FreeList freeInfo = freeProcSvc.getFreeInfoUp(where);
		// 수정하려는 게시글 정보들을 FreeList형 인스턴스로 받아옴
		
		if (freeInfo != null) { // 수정하려는 게시글이 있으면
			request.setAttribute("freeInfo", freeInfo);
			request.setAttribute("args", args);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_form_up.jsp");
			dispatcher.forward(request, response);
			
		} else {	// 수정하려는 게시글이 없으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			if (ismem == null)	{	// 수정하려는 게시글이 회원글일 경우 
				out.println("alert('잘못된 경로로 들어오셨습니다.');");
			} else {	// 수정하려는 게시글이 비회원글일 경우 
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
