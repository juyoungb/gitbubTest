package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/freeFormIn")
public class FreeFormInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FreeFormInCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/bbs/free_form_in.jsp");
		dispatcher.forward(request, response);
		//글 등록 전용폼으로 따로 service나 Dao없이 바로 view에 해당하는 jsp 파일로 이동
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}
