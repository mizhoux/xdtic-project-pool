<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<fis:widget name="page/widget/header/signInfoHeader.jsp" />	
		</header>

		<main>
			<div class="tic-sign-info-wrap">
				<div class="tic-left">
					<img src="/xdtic/static/images/avatar.png" alt="<c:out value='${signInfo.username}' />">
				</div>
				<div class="tic-center">
					<p>
						<span class="tic-font-strong"><c:out value="${signInfo.username}" /></span>
						<span>报名了</span>
						<span class="tic-font-strong"><c:out value="${signInfo.apply}" /></span>
					</p>
					<p class="tic-font-secondary">
						<c:out value="${signInfo.date}" />
						&nbsp;
						<c:out value="${signInfo.time}" />
					</p>
				</div>
				<div class="tic-right">
				</div>
			</div>
			<hr>
			<div class="tic-table">
				<form class="tic-form">
					<div class="tic-tr-two">
						<span class="tic-td-label">昵称</span>
						<span class="tic-td-content"><c:out value='${user.username}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">邮箱</span>
						<span class="tic-td-content"><c:out value='${user.email}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">性别</span>
						<c:set var="userSex" value="男" />
						<c:if test="${user.sex == 'girl'}">
							<c:set var="userSex" value="女" />
						</c:if>
						<span class="tic-td-content"><c:out value='${userSex}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">专业</span>
						<span class="tic-td-content"><c:out value='${user.profe}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">手机</span>
						<span class="tic-td-content"><c:out value='${user.phone}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">学号</span>
						<span class="tic-td-content"><c:out value='${user.stunum}' /></span>
					</div>
					<hr>
					<div class="tic-tr-two">
						<span class="tic-td-label">个人能力</span>
						<span class="tic-td-content"><c:out value='${user.profile}' /></span>
					</div>
					<hr>
					<div class="tic-tr-two">
						<span class="tic-td-label">项目经历</span>
						<span class="tic-td-content"><c:out value='${user.pexperice}' /></span>
					</div>
				</form>
			</div>
		</main>


	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/signDetail.scss" />
	</fis:block>

	<fis:block name="jsPre">
    </fis:block>

	<fis:block name="js">
		<fis:parent />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myPost/signDetail.jsp" />
</fis:extends>