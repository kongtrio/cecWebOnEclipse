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
<form action='<%=basePath%>manage/friendlinkadd' method='post' enctype="multipart/form-data">
    <input type="hidden" id="isIndex" name="isIndex"/>
<div class="title">
    链接名称：<input type="text"  name="name" autocomplete="off"/> <br/>
    链接地址：<input type="text" name="address" autocomplete="off"/> <br/>
    简介：<input type="text"  name="summary" autocomplete="off"/> <br/>
    <div class="check" id="indexTopDiv">
        <input type="checkbox"  id="isIndexTop_check"><label for="isIndexTop_check" unselectable="on"
                                                             onselectstart="return false;">是否首页栏目</label>
    </div>
</div>
</form>
<button class="sub" id="addLink" style="cursor: hand;top:20px;">添加链接</button>
<script src="<%=basePath%>js/icheck.min.js"></script>
<script>
    var result = $('#result').val();
    if (result == "suc") {
        alert("添加成功");
    }else if(result == "fail"){
        alert("添加失败");
    }

    $('#indexTopDiv').on('ifClicked', function () {
        $('#isIndex').val(!$('#isIndexTop_check').is(':checked'));
    });

    $("#addLink").click(function () {
        var name = $("input[name='name']").val();
        var address = $("input[name='address']").val();
        if (isBlank(name)) {
            alert("链接名称不能为空");
            return false;
        }

        if (isBlank(address)) {
            alert("链接地址不能为空");
            return false;
        }
        $("form").submit();
    });

</script>
</body>
</html>
