<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header.jsp"></c:import>
<script src="${pageContext.request.contextPath}/js/product/edit.js"></script>
<div class="container">
  <div class="productWriteWrap">
    <h2 class="writeTitle">商品を修正する</h2>
    <p class="writeDesc">商品の情報を入力してください。</p>

    <form class="productForm" id="productForm" method="post" action="/prod/editAction.do" enctype="multipart/form-data">
        <input type="hidden" name="productId" value="${product.productId}">
      <!-- status -->
      <div class="formGroup">
        <label for="status">状態</label>
        <select name="status" id="status">
            <option value="SELL" ${product.status eq 'SELL' ? 'selected' : ''}>販売中</option>
            <option value="RESERVE" ${product.status eq 'RESERVE' ? 'selected' : ''}>予約中</option>
            <option value="SOLD" ${product.status eq 'SOLD' ? 'selected' : ''}>販売完了</option>
        </select>
      </div>
      
      <!-- category -->
      <div class="formGroup">
        <label for="category_id">カテゴリー</label>
        <select name="category_id" id="category_id" data-item-name="カテゴリー" required>
          <option value="">選択してください</option>
          <c:forEach var="cat" items="${categoryList}">
            <option value="${cat.categoryId}" ${cat.categoryId == product.categoryId ? "selected" : "" }>${cat.categoryName}</option>
          </c:forEach>
        </select>
      </div>

      <!-- title -->
      <div class="formGroup">
        <label for="title">商品名</label>
        <input type="text" name="title" id="title" placeholder="商品名を入力してください" value="${product.title}" data-item-name="商品名" required>
      </div>

      <!-- price -->
      <div class="formGroup">
        <label for="price">価格（円）</label>
        <input type="number" name="price" id="price" placeholder="例：3000" value="${product.price}" data-item-name="価格" required>
      </div>
      
      <!-- photo -->
      <div class="formGroup">
	  <label>代表画像</label>
	  <div class="inputImg main ${not empty product.images ? 'filled' : ''}" data-type="main">
	    <c:choose>
	      <c:when test="${not empty product.images}">
	        <img src="/upload/maru/${product.images[0]}" style="display:block;">
	        <span class="placeholder" style="display:none;">クリックして画像追加</span>
	      </c:when>
	      <c:otherwise>
	        <img style="display:none;">
	        <span class="placeholder">クリックして画像追加</span>
	      </c:otherwise>
	    </c:choose>
	  </div>
	  <input type="file" name="mainImage" id="mainImage" hidden>
	</div>
	
	<div class="formGroup">
	  <label>詳細画像</label>
	  <div id="detailImageWrap" class="detailImageWrap">
	    
	    <c:forEach var="imgName" items="${product.images}" varStatus="status">
	      <c:if test="${!status.first}"> <div class="inputImg detail filled">
	          <img src="/upload/maru/${imgName}" style="display:block;">
	          <button type="button" class="removeBtn" style="display:block;">×</button>
	          <input type="hidden" name="existingDetails" value="${imgName}">
	        </div>
	      </c:if>
	    </c:forEach>
	
	    <div class="inputImg detail">
	      <span class="placeholder">画像追加</span>
	      <img style="display:none;">
	      <button type="button" class="removeBtn">×</button>
	      <input type="file" name="detailImages" accept="image/*" hidden>
	    </div>
	  </div>
	</div>

      <!-- content -->
      <div class="formGroup">
        <label for="content">商品説明</label>
        <textarea name="content" id="content" rows="8"
          placeholder="商品の状態、サイズ、使用感などを記載してください" data-item-name="商品説明" required>${product.content}</textarea>
      </div>

      <!-- button -->
      <div class="formBtnGroup">
        <button type="submit" class="btnPrimary" id="btn-insert">出品する</button>
        <button type="button" class="btnCancel" onclick="history.back()">キャンセル</button>
      </div>

    </form>
  </div>
</div>
    
<c:import url="/footer.jsp"></c:import>