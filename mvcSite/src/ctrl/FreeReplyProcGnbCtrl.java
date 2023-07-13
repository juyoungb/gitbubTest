package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/freeReplyProcGnb")
public class FreeReplyProcGnbCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public FreeReplyProcGnbCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gnb = request.getParameter("gnb");
		int fridx = Integer.parseInt(request.getParameter("fridx"));
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(loginInfo == null) {
			out.println("<script>");
			out.println("alert('로그인 후 사용하실수 있습니다.')");
			out.println("</script>");
			out.close();
		}
		
		FreeReplyGnb freeReplyGnb = new FreeReplyGnb();
		freeReplyGnb.setFr_idx(fridx);
		freeReplyGnb.setFrg_gnb(gnb);
		freeReplyGnb.setMi_id(loginInfo.getMi_id());
		// 좋아요/싫어요 처리를 위한 데이터들을FreeReplyGnb형 인스턴스에 저장
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		int result = freeReplyProcSvc.replyGnb(freeReplyGnb);
		out.println(result);
		
	}

}
