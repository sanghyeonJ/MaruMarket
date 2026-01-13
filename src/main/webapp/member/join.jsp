<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/header.jsp"></c:import>
<script src="${pageContext.request.contextPath}/js/member/join.js"></script>

<div class="container">
  <div class="signupWrap">
    <h2 class="signupTitle">新規会員登録</h2>
    <p class="signupDesc">MARUMARKETをご利用いただくために、以下の情報を入力してください。</p>

    <form class="signupForm" name="joinForm" id="joinForm">
      
      <div class="formGroup">
        <label for="user_id">ユーザーID</label>
        <input type="text" placeholder="IDを入力してください" name="user_id" id="user_id" data-checked="N" data-item-name="ID">
        <p class="inputMsg userid-msg"></p>
      </div>

      <div class="formGroup">
        <label for="user_pw">パスワード</label>
        <input type="password" placeholder="パスワードを入力してください" name="user_pw" id="user_pw">
      </div>

      <div class="formGroup">
        <label for="user_pw_check">パスワード（確認）</label>
        <input type="password" placeholder="パスワードをもう一度入力してください" name="user_pw_check" id="user_pw_check" data-checked="N" data-item-name="パスワード">
        <p class="inputMsg pw-msg"></p>
      </div>

      <div class="formGroup">
        <label for="user_name">名前</label>
        <input type="text" placeholder="名前を入力してください" name="user_name" id="user_name" data-checked="N" data-item-name="名前">
      </div>
      
      <div class="formGroup">
        <label for="email">メールアドレス</label>
        <input type="email" placeholder="example@mail.com" name="email" id="email" data-checked="N" data-item-name="メールアドレス">
      </div>

      <button type="submit" class="btnSignup" id="btn-join">登録する</button>
    </form>

    <p class="loginLink">
      すでにアカウントをお持ちの方は
      <a href="/mem/login.do">ログイン</a>
    </p>
  </div>
</div>

<c:import url="/footer.jsp"></c:import>
