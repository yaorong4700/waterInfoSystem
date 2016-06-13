<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>

    <link rel="stylesheet" type="text/css" href="../css/flexigrid.pack.css" />
    <script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
    <script type="text/javascript" src="../js/Flexigrid/flexigrid.pack.js"></script>
    <script type="text/javascript" src="../js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
    <script language="javascript" type="text/javascript" src="../js/datePicker/WdatePicker.js"></script>
    
    <script type="text/javascript" src="../js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="../js/js_user/com.js"></script>
	<script type="text/javascript" src="../js/cookie/cookie.js"></script>
    
    <style type="text/css">
       body,table{
         font-size:13px;
       }
    </style>
    <script type="text/javascript">
    	var language = getCookieValue("language");
    	var E00021 = "";
   	 	var E00022 = "";
   	    var E00023 = "";
   		var E00031 = "";
   		var E00037 = "";
   		var E00038 = "";
   		var I0004 = "";
   	    var I0005 = "";
   		var staffID = "";
   		var staffName = "";
   		var department = "";
   		var branch = "";
   		var new_branch = "";
   		var email = "";
   		var totalTimes = "";
   		var devTime = "";
   		var otherTime = "";
   	 	var pagestat = "";
	    var tbltitle = "";
    	var select = "";
    	//国际化
   		function setPageByLanguage(){
   			
   			document.title = $.i18n.prop('hourManagerByProject_string_title');
   			staffID =$.i18n.prop('hourManagerByProject_string_staffID');
   			staffName =$.i18n.prop('hourManagerByProject_string_staffName');
   			department =$.i18n.prop('hourManagerByProject_string_department');
   			branch =$.i18n.prop('hourManagerByProject_string_branch');
   			new_branch =$.i18n.prop('hourManagerByProject_string_new_branch');
   			email =$.i18n.prop('hourManagerByProject_string_email');
   			totalTimes =$.i18n.prop('hourManagerByProject_string_totalTimes');
   			devTime =$.i18n.prop('hourManagerByProject_string_devTime');
   			otherTime =$.i18n.prop('hourManagerByProject_string_otherTime');
   			pagestat =$.i18n.prop('hourManagerByProject_string_pagestat');
   			tbltitle =$.i18n.prop('hourManagerByProject_string_tbltitle');

   			E00021 = $.i18n.prop('E00021');
   	   	 	E00022 = $.i18n.prop('E00022');
   	   	    E00023 = $.i18n.prop('E00023');
   	   	    E00031 = $.i18n.prop('E00031');
   	   		E00037 = $.i18n.prop('E00037');
   	 		E00038 = $.i18n.prop('E00038');
   	   	    I0004 = $.i18n.prop('I0004');
   	   	    I0005 = $.i18n.prop('I0005');
   			
   	   		$('#hourManagerByProject_string_startDate').html($.i18n.prop('hourManagerByProject_string_startDate'));
   	   		$('#hourManagerByProject_string_endDate').html($.i18n.prop('hourManagerByProject_string_endDate'));	
   	  	 	$('#hourManagerByProject_string_selecttitle').html($.i18n.prop('hourManagerByProject_string_selecttitle'));
   			$('#hourManagerByProject_string_departmenttitle').html($.i18n.prop('hourManagerByProject_string_departmenttitle'));
  	 		$('#hourManagerByProject_string_department_1').html($.i18n.prop('hourManagerByProject_string_department_1'));
   			$('#hourManagerByProject_string_category').html($.i18n.prop('hourManagerByProject_string_category'));
   			$('#hourManagerByProject_string_project').html($.i18n.prop('hourManagerByProject_string_project'));
   			$('#query').val($.i18n.prop('hourManagerByProject_string_button'));
   			select = $.i18n.prop('hourManagerByProject_string_select');
   			
   			$('#hourManagerByProject_string_department_0').html(select);
   			$('#hourManagerByProject_string_category_0').html(select);
   			$('#hourManagerByProject_string_project_0').html(select);
   		
   		}

        $(document).ready(function(){
        	
        	if (language!="CN" && language!="JP"){
        		language = "CN";
        	}
        	loadProperties(language,setPageByLanguage,"../");
        	
            /**
             * add event dialog
             */
            var myDate = new Date();
            var year = myDate.getFullYear();
            var startMonth = myDate.getMonth();
            var endMonth   = startMonth+1;
            if(startMonth<10){
                startMonth = "0"+startMonth;
            }
			if (endMonth < 10) {
			    endMonth   = "0" + endMonth;
			}
			var start      = year + "-" + startMonth + "-21";
            var end        = year + "-" + endMonth + "-20";
            $("#startDate").val(start);
            $("#endDate").val(end);

            $("#query").click(function() {
                var startDate  = $("#startDate").val();
                var endDate    = $("#endDate").val();
                var departmentID = $("#department").val();
                var project    = $("#project").val();
                var category   = $("#category").val();
                if (startDate =="") {
                    alert(E00021);
                    $("#startDate").focus();
                    return false;
                }
                if (endDate == "") {
                    alert(E00022);
                    $("#endDate").focus();
                    return false;
                }
                if (startDate > endDate)
                {
                    alert(E00023);
                    $("#endDate").focus();
                    return false;
                }
                if (departmentID == "null") {
                    alert(E00031);
                    $("#department").focus();
                    return false;
                }
                if (category == "null") {
                    alert(E00037);
                    $("#category").focus();
                    return false;
                }
                if (project == "null") {
                    alert(E00038);
                    $("#project").focus();
                    return false;
                }

                var params = [{"name" : "startDate", "value" : startDate}
                             ,{"name" : "endDate","value" : endDate}
                             ,{"name" : "departmentID", "value" : $("#department").val()}
                             ,{"name" : "name", "value" : $("#project").val()}
                             ,{"name" : "category", "value" : $("#category").val()}
                             ];
                $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
            });

            $("#gridTable").flexigrid({
                url : 'hourManagerByProject.do',
                dataType : 'json',
                colModel : [
                {
                    display : staffID,
                    name : 'staffID',
                    width : 1,
                    sortable : false,
                    align : 'left',
                    hide : true
                }, {
                    display : staffName,
                    name : 'staffName',
                    id:'staffName',
                    width : 88,
                    sortable : false,
                    align : 'center'
                }, {
                    display : department,
                    name : 'department',
                    id:'department',
                    width : 132,
                    sortable : false,
                    align : 'center'
                }, {
                    display : branch,
                    name : 'branch',
                    id:'branch',
                    width : 150,
                    sortable : false,
                    align : 'left',
                    hide : true
                }, {
                    display : new_branch,
                    name : 'new_branch',
                    id:'new_branch',
                    width : 150,
                    sortable : false,
                    align : 'left'
                }, {
                    display : email,
                    name : 'Email',
                    width : 500,
                    sortable : false,
                    align : 'left'
                },{
                    display : totalTimes,
                    name : 'totalTimes',
                    width : 100,
                    sortable : false,
                    align : 'center'
                },{
                    display : devTime,
                    name : 'devTime',
                    width : 100,
                    sortable : false,
                    align : 'center'
                },{
                    display : otherTime,
                    name : 'otherTime',
                    width : 100,
                    sortable : false,
                    align : 'center'
                }],
                useRp: true,
                pagestat : pagestat,
                sortname : "name",
                title: tbltitle,
                method : 'POST',
                showToggleBtn : false,
                autoload : false,
                sortorder : "asc",
                usepager : true,
                query: '',
                useRp : true,
                nomsg : I0004,
                rp : 15,
                showTableToggleBtn : true,
                procmsg : I0005,
                width : 1400,
                height : 'auto'
            });
        });

        function changeList(){
            var departmentID = $("#department").val();
            var category      = $("#category").val();
            $.ajax({
                type: "post",
                url: "selectProject.do",
                dataType: "json",
                data:{"departmentID":departmentID,"category":category},
                success: function (result) {
                    var e=document.getElementById("project");
                    e.options.length=1; //清除下拉框数据
                    $.each(result.result,function(index,content) {
                         e.options.add(new Option(content.projectName, content.projectName));
                        });
                }
            });
        }
    </script>
