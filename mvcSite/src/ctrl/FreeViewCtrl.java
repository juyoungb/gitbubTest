package ctrl;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oracle.jrockit.jfr.RequestableEvent;

import java.util.*; // 댓글목록을 위한  ArrayList사용하기 위해 import 
import svc.*;
import vo.*;


@WebServlet("/freeView")
public class FreeViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FreeViewCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype"); 
		String keyword = request.getParameter("keyword"); 
		String args = "?cpage=" + cpage;
		
		if(schtype != null && !schtype.equals("") && keyword != null && !keyword.equals("")) {
			URLEncoder.encode(keyword, "UTF-8");
			args += "&schtype=" + schtype + "&keyword=" + keyword; 
		}
		int flidx =  Integer.parseInt(request.getParameter("flidx")); //보여줄 게시글의 글번호
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int result = freeProcSvc.readUpdate(flidx); //사용자가 선택한 게시글의 조회수를 증가시키는 메소드 호출
		
		FreeList freeList = freeProcSvc.getFreeInfo(flidx);
		// 사용자가 선택한 게시글의내용들을 FreeList형 인스턴스로 받아옴
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		ArrayList<FreeReply> replyList = freeReplyProcSvc.getReplyList(flidx);
		// 사용자가 선택한 게시글의 속하는 댓글들의 목록을 가져옴
		
		if (freeList == null) {  //게시글이 없을경우
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('존재하지 않는 게시글 입니다.');");
			out.println("locaiotn.replace('freeList');"); //받아온 쿼리스트링을 다시 찍을 필요가 없음
			out.println("</script>");
			out.close();
			
		} else {	// 게시글이 있을 경우	
			request.setAttribute("args", args);
			request.setAttribute("freeList", freeList); 
			request.setAttribute("replyList", replyList); 
			//view로 이동해서 값을 전달해 주기 위해서 request로 넘겨줌(RequestDispatcher로만 값을 넘겨줄 수 있음)
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_view.jsp");
			dispatcher.forward(request, response);
		}
	}
}
