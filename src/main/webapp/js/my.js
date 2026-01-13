///////// common
function goPage(uri){
  location.href = uri;
}

$(function(){

  $(".drop").on("mouseover", function(){
    $(this).find(".dropmenu").stop(true).slideDown(300);
  });
  $(".drop").on("mouseleave", function(){
    $(this).find(".dropmenu").stop(true).slideUp(300);
  });

});

