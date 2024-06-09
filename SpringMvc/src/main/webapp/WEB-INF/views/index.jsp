<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>메인페이지</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="common/header.jsp"/>  <!-- header.jsp 페이지를 불러온다. -->
		<c:choose>
			<c:when test="${!empty member}">
				<h3>${member.memName}님 방문을 환영합니다.</h3>
			</c:when>
			<c:otherwise>
				<h3>로그인X</h3>
			</c:otherwise>	
		</c:choose>
		
		<!-- 회원가입 성공 시 modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">${messageType}</h4>
					</div>
					<div class="modal-body">
						<p id="joinYnMessage">${message}</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">	
	$(document).ready(function(){
		//회원가입 성공 시 모달 처리
		if(${!empty messageType}) {
			$("#myModal").modal("show");
		}
	});
</script>
</html>