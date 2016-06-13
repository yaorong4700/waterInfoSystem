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
	width: 130px;
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
					<th><label id="staffInfo_string_jobNo">工号:</label></th>
					<td><input type="text" name="jobNo" id="jobNo" class="input_txt" value="${staff.jobNo}" />
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
					<th><label id="staffInfo_string_position">职位:</label></th>
					
					<td><select name="positionID" id="positionID" style="WIDTH: 205px;height: 25px">
					<c:forEach items="${selectInfo.positionList}" var="position" varStatus="vs">
					<OPTION value="${position.positionID}" <c:if test="${staff.position eq position.position}">SELECTED</c:if>>${position.position}</OPTION>
					</c:forEach>
					</select>
					<label style="color:red;">*</label>
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
					<!-- <label style="color:red;">*</label> -->
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_branch">课别:</label></th>
					<td><select name="branchID" id="branchID" style="WIDTH: 205px;height: 25px">
					<%-- <OPTION id="staffInfo_string_branch_1" value="0" <c:if test="${staff.branch eq '0'}">SELECTED</c:if>>无</OPTION> --%> 
					<%-- <c:forEach items="${selectInfo.branchList}" var="branch" varStatus="vs">
						<OPTION value="${branchID}" <c:if test="${staff.branchID eq branchID}">SELECTED</c:if>>${branch}</OPTION>
					</c:forEach> 
					 --%>
					</select>
					<!-- <label style="color:red;">*</label> -->
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_enrolementCode">在籍所属コード:</label></th>
					<td><input type="text" name="enrolementCode" id="enrolementCode" class="input_txt" value="${staff.enrolementCode}"  />
					<!-- <label style="color:red;">*</label> -->
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
					<OPTION id="staffInfo_string_state_4" VALUE="4" <c:if test="${staff.state ==4}">SELECTED</c:if>>異動</OPTION>
					</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_role">角色权限:</label></th>
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
						<select name="sortID" id="sortID" style="WIDTH: 205px;height: 25px">
							<c:forEach items="${selectInfo.sortList}" var="staffSort" varStatus="vs">
								<OPTION value="${staffSort.sortID}" <c:if test="${staff.sort eq staffSort.sort }">selected</c:if>>${staffSort.sort}</OPTION>
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
					<th><label id="staffInfo_string_jobCategory">職種:</label></th>
					<td>
					<%-- <input type="text" name="jobCategory" id="jobCategory" class="input_txt" value="${staff.jobCategory}"  /> --%>
						<select name="jobCategory" id="jobCategory"   style="WIDTH: 205px;height: 25px">
						<c:forEach items="${selectInfo.jobCategoryList}" var="job" varStatus="vs">
							<option value="${job.jobCategory}" <c:if test="${staff.jobCategory eq job.jobCategory}">selected</c:if>>${job.jobCategory}</option>
						</c:forEach>
						</select>
					<label style="color:red;">*</label>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_designQualified">設計有資格総合判定:</label></th>
					<td>
					<%-- <input type="text" name="designQualified" id="designQualified" class="input_txt" value="${staff.designQualified}"  /> --%>
						<select name="designQualified" id="designQualified"   style="WIDTH: 205px;height: 25px">
						<option value=""></option>
						<option value="Ⅰ" <c:if test="${staff.designQualified eq 'Ⅰ'}">selected</c:if>>Ⅰ</option>
						<option value="Ⅱ" <c:if test="${staff.designQualified eq 'Ⅱ'}">selected</c:if>>Ⅱ</option>
						<option value="Ⅲ" <c:if test="${staff.designQualified eq 'Ⅲ'}">selected</c:if>>Ⅲ</option>
						</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_PMLevel">PMレベル:</label></th>
					<td>
					<%-- <input type="text" name="pmLevel" id="pmLevel" class="input_txt" value="${staff.pmLevel}"  /> --%>
						<select name="pmLevel" id="pmLevel"   style="WIDTH: 205px;height: 25px">
						<option value=""></option>
						<option value="PMⅠ" <c:if test="${staff.pmLevel eq 'PMⅠ'}">selected</c:if>>PMⅠ</option>
						<option value="PMⅡ" <c:if test="${staff.pmLevel eq 'PMⅡ'}">selected</c:if>>PMⅡ</option>
						<option value="PMⅢ" <c:if test="${staff.pmLevel eq 'PMⅢ'}">selected</c:if>>PMⅢ</option>
						<option value="PMⅣ" <c:if test="${staff.pmLevel eq 'PMⅣ'}">selected</c:if>>PMⅣ</option>
						<option value="PMⅤ" <c:if test="${staff.pmLevel eq 'PMⅤ'}">selected</c:if>>PMⅤ</option>
						<option value="対象外" <c:if test="${staff.pmLevel eq '対象外'}">selected</c:if>>対象外</option>
						</select>
					</td>
				</tr>
				<tr class="info">
					<th><label id="staffInfo_string_comment">特記事項:</label></th>
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
	var save = "";
	var E0002 = "";
	var E0008 = "";
	var E0009 = "";
	var E00010 = "";
	var E00011 = "";
	var E00050 = "";
	var E00051 = "";
	var E00052 = "";
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
		
	});
      function departmentChange(id) {
            var departmentID = $("#departmentID").val();
            $.ajax({ 
	                type: "post", 
	                url: "listBranch.do", 
	                dataType: "json", 
	                data:{"departmentID":departmentID}, 
	                success: function (result) {
	                		var e=document.getElementById("branchID");
	        				/* e.options.length=1;	//清楚下拉框数据 */
	        				e.options.length=0;//清楚下拉框数据
	        				//e.options.add(new Option("",""));	
	                		$.each(result.branch,function(index,content) {
		                			e.options.add(new Option(content.branch,content.branchID));	
	                            });
	                        $("#branchID").val("${staff.branchID}");
	                }, 
	            });
        }
      
      function checkEnrolementCode() {
      	  var departmentID = $("#departmentID").val();
          var enrolementCode = $("#enrolementCode").val().trim();
          var branchID = $("#branchID").val();
          var res=0;
          var flag=false;
          
          if(departmentID=="0" && enrolementCode!=""){
        	  alert(E00062);
 			 $("#enrolementCode").focus();
 			 return false;
          }
          if(departmentID=="0" && enrolementCode==""){
 			 $("#enrolementCode").focus();
 			 return true;
          }
          $.ajax({ 
        	  		async:false,
                    type: "post", 
                    url: "checkEnrolement.do", 
                    dataType: "json", 
                    data:{"departmentID":departmentID,"enrolementCode":enrolementCode,"branchID":branchID}, 
                    success: function (result) {
                    	res= result.Count;
                    },
                    complete: function (){
                    	flag =true;
                    }
                });
          if(flag){
    		 if(Number(res)==0){
    			 alert(E00062);
    			 $("#enrolementCode").focus();
    			 return false;
    		 }else{
    			 return true;
    		 }
          };
      }
      
      
	function checkInfo() {

		if ($("#name").val() == "") {
			alert(E0008);
			$("#name").focus();
			return false;
		}
		if ($("#jobNo").val() == "") {
			alert(E0009);
			$("#jobNo").focus();
			return false;
		}
		if ($("#password").val() == "") {
			alert(E0002);
			$("#password").focus();
			return false;
		}
/* 		if ($("#branchID").val() == "0") {
			alert(E00050);
			$("#branchID").focus();
			return false;
		} */
		/* if ($("#enrolementCode").val() == "") {
			alert(E00051);
			$("#enrolementCode").focus();
			return false;
		} */
		if ($("#email").val() == "") {
			alert(E00010);
			$("#email").focus();
			return false;
		}
		var mail=$("#email").val();
		var mails=mail.split("@");
		var filename=mails[mails.length-1];
		if(filename!="clarion.co.jp"){
			alert(E00059);
			$("#email").focus();
			return false;
		};
		if ($("#URKeyCode").val() == "") {
			alert(E00053);
			$("#URKeyCode").focus();
			return false;
		}
		if ($("#jobCategory").val() == "") {
			alert(E00052);
			$("#jobCategory").focus();
			return false;
		}
		var ss=checkEnrolementCode();
		if(ss==false){
			return false;
		}
		return true;
		
	}

	function success() {
		frameElement.lhgDG.curWin.successReloadCT(); 
		dg.cancel();
	}

	function failed() {
		alert(E00011);
		$("#email").select();
		$("#email").focus();
	}
	
	function failedJobNo() {
		alert(E00063);
		$("#jobNo").focus();
	}
	
	function setPageByLanguage(){
		//document.title = $.i18n.prop('staffInfo_string_title');
		$('#staffInfo_string_name').html($.i18n.prop('staffInfo_string_name'));
		$('#staffInfo_string_password').html($.i18n.prop('staffInfo_string_password'));
		$('#staffInfo_string_jobNo').html($.i18n.prop('staffInfo_string_jobNo'));
		$('#staffInfo_string_position').html($.i18n.prop('staffInfo_string_position'));	
		$('#staffInfo_string_department').html($.i18n.prop('staffInfo_string_department'));
		$('#staffInfo_string_branch').html($.i18n.prop('staffInfo_string_branch'));
		$('#staffInfo_string_branch_1').html($.i18n.prop('staffInfo_string_branch_1'));;
		$('#staffInfo_string_email').html($.i18n.prop('staffInfo_string_email'));
		$('#staffInfo_string_state').html($.i18n.prop('staffInfo_string_state'));
		$('#staffInfo_string_state_1').html($.i18n.prop('staffInfo_string_state_1'));
		$('#staffInfo_string_state_2').html($.i18n.prop('staffInfo_string_state_2'));
		$('#staffInfo_string_state_3').html($.i18n.prop('staffInfo_string_state_3'));
		$('#staffInfo_string_state_4').html($.i18n.prop('staffInfo_string_state_4'));
		$('#staffInfo_string_role').html($.i18n.prop('staffInfo_string_role'));
		$('#staffInfo_string_sort').html($.i18n.prop('staffInfo_string_sort'));
		$('#staffInfo_string_companyName').html($.i18n.prop('staffInfo_string_companyName'));
		$('#staffInfo_string_comment').html($.i18n.prop('staffInfo_string_comment'));
		$('#staffInfo_string_enrolementCode').html($.i18n.prop('staffInfo_string_enrolementCode'));
		$('#staffInfo_string_sortID').html($.i18n.prop('staffInfo_string_sortID'));
		$('#staffInfo_string_jobCategory').html($.i18n.prop('staffInfo_string_jobCategory'));
		$('#staffInfo_string_designQualified').html($.i18n.prop('staffInfo_string_designQualified'));
		$('#staffInfo_string_PMLevel').html($.i18n.prop('staffInfo_string_PMLevel'));
		
		save = $.i18n.prop('staffInfo_string_save');
		E0002 = $.i18n.prop('E0002');
		E0008 = $.i18n.prop('E0008');
		E0009 = $.i18n.prop('E0009');
		E00010 = $.i18n.prop('E00010');
		E00011 = $.i18n.prop('E00011');
		E00050 = $.i18n.prop('E00050');
		E00051 = $.i18n.prop('E00051');
		E00052 = $.i18n.prop('E00052');
		E00053 = $.i18n.prop('E00053');
		E00059  = $.i18n.prop('E00059');
		E00062  = $.i18n.prop('E00062');
		E00063 = $.i18n.prop("E00063");
	}
</script>
	</body>
</html>