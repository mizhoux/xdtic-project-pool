<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
		<header>
			<div class="tic-header-three">
				<span></span>
				<h2><c:out value="${user.name}" /></h2>
				<a href="<c:url value='/myProject/postProject'/>" v-tap>
					<img src="/xdtic/static/images/myProject/post-project.png" alt="发布新项目">
				</a>
			</div>
			<nav class="tic-nav-header" id="myProjectNav"
			 v-tap="{methods: switchType}">
				<a data-type="join"
				 :class="{'tic-nav-current': currentLink === 'join'}">
					我的参与	
				</a>
				<a data-type="collect"
				 :class="{'tic-nav-current': currentLink === 'collect'}">
					我的收藏
				</a>
				<a data-type="post"
				 :class="{'tic-nav-current': currentLink === 'post'}">
					我的发布
				</a>
			</nav>
		</header>

		<main id="appBody">
			<div class="tic-project-box" id="projectBox" v-infinite-scroll="loadProject" infinite-scroll-disabled="busy" infinite-scroll-distance="10" infinite-scroll-immediate-check="checkImmediately">
				<div>
					<tic-project
					 v-for="(project, index) in projects"
					 :project="project"
					 :index="index"
					 :projecttype="myProjectType"
					 userid="<c:out value='${user.id}' />"
					 @collect="collect"
					 @uncollect="uncollect"
					 @collectfail="openDialog" >
					</tic-project>
				</div>
			</div>
			<div v-show="collectIsFail" class="tic-dialog" v-cloak>
			    <div class="weui-mask"></div>
			    <div class="weui-dialog">
			        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
			        <div class="weui-dialog__bd">啊哦，操作失败，稍后重试呗~</div>
			        <div class="weui-dialog__ft">
			            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
			        </div>
				   </div>
			</div>
			<div class="weui-loadmore" v-show="isLoading">
			    <i class="weui-loading"></i>
			    <span class="weui-loadmore__tips">正在加载</span>
			</div>
			<div class="weui-loadmore weui-loadmore_line" v-show="noMore">
			    <span class="weui-loadmore__tips">没有啦</span>
			</div>
			<fis:widget name="page/widget/footer/nav.jsp" />
		</main>		
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/myProject.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script type="text/javascript">
			var userInfo = {
				id: '<c:out value="${user.id}" />'
			};

			var pageSelected = 'myProject';
		</script>
	</fis:block>

	<fis:block name="js">
		<fis:parent />
		
		<fis:require id="static/js/myProject/myProject.js" />

		<script type="text/x-template" id="tic-project">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__bd">
				    <a :href="projectDetailLink +'?proId=' + project.proId + '&uid=' + userid" class="weui-media-box weui-media-box_appmsg">
			         <div class="weui-media-box__hd">
			             <img class="weui-media-box__thumb" src="<c:url value='/static/images/avatar.png' />" alt="">
				        </div>
				        <div class="weui-media-box__bd">
					        <h4 class="weui-media-box__title">
				            	<span :class="{'tic-hot': !!project.isHot}">
				            		{{project.proname}}
				            	</span>
				            	<span class="tic-collect">
				                	<img src="<c:url value='/static/images/hall/uncollect.png' />" alt="收藏"
				                	 v-tap="{methods: collect, projectIndex: index, userid: userid}"
				                	 v-show="!project.isCollected" />
				                	<img src="<c:url value='/static/images/hall/collect.png' />" alt="取消收藏"
				                	 v-tap="{methods: uncollect, projectIndex: index, userid: userid}"
				                	 v-show="project.isCollected" />
			                	</span>
				            </h4>
				            <p class="tic-media-box__label">
				            	<span
				            	 v-for="tag in project.tags">{{tag}}</span>
				            </p>
				            <p class="weui-media-box__desc">{{project.desc}}</p>
				            <p class="tic-media-box__footer">
				            	<span v-if="projectStatu.length != ''" class="tic-float-left tic-secondary">
				            		{{projectStatu}}
				            	</span>
					        	<span class="tic-strong">{{project.username}}</span>
					        	<span class="tic-secondary">{{project.date}}</span>
					        </p>
				        </div>
				    </a>
				</div>
			</div>
		</script>
    </fis:block>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myProject.jsp" />
</fis:extends>