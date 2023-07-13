package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

@WebServlet("/comboCtgr")
public class ComboCtgrCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ComboCtgrCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		ComboCtgrSvc comboCtgrSvc = new ComboCtgrSvc();
		ArrayList<ProductCtgrBig> bigList = comboCtgrSvc.getBigList();
		ArrayList<ProductCtgrSmall> smallList = comboCtgrSvc.getSmallList();
		
		request.setAttribute("bigList", bigList);
		request.setAttribute("smallList", smallList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("combo_ctgr.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
