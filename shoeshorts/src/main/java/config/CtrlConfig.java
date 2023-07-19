package config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import ctrl.*;
import test.*;
import svc.*;

@Configuration
public class CtrlConfig {
	
	@Autowired
	private LoginSvcSpr loginSvcSpr;
	@Autowired
	private MemberSvc memberSvc;
	
	@Bean
	public TestCtrl testCtrl() {
		return new TestCtrl();
	}	
/*
	@Bean
	public IndexCtrl indexCtrl() {
		return new IndexCtrl();
	}	
*/	
	
	@Bean
	public LoginCtrlMvc loginCtrlMvc() {
		return new LoginCtrlMvc();
	}
	
	@Bean
	public LoginCtrlSpr loginCtrlSpr() {
		LoginCtrlSpr loginCtrlSpr = new LoginCtrlSpr();		
		loginCtrlSpr.setLoginSvcSpr(loginSvcSpr);
		return loginCtrlSpr;
	}
	
	@Bean
	public LogoutCtrl logoutCtrl() {
		return new LogoutCtrl();
	}
	@Bean
	public MemberCtrl memberCtrl() {
		MemberCtrl memberCtrl = new MemberCtrl();
		memberCtrl.setMemberSvc(memberSvc);
		return memberCtrl;
	}
}
