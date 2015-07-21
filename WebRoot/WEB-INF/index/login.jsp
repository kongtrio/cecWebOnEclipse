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
  <link rel="stylesheet" href="<%=basePath%>css/login.css"/>
  <title>集美大学计算机工程学院</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta name="renderer" content="webkit" /> 
  <meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
	<!--[if lt IE 8]>
	<script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
	<![endif]-->
    
</head>

<BODY id=loginFrame>
<input type="hidden" id="result" value="<s:property value='#request.result'/>"/>
<DIV id=header>
  <DIV id=logo>
    <%--<span>集美大学计算机工程学院</span>--%>
    <%--<A title=集美大学计算机工程学院--%>
                  <%--href="<%=basePath%>"></A>--%>
  </DIV></DIV>
<DIV id=loginBox>
  <DIV id=loginBoxHeader></DIV>
  <DIV id=loginBoxBody>
    <UL class=floatLeft>
      <FORM id=login method="post" action="login">
        <LI>
          <P>登陆名:</P><INPUT id=email class=textInput maxLength=150 size=30 type=text
                             name="userName"> </LI>
        <LI>
          <P>密码:</P><INPUT id=password class=textInput maxLength=80 size=30
                           type=password name="password">
        </LI>
        <LI>
          <P>验证码:</P><INPUT id=verry class=textInput maxLength=150 size=30 type=text style="width: 80px;"
                             name="verifyCode">  <img src="image.jsp" id="randImage" onclick="javascript:loadimage();" alt=""/>
        </LI>
        <LI>
        <LI class=highlight><INPUT id=loginBtn onclick=this.blur(); value=登录 type=submit>
        </LI>
        <LI></LI></FORM></UL>
    <DIV
            class=floatRight>欢迎访问集美大学计算机工程学院
    </DIV><BR clear=all></DIV>
  <DIV id=loginBoxFooter></DIV></DIV>
<DIV id=footer><A href="<%=basePath%>"><IMG alt="集美大学计算机工程学院"
                                                      src="images/copyright.png"></A> </DIV>
<script>
  function loadimage() {
    document.getElementById("randImage").src = "image.jsp?" + Math.random();
  }

  var result = document.getElementById("result").value;
  if (result == "1") {
    alert("账号或者密码错误");
  } else if (result == "2") {
    alert("请先登陆");
  } else if (result == "3") {
    alert("验证码错误");
  }else if (result == "4") {
    alert("系统错误，请联系管理员");
  }
</script>
</BODY>

</html>
