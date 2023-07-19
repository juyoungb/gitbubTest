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
		
		if (loginInfo == null) { // 로그인 실패
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디와 암호를 확인하세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
		}
		return "redirect:/";
		//dispatcher 방식이 아닌 리다이렉트 방식으로 이동
	}
}
