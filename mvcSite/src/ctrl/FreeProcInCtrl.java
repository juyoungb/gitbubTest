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
		
		FreeList freeList = new FreeList(); //�Է��� �Խñ� ������ ������ �ν��Ͻ�
		freeList.setFl_title(fl_title);
		freeList.setFl_content(fl_content);
		freeList.setFl_ip(request.getRemoteAddr());
		
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");
		// ���� �α��� �� ȸ���� ������ ����ִ� ������ �Ӽ��� �����Ͽ� MemberInfo�� �ν��Ͻ�loginInfo�� ����
		// loginInfo�� null�̸� ��ȸ�����̰�, null�� �ƴϸ� ȸ������ �� 
	
		if (loginInfo == null) { // ��ȸ�����̸�
			freeList.setFl_writer(request.getParameter("fl_writer").trim().replace("<", "&lt;"));
			freeList.setFl_pw(request.getParameter("fl_pw").trim().replace("<", "&lt;"));
			freeList.setFl_ismem("n");		
		} else { // ȸ�����̸�
			freeList.setFl_writer(loginInfo.getMi_id());
			freeList.setFl_pw("");
			freeList.setFl_ismem("y");
		}
		// ����Ҷ� �ʿ���(t_free_list ���̺� insert��) �����͵��� ��� FreeList�� �ν��Ͻ��� ����
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		int flidx = freeProcSvc.freeInsert(freeList);
		// insert ������ ���� �ϹǷ� insert�� ���ڵ� ������ �޾ƿ��°��� �Ϲ����̳�, ó�� �� �ش� �� ����ȭ������ �̵��ؾ� �ϱ� ������ �ش� ���� �۹�ȣ�� �޾ƿ�
		
		if(flidx > 0) { //���������� ���� ��ϵǾ�����
			response.sendRedirect("freeView?cpage=1&flidx=" + flidx);	// ctrl�� �̵�
			
		} else { //�� ��Ͻ� ������ �߻�������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�� ��Ͽ� ���� �Ͽ����ϴ�. �ٽ� �õ����ּ���.');");
			out.println("history.back();"); //�޾ƿ� ������Ʈ���� �ٽ� ���� �ʿ䰡 ����
			out.println("</script>");
			out.close();
		}
	}
}
