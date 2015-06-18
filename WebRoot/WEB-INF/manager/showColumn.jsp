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
<div style="clear:both;"></div>
<form action="<%=basePath%>manage/columnManage-delColumn" method="post">
    <table class="table" style="margin-top: 10px;">
        <thead>
        <tr>
            <th width="3%"><input type="checkbox" id="checkAll"/></th>
            <th width="15%">栏目名称</th>
            <th width="10%">栏目等级</th>
            <th width="10%">新闻数</th>
            <th width="10%">是否链接</th>
            <th width="10%">链接地址</th>
            <th width="10%">在新窗口打开</th>
            <th width="10%">在首页导航栏</th>
            <th width="10%">在首页显示</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody id="new_tab">
        <s:iterator id="item" value="#request.colmun">
            <tr id="<s:property value="#item.columnId"/>">
                <td><input type="checkbox" name="ids" value="${item.columnId}"/></td>
                <td id="name_<s:property value="#item.columnId"/>"><s:if test="#item.artColumns.size()>0"><a
                        onclick="showChild(<s:property value="#item.columnId"/>,'${item.colName}')">+</a></s:if>${item.colName}</td>
                <td id="level_<s:property value="#item.columnId"/>">${item.colLevel}</td>
                <td>${item.artCount}</td>
                <td id="isAddress_<s:property value="#item.columnId"/>"><s:if
                        test="#item.isAddress">是</s:if><s:else>否</s:else></td>
                <td id="outAddress_<s:property value="#item.columnId"/>"><s:if
                        test="#item.isAddress">${item.outAddress}</s:if></td>
                <td id="isWindowOpened_<s:property value="#item.columnId"/>"><s:if
                        test="#item.isNewWindows">是</s:if><s:else>否</s:else></td>
                <td id="isNav_<s:property value="#item.columnId"/>"><s:if
                        test="#item.isNav">是</s:if><s:else>否</s:else></td>
                <td id="isIndex_<s:property value="#item.columnId"/>"><s:if
                        test="#item.isIndex">是</s:if><s:else>否</s:else></td>
                <td id="td_<s:property value="#item.columnId"/>">
                    <button class="edit" onclick="alterColmun(<s:property
                            value="#item.columnId"/>)">编辑
                    </button>
                        <%--<input type="button" value="修改" onclick="alterColmun(<s:property--%>
                        <%--value="#item.columnId"/>)"--%>
                        <%--class="alterButton btn btn-primary"/>--%>
                </td>
            </tr>
            <s:if test="#item.artColumns.size()>0">
                <s:iterator id="child" value="#item.artColumns">
                    <tr id="<s:property value="#child.columnId"/>" class="parent_<s:property value="#item.columnId"/>" style="display: none;">
                        <td><input type="checkbox" name="ids" value="${child.columnId}"/></td>
                        <td id="name_<s:property value="#child.columnId"/>">&nbsp;&nbsp;&nbsp;&nbsp;${child.colName}</td>
                        <td id="level_<s:property value="#child.columnId"/>">${child.colLevel}</td>
                        <td>${child.artCount}</td>
                        <td id="isAddress_<s:property value="#child.columnId"/>"><s:if
                                test="#child.isAddress">是</s:if><s:else>否</s:else></td>
                        <td id="outAddress_<s:property value="#child.columnId"/>"><s:if
                                test="#child.isAddress">${child.outAddress}</s:if></td>
                        <td id="isWindowOpened_<s:property value="#child.columnId"/>"><s:if
                                test="#child.isNewWindows">是</s:if><s:else>否</s:else></td>
                        <td id="isNav_<s:property value="#child.columnId"/>"><s:if
                                test="#child.isNav">是</s:if><s:else>否</s:else></td>
                        <td id="isIndex_<s:property value="#child.columnId"/>"><s:if
                                test="#child.isIndex">是</s:if><s:else>否</s:else></td>
                        <td id="td_<s:property value="#child.columnId"/>">
                            <button class="edit" onclick="alterColmun(<s:property
                                    value="#child.columnId"/>)">编辑
                            </button>
                                <%--<input type="button" value="修改" class="alterButton btn btn-primary"/>--%>
                        </td>
                    </tr>
                </s:iterator>
            </s:if>
        </s:iterator>
        </tbody>
    </table>
