<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
    	<c:choose>
	    	<c:when test="${not empty user}">
				<header>
					<div class="tic-avatar">
						<div class="avatar">
							<img src="/xdtic/static/images/avatar.png" alt="西电腾讯俱乐部">
							<p><c:out value="${user.username}" /></p>
						</div>
						<div class="bg">
							<img src="/xdtic/static/images/avatar-bg.png" alt="">
						</div>
					</div>
				</header>
				<main>
					<div class="tic-four-grid" id="app">
						<a href="<c:url value='/user/msgs' />?userid=<c:out value="${user.id}" />" v-tap class="grid"
						 :class="{notice: hasMsg}">
							<img src="/xdtic/static/images/sys-msg.png" alt="系统消息">
							<p>系统消息</p>
						</a>
						<a class="grid" href="<c:url value='/user/profile' />?userid=<c:out value="${user.id}" />" v-tap>
							<img src="/xdtic/static/images/profile.png" alt="个人信息">
							<p>个人信息</p>
						</a>
						<a class="grid" href="<c:url value='/logout' />" v-tap>
							<img src="/xdtic/static/images/switch-account.png" alt="切换账号">
							<p>切换账号</p>
						</a>
						<a class="grid" href="<c:url value='/user/resetPass' />" v-tap>
							<img src="/xdtic/static/images/change-pass.png" alt="修改密码">
							<p>修改密码</p>
						</a>
					</div>
				</main>

				<nav>
				    <div class="tic-nav">
				        <div>
				        	<a href="<c:url value='/hall' />">
				                <img src="/xdtic/static/images/hall.png" alt="项目大厅">
								<p>项目大厅</p>
				        	</a>
				        </div>
				        <div>
				        	<a href="<c:url value='/myProject' />?type=join&userid=<c:out value="${user.id}" />">
				                <img src="/xdtic/static/images/my-project.png" alt="我的项目">
								<p>我的项目</p
				        	</a>
				        </div>
				        <div>
				        	<a class="selected" href="<c:url value='/user' />?userid=<c:out value="${user.id}" />">
				                <img src="/xdtic/static/images/center-selected.png" alt="个人中心">
								<p>个人中心</p>
				        	</a>
				        </div>
				    </div>
				</nav>
			</c:when>
		</c:choose>
    </fis:block>

    <fis:block name="style">
    	<fis:parent />
    	<fis:require id="static/scss/center.scss" />
    </fis:block>

	<fis:block name="jsPre">
		<script type="text/javascript">
			var userInfo = {
				id: "<c:out value="${user.id}" />"
			};
		</script>
	</fis:block>

	<fis:block name="js">
		<fis:parent />

		<fis:require id="static/js/user/center.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/center.jsp" />
</fis:extends>
