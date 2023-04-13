package cont;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;

@WebServlet("/MemberOut")
public class MemberOut extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		
		MemberDAO dao = MemberDAO.getInstance();
		
		String id = (String)request.getSession().getAttribute("loginID");
		
		try {
			int result = dao.memberDelete(id);
			request.getSession().invalidate();
			
			if(result>0) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<script>alert('탈퇴에 성공하셨습니다.'); location.href='index.jsp';</script>"); 
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
