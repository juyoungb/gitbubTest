package config;

import java.io.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class LoginCtrlMvc {
	@GetMapping("/loginMvc")
	public String loginMvc() {
		return "loginFormMvc";
	}

	@PostMapping("/loginMvc")
	public String loginMvc(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		String pwd = request.getParameter("pwd").trim();
		
		LoginSvcMvc loginSvcMvc = new LoginSvcMvc();		
		MemberInfo loginInfo = loginSvcMvc.getLoginInfo(uid, pwd);
		
		if (loginInfo == null) { // �α��� ����
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���̵�� ��ȣ�� Ȯ���ϼ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
		}
		return "redirect:/";
		//dispatcher ����� �ƴ� �����̷�Ʈ ������� �̵�
	}
}
