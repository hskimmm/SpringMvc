<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>프로필사진등록</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<jsp:include page="../common/header.jsp"/>
	<h2>프로필사진등록</h2>
	<div class="panel panel-default">
	<div class="panel-heading">프로필사진등록</div>
	<div class="panel-body">
		<!-- 폼으로 파일을 처리할떄에는 enctype="multipart/form-data" 타입을 지정해줘야한다.  -->
		<form action="memberProfileUpdate.do?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
			<input type="hidden" id="memId" name="memId" value="${member.memId}"/>
			<table class="table table-bordered">
				<tr>
					<td>아이디</td>
					<td>${member.memId}</td>
				</tr>
				<tr>
					<td>사진업로드</td>
					<td colspan="2">
						이미지를 업로드하세요.
						<input type="file" name="memProfile"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="btnTd">
						<input type="submit" class="btn btn-primary btn-sm pull-right" value="등록"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 사진업로드 유무 처리 -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">${messageType}</h4>
				</div>
				<div class="modal-body">
					<p id="loginCheck">${message}</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer">프로필사진등록</div>
	</div>
</div>
</body>
<script>
	$(document).ready(function(){
		if(${!empty messageType}){
			$("#myModal").modal("show");
		}
	});
</script>
</html>
