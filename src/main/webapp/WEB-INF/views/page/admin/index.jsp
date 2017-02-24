<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame_admin.jsp">
	<fis:block name="article">
		<div class="article">
            <div class="card-wrap">
                <div class="ui link cards">
                    <div class="card">
                        <div class="image">
                            <img src="/xdtic/static/images/admin/project-matthew.png" alt="项目管理">
                        </div>
                        <div class="content">
                            <a href="<c:url value='/admin/project/check' />" class="ui blue basic button" v-tap>项目审核</a>
                            <a href="<c:url value='/admin/project/look' />" class="ui olive basic button" v-tap>项目浏览</a>
                        </div>
                    </div>
                    <div class="card">
                        <div class="image">
                            <img src="/xdtic/static/images/admin/user-kristy.png" alt="用户管理">
                        </div>
                        <div class="content">
                            <a href="<c:url value='/admin/user/add' />" class="ui blue basic button" v-tap>用户添加</a>
                            <a href="<c:url value='/admin/user/look' />" class="ui olive basic button" v-tap>用户浏览</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />

		<fis:require id="static/scss/admin/index.scss" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />

		<fis:require id="static/js/admin/index.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/index.jsp" />
</fis:extends>