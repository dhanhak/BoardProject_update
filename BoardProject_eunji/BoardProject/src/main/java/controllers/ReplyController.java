package controllers;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReplyDAO;

@WebServlet("*.reply")
public class ReplyController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String cmd = request.getRequestURI();
		System.out.println(cmd);
		
		try {
			if(cmd.equals("/insert.reply")) {
				ReplyDAO dao = ReplyDAO.getInstance();
				
				String commentId = request.getParameter("commentId");
				String comment = request.getParameter("comment");
				int replyseq = Integer.parseInt(request.getParameter("replyseq"));
				
				dao.insertReply(commentId,comment,replyseq);
				
				response.sendRedirect("/selectContentsCheck.board?seq="+replyseq);
				
			}else if(cmd.equals("/delete.reply")) {
				ReplyDAO dao = ReplyDAO.getInstance();
				int id = Integer.parseInt(request.getParameter("id"));
				
				int replyseq = Integer.parseInt(request.getParameter("replyseq"));
				
				dao.deleteReply(id);
				
				response.sendRedirect("/selectContentsCheck.board?seq="+replyseq);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.html");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
