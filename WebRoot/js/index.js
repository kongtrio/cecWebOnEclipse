/**
 * Created by manggo on 2015/5/5.
 */
//二级菜单
$(".nav-item >li").mouseover(function(){
    $(this).find('.second-level').slideDown();});
$(".nav-item >li").mouseleave(function(){
    $(this).find('.second-level').slideUp();});

//通知字数
var str = $("#tongzhi");
if(str.text().length>46)
    str.text(str.text().substr(0,46)+"...");

//选项卡
$("#label li").mouseover(function(){

    /*img*/
    $(this).children().animate(
        {
            top: '-100px'
        },1500,'linear'
    );

    /*label*/
    $("#label li").each(function(){
        $(this).removeClass("display");
    });
    $(this).addClass("display");

    /*card*/
    $("#card li").each(function(){
        $(this).removeClass("display-non");
    });
    $("#card li:eq("+($(this).val()-1)+")").addClass("display-non");
});


//图片轮播2
var Hongru={};
function H$(id){return document.getElementById(id)}
function H$$(c,p){return p.getElementsByTagName(c)}
Hongru.fader = function(){
    function init(anthor,options){this.anchor=anthor; this.init(options);}
    init.prototype = {
        init:function(options){ //options参数：id（必选）：图片列表父标签id；auto（可选）：自动运行时间；index（可选）：开始的运行的图片序号
            var wp = H$(options.id), // 获取图片列表父元素
                ul = H$$('ul',wp)[0], // 获取
                li = this.li = H$$('li',ul);
            this.a = options.auto?options.auto:4; //自动运行间隔
            this.index = options.position?options.position:0; //开始运行的图片序号（从0开始）
            this.curC = options.curNavClass?options.curNavClass:'fader-cur-nav';
            this.l = li.length;
            this.cur = this.z = 0; //当前显示的图片序号&&z-index变量
            nav_wp = document.createElement('div'); //先建一个div作为控制器父标签，你也可以用<ul>或<ol>来做，语义可能会更好，这里我就不改了
            nav_wp.style.cssText = 'position:absolute;right:0;bottom:0;padding:8px 0;'; //为它设置样式
            /* -- 显示备注 --*/
            var alt = this.alt = document.createElement('p'); //添加一个p标签，用于显示文本
            this.img = [];
            for(var k=0;k<this.l;k++){
                this.img.push(H$$('img',this.li[k])[0]); //提取轮播模块里的图片，目的是取alt
            }
            /* ==加入淡入淡出功能 ==*/
            for(var i=0;i<this.l;i++){
                this.li[i].o = 100; //为每一个图片都设置一个透明度变化量
                this.li[i].style.opacity = this.li[i].o/100; //非IE用opacity即可
                this.li[i].style.filter = 'alpha(opacity='+this.li[i].o+')'; //IE用滤镜
                /* == 绘制控制器 == */
                var nav = document.createElement('a'); //这里我就直接用a标签来做控制器，考虑语义的话你也可以用li
                nav.className = options.navClass?options.navClass:'fader-nav'; //控制器class，默认为'fader-nav'
                nav.innerHTML = i+1;
                nav.onclick = new Function(this.anchor+'.pos('+i+')'); //绑定onclick事件，直接调用之前写好的pos()函数
                nav_wp.appendChild(nav);
            }
            wp.appendChild(alt); //
            wp.appendChild(nav_wp); //控制器append到页面
            this.textH = nav_wp.offsetHeight;
            alt.style.cssText = 'height:'+this.textH+'px;line-height:'+this.textH+'px;color:#fff;font-size:12px;padding-left:20px;margin:0;position:absolute;bottom:0;overflow:hidden;width:100%;background:#000;opacity:0.7;filter:alpha(opacity=70);'; //为这个层添加样式
            this.pos(this.index); //变换函数
        },
        auto:function(){
            this.li.a = setInterval(new Function(this.anchor+'.move(1)'),this.a*1000);
        },
        move:function(i){//参数i有两种选择，1和-1,1代表运行到下一张，-1代表运行到上一张
            var n = this.cur+i;
            var m = i==1?n==this.l?0:n:n<0?this.l-1:n; //下一张或上一张的序号（注意三元选择符的运用）
            this.pos(m); //变换到上一张或下一张
        },
        pos:function(i){
            clearInterval(this.li.a); //清除自动变换计时器
            clearInterval(this.li[i].f); //清除淡入淡出效果计时器
            this.z++;
            this.li[i].style.zIndex = this.z; //每次让下一张图片z-index加一
            this.alt.style.zIndex = this.z+1;
            nav_wp.style.zIndex = this.z+2;
            this.cur = i; //绑定当前显示图片的正确序号
            this.li.a = false; //做一个标记，下面要用到，表示清除计时器已经完成
//this.auto(); //自动运行
            if(this.li[i].o>=100){ //在图片淡入之前先把图片透明度置为透明
                this.li[i].o = 0;
                this.li[i].style.opacity = 0;
                this.li[i].style.filter = 'alpha(opacity=0)';
                this.alt.style.height = 0; //做备注层的滑动效果
            }
            this.alt.innerHTML = this.img[i].alt; //植入alt文本
            for(var x=0;x<this.l;x++){
                nav_wp.getElementsByTagName('a')[x].className = x==i?this.curC:'fader-nav'; //绑定当前控制器样式
            }
            this.li[i].f = setInterval(new Function(this.anchor+'.fade('+i+')'),20);
        },
        fade:function(i){
            if(this.li[i].o>=100){
                clearInterval(this.li[i].f); //如果透明度变化完毕，清除计时器
                if(!this.li.a){ //确保所有计时器都清除掉之后再开始自动运行。要不然会导致有控制器时点击过快的话，计时器没来得及清除就开始下一次变化，功能就乱了
                    this.auto();
                }
            }
            else{
                this.li[i].o+=5;
                this.li[i].style.opacity = this.li[i].o/100;
                this.li[i].style.filter = 'alpha(opacity='+this.li[i].o+')';
                this.alt.style.height = Math.ceil(this.li[i].o*this.textH/100)+'px'; //做文字滑动效果
            }
        }
    };
    return {init:init}
}();

var fader = new Hongru.fader.init('fader',{
    id:'fader'
});