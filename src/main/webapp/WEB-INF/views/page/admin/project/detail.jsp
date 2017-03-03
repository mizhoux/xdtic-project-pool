<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame_admin.jsp">
    <fis:block name="article">
		<header>
			<div class="tic-header-three">
				<div class="tic-header-three-center">
			        <p class="tic-title-main"><c:out value="${project.proname}" /></p>
			        <p class="tic-title-secondary">
			            标签：
			            <c:forEach items="${project.tag}" var="item">
			                <span><c:out value="${item}" /></span>
			            </c:forEach>
			        </p>
			    </div>
			</div>	
		</header>

		<main>
			<fis:widget name="page/widget/main/projectDetail.jsp" />	
		</main>
	
        <c:choose>
        	<c:when test="${project.statu != 'check'}">
	        	<div class="ui buttons tic-buttons" id="projectOperation">
		            <button class="ui red button" v-tap="{methods: deleteProject}">删除</button>
		        </div>
        	</c:when>
        	<c:otherwise>
				<div class="ui buttons tic-buttons" id="projectOperation">
		            <button class="ui button" v-tap="{methods: reject}">拒绝</button>
		            <div class="or"></div>
		            <button class="ui positive button" v-tap="{methods: accept}">通过</button>
		        </div>
	        </c:otherwise>
	    </c:choose>
        <div id="toast" v-show="isChecked" v-cloak>
		    <div class="weui-mask_transparent"></div>
		    <div class="weui-toast">
		        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
		        <c:choose>
		        	<c:when test="${project.statu != 'check'}">
		        		<p class="weui-toast__content">已删除</p>
		        	</c:when>
		        	<c:otherwise>
		        		<p class="weui-toast__content">已审核</p>
		        	</c:otherwise>
		        </c:choose>
		    </div>
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/weui.css" />
		<fis:require id="static/scss/detailPost.scss" />
		<fis:require id="static/scss/admin/projectDetail.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script type="text/javascript">
		    var projectInfo = {
		        "proId": "<c:out value="${project.proId}" />",
		        "proname": "<c:out value="${project.proname}" />"
		    };

		    var userInfo = {};
		</script>

		<fis:require id="static/js/admin/project/detail.js" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />
    </fis:block>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/project/detail.jsp" />
</fis:extends>
