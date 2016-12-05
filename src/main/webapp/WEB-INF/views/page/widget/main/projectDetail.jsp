<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fis:require id="page/widget/main/projectDetail.scss" />

<article class="tic-article">
    <div class="tic-article-header">
        <div class="tic-article-header-left">
            <img src="/xdtic/static/images/avatar.png" alt="adoug">     
        </div>
        <div class="tic-article-header-center">
            <p class="tic-title-strong"><c:out value='${projectCreator.username}' /></p>
            <p class="tic-title-secondary"><c:out value='${project.date}' /></p>
        </div>

        <c:if test="${project.statu}">
            <c:set var="projectStatu" value="审核中" />
            <c:if test="${project.statu} == 'pass'">
                <c:set var="projectStatu" value="审核通过" />
            </c:if>
            <p class="tic-article-header-right tic-title-em">状态：<c:out value="${projectStatu}" /></p>
        </c:if>
    </div>
    <section class="tic-article-section">
        <p>
            <c:out value='${project.promassage}' />
        </p>
    </section>
    <hr>
    <section class="tic-article-section">
        <p class="tic-title-strong">招聘详情</p>
        <p>
            <c:out value='${project.prowant}' />
        </p>
    </section>
    <hr/>
    <section class="tic-article-section">
        <p class="tic-title-strong">联系方式</p>
        <p>
            <c:out value='${project.concat}' />
        </p>
    </section>
</article>