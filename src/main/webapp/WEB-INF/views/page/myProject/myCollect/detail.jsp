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
			<c:when test="${userIsJoined}">
				<footer class="tic-detail-footer">
					<a href="javascript:;" v-tap>
						<img src="/xdtic/static/images/myProject/checking.png" alt="等待审核">
					</a>
				</footer>
			</c:when>
			<c:otherwise>
				<footer class="tic-detail-footer">
					<a href="<c:url value='/project/toJoin' />?proId=<c:out value='${project.proId}' />&uid=<c:out value='${user.id}' />" v-tap>
						<img src="/xdtic/static/images/myProject/tojoin.png" alt="我要加入">
					</a>
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
		        "proId": '<c:out value="${project.proId}" />',
		        "proname": '<c:out value="${project.proname}" />',
		        "isCollected": <c:out value="${project.isCollected}" />,
				"promassage": getMultiline(function() {/*
					<c:out value="${project.promassage}" />
				*/
				}),
				"prowant": getMultiline(function() {/*
					<c:out value="${project.prowant}" />
				*/
				}),
				"concat": getMultiline(function() {/*
					<c:out value="${project.concat}" />
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
  <fis:require name="page/myProject/myCollect/detail.jsp" />
</fis:extends>