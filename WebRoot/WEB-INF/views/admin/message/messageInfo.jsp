<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>提示信息</title>
		<link type="text/css" rel="stylesheet" href="../css/main.css" />
		<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 400px;
	height: 20px;
	line-height: 20px;
}


.info {
	height: 40px;
	line-height: 40px;
}

.info th {
	text-align: right;
	width: 60px;
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
		<form action="save.do" name="userForm" id="userForm" target="result"
			method="post" onsubmit="return checkInfo();">
			<input name="id" id="id" value="${message.id}" type = "hidden"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th>提示信息:</th>
					<td><input type="text" name="message" id="message" class="input_txt"	value="${message.message }" />
					</td></tr>
				<tr class="info">
					<th>状态:</th>
					<td>
					<select name="state" id="state"   style="WIDTH: 400px;height: 25px">
					<OPTION VALUE="停用" <c:if test="${message.state eq '停用'}">SELECTED</c:if> >停用</OPTION>
					<OPTION VALUE="启用" <c:if test="${message.state eq '启用'}">SELECTED</c:if>>启用</OPTION>
					</select>
				</tr>
			</table>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>
<script language="javascript" type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>  
		<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript">
		var dg;
		$(document).ready(function() {
		dg = frameElement.lhgDG;
		
		dg.addBtn('ok', '保存', function() {
			$("#userForm").submit();
			//alert("qianqibaiguai");
		});
		if ($("#id").val() != "") {
			$("#username").attr("readonly", "readonly");
			$("#username").css("color", "gray");
			var state = "${user.state}";
			if (state != "") {
				$("#state").val(state);
			}
		}
	});

	function checkInfo() {
		if ($("#message").val() == "") {
			alert("请输入提示信息!");
			$("#message").focus();
			return false;
		}
		return true;
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
		alert("新增失败，该E-mail已存在！");
		$("#email").select();
		$("#email").focus();
	}
</script>
	</body>
</html>