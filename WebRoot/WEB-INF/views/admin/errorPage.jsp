<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
<style>
		/*democss*/
		body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td{margin:0;padding:0;}
h3{color:#555;font-weight:normal;margin:10px 0px; }body{text-align:center;margin-top:30px;}
h1,h3{font-family: microsoft yahei}
table{width:600px;margin:0px auto;background: #ff6600;color:#fff}
ul,ol{
    list-style:none;
}
a{color:blue;margin:0px 3px }
#result{width:560px;margin:30px auto;padding:20px;}
table td{cursor:pointer;text-align: left;padding:5px;width: 100px;}
body,button,input,select,textarea{font:12px/1.5 tahoma,arial,\5b8b\4f53,sans-serif;}
		
		/*弹窗css*/
.jsadsense_case{height:320px; width:320px; position:absolute; border:1px solid #a95f00;z-index:100000; border-bottom:0; bottom:0px; right:0px; background:#fff; overflow:hidden;}
.jsadsense_tit{ width:320px; height:30px;  line-height:30px;}
.jsadsense_tit h3{margin:0px; height:30px; padding-left:10px; font-size:14px; font-family:"microsoft yahei"; color:#810000;  float:left; display:inline;}
.jsadsense_tit span{ width:15px; height:30px;font-weight: bold;  color:#810000; cursor:pointer; font-size:12px; font-family:"microsoft yahei"; float:right; display:block;}
.jsadsense__con{ width:320px; height:290px; overflow:hidden;}</style>


<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jsadsense/jsadsense.js"></script>
<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/js_user/com.js"></script>
</head>
<body style="height: 100%;background-color:#daeef3">
	<script type="text/javascript">
  		//禁止后退键 作用于Firefox、Opera   
		document.onkeypress=banBackSpace;  
		//禁止后退键  作用于IE、Chrome   
		document.onkeydown=banBackSpace;
		$(document).ready(function(){
			loadProperties("zh",setPageByLanguage);
		});
		function setPageByLanguage(){
			$("#p_msg1").html($.i18n.prop('UserRoleManage_String_RoleName'));
			$("#p_msg2").html($.i18n.prop('UserRoleManage_String_RoleName'));
		}
	</script>
    <div style="height: 10px;text-align: left;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="clarion" src="images/clarion.png" ></div>
    <div style="margin-left:10%;font-size:30px;font-weight: bold;color:#FF0000;text-align:center;width:90%"><p id="p_msg1"></p><p id="p_msg2"></p></div>
    <div style="background-color:#daeef3;min-height: 100%;">
	<div id="notice" style="text-align:left;font-family: monospace"></div>
	</div>
</body>
</html>
