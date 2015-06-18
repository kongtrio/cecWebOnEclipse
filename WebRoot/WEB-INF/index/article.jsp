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
    <link rel="stylesheet" href="<%=basePath%>css/newsinfo.css"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
</head>
<body>
<section class="nav">
    <div class="continer">
        <ul class="nav-item">
            <li><a href="<%=basePath%>">学院首页</a></li>
            <s:iterator value="#request.navColumn" id="item">
                <li><a <s:if test="#item.isNewWindows==true">target="_blank"</s:if>  href="<s:if test="#item.isAddress==true">http://${item.outAddress} </s:if><s:else><%=basePath%>showColumn?colId=${item.columnId}</s:else>">${item.colName}</a>
                    <s:if test="#item.artColumns.size() != 0">
                        <ul class="second-level">
                            <s:iterator value="#item.artColumns" id="col">
                                <li><a <s:if test="#col.isNewWindows==true">target="_blank"</s:if> href="<s:if test="#col.isAddress==true">http://${col.outAddress} </s:if><s:else><%=basePath%>showColumn?colId=${col.columnId}</s:else>">${col.colName} </a></li>
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
<section class="menu">
    <div class="continer">
        <span id="weizhi">
            <ul style="display: inline-block;">
                <li><a href="<%=basePath%>">学院主页</a></li>
            </ul>
        </span>
    </div>
</section>
<input type="hidden" id="isNewTab" value="<s:property value='#request.article.isNewTab'/>"/>
<input type="hidden" id="contentValue" value=" <s:property value="#request.article.content"></s:property>"/>
<section style="margin-top: 27px;min-height: 340px;" class="continer">
    <div class="wzbt"><s:property value="#request.article.title"></s:property></div>
    <hr style="margin:10px 0;border: 1px solid #0066cc" noshade/>
    <div class="time">发布时间：<s:date
            name="#request.article.publicTime" format="yyyy/MM/dd"/>
        &nbsp;&nbsp;&nbsp;<span>浏览次数：<s:property
                value="#request.article.readCount"></s:property></span>
    </div>
    <div class="main" style="margin-left: 80px;margin-top: 10px;">
        <%--<s:if test="#request.article.isNewTab==8">--%>
            ${article.content}
        <%--</s:if>--%>
    </div>
</section>
<div style="text-align: center"><a href="javascript:window.opener=null;window.open('','_self');window.close();"><img src="images/b_close.gif" alt=""/></a></div>

<div style="clear: both;"></div>
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
    $(document).ready(function () {
        var isNewTab = $("#isNewTab").val();
        if(isNewTab!=8){
            var content = $("#contentValue").val();
            $("div[class='main']").html(htmlDecode(content));
        }

        function htmlEncode(value) {
            if (value) {
                return jQuery('<div />').text(value).html();
            } else {
                return '';
            }
        }

        function htmlDecode(value) {
            if (value) {
                return $('<div />').html(value).text();
            } else {
                return '';
            }
        }

    });
</script>
</body>
</html>
