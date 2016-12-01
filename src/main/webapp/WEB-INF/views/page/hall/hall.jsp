<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
		<header class="tic-header-hall">
			<img src="/xdtic/static/images/hall/logo.png" alt="西电腾讯俱乐部">
			<aside>
				<div class="weui-search-bar" id="searchBar"
				 :class="{'weui-search-bar_focusing': isFocusing}">
				    <form class="weui-search-bar__form"
				     @submit.prevent="search">
				        <div class="weui-search-bar__box">
				            <i class="weui-icon-search"></i>
				            <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="大家都在搜：T派校园大赛" 
				             v-model="keyWords"
				             @blur="inputBlur" />
				            <a href="javascript:" class="weui-icon-clear" id="searchClear"
				             v-tap="{methods:clearInput}"></a>
				        </div>
				        <label for="search_input" class="weui-search-bar__label" id="search_text"
				         v-show="!isFocusing"
				         v-tap="{methods:showInput}">
				            <i class="weui-icon-search"></i>
				            <span>大家都在搜：T派校园大赛</span>
				        </label>
				    </form>
				    <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel"
				     v-tap="{methods:cancelSearch}">取消</a>
				</div>
			</aside>
		</header>

		<main id="appBody">
			<div class="tic-project-box" id="projectBox" v-infinite-scroll="loadProject" infinite-scroll-disabled="busy" infinite-scroll-distance="10" infinite-scroll-immediate-check="checkImmediately">
				<div id="hotProjectBox">
					<tic-project
					 v-for="(hotProject, index) in hotProjects"
					 v-bind:project="hotProject"
					 v-bind:index="index"
					 userid="<c:out value='${user.id}' />"
					 @collect="collectHot"
					 @uncollect="uncollectHot"
					 @collectFail="openDialog">
					</tic-project>
				</div>
				<div id="normalProjectBox">
					<tic-project
					 v-for="(project, index) in projects"
					 :project="project"
					 :index="index"
					 userid="<c:out value='${user.id}' />"
					 @collect="collect"
					 @uncollect="uncollect"
					 @collectFail="openDialog">
					</tic-project>
				</div>
				<div v-show="collectIsFail">
				    <div class="weui-mask"></div>
				    <div class="weui-dialog">
				        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
				        <div class="weui-dialog__bd">啊哦，操作失败，稍后重试呗~</div>
				        <div class="weui-dialog__ft">
				            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
				        </div>
				    </div>
				</div>
			</div>
			<div class="weui-loadmore" v-show="isLoading">
			    <i class="weui-loading"></i>
			    <span class="weui-loadmore__tips">正在加载</span>
			</div>
			<div class="weui-loadmore weui-loadmore_line" v-show="noMore">
			    <span class="weui-loadmore__tips">没有更多消息了</span>
			</div>
		</main>		

		<fis:widget name="page/widget/footer/nav.jsp" />
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/hall.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script type="text/javascript">
			var userInfo = {
				id: '<c:out value="${user.id}" />'
			};
		</script>
	</fis:block>

	<fis:block name="js">
		<fis:parent />
		<fis:require id="static/libs/mod.js" />
		<fis:require id="static/js/hall/hall.js" />

		<script type="text/x-template" id="tic-project">
			<div class="weui-panel weui-panel_access">
				<div class="weui-panel__bd">
				    <a :href="'/xdtic/project?id='+project.proId" class="weui-media-box weui-media-box_appmsg">
			         <div class="weui-media-box__hd">
			             <img class="weui-media-box__thumb" src="/xdtic/static/images/avatar.png" alt="">
				        </div>
				        <div class="weui-media-box__bd">
					        <h4 class="weui-media-box__title">
				            	<span :class="{'tic-hot': !!project.isHot}">
				            		{{project.proname}}
				            	</span>
				            	<span class="tic-collect">
				                	<img src="/xdtic/static/images/hall/uncollect.png" alt="收藏"
				                	 v-tap="{methods: collect, projectIndex: index, userid: userid}"
				                	 v-show="!project.isCollected" />
				                	<img src="/xdtic/static/images/hall/collect.png" alt="取消收藏"
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
  <fis:require name="page/hall/hall.jsp" />
</fis:extends>