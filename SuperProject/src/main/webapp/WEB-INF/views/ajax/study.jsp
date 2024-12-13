<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
		
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	
</head>
<body>
	
	<h1>AJAX(에이잭스)</h1>
	
	<pre>
		Asynchronous JavaScript and XML의 약자로
		서버로부터 데이터를 가져와서 전체페이지를 다시 만들지 않고,
		일부만 로드해서 내용물만 바꿀수 있는 기법
		
		참고로, 기존에 a태그를 이용해서 요청 / form태그를 이용한 요청은 동기식 요청!
		=> 응답페이지가 돌아와야만 그 결과를 볼수있음(== 페이지가 깜빡 거린다.)
		
		비동기식 요청을 보내기 위해서는 AJAX라는 기술이 필요함!
		
		* 동기식 / 비동기식 요청차이
		- 동기식 : 요청 처리 후 응답 HTML데이터를 받아 화면에 렌더링 한 뒤에만 다음 작업이 가능
				  만약 서버에서 응답페이지를 돌려주는 시간이 지연되면 무작정 기다려야 함 <br>
				  전체 화면이 리로드됨(새로고침, 페이지가 기본적으로 한 번 깜빡 거리면서 넘어감)
		
		- 비동기식 : 현재 페이지는 그대로 유지하면서 중간중간 추가적인 요청을 보낼 수 있음
					요청을 보낸다고 해서 다른 페이지로 넘어가지 않음(현재 페이지가 그대로 유지)
					요청을 보내놓고 그에 해당하는 응답이 돌아올때까지 다른 작업을 할수 있음
			예) 아이디 중복체크, 검색어 자동완성, 댓글
		
		* AJAX 
	</pre>
	
	<pre>
		* jQuery를 사용한 AJAX통신
		
		[ 표현법 ]
		$.ajax({
			속성명 : 값;
			속성명 : 값;
			...
		});
		
		* 주요속성
		
		- url      : 요청할URL(필수로 작성)=> form태그의 action속성
		- type     : 요청 전송방식(GET/POST...PUT, DELETE) => form태그의 method속성
		- data     : 요청시 전달값({키:밸류, 키:밸류}) => form태그에 input요소에 입력한 값
		- success  : AJAX통신 성공 시 실행할 익명함수를 정의
		
		- error    : AJAX통신 실패 시 실행할 익명함수를 정의
		- complete : AJAX통신을 성곡하든 실패하든 무조건 끝나면 실행할 익명함수를 정의
		- async    : 서버와의 비동기 처리방식 설정 여부(기본값 true) 
		
		
		* 부수적인 속성
		
		- contentType : request 의 데이터 인코딩 방식 정의 (보내는 측의 데이터 인코딩)
		- dataType : 서버에서 response 로 오는 데이터의 데이터 형 설정, 값이 없다면 스마트하게 판단함
					xml : 트리 형태의 구조
					json : 맵 형태의 데이터 구조 (일반적인 데이터 구조)
					script : javascript 및 일반 String 형태의 데이터
					html : html 태그 자체를 return 하는 방식
					text : String 데이터
		- accept : 파라미터의 타입을 설정 (사용자 특화 된 파라미터 타입 설정 가능)
		- beforeSend : ajax 요청을 하기 전 실행되는 이벤트 callback 함수
		 				(데이터 가공 및 header 관련 설정)
		- cache : 요청 및 결과값을 scope 에서 갖고 있지 않도록 하는 것 (기본값 true)
		- contents : jQuery 에서 response 의 데이터를 파싱하는 방식 정의
		- context : ajax 메소드 내 모든 영역에서 파싱 방식 정의
		- crossDomain : 타 도메인 호출 가능 여부 설정 (기본값 false)
		- dataFilter : response 를 받았을 때 정상적인 값을 return 할 수 있도록 데이터와 데이터 타입 설정
		- global : 기본 이벤트 사용 여부 (ajaxStart, ajaxStop)
				   (버퍼링 같이 시작과 끝을 나타낼 때, 선처리 작업)
		- password : 서버에 접속 권한 (비밀번호) 가 필요한 경우
		- processData : 서버로 보내는 값에 대한 형태 설정 여부 (기본 데이터를 원하는 경우 false 설정)
		- timeout : 서버 요청 시 응답 대기 시간 (milisecond)
		
	</pre>
	
	<hr>
	
	<h2>jQuery방식으로 AJAX요청 및 응답</h2>
	
	<h3>1. 버튼 클릭 시 GET방식으로 서버에 데이터 전송 및 응답</h3>
	
	<div class="form-control">
	
		<div class="form-group">
			입력 : <input type="text" id="input1">
		</div>
		<div class="form-group">
			<button class="btn btn-primary" id="ajax-btn">요청보내기!</button>
		</div>
		
	</div>
	
	응답 : <label id="output1">현재 응답 없음</label>
	
	<script>
	
		// 버튼을 클릭하면 
		$('#ajax-btn').click(function(){
		
			// 동기식 요청 떄 : location.href = '요청URL?쿼리스트림';
		
			// 비동기식 요청
			$.ajax({
				url : 'ajax1.do',
				type : 'get',
				data : {
					value : $('#input1').val()
					// 원래 input요소의 name속성에 적었던 key -> id속성으로 선택해서 value에 있는 값을 뽑는다
				},
				success : function(result){
					console.log('AJAX요청 성공');
					console.log(result);
					$('#output1').text(result);
				},
				error : function(){
					console.log('AJAX요청 실패');
				},
				complete : function(){
					
				}
			});
		});
	
	</script>
	
	<h3>2. 버튼 클릭 시 POST방식으로 요청 및 응답</h3>
	
	이름 : <input type="text" id="input_2_1" /> <br/>
	나이 : <input type="number" id="input_2_2" /> <br/>
	<button onclick="ajaxFunction();">요청~</button>
	
	<br/><br/>
	응답 : <label id="output2">현재 응답 없음</label>
	
	<script>
		function ajaxFunction(){
			
			$.ajax({
				url : 'ajax2.do',
				type : 'post',
				data : {
					name : $('#input_2_1').val(),
					age : $('#input_2_2').val()
				},
				success : function(result){
					
					console.log(result);
					
					// 자바스크립트에서 객체가 가지고 있는 속성값에 접근하는 방법은?
					// 객체명.속성명 or 객체명['속성명']
					$('#output2').text(`이름 : \${result.name}, 나이 : \${result.age}`);
					// \는 el구문이 아니라 스크립트라는 표시
				}
			});
		}
	</script>
	
	<h3>3. 서버로 요청후, 응답으로 돌아온 객체를 화면상에 출력해보기</H3>
	
	회원번호 입력 : <input type="text" id="input3" />
	<button id="findByUserNo">회원 조회하기</button>
	
	<div id="div-result"></div>
	
	게시글 번호 입력 : <input type="text" id="input3_2" />
	
	<table id="output4">
		<thead>
			<tr>
				<th>게시글번호</th>
				<th>게시글제목</th>
				<th>작성자</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	
	</table>
	
	<script>
		
		$('#findByUserNo').on('click', function(){
		
			const value = $('#input3_2').val();
			
			$.ajax({
				url : 'ajax3.do',
				data : {
					userNo : value
				},
				type : 'get',
				success : function(result){
					
					console.log(result);
					/*
					const str = `회원번호 : \${ result.userNo } <br>`
							  + `아이디 : \${ result.userId }  <br>`
							  + `이름 : \${ result.userName }  <br>`;
					
					$('#div-result').html(str);
					*/
					
					// 서블릿에서 gson.toJson으로 List를 이용해서 VO를 여러 개 묶어서 보냈단 말이에요
					
					// 여기부터 아래 잘 기억해두자ㅏㅏㅏㅏ
					
					let resultStr = '';
					
					for(obj of result){	// 객체의 속성의 값을 뽑아
						
						// console.log(obj);
						resultStr += '<tr>'
								   + `<td>\${obj.boardNo}</td>`
								   + `<td>\${obj.boardTitle}</td>`
								   + `<td>\${obj.boardWriter}</td>`
								   + '<tr>';
					}
					
					$('#output4 tbody').html(resultStr);
					
					
				}
			})
			
		});
		
	
	
	</script>
	
	
	
	
	
	
	
	
	
	
	
	






<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>