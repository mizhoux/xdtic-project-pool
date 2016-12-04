<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<h2>注册</h2>
		</header>
		<main>
			<img src="/xdtic/static/images/logo2.png" alt="西电腾讯俱乐部" />

			<form action="<c:url value='/fn/user/register' />" method="POST" class="tic-form" id="formRegister">
				<p class="tic-error-tip" 
				     v-show="hasError">{{errorMsg}}</p>
				<div class="tic-field"
					 :class="{ 'tic-error': usernameError }">
					<input type="text" name="username" placeholder="用户名" 
						 v-model="username"
						 @blur="validName"/>
				</div>
				<div class="tic-field"
					 :class="{ 'tic-error': passError }">
					<input type="password" name="pass" placeholder="密码" 
						 v-model="password"/>
				</div>
				<div class="tic-field"
					 :class="{ 'tic-error': passError }">
					<input type="password" name="passConfirm" placeholder="确认密码" 
						 v-model="passConfirm"/>
				</div>
				<div class="tic-field">
					<a class="weui-btn weui-btn_primary" id="btnRegister" v-tap.prevent="{ methods: validRegister }">注册</a>
				</div>
				<a href="<c:url value='/user/register' />" class="tic-form-para">
					已经有账号？快快登录
				</a>
			</form>
		</main>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/register.scss" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />
		
		<fis:require id="static/js/user/register.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/register.jsp" />
</fis:extends>