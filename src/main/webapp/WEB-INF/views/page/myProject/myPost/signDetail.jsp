<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<fis:widget name="page/widget/header/signInfoHeader.jsp" />	
		</header>

		<main id="signDetailContent">
			<div class="tic-sign-info-wrap">
				<div class="tic-left">
					<img src="/xdtic/static/images/avatar.png" alt="<c:out value='${signInfo.username}' />">
				</div>
				<div class="tic-center">
					<p>
						<span class="tic-font-strong"><c:out value="${signUser.username}" /></span>
						<span>报名了</span>
						<span class="tic-font-strong"><c:out value="${signInfo.apply}" /></span>
					</p>
					<p class="tic-font-secondary">
						<c:out value="${fn:split(signInfo.signDate, ' ')[0]}" />
						&nbsp;
						<c:out value="${fn:split(signInfo.signDate, ' ')[1]}" />
					</p>
				</div>
				<div class="tic-right">
				</div>
			</div>
			<hr>
			<div class="tic-table">
				<form class="tic-form">
					<div class="tic-tr-two">
						<span class="tic-td-label">用户名</span>
						<span class="tic-td-content"><c:out value='${signUser.username}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">邮箱</span>
						<span class="tic-td-content"><c:out value='${signUser.email}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">性别</span>
						<c:set var="userSex" value="男" />
						<c:if test="${signUser.gender == 'F'}">
							<c:set var="userSex" value="女" />
						</c:if>
						<span class="tic-td-content"><c:out value='${userSex}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">专业</span>
						<span class="tic-td-content"><c:out value='${signUser.major}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">手机</span>
						<span class="tic-td-content"><c:out value='${signUser.phone}' /></span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">学号</span>
						<span class="tic-td-content"><c:out value='${signUser.stuNum}' /></span>
					</div>
					<hr>
					<div class="tic-tr-two">
						<span class="tic-td-label">个人能力</span>
						<span class="tic-td-content">{{signUserSkill}}</span>
					</div>
					<hr>
					<div class="tic-tr-two">
						<span class="tic-td-label">项目经历</span>
						<span class="tic-td-content">{{signUserExperience}}</span>
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
		<script type="text/javascript">
			var getMultiline = function(f) {
				return f.toString().replace(/^[^\/]+\/\*!?\s?/, '')
					.replace(/\*\/[^\/]+$/, '').trim();
			};

			var signUserInfo = {
				skill: getMultiline(function() {/*
					<c:out value="${signUser.skill}" />
				*/
				}),
				experience: getMultiline(function() {/*
					<c:out value="${signUser.experience}" />
				*/
				})
			};
		</script>
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		<fis:require id="static/js/myProject/myPost/signDetail.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myPost/signDetail.jsp" />
</fis:extends>