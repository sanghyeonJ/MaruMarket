<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${not empty productList}">
        <c:forEach var="item" items="${productList}">
          <div class="productItem status-${item.status}" onclick="goPage('/prod/detail.do?productId=${item.productId}')">
            <div class="imgBox">
              <img src="/upload/maru/${item.mainImage}">
              <div class="status"></div>
            </div>
            <h4 class="ellipsis-1">${item.title}</h4>
            <div class="itemInfo">
              <p class="price"><fmt:formatNumber value="${item.price}" />円</p>
            </div>
          </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="no-data">
            <p>登録された商品がありません。</p>
        </div>
    </c:otherwise>
</c:choose>