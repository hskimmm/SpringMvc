<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->
<!DOCTYPE html>
<html lang="en">
<head>
  <title>메인페이지</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style type="text/css">
  	.index-image-main {
  		width: 100%;
  		height: 400px;
  	}
  	
  	.index-image-basic {
  		width: 50px;
  		height: 50px;
  	}
  	
  	.index-image-member {
  		width: 50px;
  		height: 50px;
  	}
  </style>
</head>
<body>
	<div class="container">
		<jsp:include page="common/header.jsp"/>  <!-- header.jsp 페이지를 불러온다. -->
		<c:choose>
			<c:when test="${!empty member}">
				<h3>
					<c:if test="${member.memProfile ne null}"><img class="index-image-member img-circle" src="resources/upload/${member.memProfile}" alt="유저 이미지"></c:if>
					${member.memName}님 방문을 환영합니다.
				</h3>
			</c:when>
			<c:otherwise>
				<h5>
					<c:if test="${member.memProfile eq null}"><img class="index-image-basic img-circle" src="resources/images/urbanbrush-20210105113834473495.jpg" alt="기본 이미지"></c:if>
					로그인하세요.	
				</h5>
			</c:otherwise>	
		</c:choose>
		<div class="panel panel-default">
			<div class="panel-heading">
				<img class="index-image-main" src="resources/images/pngtree-three-puppies-with-their-mouths-open-are-posing-for-a-photo-image_2902292.jpg"/>
			</div>
			<div class="panel-body">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#home">Home</a></li>
					<li><a data-toggle="tab" href="#menu1">게시판</a></li>
					<li><a data-toggle="tab" href="#menu2">공지사항</a></li>
				</ul>
				<div class="tab-content">
					<div id="home" class="tab-pane fade in active">
						<h3>HOME</h3>
						<p>Some content.</p>
						</div>
						<div id="menu1" class="tab-pane fade">
						<h3>게시판</h3>
						<p>Some content in menu 1.</p>
						</div>
						<div id="menu2" class="tab-pane fade">
						<h3>공지사항</h3>
						<p>Some content in menu 2.</p>
					</div>
				</div>
			</div>
			<div class="panel-footer">Panel Footer</div>
		</div>
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