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
				width: 65px;
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
			var role = "${staff.role}"; 	
		
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
				$('#detailed_string_gender').html($.i18n.prop('detailed_string_gender'));
				$('#detailed_string_dateGraduation').html($.i18n.prop('detailed_string_dateGraduation'));
				$('#detailed_string_dateIntoCompany').html($.i18n.prop('detailed_string_dateIntoCompany'));
				$('#detailed_string_position').html($.i18n.prop('detailed_string_position'));
				$('#detailed_string_memo').html($.i18n.prop('detailed_string_memo'));
				$('#detailed_string_superior').html($.i18n.prop('detailed_string_superior'));
				$('#detailed_string_department').html($.i18n.prop('detailed_string_department'));
				$('#detailed_string_branch').html($.i18n.prop('detailed_string_branch'));
				$('#detailed_string_team').html($.i18n.prop('detailed_string_team'));
				$('#detailed_string_email').html($.i18n.prop('detailed_string_email'));
				$('#detailed_string_state').html($.i18n.prop('detailed_string_state'));
				$('#detailed_string_role').html($.i18n.prop('detailed_string_role'));
				$('#detailed_string_sort').html($.i18n.prop('detailed_string_sort'));
				$('#detailed_string_dateQuitCompany').html($.i18n.prop('detailed_string_dateQuitCompany'));
				
				if (state =="1") {
					$('#state').val($.i18n.prop('detailed_string_state_1'));
				} else if (state =="2") {
					$('#state').val($.i18n.prop('detailed_string_state_2'));
				} else if (state =="3") {
					$('#state').val($.i18n.prop('detailed_string_state_3'));
				}
				
				if (role =="1") {
					$('#role').val($.i18n.prop('detailed_string_role_1'));
				} else if (role =="2") {
					$('#role').val($.i18n.prop('detailed_string_role_2'));
				} else if (role =="3") {
					$('#role').val($.i18n.prop('detailed_string_role_3'));
				} else if (role =="4") {
					$('#role').val($.i18n.prop('detailed_string_role_4'));
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
					<th><label id="detailed_string_gender">性别:</label></th>
					<td><input type="text" name="gender" id="gender" class="input_txt" value="${staff.gender }" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_dateGraduation">毕业时间:</label></th>
					<td><input type="text" name="dateGraduation" id="dateGraduation" class="input_txt"	value="${staff.dateGraduation }" readonly />
					</td></tr>

				<tr class="info">
					<th><label id="detailed_string_dateIntoCompany">进公司:</label></th>
					<td><input type="text" name="dateIntoCompany" id="dateIntoCompany" class="input_txt" value="${staff.dateIntoCompany}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_position">职位:</label></th>
					<td><input type="text" name="position" id="position" class="input_txt" value="${staff.position}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_memo">能力别:</label></th>
					<td><input type="text" name="memo" id="memo" class="input_txt" value="${staff.memo}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_superior">上司:</label></th>
					<td><input type="text" name="superior" id="superior" class="input_txt"	value="${staff.superior }" readonly/>
					</td></tr>

				<tr class="info">
					<th><label id="detailed_string_department">部门:</label></th>
					<td><input type="text" name="department" id="department" class="input_txt" value="${staff.department}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_branch">课别:</label></th>
					<td><input type="text" name="branch" id="branch" class="input_txt" value="${staff.branch}" readonly/></td>
				</tr>
				<tr class="info">
					<th><label id="detailed_string_team">组别:</label></th>
					<td><input type="text" name="team" id="team" class="input_txt" value="${staff.team}" readonly/></td>
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
					<th><label id="detailed_string_dateQuitCompany">离职日:</label></th>
					<td><input type="text" name="dateQuitCompany" id="dateQuitCompany" class="input_txt"  onClick="WdatePicker()" value="${staff.dateQuitCompany}" /></td>
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
			</table>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>
			
		

	</body>
</html>