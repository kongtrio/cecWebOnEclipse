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
<input type="hidden" id="pageNum" value="<s:property value="#request.news.pageNum"/>"/>
<input type="hidden" id="stage" value="<s:property value="#request.stage"/>"/>
<input type="hidden" id="typeValue" value="<s:property value='#request.type'></s:property> "/>
<input type="hidden" id="colValue" value="<s:property value='#request.colId'></s:property> "/>
<input type="hidden" id="is2Top" value="<s:property value='#request.is2Top'></s:property> "/>
<input type="hidden" id="result" value="<s:property value='#request.result'></s:property> "/>

<div class="seach" style="height: 50px;">
    <form action="<%=basePath%>manage/topManage-toTopPage" method="post" name="form1">
        <input type="hidden" name="stage" value="showNews"/>
        <input type="hidden" name="selectTop" value="false"/>

        <input id="ok2top" class="edit" value="置顶选中的新闻"  type="button" style="margin-top: 15px;width: 100px"/>
        <input id="ok2cancelTop" class="delete" width="100px" value="取消置顶" type="button" style="margin-top: 15px;;width: 100px"/>

        <select name="is2Top" id="is2TopSelect" style="float: right">
            <option value="true">查看置顶的新闻</option>
            <option value="false">查看没有置顶的新闻</option>
        </select>
        <select name="colId" id="typeSelect" style="float:right;">
            <option value="0">全部</option>
            <s:iterator value="#request.columns" id="item">
                <option value="<s:property value='#item.columnId'/>">${item.colName}</option>
                <s:if test="#item.artColumns.size()!=0">
                    <s:iterator value="#item.artColumns" id="child">
                        <option value="<s:property value='#child.columnId'/>">
                            &nbsp;&nbsp;&nbsp;&nbsp;-${child.colName}</option>
                    </s:iterator>
                </s:if>
            </s:iterator>
        </select>
        <select name="type" id="topType" style="float:right;">
            <option value="isTop">头条新闻</option>
            <option value="isColmunTop">栏目置顶</option>
            <option value="isIndexTop">首页置顶</option>
        </select>
    </form>
</div>

<div class="list">
    <table class="table table-striped">
        <thead>
        <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>id</th>
            <th>新闻标题</th>
            <th>发布日期</th>
            <th>类别</th>
        </tr>
        </thead>
        <tbody id="new_tab">
        <s:iterator value="#request.news" id="item">
            <tr>
                <td><input type="checkbox" name="ids" value="${item.artId}"/></td>
                <td>${item.artId}</td>
                <td>${item.title}</td>
                <td>
                    <s:date name="#item.publicTime" format="yyyy-MM-dd hh:mm:ss"/>
                </td>
                <td>${item.artColumn.colName}</td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <div class="news_box">
        <div class="news_conte" id="demo7" style="width:800px;">
        </div>
    </div>
</div>
<link href="<%=basePath%>css/page.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.myPagination.js" type="text/javascript"></script>
<script type="text/javascript">
    var result = $("#result").val();
    var stage = $('#stage').val();
    if (stage == "two") {
        $("#second_tab").addClass("display");
        $('.top ul li:eq(1)').attr("id", "active");
    } else {
        $("#first_tab").addClass("display");
        $('.top ul li').first().attr("id", "active");
    }

    if (result == "suc2top") {
        alert("修改置顶状态成功");
    } else if (result == "fail2top") {
        alert("修改置顶状态失败");
    }

    var type = $("#typeValue").val();
    var colId = $("#colValue").val();
    var is2Top = $("#is2Top").val();
    prepare();
    $('select[name="type"]').val(type.trim());
    $('select[name="colId"]').val(colId.trim());
    $('select[name="is2Top"]').val(is2Top.trim());

    $('#typeSelect').change(function () {
        $('form[name="form1"]').submit();
    });

    $('#topType').change(function () {
        $('form[name="form1"]').submit();
    });

    $('select[name="is2Top"]').change(function () {
        $('form[name="form1"]').submit();
    });

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

    $("#ok2top").click(function () {
        submitTopChange(true);
    });

    $("#ok2cancelTop").click(function () {
        submitTopChange(false);
    });

    function submitTopChange(changeTop) {
        var ids = "";
        $("input[name='ids']").each(function (i) {
            if (this.checked) {
                ids += this.value + ",";
            }
        });
        if (isBlank(ids)) {
            return false;
        } else {
            ids = ids.substring(0, ids.length - 1);
        }
        window.location = "<%=basePath%>manage/topManage-changeTop?topChange=" + changeTop + "&colId=" + colId + "&type=" + type + "&is2Top=" + !!is2Top + "&ids=" + ids;
    }

    //分页查看
    var pageNum = '', pageSize = '';
    function callSuc(data) {
        var result = data.result;
        if (result == "suc") {
            var news = data.news;
            var content = "";
            for (var i = 0; i < news.length; i++) {
                var art = news[i];
                content += '<tr>';
                content += '<td><input type="checkbox" name="ids"  value="' + art.artId + '"/></td>';
                content += '<td>' + art.artId + '</td>';
                content += '<td>' + art.title + '</td>';
                content += '<td>' + new Date(art.publicTime.time).Format("yyyy-MM-dd hh:mm:ss") + '</td>';
                content += '<td>' + art.artColumnName + '</td>';
                content += '</tr>';
            }
            $('#new_tab').html(content);
        } else {
            alert("系统异常");
        }
    }

    function getData() {
        $("#demo7").myPagination({
            currPage: 1,
            pageCount: pageNum,
            pageSize: pageSize,
            cssStyle: 'tres',
            ajax: {
                on: true,
                callback: 'callSuc',
                url: "<%=basePath%>manage/topManage-showTopNewByPage",
                dataType: 'json',//传参数到后台,on一定要变成true
                param: {"on": true, "type": type, "colId": colId, "is2Top": is2Top}
            }
        });
    }

    function prepare() {
        pageNum = $('#pageNum').val();
        pageSize = pageNum > 10 ? 10 : pageNum;
        if (pageNum > 1) {
            getData();
        }
    }
</script>
</body>
</html>
