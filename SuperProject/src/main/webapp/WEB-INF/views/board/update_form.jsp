<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업데이트~~~</title>
<style>

    
    form{
    	width : 80%;
    	margin : auto;
    }
</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">

        <h2 align="center">게시글 수정하기</h2>
        <br><br> 

        <form action="update.board" method="post" id="update-form"
        	  enctype="multipart/form-data">  <!-- 이거 오타나면 못찾는다~~~ -->
        	
        	<input type="hidden" name="boardNo" value="${ board.boardNo }" />
        	<!-- boardNo 몰래몰래 넘겨ㅏㅓㅓ -->
        	
        	<div class="form-group">
	        	<select name="category" class="form-control">
	        		<c:forEach items="${ categoryList }" var="c">
	        			<option value="${ c.categoryNo }" class="${ c.categoryName }">
	        				${ c.categoryName }
	        			</option>
	        		</c:forEach>
	        	</select>
        	</div>

			<script>
				$(function(){
					$('option[class="${board.category}"]').attr('selected', true);
					/*
					${'option'}.each(function(){
						if('${board.category}' === $(this).text().trim()){}
							$(this).attr('selected', true);
					})
					*/
				});
			
			</script>

            <div class="form-group">
                <label for="usr">제목</label>
                <input type="text" class="form-control" id="usr" name="title" value="${ board.boardTitle }">
            </div>

            <div class="form-group">
                <label for="comment">내용</label>
                <textarea class="form-control" name="content" rows="15" id="comment" style="resize:none;">${ board.boardContent }</textarea>
            </div>
            
            <div class="form-group">
            	<input type="file" name="reUpfile">
            	
            	<c:if test="${ not empty attachment }">
            	<!-- 기존 첨부파일이 존재했을 경우 원본파일명 보여주기 -->
            	첨부파일 : <label>${ attachment.originName }</label>
            	
            	<input type="hidden" name="fileNo" value="${ attachment.fileNo }" />
            	<input type="hidden" name="changeName" value="${ attachment.changeName }" /> 
            	<!-- 첨부파일이 수정되었다면 기존파일을 지우기 위해서 바뀐 파일명을 보내주자 -->
            	</c:if>
            </div>

            <div align="center">
                <button type="submit" class="btn btn-sm btn-info">수정하기</button>
                <button type="button" class="btn btn-sm btn-secondary"
                onclick="history.back();">뒤로가기</button>
            </div>

        </form>
        
    </div>
    
	<jsp:include page="../include/footer.jsp" />
    

</body>
</html>