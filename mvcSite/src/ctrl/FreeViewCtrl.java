package ctrl;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oracle.jrockit.jfr.RequestableEvent;

import java.util.*; // ��۸���� ����  ArrayList����ϱ� ���� import 
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
		int flidx =  Integer.parseInt(request.getParameter("flidx")); //������ �Խñ��� �۹�ȣ
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int result = freeProcSvc.readUpdate(flidx); //����ڰ� ������ �Խñ��� ��ȸ���� ������Ű�� �޼ҵ� ȣ��
		
		FreeList freeList = freeProcSvc.getFreeInfo(flidx);
		// ����ڰ� ������ �Խñ��ǳ������ FreeList�� �ν��Ͻ��� �޾ƿ�
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		ArrayList<FreeReply> replyList = freeReplyProcSvc.getReplyList(flidx);
		// ����ڰ� ������ �Խñ��� ���ϴ� ��۵��� ����� ������
		
		if (freeList == null) {  //�Խñ��� �������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�������� �ʴ� �Խñ� �Դϴ�.');");
			out.println("locaiotn.replace('freeList');"); //�޾ƿ� ������Ʈ���� �ٽ� ���� �ʿ䰡 ����
			out.println("</script>");
			out.close();
			
		} else {	// �Խñ��� ���� ���	
			request.setAttribute("args", args);
			request.setAttribute("freeList", freeList); 
			request.setAttribute("replyList", replyList); 
			//view�� �̵��ؼ� ���� ������ �ֱ� ���ؼ� request�� �Ѱ���(RequestDispatcher�θ� ���� �Ѱ��� �� ����)
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_view.jsp");
			dispatcher.forward(request, response);
		}
	}
}
