<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="js/ext-4.0.7-gpl/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/extstyle.css" />
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog-4.20/lhgdialog.min.js?self=false&skin=iblue"></script>
	<!-- 以下2个javascript为国际化接口 -->
	<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="js/js_user/com.js"></script>
	<script type="text/javascript" src="js/cookie/cookie.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/ext-all.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/CellEditing.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/ComboPageSize.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/GridTitleAlign.js"></script>
<title>欢迎</title>
</head>
<body style="width:100%">
<input type="hidden" id="tbxHiddenFileName" value="${fileName}"></input>
<input type="hidden" id="tbxHiddentype" value="${type}"></input>
<div style="text-align:center;margin-top:10px">
 <a href="#" onclick="downLoadExcel()" id="downLoadLink">点击下载</a>
</div>
<div style="text-align:right;margin-top:10px;position:absolute;right:10px;bottom:10px;">
<input id="btnClose" type="button"  style="width: 50px;" name="exportBtn" onclick="windowClose()"/>
</div>
<script type="text/javascript">
var language = getCookieValue("language");
$(document).ready(function(){
	//禁止后退键 作用于Firefox、Opera   
	document.onkeypress=banBackSpace;  
	//禁止后退键  作用于IE、Chrome   
	document.onkeydown=banBackSpace;
	//国际化
	if (language!="CN" && language!="JP"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage);
});
function downLoadExcel(){
	var fileName=document.getElementById("tbxHiddenFileName").value;
	var type=document.getElementById("tbxHiddentype").value;
	if(type == "3"){
		fileName = encodeURI(fileName);
	}
	var url="downLoad.do?path="+fileName+"&type="+type;
	window.location.href=url;
}
function windowClose(){
	var api = frameElement.api;
	api.close();
}
//国际化
function setPageByLanguage(){
	$('#downLoadLink').html($.i18n.prop('default_string_downloadLink'));
	$('#btnClose').val($.i18n.prop('default_string_closeBtn'));
}
</script>
</body>
</html>