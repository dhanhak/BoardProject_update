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
<body>
	<table border="1" width="100%">
		<tr>
			<th height="20" colspan="5">자유게시판</th>
		</tr>
		<tr height="20">
			<th width="4%"></th>
			<th align="center" width="50%">제목</th>
			<th align="center" width="15%">작성자</th>
			<th align="center" width="16%">날짜</th>
			<th align="center" width="15%">조회</th>

		</tr>

		<c:forEach var="i" items="${result}">
			<tr height="30">
				<td align="center" width="4%">${i.seq}</td>
				<!-- get 방식으로 보내기 -->
				<td align="center" width="50%" id="title"><a
					href="/selectContentsCheck.board?seq=${i.seq}">${i.title}</a></td>
				<td align="center" width="15%">${i.writer}</td>
				<td align="center" width="16%"><%@page
						import="commons.CalculationUtils"%>
					${CalculationUtils.calculateTime(i.write_date)}</td>
				<td align="center" width="15%">${i.view_count}</td>
			</tr>
		</c:forEach>

		<tr height="20">
			<td align="center" colspan="5">${navi }</td>
		</tr>
		<tr height="20">
			<td align="right" colspan="5">
			<a href="/toIndex.board"><input type="button" id="back" value="뒤로가기"></a> 
			<a href="/toWriteForm.board"><input type="button" id="writeBtn" value="작성하기"></a></td>
		</tr>
	</table>

	<script>
	<!--
		$("title").on(
				"click",
				function() {
					location.href = "/selectContentsCheck.board?seq="
							+ this.previousSibling.innerText;
				})
		-->
	</script>
</body>
</html>