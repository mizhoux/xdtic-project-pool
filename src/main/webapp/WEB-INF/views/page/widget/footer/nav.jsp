<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fis:require id="page/widget/footer/nav.scss" />

<nav>
    <div class="tic-nav">
        <div>
        	<a href="<c:url value='/hall' />">
            	<img src="/xdtic/static/images/hall-selected.png" alt="项目大厅" v-if="pageSelected === 'hall'" v-cloak>

                <img src="/xdtic/static/images/hall.png" alt="项目大厅" v-else v-cloak>
        	</a>
        </div>
        <div>
        	<a href="<c:url value='/myProject' />?type=join&userid=<c:out value="${user.id}" />">
                <img src="/xdtic/static/images/my-project-selected.png" alt="我的项目" v-if="pageSelected === 'myProject'" v-cloak>
                <img src="/xdtic/static/images/my-project.png" alt="我的项目" v-else v-cloak>
        	</a>
        </div>
        <div>
            
        	<a href="<c:url value='/user' />?userid=<c:out value="${user.id}" />">
                <img src="/xdtic/static/images/center-selected.png" alt="个人中心" v-if="pageSelected === 'center'" v-cloak>
                <img src="/xdtic/static/images/center.png" alt="个人中心" v-else v-cloak>
        	</a>
        </div>
    </div>
</nav>

