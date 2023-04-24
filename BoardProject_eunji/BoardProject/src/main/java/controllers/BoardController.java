package controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import dao.FileDAO;
import dao.ReplyDAO;
import dto.BoardDTO;
import dto.FileDTO;
import dto.ReplyDTO;
import statics.Settings;

@WebServlet("*.board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		
		String cmd = request.getRequestURI();
		
		try {
			if(cmd.equals("/list.board")) {
				
				int currentPage = Integer.parseInt(request.getParameter("cpage"));
				request.getSession().setAttribute("cpage",currentPage);
				
				// 1 -> 1, 10
				// 2 -> 11, 20
				// 3 -> 21, 30
				int start = currentPage * Settings.BOARD_RECORD_DOUNT_PER_PAGE -(Settings.BOARD_RECORD_DOUNT_PER_PAGE-1);
				int end = currentPage * Settings.BOARD_RECORD_DOUNT_PER_PAGE;
				
				List<BoardDTO> arr = BoardDAO.getInstance().selectBound(start,end);
				List<String> pageNavi = BoardDAO.getInstance().getPageNavi(currentPage,null,null);
				
				request.setAttribute("result", arr);
				request.setAttribute("navi", pageNavi);
				request.getRequestDispatcher("/board/board.jsp").forward(request, response);
				
			}else if(cmd.equals("/toWriteForm.board")) {
				int currentPage = Integer.parseInt(request.getParameter("cpage"));
				request.getSession().setAttribute("cpage",currentPage);
				
				response.sendRedirect("/board/writeForm.jsp");
				
			}else if(cmd.equals("/toIndex.board")) {
				response.sendRedirect("/index.jsp");
				
			}else if(cmd.equals("/insertContentsCheck.board")) {
				String realPath = request.getServletContext().getRealPath("upload");
				
				File realPathFile = new File(realPath);
				if(!realPathFile.exists()) {
					realPathFile.mkdir();
				}
				
				MultipartRequest multi = new MultipartRequest(request, realPath, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());

				String writer = (String)request.getSession().getAttribute("loginID");
				String title = multi.getParameter("title");
				String contents = multi.getParameter("contents");
				
				FileDAO dao = FileDAO.getInstance();
				
				
				System.out.println(realPath);
				String message = multi.getParameter("message");
				System.out.println("전송 된 메세지 : " + message);
				
				String oriName = multi.getOriginalFileName("file");
				String sysName = multi.getFilesystemName("file");

				int boardseq = BoardDAO.getInstance().getBoardSeq();
				int result = BoardDAO.getInstance().insertContent(boardseq,writer, title, contents);
				dao.insert(new FileDTO(0,oriName,sysName,boardseq));
				
				//response.sendRedirect("/index.jsp?state=a_w");
				if(result>0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter pwriter = response.getWriter();
					pwriter.println("<script>alert('글 작성이 완료되었습니다!'); location.href='/list.board?cpage=1';</script>"); 
					pwriter.close();
				}
				
			}else if(cmd.equals("/selectContentsCheck.board")) {
				BoardDAO dao = BoardDAO.getInstance();
				ReplyDAO replydao = ReplyDAO.getInstance();
				FileDAO filedao = FileDAO.getInstance();
				
				int searchSeq = Integer.parseInt(request.getParameter("seq"));
				
				String loginID = (String)request.getSession().getAttribute("loginID");
				request.setAttribute("loginID", loginID);
				
				// 클릭하자마자 조회수 1 up
				dao.updateViewCount(searchSeq);
				// searchSeq에 해당하는 dto 반환
				BoardDTO dto = dao.selectContentBySeq(searchSeq);
				request.setAttribute("dto", dto);
				
				List<ReplyDTO> replylist = replydao.selectBySeq(searchSeq);
				request.setAttribute("replylist", replylist);
				
				FileDTO file = filedao.list(searchSeq);
				request.setAttribute("file", file);
				
				request.getRequestDispatcher("/board/contentsCheckView.jsp").forward(request, response);
				
			}else if(cmd.equals("/deleteContentsCheck.board")) {
				int deleteSeq = Integer.parseInt(request.getParameter("seq"));
				
				int result = BoardDAO.getInstance().deleteContentBySeq(deleteSeq);
				if(result>0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter pwriter = response.getWriter();
					pwriter.println("<script>alert('게시글 삭제 완료!'); location.href='/list.board?cpage=1';</script>"); 
					pwriter.close();
				}
				
			}else if(cmd.equals("/updateContentsCheck.board")) {
				int updateSeq = Integer.parseInt(request.getParameter("seq"));
				
				String title = request.getParameter("title");
				String contents = request.getParameter("contents");
				
				int result = BoardDAO.getInstance().updateContentBySeq(updateSeq,title,contents);
				if(result>0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter pwriter = response.getWriter();
					pwriter.println("<script>alert('게시글 수정 완료!'); location.href='/list.board?cpage=1';</script>"); 
					pwriter.close();
				}
				
			}else if(cmd.equals("/search.board")) {
				
				BoardDAO dao = BoardDAO.getInstance();
				int currentPage = Integer.parseInt(request.getParameter("cpage"));
				
				String sel = request.getParameter("sel");
				String search = request.getParameter("search");
				int start = currentPage * Settings.BOARD_RECORD_DOUNT_PER_PAGE -(Settings.BOARD_RECORD_DOUNT_PER_PAGE-1);
				int end = currentPage * Settings.BOARD_RECORD_DOUNT_PER_PAGE;
				
				List<BoardDTO> list = dao.searchBoard(start, end, sel, search);
				
				List<String> pageNavi = dao.getPageNavi(currentPage,sel,search);
				System.out.println(search);
				request.setAttribute("sel", sel);
				request.setAttribute("search", search);
				request.setAttribute("result", list);
				request.setAttribute("navi", pageNavi);
				request.getRequestDispatcher("/board/board.jsp").forward(request, response);
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
