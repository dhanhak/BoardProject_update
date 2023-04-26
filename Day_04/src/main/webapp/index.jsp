<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AJAX Practice</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
</head>
<body>
	<fieldset>
		<legend>1. 기본 AJAX (Asynchronous JavaScript And XML)</legend>
		<button id="ajax1">테스트</button>
	</fieldset>
	<fieldset>
		<legend>2. 파라미터를 가지는 비동기 통신 (Asynchronous JavaScript And XML)</legend>
		<button id="ajax2">테스트</button>
	</fieldset>
	<fieldset>
		<legend>3.response를 돌려받는 가지는 비동기 통신 (Asynchronous JavaScript
			And XML)</legend>
		<button id="ajax3">테스트</button>
	</fieldset>
	<fieldset>
		<legend>4.배열을 돌려받는 가지는 비동기 통신 (Asynchronous JavaScript And
			XML)</legend>
		<button id="ajax4">테스트</button>
	</fieldset>
	<fieldset>
		<legend>5.객체를 돌려받는 가지는 비동기 통신 (Asynchronous JavaScript And
			XML)</legend>
		<button id="ajax5">테스트</button>
	</fieldset>
	<fieldset>
		<legend>6.객체 배열을 돌려받는 가지는 비동기 통신 (Asynchronous JavaScript And
			XML)</legend>
		<button id="ajax6">테스트</button>
	</fieldset>

	<fieldset>
		<legend>7.여러가지 데이터를 돌려받는 가지는 비동기 통신 (Asynchronous JavaScript
			And XML)</legend>
		<button id="ajax7">테스트</button>
	</fieldset>

	<fieldset>
		<legend>8. 비동기로 파일 전송하기</legend>
		<input type="file" id="fileInput">
		<button id="upload">업로드</button>
	</fieldset>


	<script>
		//1.가장 기본 통신
		$("#ajax1").on("click", function() {

			$.ajax({
				url : "/exam01.ajax"
			}).done();
		})
		$("#ajax2").on("click", function() {

			//2. 파라미터를 가지는 비동기 통신
			$.ajax({
				url : "/exam02.ajax",
				type : "post",
				data : {
					fruit : "Apple", // input type=text name=fruit value="Apple"
					music : "Piano"
				}
			});
		})

		//3.response를 돌려받는 비동기 통신 : server - > client

		$("#ajax3").on("click", function() {
			$.ajax({
				url : "/exam03.ajax"

			}).done(function(resp) {
				console.log(resp);

			});
		})

		// 4.배열을 돌려받는 비동기 통신 :server -> client
		$("#ajax4").on("click", function() {
			$.ajax({
				url : "/exam04.ajax",
				dataType : "json"
			}).done(function(resp) {
				//resp = JSON.pares(resp); //역직렬화 함수 
				console.log(resp[0]);

			});

		})
		//5. 객체를 돌려받는 비동기 통신 : server -> client
		$("#ajax5").on("click", function() {
			$.ajax({
				url : "/exam05.ajax",
				dataType : "json"
			}).done(function(resp) {
				console.log(resp);
				console.log(resp.name)
			});
		})

		//6. 객체 배열을 돌려받는 비동기 통신 : server -> client
		$("#ajax6").on("click", function() {
			$.ajax({
				url : "/exam06.ajax",
				dataType : "json"
			}).done(function(resp) {
				console.log(resp);

			});

		})

		//7. 여러가지 데이터들을 돌려받는 비동기 통신 : server -> client
		$("#ajax7").on("click", function() {
			$.ajax({
				url : "/exam07.ajax",
				dataType : "json"
			}).done(function(resp) {
				console.log(resp.contacts[0].id);
			})

		})
		
		//8. ajax 로 파일 전송하기 : client -> server
		$("#upload").on("click", function(){
			
				let fileInput = $("#fileInput")[0];		//jqery 는 무조건 배열이여서 varchar차지 많이하게됨
				let file = fileInput.files[0];
				
			if(!file){
				alert("파일을 먼저 선택해야 합니다.");
				return;
			}
			
			let formData = new FormData();
			formData.append("file",file);
			
			$.ajax({
				url:"/exam08.ajax",
				type:"post",
				data:formData,
				processData:false,
				contentType:false
			})
			
		});
	</script>

</body>
</html>