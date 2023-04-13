package cont;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commons.EncryptionUtils;
import dao.MemberDAO;

@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf8");
		

		try {
			MemberDAO dao = MemberDAO.getInstance();
			String id = request.getParameter("userID");
			String pw = EncryptionUtils.sha512(request.getParameter("userPW"));
			
			boolean result = dao.loginCheck(id, pw);
			
			if(result) {
				// request는 a->b만 정보 공유 가능(휘발성 높아서)
				// 리턴값이 세션 저장소
				// 세션 저장소는 tomcat이 켜졌을 때 만들어짐
				// 세션 저장소에 데이터 보관
				// 서버쪽 전역변수와 같음
				// Tomcat에게 키 주면서 Tomcat이 해당하는 키의 값을 가져옴
				request.getSession().setAttribute("loginID", id);
				// request.getSession().setAttribute("userPW", id);
			}
			
			response.sendRedirect("/index.jsp");
			
			// request.setAttribute("result", result);
			// request.getRequestDispatcher("/member/loginCheckView.jsp").forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
