<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- context root 경로를 가지고옴 -->

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}">Home</a></li>
        <li><a href="boardMain.do">게시판</a></li>
        <li><a href="noticeMain.do">공지사항</a></li>
        <!-- <li><a href="#">Page 2</a></li> -->
      </ul>
      <!-- 로그인 X -->
      <c:if test="${empty member}"> <!-- mvo 객체가 비워있을경우 -->
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown">
			  	<a class="dropdown-toggle" data-toggle="dropdown" href="#">접속하기<span class="caret"></span></a>
			  	<ul class="dropdown-menu">
			     	<li><a href="memberLoginForm.do">로그인</a></li>
			     	<li><a href="memJoinForm.do">회원가입</a></li>
			   	</ul>
			</li>
		</ul>
      </c:if>
      <!-- 로그인 O -->
      <c:if test="${!empty member}"> <!-- mvo 객체의 값이 있을 경우 -->
      	<ul class="nav navbar-nav navbar-right">
			<li class="dropdown">
			  	<a class="dropdown-toggle" data-toggle="dropdown" href="#">회원관리<span class="caret"></span></a>
			  	<ul class="dropdown-menu">
			     	<li><a href="memberUpdateForm.do">회원정보수정</a></li>
			     	<li><a href="memberProfileForm.do">프로필사진등록</a></li>
			     	<li><a href="memberLogout.do">로그아웃</a></li>
			   	</ul>
			</li>
		</ul>
      </c:if>
    </div>
  </div>
</nav>