<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<div id="appPostProject">
			<header>
				<div class="tic-header-three">
					<a v-tap.prevent="{methods:navBack}" class="tic-header-three-aside">
				        <img src="/xdtic/static/images/arrow_prev.png" alt="后退">
				    </a>
				    <div class="tic-header-three-center">
				        <p class="tic-title-main">发布项目</p>
				    </div>
				    <span class="tic-header-three-aside">
				    </span>
				</div>
			</header>
			<main>
				<div>
					<form id="formPostProject">
						<tic-tag-box @tagchange="updateTag"></tic-tag-box>
						<p class="tic-error-tip" v-show="hasError" v-cloak>{{errorMsg}}</p>
						<div class="weui-cells weui-cells_form">
							<div class="weui-cell">
							    <div class="weui-cell__bd">
							        <input class="weui-input" type="text" name="name" placeholder="标题（至少2个字）"
									 v-model="name" />
							    </div>
							</div>
							<div class="weui-cell">
							    <div class="weui-cell__bd">
							        <textarea class="weui-textarea" name="content" placeholder="项目详情介绍（至少10个字）" rows="5"
									 v-model="content"></textarea>
							    </div>
							</div>
						</div>
						<hr>
						<div class="weui-cells__title tic-cells-title">招聘详情</div>
						<div class="weui-cells weui-cells_form">
						    <div class="weui-cell">
						        <div class="weui-cell__bd">
						            <textarea class="weui-textarea" name="recruit" placeholder="招聘信息（至少6个字）" rows="3"
									 v-model="recruit"></textarea>
						        </div>
							   </div>
						</div>
						<div class="weui-cells__title tic-cells-title">联系方式</div>
						<div class="weui-cells weui-cells_form">
						    <div class="weui-cell">
						        <div class="weui-cell__bd">
						            <textarea class="weui-textarea" name="contact" placeholder="请注明电话或邮箱或其他联系方式" rows="3"
									 v-model="contact"></textarea>
						        </div>
							   </div>
						</div>
						<input type="hidden" name="userId" value="<c:out value='${user.id}' />">
						<button class="weui-btn weui-btn_primary" id="btnConfirm" v-tap.prevent="{methods: validForm}">发布</button>
					</form>
				</div>
			</main>
			<div v-show="postIsFail" v-cloak>
			    <div class="weui-mask"></div>
			    <div class="weui-dialog">
			        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
			        <div class="weui-dialog__bd">啊哦，修改失败，稍后重试呗~</div>
			        <div class="weui-dialog__ft">
			            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
			        </div>
			    </div>
			</div>
			<div id="toast" v-show="postIsSucc" v-cloak>
			    <div class="weui-mask_transparent"></div>
			    <div class="weui-toast">
			        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
			        <p class="weui-toast__content">发布成功</p>
			    </div>
			</div>
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/postProject.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script type="text/x-template" id="tic-tag">
			<div class="tic-tag-box">
				<div class="tic-tag-fn"
				 :class="{'tic-tag-fn-fold': !unfold}"
				 v-tap="{methods: switchTag}">为项目添加标签(请至少选择一个)</div>
				<div class="tic-tag-content"
				 v-show="showTag"
				 v-tap="{methods: tagTap}">
					<span class="tic-tag"
					 v-for="(tag, index) in tags"								
					 :data-tag="tag"
					 :class="{'tic-tag-selected': isSelected(tag)}">{{tag}}</span>
					 <span class="tic-tag"
					  v-tap="{methods: showTagAdd}">+新标签</span>
				</div>
				<div class="tic-tag-content-selected"
				 v-show="!showTag">
					<span class="tic-tag tic-tag-selected"
					 v-for="tag in selected">{{tag}}</span>
				</div>
				<div id="addTag" v-show="isShowTagAdd" v-cloak>
				    <div class="weui-mask"></div>
				    <div class="weui-dialog">
				        <div class="weui-dialog__hd"><strong class="weui-dialog__title">添加标签</strong></div>
				        <div class="weui-dialog__bd tic-form">
				        	<p>请输入你想添加的标签名称</p>
				        	<p>最多24个字符</p>
				        	<input type="text" v-model="tagAdded">
				        </div>
				        <div class="weui-dialog__ft">
				            <a class="weui-dialog__btn weui-dialog__btn_default"
				             v-tap.prevent="{methods: hideTagAdd}">取消</a>
				            <a class="weui-dialog__btn weui-dialog__btn_primary"
				             v-tap.prevent="{methods: addTag}">添加</a>
				        </div>
				    </div>
				</div>
				<input type="hidden" name="tag"
				 :value="tagsSelected">
			</div>
		</script>
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		
		<fis:require id="static/js/myProject/postProject.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/postProject.jsp" />
</fis:extends>