<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<div id="appProfile">
			<header>
				<div class="tic-header-three">
					<a v-tap="{methods: navBack}" class="tic-header-three-aside">
	        			<img src="/xdtic/static/images/arrow_prev.png" alt="后退">
	    			</a>
					<h2>个人信息</h2>
					<span v-tap="{methods:showEditer}">
						<img src="/xdtic/static/images/change-profile.png" alt="修改个人信息">
					</span>
				</div>
			</header>
			<main>
				<div class="tic-table-head">
					<h3>个人信息修改</h3>
					<p>(完善个人信息后，系统可帮您在加入项目时预填哦~)</p>
				</div>
				<div class="tic-table">
					<form method="POST" class="tic-form" id="formProfile">
						<p class="tic-error-tip" v-show="hasError">{{errorMsg}}</p>
						<div class="tic-tr-two">
							<span class="tic-td-label">用户名</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.username}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="username" v-model="user.username" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">姓名</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.realname}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="realname" v-model="user.realname" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">邮箱</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.email}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="email" v-model="user.email" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">性别</span>
							<span class="tic-td-content" v-show="!isEditing">{{userSex}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<div class="weui-cells weui-cells_checkbox">
								    <label class="weui-cell weui-check__label" for="radioBoy">
								        <div class="weui-cell__hd">
								            <input type="radio" value="M" class="weui-check" id="radioBoy" name="gender" v-model="user.gender">
								            <i class="weui-icon-checked"></i>
								        </div>
								        <div class="weui-cell__bd">
								            <p>男</p>
								        </div>
								    </label>
								    <label class="weui-cell weui-check__label" for="radioGirl">
								        <div class="weui-cell__hd">
								            <input type="radio" value="F" name="gender" id="radioGirl" class="weui-check" v-model="user.gender">
								            <i class="weui-icon-checked"></i>
								        </div>
								        <div class="weui-cell__bd">
								            <p>女</p>
								        </div>
								    </label>
								</div>
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">专业</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.major}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="major" v-model="user.major" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">手机</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.phone}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="phone" v-model="user.phone" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">学号</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.stuNum}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="stuNum" v-model="user.stuNum" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">个人能力</span>
							<span class="tic-td-content" v-show="!isEditing">
								<multiline-content :content="user.skill"></multiline-content>
							</span>
							<span class="tic-td-content" v-show="isEditing">
								<textarea name="skill" v-model="user.skill"></textarea>
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">项目经历</span>
							<span class="tic-td-content" v-show="!isEditing">
								<multiline-content :content="user.experience"></multiline-content>
							</span>
							<span class="tic-td-content" v-show="isEditing">
								<textarea name="experience" v-model="user.experience"></textarea>
							</span>
						</div>
						<input type="hidden" name="id" value="<c:out value="${user.id}" />">
						<button class="weui-btn weui-btn_primary" id="btnConfirm" v-show="isEditing" v-tap.prevent="{methods: editProfile}">确认</button>
					</form>
				</div>
			</main>
			<div v-show="editFail" v-cloak>
			    <div class="weui-mask"></div>
			    <div class="weui-dialog">
			        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
			        <div class="weui-dialog__bd">啊哦，修改失败，稍后重试呗~</div>
			        <div class="weui-dialog__ft">
			            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
			        </div>
			    </div>
			</div>
			<div id="toast" v-show="editIsSucc" v-cloak>
			    <div class="weui-mask_transparent"></div>
			    <div class="weui-toast">
			        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
			        <p class="weui-toast__content">修改成功</p>
			    </div>
			</div>
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/profile.scss" />
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
				username: "<c:out value="${user.username}" />",
				email: "<c:out value="${user.email}" />",
				realname: "<c:out value="${user.realname}" />",
				gender: "<c:out value="${user.gender}" />",
				marjor: "<c:out value="${user.skill}" />",
				phone: "<c:out value="${user.phone}" />",
				stuNum: "<c:out value="${user.stuNum}" />",
				skill: getMultiline(function() {/*
					<c:out value="${user.skill}" />
				*/
				}),
				experience: getMultiline(function() {/*
					<c:out value="${user.experience}" />
				*/
				})
			};
		</script>
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		
		<fis:require id="static/js/user/profile.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/profile.jsp" />
</fis:extends>