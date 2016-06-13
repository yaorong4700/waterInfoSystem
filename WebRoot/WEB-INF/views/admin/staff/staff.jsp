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
	<script type="text/javascript" src="js/Flexigrid/flexigrid.js"></script>
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
	var tblname ="";
	var tblgender = "";
	var tblposition = "";
	var tbldepartment = "";
	var tblbranch = "";
	var tblsuperior="";
	var tblemail ="";
	var tblstateName="";
	var tblrole="";
	var tbladd="";
	var tbldelete="";
	var tbledit="";
	var tbldownloadStaff="";
	var tbluploadStaff="";
	var tblpagestat="";
	var tbltitle="";
	var I0004="";
	var I0005="";
	var I0006="";
	var rowtitle="";
	var tbldownloadtitle="";
	var I0007="";
	var tbluploadtitle="";
	var I0008="";
	var tbldeleteinfo1="";
	var tbldeleteinfo2="";
	var I0002="";
	var tbladduser = "";
	var I0009 = "";
	var tblupduser="";
	var tblcancel="";
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
	$(document).ready(function(){
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);
		
		if("${staff.email}".indexOf(".co.jp") > 0){
			$("#belong").val("1");
			$('#search_condition_cxee').hide();
			$('#search_condition_ct').show();
			$('#gridCXEE').hide();
	    	$('#gridCT').show();
	    	$("#add").attr({"disabled":"disabled"});
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#belong").attr("disabled", "disabled");
				$("#departmentCT").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#departmentCT").find("option[value="+departmentId+"]").attr("selected",true);
				changeListCT();
	    	}
		} else {
			$("#belong").val("2");
			$('#search_condition_cxee').show();
			$('#search_condition_ct').hide();
			$('#gridCXEE').show();
	    	$('#gridCT').hide();
	    	$("#addCT").attr({"disabled":"disabled"});
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#belong").attr("disabled", "disabled");
				$("#departmentCXEE").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#departmentCXEE").find("option[value="+departmentId+"]").attr("selected",true);
				changeListCXEE();
	    	}
		}

		//归属变更时的动作
		$('#belong').change( function () {
		    var valueSelected = this.value;
		    if (valueSelected == "1"){
		    	$('#search_condition_cxee').hide();
		    	$('#search_condition_ct').show();
		    	$('#gridCXEE').hide();
		    	$('#gridCT').show();
		    	if ("${staff.email}".indexOf(".co.jp") > 0){
		    		$("#add").attr({"disabled":"disabled"});
		    		$("#addCT").removeAttr("disabled");
		    		$("#edit").removeAttr("disabled");
		    		$("#delete").removeAttr("disabled");
		    	} else {
		    		$("#addCT").attr({"disabled":"disabled"});
		    		$("#edit").attr({"disabled":"disabled"});
		    		$("#delete").attr({"disabled":"disabled"});
		    		$("#add").attr({"disabled":"disabled"});
		    	} 
		    } else {
		    	$('#search_condition_cxee').show();
		    	$('#search_condition_ct').hide();
		    	$('#gridCXEE').show();
		    	$('#gridCT').hide();
		    	if ("${staff.email}".indexOf(".co.jp") > 0){
		    		$("#add").attr({"disabled":"disabled"});
		    		$("#edit").attr({"disabled":"disabled"});
		    		$("#delete").attr({"disabled":"disabled"});
		    		$("#addCT").attr({"disabled":"disabled"});
		    	} else {
		    		$("#addCT").attr({"disabled":"disabled"});
		    		$("#add").removeAttr("disabled");
		    		$("#edit").removeAttr("disabled");
		    		$("#delete").removeAttr("disabled");
		    	}
		    }
		});
		//U/L变更时的动作
		$('#upload').change( function () {
		    var valueSelected = this.value;
		    if (valueSelected == "1") {
	            var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	            if (SpecialRole1Flag != 1) {
		        	alert(E00047);
	  	   			return false;
	            }
		    	uploadStaffCT();
		    } else if (valueSelected == "2") {
	            var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	            if (SpecialRole2Flag != 1) {
		        	alert(E00048);
	  	   			return false;
	            }
		    	uploadStaff();
		    	
		    }
		    $('#upload option:first').attr("selected",true);
		});
		//D/L变更的动作
		$('#download').change( function () {
			var valueSelected = this.value;
		    if (valueSelected == "1") {
		    	downloadStaffCT();
		    } else if (valueSelected == "2") {
		    	downloadStaff();
		    }
		    $('#download option:first').attr("selected",true);
		});
	/**
	 * add event dialog
	 */
		$("#query").click(function() {
			 var QueryRoleFlag 		 = $("#QueryRoleFlag").val();
			 if(QueryRoleFlag != 1){
				 return "";
			 }else{
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
						var departmentID   = $("#departmentCT").val();
				        var branchID       = $("#branchCT").val();
				        var name           = $("#nameCT").val();
				        var positionID     = $("#positionIDCT").val();
				        var state          = $("#stateCT").val();
						
				        var params = [{"name" : "department","value" : departmentID}
				                     ,{"name" : "branch","value" : branchID}
				                     ,{"name" : "staffName","value" : name}
				                     ,{"name" : "position","value" : positionID}
				                     ,{"name" : "stateName","value" : state}
				                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
				                     ];
				        $('#gridTableCT').flexOptions({params : params, newp : 1}).flexReload();
					} else{
				        var departmentID   = $("#departmentCXEE").val();
				        var branchID       = $("#branchCXEE").val();
				        var name           = $("#nameCXEE").val();
				        var searchSuperior = $("#searchSuperior").val();
				        var positionID     = $("#positionIDCXEE").val();
				        var state          = $("#stateCXEE").val();
				        var dateQuitCompany = $("#dateQuitCompany").val();
				        var isYMDFlag = "";
				        
				        var regYM = /^[1-9][0-9]{3}\/[0-9]{2}$/;
						var regYMD = /^[1-9][0-9]{3}\/[0-9]{2}\/[0-9]{2}$/;
						var regY = /^[1-9][0-9]{3}$/
						if (dateQuitCompany != "") {
							if (regYMD.test(dateQuitCompany) == true){
								isYMDFlag = "1";
							} else if(regYM.test(dateQuitCompany) == true){
								isYMDFlag = "2";
							} else if (regY.test(dateQuitCompany) == true){
								isYMDFlag = "3";
							} else {
								alert($.i18n.prop('E0090'));
								return;
							}
						}
						
						
				        var params = [{"name" : "department","value" : departmentID}
				                     ,{"name" : "branch","value" : branchID}
				                     ,{"name" : "staffName","value" : name}
				                     ,{"name" : "superior","value" : searchSuperior}
				                     ,{"name" : "position","value" : positionID}
				                     ,{"name" : "stateName","value" : state}
				                     ,{"name" : "dateQuitCompany","value" : dateQuitCompany}
				                     ,{"name" : "isYMDFlag","value" : isYMDFlag}
				                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
				                     ];
				        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
					}
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
				id:'user_new_CT',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/addCT.do'
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
				id:'user_new',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/add.do'
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
		    } else {
			    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
				  if (i) ids += ',';
				  ids += $(this).text();
			    })
		    }
		    var dg = new $.dialog({
		        title:tblupduser,
		        id:'user_edit',
		        width:500,
		        height:400,
		        iconTitle:false,
		        cover:true,
		        maxBtn:false,
		        xButton:true,
		        resize:false,
		        page:'staff/edit.do?id='+ids
		    });
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
		    } else {
			    $('#gridTable .trSelected td:nth-child(2) div').each(function(i) {
			    	if (i)  name += ',';
					name += $(this).text();
				});
			    
			    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
					if (i) ids += ','; 
					ids += $(this).text();
				});
		    }
		    if (confirm(tbldeleteinfo1 + name + tbldeleteinfo2)) {
				$.ajax({ 
				        type: "post", 
				        url: "staff/staffDelete.do?ids="+ids, 
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
		});
		
		$("#gridTable").flexigrid({
			
			url : 'staff/listStaff.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'staffID',
				name : 'staffID',
				width : 1,
				sortable : false,
				align : 'left',
				hide : true
			}, 
			{
				display : tblname,
				name : 'name',
				id:'name',
				width : 100,
				sortable : false,
				align : 'center'
			},
			{
				display : tblgender,
				name : 'gender',
				width : 50,
				sortable : true,
				align : 'left'
			},
			{
				display : tblposition,
				name : 'position',
				width : 150,
				sortable : true,
				align : 'left'
			},{
				display : 'departmentID',
				name : 'departmentID',
				width : 1,
				sortable : true,
				align : 'left',
				hide: true
			},{
				display : tbldepartment,
				name : 'department',
				width : 150,
				sortable : true,
				align : 'left'
			} ,{
				display : 'branchID',
				name : 'branchID',
				width : 1,
				sortable : true,
				align : 'left',
				hide: true
			}, {
				display : tblbranch,
				name : 'branch',
				width : 160,
				sortable : true,
				align : 'left'
			}, {
				display : tblsuperior,
				name : 'superior',
				width : 100,
				sortable : true,
				align : 'center'
			}, {
				display : tblemail,
				name : 'email',
				width : 300,
				sortable : false,
				align : 'left'
			} , {
				display : tblstateName,
				name : 'stateName',
				width : 80,
				sortable : false,
				align : 'left'
			}, {
				display : tbldateQuitCompany,
				name : 'dateQuitCompany',
				width : 80,
				sortable : false,
				align : 'left'
			},{
				display : tblrole,
				name : 'roleName',
				width : 100,
				sortable : false,
				align : 'left'
			},{
				display : tblcomment,
				name : 'comment',
				width : 100,
				sortable : false,
				align : 'left'
			}  ],
			
			/* buttons : [ <c:if test="${staff.role == 2}">{
				name : tbladd,
				bclass : 'add',
				onpress : test
			}, {
				name : tbldelete,
				bclass : 'delete',
				onpress : test
			},{
				name : tbledit,
				bclass : 'edit',
				onpress : test
			}, 
			{
				name : tbldownloadStaff,
				onpress : downloadStaff
			}, 
			{
				name : tbluploadStaff,
				onpress : uploadStaff
			}, 
			</c:if>  
			{
				separator : true
			} ], */
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
			onRowDblclick : rowDblclick,  
			procmsg : I0005, 
			width : 1500,
			height : 'auto'
		});

		$("#gridTableCT").flexigrid({
			url : 'staff/listStaffCT.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'staffID',
				name : 'staffID',
				width : 1,
				sortable : false,
				align : 'left',
				hide : true
			}, 
			{
				display : tblname,
				name : 'name',
				id:'name',
				width : 100,
				sortable : false,
				align : 'center'
			}, {
				display : tblposition,
				name : 'position',
				width : 150,
				sortable : true,
				align : 'left'
			},{
				display : 'departmentID',
				name : 'departmentID',
				width : 1,
				sortable : true,
				align : 'left',
				hide: true
			},{
				display : tbldepartment,
				name : 'department',
				width : 150,
				sortable : true,
				align : 'left'
			} ,{
				display : 'branchID',
				name : 'branchID',
				width : 1,
				sortable : true,
				align : 'left',
				hide: true
			}, {
				display : tblbranch,
				name : 'branch',
				width : 160,
				sortable : true,
				align : 'left'
			}, {
				display : tblemail,
				name : 'email',
				width : 300,
				sortable : false,
				align : 'left'
			} , {
				display : tblstateName,
				name : 'stateName',
				width : 80,
				sortable : false,
				align : 'left'
			}, {
				display : tblrole,
				name : 'roleName',
				width : 100,
				sortable : false,
				align : 'left'
			},{
				display : tblcomment,
				name : 'comment',
				width : 100,
				sortable : false,
				align : 'left'
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
			onRowDblclick : rowDblclickCT,  
			procmsg : I0005, 
			width : 1250,
			height : 'auto'
			
		});
	});
		 function rowDblclick(rowData){
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
				        page:'staff/more.do?id='+staffId
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
		function downloadStaff() {
		      if (confirm(I0006)) {
   	   		       var path = "staff/downloadStaff.do"
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
		function downloadStaffCT() {
		      if (confirm(I0006)) {
 	   		       var path = "staff/downloadStaffCT.do"
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
		function uploadStaff(){
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
				page:'staff/upload.do'
				});
    		dg.ShowDialog();
		}
		function uploadStaffCT(){
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
				page:'staff/uploadCT.do'
				});
    		dg.ShowDialog();
		}
		/* function test(com, grid) {
			
			if (com == tbldelete) {
			 selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert(I0008);
					    return;
				    }
				    name = '';
				    $('.trSelected td:nth-child(2) div', grid).each(function(i) {
					        if (i)
						        name += ',';
					        name += $(this).text();
				        });
				    ids = '';
				    $('.trSelected td:nth-child(1) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    if (confirm(tbldeleteinfo1 + name + tbldeleteinfo2)) {
					     $.ajax({ 
						        type: "post", 
						        url: "staff/staffDelete.do?ids="+ids, 
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
			} else if (com == tbladd) {
				 var dg = new $.dialog({
				title:tbladduser,
				id:'user_new',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/add.do'
				});
    		dg.ShowDialog();
			}else if(com == tbledit){
			     selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert(I0008);
					    return;
				    }
				    if (selected_count > 1) {
					    alert(I0009);
					    return;
				    }
				    ids = "";
				     $('.trSelected td:nth-child(1) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    
				    var dg = new $.dialog({
				        title:tblupduser,
				        id:'user_edit',
				        width:500,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        page:'staff/edit.do?id='+ids
				    });
    		dg.ShowDialog();
			}
		} */

		//部门变更时候，改变课别的选项
		function changeListCXEE(){
		    var departmentID = $("#departmentCXEE").val();
		    $.ajax({
		        type: "post",
		        url: "staff/getBranchCXEE.do",
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
			$('#staff_string_select_4').html($.i18n.prop('staff_string_select_4'));
			$('#staff_string_name').html($.i18n.prop('staff_string_name'));
			
			$('#staff_string_department_cxee').html($.i18n.prop('staff_string_department'));
			$('#staff_string_department_select_cxee').html($.i18n.prop('staff_string_department_select'));
			$('#staff_string_branch_select_cxee').html($.i18n.prop('staff_string_branch_select'));
			$('#staff_string_positionID_select_cxee').html($.i18n.prop('staff_string_positionID_select'));
			$('#staff_string_state_select_cxee').html($.i18n.prop('staff_string_state_select'));			
			$('#staff_string_branch_cxee').html($.i18n.prop('staff_string_branch'));
			$('#staff_string_positionID_cxee').html($.i18n.prop('staff_string_positionID'));
			$('#staff_string_state_cxee').html($.i18n.prop('staff_string_state'));
			$('#staff_string_select_1_cxee').html($.i18n.prop('staff_string_select_1'));
			$('#staff_string_select_2_cxee').html($.i18n.prop('staff_string_select_2'));
			$('#staff_string_select_3_cxee').html($.i18n.prop('staff_string_select_3'));
			$('#staff_string_name_cxee').html($.i18n.prop('staff_string_name'));
			
			$('#staff_string_superior').html($.i18n.prop('staff_string_superior'));
			$('#staff_string_dateQuitCompany').html($.i18n.prop('staff_string_dateQuitCompany'));
			$('#staff_string_belong').html($.i18n.prop('staff_string_belong'));
			$('#staff_string_belong_CT').html($.i18n.prop('staff_string_belong_CT'));
			$('#staff_string_belong_CXEE').html($.i18n.prop('staff_string_belong_CXEE'));
			$('#staff_string_upload').html($.i18n.prop('staff_string_upload'));
			$('#staff_string_uploadCT').html($.i18n.prop('staff_string_uploadCT'));
			$('#staff_string_uploadCXEE').html($.i18n.prop('staff_string_uploadCXEE'));
			$('#staff_string_download').html($.i18n.prop('staff_string_download'));
			$('#staff_string_downloadCT').html($.i18n.prop('staff_string_downloadCT'));
			$('#staff_string_downloadCXEE').html($.i18n.prop('staff_string_downloadCXEE'));
			
			$('#query').val($.i18n.prop('staff_string_query'));
			$('#add').val($.i18n.prop('staff_string_add'));
			$('#addCT').val($.i18n.prop('staff_string_addCT'));
			$('#edit').val($.i18n.prop('staff_string_edit'));
			$('#delete').val($.i18n.prop('staff_string_delete'));
			
			tblname = $.i18n.prop('staff_string_tblname');
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
			tblpagestat=$.i18n.prop('staff_string_tblpagestat');
			tbltitle=$.i18n.prop('staff_string_tbltitle');
			I0004=$.i18n.prop('I0004');
			I0005=$.i18n.prop('I0005');;
			I0006=$.i18n.prop('I0006');
			rowtitle=$.i18n.prop('staff_string_tblrowtitle');
			tbldownloadtitle=$.i18n.prop('staff_string_tbldownloadtitle');
			I0007=$.i18n.prop('I0007');
			tbluploadtitle=$.i18n.prop('staff_string_tbluploadtitle');
			I0008=$.i18n.prop('I0008');
			tbldeleteinfo1=$.i18n.prop('staff_string_tbldeleteinfo1');
			tbldeleteinfo2=$.i18n.prop('staff_string_tbldeleteinfo2');
			I0002=$.i18n.prop('I0002');
			tbladduser = $.i18n.prop('staff_string_tbladduser');
			I0009 = $.i18n.prop('I0009');
			tblupduser=$.i18n.prop('staff_string_tblupduser');
			tblcancel=$.i18n.prop('staff_string_tblcancel');
			tbldateQuitCompany=$.i18n.prop('staff_string_tbldateQuitCompany');
			tblcomment=$.i18n.prop('staff_string_tblcomment');
			E00045 = $.i18n.prop('E00045');
			E00046 = $.i18n.prop('E00046');
			E00047 = $.i18n.prop('E00047');
			E00048 = $.i18n.prop('E00048');
		}
	</script>
</head>
<body style="width:1100px;" >
<!-- 自定义搜索框,后面改用框架自带搜索框 
    <table  id="findtable"  border="1" bordercolor="#a0c6e5"  width="1400" align="center" border="5" style="background: #EEEEEE;border-collapse:collapse;">
		<tr >
			<td style="color:black;width:5%">&nbsp;&nbsp;员工姓名:</td>
			<td  style="width:8%"><input type="text" id="staffName" size="11" />
			</td>
			<td  style="width:5%" ><input type="button" id="search" onclick="search()" value="搜索"  />
			</td>
			<td style="width:85%">&nbsp;</td>
		</tr>
		
	</table>
 -->
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
				</select>
			</td>
			<td style="width:500px;"></td>
 			<td style="width:auto;">
            <c:if test="${QueryRoleFlag == '1'}">
                <input id="query" type="button" value="查询" style="margin-left:5px; padding: .2em;">
            </c:if>
<!--             </td>
            <td style="width:50px;"> -->
            <c:if test="${AlterRoleFlag == '1'}">
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
            <!-- </td>
            <td style="width:80px;"> -->
            </c:if>
            <c:if test="${UploadRoleFlag == '1'}">
				<select name="upload" id="upload"  style="margin-bottom:5px; width:80px; padding: .2em;">
					<option id="staff_string_upload" value="0" selected>U/L</option>
					<c:if test="${staff.email.endsWith('.co.jp')}">
					<OPTION id="staff_string_uploadCT" VALUE="1">CT</OPTION>
					</c:if>
					<c:if test="${!staff.email.endsWith('.co.jp')}">
					<OPTION id="staff_string_uploadCXEE" VALUE="2">CHI</OPTION>
					</c:if>
				</select>
			</c:if>
			<!-- </td>
            <td style="width:80px;"> -->
            <c:if test="${DownloadRoleFlag == '1'}">
                <select name="download" id="download"  style="margin-bottom:5px; width:80px; padding: .2em;">
					<option id="staff_string_download" value="0" selected>D/L</option>
	            <c:if test="${SpecialRole1Flag == '1'}">
					<OPTION id="staff_string_downloadCT" VALUE="1">CT</OPTION>
				</c:if>
	            <c:if test="${SpecialRole2Flag == '1'}">
					<OPTION id="staff_string_downloadCXEE" VALUE="2">CHI</OPTION>
				</c:if>
				</select>
			</c:if>
            </td>
 		</tr>
 	</table>
 	<table id="search_condition_ct">
 		<tr>
            <td style="width:212px;">
                <label id="staff_string_department">部门:</label>
                <select name="departmentCT" id="departmentCT" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:168px; padding: .2em;" onChange="changeListCT()">
                    <option id="staff_string_department_select" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${departmentListCT}" var="department" varStatus="vs">
                    <option value="${department.departmentID}" >${department.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:200px;">
                <label id="staff_string_branch">课别:</label>
                <select name="branchCT" id="branchCT" class="text ui-widget-content ui-corner-all"
                    style="margin-bottom:5px; width:128px; padding: .2em;">
                    <option id="staff_string_branch_select" value="" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:128px;">
            	<label id="staff_string_positionID">职位:</label>	
				<select name="positionIDCT" id="positionIDCT" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="staff_string_positionID_select" value="" SELECTED>--请选择--</option>
					<c:forEach items="${positionListCT}" var="position" varStatus="vs">
						<OPTION value="${position.positionID}">${position.position}</OPTION>
					</c:forEach>
				</select>
			</td>
			<td style="width:148px;">
				<label id="staff_string_state">在职状态:</label>
				<select name="stateCT" id="stateCT"  style="margin-bottom:5px; width:88px; padding: .2em;">
					<option id="staff_string_state_select" value="" SELECTED>--请选择--</option>
					<OPTION id="staff_string_select_1" VALUE="1">在职</OPTION>
					<OPTION id="staff_string_select_2" VALUE="2">休假</OPTION>
					<OPTION id="staff_string_select_3" VALUE="3">离职</OPTION>
					<OPTION id="staff_string_select_4" VALUE="4">異動</OPTION>
				</select>
			</td>		
            <td style="width:128px;">
                <label id="staff_string_name">姓名:</label>
			    <input type="text" id="nameCT" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>
			</td>
        </tr>
 	</table>
 	<table id="search_condition_cxee">
        <tr>
            <td style="width:212px;">
                <label id="staff_string_department_cxee">部门:</label>
                <select name="departmentCXEE" id="departmentCXEE" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:168px; padding: .2em;" onChange="changeListCXEE()">
                    <option id="staff_string_department_select_cxee" value="" SELECTED>--请选择--</option>
                    <c:forEach items="${departmentListCXEE}" var="department" varStatus="vs">
                    <option value="${department.departmentID}" >${department.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:200px;">
                <label id="staff_string_branch_cxee">课别:</label>
                <select name="branchCXEE" id="branchCXEE" class="text ui-widget-content ui-corner-all"
                    style="margin-bottom:5px; width:128px; padding: .2em;">
                    <option id="staff_string_branch_select_cxee" value="" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:128px;">
            	<label id="staff_string_positionID_cxee">职位:</label>	
				<select name="positionIDCXEE" id="positionIDCXEE" style="margin-bottom:5px; width:88px; padding: .2em;" >
					<option id="staff_string_positionID_select_cxee" value="" SELECTED>--请选择--</option>
					<c:forEach items="${positionListCXEE}" var="position" varStatus="vs">
						<OPTION value="${position.positionID}">${position.position}</OPTION>
					</c:forEach>
				</select>
			</td>
			<td style="width:148px;">
				<label id="staff_string_state_cxee">在职状态:</label>
				<select name="stateCXEE" id="stateCXEE"  style="margin-bottom:5px; width:88px; padding: .2em;">
					<option id="staff_string_state_select_cxee" value="" SELECTED>--请选择--</option>
					<OPTION id="staff_string_select_1_cxee" VALUE="1">在职</OPTION>
					<OPTION id="staff_string_select_2_cxee" VALUE="2">休假</OPTION>
					<OPTION id="staff_string_select_3_cxee" VALUE="3">离职</OPTION>
				</select>
			</td>		
            <td style="width:128px;">
                <label id="staff_string_name_cxee">姓名:</label>
			    <input type="text" id="nameCXEE" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>
			</td>
			<td style="width:148px;">
                <label id="staff_string_dateQuitCompany">离职日:</label>
                <input type="text" id="dateQuitCompany" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="10"/>
            </td>
            <td style="width:128px;">
                <label id="staff_string_superior">上司:</label>
			    <input type="text" id="searchSuperior" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>
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