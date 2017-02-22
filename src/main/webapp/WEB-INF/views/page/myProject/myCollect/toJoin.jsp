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
					<p>(记得在个人中心完善简历，系统可帮您预填哦~)</p>
				</div>
				<div class="tic-table">
					<form class="tic-form" id="formToJoin"
					 @blur.capture="validateToJoin">
						<div class="tic-form-error" v-if="hasError">
							<p>还有必填项没有填~</p>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">姓名</span>
							<span class="tic-td-content">
								<!-- <input type="text" name="name" v-model="user.name"
								 
								 :class="{'tic-error-input': inputError['name']}" /> -->
								{{user.name}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">性别</span>
							<span class="tic-td-content">
								<!-- <div class="weui-cells weui-cells_checkbox tic-cells-nomargin">
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
								</div> -->
								{{user.sex === 'boy' ? '男' : '女'}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">专业</span>
							<span class="tic-td-content">
								<!-- <input type="text" name="profe" v-model="user.profe"
								 
								 :class="{'tic-error-input': inputError['profe']}" /> -->
								 {{user.profe}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">手机</span>
							<span class="tic-td-content">
								<!-- <input type="text" name="phone" v-model="user.phone"
								 
								 :class="{'tic-error-input': inputError['phone']}" /> -->
								 {{user.phone}}
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">学号</span>
							<span class="tic-td-content">
								<!-- <input type="text" name="stunum" v-model="user.stunum"
								  
								 :class="{'tic-error-input': inputError['stunum']}" /> -->
								 {{user.stunum}}
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
								<textarea name="profile" v-model="user.profile"></textarea>
							</span>
						</div>
						<div class="tic-tr-two">
							<span class="tic-td-label">项目经历(选填)</span>
							<span class="tic-td-content">
								<textarea name="pexperice" v-model="user.pexperice"></textarea>
							</span>
						</div>
						<input type="hidden" name="proId" value="<c:out value='${project.proId}' />">
						<input type="hidden" name="uid" :value="user.id">
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
				<div id="toast" v-show="editIsSucc">
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
							.replace(/\*\/[^\/]+$/, '');
			};
		</script>
        <script>
			var userInfo = {
				id: "<c:out value="${user.id}" />",
				username: "<c:out value="${user.username}" />",
				email: "<c:out value="${user.email}" />",
				name: "<c:out value="${user.name}" />",
				sex: "<c:out value="${user.sex}" />",
				profe: "<c:out value="${user.profe}" />",
				phone: "<c:out value="${user.phone}" />",
				stunum: "<c:out value="${user.stunum}" />",
				profile: "<c:out value="${user.profile}" />",
				pexperice: getMultiline(function() {/*
					<c:out value="${user.pexperice}" />
				*/
				})
			};
			var projectInfo = {
		        "proId": '<c:out value="${project.proId}" />',
		        "proname": '<c:out value="${project.proname}" />',
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