</form>
<button class="delete" id="ok2del">删除</button>

<link href="<%=basePath%>css/page.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.myPagination.js" type="text/javascript"></script>

<script>
    var htmlList = new Array();
    $(document).ready(function () {
//        $(".alterButton").click(function () {
//            var but_id = this.id;
//            var id = but_id.split("_");
//            alterColmun(id[1]);
//        });
    });

    function turn(colmunId, box) {
        if (box.checked) {
            $("#outAddress__" + colmunId).show();
        } else {
            $("#outAddress__" + colmunId).hide();
        }
    }

    //取消修改
    function cancelAlter(colmunId) {
        $("#" + colmunId).html(htmlList[colmunId]);
    }

    //提交修改信息
    function submitForm(colmunId) {
        var colName = $("#colName__" + colmunId).val();
        var colLevel = $("#colLevel__" + colmunId).val();
        if (isBlank(colName)) {
            alert("栏目名称不能为空");
        }
        if (isNaN(colLevel)) {
            alert("栏目等级必须为数字");
        }
        var isAddress = $("#isAddress__" + colmunId).attr("checked");
        if (isAddress) {
            var outAddress = $("#outAddress__" + colmunId).val();
            isAddress = true;
        } else {
            isAddress = false;
        }
        var isWindowOpened = $("#isWindowOpened__" + colmunId).attr("checked");
        var isNav = $("#isNav__" + colmunId).attr("checked");
        var isIndex = $("#isIndex__" + colmunId).attr("checked");
        if (isWindowOpened) {
            isWindowOpened = true;
        } else {
            isWindowOpened = false;
        }
        if (isNav) {
            isNav = true;
        } else {
            isNav = false;
        }
        if (isIndex) {
            isIndex = true;
        } else {
            isIndex = false;
        }
        $.ajax({
            type: "POST",
            url: "<%=basePath%>manage/columnManage-saveColumnAlter",
            data: {
                "columnId": colmunId,
                "colName": colName.trim(),
                "colLevel": colLevel,
                "isAddress": isAddress,
                "outAddress": outAddress,
                "isNewWindows": isWindowOpened,
                "isNav": isNav,
                "isIndex": isIndex
            },
            success: function (data) {
                alterSuc(data, colmunId);
            }
        });
    }

    //修改成功后刷新页面
    function alterSuc(data, colmunId) {

        if (data == false) {
            alert("修改失败");
            $("#" + colmunId).html(htmlList[colmunId]);
            return;
        }
        var colName = $("#colName__" + colmunId).val();
        var colLevel = $("#colLevel__" + colmunId).val();

        if (data == 'child') {
            $("#" + colmunId + ">td").eq(1).html(colName);
        } else if(data == 'noChild') {
            $("#" + colmunId + ">td").eq(1).html(colName);
        }else{
            showChild(colmunId,colName);
        }

        var isAddress = $("#isAddress__" + colmunId).attr("checked");
        if (isAddress) {
            isAddress = "是";
            var outAddress = $("#outAddress__" + colmunId).val();
            $("#" + colmunId + ">td").eq(5).html(outAddress);
        } else {
            isAddress = "否";
        }

        var isWindowOpened = $("#isWindowOpened__" + colmunId).attr("checked");
        var isNav = $("#isNav__" + colmunId).attr("checked");
        var isIndex = $("#isIndex__" + colmunId).attr("checked");
        if (isWindowOpened) {
            isWindowOpened = "是";
        } else {
            isWindowOpened = "否";
        }
        if (isNav) {
            isNav = "是";
        } else {
            isNav = "否";
        }
        if (isIndex) {
            isIndex = "是";
        } else {
            isIndex = "否";
        }

        $("#" + colmunId + ">td").eq(0).html("<input type='checkbox' name='ids'' value='" + colmunId + "'/>");
        $("#" + colmunId + ">td").eq(2).html(colLevel);
        $("#" + colmunId + ">td").eq(4).html(isAddress);
        $("#" + colmunId + ">td").eq(6).html(isWindowOpened);
        $("#" + colmunId + ">td").eq(7).html(isNav);
        $("#" + colmunId + ">td").eq(8).html(isIndex);
        $("#" + colmunId + ">td").eq(9).html('<button class="edit"  onclick="alterColmun(' + colmunId + ')">编辑</button>');
    }

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

    //点修改的时候刷新页面
    function alterColmun(colmunId) {
        htmlList[colmunId] = $("#" + colmunId).html();
        $("#" + colmunId + ">td").eq(0).html("");
        //栏目名字
        var colName = $("#name_" + colmunId).html();
        var tmp = colName.split("</a>");
        if (tmp.length == 2) {
            colName = tmp[1];
        }
        $("#" + colmunId + ">td").eq(1).html("<input id='colName__" + colmunId + "'value='" + colName + "'  style='width: 120px'/>");
        //栏目等级
        var colLevel = $("#level_" + colmunId).html();
        $("#" + colmunId + ">td").eq(2).html("<input id='colLevel__" + colmunId + "'value='" + colLevel + "' style='width: 30px'/>");
        //是否是外部链接
        var isAddress = $("#isAddress_" + colmunId).html();
        if (isAddress == "是") {
            var outAddress = $("#outAddress_" + colmunId).html();
            $("#" + colmunId + ">td").eq(4).html("<input onclick='turn(" + colmunId + ",this)' type='checkbox' id='isAddress__" + colmunId + "' checked/>");
            $("#" + colmunId + ">td").eq(5).html("<input id='outAddress__" + colmunId + "' value='" + outAddress + "'   style='width: 120px'/>");
        } else {
            $("#" + colmunId + ">td").eq(4).html("<input onclick='turn(" + colmunId + ",this)'  type='checkbox' id='isAddress__" + colmunId + "'/>");
            $("#" + colmunId + ">td").eq(5).html("<input id='outAddress__" + colmunId + "' style='display:none;'/>");
        }

        //是否在新窗口打开
        var isWindowOpened = $("#isWindowOpened_" + colmunId).html();
        if (isWindowOpened == "是") {
            $("#" + colmunId + ">td").eq(6).html("<input type='checkbox' id='isWindowOpened__" + colmunId + "' checked/>");
        } else {
            $("#" + colmunId + ">td").eq(6).html("<input type='checkbox' id='isWindowOpened__" + colmunId + "'/>");
        }

        var isNav = $("#isNav_" + colmunId).html();
        if (isNav == "是") {
            $("#" + colmunId + ">td").eq(7).html("<input type='checkbox' id='isNav__" + colmunId + "' checked/>");
        } else {
            $("#" + colmunId + ">td").eq(7).html("<input type='checkbox' id='isNav__" + colmunId + "'/>");
        }

        var isIndex = $("#isIndex_" + colmunId).html();
        if (isIndex == "是") {
            $("#" + colmunId + ">td").eq(8).html("<input type='checkbox' id='isIndex__" + colmunId + "' checked/>");
        } else {
            $("#" + colmunId + ">td").eq(8).html("<input type='checkbox' id='isIndex__" + colmunId + "'/>");
        }

        $("#" + colmunId + ">td").eq(9).html('<input type="button" class="edit"  id="butSave_' + colmunId + '"  value="保存" onclick="submitForm(' + colmunId + ')"><button class="delete"  onclick="cancelAlter(' + colmunId + ')">取消</button>');
    }

    $("#ok2del").click(function () {
        var flag = confirm("删除此栏目将会同时删除其下所有的子栏目以及所有的文章,确认要删除吗？");
        if (flag) {
            $("form").submit();
        }
    });

    function showChild(columnId,colName) {
        $(".parent_" + columnId).show();
        $("#name_"+columnId).html("<a onclick='hideChild("+columnId+",\""+colName+"\")'>-</a>"+colName);
    }

    function hideChild(columnId,colName){
        $(".parent_" + columnId).hide();
        $("#name_"+columnId).html("<a onclick='showChild("+columnId+",\""+colName+"\")'>+</a>"+colName);
    }
</script>
<style>
a{
  cursor: hand;;
 }
</style>
</body>
</html>
