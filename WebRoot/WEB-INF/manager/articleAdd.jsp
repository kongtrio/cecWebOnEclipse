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

<form action='<%=basePath%>manage/artManage-addNews' method='post' enctype="multipart/form-data">
    <input type="hidden" id="isTop" name="isTop" value="false"/>
    <input type="hidden" id="isColmunTop" name="isColmunTop" value="false"/>
    <input type="hidden" id="isIndexTop" name="isIndexTop" value="false"/>
    <input type="hidden" id="isAddress" name="isAddress" value="false"/>
    <input type="hidden" id="isMark" name="isMark" value="false"/>
    <input type="hidden" id="isSchool" name="isSchool" value="false"/>
    <input type="hidden" id="content" name="content"/>

    <div class="sel_wrap">
        <label>请选择栏目名称</label>
        <select class="select" name="colId" required>
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

    <div class="title">
        标题：<input type="text" name="title" autocomplete="off"/> <br/>
        作者：
        <s:if test="#session.user.uLevel==true">
            <input type="text" name="author" autocomplete="off"/>
        </s:if>
        <s:else>
            <s:property value="#session.user.nickName"></s:property>
            <input type="hidden" name="author" value="<s:property value="#session.user.nickName"/>"/>
        </s:else>

        <br/>
        简介：<input type="text" name="summary" autocomplete="off"/> <br/>
        图片：<input type="file" name="artImage" style="border: none"/>
    </div>
    <hr style="margin-top: 20px;width: 980px"/>
    <div>
        <div class="islink">
            <input type="checkbox" id="islink"><label for="islink" unselectable="on" id="isAddress_check"
                                                      onselectstart="return false;">是否为外部链接</label>
        </div>
        <div class="link">
            外部链接地址: <input type="text" class="url" id='outAddress' name='outAddress' disabled/>
        </div>
        <hr style="margin-top: 20px;width: 980px"/>
        <div class="check" id="topDiv">
            <input type="checkbox" id="isTop_check"><label for="isTop_check" unselectable="on"
                                                           onselectstart="return false;">会议通知</label>
        </div>
        <div class="check" id="colmunTopDiv">
            <input type="checkbox" id="isColmunTop_check"><label for="isColmunTop_check" unselectable="on"
                                                                 onselectstart="return false;">栏目置顶</label>
        </div>
        <div class="check" id="indexTopDiv">
            <input type="checkbox" id="isIndexTop_check"><label for="isIndexTop_check" unselectable="on"
                                                                onselectstart="return false;">通知公告</label>
        </div>
        <div class="check" id="isMarkDiv">
            <input type="checkbox" id="isMark_check"><label for="isMark_check" unselectable="on"
                                                            onselectstart="return false;">醒目</label>
        </div>
        <div class="check" id="isSchoolDiv">
            <input type="checkbox" id="isSchool_check"><label for="isSchool_check" unselectable="on"
                                                              onselectstart="return false;">校内新闻</label>
        </div>
    </div>
</form>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/ueditor.all.min.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="<%=basePath%>js/icheck.min.js"></script>
<!--编辑器-->
<script id="editor" type="text/plain"></script>
<button class="sub" id="addNew" style="cursor: hand">发布</button>
<script>
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

    var result = $('#result').val();
    var isAddress = false;
    if (result == 'addSuc') {
        alert("成功发布新闻");
    } else if (result == "error") {
        alert("发布新闻失败");
    } else if (result == "ColumnErr") {
        alert("不能选择带有子栏目的一级栏目");
    }

    $('#topDiv').on('ifClicked', function () {
        var isTop = $('#isTop_check').is(':checked');
        $('#isTop').val(!isTop);
    });

    $('#colmunTopDiv').on('ifClicked', function () {
        $('#isColmunTop').val(!$('#isColmunTop_check').is(':checked'));
    });

    $('#indexTopDiv').on('ifClicked', function () {
        $('#isIndexTop').val(!$('#isIndexTop_check').is(':checked'));
    });

    $('#isMarkDiv').on('ifClicked', function () {
        $('#isMark').val(!$('#isMark_check').is(':checked'));
    });

    $('#isSchoolDiv').on('ifClicked', function () {
        $('#isSchool').val(!$('#isMSchool_check').is(':checked'));
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

    $('#addNew').click(function () {
        var res = verify();
        var flag = false;
        switch (res) {
            case 'title':
                alert('新闻标题不能为空');
                break;
            case 'outAddress':
                alert('请输入外部链接');
                break;
            case 'content':
                alert('请输入新闻正文');
                break;
            case 'author':
                alert('请输入发布人名字');
                break;
            default:
                flag = true;
        }
        if (flag == true) {
            $("form").submit();
        }
    });

    function verify() {
        var title = $('input[name="title"]').val();
        if (isBlank(title)) {
            return 'title';
        }
        if (isAddress) {
            var outAddress = $('input[name="outAddress"]').val();
            if (isBlank(outAddress)) {
                return 'outAddress';
            }
        } else {
            var content = UE.getEditor('editor').getContent();
            if (isBlank(content)) {
                return 'content';
            }
            $("#content").val(content);
        }

        var author = $('input[name="author"]').val();
        if (isBlank(author)) {
            return 'author';
        }

        return 'suc';
    }
</script>
</body>
</html>
