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
		var I0018 = "";
		function setPageByLanguage(){
			
			I0018 = $.i18n.prop('I0018');
			$('#deadline_string_endtime').html($.i18n.prop('deadline_string_endtime'));
			$('#deadline_string_date3').html($.i18n.prop('deadline_string_date3'));
			$('#endtimebutton').val($.i18n.prop('deadline_string_endtimebutton'));
		}
		
		$(document).ready(function(){
			if (language!="CN" && language!="JP"){
				language = "CN";
			}
			loadProperties(language,setPageByLanguage);
			
           $("#endtimebutton").click(function() {   
        	   var myendtimenew  = $("#endtime").val();
               $("#date3").attr("value",myendtimenew);
        	   $.ajax({ 
                   type: "post", 
                   url: "deadline/endtime.do", 
                   dataType: "json", 
                   data:{"myendtimenew":myendtimenew}, 
                   success: function (data) {
                   	alert(I0018);
                   }, 
               });
          });
       });	
</script>
	</head>
	<body>
	    <form action="endtime.do" name="endtimeForm" id="endtimeForm" target="result"
			method="post" onsubmit="return checkInfo();">
			<table style="width:35%; padding:5px;">
				<tr class="info">
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
				</tr>
				<tr>
					<td style="width: 5%;">
						<label  id= "deadline_string_endtime">请输入填写工数的截止时间:</label>
					</td>
						<td style="width: 10%;">
								
						<input type="text" name="endtime" id="endtime" class="input_txt" onClick="WdatePicker()" value="${endtime}"  
						class="text ui-widget-content ui-corner-all" 
						style="margin-bottom:12px; width:55%; padding: .4em;"/>
						</td>							
				</tr>
				<tr>
					<td style="width: 5%;">
						<label  id= "deadline_string_date3">当前工数锁定时间:</label>
					</td>
					<td style="width: 10%;">
					<input type="text" name="date3" id="date3" class="input_txt"	value="${date3}" readonly 
					class="text ui-widget-content ui-corner-all" 
					style="margin-bottom:12px; width:55%; padding: .4em;"/>
					</td>
				</tr>
				
			</table>
		</form>
			<div class="page_and_btn">
			<div>
				<input type=button id= "endtimebutton"  value=锁定截止日期  />
			</div>
			
		</div>
	</body>
</html>