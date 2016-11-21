<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<div class="tic-header-three">
				<span>&lt;</span>
				<h2>个人信息</h2>
				<span>
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
				<form action="/fn/update/profile" method="POST" id="formProfile">
					<div class="tic-tr-two">
						<span class="tic-td-label">昵称</span>
						<span class="tic-td-content" v-show="!isEditing">{{user.userName}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<input type="text" v-model="user.userName" />
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">姓名</span>
						<span class="tic-td-content" v-show="!isEditing">{{user.userRealName}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<input type="text" v-model="user.userRealName" />
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">性别</span>
						<span class="tic-td-content" v-show="!isEditing">{{userSex}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<div class="weui-cells weui-cells_checkbox">
							    <label class="weui-cell weui-check__label" for="radioBoy">
							        <div class="weui-cell__hd">
							            <input type="radio" value="boy" class="weui-check" id="radioBoy" name="userSex" v-model="user.userSex">
							            <i class="weui-icon-checked"></i>
							        </div>
							        <div class="weui-cell__bd">
							            <p>男</p>
							        </div>
							    </label>
							    <label class="weui-cell weui-check__label" for="radioGirl">
							        <div class="weui-cell__hd">
							            <input type="radio" value="girl" name="userSex" id="radioGirl" class="weui-check" v-model="user.userSex">
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
							<input type="text" v-model="user.major" />
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label" v-show="!isEditing">手机</span>
						<span class="tic-td-content">{{user.phone}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<input type="text" v-model="user.phone" />
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">学号</span>
						<span class="tic-td-content" v-show="!isEditing">{{user.studentNo}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<input type="text" v-model="user.studentNo" />
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">个人能力</span>
						<span class="tic-td-content" v-show="!isEditing">{{user.ability}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<textarea v-model="user.ability"></textarea>
						</span>
					</div>
					<div class="tic-tr-two">
						<span class="tic-td-label">项目经历</span>
						<span class="tic-td-content" v-show="!isEditing">{{user.projectDesc}}</span>
						<span class="tic-td-content" v-show="isEditing">
							<textarea v-model="user.projectDesc"></textarea>
						</span>
					</div>
					<a class="weui-btn weui-btn_primary" id="btnConfirm">确认</a>
				</form>
			</div>
		</main>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/profile.scss" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />
		<fis:require id="static/libs/mod.js" />
		<fis:require id="static/js/user/profile.js" />
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/profile.jsp" />
</fis:extends>