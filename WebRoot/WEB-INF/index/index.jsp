<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String tomcatPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class=" webp webp-alpha webp-animation webp-lossless">
<head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="<%=basePath%>css/index.css"/>
    <title>集美大学计算机工程学院</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
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
    <style>
    .lstr{
		  display: inline-block;
		  white-space: nowrap;
		  width: 221px;
		  height: 20px;
		  line-height: 20px;
		  text-overflow: ellipsis;
		  -moz-text-overflow: ellipsis;
		  overflow: hidden;
    }
    </style>
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
<section class="pic"></section>
<section class="menu">
    <div class="continer">
        <span style="color: #ffffff">[会议通知]</span><img class="laba" src="images/icon.gif" alt=""/>
        <a id="tongzhi" target="_blank" href="<%=basePath%>showArticle?articleId=${topArticle.artId}" style="color: #ffffff;">
            ${topArticle.title}</a>
        <span class="time" style="color: #ffffff">(<s:date name="#request.topArticle.publicTime" format="yyyy/MM/dd"/>)</span>
    </div>
</section>
<section>
    <div class="continer">
        <div class="lanmu" style="margin-left: 0">
            <span class="title">通知公告</span>
            <span class="more">/<a href="<%=basePath%>indexArticle">更多>></a></span>
            <ul class="main">
                <s:iterator value="#request.indexArticle" id="item">
                    <li>
                        <a class="lstr" target="_blank"
                           href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${item.artId}</s:else>">
                            ▶${item.title}</a>
                        <span class="mmdd"><s:date name="#request.item.publicTime" format="MM-dd"/></span>
                        <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="10px;" title="醒目"></s:if>
                        <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="10px;" title="校内新闻"></s:if>
                        <s:if test="#item.isNew==true"><i></i></s:if>
                    </li>
                </s:iterator>
            </ul>
        </div>

        <div class="lanmu">
            <span class="biaoti">学院风采</span>
            <div class="yyccdiv">
                <div id="fader">
                    <ul>
                        <s:iterator value="#request.articleWithImage" id="item">
                            <li><a target="_blank" href="<%=basePath%>showArticle?articleId=${item.artId}"><img
                                    src="<%=basePath%>${item.titlePic}"
                                    alt="${item.artId}${item.title}"/></a></li>
                        </s:iterator>
                    </ul>
                </div>
            </div>
        </div>
        <s:set name="new2" value="#request.indexColumn.get(0)"></s:set>
        <div class="lanmu">
            <span class="title">${new2.colName}</span>
            <span class="more">/<a href="<%=basePath%>showColumn?colId=${new2.columnId}">更多>></a></span>
            <ul class="main">
                <s:iterator value="#new2.listArticles" id="item">
                    <li>
                        <a class="lstr" target="_blank"
                           href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${item.artId}</s:else>">
                            ▶${item.title}</a>
                        <span class="mmdd"><s:date name="#request.item.publicTime" format="MM-dd"/></span>
                        <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="10px;" title="醒目"></s:if>
                        <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="10px;" title="校内新闻"></s:if>
                        <s:if test="#item.isNew==true"><i></i></s:if>
                    </li>
                </s:iterator>
            </ul>
        </div>
        <s:set name="new3" value="#request.indexColumn.get(1)"></s:set>
        <div class="lanmu" style="margin-left: 0">
            <span class="title">${new3.colName}</span>
            <span class="more">/<a href="<%=basePath%>showColumn?colId=${new3.columnId}">更多>></a></span>
            <ul class="main">
                <s:iterator value="#new3.listArticles" id="item">
                    <li>
                        <a class="lstr" target="_blank"
                           href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${item.artId}</s:else>">
                            ▶${item.title}</a>
                        <span class="mmdd"><s:date name="#request.item.publicTime" format="MM-dd"/></span>
                        <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="10px;" title="醒目"></s:if>
                        <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="10px;" title="校内新闻"></s:if>
                        <s:if test="#item.isNew==true"><i></i></s:if>
                    </li>
                </s:iterator>
            </ul>
        </div>
        <s:set name="new4" value="#request.indexColumn.get(2)"></s:set>
        <div class="lanmu">
            <span class="title">${new4.colName}</span>
            <span class="more">/<a href="<%=basePath%>showColumn?colId=${new4.columnId}">更多>></a></span>
            <ul class="main">
                <s:iterator value="#new4.listArticles" id="item">
                    <li>
                        <a class="lstr" target="_blank"
                           href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${item.artId}</s:else>">
                            ▶${item.title}</a>
                        <span class="mmdd"><s:date name="#request.item.publicTime" format="MM-dd"/></span>
                        <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="10px;" title="醒目"></s:if>
                        <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="10px;" title="校内新闻"></s:if>
                        <s:if test="#item.isNew==true"><i></i></s:if>
                    </li>
                </s:iterator>
            </ul>
        </div>
        <s:set name="new5" value="#request.indexColumn.get(3)"></s:set>
        <div class="lanmu">
            <span class="title">${new5.colName}</span>
            <span class="more">/<a href="<%=basePath%>showColumn?colId=${new5.columnId}">更多>></a></span>
            <ul class="main">
                <s:iterator value="#new5.listArticles" id="item">
                    <li>
                        <a class="lstr" target="_blank"
                           href="<s:if test="#item.isAddress==true">${item.outAddress} </s:if><s:else><%=basePath%>showArticle?articleId=${item.artId}</s:else>">
                            ▶${item.title}</a>
                        <span class="mmdd"><s:date name="#request.item.publicTime" format="MM-dd"/></span>
                        <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="10px;" title="醒目"></s:if>
                        <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="10px;" title="校内新闻"></s:if>
                        <s:if test="#item.isNew==true"><i></i></s:if>
                    </li>
                </s:iterator>
            </ul>
        </div>
        <div style="clear: both;"></div>
    </div>
