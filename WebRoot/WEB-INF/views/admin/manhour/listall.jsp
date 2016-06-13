<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工数显示</title>
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
	var tblname="";
	var tbldate="";
	var tblcategory="";
	var tblprojectName="";
	var tbltask="";
	var tbltaskRate="";
	var tbltimes="";
	var tblinsertTime="";
    var tblpagestat="";
    var tbltitle="";
    var I0004="";
    var I0005="";
	
	function setPageByLanguage(){
		
		I0004 = $.i18n.prop('I0004');
		I0005 = $.i18n.prop('I0005');
		tblname=$.i18n.prop('download_string_tblname');
		tbldate=$.i18n.prop('download_string_tbldate');
		tblcategory=$.i18n.prop('download_string_tblcategory');
		tblprojectName=$.i18n.prop('download_string_tblprojectName');
		tbltask=$.i18n.prop('download_string_tbltask');
		tbltaskRate=$.i18n.prop('download_string_tbltaskRate');
		tbltimes=$.i18n.prop('download_string_tbltimes');
		tblinsertTime=$.i18n.prop('download_string_tblinsertTime');
		tblpagestat=$.i18n.prop('download_string_tblpagestat');
	    tbltitle=$.i18n.prop('download_string_tbltitle');
	}
	
	$(document).ready(function(){	
		
		if (language!="CN" && language!="JP"){
			language = "CN"; 
		}
		loadProperties(language,setPageByLanguage);
		/**
	 * add event dialog
	 */
	
	
	$("#gridTable").flexigrid({
			url : 'manhour/listAllManhour.do',
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
				display : tblname,
				name : 'name',
				id:'name',
				width : 50,
				sortable : false,
				align : 'center'
			}, {
				display : tbldate,
				name : 'date',
				width : 100,
				sortable : true,
				align : 'left'
			}, {
				display : tblcategory,
				name : 'category',
				width : 50,
				sortable : true,
				align : 'left'
			}, {
				display : tblprojectName,
				name : 'projectName',
				width : 500,
				sortable : true,
				align : 'left'
			} , {
				display : tbltask,
				name : 'task',
				width : 200,
				sortable : true,
				align : 'left'
			}, {
				display : tbltaskRate,
				name : 'taskRate',
				width : 75,
				sortable : false,
				align : 'left'
			}, {
				display :tbltimes,
				name : 'times',
				width : 50,
				sortable : false,
				align : 'left'
			} , {
				display : tblinsertTime,
				name : 'insertTime',
				width : 125,
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
			searchitems : [  {display : tblname,name : 'name',isdefault : true}, {display : tblcategory,name : 'category'},{display : tblprojectName,name : 'projectName'} ],
			width : 1400,
			height : 'auto'
		});
});

		
		
		/*自定义搜索框,后面改用框架自带搜索框 
		function search(){
            var params = [{"name" : "staffName", "value" : $("#staffName").val()}];  
            $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
        }
		*/
	</script>
</head>
<body>
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
<table id="gridTable"></table>


</body>
</html>