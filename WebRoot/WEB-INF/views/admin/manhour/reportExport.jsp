<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>报表生成</title>
		<link type="text/css" rel="stylesheet" href="css/main.css" />
		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
		<script language="javascript" type="text/javascript" src="js/datePicker/WdatePicker.js"></script> 
		<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
		<script type="text/javascript" src="js/js_user/com.js"></script>
		<script type="text/javascript" src="js/cookie/cookie.js"></script>

		<script type="text/javascript">
		var language = getCookieValue("language");
		var select = "";
		var E00031 = "";
		var E00017 = "";
		var E00018 = "";
		var E00032 = "";
		var E00019 = "";
		var E00020 = "";
		var E00033 = "";
		var E00034 = "";
		var I0017 = "";
		var subtitle = "";
		var E00035 = "";
		var E00036 = "";
		var E00042 = "";
		var E00043 = "";
		var E00044 = "";
		
		function setPageByLanguage(){
			
			select = $.i18n.prop('reportExport_string_select');
			E00031 = $.i18n.prop('E00031');
			E00017 = $.i18n.prop('E00017');
			E00018 = $.i18n.prop('E00018');
			E00032 = $.i18n.prop('E00032');
			E00019 = $.i18n.prop('E00019');
			E00020 = $.i18n.prop('E00020');
			E00033 = $.i18n.prop('E00033');
			E00034 = $.i18n.prop('E00034');
			I0017 = $.i18n.prop('I0017');
			subtitle = $.i18n.prop('reportExport_string_subtitle');
			E00035 = $.i18n.prop('E00035');
			E00036 = $.i18n.prop('E00036');
			E00042 = $.i18n.prop('E00042');
			E00043 = $.i18n.prop('E00043');
			E00044 = $.i18n.prop('E00044');
			
			$('#reportExport_string_cxee_excel').html($.i18n.prop('reportExport_string_cxee_excel'));
			
			$('#reportExport_string_department').html($.i18n.prop('reportExport_string_department'));
			$('#reportExport_string_department_1').html($.i18n.prop('reportExport_string_department_1'));
			$('#reportExport_string_department_2').html($.i18n.prop('reportExport_string_department_2'));
			$('#reportExport_string_department_3').html($.i18n.prop('reportExport_string_department_3'));
			$('#reportExport_string_branch').html($.i18n.prop('reportExport_string_branch'));
			$('#reportExport_string_startDate').html($.i18n.prop('reportExport_string_startDate'));
			$('#reportExport_string_startYear').html($.i18n.prop('reportExport_string_startYear'));
			$('#reportExport_string_startMonth').html($.i18n.prop('reportExport_string_startMonth'));
			$('#reportExport_string_startDay').html($.i18n.prop('reportExport_string_startDay'));
			$('#reportExport_string_endDate').html($.i18n.prop('reportExport_string_endDate'));
			//$('#reportExport_string_endYear').html($.i18n.prop('reportExport_string_endYear'));
			$('#reportExport_string_endMonth').html($.i18n.prop('reportExport_string_endMonth'));
			$('#reportExport_string_exporttime').html($.i18n.prop('reportExport_string_exporttime'));
			$('#exportdepartmonth').val($.i18n.prop('reportExport_string_exportdepartmonth'));
			$('#exportPersonsWorkNum').val($.i18n.prop('reportExport_string_exportPersonsWorkNum'));
			$('#exportCXEECSV').val($.i18n.prop('reportExport_string_exportCSV'));
/* 			$('#reportExport_string_memo1').html($.i18n.prop('reportExport_string_memo1'));
			$('#reportExport_string_memo2').html($.i18n.prop('reportExport_string_memo2'));
			$('#reportExport_string_memo3').html($.i18n.prop('reportExport_string_memo3')); */
			
			$('#reportExport_string_department_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_department_01').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_department_02').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_branch_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_branch_0_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_year_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_year_0_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_month_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_month_0_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_startday_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endyear_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endyear_0_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endmonth_0').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endmonth_0_1').html($.i18n.prop('reportExport_string_select'));
			
			$('#reportExport_string_endmonth_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endmonth_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endyear_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_endyear_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_month_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_month_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_year_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_year_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_startday_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_branch_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			
			$('#reportExport_string_branch_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			
			$('#reportExport_string_department_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_department_0_ForCt_1').html($.i18n.prop('reportExport_string_select'));
			
			$('#reportExport_string_belong_0_ForCt').html($.i18n.prop('reportExport_string_select'));
			$('#reportExport_string_department_0_ForCt1').html($.i18n.prop('reportExport_string_select'));

			$('#reportExport_string_belong_0').html($.i18n.prop('reportExport_string_select'));
			
		}
		
			function initMonth()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 	var select = document.getElementById('month');			
				select.options.length=1;	//清楚下拉框数据	
				
				var selYear=document.getElementById("year").value;
				if(selYear >0 && selYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("month").options[i]=new Option(i,i);
			
					}
				}
				else if(selYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("month").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("month").options[i]=new Option(i,i);
					}	
				}
				calcExportPeriods();
			}
			function initMonth_ForCt()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 	var select = document.getElementById('month_ForCt');			
				select.options.length=1;	//清楚下拉框数据	
				
				var selYear=document.getElementById("year_ForCt").value;
				if(selYear >0 && selYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("month_ForCt").options[i]=new Option(i,i);
			
					}
				}
				else if(selYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("month_ForCt").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("month_ForCt").options[i]=new Option(i,i);
					}	
				}
				calcExportPeriods_ForCt();
			}	
			function initendMonth()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 
				var endselect = document.getElementById('endmonth');			
				endselect.options.length=1;	//清楚下拉框数据	
				var endselYear=document.getElementById("endyear").value;
				
				if(endselYear >0 && endselYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("endmonth").options[i]=new Option(i,i);
			
					}
				}
				else if(endselYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("endmonth").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("endmonth").options[i]=new Option(i,i);
					}	
				}
				calcExportPeriods();
			}
			function initendMonth_ForCt()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 
				var endselect = document.getElementById('endmonth_ForCt');			
				endselect.options.length=1;	//清楚下拉框数据	
				var endselYear=document.getElementById("endyear_ForCt").value;
				
				if(endselYear >0 && endselYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("endmonth_ForCt").options[i]=new Option(i,i);
			
					}
				}
				else if(endselYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("endmonth_ForCt").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("endmonth_ForCt").options[i]=new Option(i,i);
					}	
				}
				calcExportPeriods_ForCt();
			}
			//CXEE侧CSV出力时开始年月
			function initMonthCSV()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 	var select = document.getElementById('month_cxee_csv');			
				select.options.length=1;	//清楚下拉框数据	
				
				var selYear=document.getElementById("year_cxee_csv").value;
				if(selYear >0 && selYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("month_cxee_csv").options[i]=new Option(i,i);
			
					}
				}
				else if(selYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("month_cxee_csv").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("month_cxee_csv").options[i]=new Option(i,i);
					}	
				}
			}
			function initMonthCSV_ForCt()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 	var select = document.getElementById('month_cxee_csv_ForCt');			
				select.options.length=1;	//清楚下拉框数据	
				
				var selYear=document.getElementById("year_cxee_csv_ForCt").value;
				if(selYear >0 && selYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("month_cxee_csv_ForCt").options[i]=new Option(i,i);
			
					}
				}
				else if(selYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("month_cxee_csv_ForCt").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("month_cxee_csv_ForCt").options[i]=new Option(i,i);
					}	
				}
			}
			function initendMonthCSV()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 
				var endselect = document.getElementById('endmonth_cxee_csv');			
				endselect.options.length=1;	//清楚下拉框数据	
				var endselYear=document.getElementById("endyear_cxee_csv").value;
				
				if(endselYear >0 && endselYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("endmonth_cxee_csv").options[i]=new Option(i,i);
			
					}
				}
				else if(endselYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("endmonth_cxee_csv").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("endmonth_cxee_csv").options[i]=new Option(i,i);
					}	
				}
			}
			function initendMonthCSV_ForCt()
			{
				var now = new Date();
	        	var years = now.getFullYear();     	
	       	 	var months = now.getMonth();
	       	 
				var endselect = document.getElementById('endmonth_cxee_csv_ForCt');			
				endselect.options.length=1;	//清楚下拉框数据	
				var endselYear=document.getElementById("endyear_cxee_csv_ForCt").value;
				
				if(endselYear >0 && endselYear <years)
				{
					for(var i=1;i<=12;i++)
					{
						document.getElementById("endmonth_cxee_csv_ForCt").options[i]=new Option(i,i);
			
					}
				}
				else if(endselYear == years)
				{
					for(var i=1;i<=months+2;i++)
					{
						if(i>12)
						{
							i=12;
							document.getElementById("endmonth_cxee_csv_ForCt").options[i]=new Option(i,i);
							break;
						}
						document.getElementById("endmonth_cxee_csv_ForCt").options[i]=new Option(i,i);
					}	
				}
			}
		$(document).ready(function(){
			
			if (language!="CN" && language!="JP"){
				language = "CN";
			}
			loadProperties(language,setPageByLanguage);

			changeBelong_ForCt();
			changeBelong();
		    var now = new Date();
	        var years = now.getFullYear();
	        var months = now.getMonth();
	        for(var i=2014;i<=years;i++)
	        {
	        	document.getElementById("year").options[i-2013]=new Option(i,i);
	        	document.getElementById("year_ForCt").options[i-2013]=new Option(i,i);
	        	document.getElementById("endyear").options[i-2013]=new Option(i,i);
	        	document.getElementById("endyear_ForCt").options[i-2013]=new Option(i,i);
	        	document.getElementById("year_cxee_csv").options[i-2013]=new Option(i,i);
	        	document.getElementById("year_cxee_csv_ForCt").options[i-2013]=new Option(i,i);
	        	document.getElementById("endyear_cxee_csv").options[i-2013]=new Option(i,i);
	        	document.getElementById("endyear_cxee_csv_ForCt").options[i-2013]=new Option(i,i);
	        }  
			if("${staff.email}".indexOf(".co.jp") > 0){
				$("#select_belong_ForCt").val("1");
		    	if ($("#DepartmentFlag").val() == "0") {
					$('#div_ForCxee').hide();
					$('#div_ForCt').show();
					$("#select_belong_ForCt").attr("disabled", "disabled");
					$("#department_ForCt").attr("disabled", "disabled");
					$("#department_cxee_csv_ForCt").attr("disabled", "disabled");
					var departmentId = "${staff.departmentID}";
					$("#department_ForCt").find("option[value="+departmentId+"]").attr("selected",true);
					$("#department_cxee_csv_ForCt").find("option[value="+departmentId+"]").attr("selected",true);
					changeList_ForCt();
					changeListCSV_ForCt();
				}
			} else {
				$("#select_belong").val("2");
		    	if ($("#DepartmentFlag").val() == "0") {
					$('#div_ForCt').hide();
					$('#div_ForCxee').show();
					var objct = document.getElementById("div_ForCxee");
					objct.style.float = "left";
					$("#select_belong").attr("disabled", "disabled");
					$("#department").attr("disabled", "disabled");
					$("#department_cxee_csv").attr("disabled", "disabled");
					var departmentId = "${staff.departmentID}";
					$("#department").find("option[value="+departmentId+"]").attr("selected",true);
					$("#department_cxee_csv").find("option[value="+departmentId+"]").attr("selected",true);
					changeList();
					changeListCSV();
				}
			}
           $("#exportdepartmonth").click(function() {   
	           var mydepartment   = $("#department").find("option:selected").text();
	           var mybranch       = $("#branch").find("option:selected").text();
	           var myDepartmentID = $("#department").val();
	           var myBranchID     = $("#branch").val();
	           var myyear         = $("#year").val();
	           var mymonth        = $("#month").val();
	           var myendyear      = $("#endyear").val();
	           var myendmonth     = $("#endmonth").val();
	           var mystartday     = $("#startday").val();
	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	           if (DownloadRoleFlag != 1 || SpecialRole2Flag != 1) {
   	   		       alert(E00044);
   	   			   return false;
	           }
               if ($("#department").val() == select) {
   	   		       alert(E00031);
   	   			   $("#department").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#year").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#startday").val() == select) {
	   			   alert(E00032);
	   			   $("#startday").focus();
	   			   return false;
	   		   }
   	   		   if ($("#endyear").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year").val()>$("#endyear").val()) {
	   		       alert(E00033);
	   			   $("#year").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month").val())>=4) {
	   		       if ($("#year").val()==$("#endyear").val()&&(parseInt($("#endmonth").val()))<parseInt($("#month").val())) {
	   			       alert(E00034);
	   			       $("#month").focus();
	   			       return false;
	   		   	   } 
	   		       if ($("#year").val()!=$("#endyear").val()&&(parseInt($("#endmonth").val()))>=4) {
			       alert(E00036);
			       $("#month").focus();
			       return false;
		   	       }
	   		   }
	   		   if (parseInt($("#month").val())<4) {
	   		 		if(parseInt($("#year").val())!=parseInt($("#endyear").val())){
						alert(E00036);
		    			$("#year").focus();
		    			return false;
	   				}
	   				if((parseInt($("#endmonth").val())>=4)||(parseInt($("#endmonth").val())<parseInt($("#month").val()))){
	   					alert(E00034);
	   			    	$("#month").focus();
	   			    	return false;
	   	   			}
	   		   }
   	   		   //return false;
   	   		   var path = "output/outputDepartJijiExcel.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   																   +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                     	   +"&startday="+mystartday
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth;

	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();

          });
           $("#exportdepartmonth_ForCt").click(function() {   
	           var mydepartment   = $("#department_ForCt").find("option:selected").text();
	           var mybranch       = $("#branch_ForCt").find("option:selected").text();
	           var myDepartmentID = $("#department_ForCt").val();
	           var myBranchID     = $("#branch_ForCt").val();
	           var myyear         = $("#year_ForCt").val();
	           var mymonth        = $("#month_ForCt").val();
	           var myendyear      = $("#endyear_ForCt").val();
	           var myendmonth     = $("#endmonth_ForCt").val();
	           var mystartday     = $("#startday_ForCt").val();
	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	           if (DownloadRoleFlag != 1 || SpecialRole1Flag != 1) {
   	   		       alert(E00043);
   	   			   return false;
	           }
               if ($("#department_ForCt").val() == select) {
   	   		       alert(E00031);
   	   			   $("#department_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#year_ForCt").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month_ForCt").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#startday_ForCt").val() == select) {
	   			   alert(E00032);
	   			   $("#startday_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if ($("#endyear_ForCt").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear_ForCt").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth_ForCt").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year_ForCt").val()>$("#endyear_ForCt").val()) {
	   		       alert(E00033);
	   			   $("#year_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month_ForCt").val())>=4) {
	   		       if ($("#year_ForCt").val()==$("#endyear_ForCt").val()&&(parseInt($("#endmonth_ForCt").val()))<parseInt($("#month_ForCt").val())) {
	   			       alert(E00034);
	   			       $("#month_ForCt").focus();
	   			       return false;
	   		   	   } 
	   		       if ($("#year_ForCt").val()!=$("#endyear_ForCt").val()&&(parseInt($("#endmonth_ForCt").val()))>=4) {
			       alert(E00036);
			       $("#month_ForCt").focus();
			       return false;
		   	       }
	   		   }
	   		   if (parseInt($("#month_ForCt").val())<4) {
	   		 		if(parseInt($("#year_ForCt").val())!=parseInt($("#endyear_ForCt").val())){
						alert(E00036);
		    			$("#year_ForCt").focus();
		    			return false;
	   				}
	   				if((parseInt($("#endmonth_ForCt").val())>=4)||(parseInt($("#endmonth_ForCt").val())<parseInt($("#month_ForCt").val()))){
	   					alert(E00034);
	   			    	$("#month_ForCt").focus();
	   			    	return false;
	   	   			}
	   		   }
   	   		   //return false;
   	   		   var path = "output/outputDepartJijiExcel_ForCt.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   																   +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                     	   +"&startday="+mystartday
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth;

	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();

          });
           $("#exportPersonsWorkNum").click(function() {   
        	   var mydepartment   = $("#department").find("option:selected").text();
	           var mybranch       = $("#branch").find("option:selected").text();
	           var myDepartmentID = $("#department").val();
	           var myBranchID     = $("#branch").val();
	           var myyear         = $("#year").val();
	           var mymonth        = $("#month").val();
	           var myendyear      = $("#endyear").val();
	           var myendmonth     = $("#endmonth").val();
	           var mystartday     = $("#startday").val()
	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
	           if (DownloadRoleFlag != 1 || SpecialRole2Flag != 1) {
   	   		       alert(E00044);
   	   			   return false;
	           }
               if ($("#department").val() == select) {
   	   		       alert(E00031);
   	   			   $("#department").focus();
   	   			   return false;
   	   		   }
               if ($("#department").val() == "公司整体") {
   	   		       alert(E00035);
   	   			   $("#department").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#year").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#startday").val() == select) {
	   			   alert(E00032);
	   			   $("#startday").focus();
	   			   return false;
	   		   }
   	   		   if ($("#endyear").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year").val() > $("#endyear").val()) {
	   		       alert(E00033);
	   			   $("#year").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month").val()) >= 4) {
   	   		       if ($("#year").val()==$("#endyear").val()&&(parseInt($("#endmonth").val()))<parseInt($("#month").val())) {
 	   			       alert(E00034);
 	   			       $("#month").focus();
 	   			       return false;
 	   		   	   } 
   	   		       if ($("#year").val() != $("#endyear").val() && (parseInt($("#endmonth").val())) >= 4) {
  			       alert(E00036);
  			       $("#month").focus();
  			       return false;
  		   	       }
	   		   }
   	   		  if (parseInt($("#month").val())<4) {
   	   		 		if(parseInt($("#year").val())!=parseInt($("#endyear").val())){
						alert(E00036);
		    			$("#year").focus();
		    			return false;
	   				}
   	   				if((parseInt($("#endmonth").val())>=4)||(parseInt($("#endmonth").val())<parseInt($("#month").val()))){
   	   					alert(E00034);
	   			    	$("#month").focus();
	   			    	return false;
   	   	   			}
	   		   }
   	   		   //return false;//界面层测试点
   	   		   var path = "output/exportPersonsWorkNum.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   	 															  +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                     	   +"&startday="+mystartday
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth;
	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();

          });
           $("#exportPersonsWorkNum_ForCt").click(function() {   
        	   var mydepartment   = $("#department_ForCt").find("option:selected").text();
	           var mybranch       = $("#branch_ForCt").find("option:selected").text();
	           var myDepartmentID = $("#department_ForCt").val();
	           var myBranchID     = $("#branch_ForCt").val();
	           var myyear         = $("#year_ForCt").val();
	           var mymonth        = $("#month_ForCt").val();
	           var myendyear      = $("#endyear_ForCt").val();
	           var myendmonth     = $("#endmonth_ForCt").val();
	           var mystartday     = $("#startday_ForCt").val()
	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	           if (DownloadRoleFlag != 1 || SpecialRole1Flag != 1) {
   	   		       alert(E00043);
   	   			   return false;
	           }
               if ($("#department_ForCt").val() == select) {
   	   		       alert(E00031);
   	   			   $("#department_ForCt").focus();
   	   			   return false;
   	   		   }
               if ($("#department_ForCt").val() == "公司整体") {
   	   		       alert(E00035);
   	   			   $("#department_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#year_ForCt").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month_ForCt").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#startday_ForCt").val() == select) {
	   			   alert(E00032);
	   			   $("#startday_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if ($("#endyear_ForCt").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear_ForCt").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth_ForCt").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year_ForCt").val() > $("#endyear_ForCt").val()) {
	   		       alert(E00033);
	   			   $("#year_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month_ForCt").val()) >= 4) {
   	   		       if ($("#year_ForCt").val()==$("#endyear_ForCt").val()&&(parseInt($("#endmonth_ForCt").val()))<parseInt($("#month_ForCt").val())) {
 	   			       alert(E00034);
 	   			       $("#month_ForCt").focus();
 	   			       return false;
 	   		   	   } 
   	   		       if ($("#year_ForCt").val() != $("#endyear_ForCt").val() && (parseInt($("#endmonth_ForCt").val())) >= 4) {
  			       alert(E00036);
  			       $("#month_ForCt").focus();
  			       return false;
  		   	       }
	   		   }
   	   		  if (parseInt($("#month_ForCt").val())<4) {
   	   		 		if(parseInt($("#year_ForCt").val())!=parseInt($("#endyear_ForCt").val())){
						alert(E00036);
		    			$("#year_ForCt").focus();
		    			return false;
	   				}
   	   				if((parseInt($("#endmonth_ForCt").val())>=4)||(parseInt($("#endmonth_ForCt").val())<parseInt($("#month_ForCt").val()))){
   	   					alert(E00034);
	   			    	$("#month_ForCt").focus();
	   			    	return false;
   	   	   			}
	   		   }
   	   		   //return false;//界面层测试点
   	   		   var path = "output/exportPersonsWorkNum_ForCt.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   	 															  +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                     	   +"&startday="+mystartday
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth;
	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();

          });
           $("#exportCXEECSV").click(function() {   
			   var sel_belong     = $("#select_belong").val();
	           var mydepartment   = $("#department_cxee_csv").find("option:selected").text();
	           var mybranch       = $("#branch_cxee_csv").find("option:selected").text();
	           var myDepartmentID = $("#department_cxee_csv").val();
	           var myBranchID     = $("#branch_cxee_csv").val();
	           if (sel_belong == "1") {
		           mydepartment   = $("#department_cxee_csv1").find("option:selected").text();
		           mybranch       = $("#branch_cxee_csv1").find("option:selected").text();
		           myDepartmentID = $("#department_cxee_csv1").val();
		           myBranchID     = $("#branch_cxee_csv1").val();
	           }
	           var myyear         = $("#year_cxee_csv").val();
	           var mymonth        = $("#month_cxee_csv").val();
	           var myendyear      = $("#endyear_cxee_csv").val();
	           var myendmonth     = $("#endmonth_cxee_csv").val();

	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	           var SpecialRole2Flag     = $("#SpecialRole2Flag").val();

	           if ($("#select_belong").val() == select) {
   	   		       alert(E00042);
   	   			   $("#select_belong").focus();
   	   			   return false;
   	   		   }
	           if (sel_belong == 2) {
		           if (DownloadRoleFlag != 1 || SpecialRole2Flag != 1) {
	   	   		       alert(E00044);
	   	   			   return false;
		           }
	           } else {
		           if (DownloadRoleFlag != 1 || SpecialRole1Flag != 1) {
	   	   		       alert(E00043);
	   	   			   return false;
		           }
	           }
	           if (sel_belong == "1") {
	               if ($("#department_cxee_csv1").val() == select) {
	   	   		       alert(E00031);
	   	   			   $("#department_cxee_csv1").focus();
	   	   			   return false;
	   	   		   }
	           } else {
	               if ($("#department_cxee_csv").val() == select) {
	   	   		       alert(E00031);
	   	   			   $("#department_cxee_csv").focus();
	   	   			   return false;
	   	   		   }
	           }
   	   		   if ($("#year_cxee_csv").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year_cxee_csv").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month_cxee_csv").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month_cxee_csv").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#endyear_cxee_csv").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear_cxee_csv").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth_cxee_csv").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth_cxee_csv").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year_cxee_csv").val()>$("#endyear_cxee_csv").val()) {
	   		       alert(E00033);
	   			   $("#year_cxee_csv").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month_cxee_csv").val())>=4) {
	   		       if ($("#year_cxee_csv").val()==$("#endyear_cxee_csv").val()&&(parseInt($("#endmonth_cxee_csv").val()))<parseInt($("#month_cxee_csv").val())) {
	   			       alert(E00034);
	   			       $("#month_cxee_csv").focus();
	   			       return false;
	   		   	   } 
	   		       if ($("#year_cxee_csv").val()!=$("#endyear_cxee_csv").val()&&(parseInt($("#endmonth_cxee_csv").val()))>=4) {
			       alert(E00036);
			       $("#month_cxee_csv").focus();
			       return false;
		   	       }
	   		   }
	   		   if (parseInt($("#month_cxee_csv").val())<4) {
	   		 		if(parseInt($("#year_cxee_csv").val())!=parseInt($("#endyear_cxee_csv").val())){
						alert(E00036);
		    			$("#year_cxee_csv").focus();
		    			return false;
	   				}
	   				if((parseInt($("#endmonth_cxee_csv").val())>=4)||(parseInt($("#endmonth_cxee_csv").val())<parseInt($("#month_cxee_csv").val()))){
	   					alert(E00034);
	   			    	$("#month_cxee_csv").focus();
	   			    	return false;
	   	   			}
	   		   }
   	   		   //return false;
   	   		   var path = "output/exportCXEECSV.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   																   +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth+"&belong="+sel_belong;

	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();
          });
           $("#exportCXEECSV_ForCt").click(function() {   
			   var sel_belong     = $("#select_belong_ForCt").val();
	           var mydepartment   = $("#department_cxee_csv_ForCt").find("option:selected").text();
	           var mybranch       = $("#branch_cxee_csv_ForCt").find("option:selected").text();
	           var myDepartmentID = $("#department_cxee_csv_ForCt").val();
	           var myBranchID     = $("#branch_cxee_csv_ForCt").val();
	           if (sel_belong == "2") {
		           mydepartment   = $("#department_cxee_csv_ForCt1").find("option:selected").text();
		           mybranch       = $("#branch_cxee_csv_ForCt1").find("option:selected").text();
		           myDepartmentID = $("#department_cxee_csv_ForCt1").val();
		           myBranchID     = $("#branch_cxee_csv_ForCt1").val();
	           }
	           var myyear         = $("#year_cxee_csv_ForCt").val();
	           var mymonth        = $("#month_cxee_csv_ForCt").val();
	           var myendyear      = $("#endyear_cxee_csv_ForCt").val();
	           var myendmonth     = $("#endmonth_cxee_csv_ForCt").val();

	           var DownloadRoleFlag     = $("#DownloadRoleFlag").val();
	           var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
	           var SpecialRole2Flag     = $("#SpecialRole2Flag").val();

	           if ($("#select_belong_ForCt").val() == select) {
   	   		       alert(E00042);
   	   			   $("#select_belong_ForCt").focus();
   	   			   return false;
   	   		   }
	           if (sel_belong == 2) {
		           if (DownloadRoleFlag != 1 || SpecialRole2Flag != 1) {
	   	   		       alert(E00044);
	   	   			   return false;
		           }
	           } else {
		           if (DownloadRoleFlag != 1 || SpecialRole1Flag != 1) {
	   	   		       alert(E00043);
	   	   			   return false;
		           }
	           }
/* 	           if (sel_belong == "2") {
	               if ($("#department_cxee_csv_ForCt1").val() == select) {
	   	   		       alert(E00031);
	   	   			   $("#department_cxee_csv_ForCt1").focus();
	   	   			   return false;
	   	   		   }
	           } else {
	               if ($("#department_cxee_csv_ForCt").val() == select) {
	   	   		       alert(E00031);
	   	   			   $("#department_cxee_csv_ForCt").focus();
	   	   			   return false;
	   	   		   }
	           } */
   	   		   if ($("#year_cxee_csv_ForCt").val() == select) {
   	   		       alert(E00017);
   	   			   $("#year_cxee_csv_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#month_cxee_csv_ForCt").val() == select) {
   	   			   alert(E00018);
   	   			   $("#month_cxee_csv_ForCt").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#endyear_cxee_csv_ForCt").val() == select) {
	   		       alert(E00032);
	   			   $("#endyear_cxee_csv_ForCt").focus();
	   			   return false;
	   		   }
	   		   if ($("#endmonth_cxee_csv_ForCt").val() == select) {
	   			   alert(E00020);
	   			   $("#endmonth_cxee_csv_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if ($("#year_cxee_csv_ForCt").val()>$("#endyear_cxee_csv_ForCt").val()) {
	   		       alert(E00033);
	   			   $("#year_cxee_csv_ForCt").focus();
	   			   return false;
	   		   }
   	   		   if (parseInt($("#month_cxee_csv_ForCt").val())>=4) {
	   		       if ($("#year_cxee_csv_ForCt").val()==$("#endyear_cxee_csv_ForCt").val()&&(parseInt($("#endmonth_cxee_csv_ForCt").val()))<parseInt($("#month_cxee_csv_ForCt").val())) {
	   			       alert(E00034);
	   			       $("#month_cxee_csv_ForCt").focus();
	   			       return false;
	   		   	   } 
	   		       if ($("#year_cxee_csv_ForCt").val()!=$("#endyear_cxee_csv_ForCt").val()&&(parseInt($("#endmonth_cxee_csv_ForCt").val()))>=4) {
			       alert(E00036);
			       $("#month_cxee_csv_ForCt").focus();
			       return false;
		   	       }
	   		   }
	   		   if (parseInt($("#month_cxee_csv_ForCt").val())<4) {
	   		 		if(parseInt($("#year_cxee_csv_ForCt").val())!=parseInt($("#endyear_cxee_csv_ForCt").val())){
						alert(E00036);
		    			$("#year_cxee_csv_ForCt").focus();
		    			return false;
	   				}
	   				if((parseInt($("#endmonth_cxee_csv_ForCt").val())>=4)||(parseInt($("#endmonth_cxee_csv_ForCt").val())<parseInt($("#month_cxee_csv_ForCt").val()))){
	   					alert(E00034);
	   			    	$("#month_cxee_csv_ForCt").focus();
	   			    	return false;
	   	   			}
	   		   }
   	   		   //return false;
   	   		   var path = "output/exportCXEECSV_ForCt.do?department="+encodeURI(mydepartment)+"&branch="+encodeURI(mybranch)
   	   																   +"&departmentID="+myDepartmentID+"&branchID="+myBranchID
   	   		                                                           +"&year="+myyear+"&month="+mymonth
   	   		                                                           +"&endyear="+myendyear+"&endmonth="+myendmonth+"&belong="+sel_belong;

	           var dg = new $.dialog({
				        title:'Download',
				        id:'user_download',
				        width:400,
				        height:400,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        loadingText:I0017,
				        page:encodeURI(path)
				    });
    		 dg.ShowDialog();
          });
       });	
