<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工管理界面</title>
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
		<form action="save.do" name="userForm" id="userForm" target="result"
			method="post" onsubmit="return checkInfo();">
			<input name="staffID" id="staffID" value="${staff.staffID }" type = "hidden"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr class="info">
					<th><label id="staffInfo_string_name">员工姓名:</label></th>
					<td><input type="text" name="name" id="name" class="input_txt"	value="${staff.name }" />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_password">密码:</label></th>
					<td><input type="password" name="password" id="password" class="input_txt" value="${staff.password}" />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_jobNo">工号:</label></th>
					<td><input type="text" name="jobNo" id="jobNo" class="input_txt" value="${staff.jobNo}" />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_gender">性别:</label></th>
					<td>
					<select name="gender" id="gender"   style="WIDTH: 205px;height: 25px">
				<!--  <OPTION id="staffInfo_string_gender_1" VALUE="女" <c:if test="${staff.gender eq '女'}">SELECTED</c:if> >女</OPTION>
					<OPTION id="staffInfo_string_gender_2" VALUE="男" <c:if test="${staff.gender eq '男'}">SELECTED</c:if>>男</OPTION> 
					<OPTION id="staffInfo_string_gender_1" VALUE="" >女</OPTION>	
					<OPTION id="staffInfo_string_gender_2" VALUE="" >男</OPTION>-->
					</select>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_dateGraduation">毕业时间:</label></th>
					<td><input type="text" name="dateGraduation" id="dateGraduation" class="input_txt"	onClick="WdatePicker()" value="${staff.dateGraduation }"  />
					</td></tr>

				<tr class="info">
					<th><label id="staffInfo_string_dateIntoCompany">进公司时间:</label></th>
					<td><input type="text" name="dateIntoCompany" id="dateIntoCompany" class="input_txt"  onClick="WdatePicker()" value="${staff.dateIntoCompany}" /></td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_position">职位:</label></th>
					
					<td><select name="positionID" id="positionID" style="WIDTH: 205px;height: 25px">
					<c:forEach items="${selectInfo.positionList}" var="position" varStatus="vs">
					<OPTION value="${position.positionID}" <c:if test="${staff.position eq position.position}">SELECTED</c:if>>${position.position}</OPTION>
					</c:forEach>
					</select>
					</td>
				</tr>
				<!-- add by weikin 开发管理部依赖，将保留字段memo设置为能力别的存储字段 -->
				<tr class="info">
					<th><label id="staffInfo_string_memo">能力别:</label></th>
					<td>
					<select name="memo" id="memo"   style="WIDTH: 205px;height: 25px">
					</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_department">部门:</label></th>
					<td>
						<select name="departmentID" id="departmentID" style="WIDTH: 205px;height: 25px"  onchange="departmentChange(this.id)">
						<option value="0"></option>
						<c:forEach items="${selectInfo.departmentList}" var="department" varStatus="vs">
						<OPTION value="${department.departmentID}" <c:if test="${staff.departmentID eq department.departmentID}">SELECTED</c:if>>${department.department}</OPTION>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_branch">课别:</label></th>
					<td>
					<select name="branchID" id="branchID" style="WIDTH: 205px;height: 25px">
					</select>
					<!-- <label style="color:red;">*</label> -->
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_team">组别:</label></th>
					<td><select name="team" id="team" style="WIDTH: 205px;height: 25px">
					<OPTION id="staffInfo_string_team_1" value="" <c:if test="${staff.team eq ''}">SELECTED</c:if>>无</OPTION>
					<c:forEach items="${selectInfo.teamList}" var="team" varStatus="vs">
					<OPTION value="${team}" <c:if test="${staff.team eq team}">SELECTED</c:if>>${team}</OPTION>
					</c:forEach>
					</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_superior">上司:</label></th>
					<td><select name="superior" id="superior" style="WIDTH: 205px;height: 25px">
					<OPTION id="staffInfo_string_superior_1" value="" <c:if test="${staff.superior eq ''}">SELECTED</c:if>>无</OPTION>
					<c:forEach items="${selectInfo.leadList}" var="superior" varStatus="vs">
					<OPTION value="${superior}" <c:if test="${staff.superior eq superior}">SELECTED</c:if>>${superior}</OPTION>
					</c:forEach>
					</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_email">Email:</label></th>
					<td><input type="text" name="email" id="email" class="input_txt" value="${staff.email}"  />
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_state">在职状态:</label></th>
					<td>
					<select name="state" id="state"   style="WIDTH: 205px;height: 25px">
					<OPTION id="staffInfo_string_state_1" VALUE="1" <c:if test="${staff.state ==1}">SELECTED</c:if>>在职</OPTION>
					<OPTION id="staffInfo_string_state_2" VALUE="2" <c:if test="${staff.state ==2}">SELECTED</c:if>>休假</OPTION>
					<OPTION id="staffInfo_string_state_3" VALUE="3" <c:if test="${staff.state ==3}">SELECTED</c:if>>离职</OPTION>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_dateQuitCompany">离职日:</label></th>
					<td><input type="text" name="dateQuitCompany" id="dateQuitCompany" class="input_txt"  onClick="WdatePicker()" value="${staff.dateQuitCompany}" /></td>
				</tr>
				<%-- <tr class="info">
					<th><label id="staffInfo_string_role">角色权限:</label></th>
					<td>
					<select name="role" id="role"   style="WIDTH: 205px;height: 25px">
					<OPTION id="staffInfo_string_role_1" VALUE="1" <c:if test="${staff.role ==1}">SELECTED</c:if> >普通权限</OPTION>
					<OPTION id="staffInfo_string_role_2" VALUE="2" <c:if test="${staff.role ==2}">SELECTED</c:if>>超级管理员</OPTION>
					<OPTION id="staffInfo_string_role_3" VALUE="3" <c:if test="${staff.role ==3}">SELECTED</c:if>>职制人员</OPTION>
					<OPTION id="staffInfo_string_role_4" VALUE="4" <c:if test="${staff.role ==4}">SELECTED</c:if>>工数担当</OPTION>
					</select>
					</td>
				</tr> --%>
				<tr class="info">
					<th><label id="staffInfo_string_role" >角色权限:</label></th>
					<td>
					<select name="URKeyCode" id="URKeyCode" style="WIDTH: 205px;height: 25px">
						<option value="" selected></option>
						<c:forEach items="${selectInfo.roleList}" var="role" varStatus="vs">
							<OPTION value="${role.roleID}" <c:if test="${role.roleID eq staff.URKeyCode}">SELECTED</c:if>>${role.roleName}</OPTION>
						</c:forEach>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_sort">员工类别:</label></th>
					<td>
					<select name="sortID" id="sortID"   style="WIDTH: 205px;height: 25px">
					<!--  <OPTION VALUE="公司员工" <c:if test="${staff.sort eq '公司员工'}">SELECTED</c:if> >公司员工</OPTION>
					<OPTION VALUE="外驻" <c:if test="${staff.sort eq '外驻'}">SELECTED</c:if>>外驻</OPTION>
					<OPTION VALUE="派遣" <c:if test="${staff.sort eq '派遣'}">SELECTED</c:if>>派遣</OPTION>-->
						<c:forEach items="${selectInfo.sortList}" var="staffSort" varStatus="vs">
							<OPTION value="${staffSort.sortID}" <c:if test="${staff.sortID eq staffSort.sortID}">SELECTED</c:if> >${staffSort.sort}</OPTION>
						</c:forEach>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_companyName">公司名称:</label></th>
					<td>
					<%-- <input type="text" name="companyName" id="companyName" class="input_txt" value="${staff.companyName}"  /> --%>
						<select name="companyName" id="companyName"   style="WIDTH: 205px;height: 25px">
						<option value=""></option>
						<option value="クラリオン株式会社" <c:if test="${staff.companyName eq 'クラリオン株式会社'}">selected</c:if>>クラリオン株式会社</option>
						<option value="厦門歌楽電子企業有限公司" <c:if test="${staff.companyName eq '厦門歌楽電子企業有限公司'}">selected</c:if>>厦門歌楽電子企業有限公司</option>
						</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_comment">备注:</label></th>
					<td><input type="text" name="comment" id="comment" class="input_txt" value="${staff.comment}"  /></td>
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
	var gender = "${staff.gender}";
	var memo = "${staff.memo}";
	var sort = "${staff.sort}";
	var save = "";
	var E0002 = "";
	var E0008 = "";
	var E0009 = "";
	var E00010 = "";
	var E00011 = "";
	var E00050 = "";
	var E00053 = "";
	$(document).ready(function() {
		
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage,"../");	
		
    $("#departmentID").trigger("onchange");
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
		var e=document.getElementById("branchID");
		e.options.length=1;
		/* if ($("#dateGraduation").val() == ""){
				$("#state").val(null);
		} */
	});
      function departmentChange(id) {
            //var department = $("#departmentID").find("option:selected").text();  
            var departmentID = $("#departmentID").val();
            $.ajax({ 
	                type: "post", 
	                url: "listBranch.do", 
	                dataType: "json", 
	                data:{"departmentID":departmentID}, 
	                success: function (result) {
	                		var e=document.getElementById("branchID");
	                		var e1=document.getElementById("superior");
	        				/* e.options.length=1;	//清楚下拉框数据	
	        				e1.options.length=1; */
	        				e.options.length=0;
	        				e1.options.length=0;
	                		$.each(result.branch,function(index,content) {
	                			 e.options.add(new Option(content.branch,content.branchID));
	                            });
	                		$.each(result.lead,function(index,content) {
	                			 e1.options.add(new Option(content, content));
	                            });
	                        //$("#branchID").val("${staff.branchID}");
                            //$("#superior").val("${staff.superior}");
	                }, 
	            });
        }
	function checkInfo() {
		/* alert("anwser me quickly!");
		alert($("#jobNo").val()); */
		if ($("#name").val() == "") {
			alert(E0008);
			$("#name").focus();
			return false;
		}
		if ($("#password").val() == "") {
			alert(E0002);
			$("#password").focus();
			return false;
		}
		if ($("#jobNo").val() == "") {
			alert(E0009);
			$("#jobNo").focus();
			return false;
		}
		/* if ($("#branchID").val() == "0") {
			alert(E00050);
			$("#branchID").focus();
			return false;
		} */
		
		/* if ($("#dateGraduation").val() == "") {
			alert("请填写毕业时间");
			$("#dateGraduation").focus();
			return false;
		} */
		
		//2015/04/29 dateIntoCompany变更为可以为空
		/* 
		if ($("#dateIntoCompany").val() == "") {
			alert("请填写入司时间");
			$("#dateIntoCompany").focus();
			return false;
		}
		*/
		if ($("#email").val() == "") {
			alert(E00010);
			$("#email").focus();
			return false;
		}
		var mail=$("#email").val();
		var mails=mail.split("@");
		var filename=mails[mails.length-1];
		if(filename!="clarion.com.cn"&&filename!="clariondcoe.com.cn"
			&&filename!="clarionchina.com"&&filename!="clarionchi.com.hk"){
			alert(E00060);
			$("#email").focus();
			return false;
		};
		
		
		
		if ($("#URKeyCode").val() == "") {
			alert(E00053);
			$("#URKeyCode").focus();
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
		//document.title = $.i18n.prop('staffInfo_string_title');
		$('#staffInfo_string_name').html($.i18n.prop('staffInfo_string_name'));
		$('#staffInfo_string_password').html($.i18n.prop('staffInfo_string_password'));
		$('#staffInfo_string_jobNo').html($.i18n.prop('staffInfo_string_jobNo'));
		$('#staffInfo_string_gender').html($.i18n.prop('staffInfo_string_gender'));
		var gender1 = $.i18n.prop('staffInfo_string_gender_1');
		var gender2 = $.i18n.prop('staffInfo_string_gender_2');
		if (gender==gender1){
			$('#gender').append('<OPTION VALUE="' + gender1+'" SELECTED>'+gender1+'</OPTION>'+'<OPTION  VALUE="' + gender2+'">'+gender2+'</OPTION>');
		} else if (gender==gender2){
			$('#gender').append('<OPTION VALUE="' + gender1+'">'+gender1+'</OPTION>'+'<OPTION  VALUE="' + gender2+'" SELECTED>'+gender2+'</OPTION>');
		} else {
			$('#gender').append('<OPTION VALUE="' + gender1+'" SELECTED>'+gender1+'</OPTION>'+'<OPTION  VALUE="' + gender2+'">'+gender2+'</OPTION>');
		}
		$('#staffInfo_string_dateGraduation').html($.i18n.prop('staffInfo_string_dateGraduation'));
		$('#staffInfo_string_dateIntoCompany').html($.i18n.prop('staffInfo_string_dateIntoCompany'));
		$('#staffInfo_string_position').html($.i18n.prop('staffInfo_string_position'));
		$('#staffInfo_string_memo').html($.i18n.prop('staffInfo_string_memo'));
		var memo1 = $.i18n.prop('staffInfo_string_memo_1');
		var memo2 = $.i18n.prop('staffInfo_string_memo_2');
		var memo3 = $.i18n.prop('staffInfo_string_memo_3');
		var memo4 = $.i18n.prop('staffInfo_string_memo_4');
		
		if (memo == memo1) {
			$('#memo').append('<OPTION VALUE="' + memo1+'" SELECTED>'+memo1+'</OPTION>'+'<OPTION VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION VALUE="' + memo3+'">'+memo3+'</OPTION>'+'<OPTION VALUE="' + memo4+'">'+memo4+'</OPTION>');
		} else if (memo == memo2) {
			$('#memo').append('<OPTION VALUE="' + memo1+'">'+memo1+'</OPTION>'+'<OPTION VALUE="' + memo2+'" SELECTED>'+memo2+'</OPTION>'+'<OPTION VALUE="' + memo3+'">'+memo3+'</OPTION>'+'<OPTION VALUE="' + memo4+'">'+memo4+'</OPTION>');
		} else if (memo == memo3) {
			$('#memo').append('<OPTION VALUE="' + memo1+'">'+memo1+'</OPTION>'+'<OPTION VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION VALUE="' + memo3+'" SELECTED>'+memo3+'</OPTION>'+'<OPTION VALUE="' + memo4+'">'+memo4+'</OPTION>');			
		} else if (memo == memo4) {
			$('#memo').append('<OPTION VALUE="' + memo1+'">'+memo1+'</OPTION>'+'<OPTION VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION VALUE="' + memo3+'">'+memo3+'</OPTION>'+'<OPTION VALUE="' + memo4+'" SELECTED>'+memo4+'</OPTION>');			
		} else {
			$('#memo').append('<OPTION VALUE="' + memo1+'">'+memo1+'</OPTION>'+'<OPTION VALUE="' + memo2+'">'+memo2+'</OPTION>'+'<OPTION VALUE="' + memo3+'">'+memo3+'</OPTION>'+'<OPTION VALUE="' + memo4+'">'+memo4+'</OPTION>');
		}
		
		$('#staffInfo_string_department').html($.i18n.prop('staffInfo_string_department'));
		$('#staffInfo_string_branch').html($.i18n.prop('staffInfo_string_branch'));
		$('#staffInfo_string_branch_1').html($.i18n.prop('staffInfo_string_branch_1'));
		$('#staffInfo_string_team').html($.i18n.prop('staffInfo_string_team'));
		$('#staffInfo_string_team_1').html($.i18n.prop('staffInfo_string_team_1'));
		$('#staffInfo_string_superior').html($.i18n.prop('staffInfo_string_superior'));
		$('#staffInfo_string_superior_1').html($.i18n.prop('staffInfo_string_superior_1'));
		$('#staffInfo_string_email').html($.i18n.prop('staffInfo_string_email'));
		$('#staffInfo_string_state').html($.i18n.prop('staffInfo_string_state'));
		$('#staffInfo_string_state_1').html($.i18n.prop('staffInfo_string_state_1'));
		$('#staffInfo_string_state_2').html($.i18n.prop('staffInfo_string_state_2'));
		$('#staffInfo_string_state_3').html($.i18n.prop('staffInfo_string_state_3'));		
		$('#staffInfo_string_role').html($.i18n.prop('staffInfo_string_role'));
		$('#staffInfo_string_role_1').html($.i18n.prop('staffInfo_string_role_1'));
		$('#staffInfo_string_role_2').html($.i18n.prop('staffInfo_string_role_2'));
		$('#staffInfo_string_role_3').html($.i18n.prop('staffInfo_string_role_3'));		
		$('#staffInfo_string_role_4').html($.i18n.prop('staffInfo_string_role_4'));
		$('#staffInfo_string_sort').html($.i18n.prop('staffInfo_string_sort'));
		var sort1 = $.i18n.prop('staffInfo_string_sort_1');
		var sort2 = $.i18n.prop('staffInfo_string_sort_2');
		var sort3 = $.i18n.prop('staffInfo_string_sort_3');
		if (sort==sort1){
			$('#sort').append('<OPTION VALUE="' + sort1+'" SELECTED>'+sort1+'</OPTION>'+'<OPTION  VALUE="' + sort2+'">'+sort2+'</OPTION>'+'<OPTION  VALUE="' + sort3+'">'+sort3+'</OPTION>');
		} else if (sort==sort2){
			$('#sort').append('<OPTION VALUE="' + sort1+'">'+sort1+'</OPTION>'+'<OPTION  VALUE="' + sort2+'" SELECTED>'+sort2+'</OPTION>'+'<OPTION  VALUE="' + sort3+'">'+sort3+'</OPTION>');
		} else if (sort==sort3){
			$('#sort').append('<OPTION VALUE="' + sort1+'">'+sort1+'</OPTION>'+'<OPTION  VALUE="' + sort2+'">'+sort2+'</OPTION>'+'<OPTION  VALUE="' + sort3+'" SELECTED>'+sort3+'</OPTION>');
		} else {
			$('#sort').append('<OPTION VALUE="' + sort1+'" SELECTED>'+sort1+'</OPTION>'+'<OPTION  VALUE="' + sort2+'">'+sort2+'</OPTION>'+'<OPTION  VALUE="' + sort3+'">'+sort3+'</OPTION>');
		}
		$('#staffInfo_string_dateQuitCompany').html($.i18n.prop('staffInfo_string_dateQuitCompany'));
		$('#staffInfo_string_companyName').html($.i18n.prop('staffInfo_string_companyName'));
		$('#staffInfo_string_comment').html($.i18n.prop('staffInfo_string_comment'));
		
		save = $.i18n.prop('staffInfo_string_save');
		E0002 = $.i18n.prop('E0002');
		E0008 = $.i18n.prop('E0008');
		E0009 = $.i18n.prop('E0009');
		E00010 = $.i18n.prop('E00010');
		E00011 = $.i18n.prop('E00011');
		E00050 = $.i18n.prop('E00050');
		E00053 = $.i18n.prop('E00053');
		E00060  = $.i18n.prop('E00060');
		
	}
</script>
	</body>
</html>