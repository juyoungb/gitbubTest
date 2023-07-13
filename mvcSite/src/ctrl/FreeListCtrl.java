package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import svc.*;
import vo.*;


@WebServlet("/freeList")
public class FreeListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public FreeListCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0;
		// 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수 등을 저장할 변수
		if(request.getParameter("cpage") != null) 
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage 값이 있으면 받아서 int형으로 형변환 함(보안상의 이유와 산술연산해야 하기 때문에)
		
		String where =" where fl_isview='y' ";
		String args = "", schargs="";	//쿼리스트링을 저장할 변수
		args = "&cpage=" + cpage;
		
		String schtype = request.getParameter("schtype"); // 검색조건(제목, 내용, 제목+내용)
		String keyword = request.getParameter("keyword"); // 검색어
	
		if(schtype == null || keyword == null) {
			schtype =""; 	keyword ="";
			// 화면상의 검색어가 NULL로 보이지 않게하기 위해 빈문자열로 채움
		} else if(!schtype.equals("") && !keyword.equals("")) {
			// 검색조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			//쿼리 스트링으로 주고 받는 검색어가 한글일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니 코드로 변환
			if (schtype.equals("tc")) {//검색조건이 '제목+내용'일 경우
				where += " and (fl_title like '%" + keyword + "%' or fl_content like '%" + keyword + "%')";
			}else {//검색조건이 '제목'이거나 '내용'일 경우
				where += " and fl_" + schtype + " like '%" + keyword + "%' "; 
			}
			schargs = "&schtype=" + schtype + "&keyword=" + keyword;
			args += schargs;
		}
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		rcnt = freeProcSvc.getFreeListCount(where);
		// 검색된 게시글의 총 개수로 게시글 일련번호와 전체 페이지수 계산을 위해 필요한 값
		
		pcnt = rcnt / psize;
		if(rcnt % psize > 0) pcnt++; // 전체 페이지수 (마지막페이지 번호)
		
		ArrayList<FreeList> freeList = freeProcSvc.getFreeList(where, cpage, psize);
		//목록 화면에서 보여줄 게시글 목록을 ArrayList<FreeList>형으로 받아옴
		//필요한 만큼만 받아오기 위해 cpage, psize가 필요함
		
		PageInfo pageInfo = new  PageInfo();
		pageInfo.setBsize(bsize);
		pageInfo.setCpage(cpage);			pageInfo.setPcnt(pcnt);
		pageInfo.setRcnt(rcnt);				pageInfo.setPsize(psize);
		pageInfo.setSchtype(schtype); 		pageInfo.setKeyword(keyword);
		pageInfo.setArgs(args);				pageInfo.setSchargs(schargs);
		//페이징과 검색에 필요한 정보들을 pageInfo형 인스턴스에 저장
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("freeList", freeList);
		// request의 영역은 요청을 받은 파일과 forward()로 이동하는 파일에서 사용할 수 있음
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_list.jsp");
		dispatcher.forward(request, response);
	}
}

