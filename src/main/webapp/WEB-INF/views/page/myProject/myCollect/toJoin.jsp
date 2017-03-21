<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<div id="appProfile">
			<header>
				<fis:widget name="page/widget/header/projectDetailHeader.jsp" />	
			</header>
			<main id="appToJoin">
				<div class="tic-table-head">
					<h3>个人资料填写</h3>
					<p>(请先在个人中心完善简历，系统可帮您预填哦~)</p>
				</div>
				<div class="tic-table">
					<form class="tic-form" id="formToJoin"
					 @blur.capture="validateToJoin">
						<div class="tic-form-error" v-if="hasError" v-cloak>
							<p>还有必填项没有填~</p>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">姓名</span>
							<span class="tic-td-content">
								{{user.realname}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">性别</span>
							<span class="tic-td-content">
								{{user.gender === 'M' ? '男' : '女'}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">专业</span>
							<span class="tic-td-content">
								 {{user.major}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">手机</span>
							<span class="tic-td-content">
								 {{user.phone}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">学号</span>
							<span class="tic-td-content">
								 {{user.stuNum}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">申请职位</span>
							<span class="tic-td-content">
								<input type="text" name="apply" v-model="userApply"
								 
								 :class="{'tic-error-input': inputError['apply']}" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">个人能力(选填)</span>
							<span class="tic-td-content">
								<textarea name="skill" v-model="user.skill"></textarea>
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">项目经历(选填)</span>
							<span class="tic-td-content">
								<textarea name="experience" v-model="user.experience"></textarea>
							</span>
						</div>
						<input type="hidden" name="proId" value="<c:out value='${project.id}' />">
						<input type="hidden" name="userId" :value="user.id">
						<button class="weui-btn weui-btn_primary" id="btnConfirm" v-tap.prevent="{methods: joinProject}">确认加入</button>
					</form>
				</div>
				<div v-show="editFail" v-cloak>
				    <div class="weui-mask"></div>
				    <div class="weui-dialog">
				        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
				        <div class="weui-dialog__bd">啊哦，提交失败，稍后重试呗~</div>
				        <div class="weui-dialog__ft">
				            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
				        </div>
				    </div>
				</div>
				<div id="toast" v-show="editIsSucc" v-cloak>
				    <div class="weui-mask_transparent"></div>
				    <div class="weui-toast">
				        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
				        <p class="weui-toast__content">报名成功</p>
				    </div>
				</div>
			</main>
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/toJoin.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script>
			var getMultiline = function(f) {
				return f.toString().replace(/^[^\/]+\/\*!?\s?/, '')
							.replace(/\*\/[^\/]+$/, '').trim();
			};
		</script>
        <script>
			var userInfo = {
				id: "<c:out value="${user.id}" />",
				username: "<c:out value="${user.username}" />",
				email: "<c:out value="${user.email}" />",
				realname: "<c:out value="${user.realname}" />",
				gender: "<c:out value="${user.gender}" />",
				phone: "<c:out value="${user.phone}" />",
				stuNum: "<c:out value="${user.stuNum}" />",
				major: "<c:out value="${user.major}" />",
				skill: getMultiline(function() {/*
					<c:out value="${user.skill}" />
				*/
				}),
				experience: getMultiline(function() {/*
					<c:out value="${user.experience}" />
				*/
				})
			};
			var projectInfo = {
		        "id": '<c:out value="${project.id}" />',
		        "name": '<c:out value="${project.name}" />',
		        "isCollected": <c:out value="${project.isCollected}" />
		    };
		</script>
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		<fis:require id="static/js/myProject/myCollect/toJoin.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/myProject/myCollect/toJoin.jsp" />
</fis:extends>