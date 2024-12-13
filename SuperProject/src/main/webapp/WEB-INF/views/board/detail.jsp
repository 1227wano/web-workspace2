<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<br/><br/>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">
		<div class="container">
		
		<div class="row">
		  <div class="offset-lg-2 col-lg-8">
		    <div class="card">
		      <div class="card-header text-white" style="background-color: #52b1ff;">${ board.boardNo }번 게시물 내용</div>
		      <div class="card-body"> 
		
		          <div class="form-group">
		            <label>카테고리</label><br>
		            <span>${ board.category }</span>
		          </div>      
		        
		          <div class="form-group">
		            <label>작성자</label>
		            <input type="text" class="form-control" name='writer' value="${ board.boardWriter }" readonly>
		          </div>
		          
		          <div class="form-group">
		            <label>제목</label>
		            <input type="text" class="form-control" name='title' value="${ board.boardTitle }" readonly>
		          </div>
		
		          <div class="form-group">
		            <label>내용</label>
		            <textarea class="form-control" rows="5" name='content' readonly style="resize:none;">${ board.boardContent }</textarea>
		          </div>
		
		          <div class="form-group">
		            <label>첨부파일</label>
		            <c:choose>
						<c:when test="${ attachment ne null }">
			            	<!-- 첨부파일은 있을수도있음 -->
			            	<!-- 
			            	resources/board_upfiles/바뀐이름
			            	-->
			            	<a 
			            	download="${ attachment.originName }"
			            	href="${ attachment.filePath }/${ attachment.changeName }"
			            	>${ attachment.originName }</a><br>
			            	
			            	<img src="${ attachment.filePath }/${ attachment.changeName }" width="360" height="200"/>
			            </c:when>
						<c:otherwise>
			            	<!-- 첨부파일은 없을수도있음 -->
			            	&nbsp;&nbsp;<span>첨부파일이 존재하지 않습니다.</span>
			            </c:otherwise>
			            	
		          	</c:choose>
		          </div>
		         
		          <a class="btn" href="list.board?currentPage=1"
		             style="background-color: #52b1ff; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
		          >목록</a>&nbsp;&nbsp;
		          
		          <!-- 
		          식별값 : MEMBER -> PK(USER_NO), UNIQUE(UESR_ID)
		          게시글 작성자 : BOARD VO -> boardWriter(USER_ID) 이 두가지를 비교하면 로그인해서 작성한 사람을 판별가능
		           -->

		          <c:if test="${ board.boardWriter eq loginUser.userId }">
			          <a 
			            class="btn" 
			            href="updateForm.board?boardNo=${ board.boardNo }"
			      		style="background-color: orange; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
			      		>수정</a>&nbsp;&nbsp;
			          
			          <a 
			            class="btn" 
			            href="delete.board?boardNo=${ board.boardNo }" onclick="return confirm('정말로 삭제하시겠습니까?')"
			      		style="background-color: red; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
			      		>삭제</a>&nbsp;&nbsp;
			      	</c:if>	
	
		      </div>
		    </div>
		  </div>
		</div>
		</div>
		<div id="reply-area">
			
			<table class="form-group" align="center">
				<thead>
					<tr>
						<th>댓글작성</th>
							
							<c:if test="${ sessionScope.loginUser ne null }">
								<td>
									<textarea id="replyContent" cols="50" rows="3" style="resize:none;" class="form-control"></textarea>
								</td>
								<td><button onclick="insertReply();" class="btn" style="width : 100%; height : 100%; background-color: #52b1ff; color : white;">댓글등록</button></td>
							</c:if>
							<c:if test="${ sessionScope.loginUser eq null }">
								<td>
									<textarea readonly cols="50" rows="3" style="resize:none;" class="form-control">로그인 후 이용가능한 서비스입니다.</textarea>
								</td>
								<td><button class="btn" style="width : 100%; height : 100%; background-color: #52b1ff; color : white;">댓글등록</button></td>
							</c:if>
					</tr>
				</thead>
				<tbody>
					<!-- 여기에 출력할거임 -->
					
					
					
				</tbody>
			</table>
			<br><br><br><br>
	    </div>

	</div>
	
	<jsp:include page="../include/footer.jsp" />
	
	<script>
		
		function selectReplyList(){
			
			$.ajax({
				url : 'list.reply',
				type : 'get',
				data : {
					boardNo : ${ board.boardNo }
				},
				success : function(list){
					
					// console.log(list);
					
					 const result = list.map(e => `<tr>`
												+ `<td>\${e.replyWriter}</td>`
												+ `<td>\${e.replyContent}</td>`
												+ `<td>\${e.createDate}</td>`
												+ `</tr>`).join('');
					$('#reply-area tbody').html(result);
				}
			});
		}
		
		$(function(){
			selectReplyList();
		});
		
		function insertReply(){
			
			$.ajax({
				url : 'insert.reply',
				type : 'post',
				data : {
					content : $('#replyContent').val(),
					boardNo : ${board.boardNo}
				},
				success : function(result) {
					//console.log(result);
					
					if(result === 'success'){
						$('#replyContent').val('');
						selectReplyList();
					}
					
				}
				
				
			})
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	</script>
	
	
	
</body>
</html>