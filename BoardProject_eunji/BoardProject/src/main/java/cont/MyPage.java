package cont;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dto.MemberDTO;

@WebServlet("/MyPage")
public class MyPage extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDAO dao = MemberDAO.getInstance();
		
		try {
			String id = (String)request.getSession().getAttribute("loginID");
			
			MemberDTO dto = dao.memberInfo(id);
			
			request.setAttribute("my", dto);
//			request.setAttribute("id", dto.getId());
//			request.setAttribute("name", dto.getName());
//			request.setAttribute("phone", dto.getPhone());
//			request.setAttribute("email", dto.getEmail());
//			request.setAttribute("zipcode", dto.getZipcode());
//			request.setAttribute("address1", dto.getAddress1());
//			request.setAttribute("address2", dto.getAddress2());
			request.getRequestDispatcher("/member/mypageView.jsp").forward(request, response);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
