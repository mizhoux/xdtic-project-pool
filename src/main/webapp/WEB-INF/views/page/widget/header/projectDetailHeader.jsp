<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fis:require id="page/widget/header/projectDetailHeader.scss" />

<div class="tic-header-three">
    <a href="javascript:window.history.go(-1);" v-tap class="tic-header-three-aside">
        <img src="/xdtic/static/images/arrow_prev.png" alt="后退">
    </a>
    <div class="tic-header-three-center">
        <p><c:out value="${project.proname}" /></p>
        <p>
            标签：<span>其他</span><span>游戏</span>
        </p>
    </div>
    <span class="tic-header-three-aside">
        <img src="/xdtic/static/images/hall/collect.png" alt="收藏">
    </span>
</div>



