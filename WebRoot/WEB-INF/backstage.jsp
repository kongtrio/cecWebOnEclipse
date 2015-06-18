<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String nowDir = request.getServletPath();
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>计算机工程学院后台管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">

    <link rel="stylesheet" href="<%=basePath%>css/backstage.css"/>
    <link href="<%=basePath%>css/blue.css" rel="stylesheet">

    <script src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
    <script src="<%=basePath%>js/rotate.js"></script>
    <script src="<%=basePath%>js/cecCommon.js"></script>

    <!--[if lte IE 8]>
    <script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
    <![endif]-->

</head>

<body>
<section class="top">
    <span></span>
    <a class="logout" href="<%=basePath%>manage/user-exit">注销</a>
    <span class="author">欢迎回来，管理员</span>
</section>
<div style="width: 1280px;">
    <section class="side">
        <div class="menu">
            <a href="<%=basePath%>">
            <img src="images/logo2.png" width="200" alt=""/>
            </a>
            <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                     src="images/jiantou.png" alt=""/>文章管理
            </div>
            <ul class="level2">
                <li <s:if test='#request.path=="articleAdd"'> class="facus"</s:if>><a href="<%=basePath%>manage/artManage-toAdd">文章发布</a></li>
                <li <s:if test='#request.path=="showArticle"'> class="facus"</s:if>><a href="<%=basePath%>manage/artManage-toArtManage">文章管理</a></li>
            </ul>
            <s:if test="#session.user!=null">
                <s:if test="#session.user.uLevel==true">
                    <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                             src="images/jiantou.png" alt=""/>栏目管理
                    </div>
                    <ul class="level2">
                        <li <s:if test='#request.path=="addColumn"'> class="facus"</s:if>><a href="<%=basePath%>manage/columnManage-toAddPage">添加栏目</a></li>
                        <li <s:if test='#request.path=="showColumn"'> class="facus"</s:if>><a href="<%=basePath%>manage/columnManage-showColumn">栏目管理</a></li>
                        <li <s:if test='#request.path=="columnRight"'> class="facus"</s:if>><a href="<%=basePath%>manage/columnRightManage-showColumnRight">栏目权限</a></li>
                    </ul>
                    <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                             src="images/jiantou.png" alt=""/>用户管理
                    </div>
                    <ul class="level2">
                        <li <s:if test='#request.path=="addUser"'> class="facus"</s:if>><a href="<%=basePath%>manage/user-toUser">添加用户</a></li>
                        <li <s:if test='#request.path=="showUser"'> class="facus"</s:if>><a href="<%=basePath%>manage/user-showUser">用户管理</a></li>
                    </ul>
                    <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                             src="images/jiantou.png" alt=""/>置顶管理
                    </div>
                    <ul class="level2">
                        <li <s:if test='#request.path=="top"'> class="facus"</s:if>><a href="<%=basePath%>manage/topManage-toTopPage">文章置顶管理</a></li>
                    </ul>
                    <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                             src="images/jiantou.png" alt=""/>友情链接
                    </div>
                    <ul class="level2">
                        <li <s:if test='#request.path=="addLink"'> class="facus"</s:if>><a href="<%=basePath%>manage/friendlinktoAdd">添加链接</a></li>
                        <li <s:if test='#request.path=="friendLink"'> class="facus"</s:if>><a href="<%=basePath%>manage/friendlink">链接管理</a></li>
                    </ul>
                    <div class="level1" unselectable="on" onselectstart="return false;"><img class="jiantou"
                                                                                             src="images/jiantou.png" alt=""/>安全
                    </div>
                    <ul class="level2">
                        <li <s:if test='#request.path=="ipcontrol"'> class="facus"</s:if>><a href="<%=basePath%>manage/ipcontrol">访问Ip管理</a></li>
                    </ul>
                </s:if>
            </s:if>
        </div>
    </section>

    <!-- 内容-->
    <section class="main">
        <decorator:body></decorator:body>
    </section>

</div>

<script>
    $(".level2 li").click(function () {
        $(".level2 li").siblings().removeClass("facus");
        $(this).addClass("facus");
    });
    $(document).ready(function () {
        $(".level1").toggle(function () {
            $(this).next("ul").slideUp();
            $(this.firstChild).rotate(-90);
        }, function () {
            $(this).next("ul").slideDown();
            $(this.firstChild).rotate(120);
        })
    });

    //select
    $(".sel_wrap").on("change", function () {
        var o;
        var opt = $(this).find('option');
        opt.each(function (i) {
            if (opt[i].selected == true) {
                o = opt[i].innerHTML;
            }
        });
        $(this).find('label').html(o);
    }).trigger('change');

    //check
    $(document).ready(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_flat-blue',
            radioClass: 'iradio_flat-blue'
        });
    });


</script>
</body>
</html>
