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

	<!-- setAttribute로 보냈을 때만 ${state} 이렇게 받기 가능 -->
	<c:if test="${param.state == 'a_j'}">
		<script>
			alert("회원가입을 축하합니다.");
		</script>
	</c:if>
	<c:if test="${param.state == 'a_w'}">
		<script>
			alert("글 작성이 완료 되었습니다!");
		</script>
	</c:if>

	<c:choose>
		<c:when test="${loginID == null}">
			<form action="/loginCheck.members" method="post">
				<table border="1" align="center">
					<tr>
						<th colspan="2">Login Box</th>
					</tr>
					<tr>
						<td>아이디 :</td>
						<td><input type="text" name="userID"
							placeholder="Input your id"></td>
					</tr>
					<tr>
						<td>패스워드 :</td>
						<td><input type="password" name="userPW"
							placeholder="Input your pw"></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							id="login" value="로그인"> <input type="button" id="toJoin"
							value="회원가입"><br> <input type="checkbox">ID
							기억하기</td>
					</tr>
				</table>
			</form>
		</c:when>
		<c:otherwise>
			<!-- 로그인 했다는 뜻 -->
			<!-- request는 forward 타고 올 수 있다. -->
			<table border=1 align=center>
				<tr>
					<th colspan=4>${sessionScope.loginID}님 환영합니다.</th>
				</tr>
				<tr>
					<td align=center><a href="/list.board?cpage=1"><button>게시판으로</button></a></td>
					<td align=center><a href="/myPage.members"><button>마이페이지</button></a></td>
					<td align=center><a href="/logout.members"><button>로그아웃</button></a></td>
					<td align=center><a href="/memberOut.members"><button id="memberOut">회원탈퇴
							</button></a></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>



	<script>
		$("#memberOut").on("click", function(){
			let result = confirm("정말 탈퇴 하시겠습니까?");
			if(result){
				location.href="/memberOut.members";
			}
		})
		$("#toJoin").on("click", function() {
			// 파일 안에 있어서 루트 적어줘야
			location.href = "/member/joinform.jsp";
		})
	</script>
</body>
</html>