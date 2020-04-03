<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/17
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户</title>
    <script src="js/jquery-3.3.1.js"></script>
    <script>
        $(function () {
           $("#btn").click(function () {
               $.post("user/testUser",{name:"邹榕",password:"requestPwd",age:18},function (data) {
                   $("#contentDiv").html("姓名："+data.name+";密码："+data.password+";年龄："+data.age);
               });
           }) ;
        });
    </script>
</head>
<body>
<input type="button" value="发送ajax" id="btn">
<div id="contentDiv"></div>

<form action="user/upload1" method="post" enctype="multipart/form-data">
    请选择文件：<input type="file" name="file">
    <br/>
    <input type="submit" value="上传">
</form>

<form action="user/upload2" method="post" enctype="multipart/form-data">
    跨服务器上传：<input type="file" name="file">
    <br/>
    <input type="submit" value="上传">
</form>
</body>
</html>
