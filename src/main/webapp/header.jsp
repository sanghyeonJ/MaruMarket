<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MARUMARKET</title>
<link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.cookie.min.js"></script>
<script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>
<body>
  <header>
    <div class="inner">
      <div class="headerWrap">
        <div class="logoBox">
          <a href="/main">MARUMARKET</a>
        </div>
        <div class="menuBox">
          <c:choose>
            <c:when test="${empty sessionScope.user_id }">
              <button type="button" onclick="goPage('/mem/login.do')"><img src="${pageContext.request.contextPath}/images/ico_login.svg" alt="ログイン">ログイン</button>
            </c:when>
            <c:otherwise>
              <button type="button"><img src="${pageContext.request.contextPath}/images/ico_chat.svg" alt="チャット">チャット</button>
              <button type="button" onclick="goPage('/prod/insert.do')"><img src="${pageContext.request.contextPath}/images/ico_plus.svg" alt="売る">売る</button>
              <ul>
                <li class="drop">
                    <p class="dropTit"><img src="${pageContext.request.contextPath}/images/ico_user.svg" alt="マイページ">マイページ</p>
                    <ul class="dropmenu">
                        <li><button type="button" onclick="goPage('/my/like.do')">お気に入り</button></li>
                        <li><button type="button" onclick="goPage('/my/buy.do')">購買内訳</button></li>
                        <li><button type="button" onclick="goPage('/my/sell.do')">販売内訳</button></li>
                    </ul>
                </li>
              </ul>
              <button type="button" onclick="goPage('/mem/logout.do')"><img src="${pageContext.request.contextPath}/images/ico_logout.svg" alt="ログアウト">ログアウト</button>
            </c:otherwise>
          </c:choose>
          
        </div>
      </div>
    </div>
  </header>