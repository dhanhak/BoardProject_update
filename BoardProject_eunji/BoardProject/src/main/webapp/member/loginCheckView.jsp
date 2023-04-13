<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
</head>
<style>
table {
	width: 100%;
	height: 90px;
	text-align: center;
	border: 1px solid black;
}
</style>
<body>
	<c:choose>
		<c:when test="${result}">
			로그인 성공 !
		</c:when>
		<c:otherwise>
			로그인 실패 ㅠ.ㅠ
		</c:otherwise>
	</c:choose>
	
</body>
</html>