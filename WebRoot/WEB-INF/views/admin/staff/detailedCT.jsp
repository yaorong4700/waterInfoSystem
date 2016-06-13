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
		
		<title>员工管理</title>
		<link type="text/css" rel="stylesheet" href="../css/main.css" />
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
				width: 130px;
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
			var state= "${staff.state}";
			
			$(document).ready(function() {
				if (language!="CN" && language!="JP"){
					language = "CN";
				}
				loadProperties(language,setPageByLanguage,"../");
			});
			
			//国际化
			function setPageByLanguage(){
				//document.title = $.i18n.prop('test');
				$('#detailed_string_name').html($.i18n.prop('detailed_string_name'));
				$('#detailed_string_password').html($.i18n.prop('detailed_string_password'));
				$('#detailed_string_jobNo').html($.i18n.prop('detailed_string_jobNo'));
				$('#detailed_string_position').html($.i18n.prop('detailed_string_position'));
				$('#detailed_string_department').html($.i18n.prop('detailed_string_department'));
				$('#detailed_string_branch').html($.i18n.prop('detailed_string_branch'));
				$('#detailed_string_email').html($.i18n.prop('detailed_string_email'));
				$('#detailed_string_state').html($.i18n.prop('detailed_string_state'));
				$('#detailed_string_role').html($.i18n.prop('detailed_string_role'));
				$('#detailed_string_sort').html($.i18n.prop('detailed_string_sort'));
				$('#detailed_string_enrolementCode').html($.i18n.prop('detailed_string_enrolementCode'));
				$('#detailed_string_companyName').html($.i18n.prop('detailed_string_companyName'));
				$('#detailed_string_jobCategory').html($.i18n.prop('detailed_string_jobCategory'));
				$('#detailed_string_designQualified').html($.i18n.prop('detailed_string_designQualified'));
				$('#detailed_string_PMLevel').html($.i18n.prop('detailed_string_PMLevel'));
				$('#detailed_string_comment').html($.i18n.prop('detailed_string_comment'));
				
				if (state =="1") {
					$('#state').val($.i18n.prop('detailed_string_state_1'));
				} else if (state =="2") {
					$('#state').val($.i18n.prop('detailed_string_state_2'));
				} else if (state =="3") {
					$('#state').val($.i18n.prop('detailed_string_state_3'));
				} else if (state =="4") {
					$('#state').val($.i18n.prop('detailed_string_state_4'));
				}				
			}
	</script>
	
	</head>
	<body>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th><label id="detailed_string_name">员工姓名:</label></th>
					<td><input type="text" name="name" id="name" class="input_txt"	value="${staff.name }" readonly/>
					</td></tr>
				<c:if test="${staffInfo.role == 2}">
				<tr class="info"> 
					<th><label id="detailed_string_password">密码:</label></th>
					<td><input name="password" id="password" class="input_txt" value="${staff.password}" readonly/></td>
				</tr>
				</c:if>
				<tr class="info">
					<th><label id="detailed_string_jobNo">工号:</label></th>
					<td><input type="text" name="jobNo" id="jobNo" class="input_txt" value="${staff.jobNo}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_position">职位:</label></th>
					<td><input type="text" name="position" id="position" class="input_txt" value="${staff.position}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_department">部门:</label></th>
					<td><input type="text" name="department" id="department" class="input_txt" value="${staff.department}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_branch">课别:</label></th>
					<td><input type="text" name="branch" id="branch" class="input_txt" value="${staff.branch}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_enrolementCode">在籍所属コード:</label></th>
					<td><input type="text" name="enrolementCode" id="enrolementCode" class="input_txt" value="${staff.enrolementCode}" readonly /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_email">Email:</label></th>
					<td><input type="text" name="email" id="email" class="input_txt" value="${staff.email}" readonly /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_state">在职状态:</label></th>
					<td><input type="text" name="state" id="state" class="input_txt" value="" readonly/></td>
				<!--	<c:if test="${staff.state ==1}">value="在职"</c:if> <c:if test="${staff.state ==2}">value="休假"</c:if> <c:if test="${staff.state ==3}">value="离职"</c:if> readonly/></td>-->
				</tr>
				<tr class="info">
					<th><label id="detailed_string_role">角色权限:</label></th>
					<td>
					<input type="text" name="roleName" id="roleName" class="input_txt" value="${staff.roleName}" readonly/>
					</td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_sort">员工类别:</label></th>
					<td><input type="text" name="sort" id="sort" class="input_txt" value="${staff.sort}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_companyName">公司名称:</label></th>
					<td><input type="text" name="companyName" id="companyName" class="input_txt" value="${staff.companyName}" readonly /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_jobCategory">職種:</label></th>
					<td><input type="text" name="jobCategory" id="jobCategory" class="input_txt" value="${staff.jobCategory}" readonly /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_designQualified">設計有資格総合判定:</label></th>
					<td><input type="text" name="designQualified" id="designQualified" class="input_txt" value="${staff.designQualified}" readonly  /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_PMLevel">PMレベル:</label></th>
					<td><input type="text" name="pmLevel" id="pmLevel" class="input_txt" value="${staff.pmLevel}"  readonly /></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_comment">特記事項:</label></th>
					<td><input type="text" name="comment" id="comment" class="input_txt" value="${staff.comment}"  readonly /></td>
				</tr>
			</table>
		<iframe name="result" id="result" src="about:blank" frameborder="0"	width="0" height="0"></iframe>
	</body>
</html>