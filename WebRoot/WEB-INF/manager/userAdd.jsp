<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/11
  Time: 10:13
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
    <title></title>
</head>
<body>
<input type="hidden" id="result" value="<s:property value="#request.result"/>"/>
<form action='<%=basePath%>manage/user-addUser' method='post' enctype="multipart/form-data">
<div class="title">
    用户登录名：<input type="text"  name="userName" autocomplete="off"/> <br/>
    用户昵称：<input type="text" name="nickName" autocomplete="off"/> <br/>
    密码：<input type="password"  name="password" autocomplete="off"/> <br/>
    确认密码：<input type="password"  id="pwdAgain"autocomplete="off"/> <br/>
</div>
</form>
<button class="sub" id="addUser" style="cursor: hand;top:20px;">添加用户</button>
<script>
    var result = $('#result').val();
    if (result == "suc") {
        alert("添加成功");
    }

    //添加用户模块
    $("input[name='userName']").keyup(function () {
        var val = this.value;
        $("input[name='nickName']").val(val);
    });
    $("#addUser").click(function () {
        var userName = $("input[name='userName']").val();
        var nickName = $("input[name='nickName']").val();
        if (isBlank(userName)) {
            alert("用户名不能为空");
            return false;
        }

        if (isBlank(nickName)) {
            alert("昵称不能为空");
            return false;
        }
        var password = $("input[name='password']").val();
        var pwdAgain = $("#pwdAgain").val();
        if (isBlank(password)) {
            alert("密码不能为空");
            return false;
        }
        if (password != pwdAgain) {
            alert("用户名和密码必须一致");
            return false;
        }

        $("form").submit();
    });

</script>
</body>
</html>
