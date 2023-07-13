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
		
		String where = " and fl_idx = " + flidx; // ȸ��, ��ȸ�� ���� ����
		if (ismem == null) {// �����Ϸ��� �Խñ��� ȸ������ ��� 
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
		} else { // �����Ϸ��� �Խñ��� ��ȸ������ ��� 
			where += " and fl_pw = '" + request.getParameter("fl_pw") + "' ";
		}

		FreeProcSvc freeProcSvc = new FreeProcSvc();
		FreeList freeInfo = freeProcSvc.getFreeInfoUp(where);
		// �����Ϸ��� �Խñ� �������� FreeList�� �ν��Ͻ��� �޾ƿ�
		
		if (freeInfo != null) { // �����Ϸ��� �Խñ��� ������
			request.setAttribute("freeInfo", freeInfo);
			request.setAttribute("args", args);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_form_up.jsp");
			dispatcher.forward(request, response);
			
		} else {	// �����Ϸ��� �Խñ��� ������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			if (ismem == null)	{	// �����Ϸ��� �Խñ��� ȸ������ ��� 
				out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			} else {	// �����Ϸ��� �Խñ��� ��ȸ������ ��� 
				out.println("alert('��й�ȣ�� Ʋ�Ƚ��ϴ�.');");
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
