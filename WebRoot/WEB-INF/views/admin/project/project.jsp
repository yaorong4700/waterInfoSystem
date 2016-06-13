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
	var tbldateQuitCompany="";
	var tblcomment="";
	var E00045="";
	var E00046="";
	var E00047="";
	var E00048="";

	function successReload(){
	    $('#gridTable').flexOptions().flexReload();
	}
	
    function successReloadCT(){
	    $('#gridTableCT').flexOptions().flexReload();
	}
    
    function successReloadCommon(){
	    $('#gridTableCommon').flexOptions().flexReload();
	}
	
	//国际化
/*	function setPageByLanguage(){
		
		I0002=$.i18n.prop('I0002');
		I0004=$.i18n.prop('I0004');
		I0005=$.i18n.prop('I0005');
		I0006=$.i18n.prop('I0006');
		I0007=$.i18n.prop('I0007');
		I0008=$.i18n.prop('I0008');
		I0009=$.i18n.prop('I0009');
		I0015=$.i18n.prop('I0015');
		I0016=$.i18n.prop('I0016');
		
		tblprojectName= $.i18n.prop('project_string_tblprojectName');
		tblcategory= $.i18n.prop('project_string_tblcategory');
		tblprojectClientNo= $.i18n.prop('project_string_tblprojectClientNo');
		tbltransferNo= $.i18n.prop('project_string_tbltransferNo');
		tblprojectClientName= $.i18n.prop('project_string_tblprojectClientName');
		tblprojectState= $.i18n.prop('project_string_tblprojectState');
		tblprojectDepartment= $.i18n.prop('project_string_tblprojectDepartment');
		tblprojectBranch= $.i18n.prop('project_string_tblprojectBranch');
		tblfunction= $.i18n.prop('project_string_tblfunction');
		tbladd= $.i18n.prop('project_string_tbladd');
		tbldelete= $.i18n.prop('project_string_tbldelete');
		tbledit= $.i18n.prop('project_string_tbledit');
		tblinfo= $.i18n.prop('project_string_tblinfo');
		tblpagestat= $.i18n.prop('project_string_tblpagestat');
		tbltitle= $.i18n.prop('project_string_tbltitle');
		
		rowcategory= $.i18n.prop('project_string_rowcategory');
		rowprojectName= $.i18n.prop('project_string_rowprojectName');
		rowprojectClientName= $.i18n.prop('project_string_rowprojectClientName');
		rowfunction= $.i18n.prop('project_string_rowfunction');
		rowdepartment= $.i18n.prop('project_string_rowdepartment');
		rowbranch= $.i18n.prop('project_string_rowbranch');
		rowprojectClientNo= $.i18n.prop('project_string_rowprojectClientNo');
		rowtransferNo= $.i18n.prop('project_string_rowtransferNo');
		subtitle1= $.i18n.prop('project_string_subtitle1');
		subtitle2= $.i18n.prop('project_string_subtitle2');
		subtitle3= $.i18n.prop('project_string_subtitle3');
		
		tblcancel= $.i18n.prop('project_string_tblcancel');
		
	}*/
	
	$(document).ready(function(){
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);	
	
		if("${staff.email}".indexOf(".co.jp") > 0){
			$("#belong").val("1");
			$('#search_condition_cxee').hide();
			$('#search_condition_common').hide();
			$('#search_condition_ct').show();
			$('#gridCXEE').hide();
			$('#gridCommon').hide();
	    	$('#gridCT').show();
	    	$("#add").attr({"disabled":"disabled"});
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#belong").attr("disabled", "disabled");
	    	}
		} else {
			$("#belong").val("2");
			$('#search_condition_cxee').show();
			$('#search_condition_ct').hide();
			$('#search_condition_common').hide();
			$('#gridCXEE').show();
			$('#gridCommon').hide();
	    	$('#gridCT').hide();
	    	$("#addCT").attr({"disabled":"disabled"});
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#belong").attr("disabled", "disabled");
				$("#projectDepartmentCXEE").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#projectDepartmentCXEE").find("option[value="+departmentId+"]").attr("selected",true);
				changeListCXEE();
	    	}
		}
		
		//归属变更时的动作
		$('#belong').change( function () {
		    var valueSelected = this.value;
		    if (valueSelected == "1"){
		    	$('#search_condition_cxee').hide();
		    	$('#search_condition_common').hide();
		    	$('#search_condition_ct').show();
		    	$('#gridCXEE').hide();
		    	$('#gridCommon').hide();
		    	$('#gridCT').show();
		    	if ("${staff.email}".indexOf(".co.jp") > 0){
		    		$("#add").attr({"disabled":"disabled"});
		    		$("#addCT").removeAttr("disabled");
		    		$("#edit").removeAttr("disabled");
		    		$("#delete").removeAttr("disabled");
		    		$("#upload").removeAttr("disabled");
		    	} else {
		    		$("#addCT").attr({"disabled":"disabled"});
		    		$("#edit").attr({"disabled":"disabled"});
		    		$("#delete").attr({"disabled":"disabled"});
		    		$("#add").attr({"disabled":"disabled"});
		    		$("#upload").removeAttr("disabled");
		    	}
		    } else if (valueSelected == "2"){
		    	$('#search_condition_cxee').show();
		    	$('#search_condition_ct').hide();
		    	$('#search_condition_common').hide();
		    	$('#gridCXEE').show();
		    	$('#gridCT').hide();
		    	$('#gridCommon').hide();
		    	if ("${staff.email}".indexOf(".co.jp") > 0){
		    		$("#add").attr({"disabled":"disabled"});
		    		$("#edit").attr({"disabled":"disabled"});
		    		$("#delete").attr({"disabled":"disabled"});
		    		$("#addCT").attr({"disabled":"disabled"});
		    		$("#upload").removeAttr("disabled");
		    	} else {
		    		$("#addCT").attr({"disabled":"disabled"});
		    		$("#add").removeAttr("disabled");
		    		$("#edit").removeAttr("disabled");
		    		$("#delete").removeAttr("disabled");
		    		$("#upload").removeAttr("disabled");
		    	}
		    }else{
		    	$('#search_condition_cxee').hide();
		    	$('#search_condition_ct').hide();
		    	$('#search_condition_common').show();
		    	$('#gridCXEE').hide();
		    	$('#gridCT').hide();
		    	$('#gridCommon').show();
		    	$("#add").attr({"disabled":"disabled"});
	    		$("#edit").attr({"disabled":"disabled"});
	    		$("#delete").attr({"disabled":"disabled"});
	    		$("#addCT").attr({"disabled":"disabled"});
	    		$("#upload").attr({"disabled":"disabled"});
		    }
		});
		//U/L变更时的动作
		$('#upload').change( function () {
		    var valueSelected = this.value;
		    if (valueSelected == "1") {
				var belong               = $("#belong").val();
	            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	            if ( SpecialRole1Flag != 1) {
		        	alert(E00047);
	  	   			return false;
	            }
		    	uploadProjectCT();
		    } else if (valueSelected == "2") {
				var belong               = $("#belong").val();
				var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	            if ( SpecialRole2Flag != 1 ) {
		        	alert(E00048);
	  	   			return false;
	            }
		    	uploadProjectCXEE();
		    }
		});
		//D/L变更的动作
		$('#download').change( function () {
			var valueSelected = this.value;
		    if (valueSelected == "1") {
		    	downloadProjectCT();
		    } else if (valueSelected == "2") {
		    	downloadProjectCXEE();
		    }else if (valueSelected == "3") {
		    	downloadProjectCommon();
		    }
		});	
		
	/**
	 * add event dialog
	 */
		$("#query").click(function() {
			var QueryRoleFlag = $("#QueryRoleFlag").val();
			if(QueryRoleFlag == 1){
				var belong               = $("#belong").val();
	            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	            if (belong == 1 && SpecialRole1Flag != 1) {
		        	alert(E00045);
	  	   			return false;
	            }
	            if (belong == 2 && SpecialRole2Flag != 1) {
		        	alert(E00046);
	  	   			return false;
	            }
				if ($("#belong").val() == "1"){
					var projectPJNameCT   = $("#projectPJNameCT").val();
			        var projectPJNoCT     = $("#projectPJNoCT").val();
			        var projectTempPJNoCT = $("#projectTempPJNoCT").val();
			        var projectCategory = $("#projectCategory").val();
			        var project3DNoCT = $("#project3DNoCT").val();
			        var projectCarmake = $("#projectCarmake").val();
			        var projectStateCT = $("#projectStateCT").val();
			        var profunction = $("#funtionCT").val();
			        var DepartmentFlag = $("#DepartmentFlag").val();
					var departmentID = "${staff.departmentID}";
			        var params = [{"name" : "PJName","value" : projectPJNameCT}
			                     ,{"name" : "PJNo","value" : projectPJNoCT}
			                     ,{"name" : "tempPJNo","value" : projectTempPJNoCT}
			                     ,{"name" : "category","value" : projectCategory}
			                     ,{"name" : "transferNo","value" : project3DNoCT}
			                     ,{"name" : "carMaker","value" : projectCarmake}
			                     ,{"name" : "projectState","value" : projectStateCT}
			                     ,{"name" : "function","value" : profunction}
			                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
			                     ,{"name" : "DepartmentFlag","value" : DepartmentFlag}
			                     ,{"name" : "departmentID","value" : departmentID}
			                     ];
			        $('#gridTableCT').flexOptions({params : params, newp : 1}).flexReload();
				} else if($("#belong").val() == "2"){
					var projectCategory = $("#projectCategoryCXEE").val();
					var projectNameCXEE = $("#projectNameCXEE").val();
					var projectClientName = $("#projectClientName").val();
					var functionList = $("#functionList").val();
			        var projectDepartmentCXEE = $("#projectDepartmentCXEE").val();
			        var projectBranchCXEE = $("#projectBranchCXEE").val();
			        var projectClientNo = $("#projectClientNo").val();
			        var transferNo = $("#transferNo").val();
			        
			        var params = [{"name" : "category","value" : projectCategory}
			                     ,{"name" : "projectName","value" : projectNameCXEE}
			                     ,{"name" : "projectClientName","value" : projectClientName}
			                     ,{"name" : "function","value" : functionList}
			                     ,{"name" : "department","value" : projectDepartmentCXEE}
			                     ,{"name" : "branch","value" : projectBranchCXEE}
			                     ,{"name" : "projectClientNo","value" : projectClientNo}
			                     ,{"name" : "transferNo","value" : transferNo}
			                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
			                     ];
			        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
				}else{
					var projectPJNameCommon   = $("#projectPJNameCommon").val();
			        var projectPJNoCommon     = $("#projectPJNoCommon").val();
			        var projectTempPJNoCommon = $("#projectTempPJNoCommon").val();
			        var projectCategoryCommon = $("#projectCategoryCommon").val();
			        var project3DNoCommon = $("#project3DNoCommon").val();
			        var projectCarmakeCommon = $("#projectCarmakeCommon").val();
			        var projectStateCommon = $("#projectStateCommon").val();
			        var functionCommon = $("#functionCommon").val();
			        var projectNameCommon = $("#projectNameCommon").val();
			        var projectClientNameCommon = $("#projectClientNameCommon").val();
			        var projectClientNoCommon = $("#projectClientNoCommon").val();
			        var params = [{"name" : "PJName","value" : projectPJNameCommon}
			                     ,{"name" : "PJNo","value" : projectPJNoCommon}
			                     ,{"name" : "tempPJNo","value" : projectTempPJNoCommon}
			                     ,{"name" : "category","value" : projectCategoryCommon}
			                     ,{"name" : "transferNo","value" : project3DNoCommon}
			                     ,{"name" : "carMaker","value" : projectCarmakeCommon}
			                     ,{"name" : "projectState","value" : projectStateCommon}
			                     ,{"name" : "function","value" : functionCommon}
			                     ,{"name" : "projectName","value" : projectNameCommon}
			                     ,{"name" : "projectClientName","value" : projectClientNameCommon}
			                     ,{"name" : "projectClientNo","value" : projectClientNoCommon}
			                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
			                     ,{"name" : "SpecialRole1Flag","value" : SpecialRole1Flag}
			                     ,{"name" : "SpecialRole2Flag","value" : SpecialRole2Flag}
			                     ];
			        $('#gridTableCommon').flexOptions({params : params, newp : 1}).flexReload();
				}
			}else{
				return "";
			}
			
	    });
	
		//CT追加button处理
		$("#addCT").click(function() {
			var belong               = $("#belong").val();
            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
            if (belong == 1 && SpecialRole1Flag != 1) {
	        	alert(E00047);
  	   			return false;
            }
			var dg = new $.dialog({
				title:tbladduser,
				id:'project_new_CT',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'project/addCT.do'
				});
			dg.ShowDialog();
		});
		
		//CHI追加button处理
		$("#add").click(function() {
			var belong               = $("#belong").val();
            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
            if (belong == 2 && SpecialRole2Flag != 1) {
	        	alert(E00048);
  	   			return false;
            }
			var dg = new $.dialog({
				title:tbladduser,
				id:'project_new',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'project/add.do'
				});
			dg.ShowDialog();
		});
		//编集button处理
		$("#edit").click(function() {
			var belong               = $("#belong").val();
            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
            if (belong == 1 && SpecialRole1Flag != 1) {
	        	alert(E00047);
  	   			return false;
            }
            if (belong == 2 && SpecialRole2Flag != 1) {
	        	alert(E00048);
  	   			return false;
            }
			var selected_count = 0;
			if("${staff.email}".indexOf(".co.jp") > 0){
				selected_count = $('#gridTableCT .trSelected').length;
			} else {
				selected_count = $('#gridTable .trSelected').length;
			}

		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    if (selected_count > 1) {
			    alert(I0009);
			    return;
		    }
		    var ids = "";
		    if("${staff.email}".indexOf(".co.jp") > 0){
		    	$('#gridTableCT .trSelected td:nth-child(1) div').each(function(i) {
					  if (i) ids += ',';
					  ids += $(this).text();
				})
				var dg = new $.dialog({
			        title:tblupduser,
			        id:'project_edit',
			        width:500,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,
			        page:'project/editCT.do?id='+ids
			    });
		    } else {
			    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
				  if (i) ids += ',';
				  ids += $(this).text();
			    })
			    var dg = new $.dialog({
			        title:tblupduser,
			        id:'project_edit',
			        width:500,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,
			        page:'project/edit.do?id='+ids
			    });
		    }
			dg.ShowDialog();
		});
		//删除button处理
		$("#delete").click(function() {
			var belong               = $("#belong").val();
            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
            if (belong == 1 && SpecialRole1Flag != 1) {
	        	alert(E00047);
  	   			return false;
            }
            if (belong == 2 && SpecialRole2Flag != 1) {
	        	alert(E00048);
  	   			return false;
            }
			var selected_count = 0;
			if("${staff.email}".indexOf(".co.jp") > 0){
				selected_count = $('#gridTableCT .trSelected').length;
			} else {
				selected_count = $('#gridTable .trSelected').length;
			}
			
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    var name = '';
		    var ids = '';
		    if ("${staff.email}".indexOf(".co.jp") > 0){
		    	$('#gridTableCT .trSelected td:nth-child(2) div').each(function(i) {
			    	if (i)  name += ',';
					name += $(this).text();
				});
			    
			    $('#gridTableCT .trSelected td:nth-child(1) div').each(function(i) {
					if (i) ids += ','; 
					ids += $(this).text();
				});
			    
			    if (confirm(tbldeleteinfo1 + name + tbldeleteinfo2)) {
					$.ajax({ 
					        type: "post", 
					        url: "project/deleteCT.do?ids="+ids, 
					        dataType: "json", 
					        success: function (data) {
		                        if(data.result == "success"){
		                            alert("success");
		                            if ("${staff.email}".indexOf(".co.jp") > 0){
		                            	$('#gridTableCT').flexReload();
		                            } else {
		                            	$('#gridTable').flexReload();
		                            }
		                        }else{
		                            alert("fail");
		                        }
					        }, 
					        error: function (XMLHttpRequest, textStatus, errorThrown) { 
					            alert(I0002); 
					        } 
					});
			    }
			    
		    } else {
			    $('#gridTable .trSelected td:nth-child(2) div').each(function(i) {
			    	if (i)  name += ',';
					name += $(this).text();
				});
			    
			    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
					if (i) ids += ','; 
					ids += $(this).text();
				});
			    if (confirm(tbldeleteinfo1 + name + tbldeleteinfo2)) {
					$.ajax({ 
					        type: "post", 
					        url: "project/delete.do?ids="+ids, 
					        dataType: "json", 
					        success: function (data) {
		                        if(data.result == "success"){
		                            alert("success");
		                            if ("${staff.email}".indexOf(".co.jp") > 0){
		                            	$('#gridTableCT').flexReload();
		                            } else {
		                            	$('#gridTable').flexReload();
		                            }
		                        }else{
		                            alert("fail");
		                        }
					        }, 
					        error: function (XMLHttpRequest, textStatus, errorThrown) { 
					            alert(I0002); 
					        } 
					});
			    }
		    }
		    
		});
	
		$("#gridTable").flexigrid({
			url : 'project/showAll.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'projectID',
				name : 'projectID',
				width : 1,
				sortable : false,
				align : 'center',
				hide : true
			}, 
			{
				display : tblname,
				name : 'projectName',
				id:'projectName',
				width : 150,
				sortable : false,
				align : 'center'
			},
			{
				display : tblcategory,
				name : 'category',
				width : 50,
				sortable : true,
				align : 'center'
			},
			{
				display : tblprojectClientNo,
				name : 'projectClientNo',
				width : 150,
				sortable : true,
				align : 'center'
			},{
				display : 'department',
				name : 'department',
				width : 1,
				sortable : true,
				align : 'center',
				hide: true
			},{
				display : tbltransferNo,
				name : 'transferNo',
				width : 100,
				sortable : true,
				align : 'center'
			} ,{
				display : 'branch',
				name : 'branch',
				width : 1,
				sortable : true,
				align : 'center',
				hide: true
			}, {
				display : tblcarMaker,
				name : 'carMaker',
				width : 100,
				sortable : true,
				align : 'center'
			}, {
				display : tblprojectClientName,
				name : 'projectClientName',
				width : 100,
				sortable : true,
				align : 'center'
			}, {
				display : tblprojectState,
				name : 'projectState',
				width : 100,
				sortable : false,
				align : 'center'
			} , {
				display : tblprojectDepartment,
				name : 'projectDepartment',
				width : 80,
				sortable : false,
				align : 'center'
			}, {
				display : tblprojectBranch,
				name : 'projectBranch',
				width : 80,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectfunction,
				name : 'function',
				width : 100,
				sortable : false,
				align : 'center'
			} ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "name",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			useRp : true,
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			//onRowDblclick : rowDblclick,  
			procmsg : I0005, 
			width :  getBrowserWidth(),
			height : 'auto'
		});	
		
		$("#gridTableCT").flexigrid({
			url : 'project/showAllCT.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'projectID',
				name : 'projectID',
				width : 50,
				sortable : false,
				align : 'center',
				hide : true
			},
			{
				display : tblPJNo,
				name : 'PJNo',
				width : 100,
				sortable : false,
				align : 'center'
			}, {
				display : tbltempPJNo,
				name : 'tempPJNo',
				width : 150,
				sortable : true,
				align : 'center'
			},{
				display : tblPJname,
				name : 'PJName',
				width : 300,
				sortable : false,
				align : 'center'
			} , {
				display : tblcategoryCT,
				name : 'category',
				width : 100,
				sortable : false,
				align : 'center'
			}, {
				display : tbl3DNo,
				name : 'transferNo',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : tblcarmakeCT,
				name : 'carMaker',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectStateCT,
				name : 'projectState',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectfunctionCT,
				name : 'function',
				width : 100,
				sortable : false,
				align : 'center'
			}  ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "name",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			useRp : true,
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			//onRowDblclick : rowDblclickCT,  
			procmsg : I0005, 
			width : getBrowserWidth(),
			height : 'auto'
		});
		
		$("#gridTableCommon").flexigrid({
			url : 'project/showAllCommon.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'projectID',
				name : 'projectID',
				width : 1,
				sortable : false,
				align : 'center',
				hide : true
			},
			{
				display : tblPJNo,
				name : 'PJNo',
				width : 80,
				sortable : false,
				align : 'center'
			}, {
				display : tbltempPJNo,
				name : 'tempPJNo',
				width : 100,
				sortable : true,
				align : 'center'
			},{
				display : tblPJname,
				name : 'PJName',
				width : 100,
				sortable : false,
				align : 'center'
			} , {
				display : tblcategoryCT,
				name : 'category',
				width : 100,
				sortable : false,
				align : 'center'
			}, {
				display : tbl3DNo,
				name : 'transferNo',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : tblcarmakeCT,
				name : 'carMaker',
				width : 100,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectClientNo,
				name : 'projectClientNo',
				width : 80,
				sortable : true,
				align : 'center'
			},{
				display : tblname,
				name : 'projectName',
				id:'projectName',
				width : 140,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectClientName,
				name : 'projectClientName',
				width : 100,
				sortable : true,
				align : 'center'
			},{
				display : tblprojectStateCT,
				name : 'projectState',
				width : 50,
				sortable : false,
				align : 'center'
			},{
				display : tblprojectfunctionCT,
				name : 'function',
				width : 50,
				sortable : false,
				align : 'center'
			}  ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "name",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			useRp : true,
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			//onRowDblclick : rowDblclickCT,  
			procmsg : I0005, 
			width : getBrowserWidth(),
			height : 'auto'
		});
		
		
});
	//国际化
	function setPageByLanguage(){
		
		$('#staff_string_department').html($.i18n.prop('staff_string_department'));
		$('#staff_string_department_select').html($.i18n.prop('staff_string_department_select'));
		$('#staff_string_branch_select').html($.i18n.prop('staff_string_branch_select'));
		$('#staff_string_positionID_select').html($.i18n.prop('staff_string_positionID_select'));
		$('#staff_string_state_select').html($.i18n.prop('staff_string_state_select'));			
		$('#staff_string_branch').html($.i18n.prop('staff_string_branch'));
		$('#staff_string_positionID').html($.i18n.prop('staff_string_positionID'));
		$('#staff_string_state').html($.i18n.prop('staff_string_state'));
		$('#staff_string_select_1').html($.i18n.prop('staff_string_select_1'));
		$('#staff_string_select_2').html($.i18n.prop('staff_string_select_2'));
		$('#staff_string_select_3').html($.i18n.prop('staff_string_select_3'));
		$('#staff_string_name').html($.i18n.prop('staff_string_name'));
		$('#staff_string_superior').html($.i18n.prop('staff_string_superior'));
		$('#staff_string_dateQuitCompany').html($.i18n.prop('staff_string_dateQuitCompany'));
		$('#staff_string_belong').html($.i18n.prop('staff_string_belong'));
		$('#staff_string_belong_CT').html($.i18n.prop('staff_string_belong_CT'));
		$('#staff_string_belong_CXEE').html($.i18n.prop('staff_string_belong_CXEE'));
		$('#staff_string_upload').html($.i18n.prop('staff_string_upload'));
		$('#staff_string_uploadCT').html($.i18n.prop('staff_string_uploadCT'));
		$('#staff_string_uploadCXEE').html($.i18n.prop('staff_string_uploadCXEE'));
		
		
		//HSCN CY ADD
		$('#project_category_select').html($.i18n.prop('project_category_select'));
		$('#project_category_select_ct').html($.i18n.prop('project_category_select'));
		$('#project_department_selectCXEE').html($.i18n.prop('project_department_selectCXEE'));
		$('#project_string_branch_select').html($.i18n.prop('project_string_branch_select'));
		$('#project_string_tblcategory_ct').html($.i18n.prop('project_string_tblcategory'));
		$('#project_string_tblcategory').html($.i18n.prop('project_string_tblcategory'));
		$('#project_string_tblprojectNameCXEE').html($.i18n.prop('project_string_tblprojectName'));
		$('#project_string_tblprojectName').html($.i18n.prop('project_string_tblprojectName'));
		$('#project_string_tblprojectClientNameCXEE').html($.i18n.prop('project_string_tblprojectClientName'));
		$('#project_string_tblprojectClientName').html($.i18n.prop('project_string_tblprojectClientName'));
		$('#project_string_tblfunction').html($.i18n.prop('project_string_tblfunction'));
		$('#project_string_tblprojectDepartment').html($.i18n.prop('project_string_tblprojectDepartment'));
		$('#project_string_tblprojectBranch').html($.i18n.prop('project_string_tblprojectBranch_title'));
		$('#project_string_tblprojectClientNo').html($.i18n.prop('project_string_tblprojectClientNo'));
		$('#project_string_tblprojectClientNoCXEE').html($.i18n.prop('project_string_tblprojectClientNo'));
		$('#project_string_srcprojectPJName').html($.i18n.prop('project_string_srcprojectPJName'));
		$('#project_string_srcprojectPJName_ct').html($.i18n.prop('project_string_srcprojectPJName'));
		$('#project_string_tblprojectPJNo').html($.i18n.prop('project_string_tblprojectPJNo'));
		$('#project_string_tblprojectPJNo_ct').html($.i18n.prop('project_string_tblprojectPJNo'));
		$('#project_string_tblprojectTempPJNo').html($.i18n.prop('project_string_tblprojectTempPJNo'));
		$('#project_string_tblprojectTempPJNo_ct').html($.i18n.prop('project_string_tblprojectTempPJNo'));
		$('#project_string_tblproject3DNo').html($.i18n.prop('project_string_tblproject3DNo'));
		$('#project_string_tblproject3DNo_ct').html($.i18n.prop('project_string_tblproject3DNo'));
		$('#project_string_tblprojectCarmake').html($.i18n.prop('project_string_tblprojectCarmake'));
		$('#project_string_tblprojectCarmake_ct').html($.i18n.prop('project_string_tblprojectCarmake'));
		$('#project_string_state').html($.i18n.prop('project_string_srcstate'));
		$('#project_string_state_ct').html($.i18n.prop('project_string_srcstate'));
		$('#project_function_list').html($.i18n.prop('project_function_srclist'));
		$('#project_function_list_ct').html($.i18n.prop('project_function_srclist'));
		
		$('#project_carmake').html($.i18n.prop('project_string_state_select'));
		$('#project_carmake_ct').html($.i18n.prop('project_string_state_select'));
		$('#project_string_state_select').html($.i18n.prop('project_string_state_select'));
		$('#project_string_state_select_ct').html($.i18n.prop('project_string_state_select'));
		
		$('#project_string_download').html($.i18n.prop('project_string_download'));
		$('#project_string_downloadCT').html($.i18n.prop('project_string_downloadCT'));
		$('#project_string_downloadCXEE').html($.i18n.prop('project_string_downloadCXEE'));
		$('#project_string_downloadCommon').html($.i18n.prop('project_string_downloadCommon'));
		$('#project_string_upload').html($.i18n.prop('project_string_upload'));
		$('#project_string_uploadCT').html($.i18n.prop('project_string_uploadCT'));
		$('#project_string_uploadCXEE').html($.i18n.prop('project_string_uploadCXEE'));
		$('#project_string_tbltransferNo').html($.i18n.prop('project_string_tbltransferNo'));
		
		
		
		
		
		$('#query').val($.i18n.prop('project_string_query'));
		$('#add').val($.i18n.prop('project_string_add'));
		$('#addCT').val($.i18n.prop('project_string_addCT'));
		$('#edit').val($.i18n.prop('project_string_edit'));
		$('#delete').val($.i18n.prop('project_string_delete'));
		
		
		tblname = $.i18n.prop('project_string_tblprojectName');
		tblcategory = $.i18n.prop('project_string_tblcategory');
		tblprojectClientNo = $.i18n.prop('project_string_tblprojectClientNo');
		tbltransferNo = $.i18n.prop('project_string_tbltransferNo');
		tblcarMaker =  $.i18n.prop('project_string_tblprojectCarmake');
		tblprojectClientName = $.i18n.prop('project_string_tblprojectClientName');
		tblprojectState = 	$.i18n.prop('project_string_tblprojectState');
		tblprojectDepartment = $.i18n.prop('project_string_tblprojectDepartment');
		tblprojectBranch = $.i18n.prop('project_string_tblprojectBranch');
		tblprojectfunction = $.i18n.prop('project_string_tblfunction');
		tbldeleteinfo1=$.i18n.prop('project_string_tbldeleteinfo1');
		tbldeleteinfo2=$.i18n.prop('project_string_tbldeleteinfo2');
		tblPJNo = $.i18n.prop('project_string_tblprojectPJNo');
		tbltempPJNo = $.i18n.prop('project_string_tblprojectTempPJNo');
		tblPJname = $.i18n.prop('project_string_tblprojectPJName');
		tblcategoryCT =$.i18n.prop('project_string_tblprojectCategoryCT');
		tbl3DNo = $.i18n.prop('project_string_tblproject3DNo');
		tblcarmake = $.i18n.prop('project_string_tblprojectCarmake');
		tblcarmakeCT = $.i18n.prop('project_string_tblprojectCarmakeCT');
		tblprojectState = $.i18n.prop('project_string_tblprojectState');
		tblprojectStateCT = $.i18n.prop('project_string_tblprojectStateCT');
		tblprojectfunction = $.i18n.prop('project_string_tblprojectFunction');
		tblprojectfunctionCT = $.i18n.prop('project_string_tblprojectFunctionCT');
		
		project_string_tblprojectPJName=$.i18n.prop('project_string_srcprojectPJName');
		project_string_tblprojectPJNo =$.i18n.prop('project_string_srcprojectPJNo');
		project_string_tblprojectTempPJNo =$.i18n.prop('project_string_srcprojectTempPJNo');
		project_string_tblcategory = $.i18n.prop('project_string_srccategory');
		project_string_tblproject3DNo = $.i18n.prop('project_string_srcproject3DNo');
		project_string_tblprojectCarmake = $.i18n.prop('project_string_srcprojectCarmake');
		project_string_state = $.i18n.prop('project_string_srcstate');
		project_function_list = $.i18n.prop('project_function_srclist');
		
		
		tblgender = $.i18n.prop('staff_string_tblgender');
		tblposition = $.i18n.prop('staff_string_tblposition');
		tbldepartment = $.i18n.prop('staff_string_tbldepartment');
		tblbranch = $.i18n.prop('staff_string_tblbranch');
		tblsuperior=$.i18n.prop('staff_string_tblsuperior');
		tblemail=$.i18n.prop('staff_string_tblemail');
		tblstateName=$.i18n.prop('staff_string_tblstateName');
		tblrole=$.i18n.prop('staff_string_tblrole');
		tbladd=$.i18n.prop('staff_string_tbladd');
		tbldelete=$.i18n.prop('staff_string_tbldelete');
		tbledit=$.i18n.prop('staff_string_tbledit');
		tbldownloadStaff=$.i18n.prop('staff_string_tbldownloadStaff');
		tbluploadStaff=$.i18n.prop('staff_string_tbluploadStaff');
		tblpagestat=$.i18n.prop('project_string_tblpagestat');
		tbltitle=$.i18n.prop('project_string_tbltitle');
		I0004=$.i18n.prop('I0004');
		I0005=$.i18n.prop('I0005');;
		I0006=$.i18n.prop('I0006');
		rowtitle=$.i18n.prop('staff_string_tblrowtitle');
		tbldownloadtitle=$.i18n.prop('project_string_tblinfo');
		I0007=$.i18n.prop('I0007');
		tbluploadtitle=$.i18n.prop('project_string_tbluploadtitle');
		I0008=$.i18n.prop('I0008');
		I0002=$.i18n.prop('I0002');
		tbladduser = $.i18n.prop('project_string_subtitle2');
		I0009 = $.i18n.prop('I0009');
		tblupduser=$.i18n.prop('project_string_subtitle3');
		tblcancel=$.i18n.prop('project_string_tblcancel');
		tbldateQuitCompany=$.i18n.prop('staff_string_tbldateQuitCompany');
		tblcomment=$.i18n.prop('staff_string_tblcomment');
		E00045 = $.i18n.prop('E00045');
		E00046 = $.i18n.prop('E00046');
		E00047 = $.i18n.prop('E00047');
		E00048 = $.i18n.prop('E00048');
	}
	
     	 function rowDblclick(rowData){
		    var projectID = $(rowData).data("projectID").toString(); 
		     var dg = new $.dialog({
				        title:subtitle1,
				        id:'project_more',
				        width:600,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        cancelBtnTxt:tblcancel,
				        resize:false,
				        page:'project/more.do?id='+projectID
				    });
    		dg.ShowDialog();
         }

     	function rowDblclickCT(rowData){
		    var staffId = $(rowData).data("staffID").toString(); 
		     var dg = new $.dialog({
				        title:rowtitle,
				        id:'user_more',
				        width:500,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        cancelBtnTxt:tblcancel,
				        resize:false,
				        page:'staff/moreCT.do?id='+staffId
				    });
    		dg.ShowDialog();
         }
		function downloadProjectCXEE() {
	      if (confirm(I0006)) {
	   		       var path = "project/downloadProject.do"
	   		       var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'user_download',
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
		function downloadProjectCT() {
	      if (confirm(I0006)) {
	   		       var path = "project/downloadProjectCT.do"
	   		       var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'user_downloadCT',
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
		function downloadProjectCommon() {
		      if (confirm(I0006)) {
		   		       var path = "project/downloadProjectCommon.do"
		   		       var dg = new $.dialog({
				        title:tbldownloadtitle,
				        id:'user_downloadCT',
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
		function uploadProjectCXEE(){
			var dg = new $.dialog({
			title:tbluploadtitle,
			id:'user_upload',
			width:500,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			cancelBtnTxt:tblcancel,
			resize:false,
			page:'project/upload.do'
			});
		dg.ShowDialog();
		}
		function uploadProjectCT(){
			var dg = new $.dialog({
			title:tbluploadtitle,
			id:'user_uploadCT',
			width:500,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			cancelBtnTxt:tblcancel,
			resize:false,
			page:'project/uploadCT.do'
			});
		dg.ShowDialog();
	}
		//部门变更时候，改变课别的选项
		function changeListCXEE(){
		    var departmentID = $("#projectDepartmentCXEE").val();
		    $.ajax({
		        type: "post",
		        url: "project/getBranchCXEE.do",
		        dataType: "json",
		        data:{"departmentID":departmentID},
		        success: function (result) {
		            var e=document.getElementById("projectBranchCXEE");
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
		        url: "staff/getBranchCT.do",
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
 	<table id="detailTable" style="width:1100px;" >
 		<tr>
 			<td style="width:148px;">
				<label id="staff_string_belong">归属:</label>
				<select name="belong" id="belong"  style="margin-bottom:5px; width:88px; padding: .2em;">
					<OPTION id="staff_string_belong_CT" VALUE="1">CT</OPTION>
					<OPTION id="staff_string_belong_CXEE" VALUE="2">CHI</OPTION>
					<OPTION id="staff_string_belong_COMMON" VALUE="3"> </OPTION>
				</select>
			</td>
			<td style="width:500px;"></td>
 			<td style="width:auto;">
            <c:if test="${QueryRoleFlag == '1'}">
                <input id="query" type="button" value="查询" style="margin-left:5px; padding: .2em;">
            </c:if>
<!--             </td>
            <td style="width:50px;"> -->
            <c:if test="${AlterRoleFlag == 1 }">
                <input id="addCT" type="button" value="CT追加" style="margin-left:5px; padding: .2em;">
<!--             </td>
            <td style="width:50px;"> -->
                <input id="add" type="button" value="CHI追加" style="margin-left:5px; padding: .2em;">
             
<!--             </td>
            <td style="width:50px;"> -->
                <input id="edit" type="button" value="编集" style="margin-left:5px; padding: .2em;">
<!--             </td>
            <td style="width:50px;"> -->
                <input id="delete" type="button" value="删除" style="margin-left:5px; padding: .2em;">
             </c:if>
            <!-- </td>
            <td style="width:80px;"> -->
            <c:if test="${UploadRoleFlag == 1 }">
				<select name="upload" id="upload"  style="margin-bottom:5px; width:80px; padding: .2em;">
					<option id="project_string_upload" value="0" selected>U/L</option>
					<c:if test="${staff.email.endsWith('.co.jp')}">
					<OPTION id="project_string_uploadCT" VALUE="1">CT</OPTION>
					</c:if>
					<c:if test="${!staff.email.endsWith('.co.jp')}">
					<OPTION id="project_string_uploadCXEE" VALUE="2">CHI</OPTION>
					</c:if>
				</select>
			</c:if>
			<!-- </td>
            <td style="width:80px;"> -->
             <c:if test="${DownloadRoleFlag == 1 }">
                <select name="download" id="download"  style="margin-bottom:5px; width:80px; padding: .2em;">
					<option id="project_string_download" value="0" selected>D/L</option>
	            <c:if test="${SpecialRole1Flag == '1'}">
					<OPTION id="project_string_downloadCT" VALUE="1">CT</OPTION>
				</c:if>
	            <c:if test="${SpecialRole2Flag == '1'}">
					<OPTION id="project_string_downloadCXEE" VALUE="2">CHI</OPTION>
				</c:if>
	            <c:if test="${SpecialRole1Flag == '1' && SpecialRole2Flag == '1'}">
					<OPTION id="project_string_downloadCommon" VALUE="3">共通</OPTION>
				</c:if>
				</select>
			</c:if>
            </td>
 		</tr>
 	</table>
 	<table id="search_condition_ct">
 		<tr>
            <td style="width:120px;">
                <label id="project_string_tblprojectPJName_ct">PJ名:</label>
			    <input type="text" id="projectPJNameCT" style="margin-bottom:5px; width:65px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:120px;">
                <label id="project_string_tblprojectPJNo_ct">PJ No.:</label>
			    <input type="text" id="projectPJNoCT" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:130px;">
                <label id="project_string_tblprojectTempPJNo_ct">仮PJ No.:</label>
			    <input type="text" id="projectTempPJNoCT" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:168px;">
                <label id="project_string_tblcategory_ct">開発段階:</label>
                <select name="projectCategory" id="projectCategory" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:100px; padding: .2em;">
                    <option id="project_category_select_ct" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${projectCategoryListCT}" var="projectCategory" varStatus="vs">
                    <option value="${projectCategory.category}" >${projectCategory.category}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:120px;">
                <label id="project_string_tblproject3DNo_ct">3D No.:</label>
			    <input type="text" id="project3DNoCT" style="margin-bottom:5px; width:60px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:158px;">
            	<label id="project_string_tblprojectCarmake_ct">メーカー:</label>	
				<select name="projectCarmake" id="projectCarmake" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="project_carmake_ct" value="" SELECTED>--请选择--</option>
					<c:forEach items="${projectCarmake}" var="projectCarmake" varStatus="vs">
						<OPTION value="${projectCarmake.carmake}">${projectCarmake.carmake}</OPTION>
					</c:forEach>
				</select>
			</td>
			<td style="width:158px;">
				<label id="project_string_state_ct">PJ状態:</label>
				<select name="projectStateCT" id="projectStateCT"  style="margin-bottom:5px; width:88px; padding: .2em;">
					<option id="project_string_state_select_ct" value="" SELECTED>--请选择--</option>
					<OPTION id="projectState_string_select_1" VALUE="1">開発中</OPTION>
					<OPTION id="projectState_string_select_2" VALUE="2">開発完了</OPTION>
					<OPTION id="projectState_string_select_3" VALUE="3">開発中止</OPTION>
					<OPTION id="projectState_string_select_4" VALUE="4">保留</OPTION>
				</select>
			</td>		
            <td style="width:158px;">
                <label id="project_function_list_ct">共通呼称:</label>
			    <input type="text" id="funtionCT" style="margin-bottom:5px; width:70px; padding: .2em;" maxlength="20"/>
			</td>
        </tr>
 	</table>
 	<table id="search_condition_cxee">
        <tr>
            <td style="width:180px;">
                <label id="project_string_tblcategory">开发阶段:</label>
                <select name="projectCategoryCXEE" id="projectCategoryCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:100px; padding: .2em;">
                    <option id="project_category_select" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${projectCategoryList}" var="projectCategoryCXEE" varStatus="vs">
                    <option value="${projectCategoryCXEE.category}" >${projectCategoryCXEE.category}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:170px;">
                <label id="project_string_tblprojectNameCXEE">项目名称:</label>
			    <input type="text" id="projectNameCXEE" style="margin-bottom:5px; width:75px; padding: .2em;" maxlength="20"/>
			</td>
			<td style="width:150px;">
                <label id="project_string_tblprojectClientNameCXEE">委托方:</label>
                <input type="text" id="projectClientName" style="margin-bottom:5px; width:60px; padding: .2em;" maxlength="10"/>
            </td>
            <td style="width:150px;">
                <label id="project_string_tblfunction">机能:</label>
			    <input type="text" id="functionList" style="margin-bottom:5px; width:60px; padding: .2em;" maxlength="20"/>
			</td>
			<td style="width:180px;">
                <label id="project_string_tblprojectDepartment">部门:</label>
                <select name="projectDepartmentCXEE" id="projectDepartmentCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:100px; padding: .2em;" onChange="changeListCXEE()">
                    <option id="project_department_selectCXEE" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${projectDepartmentListCXEE}" var="projectDepartmentCXEE" varStatus="vs">
                    <option value="${projectDepartmentCXEE.departmentID}" >${projectDepartmentCXEE.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:170px;">
                <label id="project_string_tblprojectBranch">课别:</label>
                <select name="projectBranchCXEE" id="projectBranchCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:120px; padding: .2em;">
                    <option id="project_string_branch_select" value="" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:128px;">
                <label id="project_string_tblprojectClientNoCXEE">依赖号:</label>
			    <input type="text" id="projectClientNo" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
			</td>
			<td style="width:128px;">
                <label id="project_string_tbltransferNo">管理项番:</label>
			    <input type="text" id="transferNo" style="margin-bottom:5px; width:40px; padding: .2em;" maxlength="20"/>
			</td>
		</tr>	
 </table>
  	<table id="search_condition_common">
        <tr>
            <td style="width:90px;">
                <label id="project_string_tblprojectPJName">PJ名:</label>
			    <input type="text" id="projectPJNameCommon" style="margin-bottom:5px; width:75px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:90px;">
                <label id="project_string_tblprojectPJNo">PJ No.:</label>
			    <input type="text" id="projectPJNoCommon" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:180px;">
                <label id="project_string_tblprojectTempPJNo">仮PJ No.:</label>
			    <input type="text" id="projectTempPJNoCommon" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:200px;">
                <label id="project_string_tblcategory_ct">開発段階:</label>
                <select name="projectCategoryCommon" id="projectCategoryCommon" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:100px; padding: .2em;">
                    <option id="project_category_select" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${projectCategoryListCT}" var="projectCategoryCommon" varStatus="vs">
                    <option value="${projectCategoryCommon.category}" >${projectCategoryCommon.category}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:150px;">
                <label id="project_string_tblproject3DNo">3D No.:</label>
			    <input type="text" id="project3DNoCommon" style="margin-bottom:5px; width:60px; padding: .2em;" maxlength="20"/>
            </td>
            <td style="width:180px;">
            	<label id="project_string_tblprojectCarmake">メーカー:</label>	
				<select name="projectCarmakeCommon" id="projectCarmakeCommon" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="project_carmake" value="" SELECTED>--请选择--</option>
					<c:forEach items="${projectCarmake}" var="projectCarmakeCommon" varStatus="vs">
						<OPTION value="${projectCarmakeCommon.carmake}">${projectCarmakeCommon.carmake}</OPTION>
					</c:forEach>
				</select>
			</td>
			<td style="width:180px;">
				<label id="project_string_state">PJ状態:</label>
				<select name="projectStateCommon" id="projectStateCommon"  style="margin-bottom:5px; width:88px; padding: .2em;">
					<option id="project_string_state_select" value="" SELECTED>--请选择--</option>
					<OPTION id="projectState_string_select_1" VALUE="1">開発中</OPTION>
					<OPTION id="projectState_string_select_2" VALUE="2">開発完了</OPTION>
					<OPTION id="projectState_string_select_3" VALUE="3">開発中止</OPTION>
					<OPTION id="projectState_string_select_4" VALUE="4">保留</OPTION>
				</select>
			</td>		
            <td style="width:180px;">
                <label id="project_function_list">共通呼称:</label>
			    <input type="text" id="functionCommon" style="margin-bottom:5px; width:70px; padding: .2em;" maxlength="20"/>
			</td>
        </tr>
        <tr>
            <td style="width:200px;">
                <label id="project_string_tblprojectName">项目名称:</label>
			    <input type="text" id="projectNameCommon" style="margin-bottom:5px; width:75px; padding: .2em;" maxlength="20"/>
			</td>
			<td style="width:148px;">
                <label id="project_string_tblprojectClientName">依頼方:</label>
                <input type="text" id="projectClientNameCommon" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="10"/>
            </td>
            <td style="width:148px;">
                <label id="project_string_tblprojectClientNo">依赖号:</label>
			    <input type="text" id="projectClientNoCommon" style="margin-bottom:5px; width:55px; padding: .2em;" maxlength="20"/>
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
<div id="gridCommon">
<table id="gridTableCommon" style="width:1100px;"></table>
</div>
</body>
</html>