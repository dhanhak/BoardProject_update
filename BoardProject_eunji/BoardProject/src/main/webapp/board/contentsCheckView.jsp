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
	<table border="1" align="center">
		<form action="/updateContentsCheck.board?seq=${dto.seq}" method="post">
			<tr>
				<th height="40" colspan=2>
					<input type="text" name="title" id="title" value="${dto.title}" style="text-align: center; width: 99%; height: 40px;" readonly>
				</th>
			</tr>
			<tr>
				<td>작성자 : ${dto.writer}</td>
				<td>조회수 : ${dto.view_count}</td>
			</tr>
			<tr>
				<td colspan=2><textarea name="contents" id="contents" cols="100" rows="20" readonly>${dto.contents}</textarea></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${dto.writer == loginID}">
					<td colspan=1>첨부파일 :
						<c:if test="${file.id != 0}">						
							<span>${file.oriName}</span>
						</c:if>
					</td>
					<td id="control" align="right" colspan=1>
						<a href="/list.board?cpage=1"> 
							<input type="button" id="back" value="목록으로"></a> 
						<a> 
							<input type="button" id="update" value="수정하기"></a> 
						<a href="/deleteContentsCheck.board?seq=${dto.seq}"> 
							<input type="button" id="delete" value="삭제하기"></a></td>
					</c:when>
					<c:otherwise>
						<td align="right" colspan=2><a
							href="/list.board?cpage=${cpage }"><input type="button"
								value="목록으로"></a></td>
					</c:otherwise>
				</c:choose>
			</tr>
			</form>
			<form action="/insert.reply" method="get">
				<tr>
					<td><input type="text" value="${loginID}" name="commentId" readonly></td>
				</tr>
				<tr>
					<td colspan="2"><textarea cols="100" row="10" name="comment" placeholder="댓글 내용을 작성하세요"></textarea></td>
					<input type="text" name="replyseq" value="${dto.seq}" style="display : none;">
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="submit" value="댓글작성">
					</td>
				</tr>
				</form>
				<c:forEach var="item" items="${replylist }">
					<tr>
						<td>${item.writer}</td>
						<td align="right"><a href="/delete.reply?id=${item.id }&replyseq=${item.parent_seq}">
						<input type="button" value="댓글삭제"></a>
						<a href=""><input type="button" value="댓글수정"></a></td>
					</tr>
					<tr>
						<td>${item.contents}</td>
					</tr>
				</c:forEach>
		</table>
	<br>
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