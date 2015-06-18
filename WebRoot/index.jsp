<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/main.css"/>
    
    <title>集美大学计算机工程学院</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script src="<%=basePath%>js/jquery-1.8.0.min.js" type="text/javascript"></script>
  </head>
  <body>
    <form action="index" name="form1" method="post"></form>
  </body>
  <script type="text/javascript">
       $('form[name="form1"]').submit();
  </script>
</html>
