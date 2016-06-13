<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="../js/jquery.form.js"></script>
		<script type="text/javascript" src="../js/jquery.i18n.properties-1.0.9.js"></script>
		<script type="text/javascript" src="../js/js_user/com.js"></script>
		<script type="text/javascript" src="../js/cookie/cookie.js"></script>
		<title>下载</title>
		<link type="text/css" rel="stylesheet" href="../css/main.css" />
		<script type="text/javascript">
		var language = getCookieValue("language");
		function setPageByLanguage(){
			document.title = $.i18n.prop('download_string_title');
			$('#download').html($.i18n.prop('点击下载'));
		}
		
		$(document).ready(function() {
			
			if (language!="CN" && language!="JP"){
				language = "CN";
			}
			loadProperties(language,setPageByLanguage,"../");
		});
		</script>
	</head>
	<body>
		<p><a  id="download" href="${webadr}" >点击下载</a></p>
	</body>
</html>