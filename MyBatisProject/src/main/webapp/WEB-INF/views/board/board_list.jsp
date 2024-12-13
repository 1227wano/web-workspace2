<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
header.masthead {
	display: none;
}

.row {
	height: 800px;
}

tr:hover {
	cursor: pointer;
}
</style>

<br />
<br />

<jsp:include page="../include/header.jsp" />

<!-- Begin Page Content -->
<div class="container">
	<div class="row">
		<div class="col-lg-1"></div>
		<div class="col-lg-10">
			<div class="panel-body">
				<h2 class="page-header">
					<span style="color: #52b1ff;">KH</span> 자유 게시판

					<!-- 로그인한 회원과 관리자만 게시판을 보이게 
               		-> if not empty loginUser and loginUser.userId.equals('관리자ID') -->
					<a href="enrollForm.board" class="btn float-right"
						style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">글쓰기</a>
				</h2>
				<table class="table table-bordered table-hover">
					<thead>
						<tr
							style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">
							<th width="100">번호</th>
							<th width="150">작성자</th>
							<th width="450">제목</th>
							<th width="200">작성일</th>
							<th width="100">조회수</th>
						</tr>
					</thead>
					<tbody>
						<!-- 게시글 리스트 목록이 출력될 영역 -->
						<!-- 원래는 list가 비어있냐 아니냐는 if문으로 감싸야함 -->
						<c:forEach items="${ list }" var="board">
							<tr style="color: #52b1ff;" class="board" id="${ board.boardNo }">
								<td>${ board.boardNo }</td>
								<td>${ board.boardWriter }</td>
								<td style="color: #52d6ffcc;">${ board.boardTitle }&nbsp;</td>
								<td>${ board.createDate }</td>
								<td>${ board.count }</td>
							</tr>
						</c:forEach>

					</tbody>

				</table>
				<script>
               		$(function(){
               			
               			$('.board').click(e => {
               				
               				// detale.board
               				// console.log(e.currentTarget.id); -> 108, 102...
               				
               				const targetId = e.currentTarget.id;
               				
               				location.href = 'detail.board?boardNo=' + targetId;
               				
               			});
               			
               		})	
               
               </script>
			</div>
			<div id="search-area" class="form-group">
				<form action="search.board" , method="get">
					<input type="hidden" name="currentPage" value="1" /> <select
						class="form-control" name="condition">
						<option value="writer">작성자</option>
						<option value="content">내용</option>
						<option value="title">제목</option>
					</select> <input type="text" name="keyword" class="form-control"
						value="${ keyword }" />
					<button type="submit" class="btn btn-block">검색</button>
				</form>
			</div>
			
			<script>
				// 사용자가 입력한 값 확인하기
				window.onload = function(){
					
					$('option[value="${condition}"]').prop('selected', true);	// 검색 후 셀렉트한 선택지 남기기
					
					const currentUrl = window.location.href;
					
					const urlObj = new URL(currentUrl);
					
					const keyword = urlObj.searchParams.get('keyword');
					
					// console.log(`keyword : \${keyword}`);
					
				}
			
			
			
			</script>

			<div class="paging-area" align="center">

				<c:if test="${ pi.currentPage > 1 }">
					<button class="btn btn-outline-primary" style="color: #52b1ff;"
						onclick="location.href='list.board?currentPage=${ pi.currentPage - 1}'">이전</button>
				</c:if>

				<c:forEach begin="${ pi.startPage }" end="${ pi.endPage }" var="i">

					<c:choose>
						<c:when test="${ empty condition }">

							<button class="btn btn-outline-primary" style="color: #52b1ff;"
								onclick="location.href='list.board?currentPage=${i}'">${i}</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-outline-primary" style="color: #52b1ff;"
								onclick="location.href='search.board?currentPage=${i}&condition=${condition}&keyword=${keyword}'">
								${i}</button>
						</c:otherwise>
					</c:choose>


				</c:forEach>
				<!-- 
				<c:if test="${ pi.currentPage ne pi.maxPage }">
					<button class="btn btn-outline-primary" style="color: #52b1ff;"
						onclick="location.href='search.board?currentPage=${i}&condition=${condition}&keyword=${keyword}'">
						다음</button>
				</c:if>
				-->
			</div>
		</div>
	</div>



</div>



<jsp:include page="../include/footer.jsp" />


