<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
		
		<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
		<script type="text/javascript" src="js/js_user/com.js"></script>
		<script type="text/javascript" src="js/cookie/cookie.js"></script>
		
		<script type="text/javascript">
		
		var language = getCookieValue("language");
		var E0005 = "";
		var E0006 = "";
		var E0007 = "";
		var I0003 = "";
		
		$(document).ready(function() {
		
		var language = getCookieValue("language");
		if (language!="CN" && language!="JP" && language!="EN"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);
			
		$("#passwordupdate").click(function() {   
	    var mypasswordnew  = $("#passwordnew").val();
	    var mypasswordconform  = $("#passwordconform").val();
	    if ($("#passwordnew").val() == "") {
			alert(E0005);
			$("#passwordnew").focus();
			return false;
		}
	    if ($("#passwordconform").val() == "") {
			alert(E0006);
			$("#passwordconform").focus();
			return false;
		}
	    if (mypasswordnew==mypasswordconform) {	
			}
		else{	
			alert(E0007);
			$("#passwordnew").focus();
			return false;
		}
	     $.ajax({ 
                type: "post", 
                url: "passwordupdate/passwordsend.do", 
                dataType: "json", 
                data:{"mypasswordnew":mypasswordnew}, 
                success: function (data) {
                	alert(I0003);
                }, 
            });      
          });
	});
		
	//国际化
	function setPageByLanguage(){
		
		document.title = $.i18n.prop('passwordupdate_string_title');
		$('#passwordupdate_string_newpassword').html($.i18n.prop('passwordupdate_string_newpassword'));
		$('#passwordupdate_string_reconfirm').html($.i18n.prop('passwordupdate_string_reconfirm'));
		$('#passwordupdate').val($.i18n.prop('passwordupdate_string_submit'));
		
		E0005 = $.i18n.prop('E0005');
		E0006 = $.i18n.prop('E0006');
		E0007 = $.i18n.prop('E0007');
		I0003 = $.i18n.prop('I0003');

	}

</script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工管理界面</title>
		<link type="text/css" rel="stylesheet" href="css/main.css" />
		<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 200px;
	height: 20px;
	line-height: 20px;
}


.info {
	height: 40px;
	line-height: 40px;
}

.info th {
	text-align: right;
	width: 100px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info td {
	text-align: left;
}
</style>
	</head>
	<body>
		<form action=".do" name="userForm" id="userForm" target="result"
			method="post" onsubmit="return checkInfo();">
			<input name="staffID" id="staffID" value="${staff.staffID }" type = "hidden"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th><label id="passwordupdate_string_newpassword">新密码:</label></th>
					<td><input type="password" name="passwordnew" id="passwordnew" class="input_txt"  /></td>
				</tr>
				<tr class="info">
					<th><label id="passwordupdate_string_reconfirm">再次确认:</label></th>
					<td><input type="password" name="passwordconform" id="passwordconform" class="input_txt"	 />
					</td>
				</tr>
				<tr class="info">
					<td><input type=button id="passwordupdate"  value=提交修改 />
					</td>
				</tr>
			</table>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>

	</body>
</html>