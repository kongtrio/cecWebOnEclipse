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
<input type="hidden" id="keyHidden" value="<s:property value="#request.key"/>"/>
<input type="hidden" id="typeHidden" value="<s:property value="#request.type"/>"/>
<input type="hidden" id="colIdHidden" value="<s:property value="#request.colId"/>"/>
<input type="hidden" id="stage" value="<s:property value="#request.showStage"/>"/>
<div>
    <form action="<%=basePath%>manage/artManage-toArtManage" method="post" name="form1">
        <div class="sel_wrap biaoti">
            <label>标题</label>
            <select name="type" class="select">
                <option value="title" selected>标题</option>
                <option value="content">内容</option>
                <option value="artId">ID</option>
            </select>
        </div>

            <input type="text" autocomplete="off" name="key" style="margin-left: 10px;height: 35px;width: 110px;">
            <button class="artButton"></button>

        <div class="sel_wrap biaoti" style="float:right;">
            <label>全部栏目</label>
            <select class="select" name="colId" id="typeSelect">
                <option value="0">全部栏目</option>
                <s:iterator value="#request.columns" id="item">
                    <option value="<s:property value='#item.columnId'/>">${item.colName}</option>
                    <s:if test="#item.artColumns.size()!=0">
                        <s:iterator value="#item.artColumns" id="child">
                            <option value="<s:property value='#child.columnId'/>">
                                &nbsp;&nbsp;&nbsp;-${child.colName}</option>
                        </s:iterator>
                    </s:if>
                </s:iterator>
            </select>
        </div>
    </form>
</div>
<div style="clear:both;"></div>
<table class="table" style="margin-top: 10px;">
    <thead>
    <tr>
        <th id="all"><input type="checkbox" id="checkAll"/></th>
        <th>id</th>
        <th>新闻标题</th>
        <th>发布日期</th>
        <th width="20%">类别</th>
    </tr>
    </thead>
    <tbody id="new_tab">
    <s:iterator value="#request.news" id="item">
        <tr>
            <td><input type="checkbox" name="ids" value="${item.artId}"/></td>
            <td>${item.artId}</td>
            <td>
                <a style="color: blue;" href="<%=basePath%>manage/artManage-toAlterView?artId=${item.artId}">${item.title}</a>
                <s:if test="#item.isMark==true"><img src="images/ic05.gif" height="17px;" style="margin-left:5px;" title="醒目"></s:if>
                <s:if test="#item.isSchool==true"><img src="images/ic04.gif" height="17px;" style="margin-left:5px;" title="校内新闻"></s:if>
                <s:if test="#item.isTop==true"><img src="images/ic01.gif" height="17px;" style="margin-left:5px;" title="头条新闻"></s:if>
                <s:if test="#item.isColmunTop==true"><img src="images/ic03.gif" height="17px;" style="margin-left:5px;" title="栏目置顶"></s:if>
                <s:if test="#item.isIndexTop==true"><img src="images/ic02.gif" height="17px;" style="margin-left:5px;" title="首页置顶"></s:if>
            </td>
            <td>
                <s:date name="#item.publicTime" format="yyyy-MM-dd hh:mm:ss"/>
            </td>
            <td>${item.artColumn.colName}</td>
        </tr>
    </s:iterator>
    </tbody>
</table>
<button class="delete" id="ok2del">删除</button>
<div class="news_box">
    <div class="news_conte" id="demo7" style="width:800px;">
    </div>
</div>

<link href="<%=basePath%>css/page.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.myPagination.js" type="text/javascript"></script>
<script src="<%=basePath%>js/icheck.min.js"></script>
<script>
    var pageNum = '', pageSize = '', key = '', type = '', colId = '';
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
                content += '<td><a style="color: blue;" href="<%=basePath%>manage/artManage-toAlterView?artId='+art.artId+'">' + art.title + '</a>';
                if(art.isMark){
                    content += '<img src="images/ic05.gif" height="17px;" style="margin-left:5px;" title="醒目">';
                }
                if(art.isSchool){
                    content += '<img src="images/ic04.gif" height="17px;" style="margin-left:5px;" title="校内新闻">';
                }
                if(art.isTop){
                    content += '<img src="images/ic01.gif" height="17px;" style="margin-left:5px;" title="头条">';
                }
                if(art.isColmunTop){
                    content += '<img src="images/ic03.gif" height="17px;" style="margin-left:5px;" title="栏目置顶">';
                }
                if(art.isIndexTop){
                    content += '<img src="images/ic02.gif" height="17px;" style="margin-left:5px;" title="首页置顶">';
                }
                content +='</td>';
                content += '<td>' + new Date(art.publicTime.time).Format("yyyy-MM-dd hh:mm:ss") + '</td>';
                content += '<td>' + art.artColumnName + '</td>';
                content += '</tr>';
            }
            $('#new_tab').html(content);
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
                url: "<%=basePath%>manage/artManage-showNewByPage",
                dataType: 'json',//传参数到后台,on一定要变成true
                param: {"on": true, "key": key, "type": type, "colId": colId}
            }
        });
    }

    function prepare() {
        pageNum = $('#pageNum').val();
        pageSize = pageNum > 10 ? 10 : pageNum;
        var keyHidden = $('#keyHidden').val();
        var typeHidden = $('#typeHidden').val();
        var colIdHidden = $('#colIdHidden').val();
        $('input[name="key"]').val(keyHidden);
        if (isNotBlank(typeHidden)) {
            $('select[name="type"]').val(typeHidden);
        }
        if (isNotBlank(colIdHidden)) {
            $('#typeSelect').val(colIdHidden);
        }
        key = encodeURI(keyHidden);
        type = typeHidden;
        colId = colIdHidden;
        if (pageNum > 1) {
            getData();
        }
    }

    $('#all').on('ifClicked', function () {
        var flag = $('#checkAll').is(':checked');
        if(!flag){
            $("input[name='ids']").each(function () {
                this.checked = true;
            });
        }else{
            $("input[name='ids']").each(function () {
                this.checked = false;
            });
        }
    });

    $('#lookByKey').click(function () {
        key = $('input[name="key"]').val();
        type = $('select[name="colId"] option:selected').val();
        $('form[name="form1"]').submit();
    });

    $('#typeSelect').change(function () {
        $('input[name="key"]').val('');
        $('form[name="form1"]').submit();
    });

    $('#ok2del').click(function () {
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
        var flag = confirm("确定要删除这些新闻吗?");
        if(flag){
            window.location = "<%=basePath%>manage/artManage-deleteNews?colId=" + colId + "&stage=showNews&ids=" + ids
        }
            ;
    });
    prepare();
</script>
</body>
</html>
