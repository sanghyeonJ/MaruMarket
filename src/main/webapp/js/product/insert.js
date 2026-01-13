
$(function () {

  // main image
  const $mainBox   = $(".inputImg.main");
  const $mainInput = $("input[name='mainImage']");
  $mainBox.on("click", function () {
    $mainInput.trigger("click");
  });
  $mainInput.on("change", function () {
    previewImage(this, $mainBox);
  });


  // sub image
  $("#detailImageWrap").on("click", ".inputImg.detail", function (e) {
    if ($(e.target).is("input")) return;
    if ($(e.target).hasClass("removeBtn")) return;
    $(this).find("input[type=file")[0].click();
  });

  $("#detailImageWrap").on("change", "input[type=file]", function () {
    const $box = $(this).closest(".inputImg");
    previewImage(this, $box);
    if (!$box.data("filled")) {
      $box.data("filled", true);
      addDetailBox();
    }
  });

  // image delete
  $(document).on("click", ".removeBtn", function (e) {
    e.stopPropagation();
    const $box = $(this).closest(".inputImg");
    if ($box.hasClass("detail")) {
      $box.remove();
      return;
    }

    $box.find("img").hide().attr("src", "");
    $box.find(".placeholder").show();
    $(this).hide();
    $mainInput.val("");
  });

  
  // image common
  $(document).on("click", "input[type=file]", function (e) {
    e.stopPropagation();
  });
  function previewImage(input, $box) {
    const file = input.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function (e) {
      $box.find("img").attr("src", e.target.result).show();
      $box.find(".placeholder").hide();
      $box.find(".removeBtn").show();
    };
    reader.readAsDataURL(file);
  }

  function addDetailBox() {
    $("#detailImageWrap").append(`
      <div class="inputImg detail">
        <span class="placeholder">画像追加</span>
        <img style="display:none;">
        <button type="button" class="removeBtn">×</button>
        <input type="file" name="detailImages" accept="image/*" hidden>
      </div>
    `);
  }
  
  
  // insert
  $("#btn-insert").on("click", function (e) {
    e.preventDefault();
    let isValid = true;

    $("#category_id, #title, #price, #content").each(function () {
      if (!$(this).val()) {
        const itemName = $(this).data("item-name");
        alert(itemName + "を入力してください。");
        $(this).focus();
        isValid = false;
        return false;
      }
    });
    
    if (!isValid) return;
    
    const mainImage = $("#mainImage")[0].files;
    if (!mainImage || mainImage.length === 0) {
      alert("代表画像を追加してください。");
      return;
    }
    $("#productForm").submit();
  });


});

