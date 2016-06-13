<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门信息上传界面</title>
<link type="text/css" rel="stylesheet" href="../css/main.css" />
<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>

<script type="text/javascript"
	src="../js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js/js_user/com.js"></script>
<script type="text/javascript" src="../js/cookie/cookie.js"></script>


<style type="text/css">
body, table {
	font-size: 13px;
}

.clear {
	clear: both;
}

.ehdel_upload_show input {
	float: left;
	margin-left: 10px;
}

.ehdel_upload {
	float: left;
	margin-top: -20px;
	*margin-top: -40px;
	filter: alpha(opacity = 0);
	-moz-opacity: 0;
	opacity: 0;
	width: 300px
}
</style>
<script type="text/javascript">

var language = getCookieValue("language");
var E00012 ="";
var E00013 ="";
var E00014 ="";
var E00015 ="";
var E00016 ="";
var I0010 ="";
var I0011 ="";
var I0012 ="";
var staffUpload_string_msg_1 ="";
var staffUpload_string_msg_2 ="";
var staffUpload_string_msg_3 ="";
var staffUpload_string_msg_4 ="";
var staffUpload_string_msg_5 ="";
var staffUpload_string_msg_6 ="";

$(document).ready(function() {
	
	if (language!="CN" && language!="JP" && language!="EN"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage,"../");	
	
    $('#xlsForm').ajaxForm({
        url:'fileUpload.do',
        type:'post',
        dataType: 'text',
        success:function (data){
            if("NOSHEET" == data){
                alert(E00012);
                return;
            }
            if("ERROR" == data){
                alert(E00013);
                return;
            }
            var obj = eval("("+data+")");
            if(obj.code == "S"){
                $("#msg").html("<font color='black'>"+I0010+obj.successRowCount+I0011+"</font>");
            }else{
                $("#msg").html("<font color='red'>"+staffUpload_string_msg_1+obj.successRowCount+staffUpload_string_msg_2+obj.ngRowCount+staffUpload_string_msg_3+"</font>");
                for(var i = 0; i< obj.result.length; i++){
                    if (i == 0){
                         $("#gridTable").append('<tr align="center" bgcolor="#FFFFFF"><td width="60" bgcolor="#FFFFFF">'+staffUpload_string_msg_4+'</td><td width="100" bgcolor="#FFFFFF">'+staffUpload_string_msg_5+'</td><td bgcolor="#FFFFFF">'+staffUpload_string_msg_6+'</td></tr>');
                    }
                    $("#gridTable").append('<tr align="center" bgcolor="#FFFFFF" ><td>'+obj.result[i].errNo+'</td><td  bgcolor="#FFFFFF">'+obj.result[i].errItem+'</td><td  bgcolor="#FFFFFF">'+ obj.result[i].errContent +"</td></tr>");
                }
            }
        },
        error:function(data){
            alert(E00014);
        }
    });
});

function dosubmit(){
    var fileName = $('#fileInfo').val();

    $("#msg").html("");
    $("#gridTable").html("");
    if('' == fileName) {
        alert(E00015);
        return;
    }
    var bingIndex = fileName.lastIndexOf('.');
    if(-1 == bingIndex) {
        alert(E00016);
        return;
    }
    var suffix = fileName.substring(bingIndex + 1, fileName.length);
    if('xls' == suffix) {
        $("#msg").html(I0012);
        $('#xlsForm').submit();
    } else {
        alert(E00016);
    }
}

function setPageByLanguage(){
	document.title = $.i18n.prop('staffUpload_string_title');
	$('#staffUpload_string_title_1').html($.i18n.prop('staffUpload_string_title_1'));
	$('#staffUpload_string_title_2').html($.i18n.prop('staffUpload_string_title_2'));
	$('#staffUpload_string_title_3').html($.i18n.prop('staffUpload_string_title_3'));
	$('#ehdel_upload_btn').val($.i18n.prop('staffUpload_string_button_1'));
	$('#ehdel_upload_xlsBut').val($.i18n.prop('staffUpload_string_button_2'));
	
	E00012 =$.i18n.prop('E00012');
	E00013 =$.i18n.prop('E00013');
	E00014 =$.i18n.prop('E00014');
	E00015 =$.i18n.prop('E00015');
	E00016 =$.i18n.prop('E00016');
	I0010 =$.i18n.prop('I0010');
	I0011 =$.i18n.prop('I0011');
	I0012 =$.i18n.prop('I0012');
	staffUpload_string_msg_1 =$.i18n.prop('staffUpload_string_msg_1');
	staffUpload_string_msg_2 =$.i18n.prop('staffUpload_string_msg_2');
	staffUpload_string_msg_3 =$.i18n.prop('staffUpload_string_msg_3');
	staffUpload_string_msg_4 =$.i18n.prop('staffUpload_string_msg_4');
	staffUpload_string_msg_5 =$.i18n.prop('staffUpload_string_msg_5');
	staffUpload_string_msg_6 =$.i18n.prop('staffUpload_string_msg_6');
}
</script>
</head>
<body>
	<form id="xlsForm" method="post" enctype="multipart/form-data">
		<div class="ehdel_upload_show">
			<p align="left" style="padding-left: 10px;">
				<label id="staffUpload_string_title_1">如果没有文件模板文件，请下载</label> <a
					id="staffUpload_string_title_2" target="_blank"
					href="../resource/Department_Template_CXEE.xls">Excel模板文件。</a>
			</p>
			<p align="left"
				style="padding-left: 10px; padding-bottom: 0px; border-bottom: 0px; margin-bottom: 0px">
				<label id="staffUpload_string_title_3">选择上传文件：</label>
			</p>
			<!-- <input id="ehdel_upload_text" type="text" name="txt"
				style="width: 250px;" /> <input id="ehdel_upload_btn"
				style="width: 50px; height: 22px" type="button" value="参照"> -->
			<input id="fileInfo" type="file" name="file"
			onchange="this.value" style="width: 350px; height: 20px" />
			<input id="ehdel_upload_xlsBut" style="width: 50px; height: 20px"
				type="button" value="导入" onclick="dosubmit()" />
		</div>
		<div class="clear"></div>
		<!-- <input id="fileInfo" type="file" name="file"
			onchange="ehdel_upload_text.value=this.value" class="ehdel_upload" /> -->
	</form>
	<p style="height: 5px color:#000000"></p>
	<div id="msg"></div>
	<p style="height: 3px color:#000000"></p>
	<div>
		<table id="gridTable" border="0" width="400" cellspacing="1"
			cellpadding="0" bgcolor="#dce9f9">
		</table>
	</div>
</body>
</html>
