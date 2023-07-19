package ctrl;

import java.io.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
@RequestMapping("/loginSpr")
public class LoginCtrlSpr {
 	private LoginSvcSpr loginSvcSpr;

	public void setLoginSvcSpr(LoginSvcSpr loginSvcSpr) {
		this.loginSvcSpr = loginSvcSpr;
	}
	
	@GetMapping // �슂泥�(loginSpr)�쓣 get諛⑹떇�쑝濡� 諛쏆븯�쓣 寃쎌슦
	public String loginForm() {
		return "loginFormSpr"; 
	}
	
	@PostMapping
	public String loginProc(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid").trim().toLowerCase();
		String pwd = request.getParameter("pwd").trim();	
		MemberInfo loginInfo = loginSvcSpr.getLoginInfo(uid, pwd);

		if (loginInfo == null) { // 濡쒓렇�씤 �떎�뙣
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�븘�씠�뵒�� �븫�샇瑜� �솗�씤�븯�꽭�슂.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("loginInfo", loginInfo);
		}
		
		return "redirect:/";
	}
}
