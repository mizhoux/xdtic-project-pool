<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fis:require id="page/widget/header/projectDetailHeader.scss" />

<div class="tic-header-three" id="detailPostHeader">
    <a href="javascript:window.history.go(-1);" v-tap class="tic-header-three-aside">
        <img src="/xdtic/static/images/arrow_prev.png" alt="后退">
    </a>
    <div class="tic-header-three-center">
        <h3>{{project.proname}}</h3>
        <p class="tic-title-secondary">
            标签：
            <c:forEach items="${project.tag}" var="item">
                <span><c:out value="${item}" /></span>
            </c:forEach>
        </p>
    </div>
    <span class="tic-header-three-aside">
        <img src="/xdtic/static/images/hall/uncollect.png" alt="收藏"
         v-show="!project.isCollected"
         v-tap="{methods: collect}" >
        <img src="/xdtic/static/images/hall/collect.png" alt="取消收藏"
         v-show="project.isCollected"
         v-tap="{methods: uncollect}" >
    </span>
    <div v-show="collectIsFail">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
             <div class="weui-dialog__hd"><strong class="weui-dialog__title">提</strong></div>
            <div class="weui-dialog__bd">啊哦，操作失败，稍后重试呗~</div>
            <div class="weui-dialog__ft">
                <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary" v-tap.prevent="{methods: closeDialog}">确定</a>
            </div>
           </div>
    </div>
</div>

<fis:require id="page/widget/header/projectDetailHeader.js" />



