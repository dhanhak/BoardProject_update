package cont;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dto.MemberDTO;

@WebServlet("/UpdateProc")
public class UpdateProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		
		// 로그인할 때 loginId로 줬으니까 !!!!!
		String id = (String)request.getSession().getAttribute("loginID");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String zipcode = request.getParameter("zipcode");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		
		try {
			int result = MemberDAO.getInstance().updateMember(new MemberDTO(id,null,name,phone,email,zipcode,address1,address2,null));
			//response.sendRedirect("/index.jsp");
			if(result>0) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<script>alert('수정 성공!'); location.href='/MyPage';</script>"); 
				writer.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
