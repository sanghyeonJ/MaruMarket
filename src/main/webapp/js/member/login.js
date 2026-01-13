$(function(){
  //login
  $("#btn-login").on("click", function(e){
    e.preventDefault();
    let user_id = $("#user_id").val();
    let user_pw = $("#user_pw").val();
    if(!user_id){
      alert("IDを入力してください");
      return;
    }
    if(!user_pw){
      alert("パスワードを入力してください");
      return;
    }
    
    $.ajax({
      type: "POST",
      url: "/mem/loginAction.do",
      data: {user_id: user_id, user_pw: user_pw},
      success: function(result){
        if(result === "success"){
          if($("#saveid").is(":checked")){
            $.cookie("saveid", user_id, {expires: 7, path: "/"});
          }else{
            $.removeCookie("saveid", {path: "/"});
          }
          location.href='/main'
        }else{
          alert("IDまたはパスワードを確認してください。");
        }
      },error: function(){
        alert("サーバーエラー発生");
      }
    })
  });
});