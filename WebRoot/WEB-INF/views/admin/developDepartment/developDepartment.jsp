<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/flexigrid.pack.css" /> 
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/Flexigrid/flexigrid.pack.js"></script> 
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
	<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script> 
	<script type="text/javascript" src="js/js_user/com.js"></script> 
	<script type="text/javascript" src="js/cookie/cookie.js"></script> 
	
	<style type="text/css">
       body,table{
         font-size:13px;
       }
       label{
         color:black;
       }
    </style>
	<script type="text/javascript">
	var language = getCookieValue("language");
	var I0002 = "";
	var I0004 = "";
	var I0005 = "";
	var I0006 = "";
	var I0007 = "";
	var I0008 = "";
	var I0009 = "";
	var I0015 = "";
	var I0016 = "";
	
	var tblprojectName= "";
	var tblcategory= "";
	var tblprojectClientNo= "";
	var tbltransferNo= "";
	var tblprojectClientName= "";
	var tblprojectState= "";
	var tblprojectDepartment= "";
	var tblprojectBranch= "";
	var tblfunction= "";
	var tbladd="";
	var tbldelete="";
	var tbledit="";
	var tblinfo= "";
	var tblpagestat= "";
	var tbltitle= "";
	var rowcategory= "";
	var rowprojectName= "";
	var rowprojectClientName= "";
	var rowfunction= "";
	var rowdepartment= "";
	var rowbranch= "";
	var rowprojectClientNo= "";
	var rowtransferNo= "";
	var subtitle1= "";
	var subtitle2= "";
	var subtitle3= "";	
	var tblcancel = "";
	
	// 追加	HSCNZJ ZZP 
	
	function successReload(){
	    $('#gridTable').flexOptions().flexReload();
	}
	 
    function successReloadCT(){
	    $('#gridTableCT').flexOptions().flexReload();
	    $.ajax({
	    
	    				type: "post", 
				        url: "developDepartment/changeDepartment.do",
				        dataType: "json", 
				        success: function (result) {
				            var e=document.getElementById("departmentCT");
				            e.options.length=1; //清除下拉框数据
				            $.each(result.result,function(index,content) {
				                e.options.add(new Option(content.department, content.departmentID));
				            });
				        }
				        
				});
	}
    
    
	

	
	$(document).ready(function(){
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);	
	
		
		if("${staff.email}".indexOf(".co.jp") > 0){
			$('#search_condition_cxee').hide();
			$('#search_condition_ct').show();
			$('#gridCXEE').hide();
	    	$('#gridCT').show();
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#departmentCT").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#departmentCT").find("option[value="+departmentId+"]").attr("selected",true);
				changeListCT();
	    	}
		} else {
			$('#search_condition_cxee').show();
			$('#search_condition_ct').hide();
			$('#gridCXEE').show();
	    	$('#gridCT').hide();
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#departmentCXEE").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#departmentCXEE").find("option[value="+departmentId+"]").attr("selected",true);
				changeListCXEE();
	    	}
		}

		//U/L时的动作
		$("#upload").click(function(){
		    
		    upload();
		});
		
		//D/L的动作
		$("#download").click(function(){
			download();
		});
		$("#uploadCT").click(function(){
		    
		    uploadCT();
		});
		
		//D/L的动作
		$("#downloadCT").click(function(){
			downloadCT();
		});
	/**
	 * add event dialog
	 */
		$("#queryCXEE").click(function() {
			var QueryRoleFlag = $("#QueryRoleFlag").val();
			if(QueryRoleFlag != 1){
				return "";
			}else{
				var department = $("#departmentCXEE").val();
				var branch = $("#branchCXEE").val();
				
				var departmentCategory = $("#departmentCategoryCXEE").val();
				
		        var params = [{"name" : "department","value" : department}
		                     ,{"name" : "branch","value" : branch}
		                     ,{"name" : "departmentCategory","value" : departmentCategory}
		                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
		                     ];
		        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
			}
	    });
		$("#queryCT").click(function() {
			var QueryRoleFlag = $("#QueryRoleFlag").val();
			if(QueryRoleFlag != 1){
				return "";
			}else{
					var belongCode =$("#belongCodeCT").val();
					var department =$("#departmentCT").val();
					var branch = $("#branchCT").val();
			        var params = [{"name" : "belongCode","value" : belongCode}
			                     ,{"name" : "department","value" : department}
			                     ,{"name" : "branch","value" : branch}
			                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
			                     ];
			        $('#gridTableCT').flexOptions({params : params, newp : 1}).flexReload();
			}
		
    });
	
		//CHI追加button处理
		$("#addCXEE").click(function() {
			var dg = new $.dialog({
				title:tbladdDevelopDepartment,
				id:'department_new_cxee',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'developDepartment/add.do'
				});
			dg.ShowDialog();
		});
		//CT追加button处理
		$("#addCT").click(function() {
			var dg = new $.dialog({
				title:tbladdDevelopDepartment,
				id:'department_new_ct',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'developDepartment/addCT.do'
				});
			dg.ShowDialog();
		});
		//编集button处理
		$("#editCXEE").click(function() {
			 var selected_count = $('#gridTable .trSelected').length;
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    if (selected_count > 1) {
			    alert(I0009);
			    return;
		    }
		    
		    var departmentIDs = ""; 
			    $('#gridTable .trSelected td:nth-child(3) div').each(function(i) {
			    	//if (i) departmentIDs += ','; 
			    	departmentIDs += $(this).text();
			    })
			var branchIDs = ""; 
			    $('#gridTable .trSelected td:nth-child(4) div').each(function(i) {
			    	//if (i) branchIDs += ','; 
			    	branchIDs += $(this).text();
			    })    
			var condition = "departmentID="+departmentIDs+"&&"+"branchID="+encodeURI(branchIDs);
			var path = 'developDepartment/edit.do?'+condition;
			    var dg = new $.dialog({
			        title:tblupdateDepartment,
			        id:'department_edit_cxee',
			        width:500,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,
			        page:encodeURI(path)
			    });
		   
			dg.ShowDialog();
		});
		$("#editCT").click(function() {
			 var selected_count = $('#gridTableCT .trSelected').length;
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    if (selected_count > 1) {
			    alert(I0009);
			    return;
		    }
		    var belongCodes = ""; 
			    $('#gridTableCT .trSelected td:nth-child(1) div').each(function(i) {
			    	//if (i) belongCodes += ','; 
			    	belongCodes += $(this).text();
			    })
			var departmentIDs = ""; 
			    $('#gridTableCT .trSelected td:nth-child(2) div').each(function(i) {
			    	//if (i) departmentIDs += ','; 
			    	departmentIDs += $(this).text();
			    })
			var branchIDs = ""; 
			    $('#gridTableCT .trSelected td:nth-child(3) div').each(function(i) {
			    	//if (i) branchIDs += ','; 
			    	branchIDs += $(this).text();
			    })    
			    
			var condition = "belongCode="+encodeURI(belongCodes)+"&&"+"departmentID="+departmentIDs+"&&"+"branchID="+branchIDs;
			var path = 'developDepartment/editCT.do?'+condition;
			    var dg = new $.dialog({
			    	title:tblupdateDepartment,
			        id:'department_edit_ct',
			        width:500,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,
			        page:encodeURI(path)
			    });
		   
			dg.ShowDialog();
		});
		//删除button处理
		
		
		$("#deleteCXEE").click(function() {
			
			var selected_count = $('#gridTable .trSelected').length;
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    var branchIDs = '';  
		    var departmentIDs ='';
		     
		    $('#gridTable .trSelected td:nth-child(3) div').each(function(i) {
				if (i) departmentIDs += ','; 
				departmentIDs += $(this).text();
			});
		    $('#gridTable .trSelected td:nth-child(4) div').each(function(i) {
				if (i) branchIDs += ','; 
				branchIDs += $(this).text();
			});
		    
		    var condition ='';
		    condition="departmentID="+departmentIDs+"&&"+"branchID="+branchIDs;
		    if (confirm(tbldeleteinfo1 + tbldeleteinfo2)) {
				$.ajax({ 
				        type: "post", 
				        url: "developDepartment/delete.do?"+condition, 
				        dataType: "json", 
				        success: function (data) {
	                        if(data.result == "success"){
	                            alert("success");
	                            $('#gridTable').flexReload();
	                        }else{
	                            alert("fail");
	                        }
				        }, 
				        error: function (XMLHttpRequest, textStatus, errorThrown) { 
				            alert(I0002); 
				        } 
				});
		    }
		});
		$("#deleteCT").click(function() {
			
			var selected_count = $('#gridTableCT .trSelected').length;
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    var belongCodes = '';  
		    var departmentIDs = '';
		    var branchIDs = '';
		    $('#gridTableCT .trSelected td:nth-child(1) div').each(function(i) {
				if (i) belongCodes += ','; 
				belongCodes += $(this).text();
			});
		    $('#gridTableCT .trSelected td:nth-child(2) div').each(function(i) {
				if (i) departmentIDs += ','; 
				departmentIDs += $(this).text();
			});
		    $('#gridTableCT .trSelected td:nth-child(3) div').each(function(i) {
				if (i) branchIDs += ','; 
				branchIDs += $(this).text();
			});
		    var condition = "belongCode="+belongCodes+"&&"+"departmentID="+departmentIDs+"&&"+"branchID="+branchIDs;
		    if (confirm(tbldeleteinfo1 +  tbldeleteinfo2)) {
				$.ajax({ 
				        type: "post", 
				        
				        url: "developDepartment/deleteCT.do?"+condition, 
				        dataType: "json", 
				        success: function (data) {
	                        if(data.result == "success"){
	                            alert("success");
/* 	                            $('#gridTableCT').flexReload(); */
	                            successReloadCT();
	                        }else{
	                            alert("fail");
	                        }
				        }, 
				        error: function (XMLHttpRequest, textStatus, errorThrown) { 
				            alert(I0002); 
				        } 
				});
		    }
		});
	
		$("#gridTable").flexigrid({
			url : 'developDepartment/showAll.do',
			dataType : 'json',
			colModel : [ 
			{
				display : 'belongCode',
				name : 'belongCode',
				width : 50,
				sortable : false,
				align : 'center',
				hide:true,
			},
			{
				display : tbldepartment,
				name : 'department',
				width : 150,
				sortable : false,
				align : 'center',
			},
			{
				display : 'departmentID',
				name : 'departmentID',
				width : 150,
				sortable : false,
				align : 'center',
				hide:true,
			},
			{
				display : 'branchID',
				name : 'branchID',
				width : 10,
				sortable : false,
				align : 'center',
				hide:true,
			},
			{
				display : tblbranch,
				name : 'branch',
				width : 150,
				sortable : false,
				align : 'center',
			},
			{
				display : tblteam,
				name : 'team',
				width : 300,
				sortable : false,
				align : 'center',
			},
			{
				display : tbldepartmentCategory,
				name : 'departmentCategory',
				width : 100,
				sortable : false,
				align : 'center'
			},
			{
				display : tblbelong,
				name : 'belong',
				width : 150,
				sortable : false,
				align : 'center'
			},
			{
				display : tblmemo,
				name : 'branchMemo',
				width : 220,
				sortable : false,
				align : 'center'
			} ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "department",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			procmsg : I0005, 
			width : getBrowserWidth(),
			height : 'auto'
		});	
		
		$("#gridTableCT").flexigrid({
			url : 'developDepartment/showAllCT.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : tblbelongCodeCT,
				name : 'belongCode',
				width : 150,
				sortable : false,
				align : 'center',
			},
			{
				display : 'departmentID',
				name : 'departmentID',
				width : 150,
				sortable : false,
				align : 'center',
				hide:true,
			},
			{
				display : 'branchID',
				name : 'branchID',
				width : 1,
				sortable : false,
				align : 'center',
				hide:true,
			},
			{
				display : tbldepartmentCT,
				name : 'department',
				width : 150,
				sortable : false,
				align : 'center'
			}, {
				display : tblbranchCT,
				name : 'branch',
				width : 300,
				sortable : false,
				align : 'center'
			},{
				display : tblbelongCT,
				name : 'belong',
				width : 150,
				sortable : false,
				align : 'center'
			} , {
				display : tbldeploymentCT,
				name : 'branchDeployment',
				width : 100,
				sortable : false,
				align : 'center'
			}, {
				display : tblmemoCT,
				name : 'branchMemo',
				width : 220,
				sortable : false,
				align : 'center'
			}],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "department",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			procmsg : I0005, 
			width : getBrowserWidth(),
			height : 'auto'
		});
		
});
	//国际化
	function setPageByLanguage(){
		
		$('#queryCXEE').val($.i18n.prop('developDepartment_string_select'));
		$('#addCXEE').val($.i18n.prop('developDepartment_string_add'));
		$('#editCXEE').val($.i18n.prop('developDepartment_string_edit'));
		$('#deleteCXEE').val($.i18n.prop('developDepartment_string_delete'));
		
		$('#queryCT').val($.i18n.prop('developDepartment_string_select'));
		$('#addCT').val($.i18n.prop('developDepartment_string_add'));
		$('#editCT').val($.i18n.prop('developDepartment_string_edit'));
		$('#deleteCT').val($.i18n.prop('developDepartment_string_delete'));

		$('#project_string_tblprojectDepartment').html($.i18n.prop('developDepartment_string_tbldepartment'));
		$('#project_string_tblprojectBranch').html($.i18n.prop('developDepartment_string_tblbranch'));
		$('#project_string_tblprojectClientNo').html($.i18n.prop('developDepartment_string_tbldepartmentCategory'));
		
		$('#lab_departmentCT').html($.i18n.prop('developDepartment_string_tbldepartment'));
		$('#lab_teamCT').html($.i18n.prop('developDepartment_string_tblbranch'));
		
		$('#lab_belongCodeCT').html($.i18n.prop('developDepartment_string_belong'));
		
		
		$('#departmentCXEE_Branch_select').html($.i18n.prop('developDepartment_string_select_default'));
		$('#departmentCXEE_Department_select').html($.i18n.prop('developDepartment_string_select_default'));
		$('#departmentCXEE_ClientNo_select').html($.i18n.prop('developDepartment_string_select_default'));
		$('#department_selectCT').html($.i18n.prop('developDepartment_string_select_default'));
		$('#branchCT_selectCT').html($.i18n.prop('developDepartment_string_select_default'));
		
		tblpagestat=$.i18n.prop('developDepartment_string_tblpagestat');
		tbldeleteinfo1=$.i18n.prop('developDepartment_string_tbldeleteinfo1');
		tbldeleteinfo2=$.i18n.prop('developDepartment_string_tbldeleteinfo2');
		//CXEE
		tbldepartment = $.i18n.prop('developDepartment_string_tbldepartmentCT');
		tblbranch = $.i18n.prop('developDepartment_string_tblbranchCT');
		tblteam = $.i18n.prop('developDepartment_string_tblteamCT');
		tbldepartmentCategory = $.i18n.prop('developDepartment_string_tbldepartmentCategoryCT');
		tblbelong = $.i18n.prop('developDepartment_string_tblbelong');
		tblmemo = $.i18n.prop('developDepartment_string_tblmemo');
		//CT
		tblbelongCodeCT = $.i18n.prop('developDepartment_string_tblbelongCodeCT');
		tbldepartmentCT = $.i18n.prop('developDepartment_string_tbldepartmentCT');
		tblbranchCT = $.i18n.prop('developDepartment_string_tblbranchCT');
		tblbelongCT = $.i18n.prop('developDepartment_string_tblbelongCT');
		tbldeploymentCT = $.i18n.prop('developDepartment_string_tbldeploymentCT');
		tblmemoCT = $.i18n.prop('developDepartment_string_tblmemoCT');
		tbltitle=$.i18n.prop('developDepartment_string_tbltitle');
		I0004=$.i18n.prop('I0004');
		I0005=$.i18n.prop('I0005');;
		I0006=$.i18n.prop('I0006');
		tbldownloadtitle=$.i18n.prop('developDepartment_string_tblinfo');
		I0007=$.i18n.prop('I0007');
		tbluploadtitle=$.i18n.prop('developDepartment_string_tbluploadtitle');
		I0008=$.i18n.prop('I0008');
		I0002=$.i18n.prop('I0002');
		tbladdDevelopDepartment = $.i18n.prop('developDepartment_string_tbladdDevelopDepartment');
		I0009 = $.i18n.prop('I0009');
		tblupdateDepartment=$.i18n.prop('developDepartment_string_tblupdateDepartment');
		tblcancel=$.i18n.prop('developDepartment_string_tblcancel');
	}
	
     	 

     	
		
		function download() {
	      		if (confirm(I0006)) {
	   		       var path = "developDepartment/downloadDepartment.do"
	   		       var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'department_download',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        cancelBtnTxt:tblcancel,
			        resize:false,	
			        loadingText:I0007, 
			        page:path
			    });
		        dg.ShowDialog(); 
	   		 }
		}
		function downloadCT() {
	      		if (confirm(I0006)) {
	   		       var path = "developDepartment/downloadDepartmentCT.do"
	   		       var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'department_downloadCT',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        cancelBtnTxt:tblcancel,
			        resize:false,	
			        loadingText:I0007,
			        page:path
			    });
		        dg.ShowDialog(); 
	   		 }
		}
		
		function upload(){
			var dg = new $.dialog({
				title:tbluploadtitle,
				id:'department_upload',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				cancelBtnTxt:tblcancel,
				resize:false,
				page:'developDepartment/upload.do'
				});
			dg.ShowDialog();
			
		}
		function uploadCT(){
			var dg = new $.dialog({
				title:tbluploadtitle,
				id:'department_uploadCT',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				cancelBtnTxt:tblcancel,
				resize:false,
				page:'developDepartment/uploadCT.do'
				});
			dg.ShowDialog();
		}
		
		//部门变更时候，改变课别的选项
		function changeListCXEE(){
		    var departmentID = $("#departmentCXEE").val();
		    $.ajax({
		        type: "post",
		        url: "developDepartment/getBranchCXEE.do",
		        dataType: "json",
		        data:{"departmentID":departmentID},
		        success: function (result) {
		            var e=document.getElementById("branchCXEE");
		            e.options.length=1; //清除下拉框数据
		            $.each(result.result,function(index,content) {
		                e.options.add(new Option(content.branch, content.branchID));
		            });
		        }
		    });
		}
		function changeListCT(){
		    var departmentID = $("#departmentCT").val();
		    $.ajax({
		        type: "post",
		        url: "developDepartment/getBranchCT.do",
		        dataType: "json",
		        data:{"departmentID":departmentID},
		        success: function (result) {
		            var e=document.getElementById("branchCT");
		            e.options.length=1; //清除下拉框数据
		            $.each(result.result,function(index,content) {
		                e.options.add(new Option(content.branch, content.branchID));
		            });
		        }
		    });
		}
	</script>
