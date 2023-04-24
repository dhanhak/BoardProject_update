package controllers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.FileDAO;
import dto.FileDTO;

@WebServlet("*.file")
public class FileController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cmd = request.getRequestURI();
		try {
			if(cmd.equals("/download.file")) {
				
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
			response.sendRedirect("error.jsp");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