</head>
<body>
<form id="form1" name="form1" method="post" >
    <table id="detailTable">
        <tr>
            <td>
                <label id="hourManagerByProject_string_startDate" >起始时间:</label>
                <input type="text" name="startDate" id="startDate"   onClick="WdatePicker()" value="${startDate}"
                       class="text ui-widget-content ui-corner-all" style="margin-bottom:5px; width:86px; padding: .2em;"/>
                &nbsp;&nbsp;
                <label id="hourManagerByProject_string_endDate" >截止时间:</label>
                <input type="text" name="endDate" id="endDate"  onClick="WdatePicker()" value="${endDate}"
                       class="text ui-widget-content ui-corner-all" style="margin-bottom:5px; width:86px; padding: .2em;"/>
            </td>
        </tr>
        <tr>
            <td>
                <label id="hourManagerByProject_string_selecttitle" >选择查询选项：</label>&nbsp;&nbsp;<label id="hourManagerByProject_string_departmenttitle" >部门：</label>
                <select name="department" id="department" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:158px; padding: .2em;">
                    <option id="hourManagerByProject_string_department_0"  value="null" selected>--请选择--</option>
                    <option id="hourManagerByProject_string_department_1"  value="公司所有部门">公司所有部门</option>
                    <c:forEach items="${departList}" var="department" varStatus="vs">
                    <option value="${department.departmentID}">${department.department}</option>
                    </c:forEach>
                </select>
                <label id="hourManagerByProject_string_category" >项目类别：</label>
                <select name="category" id="category" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:128px; padding: .2em;" onChange="changeList()">
                    <option id="hourManagerByProject_string_category_0" value="null" selected>--请选择--</option>
                    <c:forEach items="${categoryList}" var="category" varStatus="vs">
                    <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
                <label id="hourManagerByProject_string_project" >项目名：</label>
                <select name="project" id="project" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:346px; padding: .2em;">
                    <option id="hourManagerByProject_string_project_0"  value="null" selected>--请选择--</option>
                </select>
                <input id="query" type="button" value="查询">
            </td>
        </tr>
    </table>
</form>
<table id="gridTable"></table>

</body>
</html>
