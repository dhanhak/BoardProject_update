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
	<form action="/updateContentsCheck.board?seq=${dto.seq}" method="post">
		<table border="1">
			<tr>
				<th height="40" colspan=2><input type="text" name="title"
					id="title" value="${dto.title}"
					style="text-align: center; width: 99%; height: 40px;" readonly>
				</th>
			</tr>
			<tr>
				<td>작성자 : ${dto.writer}</td>
				<td>조회수 : ${dto.view_count}</td>
			</tr>
			<tr>
				<td colspan=2><textarea name="contents" id="contents"
						cols="100" rows="20" readonly>${dto.contents}</textarea></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${dto.writer == loginID}">
						<td id="control" align="right" colspan=2>
							<a href="/list.board?cpage=1">
								<input type="button" id="back" value="목록으로"></a> 
							<a>
								<input type="button" id="update" value="수정하기"></a> 
							<a href="/deleteContentsCheck.board?seq=${dto.seq}">
								<input type="button" id="delete" value="삭제하기"></a>
						</td>
					</c:when>
					<c:otherwise>
							<td align="right" colspan=2>
								<a href="/list.board?cpage=${cpage }"><input type="button" value="목록으로"></a>
							</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	</form>
	<script>
		$("#update").on("click", function() {
			$("input").removeAttr("readonly");
			$("textarea").removeAttr("readonly");
			$("#back,#update,#delete").css("display", "none");

			let updateComplete = $("<button>");
			updateComplete.text("수정 완료");

			let cancel = $("<button>");
			cancel.attr("type", "button");
			cancel.text("취소");
			cancel.on("click", function() {
				location.reload();
			})

			$("#control").append(cancel);
			$("#control").prepend(updateComplete);

		})
	</script>
</body>
</html>