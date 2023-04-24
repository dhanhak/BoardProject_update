package controllers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.FilesDAO;
import dto.FilesDTO;

@WebServlet("*.file")
public class FileController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FilesDAO dao = FilesDAO.getInstance();
		String cmd = request.getRequestURI();
		System.out.println("요청된 URL : " + cmd);
		try {
			if(cmd.equals("/uploadForm.file")) {
				response.sendRedirect("/file/upload.jsp");

			}else if(cmd.equals("/upload.file")) {

				String realPath = request.getServletContext().getRealPath("upload");
				File realPathFile = new File(realPath);
				if(!realPathFile.exists()) {	// 그 폴더가 없다면
					realPathFile.mkdir();		// 폴더를 만들어라	(클라이언트가 만든 파일을 보관하기 위해서)
				}
				System.out.println(realPath);
				MultipartRequest multi = new MultipartRequest(request, realPath, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());

				String message = multi.getParameter("message");
				System.out.println("전송 된 메세지 : " + message);

				// seq
				// 업로드 시킬 때 당시의 원본 이름.
				String oriName = multi.getOriginalFileName("file");
				// 업로드 되어 RenamePolicy 영향을 받은 후 이름.
				String sysName = multi.getFilesystemName("file");
				// parent_seq 

				dao.insert(new FilesDTO(0,oriName,sysName,0));
				response.sendRedirect("/");

			}else if(cmd.equals("/list.file")) {
				
				List<FilesDTO> list = dao.list();
				request.setAttribute("list", list);

				request.getRequestDispatcher("/file/list.jsp").forward(request, response);
			}else if(cmd.equals("/download.file")) {
				
				String oriName = request.getParameter("oriName");
				
				oriName = new String(oriName.getBytes("utf-8"), "ISO-8859-1");
				
				response.reset();
				response.setHeader("Content-Disposition", "attachment;filename=" + oriName);

				String uploadPath = request.getServletContext().getRealPath("upload");
				String sysName = request.getParameter("sysName");

				File target = new File(uploadPath + "/" + sysName);

				try(ServletOutputStream sos = response.getOutputStream();
						FileInputStream fis = new FileInputStream(target);
						DataInputStream dis = new DataInputStream(fis);){
					byte[] fileContents = new byte[(int) target.length()];
					dis.readFully(fileContents);
					sos.write(fileContents);
					sos.flush();
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
