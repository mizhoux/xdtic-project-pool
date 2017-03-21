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
            <fis:require id="static/scss/weui.css" />
            <fis:require id="static/scss/common.scss" />
        </fis:block>

        <fis:block name="js">
            <fis:require id="static/libs/common.js" />
            <fis:require id="static/libs/vue.js" />
            <fis:require id="static/libs/vue-tap.js" />
            <fis:require id="static/libs/mod.js" />
        </fis:block>
        
        <fis:block name="jsPre">
        </fis:block>
    </fis:head>

    <fis:body class="body">
        <div id="wrapper">
            <fis:block name="body"></fis:block>
        </div>
    </fis:body>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/layout/frame.jsp" />
</fis:html>

