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
<span style="font-weight:blod;font-size: 21px;">后台管理白名单</span>
<hr/>
<span>ip地址:</span><input style="margin-left: 5px;" id="manage_ip"/>
<span style="margin-left: 10px;">描述:</span><input style="margin-left: 5px;" id="manage_remark"/>
<input type="button" class="edit" style="width: 60px;margin-top: 5px;margin-left: 10px;" value="添加Ip"
       onclick="addIps(0)">

<div style="border: 1px solid #000000;min-height: 40px;margin-top: 10px;" id="managerIps">
    <s:iterator value="#request.managerIps" id="item">
        ${item.ip} <s:if test="#item.remark!=null && #item.remark!=''">(${item.remark})</s:if>
        <input type="button" class="delete" style="width: 60px;height:20px;margin-top: 5px;margin-left: 10px;"
               value="删除"  onclick="del(0,${item.id})">
        <br/>
    </s:iterator>
</div>
<br/><br/>

<span style="font-weight:blod;font-size: 21px;">校内新闻白名单</span>
<hr/>
<span>ip地址:</span><input style="margin-left: 5px;" id="school_ip"/>
<span style="margin-left: 10px;">描述:</span><input style="margin-left: 5px;" id="school_remark"/>
<input type="button" class="edit" style="width: 60px;margin-top: 5px;margin-left: 10px;" value="添加Ip"
       onclick="addIps(1)">

<div style="border: 1px solid #000000;min-height: 40px;margin-top: 10px;" id="schoolIps">
    <s:iterator value="#request.schoolIps" id="item">
        ${item.ip} <s:if test="#item.remark!=null && #item.remark!=''">(${item.remark})</s:if>
        <input type="button" class="delete" style="width: 60px;height:20px;margin-top: 5px;margin-left: 10px;"
               value="删除" onclick="del(1,${item.id})">
        <br/>
    </s:iterator>
</div>
<br/><br/>

<span style="font-weight:blod;font-size: 21px;">系统访问黑名单</span>
<hr/>
<span>ip地址:</span><input style="margin-left: 5px;" id="system_ip"/>
<span style="margin-left: 10px;">描述:</span><input style="margin-left: 5px;" id="system_remark"/>
<input type="button" class="edit" style="width: 60px;margin-top: 5px;margin-left: 10px;" value="添加Ip"
       onclick="addIps(2)">

<div style="border: 1px solid #000000;min-height: 40px;margin-top: 10px;" id="systemIps">
    <s:iterator value="#request.systemIps" id="item">
        ${item.ip} <s:if test="#item.remark!=null && #item.remark!=''">(${item.remark})</s:if>
        <input type="button" class="delete" style="width: 60px;height:20px;margin-top: 5px;margin-left: 10px;"
               value="删除"  onclick="del(2,${item.id})">
        <br/>
    </s:iterator>
</div>

<div style="margin-top: 10px;">
    <span style="color: #ff0000;">备注:IP地址必须规范,*表示匹配全部,比如 210.* 表示匹配所有210开头的IP地址</span>
</div>

<script>
    function addIps(type) {
        var ip, remark;
        if (type == 0) {
            ip = $("#manage_ip").val();
            remark = $("#manage_remark").val();
        } else if (type == 1) {
            ip = $("#school_ip").val();
            remark = $("#school_remark").val();
        } else if (type == 2) {
            ip = $("#system_ip").val();
            remark = $("#system_remark").val();
        }

        var res = isIp(ip);
        if(res==false){
            alert("ip不合法,请重新输入ip");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "<%=basePath%>manage/ipcontroladd",
            data: {
                "ip": ip,
                "remark": remark.trim(),
                "type": type
            },
            success: function (data) {
                var divName = "";
                if (type == 0) {
                    divName = "managerIps";
                } else if (type == 1) {
                    divName = "schoolIps";
                } else if (type == 2) {
                    divName = "systemIps";
                }
                flushDiv(data,divName);
            }
        });


    }

    function del(type,id){
        $.ajax({
            type: "POST",
            url: "<%=basePath%>manage/ipcontroldel",
            data: {
                "id": id,
                "type": type
            },
            success: function (data) {
                var divName = "";
                if (type == 0) {
                    divName = "managerIps";
                } else if (type == 1) {
                    divName = "schoolIps";
                } else if (type == 2) {
                    divName = "systemIps";
                }
                flushDiv(data,divName);
            }
        });
    }

    function flushDiv(data,divName){
        data = eval("("+data+")");
        var res = data.res;
        if(res==true){
            var ips = data.ips;
            var content = "";
            for (var i = 0; i < ips.length; i++) {
                content+=ips[i].ip;
                if(ips[i].remark!="" && ips[i].remark!=null){
                    content+="("+ips[i].remark+")";
                }
                content+= '<input type="button" class="delete" style="width: 60px;height:20px;margin-top: 5px;margin-left: 10px;"value="删除"  onclick="del('+ips[i].type+','+ips[i].id+')"> <br/>';
            }
            $("#"+divName).html(content);
        }else{
            $("#"+divName).html("");
        }

    }

    //验证ip地址
    function isIp(ip) {
        var reg = /^(\d{1,3}\.){3}\d{1,3}$/;
        var res = reg.test(ip);
        if(res==false){
            reg = /^(\d{1,3}\.){0,3}\*$/;
            res = reg.test(ip);
        }else{
            return true;
        }
        return res;
    }
</script>
</body>
</html>
