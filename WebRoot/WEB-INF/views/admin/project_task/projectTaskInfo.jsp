<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>作业类型录入界面</title>
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
	font-size: 13px;
}

.info td {
	text-align: left;
}
</style>
	</head>
	<body>
		<form action="save.do" name="projectTaskForm" id="projectTaskForm" target="result"
			method="post" onsubmit="return checkInfo();">
			<input name="taskID" id="taskID" value="${projectTask.taskID }" type = "hidden"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th><label id="projectTaskInfo_string_tblcategory">项目类别:</label></th>
					<td>
					<select name="categoryID" id="categoryID"   style="WIDTH: 205px;height: 25px">
					<c:forEach items="${categoryList}" var="category" varStatus="vs">
					<OPTION value="${category.categoryID}" <c:if test="${projectTask.categoryID eq category.categoryID}">SELECTED</c:if>>${category.categoryName}</OPTION>
					</c:forEach>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="projectTaskInfo_string_tbltask">作业类型名称:</label></th>
					<td><input type="text" name="task" id="task" class="input_txt" value="${projectTask.task}" />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<c:if test='${staff.kaisya == "CT" }'>
				<tr class="info">
					<th><label id="projectTaskInfo_string_tbltaskProcessID">作业类型ID:</label></th>
					<td><input type="text" name="taskProcessID" id="taskProcessID" class="input_txt" value="${projectTask.taskProcessID}" />
					<label style="color:red;">*</label>
					</td>
				</tr>
				</c:if>
				<tr class="info">
					<th><label id="projectTaskInfo_string_tbldepartment">所属部门:</label></th>
					<td>
					<select name="department" id="department"   style="WIDTH: 205px;height: 25px">
					<c:forEach items="${departList}" var="department" varStatus="vs">
					<OPTION value="${department.departmentID}" <c:if test="${projectTask.department eq department.department}">SELECTED</c:if>>${department.department}</OPTION>
					</c:forEach>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info" style="display:none">
					<th>activity:</th>
					<td><input type="text" name="activity" id="activity" class="input_txt" value="${projectTask.activity}" /></td>
				</tr>
				<tr class="info">
					<th><label id="projectTaskInfo_string_tblmemo">所属类别:</label></th>
					<td>
					<select name="memo" id="memo"   style="WIDTH: 205px;height: 25px" >
				<!--  	<OPTION VALUE=" " <c:if test="${projectTask.memo == ' '}">SELECTED</c:if> >空</OPTION>
					<OPTION VALUE="开发" <c:if test="${projectTask.memo =='开发'}">SELECTED</c:if> >开发</OPTION>
					<OPTION VALUE="附带" <c:if test="${projectTask.memo =='附带'}">SELECTED</c:if>>附带</OPTION>-->
					</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="projectTaskInfo_string_isVisible">表示:</label></th>
					<td>
						<select name="isVisible" id="isVisible" style="WIDTH: 205px;height: 25px" >
						<OPTION id="projectTaskInfo_string_visible_1" VALUE="1" <c:if test="${projectTask.isVisible eq '1'}">SELECTED</c:if>>表示</OPTION>
						<OPTION id="projectTaskInfo_string_visible_2" VALUE="0" <c:if test="${projectTask.isVisible eq '0'}">SELECTED</c:if>>非表示</OPTION>
                		</select>
                		<label style="color:red;">*</label>
					</td>
				</tr>
			</table>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0"
			width="0" height="0"></iframe>
		<script language="javascript" type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>  
		<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
		
		<script type="text/javascript" src="../js/jquery.i18n.properties-1.0.9.js"></script>
		<script type="text/javascript" src="../js/js_user/com.js"></script>
		<script type="text/javascript" src="../js/cookie/cookie.js"></script>
		
		<script type="text/javascript">
		var dg;
		
		var language = getCookieValue("language");
		var memo = "${projectTask.memo}";
		var save = "";
		var E00028 = "";
		var E00029 = "";
		var E00030 = "";
		
		function setPageByLanguage(){
			//document.title = $.i18n.prop('staffInfo_string_title');
			$('#projectTaskInfo_string_tblcategory').html($.i18n.prop('projectTaskInfo_string_tblcategory'));
			$('#projectTaskInfo_string_tbltask').html($.i18n.prop('projectTaskInfo_string_tbltask'));
			$('#projectTaskInfo_string_tbldepartment').html($.i18n.prop('projectTaskInfo_string_tbldepartment'));
			$('#projectTaskInfo_string_tblmemo').html($.i18n.prop('projectTaskInfo_string_tblmemo'));
			$('#projectTaskInfo_string_tbltaskProcessID').html($.i18n.prop('projectTaskInfo_string_tbltaskProcessID'));
			$('#projectTaskInfo_string_isVisible').html($.i18n.prop('projectTaskInfo_string_isVisible'));
			$('#projectTaskInfo_string_visible_1').html($.i18n.prop('projectTaskInfo_string_visible_1'));
			$('#projectTaskInfo_string_visible_2').html($.i18n.prop('projectTaskInfo_string_visible_2'));
			
			var memo1 = $.i18n.prop('projectTaskInfo_string_tblmemo_1');
			var memo2 = $.i18n.prop('projectTaskInfo_string_tblmemo_2');
			var memo3 = $.i18n.prop('projectTaskInfo_string_tblmemo_3');
			if (memo==" "){
				$('#memo').append('<OPTION VALUE=" " SELECTED>'+memo1+'</OPTION>'+'<OPTION  VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION  VALUE="' + memo3+'">'+memo3+'</OPTION>');
			} else if (memo==memo2){
				$('#memo').append('<OPTION VALUE=" ">'+memo1+'</OPTION>'+'<OPTION  VALUE="' + memo2+'" SELECTED>'+memo2+'</OPTION>'+'<OPTION  VALUE="' + memo3+'">'+memo3+'</OPTION>');
			} else if (memo==memo3){
				$('#memo').append('<OPTION VALUE=" ">'+memo1+'</OPTION>'+'<OPTION  VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION  VALUE="' + memo3+'" SELECTED>'+memo3+'</OPTION>');
			} else {
				$('#memo').append('<OPTION VALUE=" " SELECTED>'+memo1+'</OPTION>'+'<OPTION  VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION  VALUE="' + memo3+'">'+memo3+'</OPTION>');
			}
			
			$('#projectTaskInfo_string_tbltask').html($.i18n.prop('projectTaskInfo_string_tbltask'));
			
			save = $.i18n.prop('projectTaskInfo_string_save');
			E00028 = $.i18n.prop('E00028');
			E00029 = $.i18n.prop('E00029');
			E00030 = $.i18n.prop('E00030');
		}
		
		$(document).ready(function() {
			if (language!="CN" && language!="JP"){
				language = "CN";
			}
			loadProperties(language,setPageByLanguage,"../");
		dg = frameElement.lhgDG;
		dg.addBtn('ok', save, function() {
			$("#projectTaskForm").submit();
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
		if ($("#task").val() == "") {
			alert(E00028);
			$("#task").focus();
			return false;
		}
		if ('${staff.kaisya == "CT" }'){
			if ($("#taskProcessID").val() == ""){
				alert($.i18n.prop('E0094'));
				$("#taskProcessID").focus();
				return false;
			}
		} 
		if ($("#department").val() == "") {
			alert(E00029);
			$("#department").focus();
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
		alert(E00030);
		$("#task").select();
		$("#task").focus();
	}
	</script>
	</body>
</html>