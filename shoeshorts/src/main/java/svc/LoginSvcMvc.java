package svc;

import config.*;
import dao.*;
import vo.*;

public class LoginSvcMvc {
	public MemberInfo getLoginInfo(String uid, String pwd) {
		MemberInfo mi = null;
		DbConfig dbConfig = new DbConfig(); 
		LoginDaoMvc loginDaoMvc = new LoginDaoMvc(dbConfig.dataSource());
		mi = loginDaoMvc.getLoginInfo(uid, pwd);
		
		return mi;
	}
}
