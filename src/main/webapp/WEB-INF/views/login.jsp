<%-- 
    Document   : login
    Created on : 2016-11-18, 15:28:00
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登录</title>
    </head>
    <body>
        <form method="POST" action="./user/login">
            用户名：<input type="text" name="username" /><br/><br/>
            密码：<input type="password" name="password" /><br/><br/>
            <input type="submit" value="登录" />
        </form>
        <br/>
        <a href="register"><button>注册</button></a>
    </body>
</html>
