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
	<fieldset>
		<legend>File List</legend>
			<c:forEach var="i" items="${list}">
				<div>${i.id} : <a href="/download.file?sysName=${i.sysName}&oriName=${oriName}">${i.oriName}</a></div>
			</c:forEach>
	</fieldset>
</body>
</html>