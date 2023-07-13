package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/memberProcIn")
public class MemberProcInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberProcInCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 받아온 회원정보들을 저장할 인스턴스 생성
		MemberInfo memberInfo = new MemberInfo();
		MemberAddr memberAddr = new MemberAddr();
		
		memberInfo.setMi_id(request.getParameter("mi_id").trim().toLowerCase());
		memberInfo.setMi_pw(request.getParameter("mi_pw").trim());
		memberInfo.setMi_name(request.getParameter("mi_name").trim());
		memberInfo.setMi_gender(request.getParameter("mi_gender"));
		memberInfo.setMi_birth(request.getParameter("by") +"-" + request.getParameter("bm")  +"-" + request.getParameter("bd"));
		memberInfo.setMi_phone("010-" +request.getParameter("p2") +"-" + request.getParameter("p3"));
		memberInfo.setMi_email(request.getParameter("e1").trim() + "@" +request.getParameter("e3").trim());
		memberInfo.setMi_isad(request.getParameter("mi_isad"));
		memberInfo.setMi_point(1000);
		
		memberAddr.setMi_id(request.getParameter("mi_id").trim().toLowerCase());
		memberAddr.setMa_name("기본 주소");
		memberAddr.setMa_rname(request.getParameter("mi_name").trim());
		memberAddr.setMa_phone("010-" +request.getParameter("p2") +"-" + request.getParameter("p3"));
		memberAddr.setMa_zip(request.getParameter("ma_zip"));
		memberAddr.setMa_addr1(request.getParameter("ma_addr1"));
		memberAddr.setMa_addr2(request.getParameter("ma_addr2"));
		
		MemberProcSvc memberProcSvc = new MemberProcSvc(); 
		int result = memberProcSvc.memberProcIn(memberInfo, memberAddr);
		if (result == 3) { //정상적으로 회원가입이 이루어 졌으면
			response.sendRedirect("login_form");
		} else { //회원 가입실패
			response.setContentType("text/html; charset=utf8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 가입에 실패 했습니다.\\n 다시 시도하세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	}	
}
