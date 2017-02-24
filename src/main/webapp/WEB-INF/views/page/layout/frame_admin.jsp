<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE html><fis:html lang="en">
    <fis:head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <title>腾讯俱乐部项目池</title>
        <!-- <link rel="shortcut icon" type="image/x-icon" href="/static/favicon.ico" /> -->
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <fis:block name="style">
            <fis:require id="static/scss/admin/semantic.css" />
            <fis:require id="static/scss/common.scss" />
            <fis:require id="static/scss/admin/common.scss" />
        </fis:block>

        <fis:block name="js">
            <fis:require id="static/libs/common.js" />
            <fis:require id="static/libs/vue.js" />
            <fis:require id="static/libs/vue-tap.js" />
            <fis:require id="static/libs/mod.js" />
            <fis:require id="static/libs/admin/common.js" />
        </fis:block>
        
        <fis:block name="jsPre">
        </fis:block>
    </fis:head>

    <fis:body class="body">
        <!--这个的app对应的Vue组件须在各页面自行构造，处理得不是很好-->
        <div id="app" class="pushable">
            <div class="ui vertical inverted sidebar menu left"
             :class="{visible: sidePush, uncover: sidePush}">
                <div class="item">
                    <a href="/admin">西电TIC项目池</a>
                </div>
                <div class="item">
                    <div class="header">
                        项目管理
                    </div>
                    <div class="menu">
                        <a href="<c:url value='/admin/project/check' />" class="item">待审核项目</a>
                        <a href="<c:url value='/admin/project/look' />" class="item">已通过项目</a>
                    </div>
                </div>
                <div class="item">
                    <div class="header">
                        用户管理
                    </div>
                    <div class="menu">
                        <a href="<c:url value='/admin/user/add' />" class="item">用户添加</a>
                        <a href="<c:url value='/admin/user/look' />" class="item">用户浏览</a>
                    </div>
                </div>
            </div>
            <div class="ui fixed inverted menu">
                <div class="ui container">
                    <a class="launch icon item" v-tap.prevent='{methods: push}'>
                        <i class="content icon"></i>
                    </a>
                    <div class="item tic-logo">TIC</div>
                    <div class="right menu">
                        <form @submit.prevent="search">
                            <div class="item">
                                <div class="ui icon input">
                                    <input type="text" :placeholder="placeholder" v-model="keyWords">
                                    <i class="search link icon"></i>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="pusher"
             :class="{dimmed: sidePush}">
                <fis:block name="article"></fis:block>
            </div>
        </div>
        <script type="text/javascript" charset="utf-8" src="http://192.168.199.1:8132/livereload.js"></script><!--livereload-->
    </fis:body>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/layout/frame_admin.jsp" />
</fis:html>

