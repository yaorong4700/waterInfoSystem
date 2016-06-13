<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>保存结果</title>
<script type="text/javascript"><!--
var dg;
var language = getCookieValue("language");
var save = "";
var E00027 = "";
		$(document).ready(function() {
			
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage,"../");	
		dg = frameElement.lhgDG;
		dg.addBtn('ok', save, function() {
			$("#projectForm").submit();
		});
	});
function setPageByLanguage(){
	document.title = $.i18n.prop('saveResult_string_title');
	save = $.i18n.prop('saveResult_string_save');
	E00027 = $.i18n.prop('E00027');
}
function success() {
			if (dg.curWin.document.forms[0]) {
				dg.curWin.document.forms[0].action = dg.curWin.location + "";
				dg.curWin.document.forms[0].submit();
			} else {
				dg.curWin.location.reload();
			}
			dg.cancel();
		}

		function failed() {
			alert(E00027);
		}
		</script>
</head>
<body>
	<script type="text/javascript">
		var msg = "${msg}";
		if(msg=="success" || msg==""){
			
			parent.success();
		}else{
			parent.failed();
		}
	</script>
</body>
</html>