function changeList(){
	var departmentID  = $("#department").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function changeList_ForCt(){
	var departmentID  = $("#department_ForCt").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect_ForCt.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch_ForCt");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function changeListCSV(){
	var departmentID  = $("#department_cxee_csv").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch_cxee_csv");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function changeListCSV1(){
	var departmentID  = $("#department_cxee_csv1").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect_ForCt.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch_cxee_csv");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function changeListCSV_ForCt(){
	var departmentID  = $("#department_cxee_csv_ForCt").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect_ForCt.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch_cxee_csv_ForCt");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function changeListCSV_ForCt1(){
	var departmentID  = $("#department_cxee_csv_ForCt1").val();
     $.ajax({ 
               type: "post", 
               url: "output/branchselect.do", 
               dataType: "json", 
               data:{"departmentID":departmentID}, 
               success: function (result) {
               		var e=document.getElementById("branch_cxee_csv_ForCt");
       				e.options.length=1;	//清楚下拉框数据	
               		//$("#branch").empty();
               		$.each(result.result,function(index,content) {
               			 e.options.add(new Option(content.branch, content.branchID));
                           });
               } 
           });
}
function calcExportPeriods(){
	var startYear  = $("#year").val();
	var startMonth = $("#month").val();
	var startDay   = $("#startday").val();
	var endYear    = $("#endyear").val();
	var endMonth   = $("#endmonth").val();
	var endDay     = 20;
	
	if (startYear == select || startMonth == select || startDay == select
			|| endYear == select || endMonth == select ){
		$("#exportPeriods").html("");
		return;
	}
	
	if (startDay == "1"){
		endDay = new Date(endYear,endMonth,0).getDate();
	} 
	if (startDay =="21" || startDay == "26"){
		endDay = startDay - 1;
		startMonth = startMonth - 1;
		if (startMonth <= 0){
			startMonth = 12;
			startYear = startYear - 1;
		}
	}
	
	$("#exportPeriods").html(startYear+"/"+startMonth+"/"+startDay+"~"+endYear+"/"+endMonth+"/"+endDay);
}

function calcExportPeriods_ForCt(){
	var startYear  = $("#year_ForCt").val();
	var startMonth = $("#month_ForCt").val();
	var startDay   = $("#startday_ForCt").val();
	var endYear    = $("#endyear_ForCt").val();
	var endMonth   = $("#endmonth_ForCt").val();
	var endDay     = 20;
	
	if (startYear == select || startMonth == select || startDay == select
			|| endYear == select || endMonth == select ){
		$("#exportPeriods_ForCt").html("");
		return;
	}
	
	if (startDay == "1"){
		endDay = new Date(endYear,endMonth,0).getDate();
	} 
	if (startDay =="21" || startDay == "26"){
		endDay = startDay - 1;
		startMonth = startMonth - 1;
		if (startMonth <= 0){
			startMonth = 12;
			startYear = startYear - 1;
		}
	}
	
	$("#exportPeriods_ForCt").html(startYear+"/"+startMonth+"/"+startDay+"~"+endYear+"/"+endMonth+"/"+endDay);
}

function changeBelong_ForCt(){
	var sel_belong =  $("#select_belong_ForCt").val();
	
	var objct = document.getElementById("department_cxee_csv_ForCt");
	var objcxee = document.getElementById("department_cxee_csv_ForCt1");
	
	if(sel_belong == "1"){
		objct.style.display = "block";
		objcxee.style.display = "none";
		document.getElementById("department_cxee_csv_ForCt").value = select;
	}else if (sel_belong == "2"){
		objct.style.display = "none";
		objcxee.style.display = "block";
		document.getElementById("department_cxee_csv_ForCt1").value = select;
	}else{
		objct.style.display = "block";
		objcxee.style.display = "none";
		document.getElementById("department_cxee_csv_ForCt").value = select;
	}
}

function changeBelong(){
	var sel_belong =  $("#select_belong").val();
	
	var objct = document.getElementById("department_cxee_csv1");
	var objcxee = document.getElementById("department_cxee_csv");
	
	if(sel_belong == "1"){
		objct.style.display = "block";
		objcxee.style.display = "none";
		document.getElementById("department_cxee_csv1").value = select;
	}else if (sel_belong == "2"){
		objct.style.display = "none";
		objcxee.style.display = "block";
		document.getElementById("department_cxee_csv").value = select;
	}else{
		objct.style.display = "none";
		objcxee.style.display = "block";
		document.getElementById("department_cxee_csv").value = select;
	}
}

</script>
</head>
<body>
	<input type="hidden" id ="DownloadRoleFlag" value = "${DownloadRoleFlag}">
	<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
	<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
	<input type="hidden" id ="DepartmentFlag" value = "${DepartmentFlag}">
    <form action="output.do" name="outputForm" id="outputForm" target="result"
			method="post" onsubmit="return checkInfo();">
		<div style="width:1500px;">
			<!-- FORCT -->
	        <div style="float:left;" id="div_ForCt">
		        <table style="width:700px; padding:5px;">
					<tr>
						<p id="reportExport_string_cxee_excel_ForCt" style="text-align:left;font-size:16px;font-weight:bold;">CT エクセルファイル</p>
					</tr>
					<tr>
						<td >
							<label id="reportExport_string_department_ForCt">部:</label>
						</td>
						<td >
						    <select name="department_ForCt" id="department_ForCt" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:150px; padding: .4em;" onChange="changeList_ForCt()">
								<option id="reportExport_string_department_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>	
								<c:forEach items="${departList_ForCt}" var="department_ForCt" varStatus="vs">
		                            <OPTION value="${department_ForCt.departmentID}" >${department_ForCt.department}</OPTION>
								</c:forEach>
							</select>
						</td>
						<td >
							<label id="reportExport_string_branch_ForCt">グループ:</label>
						</td>
						<td >
						    <select name="branch_ForCt" id="branch_ForCt" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:150px; padding: .4em;">
							   <option id="reportExport_string_branch_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>	
							</select>
						</td>
					</tr>
					<tr>
						<td >
							<label id="reportExport_string_startDate_ForCt">開始日：</label>
						</td>
						<td >
							<select name="year_ForCt" id="year_ForCt" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:150px; padding: .4em;" onchange="initMonth_ForCt()">
								<option id="reportExport_string_year_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>							
							</select>	
						</td>					
						<td >
							<label id="reportExport_string_startYear_ForCt">年</label>
						</td>
						<td >
						<select name="month_ForCt" id="month_ForCt" class="text ui-widget-content ui-corner-all" 
						style="margin-bottom:12px; width:150px; padding: .4em;" onchange="calcExportPeriods_ForCt()">
							<option id="reportExport_string_month_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>		
						</select>				
						</td>							
						<td >
							<label id="reportExport_string_startMonth_ForCt">月</label>
						</td>
						<td >
							<select name="startday_ForCt" id="startday_ForCt" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;" onchange="calcExportPeriods_ForCt()" >
								<option id="reportExport_string_startday_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>
								<option value="21">21</option>
								<option value="1">1</option>
								<option value="26">26</option>		
							</select>
						</td>
						<td>
							<label id="reportExport_string_startDay_ForCt">日</label>
						</td>
					</tr>
					<tr>
						<td>
							<label id="reportExport_string_endDate_ForCt">終了日：</label>
						</td>
						<td>
							<select name="endyear_ForCt" id="endyear_ForCt" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:150px; padding: .4em;" onchange="initendMonth_ForCt()">
								<option id="reportExport_string_endyear_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>							
							</select>	
						</td>
						<td>
							<label id="reportExport_string_endYear_ForCt">年</label>
						</td>
						<td>
						<select name="endmonth_ForCt" id="endmonth_ForCt" class="text ui-widget-content ui-corner-all" 
						style="margin-bottom:12px; width:150px; padding: .4em;" onchange="calcExportPeriods_ForCt()">
							<option id="reportExport_string_endmonth_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>		
						</select>				
						</td>
						<td>
							<label id="reportExport_string_endMonth_ForCt">月</label>
						</td>
					</tr>
					<tr>							
						<td>
							<label id="reportExport_string_exporttime_ForCt">出力期間：</label>
						</td>
						<td colspan="2">
							<p align="left" style="margin-bottom:12px; width:auto; padding: .4em;" id="exportPeriods_ForCt"></p>				
						</td>							
					</tr>
		        </table>
				<div style="margin-left:50px; margin-top:10px; position:absolute;">
					<div>
						<input type=button id="exportdepartmonth_ForCt" value="部/グループ実績集計"/>
						<br>
					</div>
				</div>
				<div style="margin-left:250px; margin-top:10px; position:absolute;">
					<div>
						<input type=button id="exportPersonsWorkNum_ForCt" value="個人実績集計"/>
						<br>
					</div>
				</div>
				<div style="margin-top:50px; position:absolute;">
					<table style="width:700px; padding:5px;">
						<tr>
							<td><p id="reportExport_string_cxee_csv_ForCt" style="text-align:left;font-size:16px;font-weight:bold;">CSVファイル</p></td>
							<td><input type=button id="exportCXEECSV_ForCt" value="出力"/></td>
						</tr>
						<tr>
							<td>
								<label id="label_belong" style="color:black">帰属:</label>
							</td>
							<td>
								<select name="selectname_belong_ForCt" id="select_belong_ForCt"  class="text ui-widget-content ui-corner-all"
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeBelong_ForCt();" >
									<option id="reportExport_string_belong_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>	
									<option value="1" >CT</option>
									<option value="2" >CHI</option>
								</select>				
							</td>
							<td colspan=2>
								<label id="reportExport_string_department_ForCt">部:</label>
							</td>
							<td colspan=3>
							    <select name="department_cxee_csv_ForCt" id="department_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeListCSV_ForCt()">
									<option id="reportExport_string_department_0_ForCt" value="--请选择--" SELECTED>--请选择--</option>	
									<c:forEach items="${departList_ForCt}" var="department_ForCt" varStatus="vs">
			                            <OPTION value="${department_ForCt.departmentID}" >${department_ForCt.department}</OPTION>
									</c:forEach>
								</select>
							    <select name="department_cxee_csv_ForCt1" id="department_cxee_csv_ForCt1" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeListCSV_ForCt1()">
									<option id="reportExport_string_department_0_ForCt1" value="--请选择--" SELECTED>--请选择--</option>	
									<option id="reportExport_string_department_1_ForCt1" value="公司整体" >公司整体</option>
									<option id="reportExport_string_department_2_ForCt1" value="開発部門" >開発部門</option>
									<option id="reportExport_string_department_3_ForCt1" value="開発以外" >開発以外</option>	
									<c:forEach items="${departList}" var="department" varStatus="vs">
			                            <OPTION value="${department.departmentID}" >${department.department}</OPTION>
									</c:forEach>
								</select>
							</td>
							<td >
								<label id="reportExport_string_branch_ForCt">グループ:</label>
							</td>
							<td >
							    <select name="branch_cxee_csv_ForCt" id="branch_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;">
								   <option id="reportExport_string_branch_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>	
								</select>
							</td>
						</tr>
						<tr>
							<td >
								<label id="reportExport_string_time_cxee_csv_ForCt">期間：</label>
							</td>
							<td >
								<select name="year_cxee_csv_ForCt" id="year_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:100px; padding: .4em;" onchange="initMonthCSV_ForCt()">
									<option id="reportExport_string_year_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>							
								</select>	
							</td>					
							<td >
								<label id="reportExport_string_startYear_ForCt">年</label>
							</td>
							<td >
							<select name="month_cxee_csv_ForCt" id="month_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;" >
								<option id="reportExport_string_month_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>		
							</select>				
							</td>							
							<td >
								<label id="reportExport_string_startMonth_ForCt">月</label>
							</td>
							<td>
								<label >～</label>
							</td>
							<td>
								<select name="endyear_cxee_csv_ForCt" id="endyear_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:100px; padding: .4em;" onchange="initendMonthCSV_ForCt()">
									<option id="reportExport_string_endyear_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>							
								</select>	
							</td>
							<td>
								<label id="reportExport_string_endYear_ForCt">年</label>
							</td>
							<td>
							<select name="endmonth_cxee_csv_ForCt" id="endmonth_cxee_csv_ForCt" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;">
								<option id="reportExport_string_endmonth_0_ForCt_1" value="--请选择--" SELECTED>--请选择--</option>		
							</select>				
							</td>
							<td>
								<label id="reportExport_string_endMonth_ForCt">月</label>
							</td>
						</tr>
			        </table>
	        	</div>
	        </div>
			<!-- FORCXEE -->
	        <div style="float:right;" id="div_ForCxee">
		        <table style="width:700px; padding:5px;">
					<tr>
						<p id="reportExport_string_cxee_excel" style="text-align:left;font-size:16px;font-weight:bold;">CXEE EXCEL报表</p>
					</tr>
					<tr>
						<td >
							<label id="reportExport_string_department">部门:</label>
						</td>
						<td >
						    <select name="department" id="department" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:150px; padding: .4em;" onChange="changeList()">
								<option id="reportExport_string_department_0" value="--请选择--" SELECTED>--请选择--</option>	
								<option id="reportExport_string_department_1" value="公司整体" >公司整体</option>
								<option id="reportExport_string_department_2" value="開発部門" >開発部門</option>
								<option id="reportExport_string_department_3" value="開発以外" >開発以外</option>	
								<c:forEach items="${departList}" var="department" varStatus="vs">
		                            <OPTION value="${department.departmentID}" >${department.department}</OPTION>
								</c:forEach>
							</select>
						</td>
						<td >
							<label id="reportExport_string_branch">课别:</label>
						</td>
						<td >
						    <select name="branch" id="branch" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:100px; padding: .4em;">
							   <option id="reportExport_string_branch_0" value="--请选择--" SELECTED>--请选择--</option>	
							</select>
						</td>
					</tr>
					<tr>
						<td >
							<label id="reportExport_string_startDate">开始日：</label>
						</td>
						<td >
							<select name="year" id="year" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:150px; padding: .4em;" onchange="initMonth()">
								<option id="reportExport_string_year_0" value="--请选择--" SELECTED>--请选择--</option>							
							</select>	
						</td>					
						<td >
							<label id="reportExport_string_startYear">年</label>
						</td>
						<td >
						<select name="month" id="month" class="text ui-widget-content ui-corner-all" 
						style="margin-bottom:12px; width:100px; padding: .4em;" onchange="calcExportPeriods()">
							<option id="reportExport_string_month_0" value="--请选择--" SELECTED>--请选择--</option>		
						</select>				
						</td>							
						<td >
							<label id="reportExport_string_startMonth">月</label>
						</td>
						<td >
							<select name="startday" id="startday" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;" onchange="calcExportPeriods()" >
								<option id="reportExport_string_startday_0" value="--请选择--" SELECTED>--请选择--</option>
								<option value="21">21</option>
								<option value="1">1</option>
								<option value="26">26</option>		
							</select>
						</td>
						<td>
							<label id="reportExport_string_startDay">日</label>
						</td>
					</tr>
					<tr>
						<td>
							<label id="reportExport_string_endDate">完了日：</label>
						</td>
						<td>
							<select name="endyear" id="endyear" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:150px; padding: .4em;" onchange="initendMonth()">
								<option id="reportExport_string_endyear_0" value="--请选择--" SELECTED>--请选择--</option>							
							</select>	
						</td>
						<td>
							<label id="reportExport_string_endYear">年</label>
						</td>
						<td>
						<select name="endmonth" id="endmonth" class="text ui-widget-content ui-corner-all" 
						style="margin-bottom:12px; width:100px; padding: .4em;" onchange="calcExportPeriods()">
							<option id="reportExport_string_endmonth_0" value="--请选择--" SELECTED>--请选择--</option>		
						</select>				
						</td>
						<td>
							<label id="reportExport_string_endMonth">月</label>
						</td>
					</tr>
					<tr>							
						<td>
							<label id="reportExport_string_exporttime">导出期间：</label>
						</td>
						<td colspan="2">
							<p align="left" style="margin-bottom:12px; width:auto; padding: .4em;" id="exportPeriods"></p>				
						</td>							
					</tr>
		        </table>
				<div style="margin-left:50px; margin-top:10px; position:absolute;">
					<div>
						<input type=button id="exportdepartmonth" value="部门/课别实绩集计"/>
						<br>
					</div>
				</div>
				<div style="margin-left:250px; margin-top:10px; position:absolute;">
					<div>
						<input type=button id="exportPersonsWorkNum" value="个人实绩集计"/>
						<br>
					</div>
				</div>
				<div style="margin-top:50px; position:absolute;">
					<table style="width:700px; padding:5px;">
						<tr>
							<td><p id="reportExport_string_cxee_csv" style="text-align:left;font-size:16px;font-weight:bold;width:65px">CSV报表</p></td>
							<td><input type=button id="exportCXEECSV" value="导出"/></td>
						</tr>
						<tr>
							<td>
								<label id="label_belong" style="color:black">归属:</label>
							</td>
							<td>
								<select name="selectname_belong" id="select_belong"  class="text ui-widget-content ui-corner-all"
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeBelong();" >
									<option id="reportExport_string_belong_0" value="--请选择--" SELECTED>--请选择--</option>	
									<option value="1" >CT</option>
									<option value="2" >CHI</option>
								</select>				
							</td>
							<td colspan=2>
								<label id="reportExport_string_department">部门:</label>
							</td>
							<td colspan=3>
							    <select name="department_cxee_csv" id="department_cxee_csv" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeListCSV()">
									<option id="reportExport_string_department_01" value="--请选择--" SELECTED>--请选择--</option>	
									<option id="reportExport_string_department_1" value="公司整体" >公司整体</option>
									<option id="reportExport_string_department_2" value="開発部門" >開発部門</option>
									<option id="reportExport_string_department_3" value="開発以外" >開発以外</option>	
									<c:forEach items="${departList}" var="department" varStatus="vs">
			                            <OPTION value="${department.departmentID}" >${department.department}</OPTION>
									</c:forEach>
								</select>
							    <select name="department_cxee_csv1" id="department_cxee_csv1" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;" onChange="changeListCSV1()">
									<option id="reportExport_string_department_02" value="--请选择--" SELECTED>--请选择--</option>	
									<c:forEach items="${departList_ForCt}" var="department_ForCt" varStatus="vs">
			                            <OPTION value="${department_ForCt.departmentID}" >${department_ForCt.department}</OPTION>
									</c:forEach>
								</select>
							</td>
							<td><p id="reportExport_string_branch" style="width:30px">课别:</p>
							</td>
							<td >
							    <select name="branch_cxee_csv" id="branch_cxee_csv" class="text ui-widget-content ui-corner-all" 
									style="margin-bottom:12px; width:100px; padding: .4em;">
								   <option id="reportExport_string_branch_0_1" value="--请选择--" SELECTED>--请选择--</option>	
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<label id="reportExport_string_time_cxee_csv">期间：</label>
							</td>
							<td >
								<select name="year_cxee_csv" id="year_cxee_csv" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:100px; padding: .4em;" onchange="initMonthCSV()">
									<option id="reportExport_string_year_0_1" value="--请选择--" SELECTED>--请选择--</option>							
								</select>	
							</td>					
							<td >
								<label id="reportExport_string_startYear">年</label>
							</td>
							<td >
							<select name="month_cxee_csv" id="month_cxee_csv" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;" >
								<option id="reportExport_string_month_0_1" value="--请选择--" SELECTED>--请选择--</option>		
							</select>				
							</td>							
							<td >
								<label id="reportExport_string_startMonth">月</label>
							</td>
							<td>
								<label >～</label>
							</td>
							<td>
								<select name="endyear_cxee_csv" id="endyear_cxee_csv" class="text ui-widget-content ui-corner-all" 
								style="margin-bottom:12px; width:100px; padding: .4em;" onchange="initendMonthCSV()">
									<option id="reportExport_string_endyear_0_1" value="--请选择--" SELECTED>--请选择--</option>							
								</select>	
							</td>
							<td>
								<label id="reportExport_string_endYear">年</label>
							</td>
							<td>
							<select name="endmonth_cxee_csv" id="endmonth_cxee_csv" class="text ui-widget-content ui-corner-all" 
							style="margin-bottom:12px; width:100px; padding: .4em;">
								<option id="reportExport_string_endmonth_0_1" value="--请选择--" SELECTED>--请选择--</option>		
							</select>				
							</td>
							<td>
								<label id="reportExport_string_endMonth">月</label>
							</td>
						</tr>
			        </table>
	        	</div>
	        </div>
        </div>
<!-- 		<div style="margin-left:50px; margin-top:80px; position:absolute;">
			<div>
				<tr >
			       <p align="left"><label id="reportExport_string_memo1">管理员友情提醒：</label></p>
			       <p align="left"><label id="reportExport_string_memo2">1、个人集记导出功能需要耗费较多的时间，请谨慎使用。</label></p>
			       <p align="left"><label id="reportExport_string_memo3">2、逻辑上，报表生成模块只允许导出某一年的4月份至次年的3月份之间的某个时间段的工数数据。</label></p>
		        </tr>
			</div>
		</div> -->
		</form>
	</body>
</html>
