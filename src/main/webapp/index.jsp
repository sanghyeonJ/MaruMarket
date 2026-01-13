<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
// 루트 경로로 직접 접근한 경우 /main으로 리다이렉트
String requestURI = request.getRequestURI();
String contextPath = request.getContextPath();
if (requestURI.equals(contextPath + "/") || requestURI.equals(contextPath)) {
    response.sendRedirect(contextPath + "/main");
    return;
}
%>
<c:import url="/header.jsp"></c:import>
<script src="${pageContext.request.contextPath}/js/main.js"></script>

<!-- body -->
<div class="container">
  <div class="inner">
    
    <div class="searchArea">
      <form>
        <div class="searchBox">
          <input type="text">
          <button type="submit" class="searchBtn"><img src="${pageContext.request.contextPath}/images/ico_search.svg" alt="検索"></button>
        </div>
      </form>
    </div>
    
    <div class="contentWrap">
      <div class="categoryArea">
        <h3>Category</h3>
        <div class="categoryList">
            <button type="button" class="categoryBtn active" data-id="0">全体</button>
            <c:forEach var="cate" items="${categoryList}">
                <button type="button" class="categoryBtn" data-id="${cate.categoryId}">
                ${cate.categoryName}
                </button>
            </c:forEach>
        </div>
      </div>
      <div class="productArea">
        <div class="productList">
          <c:import url="/mainListItem.jsp"></c:import>
        </div>
        
        <button type="button" class="btnLoadMore" id="btnLoadMore">もっと見る</button>
      </div>
    </div>
    
  </div>
</div>



<c:import url="/footer.jsp"></c:import>