package cont;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;

@WebServlet("/IdCheck")
public class IdCheck extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf8");
		
		MemberDAO dao = MemberDAO.getInstance();

		String id = request.getParameter("id");
		System.out.println("전달 된 ID : " + id);
		try {
			boolean result = dao.isIdExist(id);
			
			request.setAttribute("result", result); // 결과값 idCheckView로 넘겨줌
			request.getRequestDispatcher("/member/idCheckView.jsp").forward(request, response);
			
//			if(result == true) {
//				response.getWriter().append("아이디가 이미 존재합니다.");
//			}else {
//				response.getWriter().append("아이디가 존재하지 않습니다.");
//			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
