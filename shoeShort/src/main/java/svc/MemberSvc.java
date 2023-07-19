package svc;

import dao.*;
import vo.*;

public class MemberSvc {
	private MemberDao memberDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public int memberInsert(MemberInfo mi) {
		int result = memberDao.memberInsert(mi);
		return result;
	}
}
