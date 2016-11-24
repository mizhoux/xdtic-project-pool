<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<header>
			<div class="tic-header-three">
				<span v-tap="{methods:navBack}">&lt;</span>
				<h2>系统信息</h2>
				<span></span>
			</div>
		</header>
		<main id="appBody">
			<div class="tic-msg-box" id="msgBox" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10" infinite-scroll-immediate-check="checkImmediately">
				<tic-msg
				 v-for="msg in msgs"
				 :msg="msg">
				</tic-msg>
			</div>
			<div class="weui-loadmore" v-show="isLoading">
			    <i class="weui-loading"></i>
			    <span class="weui-loadmore__tips">正在加载</span>
			</div>
			<div class="weui-loadmore weui-loadmore_line" v-show="noMore">
			    <span class="weui-loadmore__tips">没有更多消息了</span>
			</div>
		</main>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/msgs.scss" />
	</fis:block>

	<fis:block name="jsPre">
    </fis:block>

	<fis:block name="js">
		<fis:parent />
		<fis:require id="static/libs/mod.js" />
		<fis:require id="static/js/user/msgs.js" />
		<script type="text/x-template" id="tic-msg">
			<div class="tic-msg">
				<div class="tic-msg-two">
					<div class="tic-msg-left">
						<img v-if="msg.type === 'join'" src="/static/images/msg-dialog-red.png" alt="">
						<img v-else src="/static/images/msg-dialog.png" alt="">
					</div>
					<div class="tic-msg-right">
						<a :href="'/xdtic/user/msg?id=' + msg.mid" class="tic-msg-link" v-tap>
							<p v-if="msg.type === 'join'">
								您已成功报名{{msg.peojectCreator}}的{{msg.proname}}，请等待发布者的手机短息通知，祝您顺利~
							</p>
							<p v-if="msg.type === 'pass'">
								您发布的{{msg.proname}}项目已经通过审核，快去看大家的踊跃报吧~
							</p>
							<p v-if="msg.type === 'post'">
								您已成功发布{{msg.proname}}项目，请耐心等待审核，通过后将在项目大厅上线，祝您顺利~
							</p>
						</a>
					</div>
				</div>
				<div class="tic-msg-two">
					<div class="tic-msg-left"></div>
					<div class="tic-msg-right tic-msg-date">
						<p>{{msg.existDate}}</p>
					</div>
				</div>
			</div>
		</script>
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/user/msgs.jsp" />
</fis:extends>