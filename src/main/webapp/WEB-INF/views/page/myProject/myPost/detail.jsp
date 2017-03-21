<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
		<header>
			<fis:widget name="page/widget/header/projectDetailHeader.jsp" />	
		</header>

		<main>
			<fis:widget name="page/widget/main/projectDetail.jsp" />	
		</main>

		<c:choose>
			<c:when test="${project.statu != 'reject'}">
				<footer class="tic-detail-footer">
					<a href="<c:url value='/myProject/myPost/editDetail' />?proId=<c:out value='${project.id}' />" v-tap>
						<img src="/xdtic/static/images/myProject/edit-info.png" alt="编辑信息">
					</a>
					<a href="<c:url value='/myProject/myPost/signInfo' />?proId=<c:out value='${project.id}' />" v-tap>
						<img src="/xdtic/static/images/myProject/sign-info.png" alt="报名信息">
					</a>
				</footer>
			</c:when>
			<c:otherwise>
				<footer class="tic-detail-footer2">
					<a href="<c:url value='/myProject/myPost/editDetail' />?proId=<c:out value='${project.id}' />" class="weui-btn weui-btn_primary" v-tap>修改并重新发布</a>
				</footer>
			</c:otherwise>
		</c:choose>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/detailPost.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script>
			var getMultiline = function(f) {
				return f.toString().replace(/^[^\/]+\/\*!?\s?/, '')
                            .replace(/\*\/[^\/]+$/, '').trim();
			};
		</script>
		<script type="text/javascript">
		    var projectInfo = {
		        "id": '<c:out value="${project.id}" />',
		        "name": '<c:out value="${project.name}" />',
		        "isCollected": <c:out value="${project.isCollected}" />,
				"content": getMultiline(function() {/*
					<c:out value="${project.content}" />
				*/
				}),
				"recruit": getMultiline(function() {/*
					<c:out value="${project.recruit}" />
				*/
				}),
				"contact": getMultiline(function() {/*
					<c:out value="${project.contact}" />
				*/
				})
		    };
		    var userInfo = {
		        "id": '<c:out value="${user.id}" />'
		    };
		</script>
	</fis:block>

	<fis:block name="js">
		<fis:parent />
		
    </fis:block>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myPost/detail.jsp" />
</fis:extends>