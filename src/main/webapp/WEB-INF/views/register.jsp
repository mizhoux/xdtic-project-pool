<%-- 
    Document   : register
    Created on : 2016-11-18, 22:31:56
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>注册</title>
    </head>
    <body>
        <form method="POST" action="/user/register"></form>
        邮箱 <input type="text" name="email">
        用户名<input type="text" name="username">
        密码<input type="password" name="password">
        <input type="submit" value="注册" >
    </body>
</html>
