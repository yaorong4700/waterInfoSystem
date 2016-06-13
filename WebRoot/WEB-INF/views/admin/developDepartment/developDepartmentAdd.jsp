<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门录入界面</title>
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
	width: 90px;
	color: #4f4f4f;
	padding-right: 5px;
	padding-top:15px;
	font-size: 13px;
}

.info td {
	text-align: left;
	padding-top:15px;
}
</style>
</head>
<body>
	<form action="save.do" name="projectForm" id="projectForm"
		target="result" method="post" onsubmit="return checkInfo();">
		<input name="type" id="type" value="${developDepartment.type}" type="hidden" />
		<input name="belongCode" id="belongCodeOld" value="${developDepartment.belongCode}" type="hidden" />
		<input name="departmentIDOld" id="departmentIDOld" value="${developDepartment.departmentID}" type="hidden" />
		<input name="branchOld" id="branchOld" value="${developDepartment.branch}" type="hidden" />
		<input name="branchIDOld" id="branchIDOld" value="${developDepartment.branchID}" type="hidden" />
		<input name="departmentID" id="departmentID" value="${developDepartment.departmentID}" type="hidden" />
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><label id="lab_addDepartment" style="padding-top:30;">部门名称:</label></th>
				<td><input type="text" name="department" id="department" class="input_txt" value="${developDepartment.department}" />
				<label style="color:red;">*</label>
				</td>
			</tr>
			<tr class="info">
				<th><label id="lab_addBranch">课别名称:</label></th>
				<td><input type="text" name="branch" id="branch" class="input_txt" value="${developDepartment.branch}"/>
				</td>
			</tr>
			
			<tr class="info">
				<th><label id="lab_addTeam">组别名称:</label></th>
				<td><input type="text" name="team" id="team" class="input_txt" value="${developDepartment.team}" /></td>
			</tr>
			<tr class="info">
				<th><label id="lab_addDepartmentCategory">部门分类:</label></th>
				<td><input type="text" name="departmentCategory" id="departmentCategory" class="input_txt" value="${developDepartment.departmentCategory}"/></td>
			</tr>
			
			<tr class="info">
				<th><label id="lab_addMemo">备注:</label></th>
				<td><input type="text" name="branchMemo" id="branchMemo" class="input_txt" value="${developDepartment.branchMemo}" /></td>
			</tr>
			
		</table>
	</form>
	<iframe name="result" id="result" src="about:blank" frameborder="0"
		width="0" height="0"></iframe>
	<script language="javascript" type="text/javascript"
		src="../js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>

	<script type="text/javascript"
		src="../js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="../js/js_user/com.js"></script>
	<script type="text/javascript" src="../js/cookie/cookie.js"></script>

	<script type="text/javascript">
		var dg;
		
		var language = getCookieValue("language");
		var save = "";
		var E00024 = "";
		var E00025 = "";
		var E00026 = "";
		var E00031 = "";
		var E00050 = "";
		
		function setPageByLanguage(){
			document.title = $.i18n.prop('projectInfo_string_title');
			$('#projectInfo_string_projectName').html($.i18n.prop('projectInfo_string_projectName'));
			$('#projectInfo_string_category').html($.i18n.prop('projectInfo_string_category'));
			$('#projectInfo_string_projectClientNo').html($.i18n.prop('projectInfo_string_projectClientNo'));
			$('#projectInfo_string_projectClientName').html($.i18n.prop('projectInfo_string_projectClientName'));
			$('#projectInfo_string_projectClientName_1').html($.i18n.prop('projectInfo_string_projectClientName_1'));
			$('#projectInfo_string_startupDate').html($.i18n.prop('projectInfo_string_startupDate'));
			$('#projectInfo_string_finishDate').html($.i18n.prop('projectInfo_string_finishDate'));
			$('#projectInfo_string_projectState').html($.i18n.prop('projectInfo_string_projectState'));
			$('#projectInfo_string_projectState_1').html($.i18n.prop('projectInfo_string_projectState_1'));
			$('#projectInfo_string_projectState_2').html($.i18n.prop('projectInfo_string_projectState_2'));
			$('#projectInfo_string_projectState_3').html($.i18n.prop('projectInfo_string_projectState_3'));
			$('#projectInfo_string_projectState_4').html($.i18n.prop('projectInfo_string_projectState_4'));
			$('#projectInfo_string_carMaker').html($.i18n.prop('projectInfo_string_carMaker')); 
			$('#projectInfo_string_department').html($.i18n.prop('projectInfo_string_department')); 
			$('#projectInfo_string_department_1').html($.i18n.prop('projectInfo_string_department_1')); 
			$('#projectInfo_string_allBranch').html($.i18n.prop('projectInfo_string_allBranch')); 
			$('#projectInfo_string_function').html($.i18n.prop('projectInfo_string_function')); 
			$('#projectInfo_string_model').html($.i18n.prop('projectInfo_string_model')); 
			$('#projectInfo_string_transferNo').html($.i18n.prop('projectInfo_string_transferNo')); 
			//3D項番名、PJNo、仮PJNo、PJ名追加	HSCNZJ	ZZP
			$('#projectInfo_string_itemName').html($.i18n.prop('projectInfo_string_itemName'));
			$('#projectInfo_string_PJNo').html($.i18n.prop('projectInfo_string_PJNo'));
			$('#projectInfo_string_tempPJNo').html($.i18n.prop('projectInfo_string_tempPJNo'));
			$('#projectInfo_string_PJName').html($.i18n.prop('projectInfo_string_PJName'));
			$('#projectInfo_string_memo').html($.i18n.prop('projectInfo_string_memo')); 
			
			//	HSCNZJ	CY
			
			$('#lab_addDepartment').html($.i18n.prop('developDepartment_string_Department')); 
			$('#lab_addBranch').html($.i18n.prop('developDepartment_string_Branch')); 
			$('#lab_addDepartmentCategory').html($.i18n.prop('developDepartment_string_DepartmentCategory')); 
			$('#lab_addTeam').html($.i18n.prop('developDepartment_string_Team')); 
			$('#lab_addMemo').html($.i18n.prop('developDepartment_string_Memo')); 
			
			save = $.i18n.prop('projectInfo_string_save');
			E00024 = $.i18n.prop('E00024');
			E00025 = $.i18n.prop('E00025');
			E00026 = $.i18n.prop('E00026');
			E00050 = $.i18n.prop('E00050');
			E00031 = $.i18n.prop('E00031');
			E00039 = $.i18n.prop('E00039');
			E00040 = $.i18n.prop('E00040');
			E00041 = $.i18n.prop('E00041');
			E00051 = $.i18n.prop('E00051');
			E00058 = $.i18n.prop('E00058');

			
		}
		
		$(document).ready(function() {
			
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage,"../");	
			
		dg = frameElement.lhgDG;
		dg.addBtn('ok', save, function() {
			$("#projectForm").submit();
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
    

	function success() {
	    frameElement.lhgDG.curWin.successReload(); 
		dg.cancel();
	}


	function checkInfo() {
		if ($("#department").val() == "") {
			alert(E00031);
			$("#department").focus();
			return false;
		}
		
		return true;
	}

	function failed() {
		alert(E00026);
		/* $("#projectName").select();
		$("#projectName").focus(); */
	}
	function failedOnlyDepartment() {
		alert(E00039);
		
	}
	function failedManyBranchTeam() {
		alert(E00041);
		
	}
	function failedNoDepartment() {
		alert(E00040);
	}
	function failedDepartmentExist() {
		alert(E00058);
	}
	</script>
</body>
</html>