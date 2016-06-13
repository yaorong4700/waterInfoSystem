<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目录入界面</title>
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

	<form action="save.do" name="projectForm" id="projectForm"
		target="result" method="post" onsubmit="return checkInfo();">
		<input type="hidden" name="projectNameOld" id="projectNameOld" value="${project.projectName }" /></input>
		<input name="projectID" id="projectID" value="${project.projectID }"
			type="hidden" />
		<input name="carMaker" id="carMaker" value="${project.carMaker}"
			type="hidden" />	
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><label id="projectInfo_string_projectName">项目名称:</label></th>
				<td><input type="text" name="projectName" id="projectName"
					class="input_txt" value="${project.projectName }" />
				<label style="color:red;">*</label>
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_category">项目类别:</label></th>
				<td><select name="category" id="category"
					style="WIDTH: 205px; height: 25px">
						<c:forEach items="${categoryList}" var="category" varStatus="vs">
							<OPTION value="${category}"
								<c:if test="${project.category eq category}">SELECTED</c:if>>${category}</OPTION>
						</c:forEach>
				</select></td>
			</tr>
			<!--<tr class="info">
					<th>直属项目:</th>
					<td><input type="text" name="directProjectID" id="directProjectID" class="input_txt" value="${project.directProjectID}" type = "hidden"/></td>
				</tr>
				-->
			<tr class="info">
				<th><label id="projectInfo_string_projectClientNo">项目委托号:</label></th>
				<td><input type="text" name="projectClientNo"
					id="projectClientNo" class="input_txt"
					value="${project.projectClientNo}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_projectClientName">项目委托方:</label></th>
				<td><select name="projectClientName" id="projectClientName"
					style="WIDTH: 205px; height: 25px">
						<option id="projectInfo_string_projectClientName_1" value="无">无</option>
						<c:forEach items="${clientList}" var="project_client"
							varStatus="vs">
							<OPTION value="${project_client}"
								<c:if test="${project.projectClientName eq project_client}">SELECTED</c:if>>${project_client}</OPTION>
						</c:forEach>
				</select></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_startupDate">启动时间:</label></th>
				<td><input type="text" name="startupDate" id="startupDate"
					class="input_txt" onClick="WdatePicker()"
					value="${project.startupDate}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_finishDate">结束时间:</label></th>
				<td><input type="text" name="finishDate" id="finishDate"
					class="input_txt" onClick="WdatePicker()"
					value="${project.finishDate}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_projectState">项目状态</label></th>
				<td><select name="projectState" id="projectState"
					style="WIDTH: 205px; height: 25px">
						<OPTION id="projectInfo_string_projectState_1" VALUE="1"
							<c:if test="${project.projectState ==1}">SELECTED</c:if>>開発中</OPTION>
						<OPTION id="projectInfo_string_projectState_2" VALUE="2"
							<c:if test="${project.projectState ==2}">SELECTED</c:if>>開発完了</OPTION>
						<OPTION id="projectInfo_string_projectState_3" VALUE="3"
							<c:if test="${project.projectState ==3}">SELECTED</c:if>>開発中止</OPTION>
						<OPTION id="projectInfo_string_projectState_4" VALUE="4"
							<c:if test="${project.projectState ==4}">SELECTED</c:if>>保留</OPTION>
				</select>
				<label style="color:red;">*</label>
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_carMaker">车厂:</label></th>
				<td><select name="carMakerID" id="carMakerID" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="project_carmake" value="" SELECTED>--请选择--</option>
					<c:forEach items="${projectCarmake}" var="projectCarmakeCommon" varStatus="vs">
						<OPTION value="${projectCarmakeCommon.carmakeID}" 
						<c:if test="${projectCarmakeCommon.carmakeID eq project.carMakerID}">SELECTED</c:if>>${projectCarmakeCommon.carmake}</OPTION>
					</c:forEach>
				</select></td>
			</tr>
			<!--  <tr class="info">
					<th>项目所属部门:</th>
					<td>
					<select name="projectDepartment" id="projectDepartment"   style="WIDTH: 205px;height: 25px" >
					<!--<option value=${project.projectDepartment}  selected >${project.projectDepartment}</option>-->
			<!-- <OPTION VALUE="无" <c:if test="${project.projectDepartment == '无'}">SELECTED</c:if> >无</OPTION>
					<OPTION VALUE="全体部门" <c:if test="${project.projectDepartment =='全体部门'}">SELECTED</c:if> >全体部门</OPTION>
					<OPTION VALUE="机构、电气、软件" <c:if test="${project.projectDepartment =='机构、电气、软件'}">SELECTED</c:if>>机构、电气、软件</OPTION>
					<OPTION VALUE="机构开发部、电气开发部" <c:if test="${project.projectDepartment =='机构开发部、电气开发部'}">SELECTED</c:if>>机构开发部、电气开发部</OPTION>
					<OPTION VALUE="机构开发部、开发品质保证部" <c:if test="${project.projectDepartment =='机构开发部、开发品质保证部'}">SELECTED</c:if>>机构开发部、开发品质保证部</OPTION>
					<OPTION VALUE="软件开发部" <c:if test="${project.projectDepartment =='软件开发部'}">SELECTED</c:if>>软件开发部</OPTION>
					<OPTION VALUE="机构开发部" <c:if test="${project.projectDepartment =='机构开发部'}">SELECTED</c:if>>机构开发部</OPTION>
					<OPTION VALUE="电气开发部" <c:if test="${project.projectDepartment =='电气开发部'}">SELECTED</c:if>>电气开发部</OPTION>
					<OPTION VALUE="开发品质保证部" <c:if test="${project.projectDepartment =='开发品质保证部'}">SELECTED</c:if>>开发品质保证部</OPTION>
					<OPTION VALUE="构想开发室" <c:if test="${project.projectDepartment =='构想开发室'}">SELECTED</c:if>>构想开发室</OPTION>
					<OPTION VALUE="工程设计部" <c:if test="${project.projectDepartment =='工程设计部'}">SELECTED</c:if>>工程设计部</OPTION>
					<OPTION VALUE="商品开发室" <c:if test="${project.projectDepartment =='商品开发室'}">SELECTED</c:if>>商品开发室</OPTION>
					<OPTION VALUE="开发管理部" <c:if test="${project.projectDepartment =='开发管理部'}">SELECTED</c:if>>开发管理部</OPTION>
					<OPTION VALUE="原价企画部" <c:if test="${project.projectDepartment =='原价企画部'}">SELECTED</c:if>>原价企画部</OPTION>
					<OPTION VALUE="设计开发部设计课" <c:if test="${project.projectDepartment =='设计开发部设计课'}">SELECTED</c:if>>设计开发部设计课</OPTION>
					<OPTION VALUE="设计开发部开发管理课" <c:if test="${project.projectDepartment =='设计开发部开发管理课'}">SELECTED</c:if>>设计开发部开发管理课</OPTION>
					<OPTION VALUE="开发统括室" <c:if test="${project.projectDepartment =='开发统括室'}">SELECTED</c:if>>开发统括室</OPTION>
					</select>
					</td>
				</tr>-->
			<tr class="info">
				<th class="info"><label style="color:red;">*</label><label id="projectInfo_string_department">项目所属部门:</label></th>
				<td>
					<div align="left">
						<input type="checkbox" name="all" id="all" <c:if test="${allSelect}">checked</c:if>>全选
					</div> 
						<c:forEach var="department" items="${departmentList}" varStatus="status">
						<c:if test="${(status.index+1) %3 ==1}"><div></c:if>
							<input type="checkbox" name="departmentList" value="${department.key}"
								<c:if test="${department.value.checked =='true'}">checked</c:if> />${department.value.name}
                        <c:if test="${(status.index +1) %3 ==0}"></div></c:if>
						</c:forEach>
				</td>
			</tr>
			<tr class="info">
				<th class="info"><label id="projectInfo_string_allBranch">项目所属课别:</label></th>
				<td>
					<div align="left">
						<input type="checkbox" name="allBranch" id="allBranch" />全选
					</div> <c:forEach var="branch" items="${branchList}" varStatus="status">
						<c:if test="${(status.index+1) %3 ==1}">
							<div>
						</c:if>
						<input type="checkbox" name="branchList" value="${branch.key}"
							<c:if test="${branch.value.checked =='true'}">checked</c:if> />${branch.value.name}
                            <c:if test="${(status.index +1) %3 ==0}">
							</div>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_function">项目所属功能:</label></th>
				<td><select name="function" id="function"
					style="WIDTH: 205px; height: 25px">
						<c:forEach items="${functionList}" var="function" varStatus="vs">
							<OPTION value="${function}"
								<c:if test="${project.function eq function}">SELECTED</c:if>>${function}</OPTION>
						</c:forEach>
				</select></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_model">机种:</label></th>
				<td><input type="text" name="model" id="model"
					class="input_txt" value="${project.model}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_transferNo">管理项番:</label></th>
				<td><input type="text" name="transferNo" id="transferNo"
					class="input_txt" value="${project.transferNo}" />
				<!-- <label style="color:red;">*</label> -->
				</td>
			</tr>
			<!-- 3D項番名、PJNo、仮PJNo、PJ名追加	HSCNZJ	ZZP -->
			<tr class="info">
				<th><label id="projectInfo_string_itemName">3D项番名:</label></th>
				<td><input type="text" name="itemName" id="itemName"
					class="input_txt" value="${project.itemName}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_PJNo">PJ No:</label></th>
				<td><input type="text" name="PJNo" id="PJNo" class="input_txt"
					value="${project.PJNo}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_tempPJNo">仮PJ No:</label></th>
				<td><input type="text" name="tempPJNo" id="tempPJNo"
					class="input_txt" value="${project.tempPJNo}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_PJName">PJ名:</label></th>
				<td><input type="text" name="PJName" id="PJName"
					class="input_txt" value="${project.PJName}" />
				<label style="color:red;">*</label>
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_memo">备注:</label></th>
				<td><input type="text" name="memo" id="memo" class="input_txt"
					value="${project.memo}" /></td>
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
		var E00054 = "";
		var E00055 = "";
		var E00056 = "";
		
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
			$('#project_carmake').html($.i18n.prop('project_string_state_select'));
			save = $.i18n.prop('projectInfo_string_save');
			E00024 = $.i18n.prop('E00024');
			E00025 = $.i18n.prop('E00025');
			E00026 = $.i18n.prop('E00026');
			E00054 = $.i18n.prop('E00054');
			E00055 = $.i18n.prop('E00055');
			E00056 = $.i18n.prop('E00056');
			
		}
		
		$(document).ready(function() {
			
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage,"../");	
			
		dg = frameElement.lhgDG;
		dg.addBtn('ok', save, function() {
			$("#carMaker").attr("value",$("#carMakerID").find("option:selected").text());
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
    
    $("#all").change(function() {
            if (!$("#all").attr("checked")) {
                $("input[name='departmentList']").attr("checked",false); 
            }else if($("#all").attr("checked")){
                $("input[name='departmentList']").attr("checked",true);
            }
    });
    
    $("input[name='departmentList']").each(function (){
    	$(this).change(function (){
    		if ($(this).attr("checked")==false){
    			 $("#all").attr("checked",false); 
    		}
    	});
    });
    
    $("#allBranch").change(function() {
        if (!$("#allBranch").attr("checked")) {
            $("input[name='branchList']").attr("checked",false); 
        }else if($("#allBranch").attr("checked")){
            $("input[name='branchList']").attr("checked",true);
        }
	});
    
	function checkInfo() {
		if ($("#projectName").val() == "") {
			alert(E00024);
			$("#projectName").focus();
			return false;
		}
		if ($("#category").val() == "") {
			alert(E00025);
			$("#category").focus();
			return false;
		}
		if ($("#PJName").val() == "") {
			alert(E00054);
			$("#PJName").focus();
			return false;
		}
/* 		if ($("#transferNo").val() == "") {
			alert(E00055);
			$("#transferNo").focus();
			return false;
		} */
        if (!$("#all").attr("checked")) {
        	var flag = "0";
        	$("input[name='departmentList']:checkbox").each(function(){
				if($(this).attr("checked")){
					flag = "1";
					return;
				}
			});
        	if (flag == "0") {
    			alert(E00056);
    			$("#all").focus();
    			return false;
        	}
        }
		return true;
	}

	function success() {
	    frameElement.lhgDG.curWin.successReload(); 
		dg.cancel();
	}

	function failed() {
		alert(E00026);
		$("#projectName").select();
		$("#projectName").focus();
	}
	</script>
</body>
</html>