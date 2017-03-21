<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fis:require id="page/widget/header/projectDetailHeader.scss" />

<div class="tic-header-three" id="detailPostHeader">
    <a href="javascript:window.history.go(-1);" v-tap class="tic-header-three-aside">
        <img src="/xdtic/static/images/arrow_prev.png" alt="后退">
    </a>
    <div class="tic-header-three-center">
        <p class="tic-title-main"><c:out value='${project.name}' /></p>
        <p class="tic-title-secondary">
            报名信息
        </p>
    </div>
    <span class="tic-header-three-aside">
    </span>
</div>
