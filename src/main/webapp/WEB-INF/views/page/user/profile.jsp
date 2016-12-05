<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<div id="appProfile">
			<header>
				<div class="tic-header-three">
					<a href="javascript:window.history.go(-1);" v-tap class="tic-header-three-aside">
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
						<div class="tic-tr-two">
							<span class="tic-td-label">昵称</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.username}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="username" v-model="user.username" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">姓名</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.name}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="name" v-model="user.realName" />
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
								            <input type="radio" value="boy" class="weui-check" id="radioBoy" name="sex" v-model="user.sex">
								            <i class="weui-icon-checked"></i>
								        </div>
								        <div class="weui-cell__bd">
								            <p>男</p>
								        </div>
								    </label>
								    <label class="weui-cell weui-check__label" for="radioGirl">
								        <div class="weui-cell__hd">
								            <input type="radio" value="girl" name="sex" id="radioGirl" class="weui-check" v-model="user.sex">
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
							<span class="tic-td-content" v-show="!isEditing">{{user.profe}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="profe" v-model="user.major" />
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
							<span class="tic-td-content" v-show="!isEditing">{{user.stunum}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<input type="text" name="stunum" v-model="user.studentNo" />
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">个人能力</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.profile}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<textarea name="profile" v-model="user.ability"></textarea>
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">项目经历</span>
							<span class="tic-td-content" v-show="!isEditing">{{user.pexperice}}</span>
							<span class="tic-td-content" v-show="isEditing">
								<textarea name="pexperice" v-model="user.projectDesc"></textarea>
							</span>
						</div>
						<input type="hidden" name="id" value="<c:out value="${user.id}" />">
						<button class="weui-btn weui-btn_primary" id="btnConfirm" v-show="isEditing" v-tap="{methods: editProfile}">确认</button>
					</form>
				</div>
			</main>
			<div v-show="editFail">
			    <div class="weui-mask"></div>
			    <div class="weui-dialog">
			        <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
			        <div class="weui-dialog__bd">啊哦，修改失败，稍后重试呗~</div>
			        <div class="weui-dialog__ft">
			            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
			        </div>
			    </div>
			</div>
			<div id="toast" v-show="editIsSucc">
			    <div class="weui-mask_transparent"></div>
			    <div class="weui-toast">
			        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
			        <p class="weui-toast__content">报名成功</p>
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
			var userInfo = {
				username: "<c:out value="${user.username}" />",
				email: "<c:out value="${user.email}" />",
				name: "<c:out value="${user.name}" />",
				sex: "<c:out value="${user.sex}" />",
				profe: "<c:out value="${user.profe}" />",
				phone: "<c:out value="${user.phone}" />",
				stunum: "<c:out value="${user.stunum}" />",
				profile: "<c:out value="${user.profile}" />",
				pexperice: "<c:out value="${user.pexperice}" />"
			}
		</script>
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		
		<fis:require id="static/js/user/profile.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/profile.jsp" />
</fis:extends>