</section>

<footer>
    <div class="footer">
        <div class="info">
            <img src="images/logo2.png" alt="" width="50%" height="40%"/>

            <p style="font-size: 13px">
                厦门市集美区银江路183号(校总部)<br/><br/>
                邮编/361021<br/>
                电话/123-123456<br/>
                传真/123456<br/><br/>
                CopyRight &copy; 2005-2007 <br/>
                集美大学计算机工程学院
            </p>
        </div>
        <div class="link">
            <span>院系链接</span>
            <ul>
                <s:iterator value="#request.friendLinks" id="item">
                    <li><a target="_blank" href="${item.address}">${item.name}</a></li>
                </s:iterator>
            </ul>
        </div>
        <div class="link2">
            <span>意见反馈</span>
            <ul>
                <li><a href="mailto:yunmingpu@jmu.edu.cn">院长信箱</a></li>
                <li><a href="mailto:chejun@jmu.edu.cn">书记信箱</a></li>
            </ul>
        </div>
        <div class="wei">
            <ul id="label">
                <li id="label1" value="1" class="labelbg display"><img src="images/weibo.png" width="60%"/></li>
                <li id="label2" value="2" class="labelbg"><img src="images/weixin.png" width="50%"/></li>
                <li id="label3" value="3" class="labelbg"><img src="images/tieba.png" width="50%"/></li>
                <li style="clear: both;display: none"></li>
            </ul>
            <div class="guanzhu">关注我们</div>
            <ul id="card">
                <li id="card1" class="cardnon display-non">
                    <a href="http://weibo.com/jmuxcbxwwb?c=spr_qdhz_bd_baidusmt_weibo_s&sudaref=www.baidu.com&nick=%E9%9B%86%E7%BE%8E%E5%A4%A7%E5%AD%A6">集美大学官方微博</a><br/>
                </li>
                <li id="card2" class="cardnon">
                    <a href="http://weixin.hlo.cc/">集美大学官方微信</a><br/>
                </li>
                <li id="card3" class="cardnon">
                    <a href="http://tieba.baidu.com/f?ie=utf-8&kw=%E9%9B%86%E7%BE%8E%E5%A4%A7%E5%AD%A6&fr=search">集美大学官方贴吧</a><br/>
                </li>
            </ul>
            <div class="morelink"><a href="#">更多关注</a></div>
        </div>
    </div>
</footer>


<script src="<%=basePath%>js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.easing.1.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/index.js" type="text/javascript"></script>
<script src="<%=basePath%>js/cecCommon.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.slides.min.js"></script>
<script>
    function submitForm(){
        $("form");
    }
</script>
</body>

</html>
