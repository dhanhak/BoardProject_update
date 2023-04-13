<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
	crossorigin="anonymous"></script>
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
			<!-- 사용할 수 없다. -->
			<table border=1 class="alert alert-light">
				<tr>
					<th colspan=2>중복 검사 결과
				</tr>
				<tr>
					<td>이미 사용중인 ID 입니다.
				</tr>
				<tr>
					<td><button id="close" class="btn btn-success">닫기</button> <script>
		$("#close").on("click", function(){
			opener.document.getElementById("id").value = "";
			window.close(); // 팝업창을 닫음
		})
	</script>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<!-- 사용 가능 -->
			<table border=1 class="alert alert-light">
				<tr>
					<th colspan=2>중복 검사 결과
				</tr>
				<tr>
					<td colspan=2>사용 가능한 ID 입니다.<br> 이 ID를 사용하시겠습니까?
				</tr>
				<tr>
					<td><button id="use" class="btn btn-success">사용</button>
					<td><button id="cancel" class="btn btn-success">취소</button>
					<script>
						$("#use").on("click", function(){
							opener.idValidFlag = true; // opener를 통해 window의 변수에 접근가능
							window.close();
						})
						$("#cancel").on("click", function(){
							opener.document.getElementById("id").value = "";
							window.close();
						})
					</script>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>

</body>
</html>