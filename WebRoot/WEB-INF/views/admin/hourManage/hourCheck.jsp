<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
    <link rel="stylesheet" type="text/css" href="css/flexigrid.pack.css" />
    <script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
    <script type="text/javascript" src="js/Flexigrid/flexigrid.pack.js"></script>
    <script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
    <script language="javascript" type="text/javascript" src="js/datePicker/WdatePicker.js"></script>
    
    <script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="js/js_user/com.js"></script>
	<script type="text/javascript" src="js/cookie/cookie.js"></script>
    
    <style type="text/css">
       body,table{
         font-size:13px;
         background:#fff
       }
    </style>

<script type="text/javascript">

var language = getCookieValue("language");
var tblstaffName="";
var tblstaffDepartment="";
var tblstaffBranch="";
var tblemail="";
var tblsuperior="";
var tblsuperiorEmail="";
var tbldate="";
var tbltotalTimes="";
var tblpagestat="";
var tbltitle="";
var rowcategory="";
var rowprojectName="";
var E00021="";
var E00022="";
var E00023="";
var E00020="";
var I0006= "";
var I0007= "";
var I0013= "";
var I0014= "";
var I0004= "";
var I0005= "";
var kaisya = "${staff.kaisya}";
//国际化
function setPageByLanguage(){
	document.title = $.i18n.prop('hourCheck_string_title');
	
	tblstaffName=$.i18n.prop('hourCheck_string_tblstaffName');
	tblstaffDepartment=$.i18n.prop('hourCheck_string_tblstaffDepartment');
	tblstaffBranch=$.i18n.prop('hourCheck_string_tblstaffBranch');
	tblemail=$.i18n.prop('hourCheck_string_tblemail');
	tblsuperior=$.i18n.prop('hourCheck_string_tblsuperior');
	tblsuperiorEmail=$.i18n.prop('hourCheck_string_tblsuperiorEmail');
	tbldate=$.i18n.prop('hourCheck_string_tbldate');
	tbltotalTimes=$.i18n.prop('hourCheck_string_tbltotalTimes');
	
	tblpagestat=$.i18n.prop('hourCheck_string_tblpagestat');
	tbltitle=$.i18n.prop('hourCheck_string_tbltitle');

	E00021=$.i18n.prop('E00021');
	E00022=$.i18n.prop('E00022');
	E00023=$.i18n.prop('E00023');
	I0006= $.i18n.prop('I0006');
	I0013= $.i18n.prop('I0013');
	I0014= $.i18n.prop('I0014');
	I0007= $.i18n.prop('I0007');
	I0004= $.i18n.prop('I0004');
	I0005= $.i18n.prop('I0005');
	
	$('#hourCheck_string_startDate').html($.i18n.prop('hourCheck_string_startDate'));
	$('#hourCheck_string_endDate').html($.i18n.prop('hourCheck_string_endDate'));
	$('#hourCheck_string_department').html($.i18n.prop('hourCheck_string_department'));
	$('#hourCheck_string_select1').html($.i18n.prop('hourCheck_string_select'));
	$('#hourCheck_string_select2').html($.i18n.prop('hourCheck_string_select'));
	$('#hourCheck_string_branch').html($.i18n.prop('hourCheck_string_branch'));
	$('#hourCheck_string_name').html($.i18n.prop('hourCheck_string_name'));
	//$('#hourCheck_string_serachSuperior').html($.i18n.prop('hourCheck_string_serachSuperior'));
	$('#query').val($.i18n.prop('hourCheck_string_query'));
	$('#sendMail').val($.i18n.prop('hourCheck_string_sendMail'));
	$('#outPut').val($.i18n.prop('hourCheck_string_outPut'));
	//alert(kaisya);
	if (kaisya =="CXEE") {
		$('#serachSuperiorTD').append('<label id="hourCheck_string_serachSuperior">'+$.i18n.prop('hourCheck_string_serachSuperior')+'</label>'+'<input type="text" id="serachSuperior" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>');
	}
	    
}

