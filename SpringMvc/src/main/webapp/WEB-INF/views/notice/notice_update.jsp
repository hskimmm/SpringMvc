<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
            width: 600px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            padding: 20px;
            margin: 6% auto;
        }
        label {
            font-weight: bold;
        }
        input[type="text"], textarea {
            width: calc(100% - 22px);
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }
        .button-group {
            text-align: center;
            margin-top: 20px;
        }
        .button-group button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-right: 10px;
        }
        .button-group button:hover {
            background-color: #0056b3;
        }
        .cancel-button {
		    background-color: transparent;
		    color: red;
		    border: none;
		    font-size: 1.5em;
		    cursor: pointer;
		}
    </style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>
    <form action="#" id="frm" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="idx" value="${notice.idx}"/>
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<label for="idx">번호</label>
    	<input type="text" id="idx" name="idx" value="${notice.idx}" readonly="readonly"/>
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${notice.title}" required>
        <label for="writer">작성자</label>
        <input type="text" id="writer" name="writer" value="${notice.writer}" readonly="readonly">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="8" required>${notice.content}</textarea>
        <label for="file">파일</label>
        <input type="file" id="file" name="file">
        <span id="originalFileName"><c:if test="${file.deleteYn == 'N'}">${file.savefilename}</c:if></span>
        <button type="button" id="cancelButton" class="cancel-button">✕</button>
        <div class="button-group">
            <button type="submit" onclick="noticeUpdate()">수정</button>
            <button type="button" onclick="window.history.back()">취소</button>
        </div>
    </form>
</body>
<script type="text/javascript">
	//전역 변수
	let fileInput; // 새로운 파일 값
	let deleteYn; // 파일 삭제여부
	let csrfHeaderName = "${_csrf.headerName}";
	let csrfTokenValue = "${_csrf.token}";
	
	//전체 버튼 이벤트 관리
	function addButtonEvent() {
		$("#file").on("change", function(){
			fileInput = $("#file")[0].files[0]; // 새로운 파일 변수에 할당
			$("#originalFileName").text(''); // 새로운 파일 추가 시 기존 파일 없애기
		});
		
		$("#cancelButton").click(function(){
			$("#originalFileName").text('');
			deleteYn = 'Y'; //파일 삭제 시 Y 값 할당
		});
	}

	//공지사항 수정
	function noticeUpdate() {
		var formData = new FormData();
		formData.append("idx", $("#idx").val());
		formData.append("title", $("#title").val());
		formData.append("content", $("#content").val());
		formData.append("file", fileInput);
		
		//파일 삭제 여부 체크
		if(deleteYn == 'Y') {
			formData.append("deleteYn", "Y");
		} else {
			formData.append("deleteYn", "N");
		}
		
		$.ajax({
			url : "noticeUpdate.do",
			type : "post",
			data : formData,
			contentType: false,
            processData: false,
            beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
			},
			success : function() {
				alert("공지사항을 수정하였습니다.");
				location.href = "noticeMain.do";
			},
			error : function() {
				alert("공지사항 수정 진행 중 에러가 발생하였습니다.");
			}
		});
	}
	
	//init
	$(function(){
		addButtonEvent();
	});
</script>
</html>