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
	width: 110px;
	color: #4f4f4f;
	padding-right: 5px;
	font-size: 13px;
}

.info td {
	text-align: left;
	padding-left:10px;
}
</style>
</head>
<body>
	<form action="saveCT.do" name="projectForm" id="projectForm"
		target="result" method="post" onsubmit="return checkInfo();">
		<input name="projectID" id="projectID" value="${project.projectID }"
			type="hidden" />
		<input name="carMaker" id="carMaker" value="${project.carMaker}"
			type="hidden" />	
		<input name="enterpriseSeg" id="enterpriseSeg" value="${project.enterpriseSeg}"
			type="hidden" />
		<table border="0" cellpadding="0" cellspacing="0">
			<tr class="info">
				<th><label id="projectInfo_string_PJNo">PJ No.:</label></th>
				<td><input type="text" name=PJNo id="PJNo" class="input_txt"
					value="${project.PJNo}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_TempPJNoCT">仮PJ No.:</label></th>
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
				<th><label id="projectInfo_string_category">開発段階:</label></th>
				<td><select name="category" id="category"
					style="WIDTH: 205px; height: 25px">
						<c:forEach items="${categoryList}" var="category" varStatus="vs">
							<OPTION value="${category}"
								<c:if test="${project.category eq category}">SELECTED</c:if>>${category}</OPTION>
						</c:forEach>
				</select>
				<label style="color:red;">*</label>
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_3DNo">3D No.:</label></th>
				<td><input type="text" name="transferNo" id="transferNo"
					class="input_txt" value="${project.transferNo}" />
				<!-- <label style="color:red;">*</label> -->
				</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_itemNameCT">アイテム名称:</label></th>
				<td><input type="text" name="itemName" id="itemName"
					class="input_txt" value="${project.itemName}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_startupDate">開発開始日</label></th>
				<td><input type="text" name="startupDate" id="startupDate"
					class="input_txt" onClick="WdatePicker()"
					value="${project.startupDate}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_finishDate">開発終了日</label></th>
				<td><input type="text" name="finishDate" id="finishDate"
					class="input_txt" onClick="WdatePicker()"
					value="${project.finishDate}" /></td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_projectState">プロジェクト状態</label></th>
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
				<td>
				<select name="carMakerID" id="carMakerID" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="project_carmake" value="" SELECTED>--请选择--</option>
					<c:forEach items="${projectCarmakeCT}" var="projectCarmakeCommon" varStatus="vs">
						<OPTION value="${projectCarmakeCommon.carmakeID}" 
						<c:if test="${projectCarmakeCommon.carmakeID eq project.carMakerID}">SELECTED</c:if>>${projectCarmakeCommon.carmake}</OPTION>
					</c:forEach>
				</select>
					</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_enterpriseSeg">事業セグメント:</label></th>
				<td>
<%-- 				<input type="text" name="enterpriseSeg" id="enterpriseSeg"
					class="input_txt" value="${project.enterpriseSeg}" /> --%>
					<select name="enterpriseSegID" id="enterpriseSegID"
					style="WIDTH: 205px; height: 25px">
						<OPTION id="projectInfo_string_projectState_1" VALUE="1"
							<c:if test="${project.enterpriseSegID ==1}">SELECTED</c:if>>その他</OPTION>
						<OPTION id="projectInfo_string_projectState_2" VALUE="2"
							<c:if test="${project.enterpriseSegID ==2}">SELECTED</c:if>>OEM</OPTION>
						<OPTION id="projectInfo_string_projectState_3" VALUE="3"
							<c:if test="${project.enterpriseSegID ==3}">SELECTED</c:if>>DOP</OPTION>
						<OPTION id="projectInfo_string_projectState_4" VALUE="4"
							<c:if test="${project.enterpriseSegID ==4}">SELECTED</c:if>>CV</OPTION>
						<OPTION id="projectInfo_string_projectState_5" VALUE="5"
							<c:if test="${project.enterpriseSegID ==5}">SELECTED</c:if>>市販</OPTION>
						<OPTION id="projectInfo_string_projectState_6" VALUE="6"
							<c:if test="${project.enterpriseSegID ==6}">SELECTED</c:if>>先行</OPTION>
						<OPTION id="projectInfo_string_projectState_7" VALUE="7"
							<c:if test="${project.enterpriseSegID ==7}">SELECTED</c:if>>複合</OPTION>
					</select>
					</td>
			</tr>
			<tr class="info">
				<th><label id="projectInfo_string_function">共通呼称:</label></th>
				<td><select name="function" id="function"
					style="WIDTH: 205px; height: 25px">
						<c:forEach items="${functionList}" var="function" varStatus="vs">
							<OPTION value="${function}"
								<c:if test="${project.function eq function}">SELECTED</c:if>>${function}</OPTION>
						</c:forEach>
				</select></td>
			</tr>
			<tr class="info">
				<th class="info"><label style="color:red;">*</label><label id="projectInfo_string_department">项目所属部门:</label></th>
				<td>
					<div align="left">
						<input type="checkbox" name="all" id="all" <c:if test="${allSelect}">checked</c:if>>全选
					</div> <c:forEach var="department" items="${departmentList}"
						varStatus="status">
						<c:if test="${(status.index+1) %3 ==1}">
							<div>
						</c:if>
						<input type="checkbox" name="departmentList"
							value="${department.key}"
							<c:if test="${department.value.checked =='true'}">checked</c:if> />${department.value.name}
                            <c:if test="${(status.index +1) %3 ==0}">
							</div>
						</c:if>
					</c:forEach>
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
			$('#projectInfo_string_enterpriseSeg').html($.i18n.prop('projectInfo_string_enterpriseSeg')); 
			$('#projectInfo_string_itemNameCT').html($.i18n.prop('projectInfo_string_itemNameCT'));
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
			$("#enterpriseSeg").attr("value",$("#enterpriseSegID").find("option:selected").text());
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
    		if ($(this).attr("checked")==true){
   			 $("#all").attr("checked",true); 
   		}
    	});
    });
    
    
    
	function checkInfo() {
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
	    frameElement.lhgDG.curWin.successReloadCT(); 
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