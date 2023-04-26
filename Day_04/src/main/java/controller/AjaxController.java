package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.ContactDTO;

@WebServlet("*.ajax")
public class AjaxController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getRequestURI();
		Gson g = new Gson();

		System.out.println(cmd);
		if(cmd.equals("/exam01.ajax")) {
			System.out.println("비동기 통신 요청 확인");

		}else if(cmd.equals("/exam02.ajax")) {
			String fruit = request.getParameter("fruit");
			String music = request.getParameter("music");

			System.out.println(fruit + " : " + music);
		}else if(cmd.equals("/exam03.ajax")) {

			response.getWriter().append("Hello AJAX");
		}else if(cmd.equals("/exam04.ajax")) {

			String[] arr = 
					new String[] {"Apple", "Orange", "Mango"};
			String resp =g.toJson(arr);
			response.getWriter().append(resp);
		}else if(cmd.equals("/exam05.ajax")) {
			ContactDTO dto = new ContactDTO(100,"Ryan","010123456");
			String resp = g.toJson(dto);
			response.getWriter().append(resp);
		}else if(cmd.equals("/exam06.ajax")) {

			List<ContactDTO> list = new ArrayList<ContactDTO>();
			list.add(new ContactDTO(1001,"TOM", "0101234516"));
			list.add(new ContactDTO(1002,"FOM", "0101734466"));
			list.add(new ContactDTO(1003,"SOM", "0101789116"));

			String resp = g.toJson(list);
			response.getWriter().append(resp);


		}else if(cmd.equals("/exam07.ajax")) {

			String[] arr = new String[] {"Apple", "Orange", "Mango"};
			List<ContactDTO> contacts = new ArrayList<>();

			contacts.add(new ContactDTO(1001,"Ryan","010123456"));
			contacts.add(new ContactDTO(1002,"Ryan","010123456"));
			contacts.add(new ContactDTO(1003,"Ryan","010123456"));

			//         String arrJson = g.toJson(arr);
			//         String dtoJson = g.toJson(dto);


			JsonObject jsonObject= new JsonObject();
			jsonObject.add("arr", g.toJsonTree(arr));
			jsonObject.add("contacts", g.toJsonTree(contacts));
			response.getWriter().append(g.toJson(jsonObject));  
			
			// 1. 아이디 중복 체크를 비동기로 작성하기
			// 2. 게시판 테이블 내용을 비동기로 받아와 보기.
			// 3. Infinity Scrolling
			
		}else if(cmd.equals("/exam08.ajax")) {
			String realPath = request.getServletContext().getRealPath("upload");
			
			File realPathFile = new File(realPath);
			System.out.println(realPath);
			if(!realPathFile.exists()) {	
				realPathFile.mkdir();		
			}
			MultipartRequest multi = new MultipartRequest(request, realPath, 1024*1024*10,"UTF-8", new DefaultFileRenamePolicy());

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}