<%--
  Created by IntelliJ IDEA.
  User: yangjb
  Date: 2015/3/22
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>集美大学计算机工程学院</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>css/newslist.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/section.css"/>
    <link href="<%=basePath%>css/page.css" rel="stylesheet" type="text/css"/>
    <meta name="renderer" content="webkit" /> 
    <meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
    <!--[if lt IE9]>
    <script>
        (function() {
            if (!
                        /*@cc_on!@*/
                            0) return;
            var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
            var i= e.length;
            while (i--){
                document.createElement(e[i])
            }
        })()
    </script>
    <![endif]-->
    <!--[if lt IE 8]>
    <script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
    <![endif]-->
    
</head>
<body>
<section class="nav">
    <div class="continer">
        <ul class="nav-item">
            <li><a href="<%=basePath%>">学院首页</a></li>
            <s:iterator value="#request.navColumn" id="item">
                <li><a <s:if test="#item.isNewWindows==true">target="_blank"</s:if>  href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showColumn?colId=${item.columnId}</s:else>">${item.colName}</a>
                    <s:if test="#item.artColumns.size() != 0">
                        <ul class="second-level">
                            <s:iterator value="#item.artColumns" id="col">
                                <li><a <s:if test="#col.isNewWindows==true">target="_blank"</s:if> href="<s:if test="#col.isAddress==true">${col.outAddress} </s:if><s:else><%=basePath%>showColumn?colId=${col.columnId}</s:else>">${col.colName} </a></li>
                            </s:iterator>
                        </ul>
                    </s:if>
                </li>
            </s:iterator>
            <li><a target="_blank" href="http://www.jmu.edu.cn/">返回学校</a></li>
        </ul>
        <form action="searchArticle"  method="post" >
            <div class="login"><a href="logintoLogin">登陆</a></div>
            <div class="search">
                <input type="text" name="key" autocomplete="off">
                <button onclick="submitForm()"></button>
            </div>
        </form>
    </div>
</section>
<%@include file="title.jspf" %>
<%--<section class="pic"></section>--%>

<section class="menu">
    <div class="continer">
        <span id="weizhi">当前位置:
            <ul style="display: inline-block;">
                <li><a href="<%=basePath%>">学院主页</a></li>
                    <li><a href="<%=basePath%>indexArticle">>通知公告</a></li>
            </ul>
        </span>
    </div>
</section>
    <input type="hidden" id="pageNum" value="<s:property value="#request.pageNum"/>"/>
    <input type="hidden" id="colIdHidden" value="<s:property value="#request.colId"/>"/>
    <input type="hidden" id="basePath" value="<%=basePath%>"/>
    <section style="margin-top: 27px;min-height: 340px;" class="continer">
        <ul class="liebiao">
            <s:iterator value="#request.indexArticleByPage" id="x">
                <li><a target="_blank"
                       href="<s:if test="#x.isAddress==true">${x.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${x.artId}</s:else>">${x.title}</a>
                    <span style="margin-left: 30px;">
                    <s:date name="#x.publicTime" format="yyyy/MM/dd"/></span>
                    <s:if test="#x.isMark==true"><img src="images/ic05.gif" height="15px;" title="醒目"></s:if>
                    <s:if test="#x.isSchool==true"><img src="images/ic04.gif" height="15px;" title="校内新闻"></s:if>
                    <s:if test="#x.isNew==true"><img src="images/new.gif" height="15px;" style="margin-left: 5px;" title="24小时内更新"></s:if>
                </li>
            </s:iterator>
        </ul>
        <div class="news_box" style="margin-top: 37px;">
            <div class="news_conte" id="demo7" style="width:800px;">
            </div>
        </div>
    </section>
<footer>
    <div class="footer">
            <span class="copyright">
                CopyRight &copy; 2005-2007 <br/>
                集美大学计算机工程学院
            </span>
    </div>
</footer>

<script src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>js/jquery.easing.1.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/index.js"></script>
<script src="<%=basePath%>js/cecCommon.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.myPagination.js" type="text/javascript"></script>
<script>
    var pageNum = '', pageSize = '', colId = '';
    $(document).ready(function () {
        pageNum = $('#pageNum').val();
        pageSize = pageNum > 10 ? 10 : pageNum;
        var colIdHidden = $('#colIdHidden').val();
        colId = colIdHidden;
        if (pageNum > 1) {
            getData();
        }
    });

    function getData() {
        $("#demo7").myPagination({
            currPage: 1,
            pageCount: pageNum,
            pageSize: pageSize,
            cssStyle: 'tres',
            ajax: {
                on: true,
                callback: 'callSuc',
                url: "indexArticlebyPage",
                dataType: 'json',//传参数到后台,on一定要变成true
                param: {"on": true, "colId": colId}
            }
        });
    }

</script>
</body>
</html>
