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
<div class="list">
    <form action="<%=basePath%>manage/user-delete" method="post">
        <table class="table">
            <thead>
            <tr>
                <th><input type="checkbox" id="checkAll"/></th>
                <th>用户登陆名</th>
                <th>用户名称</th>
                <th>注册时间</th>
                <th>登陆次数</th>
                <th>最后登录时间</th>
                <th>最后登陆的IP</th>
                <th>发表文章数</th>
            </tr>
            </thead>
            <tbody id="new_tab">

            <s:iterator value="#request.users" id="item">
                <tr>
                    <td><input type="checkbox" name="ids" value="${item.userId}"/></td>
                    <td>${item.userName}</td>
                    <td>${item.nickName}</td>
                    <td>
                        <s:date name="#item.regTime" format="yyyy-MM-dd hh:mm:ss"/>
                    </td>
                    <td>${item.loginCount}</td>
                    <td>
                        <s:date name="#item.lastTime" format="yyyy-MM-dd hh:mm:ss"/>
                    </td>
                    <td>${item.lastIp}</td>
                    <td>${item.artCount}</td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <div class="news_box">
            <div class="news_conte" id="demo7" style="width:800px;">
                <input id="ok2del" class="delete" value="删除" type="submit"/>
            </div>
        </div>
    </form>
</div>
<script>
    $('#checkAll').click(function () {
        if (this.checked) {
            $("input[name='ids']").each(function () {
                this.checked = true;
            });
        } else {
            $("input[name='ids']").each(function () {
                this.checked = false;
            });
        }
    });

    $("#ok2del").click(function () {
        var sure = confirm("确定要删除这些用户吗？");
        if (sure) {
            return true;
        } else {
            return false;
        }
    });
</script>
</body>
</html>
