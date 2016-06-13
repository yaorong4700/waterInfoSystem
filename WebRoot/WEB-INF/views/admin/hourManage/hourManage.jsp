<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工数查询</title>
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
	
	var now = new Date()
	var years = now.getFullYear()
	var months = now.getMonth()//月份对应（0-11）
	
	var language = getCookieValue("language");
	var tblcategory ="";
	var tblprojectName="";
	var tbltaskName="";
	var tbltotalTimes="";
	var tblpagestat="";
	var tbltitle="";
	var rowcategory="";
	var rowprojectName="";
	var E00017="";
	var E00018="";
	var E00019="";
	var E00020="";
	var I0004= "";
	var I0005= "";
	
	//国际化
	function setPageByLanguage(){
		
		document.title = $.i18n.prop('hourManage_string_title');
		
		tblcategory =$.i18n.prop('hourManage_string_tblcategory');
		tblprojectName=$.i18n.prop('hourManage_string_tblprojectName');
		tbltaskName=$.i18n.prop('hourManage_string_tbltaskName');
		tbltotalTimes=$.i18n.prop('hourManage_string_tbltotalTimes');
		tblpagestat=$.i18n.prop('hourManage_string_tblpagestat');
		tbltitle=$.i18n.prop('hourManage_string_tbltitle');
		rowcategory=$.i18n.prop('hourManage_string_rowcategory');
		rowprojectName=$.i18n.prop('hourManage_string_rowprojectName');
		E00017=$.i18n.prop('E00017');
		E00018=$.i18n.prop('E00018');
		E00019=$.i18n.prop('E00019');
		E00020=$.i18n.prop('E00020');
		I0004= $.i18n.prop('I0004');
		I0005= $.i18n.prop('I0005');
		
		$('#hourManage_string_startYM').html($.i18n.prop('hourManage_string_startYM'));
		$('#hourManage_string_staryear').html($.i18n.prop('hourManage_string_staryear'));
		$('#hourManage_string_starmonth').html($.i18n.prop('hourManage_string_starmonth'));
		$('#hourManage_string_endYM').html($.i18n.prop('hourManage_string_endYM'));
		$('#hourManage_string_endyear').html($.i18n.prop('hourManage_string_endyear'));
		$('#hourManage_string_endmonth').html($.i18n.prop('hourManage_string_endmonth'));
		$('#hourManage_string_select').html($.i18n.prop('hourManage_string_select'));
		$('#hourManage_string_allPJ').html($.i18n.prop('hourManage_string_allPJ'));
		$('#query').val($.i18n.prop('hourManage_string_query'));
		$('#hourManage_string_attention').html($.i18n.prop('hourManage_string_attention'));
	}
	
