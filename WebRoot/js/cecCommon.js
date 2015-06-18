//格式化日期
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                       //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

//判断属性是否为空
function isBlank(param) {
    if (param == undefined || param == null || param == '') {
        return true;
    }
    return false;
}

//判断属性非空
function isNotBlank(param) {
    return !isBlank(param);
}

//点击单选按钮的时候设置值(checkBoxId的值改变的时候，给valueId的组件赋值)
function setCheckValue(checkBoxId, valueId) {
    $("#" + checkBoxId).click(function () {
        if (this.checked) {
            $("#" + valueId).val(true);
        } else {
            $("#" + valueId).val(true);
        }
    });
}

//列表页斑马纹
$(".liebiao li:even").css("background-color","rgb(210, 224, 236)");

//对返回的新闻进行解析
function callSuc(data) {
    var result = data.result;
    if (result == "suc") {
        var news = data.news;
        var content = "";
        for (var i = 0; i < news.length; i++) {
            var art = news[i];
            content += '<li>';

            var url = $("#basePath").val() + "showArticle?articleId=" + art.artId;
            if (art.isAddress) {
                url = "http://" + art.outAddress;
            }
            content += '<a target="_blank"';
            content += 'href="' + url + '">' + art.title + '</a>';
            content += '<span style="margin-left: 30px;float: right;">' + new Date(art.publicTime.time).Format("yyyy/MM/dd") + '</span>';
            if(art.isMark==true){
                content += '<img src="images/ic05.gif" height="15px;" style="margin-left: 5px;" title="醒目">';
            }
            if(art.isSchool==true){
                content += '<img src="images/ic04.gif" height="15px;" style="margin-left: 5px;" title="醒目">';
            }
            if(art.isNew==true){
                content += '<img src="images/new.gif" height="15px;" style="margin-left: 5px;" title="24小时内更新">';
            }
            content += '</li>';
        }
        $('ul[class="liebiao"]').html(content);
        //列表页斑马纹
        $(".liebiao li:even").css("background-color","rgb(210, 224, 236)");
    }
}

function submitForm(){
    $("form").submit();
}


