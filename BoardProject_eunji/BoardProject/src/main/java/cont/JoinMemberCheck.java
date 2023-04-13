package cont;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commons.EncryptionUtils;
import dao.MemberDAO;
import dto.MemberDTO;


@WebServlet("/JoinMemberCheck")
public class JoinMemberCheck extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = new MemberDTO();
		
		try {
			String id = request.getParameter("id");
			String pw = EncryptionUtils.sha512(request.getParameter("pw"));
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String zipcode = request.getParameter("zipcode");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			
			int result = dao.joinMember(new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2,null));
			
			// alert창 띄우기
			response.sendRedirect("/index.jsp?state=a_j");
			
//			if(result>0) {
//				response.setContentType("text/html; charset=UTF-8");
//				PrintWriter writer = response.getWriter();
//				writer.println("<script>alert('입력 성공!'); location.href='index.jsp';</script>"); 
//				writer.close();
//			}
			
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
