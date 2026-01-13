$(function(){

  //join
  $("#user_id").blur(function(){
    if(!$(this).val()){
      $(".userid-msg").text("");
      $(this).attr("data-checked", "N");
      return;
    }
    const $this = $("#user_id");
    $.ajax({
      type: "POST",
      url: "/mem/useridcheck.do",
      data: {user_id:$("#user_id").val()},
      success: function(result){
        if(result == -1){
          $(".userid-msg").text("使用可能なIDです。");
          $this.attr("data-checked", "Y");
        }else{
          $(".userid-msg").text("重複したIDです。");
          $this.attr("data-checked", "N");
        }
      }, error: function(){
        alert("エラー発生");
      }
    });
  });
  $("#user_pw_check").blur(function(){
    if($(this).val() != $("#user_pw").val()){
      $(".pw-msg").text("パスワードが異なります。");
      $(this).attr("data-checked", "N");
    }else{
      $(".pw-msg").text("");
      $(this).attr("data-checked", "Y");
    }
  });
  $("#email").blur(function(){
    if(!$(this).val()){
      $(this).attr("data-checked", "N");
    }else{
      $(this).attr("data-checked", "Y");
    }
  });
  $("#user_name").blur(function(){
    if(!$(this).val()){
      $(this).attr("data-checked", "N");
    }else{
      $(this).attr("data-checked", "Y");
    }
  });
  $("#btn-join").on("click", function(e){
    e.preventDefault();
    let isvalid = true;
    $("#user_id, #user_pw, #user_name, #email").each(function(){
      if(!$(this).val()){
        isvalid = false;
        const item = $(this).data("itemName");
        alert(item + "を確認してください");
        return false;
      }else{
        isvalid = true;
      }
    });
    if(isvalid){
      $("#joinForm").attr("method", "post");
      $("#joinForm").attr("action", "/mem/memberInsert.do");
      $("#joinForm").submit();
    }
  });

});