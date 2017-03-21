<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<fis:require id="page/widget/main/projectDetail.scss" />

<article class="tic-article" id="detailProjectMain">
    <div class="tic-article-header">
        <div class="tic-article-header-left">
            <img src="/xdtic/static/images/avatar.png" alt="adoug">     
        </div>
        <div class="tic-article-header-center">
            <p class="tic-title-strong"><c:out value="${projectCreator.username}" /></p>
            <p class="tic-title-secondary"><c:out value="${project.creation_date}" /></p>
        </div>

        <c:if test="${project.statu != null}">
            <c:choose>
                <c:when test="${project.statu == 1}">
                    <c:set var="projectStatu" value="" />
                </c:when>
                <c:when test="${project.statu == 2}">
                    <c:set var="projectStatu" value="状态：审核被拒" />
                </c:when>
                <c:otherwise>
                    <c:set var="projectStatu" value="状态：审核中" />
                </c:otherwise>
            </c:choose>
            <p class="tic-article-header-right tic-title-em"><c:out value="${projectStatu}" /></p>
        </c:if>
    </div>
    <section class="tic-article-section">
        <multiline-content :content="project.content"></multiline-content>
    </section>
    <hr>
    <section class="tic-article-section">
        <p class="tic-title-strong">招聘详情</p>
        <multiline-content :content="project.recruit"></multiline-content>
    </section>
    <hr/>
    <section class="tic-article-section">
        <p class="tic-title-strong">联系方式</p>
        <multiline-content :content="project.contact"></multiline-content>
    </section>
</article>

<fis:require id="page/widget/main/projectDetail.js" />