</head>
<body style="width:1100px;" >
<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
<input type="hidden" id ="AlterRoleFlag" value = "${AlterRoleFlag}">
<input type="hidden" id ="UploadRoleFlag" value = "${UploadRoleFlag}">
<input type="hidden" id ="DownloadRoleFlag" value = "${DownloadRoleFlag}">
<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
<input type="hidden" id ="DepartmentFlag" value = "${DepartmentFlag}">
<p style="height:15px;"></p>
 	<table id="search_condition_ct" >
 		<tr>
            <td style="width:auto;">
                <label id="lab_belongCodeCT">在籍所属コード:</label>
			    <input type="text" id="belongCodeCT" style="margin-bottom:5px; width:90px; padding: .2em;" maxlength="45"/>
           </td>
            <td style="width:210px;">
                <label id="lab_departmentCT">部:</label>
                <select name="departmentCT" id="departmentCT" 
                        style="margin-bottom:5px; width:170px; padding: .2em;" onChange="changeListCT()">
                    <option id="department_selectCT" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${departmentList}" var="departmentList" varStatus="vs">
                    <option value="${departmentList.departmentID}" >${departmentList.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:200px;">
                <label id="lab_teamCT">グループ:</label>
			   	<select name="branchCT" id="branchCT" 
                        style="margin-bottom:5px; width:120px; padding: .2em;">
                    <option id="branchCT_selectCT" value="" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:auto;align :center;" >
            <c:if test="${QueryRoleFlag == '1'}">
	            <c:if test="${SpecialRole1Flag == '1'}">
			    <input id="queryCT" type="button" value="查询" style="margin-left:200px; margin-bottom:5px; padding: .2em;">
	            </c:if>
            </c:if>
			<c:if test="${AlterRoleFlag == 1 }">
	            <c:if test="${SpecialRole1Flag == '1'}">
<!-- 	            <td style="width:auto;"> -->
				    <input id="addCT" type="button" value="追加" style="margin-left:5px; margin-bottom:5px;padding: .2em;">
				<!-- </td> -->
	           <!--  <td style="width:auto;"> -->
				    <input id="editCT" type="button" value="编集" style="margin-left:5px; margin-bottom:5px;padding: .2em;">
				<!-- </td>
			 	<td style="width:auto;"> -->
				   <input id="deleteCT" type="button" value="删除" style="margin-left:5px;margin-bottom:5px; padding: .2em;">
				<!-- </td> -->
				</c:if>
			</c:if>
			</td>
			<c:if test="${UploadRoleFlag == 1 }">
	            <c:if test="${SpecialRole1Flag == '1'}">
				<td style="width:55px; margin-bottom:5px; padding: .2em;margin-left:10px;"><input id="uploadCT" type="button"
					value="U/L">
				</td>
				</c:if>
			</c:if>
				<td style="width:55px; margin-bottom:5px;  padding: .2em;margin-left:10px;"><input id="downloadCT" type="button"
					value="D/L">
				</td>
 		</tr>
 	</table>
 	<table id="search_condition_cxee">
        <tr>
			<td style="width:220px;">
                <label id="project_string_tblprojectDepartment">部门:</label>
                <select name="departmentCXEE" id="departmentCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:170px; padding: .2em;" onChange="changeListCXEE()">
                    <option id="departmentCXEE_Department_select" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${departmentList}" var="departmentList" varStatus="vs">
                    <option value="${departmentList.departmentID}" >${departmentList.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:200px;">
                <label id="project_string_tblprojectBranch">课别:</label>
                <select name="branchCXEE" id="branchCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:120px; padding: .2em;">
                    <option  id="departmentCXEE_Branch_select" value="" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:170px;">
                <label id="project_string_tblprojectClientNo">分类:</label>
			     <select name="departmentCategoryCXEE" id="departmentCategoryCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:120px; padding: .2em;" >
                    <option value="" id="departmentCXEE_ClientNo_select" SELECTED>--请选择--</option>
                    <c:forEach items="${departmentCategoryList}" var="dclist" varStatus="vs">
                    <option value="${dclist.departmentCategory}" >${dclist.departmentCategory}</OPTION>
                    </c:forEach>
                </select>
			</td>
			 <td style="width:auto;">
            <c:if test="${QueryRoleFlag == '1'}">
	            <c:if test="${SpecialRole2Flag == '1'}">
                <label id="project_string_tblprojectClientNo"></label>
			    <input id="queryCXEE" type="button" value="查询" style="width:55px; margin-left:200px; margin-bottom:5px; padding: .2em;">
	            </c:if>
            </c:if>
			</td>
			<c:if test="${AlterRoleFlag == 1 }">
            <c:if test="${SpecialRole2Flag == '1'}">
			<td style="width:auto;">
			    <input id="addCXEE" type="button" value="追加" style="width:55px; margin-left:5px; padding: .2em;">
			</td>
			 <td style="width:auto;">
			    <input id="editCXEE" type="button" value="编集" style="width:55px; margin-left:5px; padding: .2em;">
			</td>
			 <td style="width:auto;">
			   <input id="deleteCXEE" type="button" value="删除" style="width:55px; margin-left:5px; padding: .2em;">
			</td>
			</c:if>
			</c:if>
			<c:if test="${UploadRoleFlag == 1 }">
	            <c:if test="${SpecialRole2Flag == '1'}">
				<td style="width:55px; margin-bottom:5px; padding: .2em;margin-left:10px;"><input id="upload" type="button"
					value="U/L">
				</td>
				</c:if>
			</c:if>
				<td style="width:55px; margin-bottom:5px;  padding: .2em;margin-left:10px;"><input id="download" type="button"
					value="D/L">
				</td>
		</tr>	
 </table>
 <p style="height:10px;"></p>
 <div id="gridCXEE">
<table id="gridTable" style="width:1100px;"></table>
</div>
<div id="gridCT">
<table id="gridTableCT" style="width:1100px;"></table>
</div>
</body>
</html>