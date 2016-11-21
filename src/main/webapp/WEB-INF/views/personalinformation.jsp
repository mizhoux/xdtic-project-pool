<%-- 
    Document   : personalinformation
    Created on : 2016-11-19, 15:48:57
    Author     : admin
--%>

<%@page import="wenjing.xdtic.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>个人信息</title>
    </head>
    <body> 
        邮箱：${user.email}
        <br/>
        昵称：${user.nickname}
        <br/>
        姓名：${user.name}
        <br/>
        性别：${user.sex}
        <br/>
        专业：${user.profe}
        <br/>
        手机：${user.phone}
        <br/>
        学号：${user.profile}
        <br/>
        项目经历：${user.pexperice} 
        <!-- 向服务器发送 AJAX 请求获得个人信息-->
    </body>
</html>
