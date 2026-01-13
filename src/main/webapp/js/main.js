$(function(){
  let page = 1;
  const pageSize = 4;
  let currentCategory = 0;
  let searchKeyword = "";

  $(document).ready(function () {
    loadProductList(true);

    // more
    $("#btnLoadMore").on("click", function () {
      page++;
      loadProductList(false);
    });

    // category click
    $(".categoryList button").on("click", function () {
      currentCategory = $(this).data("id");
      page = 1;
      loadProductList(true);
    });
  });

  // get list
  function loadProductList(reset) {
    $.ajax({
      url: "/main",
      type: "get",
      data: {
        page: page,
        categoryId: currentCategory,
        pageSize: pageSize,
        keyword: searchKeyword
      },
      success: function (data) {
        if (reset) {
          $(".productList").html(data);
        } else {
          $(".productList").append(data);
        }

        if ($(data).filter(".productItem").length < pageSize) {
          $("#btnLoadMore").hide();
        } else {
          $("#btnLoadMore").show();
        }
      },
      error: function () {
        alert("商品のインポートに失敗しました。");
      }
    });
  }
  
  // category
  $(document).on("click", ".categoryBtn", function() {
      $(".categoryBtn").removeClass("active");
      $(this).addClass("active");

      currentCategory = $(this).data("id");
      page = 1;
      
      loadProductList(true);
  });
  
  // search
  $(".searchArea form").on("submit", function (e) {
      e.preventDefault();
      
      searchKeyword = $(this).find("input[type='text']").val();
      page = 1;
      loadProductList(true);
    });

});