function init()
{
	var select = document.getElementById('startMonth');			
	select.options.length=1;	//清除下拉框数据	
	var year=document.getElementById("startYear").value;
	//var month=document.getElementById("startMonth").value;
	if(year >0 && year <years)
	{
	for(var i=1;i<=12;i++)
	{
		document.getElementById("startMonth").options[i]=new Option(i,i);
		
	}
	}
	else 
	{
	for(var i=1;i<=months+2;i++)
	{
		if(i>12)
		{
			i=12;
			document.getElementById("startMonth").options[i]=new Option(i,i);
			break;
		}
		document.getElementById("startMonth").options[i]=new Option(i,i);
	}	
	}
	
}
function initEndYear()
{
	var select = document.getElementById('endYear');			
	select.options.length=1;	//清除下拉框数据
	var year=document.getElementById("startYear").value;
	if(year >0 && year <years)
	{
	for(var i=year-2011;i<=years-2011;i++)
	{
		document.getElementById("endYear").options[i]=new Option(i+2011,i+2011);
	}
	}
	else if(year == years)
	{
	document.getElementById("endYear").options[1]=new Option(years,years);	
	}
	else
	{
	}
}
function initEndMonth()
{
	var select = document.getElementById('endMonth');			
	select.options.length=1;	//清除下拉框数据
	var vStartYear=document.getElementById("startYear").value;
	var vEndYear=document.getElementById("endYear").value;
	var vStartMonth=document.getElementById("startMonth").value;

	if(vEndYear == vStartYear && vEndYear != years)
	{
	var j=1;
	for(var i=vStartMonth;i<=12;i++)
	{
		document.getElementById("endMonth").options[j]=new Option(i,i);//Option(文本，值)
		j=j+1;
	}
	}
	else if(vEndYear ==vStartYear && vEndYear == years)
	{
	var j=1;
		for(var i=vStartMonth;i<=months+2;i++)
		{
			if(i>12)
			{
				i=12;
				break;
			}
			document.getElementById("endMonth").options[j]=new Option(i,i);
			j=j+1;
		}
	}
	else if(vEndYear > vStartYear && vEndYear == years)
	{
		for(var i=1;i<=months+2;i++)
		{
			if(i>12)
			{
				i=12;
				break;
			}
			document.getElementById("endMonth").options[i]=new Option(i,i);
		}
	}
	else if(vEndYear > vStartYear && vEndYear != years)
	{
	for(var i=1;i<=12;i++)
	{
		document.getElementById("endMonth").options[i]=new Option(i,i);
	}
	}
	else
	{}
}
	$(document).ready(function(){	
		/**
	 * add event dialog
	 */
	
	var now = new Date()
	var years = now.getFullYear()
	var months = now.getMonth()//月份对应（0-11）
	
	if (language!="CN" && language!="JP"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage);
	
	for(var i=2013;i<=years;i++)
	{
		document.getElementById("startYear").options[i-2012]=new Option(i,i);	
	}
	
	
	$("#query").click(function() { 
	           var startYear  = $("#startYear").val();
	           var startMonth  = $("#startMonth").val();
	           var endYear  = $("#endYear").val();
	           var endMonth = $("#endMonth").val();
               if ($("#startYear").val() =="-------") {
   	   		       alert(E00017);
   	   			   $("#startYear").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#startMonth").val() == "---") {
   	   		       alert(E00018);
   	   			   $("#startMonth").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#endYear").val() =="-------") {
   	   		       alert(E00019);
   	   			   $("#endYear").focus();
   	   			   return false;
   	   		   }
   	   		   if ($("#endMonth").val() == "---") {
   	   		       alert(E00020);
   	   			   $("#endMonth").focus();
   	   			   return false;
   	   		   }
            var params = [{"name" : "startYear", "value" : $("#startYear").val()},
            			  {"name" : "startMonth","value" : $("#startMonth").val()},
            			  {"name" : "endYear", "value" : $("#endYear").val()},
            			  {"name" : "endMonth", "value" : $("#endMonth").val()}];  
            $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
   	   		  });
   	   		  //查询所有工数
   	   		  $("#queryAll").click(function() { 
              var params = [{"name" : "startDate", "value" :"all" },
              				{"name" : "endDate", "value" :"query"}];  
            $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
   	   		  });
	$("#gridTable").flexigrid({
			url : 'hourManage/personalQuery.do',
			dataType : 'json',
			colModel : [ 
			 {
				display : 'id',
				name : 'id',
			
				width : 1,
				sortable : false,
				align : 'left',
				hide : true
			}, 
			  {
				display : tblcategory,
				name : 'category',
				width : 100,
				sortable : true,
				align : 'left'
			}, {
				display : tblprojectName,
				name : 'projectName',
				width : 350,
				sortable : true,
				align : 'left'
			},{
				display : tbltaskName,
				name : 'taskName',
				width : 250,
				sortable : true,
				align : 'left'
			}, {
				display : tbltotalTimes,
				name : 'totalTimes',
				width : 50,
				sortable : false,
				align : 'left'
			} ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "name",
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
			searchitems : [ {display : rowcategory,name : 'category',isdefault : true},{display : rowprojectName,name : 'projectName'} ],
			width : 800,
			height : 'auto'
				});
			}); 
			
	</script>
</head>
<body>
	<form id="form1" name="form1" method="post" >
		<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
  		<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
   	 	<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
		<tr>
			<td colspan="2" style="height: 40px">
				<label>   </label>
				<p style="width: 471px; color:#000000">
				<label id="hourManage_string_startYM">起始月份</label>
				<select id="startYear" onchange="init()">
				<option value="-------" selected>-------</option>
				</select>
				<label id="hourManage_string_staryear">年</label>
				<select id="startMonth" onchange="initEndYear()">
				<option value="---" selected>---</option>
				</select>
				<label id="hourManage_string_starmonth">月</label>
				
				<label id="hourManage_string_endYM">截止月份</label>
				<select id="endYear" onchange="initEndMonth()">
				<option value="-------" selected>-------</option>
				</select>
				<label id="hourManage_string_endyear">年</label>
				<select id="endMonth" >
				<option value="---" selected>---</option>
				</select>
				<label id="hourManage_string_endmonth">月</label>
				</p>
      
				<p style="width: 567px;color:#000000" >
					<label id="hourManage_string_select">
					选择查询选项：
					</label>
					<input id="AllProjectList" type="radio" name="QuerySelect" value="AllProjectList"  checked/><label id="hourManage_string_allPJ">所有项目</label>	  
      
	            <c:if test="${QueryRoleFlag == '1'}">
				<c:if test="${staff.email.endsWith('.co.jp') && SpecialRole1Flag == '1' || !staff.email.endsWith('.co.jp') && SpecialRole2Flag == '1'}">
					<input id="query" type="button"   value="查询">
	            </c:if>
	            </c:if>
					</p>
			</td>
        </tr>
    
    </form>	
	<table id="gridTable"></table>
<label id="hourManage_string_attention">注意：初始默认显示为当前日期的月工数统计！</label>

</body>
</html>