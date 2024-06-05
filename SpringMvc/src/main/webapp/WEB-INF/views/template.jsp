<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>게시판</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
  		<h2>Spring MVC</h2>
	  	<div class="panel panel-default">
	    	<div class="panel-heading">BOARD</div>
	    	<div class="panel-body">Panel Content</div>
	    	<div class="panel-footer">게시판연습</div>
	  	</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		// 페이지 로딩 시 바로 실행 되는 함수
	});
	
	/* ajax 기본 포맷 */
	$.ajax({
		url : "", //Controller 매핑 주소
		type : "", //요청방식
		contentType : "application/json;charset=utf-8", //data의 타입을 설정(JSON으로 서버로 넘기겠다는 뜻)
		data : {/* 서버로 전송할 데이터 */},
		dataType : "", //서버로부터 응답 받는 데이터 타입
		success : function(/* 서버로부터 받은 리턴 데이터 */),//ajax 성공시
		error : function(/* 오류정보 */){ //ajax 실패시
			
		}
	});
</script>
</html>
