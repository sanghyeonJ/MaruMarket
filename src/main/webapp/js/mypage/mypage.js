$(function() {
    let page = 1;
    const pageSize = 4;
    // 현재 접속한 URL (/mypage/like.do 등)
    const currentUrl = window.location.pathname;

    $(document).ready(function() {
        // "더보기" 버튼 이벤트만 담당
        $("#btnLoadMore").on("click", function() {
            page++;
            loadMoreItems();
        });

    });

    function loadMoreItems() {
        $.ajax({
            url: currentUrl,
            type: "get",
            data: {
                page: page,
                pageSize: pageSize,
            },
            success: function(data) {
                // 가져온 데이터에서 실제 아이템이 있는지 체크
                const $items = $(data).filter(".productItem");

                if ($items.length > 0) {
                    $(".productList").append(data);
                }

                // 더 가져올 데이터가 없으면 버튼 숨김
                if ($items.length < pageSize) {
                    $("#btnLoadMore").hide();
                } else {
                    $("#btnLoadMore").show();
                }
            },
            error: function() {
                alert("データを読み込めませんでした。");
            }
        });
    }
});