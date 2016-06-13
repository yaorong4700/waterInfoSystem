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
	height: 20%;
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
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th>提示信息:</th>
					<td><input type="text" name="message" id="message" class="input_txt"	value="${message.message }" readonly/>
					</td></tr>
				<tr class="info">
					<th>状态:</th>
					<td><input type="text" name="state" id="state" class="input_txt" value="${message.state }" readonly/></td>
				</tr>
				
			</table>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>

	</body>
</html>