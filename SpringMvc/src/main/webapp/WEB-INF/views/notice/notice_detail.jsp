<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>공지사항</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<style>
        table {
            width: 600px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-collapse: collapse;
            margin-bottom: 20px;
            margin-left: 36%;
    		margin-top: 6%;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .button-group {
            text-align: center;
            margin-left : 780px;
        }
        .button-group a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-align: center;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-right: 10px;
        }
        .button-group a:hover {
            background-color: #0056b3;
        }
        textarea {
            width: 800px;
            height: 500px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            resize: vertical;
        }
    </style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<table>
        <tr>
            <th colspan="2">공지사항 상세 정보</th>
        </tr>
        <tr>
        	<td>번호</td>
        	<td>${notice.idx}</td>
        </tr>
        <tr>
            <td>제목</td>
            <td>${notice.title}</td>
        </tr>
        <tr>
            <td>작성자</td>
            <td>${notice.writer}</td>
        </tr>
        <tr>
            <td>내용</td>
            <td><textarea readonly="readonly">${notice.content}</textarea></td>
        </tr>
        <tr>
        	<td>파일</td>
        	<td>
        		<c:if test="${!empty file}">
        			<a href="noticeFileDownload.do?filename=${file.savefilename}"><c:if test="${file.deleteYn == 'N'}">${file.filename}</c:if></a>
        		</c:if>
       		</td>
        </tr>
        <tr>
            <td>작성일</td>
            <td>${notice.indate}</td>
        </tr>
        <tr>
        	<td>조회수</td>
        	<td>${notice.count}</td>
        </tr>
    </table>
   	<div class="button-group">	
   		<c:if test="${noticeAuth == 'ROLE_ADMIN'}">
   			<a href="#" onclick="noticeUpdate(${notice.idx})">수정</a>
        	<a href="#" onclick="noticeDelete(${notice.idx})">삭제</a>
   		</c:if>
        <a href="#" id="noticeList">목록</a>
    </div>
</body>
<script type="text/javascript">
	let csrfHeaderName = "${_csrf.headerName}";
	let csrfTokenValue = "${_csrf.token}";
	//전체 버튼 이벤트 관리
	function addButtonEvent(){
		//목록
		$("#noticeList").click(function(){
			location.href = "noticeMain.do";
		});
	}
	
	//수정 페이지 이동
	function noticeUpdate(idx) {
		location.href = "noticeUpdateForm.do?idx="+idx;
	}
	
	//공지사항 삭제
	function noticeDelete(idx) {
		$.ajax({
			url : "noticeDelete.do",
			type : "get",
			data : {"idx" : idx},
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success : function(){
				alert("공지사항을 삭제하였습니다.");
				location.href = "noticeMain.do";
			},
			error : function() {
				alert("공지사항 삭제 중 에러가 발생하였습니다.");
			}
		});
	}

	//init
	$(function(){
		addButtonEvent();
	});
</script>
</html>
