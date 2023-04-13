<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<style>
table {
	margin: auto;
}
</style>
</head>
<body>
	<form action="/updateProc.members" method="post" id="updateFrm">
		<table border=1 align="center">
			<tr>
				<th colspan=2>My Info</th>
			</tr>
			<tr>
				<td>아이디</td>
				<td>${my.id}</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td><input type=text id="pw" name="pw" readonly></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" id="name" value="${my.name}" name="name" readonly></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" id="phone" value="${my.phone}" name="phone"
					readonly></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" id="email" value="${my.email}" name="email"
					readonly></td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td><input type="text" id="zipcode" value="${my.zipcode}" name="zipcode"
					readonly>
					<button type="button" id="find">우편번호 찾기</button></td>
			</tr>
			<tr>
				<td>주소1</td>
				<td><input type="text" id="address1" value="${my.address1}" name="address1"
					readonly></td>
			</tr>
			<tr>
				<td>주소2</td>
				<td><input type="text" id="address2" value="${my.address2}" name="address2"
					readonly></td>
			</tr>
			<tr>
				<td>가입 날짜</td>
				<td>${my.join_date}</td>
			</tr>
			<tr>
				<td colspan=2 align=center id="control">
					<!-- 기본이 submit이니까 ! -->
					<button type="button" id="update">정보수정</button>
					<button type="button" id="back">돌아가기</button>
				</td>
				<!-- <td colspan=2 align="center"><a href="index.jsp">back</a></td> -->
			</tr>
		</table>
	</form>
	<script>
		$("#update").on("click", function() {
			$("input").removeAttr("readonly");
			$("#update,#back").css("display", "none");

			// 업데이트 완료 버튼 -> form으로 전송
			let updateComplete = $("<button>");
			updateComplete.text("수정완료");

			// 취소 버튼
			let cancel = $("<button>");
			cancel.attr("type", "button");
			cancel.text("취소");
			cancel.on("click", function() {
				// 현재 페이지 새로고침 기능 (f5 누르는 효과)
				location.reload();
			})
			$("#control").append(cancel);
			$("#control").prepend(updateComplete);
		});

		$("#back").on("click", function() {
			location.href = "/index.jsp";
		});
	
		// 우편번호 찾기
		$("#find").on("click", function () {
            new daum.Postcode({
                oncomplete: function (data) {
                    var roadAddr = data.roadAddress;
                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById('address1').value = roadAddr;
                }
            }).open();
            return false;
        })
        
        // submit이 발생했을 때 ! click이 아니라!
		$("#updateFrm").on("submit", function() {
			let regexName = /^[A-Za-z가-힣]+$/;
			let regexPhone = /^01\d[0-9]{4}[0-9]{4}$/;
			let regexEmail = /.+?@.+?/;

			let name = $("#name").val();
			let phone = $("#phone").val();
			let email = $("#email").val();

			let resultName = regexName.exec(name);
			let resultPhone = regexPhone.exec(phone);
			let resultEmail = regexEmail.exec(email);

			if (name == "") {
				Swal.fire({
					icon : 'error',
					title : 'Oops',
					text : '필수항목을 채워주세요.',
				})
				return false;
			}
			
			if (!resultName) {
				Swal.fire({
					icon : 'error',
					title : '이름 형식 오류',
					text : '한글만 입력 가능합니다.',
				})
				return false;
			}
			
			if (!resultPhone) {
				Swal.fire({
					icon : 'error',
					title : '전화번호 형식 오류',
					text : '01xxxxxxxxx 형태 ( - 제외 ) 로 입력 가능합니다.',
				})
				return false;
			}

			if (!resultEmail) {
				Swal.fire({
					icon : 'error',
					title : '이메일 형식 오류',
					text : 'oooo@oooo 형태로 입력 가능합니다.',
				})
				return false;
			}

		})
	</script>
</body>
</html>