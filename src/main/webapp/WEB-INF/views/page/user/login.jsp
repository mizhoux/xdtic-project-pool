<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
        <header>
            <h2>登录</h2>
        </header>
        <main>
            <img src="/xdtic/static/images/logo2.png" alt="西电腾讯俱乐部" />

            <form action="/xdtic/fn/user/login" method="POST" class="tic-form" id="formLogin">
                <p class="tic-error-tip" 
                   v-show="hasError">用户名或密码错误</p>
                <div class="tic-field"
                     :class="{ 'tic-error': hasError }">
                     <input type="text" name="username" placeholder="用户名" 
                       v-model="userName"/>
                </div>
                <div class="tic-field"
                     :class="{ 'tic-error': hasError }">
                     <input type="password" name="password" placeholder="密码" 
                       v-model="password"/>
                </div>
                <div class="tic-field">
                    <a class="weui-btn weui-btn_primary" id="btnLogin" v-tap.prevent="{ methods: validUser }">登录</a>
                </div>
            </form>
        </main>
    </fis:block>

    <fis:block name="style">
        <fis:parent />
        <fis:require id="static/scss/register.scss" />
    </fis:block>

    <fis:block name="js">
        <fis:parent />
        <fis:require id="static/libs/mod.js" />
        <fis:require id="static/js/user/login.js" />
    </fis:block>

    <%-- auto inject by fis3-preprocess-extlang--%>
    <fis:require name="page/user/login.jsp" />
</fis:extends>