$(document).ready(function() {
	
	if (language!="CN" && language!="JP" && language!="EN"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage);

	if ($("#DepartmentFlag").val() == "0") {
		$("#department").attr("disabled", "disabled");
		var departmentId = "${staff.departmentID}";
		$("#department").find("option[value="+departmentId+"]").attr("selected",true);
		changeList();
	}

    /**
     * add event dialog
     */
    var myDate     = new Date();
    var year       = myDate.getFullYear();
    var startYear  = year;
    var startMonth = myDate.getMonth();
    var endMonth   = startMonth+1;
    if (startMonth < 10) {
    	if (startMonth == 0 ) {
    		startMonth = "12"
    		startYear = year - 1;
    	} else {
        	startMonth = "0" + startMonth;
    	}
    }
    if (endMonth < 10) {
    	endMonth = "0" + endMonth;
    }
    
    var start      = startYear + "-" + startMonth + "-21";
    var end        = year + "-" + endMonth + "-20";
    $("#startDate").val(start);
    $("#endDate").val(end);

    $("#query").click(function() {
    	var QueryRoleFlag = $("#QueryRoleFlag").val();
    	if(QueryRoleFlag != 1){
    		return "";
    	}else{
	        var startDate      = $("#startDate").val();
	        var endDate        = $("#endDate").val();
	        var departmentID   = $("#department").val();
	        var branchID       = $("#branch").val();
	        var name           = $("#name").val();
	        var serachSuperior = $("#serachSuperior").val();
	
	        if (startDate == "") {
	            alert(E00021);
	            $("#startDate").focus();
	            return false;
	        }
	        if (endDate == "") {
	            alert(E00022);
	            $("#endDate").focus();
	            return false;
	        }
	        if (startDate > endDate) {
	            alert(E00023);
	            $("#endDate").focus();
	            return false;
	        }
	        $("#gridTable").find("tbody").children().remove();
	        var params = [{"name" : "startDate", "value" : startDate}
	                     ,{"name" : "endDate","value" : endDate}
	                     ,{"name" : "departmentID","value" : departmentID}
	                     ,{"name" : "branchID","value" : branchID}
	                     ,{"name" : "name","value" : name}
	                     ,{"name" : "serachSuperior","value" : serachSuperior}
	                     ,{"name" : "kaisya","value" : kaisya}
	                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
	                     ];
	        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
    	}
    });

    $("#sendMail").click(function() {
        var startDate = $("#startDate").val();
        var endDate   = $("#endDate").val();
        if (startDate == "") {
            alert(E00021);
            $("#startDate").focus();
            return false;
        }
        if (endDate == "") {
            alert(E00022);
            $("#endDate").focus();
            return false;
        }
        if (startDate > endDate) {
            alert(E00023);
            $("#endDate").focus();
            return false;
        }
        
        if (confirm(I0013)) {
            var path = "hourCheck/sendMail.do?startDate="+startDate+"&endDate="+endDate+"&kaisya="+kaisya;
            var dg = new $.dialog({
                title:'sendMail',
                id:'user_download',
                width:400,
                height:400,
                iconTitle:false,
                cover:true,
                maxBtn:false,
                xButton:true,
                resize:false,
                loadingText:I0014,
                page:path
            });
            dg.ShowDialog();
        }
    });

    $("#outPut").click(function() {
    	
    	var dataCount=$("#gridTable tr").length;
    	if (dataCount==0){
    		return false;
    	}
    	
        var startDate = $("#startDate").val();
        var endDate   = $("#endDate").val();
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
        if (startDate > endDate) {
            alert(E00023);
            $("#endDate").focus();
            return false;
        }
        if (confirm(I0006)) {
        	
            var path = "hourCheck/outPut.do?startDate="+startDate+"&endDate="+endDate+"&kaisya="+kaisya;
            var dg   = new $.dialog({
                title:'outPut',
                id:'user_download',
                width:400,
                height:400,
                iconTitle:false,
                cover:true,
                maxBtn:false,
                xButton:true,
                resize:false,
                loadingText:I0007,
                page:path
            });
            dg.ShowDialog();
        }
    });
    if (kaisya =="CXEE") {
	    $("#gridTable").flexigrid({
	        url : 'hourCheck/checkerror.do',
	        dataType : 'json',
	        colModel : [
	        {   display : 'staffID',
	            name : 'staffID',
	            width : 1,
	            sortable : false,
	            align : 'left',
	            hide : true
	        },
	        {   display : tblstaffName,
	            name : 'staffName',
	            id:'staffName',
	            width : 100,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tblstaffDepartment,
	            name : 'staffDepartment',
	            width : 150,
	            sortable : false,
	            align : 'center'
	        },
	        {   display : tblstaffBranch,
	            name : 'staffBranch',
	            width : 150,
	            sortable : false,
	            align : 'center'
	        },
	        {   display : tblemail,
	            name : 'staffEmail',
	            width : 200,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tblsuperior,
	            name : 'superior',
	            width : 100,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tblsuperiorEmail,
	            name : 'superiorEmail',
	            width : 200,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tbldate,
	            name : 'date',
	            width : 100,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tbltotalTimes,
	            name : 'totalTimes',
	            width : 100,
	            sortable : false,
	            align : 'center'
	        }],
	        useRp: true,
	        pagestat : tblpagestat,
	        sortname : "name",
	        title: tbltitle,
	        method : 'POST',
	        showToggleBtn : false,
	        autoload : false, 
	        sortorder : "asc",
	        usepager : true,
	        useRp : true,
	        nomsg : I0004,
	        rp : 10,
	        showTableToggleBtn : true,
	        procmsg : I0005,
	        width : 1400,
	        height : 'auto'
	    });
    } else {
    	$("#gridTable").flexigrid({
	        url : 'hourCheck/checkerror.do',
	        dataType : 'json',
	        colModel : [
	        {   display : 'staffID',
	            name : 'staffID',
	            width : 1,
	            sortable : false,
	            align : 'left',
	            hide : true
	        },
	        {   display : tblstaffName,
	            name : 'staffName',
	            id:'staffName',
	            width : 150,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tblstaffDepartment,
	            name : 'staffDepartment',
	            width : 150,
	            sortable : false,
	            align : 'center'
	        },
	        {   display : tblstaffBranch,
	            name : 'staffBranch',
	            width : 200,
	            sortable : false,
	            align : 'center'
	        },
	        {   display : tblemail,
	            name : 'staffEmail',
	            width : 200,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tbldate,
	            name : 'date',
	            width : 100,
	            sortable : false,
	            align : 'left'
	        },
	        {   display : tbltotalTimes,
	            name : 'totalTimes',
	            width : 200,
	            sortable : false,
	            align : 'center'
	        }],
	        useRp: true,
	        pagestat : tblpagestat,
	        sortname : "name",
	        title: tbltitle,
	        method : 'POST',
	        showToggleBtn : false,
	        autoload : false, 
	        sortorder : "asc",
	        usepager : true,
	        useRp : true,
	        nomsg : I0004,
	        rp : 10,
	        showTableToggleBtn : true,
	        procmsg : I0005,
	        width : 1050,
	        height : 'auto',
	        onSuccess : function (a){ if (a.success){alert(123);}}
	    });  	
    }
});

