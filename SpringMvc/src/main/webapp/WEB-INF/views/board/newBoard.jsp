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
	<style>
		.insert-body {
			display:none;
		}
		
		.detail-tr {
			display:none;
		}
	</style>
</head>
<body>
	<div class="container">
	<jsp:include page="../common/header.jsp"/>
  		<h2>게시판</h2>
	  	<div class="panel panel-default">
	    	<div class="panel-heading">BOARD</div>
	    	<div class="panel-body" id="newBoardListView"></div>
	    	<div class="panel-body insert-body" id="newBoardWriteForm">
	    		<form id="frm">
			    	<table class="table">
			    		<tr>
			    			<td>제목</td>
			    			<td><input type="text" id="title" name="title" class="form-control"></td>
			    		</tr>
			    		<tr>
			    			<td>내용</td>
			    			<td><textarea rows="7" class="form-control" id="content" name="content"></textarea></td>
			    		</tr>
			    		<tr>
			    			<td>작성자</td>
			    			<td><input type="text" class="form-control" id="writer"name="writer"></td>
			    		</tr>
			    		<tr>
			    			<td colspan="2" align="center">
			    				<button type="button" class="btn btn-success btn-sm" onclick='goNewBoardInsert()'>등록</button>
			    				<button type="reset" class="btn btn-warning btn-sm">취소</button>
			    				<button type="button" class="btn btn-info btn-sm" onclick='goNewBoardList()'>목록</button>
			    			</td>
			    		</tr>
			    	</table>
		    	</form>
	    	</div>
	    	<div class="panel-footer">게시판연습</div>
	  	</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		loadNewBoardList();
	});
	
	//게시판리스트 불러오기
	function loadNewBoardList(){
		$.ajax({
			url : "board/all", //Controller 매핑 주소
			type : "get", //요청방식
			dataType : "json", //서버로부터 응답 받는 데이터 타입
			success : makeNewBoardListView,//ajax 성공시
			error : function(){ //ajax 실패시
				alert("error");
			}
		});
	}
	
	//콜백함수
	//ajax 데이터를 받아와서 게시판 리스트를 만드는 메서드
	function makeNewBoardListView(data){
		//data 파라미터에는 Controller의 메서드에서 리턴한 JSON 데이터가 담겨져 있다.
		//data -> [{},{},{}] 형태
		let boardListHtml = "<table class='table table-bordered'>";
		boardListHtml += "<tr>";
		boardListHtml += "<td>번호</td>";
		boardListHtml += "<td>제목</td>";
		boardListHtml += "<td>작성자</td>";
		boardListHtml += "<td>작성일</td>";
		boardListHtml += "<td>조회수</td>";
		boardListHtml += "</tr>";
		
		$.each(data, function(index, obj){
			//게시판 리스트
			boardListHtml += "<tr>";
			boardListHtml += "<td>" + obj.idx + "</td>";
			boardListHtml += "<td id='t"+obj.idx+"'><a href='javascript:goNewBoardDetail("+obj.idx+")'>" + obj.title + "</a></td>";
			boardListHtml += "<td>" + obj.writer + "</td>";
			boardListHtml += "<td>" + obj.indate + "</td>";
			boardListHtml += "<td id='newBoardCount"+obj.idx+"'>" + obj.count + "</td>";
			boardListHtml += "</tr>";
			
			//게시판 상세
			boardListHtml += "<tr class='detail-tr' id='newBoardContentView"+obj.idx+"'>";
			boardListHtml += "<td>내용</td>";
			boardListHtml += "<td colspan='4'>";
			boardListHtml += "<textarea rows='7' class='form-control' id='ta"+obj.idx+"' readonly></textarea>";
			boardListHtml += "<br/>";
			boardListHtml += "<span id='updateBtn"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goNewBoardUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp";
			boardListHtml += "<button class='btn btn-warning btn-sm' onclick='goNewBoardDelete("+obj.idx+")'>삭제</button>&nbsp";
			boardListHtml += "<button class='btn btn-info btn-sm' onclick='loadNewBoardList()'>목록</button";
			boardListHtml += "</td>";
			boardListHtml += "</tr>";
		});
		
		boardListHtml += "<tr>"
		boardListHtml += "<td colspan='5'>"
		boardListHtml += "<button class='btn btn-primary btn-sm' onclick='newBoardWriteForm()'>글쓰기</button>"
		boardListHtml += "</td>"
		boardListHtml += "</tr>"
		boardListHtml += "</table>";
		
		//게시판글쓰기 폼 block 처리
		$("#newBoardListView").css("display", "block");
		$("#newBoardWriteForm").css("display", "none");
		
		$("#newBoardListView").html(boardListHtml);
	}
	
	//게시판글쓰기
	function newBoardWriteForm(){
		$("#newBoardListView").css("display", "none"); //기존 게시판리스트 숨김
		$("#newBoardWriteForm").css("display", "block"); //글쓰기 FORM 보이기
	}
	
	//게시판글쓰기 - 목록 버튼 클릭 시
	function goNewBoardList(){
		$("#newBoardListView").css("display", "block");
		$("#newBoardWriteForm").css("display", "none");
	}
	
	//게시판글쓰기 - 등록 버튼 클릭 시
	function goNewBoardInsert(){
		//폼 안의 데이터를 직렬화(한꺼번에 가지고 오는 방법)
		let formData = $("#frm").serialize();
		console.log(formData);
		
		//서버로 입력 받은 데이터를 넘김
		$.ajax({
			url : "board/new",
			type : "post",
			data : formData,
			success : function(){
				loadNewBoardList();
				
				//기존 입력 받은 폼 초기화
				$("#frm")[0].reset();
			},
			error : function(){
				alert(error);
			}
		});
	}
	
	//게시판상세보기
	function goNewBoardDetail(idx){
		//특정 게시판 내용 노출 유무
		if($("#newBoardContentView"+idx).css("display") == "none"){
			//게시판 내용을 서버로부터 가져오기
			$.ajax({
				url : "board/"+idx,
				type : "get",
				dataType : "json",
				success : function(data){
					$("#ta"+idx).val(data.content);
					
					//조회수 증가
					$.ajax({
						url : "board/count/"+idx,
						type : "put",
						dataType : "json",
						success : function(data){
							$("#newBoardCount"+idx).text(data.count);
						},
						error : function(){
							alert("error");
						}
					});
				},
				error : function(){
					alert("error");
				}
			});
			
			$("#newBoardContentView"+idx).css("display", "table-row"); // 특정 게시판의 내용 보이기
			$("#ta"+idx).attr("readonly", true); // 다시 클릭시 readonly 적용
		}else{
			$("#newBoardContentView"+idx).css("display", "none"); // 특정 게시판의 내용 숨기기
		}
		
	}
	
	//게시판수정폼이동
	function goNewBoardUpdateForm(idx){
		$("#ta"+idx).attr("readonly", false);
		let title = $("#t"+idx).text(); // 제목 값 가져오기
		
		let newInput = "<input type='text' class='form-control' id='updateTitle"+idx+"' value='"+title+"'/>"; //input 새로 만들어서 기존 제목 값 넣어줌
		$("#t"+idx).html(newInput); // 기존 제목 a태그를 지우고 위에 만든 input 태그를 추가
		
		let newButton = "<button class='btn btn-info btn-sm' onclick='goNewBoardUpdate("+idx+")'>수정</button>" //수정화면 버튼을 수정 버튼으로 변경
		$("#updateBtn"+idx).html(newButton);
	}
	
	//게시판수정
	function goNewBoardUpdate(idx){
		let title = $("#updateTitle"+idx).val();
		let content = $("#ta"+idx).val();
		
		$.ajax({
			url : "board/update",
			type : "put",
			contentType : "application/json;charset=utf-8", //data의 타입을 설정(JSON으로 서버로 넘기겠다는 뜻)
			data : JSON.stringify({"title": title, "content" : content, "idx" : idx}), //JSON.stringify(데이터) -> 데이터가 JSON 형태로 변환됨
			success : loadNewBoardList,
			error : function(){
				alert("error");
			}
		});
	}
	
	//게시판삭제
	function goNewBoardDelete(idx){
		$.ajax({
			url : "board/"+idx,
			type : "delete",
			success : loadNewBoardList,
			error : function(){
				alert("error");
			}
		});
	}
	
	/* 
		하나의 Jsp로 게시판 CRUD를 해봤는데 게시판의 특정 idx를 "+obj.idx+" 로 코드에 넣어서 사용하는게
		코드가 복잡해지고 다른사람이 볼때 이해를 못할것 같다.
		페이지를 더 만들더라도 컨트롤러를 통해 가져오는게 더 코드가 깔끔하고 이해하기 쉬울꺼 같음.
	*/

</script>
</html>
