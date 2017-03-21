<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame_admin.jsp">
	<fis:block name="article">
		<div class="article" id="userBox">
            <div class="ui masthead vertical segment">
                <div class="ui container">
                    <div class="introduction">
                        <h1 class="ui header">用户浏览</h1>
                    </div>
                </div>
            </div>
            <div class="ui main intro container centered grid" v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10" infinite-scroll-immediate-check="checkImmediately">
                <form id="formUsers">
                    <table class="ui basic collapsing celled table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>编号</th>
                                <th>用户名</th>
                                <th>姓名</th>
                                <th>邮箱</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr is="tic-user"
                             @checked="updateChecked"
                             v-for="(user, index) in users"
                             :user="user"
                             :index="index"></tr>
                         </tbody>
                    </table>
                </form>
                <div class="weui-loadmore" v-show="isLoading">
                    <i class="weui-loading"></i>
                    <span class="weui-loadmore__tips">正在加载</span>
                </div>
                <div class="weui-loadmore weui-loadmore_line" v-show="noMore">
                    <span class="weui-loadmore__tips">没有更多用户了</span>
                </div>
            </div>
        </div>
        <div class="ui fixed menu tic-user-op">
            <div class="item">
                <div class="ui orange small button"
                 :class="{disabled: !hasChecked}"
                 v-tap="{methods: deleteUser}">删除</div>
            </div>
            <div class="item" style="display: none">
                <div class="ui red small button"
                 :class="{disabled: !hasUser}"
                 v-tap="{methods: deleteAll}">删除全部</div>
            </div>
            <div class="item ui right" style="display: none">
                <div class="ui small primary labeled icon button"
                 v-tap="{methods: addUser}">
                    <i class="user icon"></i> 添加用户
                </div>
            </div>
        </div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />

        <fis:require id="static/scss/weui.css" />
		<fis:require id="static/scss/admin/userLook.scss" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />

        <fis:require id="static/js/admin/user/look.js" />

        <script type="text/x-template" id="tic-user-look">
            <transition name="slide">
                <tr>
                    <td class="collapsing">
                        <div class="ui fitted slider checkbox">
                            <input type="checkbox"
                             :value="user.id"
                             v-model="checked">
                            <label></label>
                        </div>
                    </td>
                    <td>{{user.id}}</td>
                    <td>{{user.username}}</td>
                    <td>{{user.real}}</td>
                    <td>{{user.email}}</td>
                </tr>
            </transition>
        </script>
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/user/look.jsp" />
</fis:extends>