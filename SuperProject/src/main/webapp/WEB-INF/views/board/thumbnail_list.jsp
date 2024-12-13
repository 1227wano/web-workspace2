<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style> 
	.list-area{
		text-align : center;
	}

	.thumbnail{
		box-shadow : 1px 1px 2px #0000002e;
		width : 300px;
		padding : 12px;
		margin : 25px;
		display : inline-block;
		background-color: #ffffffb0;
		color:#000000b0;
		font-weight: bold;
		border-radius: 12px;
	}

	.thumbnail > img{
		width : 270px;
		height : 180px;
		margin-bottom : 8px;
		border : 1px solid rgb(172 205 255 / 57%);
		border-radius: 12px;
	}

	.thumbnail:hover{
		cursor:pointer;
		opacity:0.8;
	}


</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">
		
		<div style="margin-top : 15px; width : 400px; height : 280px; margin:auto;">
			<img width="100%" height="100%" src="https://kh-academy.co.kr/resources/images/main/logo.svg" alt="">
		</div>
		
			<div style="align:right;" >
				<a class="btn btn-sm btn-primary"
				   style="background:rgb(193 229 255); border:none; width:100%"
				   href="enrollForm.thumbnail"
				>글작성</a>
			</div>

		<div class="list-area">
			
				<br>
			<c:choose>
				<c:when test="${ empty list }">
					등록된 게시글이 없습니다.
				</c:when>
				<c:otherwise>	
				<c:forEach items="${ list }" var="board">
				<div class="thumbnail" align="center">
					<input type="hidden" value="${ board.boardNo }" />
					<img src="${ board.imagePath }" alt="이미지">
					<p>
						<label>No. ${ board.boardNo }</label> / <span>${ board.boardTitle }</span> <br>
						<label>조회수</label> : <span>${ board.count }</span>
					</p>
				</div>
				</c:forEach>
				</c:otherwise>
			</c:choose>
			
			<script>
				$('.thumbnail').click(function(){
					
					// 클릭할때마다 => URL로 요청 => location.href
					
					// detail.thumbnail?boardNo=
					// console.log($(this).children().eq(1).children().eq(0).text().split(' ')[1]);
					// ↑ 너무 복잡... 위에서 input hidden만들어서 몰래 boardNo들고오기
					console.log($(this).children().eq(0).val());
					
					const boardNo = $(this).children().eq(0).val();
					
					location.href = 'detail.thumbnail?boardNo=' + boardNo;
				});
			
			</script>
		</div>
	</div>
	
	<jsp:include page="../include/footer.jsp" />


</body>
</html>