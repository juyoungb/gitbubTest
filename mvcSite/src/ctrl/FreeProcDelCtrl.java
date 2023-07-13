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
		if (ismem == null ) { // �����Ϸ��±��� ȸ�����̸�
			HttpSession session = request.getSession();
			MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
			where += " and fl_writer = '" + loginInfo.getMi_id() + "' ";
			
		} else {// �����Ϸ��±��� ��ȸ�����̸�
			where += " and fl_pw = '" + request.getParameter("fl_pw") + "' ";
		}
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int result  = freeProcSvc.freeDelete(where);

		if (result == 1) { // ���������� ������ �Ǿ��� ���
			response.sendRedirect("freeList");
		} else { //  ������ �ȉ��� ���
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
