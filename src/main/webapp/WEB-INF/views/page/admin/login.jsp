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

			<form action="<c:url value='/fn/admin/login' />" method="POST" class="tic-form" id="formLogin">
				<c:if test="${loginFail}">
					<p class="tic-error-tip" >用户名或密码错误</p>
				</c:if>
				<div class="tic-field">
					<input type="text" name="username" placeholder="用户名" />
				</div>
				<div class="tic-field">
					<input type="password" name="password" placeholder="密码" />
				</div>
				<div class="tic-field">
					<input type="submit" value="登录" class="weui-btn weui-btn_primary" id="btnLogin">
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
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/login.jsp" />
</fis:extends>