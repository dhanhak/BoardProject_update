<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<th>ID</th>
			<th>oriName</th>
			<th>sysName</th>
			<th>parent_seq</th>
		</tr>
		<c:forEach var="i" items="${list}">
			<tr>
				<td>${i.id}</td>
				<td>${i.oriName}</td>
				<td>${i.sysName}</td>
				<td>${i.parent_seq}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" align="right"><a href="/"><button>뒤로가기</button></a></td>
		</tr>
	</table>
</body>
</html>