package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

@WebServlet("/productView")
public class ProductViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ProductViewCtrl() { super(); }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		
		// 1.조회수 증가 
		ProductProcSvc productProcSvc = new ProductProcSvc();
		int result = productProcSvc.readUpdate(piid);
		
		// 2.보여줄 상품 정보 받아오기
		ProductInfo pi = productProcSvc.getProductInfo(piid);
		if (pi == null) { //보여주려는 상품이 없을경우
		response.setContentType("text/html; chatset=utf-8");	
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('상품정보가 없습니다.'); history.back()");
		out.println("</script>");
		out.close();
		}
		
		// 3.해당상품의 후기 목록 받아오기
		ArrayList<ReviewList> reviewList = productProcSvc.getReviewList(piid);
				
		// 4.상품상세보기 화면으로 이동
		request.setAttribute("productInfo", pi);
		request.setAttribute("reviewList", reviewList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/product/product_view.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
