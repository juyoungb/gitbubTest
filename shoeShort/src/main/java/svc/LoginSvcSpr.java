package svc;

import dao.*;
import vo.*;

public class LoginSvcSpr {
	private LoginDaoSpr loginDaoSpr;

	public void setLoginDaoSpr(LoginDaoSpr loginDaoSpr) {
		this.loginDaoSpr = loginDaoSpr;
	} 
	
	public MemberInfo getLoginInfo(String uid, String pwd) {
		MemberInfo mi = loginDaoSpr.getLoginInfo(uid, pwd);
		return mi;
	}
	
}

