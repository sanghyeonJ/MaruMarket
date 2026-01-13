<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/header.jsp"></c:import>
<link href="${pageContext.request.contextPath}/css/swiper-bundle.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/swiper-bundle.min.js"></script>
<div class="container">
  <div class="inner productDetail">

    <!-- 이미지 영역 -->
    <div class="detailImageArea">
      <div class="swiper productSwiper">
        <div class="swiper-wrapper">
	    <c:choose>
	      <c:when test="${not empty product.images}">
	        <c:forEach var="imgName" items="${product.images}">
	          <div class="swiper-slide">
	            <img src="/upload/maru/${imgName}">
	          </div>
	        </c:forEach>
	      </c:when>
	    </c:choose>
	  </div>

        <!-- 페이징 -->
        <div class="swiper-pagination"></div>
      </div>
    </div>

    <!-- 정보 영역 -->
    <div class="detailInfoArea">
      <div class="statusBadge status-${product.status }">${product.status }</div>
      <h2 class="title">${product.title }</h2>
      <p class="price">${product.price }円</p>
      <div class="infoBox">
        <dl>
          <dt>カテゴリー</dt>
          <dd>${product.categoryName }</dd>
        </dl>
        <dl>
          <dt>登録日</dt>
          <dd><fmt:formatDate value="${product.regdate}" pattern="yyyy-MM-dd HH:mm" /></dd>
        </dl>
        <dl>
          <dt>出品者</dt>
          <dd>${product.sellerId}</dd>
        </dl>
      </div>
      <div class="subInfo">
        <dl>
          <dt>chat</dt>
          <dd>2</dd>
        </dl>
        <dl>
          <dt>like</dt>
          <dd>${likeCount}</dd>
        </dl>
        <dl>
          <dt>view</dt>
          <dd>${product.viewCount}</dd>
        </dl>
      </div>
      <div class="btnArea">
        <c:choose>
          <c:when test="${isOwner}">
            <button class="btnDelete" onclick="deleteProduct(${product.productId})">削除</button>
            <button class="btnEdit" onclick="location.href='/prod/edit.do?productId=${product.productId}'">修整</button>
          </c:when>
          <c:otherwise>
            <button class="btnLike ${isLiked ? 'on' : ''}" onclick="toggleLike(${product.productId})">
              <img src="${pageContext.request.contextPath}/images/ico_like${isLiked ? '_full' : ''}.svg">お気に入り
            </button>
            <button class="btnBuy ${product.status == 'SOLD' ? 'btnDisabled' : ''} ">購入する</button>
          </c:otherwise>
        </c:choose>
      </div>

    </div>

    <!-- 설명 -->
    <div class="detailContent">
      <h3>商品説明</h3>
      <p>
        ${product.content}
      </p>
    </div>

  </div>
</div>

<script>
  new Swiper(".productSwiper", {
    loop: true,
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
  });
  

  function toggleLike(pid) {
      if("${empty loginUser}" == "true") {
          alert("ログインが必要なサービスです");
          goPage('/mem/login.do');
          return;
      }
      
      fetch('/prod/toggleLike.do?productId=' + pid)
          .then(res => res.json())
          .then(data => {
              if(data.result == -1) {
                  alert("ログイン セッションが期限切れです。");
              } else if(data.result == 1) {
                  // 찜 추가 성공
                  document.querySelector(".btnLike img").setAttribute("src", "${pageContext.request.contextPath}/images/ico_like_full.svg");
                  // 찜 수 증가
                  var likeCountEl = document.querySelector(".subInfo dl:nth-child(2) dd");
                  var currentCount = parseInt(likeCountEl.textContent) || 0;
                  likeCountEl.textContent = currentCount + 1;
                  alert("お気に入りリストに追加されました。");
              } else if(data.result == 0) {
                  // 찜 삭제 성공
                  document.querySelector(".btnLike img").setAttribute("src", "${pageContext.request.contextPath}/images/ico_like.svg");
                  // 찜 수 감소
                  var likeCountEl = document.querySelector(".subInfo dl:nth-child(2) dd");
                  var currentCount = parseInt(likeCountEl.textContent) || 0;
                  likeCountEl.textContent = Math.max(0, currentCount - 1);
                  alert("お気に入りリストから削除されました");
              }
          })
          .catch(err => console.log("エラー発生:", err));
  }
  
  function deleteProduct(pid) {
      if(confirm("本当に削除しますか？")) {
          fetch('/prod/delete.do?productId=' + pid, {
              method: 'POST'
          })
          .then(res => res.json())
          .then(data => {
              if(data.result === 'success') {
                  alert("商品が削除されました。");
                  location.href = '/main';
              } else {
                  alert("削除に失敗しました。");
              }
          })
          .catch(err => {
              console.log("エラー発生:", err);
              alert("削除中にエラーが発生しました。");
          });
      }
  }
</script>


<c:import url="/footer.jsp"></c:import>