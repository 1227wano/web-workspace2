<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공용 실패 페이지</title>
</head>
<body>

	<jsp:include page="../include/header.jsp" /> <!-- ..이 상위폴더! 여기선 views가 됨 -->

	<h1 style="color:red; margin:50px;">${ failMsg }</h1>

	<jsp:include page="../include/footer.jsp" />

	
	
	

</body>
</html>