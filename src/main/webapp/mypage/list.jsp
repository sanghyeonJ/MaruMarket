<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header.jsp"></c:import>
<script src="${pageContext.request.contextPath}/js/mypage/mypage.js"></script>

<div class="container">
  <div class="inner">
    
    <div class="contentWrap">
      <div class="categoryArea">
        <h3>MyPage</h3>
        <div class="categoryList">
            <button type="button" class="categoryBtn ${menuType eq 'LIKE' ? 'active' : ''}" onclick="goPage('/my/like.do')">お気に入り</button>
            <button type="button" class="categoryBtn ${menuType eq 'BUY' ? 'active' : ''}" onclick="goPage('/my/buy.do')">購買内訳</button>
            <button type="button" class="categoryBtn ${menuType eq 'SELL' ? 'active' : ''}" onclick="goPage('/my/sell.do')">販売内訳</button>
        </div>
      </div>
      <div class="productArea">
        <div class="productList">
          <c:import url="${pageContext.request.contextPath}/mainListItem.jsp"></c:import>
        </div>
        
        <button type="button" class="btnLoadMore" id="btnLoadMore" style="${productList.size() < 4 ? 'display:none;' : ''}">もっと見る</button>
      </div>
    </div>
    
  </div>
</div>

<c:import url="/footer.jsp"></c:import>