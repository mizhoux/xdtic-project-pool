<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame_admin.jsp">
	<fis:block name="article">
		<div class="article" id="projectBox">
            <div class="ui masthead vertical segment">
                <div class="ui container">
                    <div class="introduction">
                        <h1 class="ui header">待审核项目</h1>
                    </div>
                </div>
            </div>
            <div class="ui main intro container" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10" infinite-scroll-immediate-check="checkImmediately">
                <table class="ui basic collapsing celled table centered grid">
                    <tbody>
                        <tr is="tic-project-check"
                         @process="processProject"
                         v-for="(project, index) in projects"
                         :project="project"
                         :index="index"></tr>
                    </tbody>
                </table>
                <div class="weui-loadmore" v-show="isLoading">
                    <i class="weui-loading"></i>
                    <span class="weui-loadmore__tips">正在加载</span>
                </div>
                <div class="weui-loadmore weui-loadmore_line" v-show="noMore">
                    <span class="weui-loadmore__tips">没有更多消息了</span>
                </div>
            </div>
        </div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />

        <fis:require id="static/scss/weui.css" />
		<fis:require id="static/scss/admin/projectCheck.scss" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />

        <fis:require id="static/js/admin/project/check.js" />

        <script type="text/x-template" id="tic-project-check">
            <tr :class="{'project-slide': project.isProcessed}" v-show="!project.animationEnd" class="project-item">
                <td>
                    <a class="tic-project-wrap" :href="'<c:url value='/admin/project?proId=' />' + project.proId">
                        <h4 class="ui image header">
                            <img src="<c:url value='/static/images/admin/avatar-lena.png' />" alt="lena" class="ui mini rounded image">
                            <div class="content">
                                {{project.proname}}
                                <div class="sub header">{{project.username}}</div>
                            </div>
                        </h4>
                    </a>
                </td>
                <td>
                   <div class="ui buttons">
                       <button class="ui button" v-tap="{methods: reject, proIndex: index}">拒绝</button>
                       <div class="or"></div>
                       <button class="ui positive button" v-tap="{methods: accept, proIndex: index}">通过</button>
                   </div> 
                </td>
            </tr>
        </script>
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/project/check.jsp" />
</fis:extends>