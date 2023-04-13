<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.4.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <style>
        table{margin:auto;}
        input {
            width: 300px;
            height: 30px;
        }
        td:first-child{text-align: right;}
    </style>
</head>
<body>
	<form id="form" action="/joinMemberCheck.members">
        <table>
            <tr>
                <th colspan="3">회원 가입 정보</th>
            </tr>
            <tr>
                <td>아이디</td>
                <td><input type="text" name="id" id="id"></td>
                <td><button type="button" id="duplCheck">중복 확인</button></td>
                <!-- 팝업창을 띄워 아이디가 사용가능한지 확인 -->
            </tr>
            <tr>
                <td>패스워드</td>
                <td><input type="password" name="pw" id="pw"></td>
            </tr>
            <tr>
                <td>패스워드 확인</td>
                <td><input type="password" name="repw" id="repw"></td>
                <td id="remessage"></td>
            </tr>
            <tr>
                <td>이름</td>
                <td><input type="text" name="name" id="name"></td>
            </tr>
            <tr>
                <td>전화번호</td>
                <td><input type="text" name="phone" id="phone"></td>
            </tr>
            <tr>
                <td>이메일</td>
                <td><input type="text" name="email" id="email"></td>
            </tr>
            <tr>
                <td>우편번호</td>
                <td><input type="text" name="zipcode" id="zipcode"></td>
                <td><button type="button" id="find">찾기</button></td>
                
            </tr>
            <tr>
                <td>주소1</td>
                <td colspan="2"><input type="text" name="address1" id="address1"></td>
            </tr>
            <tr>
                <td>주소2</td>
                <td colspan="2"><input type="text" name="address2" id="address2"></td>
            </tr>
            <tr>
                <td colspan="3" style="text-align: center;">
                    <button type="submit" id="join">회원가입</button>
                    <button type="reset" id="again">다시 입력</button>
                </td>
            </tr>

        </table>
    </form>

    <script>
	
    	var idValidFlag = false; // 회원가입 허락 x
    	
    	// id창에 input 하나라도 발생한다면 false로 갱신
    	
        $("#duplCheck").on("click", function () {
        	window.open("/idCheck.members?id="+$("#id").val(),"","width=350px,height=250px");
        })
        
        // "input"은 마우스 클릭도 해당되니까 키보드를 뗐을 때 false 되도록
    	$("#id").on("keyup",function(){idValidFlag = false;})
        $("#form").on("submit", function(){
        	if(idValidFlag == false){
        		alert("중복 확인을 확인해 주세요");
        		return false;
        	}
        })
    	
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
        //$("#join").on("click", function () {
        	
            // 데이터 DB에 입력 (Servlet -> DAO ->Servlet -> joinform)
            // 다시 로그인 창으로
        //})
        $("#again").on("click", function () {
            $("input").val("");
            return false;
        })


        $("#repw").on("keyup", function () {
            if ($("#pw").val() != $("#repw").val()) {
                $("#remessage").html("패스워드가 일치하지 않습니다.");
                $("#remessage").css({ "color": "red" });
                return false;
            } else {
                // 이렇게 한 줄로도 가능
                $("#remessage").html("패스워드가 일치합니다.").css({ "color": "blue" });
            }
        })

        let regexId = /^[a-z0-9]{6,13}$/;
        let regexPw = /^[a-z0-9_!]{10,}/;
        let regexName = /^[A-Za-z가-힣]+$/;
        let regexPhone = /^01\d[0-9]{4}[0-9]{4}$/;
        let regexEmail = /.+?@.+?/;

        document.getElementById("join").onclick = function () {

            let id = document.getElementById("id").value;
            let pw = document.getElementById("pw").value;
            let repw = document.getElementById("repw").value;
            let name = document.getElementById("name").value;
            let phone = document.getElementById("phone").value;
            let email = document.getElementById("email").value;

            let resultId = regexId.exec(id);
            let resultPw = regexPw.exec(pw);
            let resultName = regexName.exec(name);
            let resultPhone = regexPhone.exec(phone);
            let resultEmail = regexEmail.exec(email);
            
            if (id == "" || pw == "" || repw == "" || name == "") {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops',
                    text: '필수항목을 채워주세요.',
                })
                return false;
            }
            if (!resultId) {
                Swal.fire({
                    icon: 'error',
                    title: 'ID 형식 오류',
                    text: '6 ~ 13 글자의 영소문자, 숫자만 입력 가능합니다.',
                })
                return false;
            }
            if (!resultPw) {
                Swal.fire({
                    icon: 'error',
                    title: 'PW 형식 오류',
                    text: '10글자 이상의 영소문자, 숫자, _ , ! 만 입력 가능합니다.',
                })
                return false;
            }
            if(pw != repw){
                Swal.fire({
                    icon: 'error',
                    title: 'PW 불일치',
                    text: '패스워드를 다시 확인해 주세요.',
                })
                return false;
            }

            if (!resultName) {
                Swal.fire({
                    icon: 'error',
                    title: '이름 형식 오류',
                    text: '한글만 입력 가능합니다.',
                })
                return false;
            }
            if (!resultPhone) {
                Swal.fire({
                    icon: 'error',
                    title: '전화번호 형식 오류',
                    text: '01xxxxxxxxx 형태 ( - 제외 ) 로 입력 가능합니다.',
                })
                return false;
            }
            
            /*
            if (null || resultPhone){
            	
            }else{
            	Swal.fire({
                    icon: 'error',
                    title: '전화번호 형식 오류',
                    text: '01xxxxxxxxx 형태 ( - 제외 ) 로 입력 가능합니다.',
                })
                return false;
            }
            */
            if (!resultEmail) {
                Swal.fire({
                    icon: 'error',
                    title: '이메일 형식 오류',
                    text: 'oooo@oooo 형태로 입력 가능합니다.',
                })
                return false;
            }

            
        }
    </script>
</body>
</html>