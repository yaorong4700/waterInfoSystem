<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/flexigrid.pack.css" /> 
	<script language="javascript" type="text/javascript"
		src="js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../js/js_user/com.js"></script>
	<script type="text/javascript" src="../js/cookie/cookie.js"></script>
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
		
		//function trim(str){
		//	return str.replace(/(^\s*)|(\s*$)/g,0);
		//}
		function Trim(str)
        { 
            return str.replace(/(^\s*)|(\s*$)/g, ""); 
    	}

		$("#queryCXEE").click(function() {
				var regionId = $("#regionId").val();
				var collectTimeStart = $("#collectTimeStart").val();
				var collectTimeEnd = $("#collectTimeEnd").val();
		        var params = [{"name" : "regionId","value" : regionId}
		                     ,{"name" : "collectTimeStart","value" : collectTimeStart}
		                     ,{"name" : "collectTimeEnd","value" : collectTimeEnd}
		                     ];
		        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
		        
		        setTimeout(function (){
		        $("#gridTable tr:eq(0)").click();
		        var deviceId=$("#gridTable tr").children('td').eq(2).children('div').html();
		        var waterNum= $("#gridTable tr").children('td').eq(4).children('div').html();
		        var waterPic= $("#gridTable tr").children('td').eq(8).children('div').html();
		        if(deviceId == null){
					deviceId = "";
				}
		        $("#gridTable tr").bind('click',function(){
/* 		        	$('#gridTable .trSelected td:nth-child(5) div').each(function(i) {
						waterNum = $(this).text();
					}); */
			      	var waterNum = "";
					var deviceId = "";
					var waterPic = "";
					deviceId = $('#gridTable .trSelected td:nth-child(3) div').text();
					waterNum = $('#gridTable .trSelected td:nth-child(5) div').text();
					waterPic = $('#gridTable .trSelected td:nth-child(9) div').text();
					$("#deviceId").val(deviceId);
					$("#waterNum").val(waterNum);
					$("#waterPic").attr("src", waterPic);
		        });
		        
		        
		        if(waterNum == null){
		        	waterNum = "";
				}
		        if(waterPic == null || waterPic==""){
		        	alert("null");
		        	$("#waterPic").attr("src", "resource/0.jpg");
				}else{
					$("#waterPic").attr("src", waterPic);
				}
		        $("#deviceId").val(deviceId);
		        $("#waterNum").val(waterNum);
		        },100);
	    });
		//编集button处理
		$("#editNum").click(function() {

		    var selected_count = $('#gridTable .trSelected').length;
		    if(selected_count == '0')
		    {
		    	 alert("请选择一条数据");
				 return;
		    }
		    var waterNum = $("#waterNum").val();
		    var regionId = '';
		    var deviceId = '';
		    var collectTime = '';
		   	var reg = new RegExp("^[0-9]+(.[0-9]{0,1})?$", "g");
		    if (!reg.test(waterNum)) {
		    	alert("请输入数字，且最多只能有1位小数！");   
		    	return;
		    }
		    

			regionId = $('#gridTable .trSelected td:nth-child(2) div').text();
			deviceId = $('#gridTable .trSelected td:nth-child(3) div').text();
			collectTime = $('#gridTable .trSelected td:nth-child(4) div').text();
		    var condition = "regionId="+regionId+"&&"+"deviceId="+deviceId+"&&"+"collectTime="+collectTime+"&&"+"waterNum="+waterNum;
		    if (confirm(tbldeleteinfo1 + tbldeleteinfo2)) {
				$.ajax({
				        type: "post", 
				        url: "regionPic/editNum.do?"+condition,
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
		    //deviceId
		    /*
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
			*/
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
		    var regionIDs = '';  
		    var deviceIDs ='';
		    var collectTimes='';
		    $('#gridTable .trSelected td:nth-child(2) div').each(function(i) {
				if (i) regionIDs += ','; 
				regionIDs += $(this).text();
			});
		    $('#gridTable .trSelected td:nth-child(5) div').each(function(i) {
				if (i) deviceIDs += ','; 
				deviceIDs += $(this).text();
			});
		    $('#gridTable .trSelected td:nth-child(6) div').each(function(i) {
				if (i) collectTimes += ','; 
				collectTimes += $(this).text();
			});
		    var condition = "regionID="+regionIDs+"&&"+"deviceID="+deviceIDs+"&&"+"collectTime="+collectTimes;
	    
			$.ajax({ 
			        type: "post", 
			        url: "regionSum/deleteWaterMax.do?"+condition,
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
		    
		});
		
		function download() {
      		if (confirm(I0006)) {
   		       var path = "regionSum/downloadRegionSum.do"
   		       var dg = new $.dialog({
		        title:tbldownloadtitle,
		        id:'regionSum_download',
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
	
		$("#gridTable").flexigrid({
			url : 'regionPic/showAll.do',
			dataType : 'json',
			colModel : [      
			{
				display : "total",
				name : 'total',
				width : 10,
				sortable : false,
				align : 'center',
				hide: true,
			},
			{
				display : "测点编号",
				name : 'regionID',
				width : 50,
				sortable : false,
				align : 'center',
			},
			{
				display : '设备编号',
				name : 'deviceID',
				width : 130,
				sortable : false,
				align : 'center',
			},
			{
				display : '抄表时间',
				name : 'collectTime',
				width : 120,
				sortable : false,
				align : 'center',
			},
			{
				display : '读数',
				name : 'waterNumber',
				width : 50,
				sortable : false,
				align : 'center',
			},
			{
				display : "状态",
				name : 'codeValue',
				width : 50,
				sortable : false,
				align : 'center',
			},
			{
				display : "识别标记",
				name : 'identifyFlag',
				width : 10,
				sortable : false,
				align : 'center',
				hide: true,
			},
			{
				display : '安装地点',
				name : 'regionSummary',
				width : 100,
				sortable : false,
				align : 'center',
			},
			{
				display : '安装地点',
				name : 'waterPic',
				width : 100,
				sortable : false,
				align : 'center',
				hide: true,
			}],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "regionID",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			nomsg : I0004,
			rp : 20,
			showTableToggleBtn : true,
			procmsg : I0005, 
			width : getBrowserWidth()/2,
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
<p style="height:15px;"></p>
 	<table id="search_condition_cxee">
        <tr>
			<td style="width:auto">
                <label>测点编号:</label>
                <input type="text" id="regionId" style="margin-bottom:5px; width:240px; padding: .2em;" maxlength="6"/>
            </td>
		</tr>	
		<tr  class="info">
			<td style="width:auto">
                <label>抄表时间:</label>
                 <input type="text" id="collectTimeStart" style="margin-bottom:5px; width:240px; padding: .2em;" class="input_txt" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" value=""/>
            </td>
			 <td style="width:auto">
                 <label style="font-size: 23px">&nbsp;&nbsp;&nbsp;  ~ &nbsp;&nbsp;&nbsp;  </label>
            </td>
            <td style="width:auto">
				<input type="text" id="collectTimeEnd" style="margin-bottom:5px; width:240px; padding: .2em;" class="input_txt" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" value=""/>
            </td>
            <td style="width:auto;">
			    <input id="queryCXEE" type="button" value="查询" style="width:55px; margin-left:200px; margin-bottom:5px; padding: .2em;">
			</td>
		</tr>
 </table>
 <p style="height:10px;"></p>
 <div id ='gridRegion' style="position:absolute; width:1100;" >
 <div id="gridCXEE" style="float:left">
<table id="gridTable" style="width:550;">
</table>
</div>
 <div id="gridRegionImg" style="float:right;margin-left:30px;">
<table id="gridTable1" style="width:550;">
<tr>
	<td style="width:auto">
         <label style="font-size:30px; color:black;">现场图片</label>
    </td>
</tr>
<tr>
	<td style="width:100px" colspan="3">
         <img id="waterPic" alt="现场图片" src="resource/0.jpg" style="position:relative;">
    </td>
</tr>
<tr>
	<td style="width:auto">
         <label style="font-size:18px; color:black;">设备编号</label>
    </td>
   	<td><input style="font-size:18px; color:black; width:160px;" type="text"  name="deviceId" id="deviceId" class="input_txt"  value="" readonly/></td>

</tr>
<tr>
	<td style="width:auto">
         <label style="font-size:18px; color:black;">读数</label>   
    </td>
    <td><input  id="waterNum" name="waterNum"  style="font-size:18px; color:black; width:80px;" type="text"  class="input_txt"  value="" maxlength="8"/></td>
    <td style="width:auto;">
		<input id="editNum" type="button" value="人工修正" style="width:65px; margin-left:8px; margin-bottom:5px; padding: .2em;">
	</td>

</tr>
</table>
</div>
</div>
</body>
</html>