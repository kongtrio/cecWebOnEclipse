/**
 * Created by manggo on 2015/5/7.
 */
jQuery.fn.rotate = function(degree){
    this.css({'cursor':'pointer','filter':'progid:DXImageTransform.Microsoft.Matrix(sizingmethod="auto expand")'});
    var ua = navigator.userAgent;
    var isIE = /msie/i.test(ua) && !window.opera;
    var i = 0, sinDeg = 0, cosDeg = 0, timer = null;
    function run(target,angle) {
        var orginW = target.clientWidth;
        var orginH = target.clientHeight;
        if (isIE) { // IE
            cosDeg = Math.cos(angle * Math.PI / 180);
            sinDeg = Math.sin(angle * Math.PI / 180);
            //with(target.filters.item(0)) {
            //    M11 = M22 = cosDeg; M12 = -(M21 = sinDeg);
            //}
            target.style.top = (orginH - target.offsetHeight) / 2 + 'px';
            target.style.left = (orginW - target.offsetWidth) / 2 + 'px';
        } else if (target.style.MozTransform !== undefined) {  // Mozilla
            target.style.MozTransform = 'rotate(' + angle + 'deg)';
        } else if (target.style.OTransform !== undefined) {   // Opera
            target.style.OTransform = 'rotate(' + angle + 'deg)';
        } else if (target.style.webkitTransform !== undefined) { // Chrome Safari
            target.style.webkitTransform = 'rotate(' + angle + 'deg)';
        } else {
            target.style.transform = "rotate(" + angle + "deg)";
        }
    };
    degree %= 360;
    return this.each(function(){
        if(degree == 0){
            return;
        }
        var target = jQuery(this)[0];
        clearInterval(timer);
        timer = setInterval(function() {
            if(degree < 0){//逆时针
                i -= -degree < 5 ? -degree : 5;
                run(target,i);
                if (i < degree + 1) {
                    i = 0;
                    clearInterval(timer);
                }
            }else{//顺时针
                i += degree < 5 ? degree : 5;
                run(target,i);
                if (i > degree - 1) {
                    i = 0;
                    clearInterval(timer);
                }
            }
        }, 10);//速度
    });
};