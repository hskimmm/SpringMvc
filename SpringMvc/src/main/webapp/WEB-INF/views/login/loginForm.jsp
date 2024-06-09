<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>로그인</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<jsp:include page="../common/header.jsp"/>
	<h2>로그인</h2>
	<div class="panel panel-default">
	<div class="panel-heading">로그인화면</div>
	<div class="panel-body">
		<form action="memberLogin.do" method="post">
			<table class="table table-bordered">
				<tr>
					<td>아이디</td>
					<td><input type="text" class="form-control" id="memId" name="memId" maxlength="20" placeholder="아이디를 입력하세요."/></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td colspan="2"><input type="password" class="form-control" id="memPassword" name="memPassword" maxlength="20" placeholder="비밀번호를 입력하세요."/></td>
				</tr>
				<tr>
					<td colspan="2" class="btnTd">
						<input type="submit" class="btn btn-primary btn-sm pull-right" value="로그인"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 로그인 유무 처리 -->
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
	<div class="panel-footer">로그인화면</div>
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
