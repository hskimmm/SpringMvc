<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>회원정보수정</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style type="text/css">
  	table {
  		text-align: center;
  		border: 1px solid #dddddd;
  	}
  	
  	table td:first-child {
  		width: 110px;
  		vertical-align: middle;
  	}
  	
  	.lastTd {
		width: 110px;
	}
	
	.genderDiv {
		text-align: center;
		margin: 0 auto;
	}
	
	.btnTd {
		text-align: left;
	}
	
	.spanMessage {
		color: red;
	}
  </style>
</head>
<body>
	<div class="container">
	<jsp:include page="../common/header.jsp"/>
		<h2>회원정보수정</h2>
		<div class="panel panel-default">
			<div class="panel-heading">회원정보수정</div>
			<div class="panel-body">
				<form action="${contextPath}/memberUpdate.do" method="post">
					<input type="hidden" id="memId" name="memId" value="${member.memId}"/>
					<input type="hidden" id="memPassword" name="memPassword" value="memPassword" value=""/>
					<table class="table table-bordered">
						<tr>
							<td>아이디</td>
							<td>${member.memId}</td>
						</tr>
						<tr>
							<td>비밀번호</td>
							<td colspan="2"><input type="password" class="form-control" id="memPwd" name="memPwd" maxlength="20" placeholder="비밀번호를 입력하세요." onkeyup="passwordCheck()"/></td>
						</tr>
						<tr>
							<td>비밀번호확인</td>
							<td colspan="2"><input type="password" class="form-control" id="memPwdOk" name="memPwdOk" maxlength="20" placeholder="비밀번호를 입력하세요." onkeyup="passwordCheck()"/></td>
						</tr>
						<tr>
							<td>이름</td>
							<td colspan="2"><input type="text" class="form-control" id="memName" name="memName" maxlength="20" placeholder="이름을 입력하세요." value="${member.memName}"/></td>
						</tr>
						<tr>
							<td>나이</td>
							<td colspan="2"><input type="text" class="form-control" id="memAge" name="memAge" maxlength="20" placeholder="나이를 입력하세요." value="${member.memAge}"/></td>
						</tr>
						<tr>
							<td>성별</td>
							<td colspan="2">
								<div class="form-group genderDiv">
									<div class="btn-group" data-toggle="buttons">
										<label class="btn btn-primary <c:if test="${member.memGender eq 'male'}">active</c:if>">
											<input type="radio" autocomplete="off" id="memGender" name="memGender" value="male" <c:if test="${member.memGender eq 'male'}">checked</c:if> />남자
										</label>
										<label class="btn btn-primary <c:if test="${member.memGender eq 'female'}">active</c:if>">
											<input type="radio" autocomplete="off" id="memGender" name="memGender" value="female" <c:if test="${member.memGender eq 'female'}">checked</c:if>/>여자
										</label>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>이메일</td>
							<td colspan="2"><input type="email" class="form-control" id="memEmail" name="memEmail" maxlength="20" placeholder="이메일을 입력하세요." value="${member.memEmail}"/></td>
						</tr>
						<tr>
							<td colspan="3">
								<span class="spanMessage" id="pwdCheckMessage"></span>
							</td>
						</tr>
						<tr>
							<td colspan="3" class="btnTd">
								<input type="submit" class="btn btn-primary btn-sm pull-right" value="수정"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- 회원정보 실패 시 modal -->
			<div id="myModal2" class="modal fade" role="dialog">
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
			<div class="panel-footer">회원정보수정</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//비밀번호 중복체크
	function passwordCheck(){
		let memberPwd = $("#memPwd").val();
		let memberPwdOk = $("#memPwdOk").val();
		
		//비밀번호,비밀번호체크 폼 체크
		if(memberPwd != memberPwdOk) {
			$("#pwdCheckMessage").text("비밀번호가 일치하지 않습니다.");
		} else {
			$("#pwdCheckMessage").text("비밀번호가 일치합니다.");
			$("#memPassword").val(memberPwd);
		}
	}
	
	$(document).ready(function(){
		//회원정봇수정 처리유무 모달 처리
		if(${!empty messageType}){
			$("#myModal2").modal("show");
		}
	});
	
</script>
</html>
