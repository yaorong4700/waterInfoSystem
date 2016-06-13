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
    </style>
	<script type="text/javascript">
	
	var language = getCookieValue("language");
	var tblcategory ="";
	var tbltask ="";
	var tbltaskProcessID= "";
	var tbldepartment ="";
	var tblmemo ="";
	var tbloutput ="";
	var tbladd ="";
	var tbledit ="";
	var tbldelete ="";
	var tblpagestat="";
	var tbltitle = "";
	var tbluploadtitle="";
	var tblcancel="";
	var rowcategory ="";
	var rowtask ="";
	var rowdepartment ="";
	var subtitle1 = "";
	var subtitle2 = "";
	var subcancel = "";
	var tblinput = "";
	var tblIsVisible = "";
	var tblNo = "";
	
	var I0004= "";
	var I0005= "";
	var I0006= "";
	var I0007= "";
	var I0008= "";
	var I0009= "";
	var I0015= "";
	var I0016= "";
	var I0002= "";
	
	
	//国际化
	function setPageByLanguage(){
		
		tblcategory =$.i18n.prop('projectTask_string_tblcategory');
		tbltask =$.i18n.prop('projectTask_string_tbltask');
		tbltaskProcessID = $.i18n.prop('projectTask_string_tbltaskProcessID');
		tbldepartment =$.i18n.prop('projectTask_string_tbldepartment');
		tblmemo =$.i18n.prop('projectTask_string_tblmemo');
		tbloutput =$.i18n.prop('projectTask_string_tbloutput');
		tbladd =$.i18n.prop('projectTask_string_tbladd');
		tbledit =$.i18n.prop('projectTask_string_tbledit');
		tbldelete =$.i18n.prop('projectTask_string_tbldelete');
		tblpagestat=$.i18n.prop('projectTask_string_tblpagestat');
		tbltitle = $.i18n.prop('projectTask_string_tbltitle');
		rowcategory =$.i18n.prop('projectTask_string_rowcategory');
		rowtask =$.i18n.prop('projectTask_string_rowtask');
		rowdepartment =$.i18n.prop('projectTask_string_rowdepartment');
		subtitle1 = $.i18n.prop('projectTask_string_subtitle1');
		subtitle2 = $.i18n.prop('projectTask_string_subtitle2');
		subcancel = $.i18n.prop('projectTask_string_subcancel');
		tblinput = $.i18n.prop('projectTask_string_tblinput');
		tblcancel = $.i18n.prop('projectTask_string_tblcancel');
		tbluploadtitle = $.i18n.prop('projectTask_string_tbluploadtitle');
		tblIsVisible = $.i18n.prop('projectTask_string_tblIsVisible');
		tblNo = $.i18n.prop('projectTask_string_tblNo');
		
		I0004= $.i18n.prop('I0004');
		I0005= $.i18n.prop('I0005');
		I0006= $.i18n.prop('I0006');
		I0007= $.i18n.prop('I0007');
		I0008= $.i18n.prop('I0008');
		I0009= $.i18n.prop('I0009');
		I0015= $.i18n.prop('I0015');
		I0016= $.i18n.prop('I0016');
		I0002= $.i18n.prop('I0002');
				
	}
	
	$(document).ready(function(){	
		/**
	 * add event dialog
	 */
	if (language!="CN" && language!="JP"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage);	
	
	$("#gridTable").flexigrid({
			url : 'projectTask/showAll.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'taskID',
				name : 'taskID',
			
				width : 1,
				sortable : false,
				align : 'left',
				hide : true
			},{
				display : tblNo,
				name : 'No',
				width : 50,
				sortable : true,
				align : 'center'
			},{
				display : tblcategory,
				name : 'category',
				width : 150,
				sortable : true,
				align : 'center'
			}, {
				display : tbltask,
				name : 'task',
				width : 400,
				sortable : true,
				align : 'left'
			}, <c:if test='${staff.kaisya == "CT" }'>{
				display : tbltaskProcessID,
				name : 'taskProcessID',
				width : 100,
				sortable : true,
				align : 'left'
			} ,</c:if> {
				display : tbldepartment,
				name : 'department',
				width : 150,
				sortable : true,
				align : 'left'
			} 
			, {
				display : tblmemo,
				name : 'memo',
				width : 100,
				sortable : true,
				align : 'left'
			}, {
				display : tblIsVisible,
				name : 'isVisible',
				width : 100,
				sortable : true,
				align : 'left'
			}],
			buttons : [<c:if test='${AlterRoleFlag == "1" }'> {
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
			},</c:if>
			<c:if test='${DownloadRoleFlag == "1" }'>{
				name : tbloutput,
				onpress : test
			},</c:if>
			<c:if test='${UploadRoleFlag == "1" && staff.kaisya == "CT"  }'>
			{
				name : tblinput,
				onpress : uploadProjectTask
			},
			</c:if>
			{
				separator : true
			} ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			usepager : true,
			query: '',
			useRp : true,
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true, 
			procmsg : I0005, 
			searchitems : [  {display : rowcategory,name : 'category',isdefault : true}, 
			{display : rowtask,name : 'task'},
			{display : rowdepartment,name : 'department'}],
			width : 1400,
			height : 'auto'
		});
});
	function uploadProjectTask(){
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
			page:'projectTask/upload.do'
			});
		dg.ShowDialog();
	}
		function test(com, grid) {
			if (com == tbldelete) {
			 selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert(I0008);
					    return;
				    }
				    task = '';
				    $('.trSelected td:nth-child(3) div', grid).each(function(i) {
					        if (i)
						        task += ',';
					        task += $(this).text();
				        });
				    ids = '';
				    $('.trSelected td:nth-child(1) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    if (confirm(I0015 + task + I0016)) {
					     $.ajax({ 
						        type: "post", 
						        url: "projectTask/delete.do?ids="+ids, 
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
				title:subtitle1,
				id:'projectTask_new',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				cancelBtnTxt:subcancel,
				resize:false,
				page:'projectTask/add.do'
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
				        title:subtitle2,
				        id:'projectTask_edit',
				        width:500,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        cancelBtnTxt:subcancel,
				        resize:false,
				        page:'projectTask/edit.do?id='+ids
				    });
    		dg.ShowDialog();
			}
			else if(com ==tbloutput){
			    if (confirm(I0006)) {
   	   		       var path = "projectTask/outPut.do"
   	   		       var dg = new $.dialog({
				        title:'outPut',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        cancelBtnTxt:subcancel,
				        resize:false,	
				        loadingText:I0007,
				        page:path
				    });
    		        dg.ShowDialog(); 
   	   		   }
			}
		}
	</script>
</head>
<body>	
<table id="gridTable"></table>


</body>
</html>