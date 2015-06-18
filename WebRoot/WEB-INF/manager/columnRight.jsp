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

<table class="table">
    <tr>
        <th width="15%">栏目名称</th>
        <th width="40%">权限拥有者</th>
        <th width="25%">操作</th>
    </tr>
    <s:iterator id="item" value="#request.colmun">
        <tr id="<s:property value="#item.columnId"/>">
            <td id="name_<s:property value="#item.columnId"/>"><s:if test="#item.artColumns.size()>0"><a
                    onclick="showChild(<s:property value="#item.columnId"/>,'${item.colName}')">+</a></s:if>${item.colName}</td>
            <td id="right_<s:property value="#item.columnId"/>">
                <s:iterator value="#item.colmunRights" id="columnRight">
                    ${columnRight.users.userName}<input type="button"
                                                        onclick="delRight(${item.columnId},${columnRight.users.userId})"
                                                        class="btn-danger btn" value="×"/>
                </s:iterator>
            </td>
            <td>
                <select id="userId_${item.columnId}">
                    <s:iterator value="#request.users" id="user">
                        <option value="${user.userId}">${user.userName}</option>
                    </s:iterator>
                </select>
                <input type="button" class="edit" style="margin-top: -10px;width: 100px;"
                       onclick="addRight(${item.columnId})" value="添加权限"/>
            </td>
        </tr>
        <s:if test="#item.artColumns.size()>0">
            <s:iterator id="child" value="#item.artColumns">
                <tr id="<s:property value="#child.columnId"/>" class="parent_<s:property value="#item.columnId"/>" style="display: none;">
                    <td id="name_<s:property value="#child.columnId"/>">&nbsp;&nbsp;&nbsp;&nbsp;${child.colName}</td>
                    <td  id="right_<s:property value="#child.columnId"/>">
                        <s:iterator value="#child.colmunRights" id="columnRight">
                            ${columnRight.users.userName}<input type="button"
                                                                onclick="delRight(${child.columnId},${columnRight.users.userId})"
                                                                class="btn-danger btn" value="×"/>
                        </s:iterator>
                    </td>
                    <td>
                        <select id="userId_${child.columnId}">
                            <s:iterator value="#request.users" id="user">
                                <option value="${user.userId}">${user.userName}</option>
                            </s:iterator>
                        </select>
                        <input type="button" class="edit" style="margin-top: -10px;width: 100px;"
                               onclick="addRight(${child.columnId})" value="添加权限"/>
                    </td>
                </tr>
            </s:iterator>
        </s:if>
    </s:iterator>
</table>
<script type="text/javascript">
    function addRight(columnId) {
        var userId = $("#userId_" + columnId).val();
        $.ajax({
            type: "GET",
            url: "<%=basePath%>manage/columnRightManage-addRight?userId=" + userId + "&columnId=" + columnId,
            success: function (data) {
                var data = eval("(" + data + ")");
                if (data.result) {
                    changeRight(data.columnId, data.users);
                } else {
                    alert("系统出错，请联系管理员");
                }
            }
        });
    }

    function delRight(columnId, userId) {
        $.ajax({
            type: "GET",
            url: "<%=basePath%>manage/columnRightManage-delRight?userId=" + userId + "&columnId=" + columnId,
            success: function (data) {
                var data = eval("(" + data + ")");
                if (data.result) {
                    changeRight(data.columnId, data.users);
                } else {
                    alert("系统出错，请联系管理员");
                }
            }
        });
    }


    function changeRight(columnId, users) {
        var content = "";
        for (var i = 0; i < users.length; i++) {
            var str = users[i].split("_");
            content += str[1] + "<input type='button' onclick='delRight(" + columnId + "," + str[0] + ")'  class='btn-danger btn'  value='×'/>";
        }
        $("#right_" + columnId).html(content);
    }

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
