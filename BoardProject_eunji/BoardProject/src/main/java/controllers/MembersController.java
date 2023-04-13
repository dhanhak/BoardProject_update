package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import commons.EncryptionUtils;
import dao.MemberDAO;
import dto.MemberDTO;

@WebServlet("*.members")
public class MembersController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");

		String cmd = request.getRequestURI();

		try {
			if(cmd.equals("/loginCheck.members")) {
				MemberDAO dao = MemberDAO.getInstance();
				String id = request.getParameter("userID");
				String pw = EncryptionUtils.sha512(request.getParameter("userPW"));

				boolean result = dao.loginCheck(id, pw);
				if(result) {
					request.getSession().setAttribute("loginID", id);
				}
				response.sendRedirect("/index.jsp");
				
			}else if(cmd.equals("/idCheck.members")) {
				MemberDAO dao = MemberDAO.getInstance();
				String id = request.getParameter("id");
				
				boolean result = dao.isIdExist(id);
				request.setAttribute("result", result); // 결과값 idCheckView로 넘겨줌
				request.getRequestDispatcher("/member/idCheckView.jsp").forward(request, response);
				
			}else if(cmd.equals("/joinMemberCheck.members")) {
				MemberDAO dao = MemberDAO.getInstance();
				
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				String email = request.getParameter("email");
				String zipcode = request.getParameter("zipcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				int result = dao.joinMember(new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2,null));
				response.sendRedirect("/index.jsp?state=a_j");
				
			}else if(cmd.equals("/logout.members")) {
				request.getSession().invalidate();
				response.sendRedirect("/index.jsp");
				
			}else if(cmd.equals("/memberOut.members")) {
				MemberDAO dao = MemberDAO.getInstance();
				String id = (String)request.getSession().getAttribute("loginID");
				
				int result = dao.memberDelete(id);
				request.getSession().invalidate();
				if(result>0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println("<script>alert('탈퇴에 성공하셨습니다.'); location.href='index.jsp';</script>"); 
					writer.close();
				}
				
			}else if(cmd.equals("/myPage.members")) {
				MemberDAO dao = MemberDAO.getInstance();
				String id = (String)request.getSession().getAttribute("loginID");
				MemberDTO dto = dao.memberInfo(id);
				
				request.setAttribute("my", dto);
				request.getRequestDispatcher("/member/mypageView.jsp").forward(request, response);
				
			}else if(cmd.equals("/updateProc.members")) {
				String id = (String)request.getSession().getAttribute("loginID");
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				String email = request.getParameter("email");
				String zipcode = request.getParameter("zipcode");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				
				int result = MemberDAO.getInstance().updateMember(new MemberDTO(id,null,name,phone,email,zipcode,address1,address2,null));
				if(result>0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter writer = response.getWriter();
					writer.println("<script>alert('수정 성공!'); location.href='/MyPage';</script>"); 
					writer.close();
				}
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.html");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
