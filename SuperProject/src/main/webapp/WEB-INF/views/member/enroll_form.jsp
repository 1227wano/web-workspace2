<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	#hobby-wrap{
	    display: flex;
	    justify-content: center;
	    flex-direction: row;
	    flex-wrap: nowrap;
	    align-items: baseline;
	}
	input[type=checkbox]{
		margin-left : 50px;
	}
</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />
	
	<script>
		function idCheck(){
			
			// console.log('하하');
			
			// 아이디 중복체크 구현하기
			
			// 사용자가 입력한 아이디값
			
			const $userId = $('#user_id');
		
			// console.log($userId.val());
			
			if($userId.val().length > 3){
			// AJAX로 요청하기
				$.ajax({
					url : 'checkId.me',
					type : 'get',
					data : {
						id : $userId.val()
					},
					success : function(response){
					
						// console.log(response);
						// response 경우의 수 : NNNNN / NNNNY
						
						if(response === "NNNNN"){
							
							$('#id-result').css('font-size', '12px')
											.css('color', 'red')
											.text('이미 존재하는 아이디임다.');
							$userId.focus();	// 못쓰게 하는거
						} else{
							
							$('#id-result').css('font-size', '12px')
											.css('color', 'green')
											.text('사용가능한 아이디입니다.');
						}
					}
				});
			} else{
				$('#id-result').css('color', 'white');
			}
			
		}
	
	</script>
	
	<div style="width : 80%; margin : auto; padding : 50px;">
		<form action="sign-up.me" name="signup" id="signUpForm" method="post"
						style="margin-bottom: 0;">
			<table
				style="cellpadding: 0; cellspacing: 0; margin: 0 auto; width: 100%">
				<tr>
					<td style="text-align: left">
						<p><strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="idChk"></span></p>
					</td>							
				</tr>
				<tr>
					<td><input type="text" name="userId" id="user_id"
						class="form-control tooltipstered" maxlength="14"
						required="required" aria-required="true"
						style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
						placeholder="숫자와 영어로 4-10자" onkeyup="idCheck()">
						</td>
					
				</tr>
				<tr>
					<td><label id="id-result"></label></td>
				</tr>
				<tr>
					<td style="text-align: left">
						<p><strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk"></span></p>
					</td>
				</tr>
				<tr>
					<td><input type="password" size="17" maxlength="20" id="password"
						name="userPwd" class="form-control tooltipstered" 
						maxlength="20" required="required" aria-required="true"
						style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
						placeholder="영문과 특수문자를 포함한 최소 8자"></td>
				</tr>
				<tr>
					<td style="text-align: left">
						<p><strong>비밀번호를 재확인해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwChk2"></span></p>
					</td>
				</tr>
				<tr>
					<td><input type="password" size="17" maxlength="20" id="password_check"
						name="pw_check" class="form-control tooltipstered" 
						maxlength="20" required="required" aria-required="true"
						style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
						placeholder="비밀번호가 일치해야합니다."></td>
				</tr>
	
				<tr>
					<td style="text-align: left">
						<p><strong>이름을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="nameChk"></span></p>
					</td>
				</tr>
				<tr>
					<td><input type="text" name="userName" id="user_name"
						class="form-control tooltipstered" maxlength="6"
						required="required" aria-required="true"
						style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
						placeholder="한글로 최대 6자"></td>
				</tr>
				
				<tr>
					<td style="text-align: left">
						<p><strong>이메일을 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="emailChk"></span></p>
					</td>
				</tr>
				<tr>
					<td><input type="email" name="email" id="user_email"
						class="form-control tooltipstered" 
						required="required" aria-required="true"
						style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
						placeholder="ex) kh@kh.com"></td>
				</tr>
				
				<tr>
					<td style="text-align: left">
						<p><strong>취미를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="hobbyChk"></span></p>
					</td>
				</tr>
				<tr>
					<td id="hobby-wrap">
						<input type="checkbox" id="baseball" value="야구" name="interest">&nbsp;<label for="baseball">야구</label>
						<input type="checkbox" id="device" value="전자제품" name="interest">&nbsp;<label for="device">전자제품</label>
						<input type="checkbox" id="camping" value="캠핑" name="interest">&nbsp;<label for="camping">캠핑</label>

						<br>

						<input type="checkbox" id="farming" value="농사" name="interest">&nbsp;<label for="farming">농사</label>
						<input type="checkbox" id="web" value="web" name="interest">&nbsp;<label for="tea">웹</label>
						<input type="checkbox" id="java" value="자바" name="interest">&nbsp;<label for="java">자바</label>
					</td>
				</tr>
	
				<tr>
					<td style="padding-top: 10px; text-align: center">
						<p><strong>회원가입하셔서 KH 수강생이 되어보세요~~!</strong></p>
					</td>
				</tr>
				<tr>
					<td style="width: 100%; text-align: center; colspan: 2;"><input
						type="submit" value="회원가입" 
						class="btn form-control tooltipstered" id="signup-btn"
						style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:include page="../include/footer.jsp" />

</body>
</html>