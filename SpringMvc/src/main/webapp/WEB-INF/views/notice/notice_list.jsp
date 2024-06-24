<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>공지사항</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style>
		h1 {
            text-align: center;
            font-size: 2rem;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f0f0f0;
            cursor: pointer;
        }
        .create-button {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            text-align: center;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .create-button:hover {
            background-color: #0056b3;
        }
        .table-wrapper {
            position: relative;
        }
        .create-notice-link {
            position: absolute;
            bottom: 10px;
            right: 10px;
            font-size: 0.9rem;
        }
        .create-notice-link a {
            color: #007bff;
            text-decoration: none;
        }
        .create-notice-link a:hover {
            text-decoration: underline;
        }
        /* 테이블 아래 오른쪽에 버튼을 배치하기 위한 스타일 */
        .create-button-container {
            position: relative;
            float: right; /* 오른쪽으로 정렬 */
            margin-top: 20px; /* 버튼과 테이블 사이 여백 조정 */
        }
    </style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<div class="container">
        <h1>공지사항</h1>
        <table>
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="list" items="${noticeList}">
	           		<tr onclick="viewNotice(${list.idx})">
		                <td>${list.idx}</td>
		                <td>${list.title}</td>
		                <td>${list.writer}</td>
		                <td>${list.indate}</td>
		                <td>${list.count}</td>
		            </tr>
            	</c:forEach>
            </tbody>
        </table>
        <div class="create-button-container">
			<a href="#" class="create-button" id="createBtn">공지사항 생성</a>
        </div>
    </div>
</body>
<script type="text/javascript">
	//전체 버튼 이벤트
	function addButtonEvent() {
		$("#createBtn").click(function(){
			location.href = "createNoticeForm.do";
		});
	}
	
	//공지사항 상세 페이지 이동
	function viewNotice(idx) {
		location.href = "noticeDetail.do?idx="+idx;
	}
	
	//init
	$(function(){
		addButtonEvent();
	});
</script>
</html>