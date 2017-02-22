<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<fis:widget name="page/widget/header/signInfoHeader.jsp" />	
		</header>

		<main>
			<div class="tic-sign-info-box" id="appSignInfo">
				<c:forEach items="${signInfos}" var="signInfo">
					<a href="<c:url value='/signInfo' />?signId=<c:out value='${signInfo.sid}' />" class="tic-sign-info-link">
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
								<img src="/xdtic/static/images/myProject/arrow-right.png" alt="查看详情">
							</div>
						</div>
					</a>
				</c:forEach>
			</div>
		</main>


	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/signInfo.scss" />
	</fis:block>

	<fis:block name="jsPre">
    </fis:block>

	<fis:block name="js">
		<fis:parent />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myPost/signInfo.jsp" />
</fis:extends>