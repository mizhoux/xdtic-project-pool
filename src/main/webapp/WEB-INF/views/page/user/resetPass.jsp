<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<div class="tic-header-three">
				<a href="javascript:window.history.go(-1);" v-tap class="tic-header-three-aside">
        			<img src="/xdtic/static/images/arrow_prev.png" alt="后退">
    			</a>
				<h2>修改密码</h2>
				<span></span>
			</div>
		</header>
		<main>
			<img src="/xdtic/static/images/change-pass-big.png" alt="西电腾讯俱乐部" />

			<form action="<c:url value='/fn/user/resetPass' />" method="POST" class="tic-form" id="formResetPass">
				<p class="tic-error-tip" 
				     v-show="hasError">{{errorMsg}}</p>
				<div class="tic-field"
					 :class="{ 'tic-error': userError }">
					<input type="text" name="username" placeholder="用户名" 
						 v-model="username"/>
				</div>
				<div class="tic-field"
					 :class="{ 'tic-error': userError }">
					<input type="password" name="passOld" placeholder="原始密码" 
						 v-model="passOld"
						 @blur="validUser" />
				</div>
				<div class="tic-field"
					 :class="{ 'tic-error': passError }">
					<input type="password" name="passNew" placeholder="新密码" 
						 v-model="passNew"/>
				</div>
				<div class="tic-field"
					 :class="{ 'tic-error': passError }">
					<input type="password" name="passNewConfirm" placeholder="确认新密码" 
						 v-model="passNewConfirm"/>
				</div>
				<div class="tic-field">
					<button class="weui-btn weui-btn_primary" id="btnRestPass" v-tap.prevent="{ methods: validForm }">确认修改</button>
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
		
		<fis:require id="static/js/user/resetPass.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/resetPass.jsp" />
</fis:extends>