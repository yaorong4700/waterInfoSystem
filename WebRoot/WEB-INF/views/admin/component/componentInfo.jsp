<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>コンポーネント管理界面</title>
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
	width: 120px;
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
			<input name="id" id="id" value="${component.id}" type = "hidden"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th><label id="componentInfo_string_isVisible">表示:</label></th>
					<td>
						<select name="isVisible" id="isVisible" style="WIDTH: 205px;height: 25px" >
						<OPTION id="component_string_visible_1" VALUE="1" <c:if test="${component.isVisible eq '1'}">SELECTED</c:if>>表示</OPTION>
						<OPTION id="component_string_visible_2" VALUE="0" <c:if test="${component.isVisible eq '0'}">SELECTED</c:if>>非表示</OPTION>
                		</select>
                		<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="componentInfo_string_category">開発段階:</label></th>
					<td><select name="categoryID" id="categoryID" style="WIDTH: 205px;height: 25px"  >
					<c:forEach items="${categoryList}" var="category" varStatus="vs">
					<OPTION value="${category.categoryID}" <c:if test="${component.categoryID eq category.categoryID}">SELECTED</c:if>>${category.categoryName}</OPTION>
					</c:forEach>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
							
				<tr class="info">
					<th><label id="componentInfo_string_componentName">コンポーネント名:</label></th>
					<td><input type="text" name="componentName" id="componentName" class="input_txt" maxlength="50" value="${component.componentName}"  />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="componentInfo_string_componentSort">分類:</label></th>
					<td>
					<select name="componentSortID" id="componentSortID" style="WIDTH: 205px;height: 25px">
					<option value="" ></option>
					<c:forEach items="${componentSortList}" var="csort" varStatus="vs">
					<OPTION value="${csort.componentSortID}" <c:if test="${component.componentSortID eq csort.componentSortID}">SELECTED</c:if>>${csort.componentSortName}</OPTION>
					</c:forEach>
					</select>
					</td>
				</tr>				
				<tr class="info">
					<th><label id="componentInfo_string_componentID">コンポーネントID:</label></th>
					<td><input type="text" name="componentID" id="componentID" class="input_txt" maxlength="10" value="${component.componentID}" /></td>
				</tr>
			</table>
		</form>
		<iframe name="result" id="result" src="about:blank" frameborder="0"	width="0" height="0"></iframe>
 
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="../js/js_user/com.js"></script>
	<script type="text/javascript" src="../js/cookie/cookie.js"></script>
	<script type="text/javascript">
	var dg;
	
	var language = getCookieValue("language");

	var save = "";
	var E0091 = "";
	var E0092 = "";
	var E0093 = "";

	$(document).ready(function() {
		
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage,"../");
		
		/* if ($("#componentID").val() != null && $("#componentID").val() != ""){
			$("#componentID").attr("readonly", "readonly");
			$("#componentID").css("color", "gray");
		} */
		
    	$("#categoryID").trigger("onchange");
			dg = frameElement.lhgDG;
		    dg.addBtn('ok', save, function() {
			$("#userForm").submit();
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

		if ($("#categoryID").val() == "") {
			alert(E0091);
			$("#categoryID").focus();
			return false;
		}
		if ($("#componentName").val() == "") {
			alert(E0092);
			$("#componentName").focus();
			return false;
		}
		
		if ($("#componentID").val() == "") {
			alert(E0093);
			$("#componentID").focus();
			return false;
		}
		return true;
	}

	function success() {
		frameElement.lhgDG.curWin.successReload(); 
		dg.cancel();
	}

	function failed() {
		alert(E00011);
		$("#email").select();
		$("#email").focus();
	}
	
	function setPageByLanguage(){

		$('#componentInfo_string_department').html($.i18n.prop('componentInfo_string_department'));
		$('#componentInfo_string_componentName').html($.i18n.prop('componentInfo_string_componentName'));
		$('#componentInfo_string_componentSort').html($.i18n.prop('componentInfo_string_componentSort'));
		$('#componentInfo_string_componentID').html($.i18n.prop('componentInfo_string_componentID'));
		$('#componentInfo_string_isVisible').html($.i18n.prop('componentInfo_string_isVisible'));
		
		save = $.i18n.prop('componentInfo_string_save');
		E0091 = $.i18n.prop('E0091');
		E0092 = $.i18n.prop('E0092');
		E0093 = $.i18n.prop('E0093');
		
	}
</script>
	</body>
</html>