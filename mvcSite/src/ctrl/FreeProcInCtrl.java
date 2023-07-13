package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/freeProcIn")
public class FreeProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public FreeProcInCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String fl_title = request.getParameter("fl_title").trim().replace("<", "&lt;");
		String fl_content = request.getParameter("fl_content").trim().replace("<", "&lt;");
		
		FreeList freeList = new FreeList(); //입력할 게시글 정보를 저장할 인스턴스
		freeList.setFl_title(fl_title);
		freeList.setFl_content(fl_content);
		freeList.setFl_ip(request.getRemoteAddr());
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		// 현재 로그인 한 회원의 정보가 들어있는 세션의 속성을 추출하여 MemberInfo형 인스턴스loginInfo에 저장
		// loginInfo가 null이면 비회원글이고, null이 아니면 회원글이 됨 
	
		if (loginInfo == null) { // 비회원글이면
			freeList.setFl_writer(request.getParameter("fl_writer").trim().replace("<", "&lt;"));
			freeList.setFl_pw(request.getParameter("fl_pw").trim().replace("<", "&lt;"));
			freeList.setFl_ismem("n");		
		} else { // 회원글이면
			freeList.setFl_writer(loginInfo.getMi_id());
			freeList.setFl_pw("");
			freeList.setFl_ismem("y");
		}
		// 등록할때 필요한(t_free_list 테이블에 insert함) 데이터들을 모두 FreeList형 인스턴스에 저장
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int flidx = freeProcSvc.freeInsert(freeList);
		// insert 쿼리를 실행 하므로 insert된 레코드 개수를 받아오는것이 일반적이나, 처리 후 해당 글 보기화면으로 이동해야 하기 때문에 해당 글의 글번호를 받아옴
		
		if(flidx > 0) { //정상적으로 글이 등록되었으면
			response.sendRedirect("freeView?cpage=1&flidx=" + flidx);	// ctrl로 이동
			
		} else { //글 등록시 문제가 발생했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 등록에 실패 하였습니다. 다시 시도해주세요.');");
			out.println("history.back();"); //받아온 쿼리스트링을 다시 찍을 필요가 없음
			out.println("</script>");
			out.close();
		}
	}
}
