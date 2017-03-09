<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame.jsp">
	<fis:block name="body">
		<div class="tic-gallery">
			<p class="tic-description">
				页面找不到了，看会漫画放松下撒~
			</p>
			<img src="/xdtic/static/images/404.jpg" alt="404 NOT FOUND">
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />

		<style type="text/css">
			.tic-description {
				padding: .5em 0;
			    text-align: center;
			    background: #58d825;
			    color: #fff;
			}

			.tic-gallery > img {
				max-width: 100%;
			}
		</style>
	</fis:block>
 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/error.jsp" />
</fis:extends>