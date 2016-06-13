<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript"
	src="../js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js/js_user/com.js"></script>
<script type="text/javascript" src="../js/cookie/cookie.js"></script>

<title>项目详细信息</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #FFFFFF;
	text-align: center;
}

.input_txt {
	width: 300px;
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
<script type="text/javascript">
			var language = getCookieValue("language");
			var projectState= "${project.projectState}";
		
			$(document).ready(function() {
				if (language!="CN" && language!="JP"){
					language = "CN";
				}
				loadProperties(language,setPageByLanguage,"../");
			});
			
			//国际化
			function setPageByLanguage(){
				document.title = $.i18n.prop('projectDetail_string_title');
				$('#projectDetail_string_projectName').html($.i18n.prop('projectDetail_string_projectName'));
				$('#projectDetail_string_category').html($.i18n.prop('projectDetail_string_category'));
				$('#projectDetail_string_directProjectID').html($.i18n.prop('projectDetail_string_directProjectID'));
				$('#projectDetail_string_projectClientNo').html($.i18n.prop('projectDetail_string_projectClientNo'));
				$('#projectDetail_string_projectClientName').html($.i18n.prop('projectDetail_string_projectClientName'));
				//$('#projectDetail_string_bunch').html($.i18n.prop('projectDetail_string_bunch'));横串削除	HSCNZJ	ZZP
				$('#projectDetail_string_startupDate').html($.i18n.prop('projectDetail_string_startupDate'));
				$('#projectDetail_string_finishDate').html($.i18n.prop('projectDetail_string_finishDate'));
				$('#projectDetail_string_projectState').html($.i18n.prop('projectDetail_string_projectState'));
				$('#projectDetail_string_carMaker').html($.i18n.prop('projectDetail_string_carMaker'));
				$('#projectDetail_string_projectDepartment').html($.i18n.prop('projectDetail_string_projectDepartment'));
				$('#projectDetail_string_projectBranch').html($.i18n.prop('projectDetail_string_projectBranch'));
				$('#projectDetail_string_function').html($.i18n.prop('projectDetail_string_function'));
				$('#projectDetail_string_model').html($.i18n.prop('projectDetail_string_model'));
				$('#projectDetail_string_transferNo').html($.i18n.prop('projectDetail_string_transferNo'));
				//3D項番名、PJNo、仮PJNo、PJ名追加	HSCNZJ	ZZP
				$('#projectDetail_string_itemName').html($.i18n.prop('projectDetail_string_itemName'));
				$('#projectDetail_string_PJNo').html($.i18n.prop('projectDetail_string_PJNo'));
				$('#projectDetail_string_tempPJNo').html($.i18n.prop('projectDetail_string_tempPJNo'));
				$('#projectDetail_string_PJName').html($.i18n.prop('projectDetail_string_PJName'));
				$('#projectDetail_string_memo').html($.i18n.prop('projectDetail_string_memo'));
				
				if (projectState =="1") {
					$('#projectState').val($.i18n.prop('projectDetail_string_projectState_1'));
				} else if (projectState =="2") {
					$('#projectState').val($.i18n.prop('projectDetail_string_projectState_2'));
				} else if (projectState =="3") {
					$('#projectState').val($.i18n.prop('projectDetail_string_projectState_3'));
				} else if (projectState =="4") {
					$('#projectState').val($.i18n.prop('projectDetail_string_projectState_4'));
				}
				
			}
	</script>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr class="info">
			<th><label id="projectDetail_string_projectName">项目名称:</label></th>
			<td><input type="text" name="projectName" id="projectName"
				class="input_txt" style="border: 0px"
				value="${project.projectName }" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_category">项目类别:</label></th>
			<td><input type="text" name="category" id="category"
				class="input_txt" style="border: 0px" value="${project.category}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_directProjectID">直属项目:</label></th>
			<td><input type="text" name="directProjectID"
				id="directProjectID" style="border: 0px" class="input_txt"
				value="${project.directProjectID}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_projectClientNo">项目委托号</label>:</th>
			<td><input type="text" name="projectClientNo"
				id="projectClientNo" style="border: 0px" class="input_txt"
				value="${project.projectClientNo}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_projectClientName">项目委托方:</label></th>
			<td><input type="text" name="projectClientName"
				id="projectClientName" style="border: 0px" class="input_txt"
				value="${project.projectClientName}" readonly /></td>
		</tr>
		<!-- <tr class="info">	横串削除	HSCNZJ	ZZP
					<th><label id="projectDetail_string_bunch">横串:</label></th>
					<td><input type="text" name="bunch" id="bunch" class="input_txt" style="border:0px"value="${project.bunch}" readonly/></td>
				</tr> -->
		<tr class="info">
			<th><label id="projectDetail_string_startupDate">启动时间:</label></th>
			<td><input type="text" name="startupDate" id="startupDate"
				class="input_txt" style="border: 0px" value="${project.startupDate}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_finishDate">结束时间:</label></th>
			<td><input type="text" name="finishDate" id="finishDate"
				class="input_txt" style="border: 0px" value="${project.finishDate}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_projectState">项目状态</label></th>
			<td><input type="text" name="projectState" id="projectState"
				class="input_txt" style="border: 0px" readonly /> <!--<c:if test="${project.projectState ==1}">value="开始"</c:if> 
					<c:if test="${project.projectState ==2}">value="完成"</c:if>
					<c:if test="${project.projectState ==3}">value="暂停"</c:if> 
					<c:if test="${project.projectState ==4}">value="终止"</c:if>--></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_carMaker">车厂:</label></th>
			<td><input type="text" name="carMaker" id="carMaker"
				class="input_txt" style="border: 0px" value="${project.carMaker}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_projectDepartment">项目所属部门:</label></th>
			<td><input type="text" name="projectDepartment"
				id="projectDepartment" class="input_txt" style="border: 0px"
				value="${project.projectDepartment}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_projectBranch">项目所属课别:</label></th>
			<td><input type="text" name="projectBranch" id="projectBranch"
				class="input_txt" style="border: 0px"
				value="${project.projectBranch}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_function">项目所属功能:</label></th>
			<td><input type="text" name="function" id="function"
				class="input_txt" style="border: 0px" value="${project.function}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_model">机种:</label></th>
			<td><input type="text" name="model" id="model" class="input_txt"
				style="border: 0px" value="${project.model}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_transferNo">管理项番:</label></th>
			<td><input type="text" name="transferNo" id="transferNo"
				class="input_txt" style="border: 0px" value="${project.transferNo}"
				readonly /></td>
		</tr>
		<!-- 3D項番名、PJNo、仮PJNo、PJ名追加	HSCNZJ	ZZP -->
		<tr class="info">
			<th><label id="projectDetail_string_itemName">3D项番名:</label></th>
			<td><input type="text" name="itemName" id="itemName"
				class="input_txt" style="border: 0px" value="${project.itemName}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_PJNo">PJ No:</label></th>
			<td><input type="text" name="PJNo" id="PJNo" class="input_txt"
				style="border: 0px" value="${project.PJNo}" readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_tempPJNo">仮PJ No:</label></th>
			<td><input type="text" name="tempPJNo" id="tempPJNo"
				class="input_txt" style="border: 0px" value="${project.tempPJNo}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_PJName">PJ名:</label></th>
			<td><input type="text" name="PJName" id="PJName"
				class="input_txt" style="border: 0px" value="${project.PJName}"
				readonly /></td>
		</tr>
		<tr class="info">
			<th><label id="projectDetail_string_memo">备注:</label></th>
			<td><input type="text" name="memo" id="memo" class="input_txt"
				style="border: 0px" value="${project.memo}" readonly /></td>
		</tr>
	</table>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>

</body>
</html>