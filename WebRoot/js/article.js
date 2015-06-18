/**
 * Created by Administrator on 2015/2/9.
 */
//获取富文本编辑器
UE.getEditor('editor');

//发表文章按钮
$('#submit').click(function () {
    var $btn = $(this).button('loading');
    // business logic...
    setTimeout(function () {
        $btn.button('reset')
    }, 1000);
    setTimeout(function () {
        $btn.button('complete')
    }, 1000);
});
//opera
$(".opera:first-child").click(function(){
    //alert($(this).val());
    window.open("newsinfo.html");
});

//checkbox
$(".check").toggle(
    function(){
        $(this).find(":first-child").attr("src","img/check.png");
    },
    function(){
        $(this).find(":first-child").attr("src","img/check2.png");
    }
);

//选项卡
$(".top ul li").click(function(){
    $(this).siblings().removeAttr("id");
    $(this).attr("id","active");
    var pos=$(this).index();

    $(".main").siblings().removeClass("display");
    $(".main:eq("+pos+")").addClass("display");
})