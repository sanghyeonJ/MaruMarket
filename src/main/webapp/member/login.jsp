<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/header.jsp"></c:import>
<script src="${pageContext.request.contextPath}/js/member/login.js"></script>

<div class="container">
  <div class="inner">
    <div class="loginWrap">
      <div class="loginBox">
        <h2>ログイン</h2>

        <form name="loginForm" id="loginForm">
          <div class="inputGroup">
            <label for="userid">ID</label>
            <input type="text" name="user_id" id="user_id" placeholder="IDを入力してください" data-item-name="ID">
          </div>

          <div class="inputGroup">
            <label for="password">パスワード</label>
            <input type="password" name="user_pw" id="user_pw" placeholder="••••••••" data-item-name="パスワード">
          </div>

          <button type="submit" class="loginBtn" id="btn-login">ログイン</button>
        </form>

        <div class="loginSub">
          <a href="javascript:void(0)">パスワードをお忘れですか？</a>
          <a href="/mem/join.do">新規会員登録</a>
        </div>
      </div>
    </div>

  </div>
</div>


<c:import url="/footer.jsp"></c:import>