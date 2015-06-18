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
<form action='<%=basePath%>manage/columnManage-addColumn' method='post' enctype="multipart/form-data">
    <input type="hidden" id="isAddress" name="isAddress"/>
    <input type="hidden" id="isNewWindows" name="isNewWindows"/>
    <input type="hidden" id="isNav" name="isNav"/>
    <input type="hidden" id="isIndex" name="isIndex"/>
    父栏目:
    <div class="sel_wrap" style="margin-top: 10px;">
        <label>请选择栏目名称</label>
        <select class="select" name="parentColId" required>
            <option value="0">作为一级栏目</option>
            <s:iterator value="#request.parentCol" id="item">
                <option value="<s:property value='#item.columnId'></s:property>">${item.colName}</option>
            </s:iterator>
        </select>
    </div>

<div class="title">
    栏目名称：<input type="text" name="colName" autocomplete="off"/> <br/>
    栏目等级：<input type="text"  name="colLevel" autocomplete="off"/> <br/>
</div>
</select>
<hr style="margin-top: 20px;width: 980px"/>
<div>
    <div class="islink">
        <input type="checkbox" id="islink"><label for="islink" unselectable="on"  id="isAddress_check"
                                                  onselectstart="return false;">是否为外部链接</label>
    </div>
    <div class="link">
        外部链接地址: <input type="text" class="url" id='outAddress' name='outAddress' disabled/>
    </div>
    <hr style="margin-top: 20px;width: 980px"/>

    <div class="check" id="topDiv">
        <input type="checkbox" id="isWindow_check"><label for="isWindow_check" unselectable="on"
                                                      onselectstart="return false;">是否打开一个新窗口</label>
    </div>
    <br/>
    <div class="check" id="navDiv">
        <input type="checkbox"  id="isNav_check"><label for="isNav_check" unselectable="on"
                                                onselectstart="return false;">是否在首页菜单显示</label>
    </div>
    <br/>
    <div class="check" id="indexTopDiv">
        <input type="checkbox"  id="isIndexTop_check"><label for="isIndexTop_check" unselectable="on"
                                                onselectstart="return false;">是否首页栏目</label>
    </div>
</div>
</form>
<button class="sub" id="addColumn" style="cursor: hand;top:20px;">添加栏目</button>
<script src="<%=basePath%>js/icheck.min.js"></script>
<script>
    var isAddress = false;
    var result = $("#result").val();
    if (result == "success") {
        alert("添加成功");
    } else if (result == "fail2Name") {
        alert("栏目名字已存在");
    }

    $('#topDiv').on('ifClicked', function () {
        var isTop= $('#isWindow_check').is(':checked');
        $('#isNewWindows').val(!isTop);
    });

    $('#navDiv').on('ifClicked', function () {
        $('#isNav').val(!$('#isNav_check').is(':checked'));
    });

    $('#indexTopDiv').on('ifClicked', function () {
        $('#isIndex').val(!$('#isIndexTop_check').is(':checked'));
    });

    $('.islink').on('ifClicked', function () {
        if ($('#islink').is(':checked')) {
            $("#editor").show();
            isAddress = false;
            $('#isAddress').val(false);
            $(".url").attr("disabled", true);
        } else {
            $("#editor").hide();
            isAddress = true;
            $('#isAddress').val(true);
            $(".url").attr("disabled", false);
        }
    });

    function dataVerify() {
        var colName = $("input[name='colName']").val();
        if (isBlank(colName)) {
            alert("栏目名称不能为空");
            return false;
        }
        var colLevel = $("input[name='colLevel']").val();
        if (isNaN(colLevel)) {
            alert("栏目等级必须是纯数字");
            return false;
        } else if (isBlank(colLevel)) {
            alert("栏目等级不能为空");
            return false;
        }

        if (isAddress) {
            var outAddress = $("#outAddress").val();
            if (isBlank(outAddress)) {
                alert("链接地址不能为空");
                return false;
            }
        }
        return true;
    }

    $("#addColumn").click(function () {
        var flag =  dataVerify();
        if(flag == true){
            $("form").submit();
        }
    });
</script>
</body>
</html>