//部门变更时候，改变课别的选项
function changeList(){
    var departmentID = $("#department").val();
    
    if (kaisya =="CXEE") {
        $.ajax({
            type: "post",
            url: "output/branchselect.do",
            dataType: "json",
            data:{"departmentID":departmentID},
            success: function (result) {
                var e=document.getElementById("branch");
                e.options.length=1; //清除下拉框数据
                //$("#branch").empty();
                $.each(result.result,function(index,content) {
                    e.options.add(new Option(content.branch, content.branchID));
                });
            }
        });
    }else{
        $.ajax({
            type: "post",
            url: "output/branchselect_ForCt.do",
            dataType: "json",
            data:{"departmentID":departmentID},
            success: function (result) {
                var e=document.getElementById("branch");
                e.options.length=1; //清除下拉框数据
                //$("#branch").empty();
                $.each(result.result,function(index,content) {
                    e.options.add(new Option(content.branch, content.branchID));
                });
            }
        });
    };
    
    

}
</script>

</head>
<body>
<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
<input type="hidden" id ="DownloadRoleFlag" value = "${DownloadRoleFlag}">
<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
<input type="hidden" id ="DepartmentFlag" value = "${DepartmentFlag}">
    <table id="detailTable">
        <tr>
            <td style="width:148px;">
                <label id="hourCheck_string_startDate">起始时间:</label>
                <input type="text" name="startDate" id="startDate"   onClick="WdatePicker()" value="${startDate}"
                       class="text ui-widget-content ui-corner-all" style="margin-bottom:5px; width:66px; padding: .2em;"/>
            </td>
            <td style="width:148px;">
                <label id="hourCheck_string_endDate">截止时间:</label>
                <input type="text" name="endDate" id="endDate"  onClick="WdatePicker()" value="${endDate}"
                       class="text ui-widget-content ui-corner-all" style="margin-bottom:5px; width:66px; padding: .2em;"/>
            </td>
            <td style="width:240px;">
                <label id="hourCheck_string_department">部门名称:</label>
                <select name="department" id="department" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:168px; padding: .2em;" onChange="changeList()">
                    <option id="hourCheck_string_select1" value="--请选择--" SELECTED>--请选择--</option>
                    <c:forEach items="${departList}" var="department" varStatus="vs">
                    <option value="${department.departmentID}" >${department.department}</OPTION>
                    </c:forEach>
                </select>
            </td>
            <td style="width:198px;">
                <label id="hourCheck_string_branch">课别名称:</label>
                <select name="branch" id="branch" class="text ui-widget-content ui-corner-all"
                    style="margin-bottom:5px; width:128px; padding: .2em;">
                    <option id="hourCheck_string_select2" value="--请选择--" SELECTED>--请选择--</option>
                </select>
            </td>
            <td style="width:128px;">
                <label id="hourCheck_string_name">姓名:</label>
			    <input type="text" id="name" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>
			</td>
            <td id="serachSuperiorTD" style="width:128px;">
             <!--   <label id="hourCheck_string_serachSuperior">上司:</label>
			    <input type="text" id="serachSuperior" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/> -->
			</td>
	    </tr>
	    <tr>
			<td colspan="6">
	            <c:if test="${QueryRoleFlag == '1'}">
				<c:if test="${staff.email.endsWith('.co.jp') && SpecialRole1Flag == '1' || !staff.email.endsWith('.co.jp') && SpecialRole2Flag == '1'}">
                <input id="query" type="button" value="查询">
                </c:if>
                </c:if>
                <c:if test="${staff.URKeyCode == 'UR000000001'}">
                <input id="sendMail" type="button" value="发送通知邮件">
                </c:if>
	            <c:if test="${DownloadRoleFlag == '1'}">
					<c:if test="${staff.email.endsWith('.co.jp') && SpecialRole1Flag == '1' || !staff.email.endsWith('.co.jp') && SpecialRole2Flag == '1'}">
		                <input id="outPut" type="button" value="导出黑名单"/>
	                </c:if>
                </c:if>
            </td>
        </tr>
    </table>

<table id="gridTable"></table>
</body>
</html>
