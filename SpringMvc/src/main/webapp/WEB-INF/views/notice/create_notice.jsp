<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        form {
            width: 400px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            margin-left: 42%;
    		margin-top: 12%;
        }
        h1 {
            text-align: center;
            font-size: 2rem;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input[type="text"],
        .form-group textarea {
            width: 100%;
            padding: 8px;
            font-size: 1rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group textarea {
            height: 150px;
            resize: vertical;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .button-group button, .button-group a {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-align: center;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .button-group button:hover, .button-group a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
	<form action="#" id="frm" name="frm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="memId" value="${member.memId}"/>
		<input type="hidden" name="memName" value="${member.memName}"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h1>공지사항</h1>
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title">
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="content"></textarea>
        </div>
        <div class="form-group">
        	<label for="file">파일</label>
        	<input type="file" name="file"/>
        </div>
        <div class="button-group">
            <button type="button" onclick="noticeCreateProcess()">생성하기</button>
            <a href="#" id="noticeList">목록</a>
        </div>
    </form>
</body>
<script type="text/javascript">
	let csrfHeaderName = "${_csrf.headerName}";
	let csrfTokenValue = "${_csrf.token}";


	//전체 버튼 이벤트
	function addButtonEvent() {
		$("#noticeList").click(function(){
			location.href = "noticeMain.do";
		});
	}
	
	//공지사항 검증
	function noticeCreateProcess(){
		let title = $("input[name=title]").val();
		let content = $("#content").val();
		
		if(!title) {
			alert("공지사항 제목을 입력하세요.");
			return false;
		}
		
		if(!content) {
			alert("공지사항 내용을 입력하세요.");
			return false;
		}
		
		noticeCreate();
	}
	
	//공지사항 생성
	function noticeCreate() {
		let frm = $("#frm").serialize();
		
		$.ajax({
			url : "noticeCreate.do",
			type : "post",
			data : frm,
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success : function() {
				alert("공지사항이 등록되었습니다.");
				location.href = "noticeMain.do";
			},
			error : function() {
				alert(error);
			}
		});
	}
	
	//init
	$(function(){
		addButtonEvent();
	});
</script>
</html>