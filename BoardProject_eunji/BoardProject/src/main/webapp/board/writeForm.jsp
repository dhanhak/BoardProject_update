<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
</head>
<body>
<form action="/insertContentsCheck.board" method="post">
	<table border="1">
		<tr>
			<th height="40">자유게시판 </th>
		</tr>
		<tr>
			<td>
			<input type="text" name="title" id="title" placeholder="글 제목을 입력하세요"></td>
		</tr>
		<tr>
			<td><textarea name="contents" id="contents" placeholder="글 내용을 입력하세요." cols="100" rows="20"></textarea></td>
		</tr>
		<tr>
			<td align="right">
				<a href="/list.board?cpage=1"> <input type="button" value="목록으로"></a>
				<a><input type="submit" value="작성완료"></a>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>