<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
<body>
	<form action="/upload.file" method="post" enctype="multipart/form-data">
		<table border="1" align="center">
			<tr>
				<th colspan="2">File Upload</th>
			</tr>
			<tr>
				<td><input type="text" name="message" placeholder="전송할 메세지 입력"></td>
				<td><input type="file" name="file"></td>
			</tr>
			<tr>
				<td><button>Upload</button></td>
			</tr>
		</table>
	</form>
</body>
</html>