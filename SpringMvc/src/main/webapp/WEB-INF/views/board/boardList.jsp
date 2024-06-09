<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>게시판리스트</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
 
<div class="container">
  <h2>게시판리스트</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body">
    	<table class="table table-bordered table-hover">
    		<tr>
    			<td>번호</td>
    			<td>제목</td>
    			<td>작성자</td>
    			<td>작성일</td>
    			<td>조회수</td>
    		<tr>
    		<c:forEach var="list" items="${boardList}">
    			<tr>
    				<td>${list.idx}</td>
    				<td><a href="boardDetail.do?idx=${list.idx}">${list.title}</a></td>
    				<td>${list.writer}</td>
    				<td>${list.indate}</td>
    				<td>${list.count}</td>
    			</tr>
    		</c:forEach>
    	</table>
    	<a href="boardForm.do" class="btn btn-primary btn-sm">글쓰기</a>
    </div>
    <div class="panel-footer">게시판연습</div>
  </div>
</div>

</body>
</html>