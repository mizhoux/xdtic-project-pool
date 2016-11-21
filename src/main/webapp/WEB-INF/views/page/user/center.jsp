<%@page import="wenjing.xdtic.model.User"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<%
    User user = new User();
    user.setId(1);
    user.setUsername("Michael");
    request.setAttribute("user", user);
%>
<fis:extends name="page/layout/frame.jsp">
    <fis:block name="body">
        <c:choose>
            <c:when test="${not empty user}">
                <header>
                    <div class="tic-avatar">
                        <div class="avatar">
                                <img src="/xdtic/static/images/avatar.png" alt="西电腾讯俱乐部">
                            <p><c:out value="${user.username}" /></p>
                        </div>
                        <div class="bg">
                            <img src="<c:url value="/static/images/avatar-bg.png"/>" alt="">
                        </div>
                    </div>
                </header>
                <main>
                    <div class="tic-four-grid">
                        <c:set var="notice" value="" />
                        <c:if test="${user.email}">
                            <c:set var="notice" value="notice" />
                        </c:if>
                        <a href="/user/msg?userid=<c:out value="${user.id}" />" v-tap class="grid <c:out value="${notice}" />">
                            <img src="/xdtic/static/images/sys-msg.png" alt="系统消息">
                            <p>系统消息</p>
                        </a>
                        <a class="grid" href="/user/profile?userid=<c:out value="${user.id}" />" v-tap>
                            <img src="/xdtic/static/images/profile.png" alt="个人信息">
                            <p>个人信息</p>
                        </a>
                        <a class="grid" href="/logout" v-tap>
                            <img src="/xdtic/static/images/switch-account.png" alt="切换账号">
                            <p>切换账号</p>
                        </a>
                        <a class="grid" href="/user/resetPass" v-tap>
                            <img src="/xdtic/static/images/change-pass.png" alt="修改密码">
                            <p>修改密码</p>
                        </a>
                    </div>
                </main>


            </c:when>

            <c:otherwise>

            </c:otherwise>
        </c:choose>


    </fis:block>

    <fis:widget name="page/widget/footer/nav.jsp" />
    
    <fis:block name="style">
        <fis:parent />
        <fis:require id="static/scss/center.scss" />
    </fis:block>

    <%-- auto inject by fis3-preprocess-extlang--%>
    
    <fis:require name="page/user/center.jsp" /> 
</fis:extends>

<%--<jsp:include page="page/widget/footer/nav.jsp" />--%>