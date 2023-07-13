package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

import svc.FreeReplyProcSvc;

@WebServlet("/freeReplyProcDel")
public class FreeReplyProcDelCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public FreeReplyProcDelCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int flidx = Integer.parseInt(request.getParameter("flidx")); //�Խñ۹�ȣ
		int fridx = Integer.parseInt(request.getParameter("fridx")); //��� ��ȣ
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(loginInfo == null) {
			out.println("<script>");
			out.println("alert('�α��� �� ����ϽǼ� �ֽ��ϴ�.')");
			out.println("</script>");
			out.close();
		}

		FreeReply freeReply = new FreeReply();
		freeReply.setFl_idx(flidx);
		freeReply.setFr_idx(fridx);
		freeReply.setMi_id(loginInfo.getMi_id());
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		int result = freeReplyProcSvc.replyDelete(freeReply);		
		out.println(result);
	}

}
