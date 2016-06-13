<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Work Effort Manage System</title>

<!-- Include CSS for JQuery Frontier Calendar plugin (Required for calendar plugin) -->
<link rel="stylesheet" type="text/css"
	href="css/frontierCalendar/jquery-frontier-cal-1.3.2.css" />

<!-- Include CSS for color picker plugin (Not required for calendar plugin. Used for example.) -->
<link rel="stylesheet" type="text/css"
	href="css/colorpicker/colorpicker.css" />

<!-- Include CSS for JQuery UI (Required for calendar plugin.) -->
<link rel="stylesheet" type="text/css"
	href="css/jquery-ui/smoothness/jquery-ui-1.8.1.custom.css" />

<!--
Include JQuery Core (Required for calendar plugin)
** This is our IE fix version which enables drag-and-drop to work correctly in IE. See README file in js/jquery-core folder. **

<script type="text/javascript" src="js/jquery-core/jquery-1.4.2-ie-fix.min.js"></script>
-->
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<link href="js/select2-3.4.5/select2.css" rel="stylesheet" />
<script src="js/select2-3.4.5/select2.js"></script>
<!-- Include JQuery UI (Required for calendar plugin.) -->
<script type="text/javascript"
	src="js/jquery-ui/smoothness/jquery-ui-1.8.1.custom.min.js"></script>

<!-- Include color picker plugin (Not required for calendar plugin. Used for example.) -->
<!-- guo 20121222 <script type="text/javascript" src="js/colorpicker/colorpicker.js"></script>-->

<!-- Include jquery tooltip plugin (Not required for calendar plugin. Used for example.) -->
<script type="text/javascript"
	src="js/jquery-qtip-1.0.0-rc3140944/jquery.qtip-1.0.js"></script>
<!-- guo 20121222 <script language="javascript" type="text/javascript" src="js/datePicker/WdatePicker.js"></script>  -->
<!--
	(Required for plugin)
	Dependancies for JQuery Frontier Calendar plugin.
    ** THESE MUST BE INCLUDED BEFORE THE FRONTIER CALENDAR PLUGIN. **
-->
<script type="text/javascript" src="js/lib/jshashtable-2.1.js"></script>

<!-- Include JQuery Frontier Calendar plugin -->
<script type="text/javascript"
	src="js/frontierCalendar/jquery-frontier-cal-1.3.2.js"></script>
<script type="text/javascript" src="js/jquery.showLoading.js"></script>
</head>
<body style="background-color: #aaaaaa;">

	<!-- Some CSS for our example. (Not required for calendar plugin. Used for example.)-->
	<style type="text/css" media="screen">
/*
Default font-size on the default ThemeRoller theme is set in ems, and with a value that when combined 
with body { font-size: 62.5%; } will align pixels with ems, so 11px=1.1em, 14px=1.4em. If setting the 
body font-size to 62.5% isn't an option, or not one you want, you can set the font-size in ThemeRoller 
to 1em or set it to px.
http://osdir.com/ml/jquery-ui/2009-04/msg00071.html
*/
body {
	font-size: 62.5%;
}

.shadow {
	-moz-box-shadow: 3px 3px 4px #aaaaaa;
	-webkit-box-shadow: 3px 3px 4px #aaaaaa;
	box-shadow: 3px 3px 4px #aaaaaa;
	/* For IE 8 */
	-ms-filter:
		"progid:DXImageTransform.Microsoft.Shadow(Strength=4, Direction=135, Color='#aaaaaa')";
	/* For IE 5.5 - 7 */
	filter: progid:DXImageTransform.Microsoft.Shadow(Strength=4, Direction=135,
		Color='#aaaaaa');
}
</style>
	<link href="css/showLoading.css" rel="stylesheet" media="screen" />

	<style type="text/css">
a {
	color: blue;
	cursor: pointer;
	text-decoration: underline;
}

div.instructions_container {
	float: left;
	width: 350px;
	margin-right: 50px;
}

div#activity_pane {
	float: left;
	width: 350px;
	height: 200px;
	border: 1px solid #CCCCCC;
	background-color: #EEEEEE;
	padding-top: 200px;
	text-align: center;
}

div.example_links 
    .link_category {
	margin-bottom: 15px;
}

.loading-indicator-bars {
	background-image: url('images/loading-bars.gif');
	width: 150px;
}
</style>
	<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="js/js_user/com.js"></script>
	<script type="text/javascript" src="js/cookie/cookie.js"></script>
	
	<script type="text/javascript">
	
	var language = getCookieValue("language");
	var I0002="";
	var year ="";
	var month = "";
	var lblday = "";
	var datelabel = "";
	var worktime= "";
	var hour = "";
	var process = "";
	var notice1 = "";
	var notice2 = "";
	var total = "";
	var notice3 = "";
	var developworks = "";
	var notice4 = "";
	var notice5 = "";
	var notice6 = "";
	var notice7 = "";
	var notice8 = "";
	var notice9 = "";
	var notice10 = "";
	var submit = "";
	var notice11 = "";
	var notice12 = "";
	var notice13 = "";
	var total2 = "";
	var select = "";
	var E0097 = "";
	
	//国际化
	function setPageByLanguage(){
		
		I0002=$.i18n.prop('I0002');
		E0097=$.i18n.prop('E0097');
		
		year = $.i18n.prop('customDemo_string_year');
		month= $.i18n.prop('customDemo_string_month');
		lblday = $.i18n.prop('customDemo_string_day');
		datelabel = $.i18n.prop('customDemo_string_date');
		worktime = $.i18n.prop('customDemo_string_worktime');
		hour = $.i18n.prop('customDemo_string_hour');
		process = $.i18n.prop('customDemo_string_process');
		notice1 = $.i18n.prop('customDemo_string_notice1');
		notice2 = $.i18n.prop('customDemo_string_notice2');
		total = $.i18n.prop('customDemo_string_total');
		notice3 = $.i18n.prop('customDemo_string_notice3');
		developworks = $.i18n.prop('customDemo_string_developworks');
		notice4 = $.i18n.prop('customDemo_string_notice4');
		notice5 = $.i18n.prop('customDemo_string_notice5');
		notice6 = $.i18n.prop('customDemo_string_notice6');
		notice7 = $.i18n.prop('customDemo_string_notice7');
		notice8 = $.i18n.prop('customDemo_string_notice8');
		notice9 = $.i18n.prop('customDemo_string_notice9');
		notice10 = $.i18n.prop('customDemo_string_notice10');
		//submit = $.i18n.prop('customDemo_string_submit');
		notice11 = $.i18n.prop('customDemo_string_notice11');
		notice12 = $.i18n.prop('customDemo_string_notice12');
		notice13 = $.i18n.prop('customDemo_string_notice13');
		total2 = $.i18n.prop('customDemo_string_total2');
		select = $.i18n.prop('customDemo_string_select');
		
		$('#BtnPreviousMonth').html($.i18n.prop('customDemo_string_PreviousMonth'));
		$('#BtnNextMonth').html($.i18n.prop('customDemo_string_NextMonth'));
		$('#BtnCurrentMonth').html($.i18n.prop('customDemo_string_CurrentMonth'));
		$('#alter-event-form').title=$.i18n.prop('customDemo_string_title1');
		$('#customDemo_string_categoryName').html($.i18n.prop('customDemo_string_categoryName'));
		$('#customDemo_string_projectID').html($.i18n.prop('customDemo_string_projectID'));
		$('#customDemo_string_taskName').html($.i18n.prop('customDemo_string_taskName'));
		$('#customDemo_string_TaskTimes').html($.i18n.prop('customDemo_string_TaskTimes'));
		$('#display-event-form').html($.i18n.prop('customDemo_string_title2'));
		$('#display-event-nochange-form').html($.i18n.prop('customDemo_string_title3'));
		$('#add-event-form').title=$.i18n.prop('customDemo_string_title4');
		$('#customDemo_string_altercategoryName').html($.i18n.prop('customDemo_string_altercategoryName'));
		$('#customDemo_string_alterprojectID').html($.i18n.prop('customDemo_string_alterprojectID'));
		$('#customDemo_string_altertaskName').html($.i18n.prop('customDemo_string_altertaskName'));
		$('#customDemo_string_alterTaskTimes').html($.i18n.prop('customDemo_string_alterTaskTimes'));
		$('#display-event-form').title=$.i18n.prop('customDemo_string_title2');
		$('#display-event-nochange-form').title=$.i18n.prop('customDemo_string_title3');
		

	}
	
$(document).ready(function(){	
	
	if (language!="CN" && language!="JP"){
		language = "CN";
	}
	loadProperties(language,setPageByLanguage);
	
    $("#projectID").select2({
       width:420
    });
    $("#alter_projectID").select2({
       width:420
    });
	var clickDate = "";
	var clickAgendaItem = "";
	var notice = "";
	
	/*get current day*/
	var calCurrentDate = new Date();
	var calCurrentYear = calCurrentDate.getFullYear();
	var calCurrentMonth = calCurrentDate.getMonth();
	var calCurrentDay = calCurrentDate.getDate();
	var currentDate = calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay;
	var calToday = "";
	var jfcalplugin;
	if(parseInt(calCurrentDay)<10){
	    if(parseInt(calCurrentMonth+1)<10){
			    calToday = calCurrentYear + "-0" + (calCurrentMonth+1) + "-0" + calCurrentDay;
			}else{
				calToday =calCurrentYear + "-" + (calCurrentMonth+1) + "-0" + calCurrentDay;
			}
		   
		}else{
			if(parseInt(calCurrentMonth+1)<10){
			    calToday = calCurrentYear + "-0" + (calCurrentMonth+1) + "-" + calCurrentDay;
			}else{
				calToday = calCurrentYear + "-" + (calCurrentMonth+1) + "-" + calCurrentDay;
			}
		    
		}
	$.ajax({ 
         type: "post", 
         url: "calendar/listHoliday.do?currentDate="+currentDate, 
         dataType: "json", 
         success: function (data) {
             var holidayArray = new Array();
             $.each(data.holiday,function(index,content) {
                   holidayArray.push(content);
             });
             
             init(holidayArray);

         }, 
         error: function (XMLHttpRequest, textStatus, errorThrown) { 
             notice = I0002;
		     $("#notice-dialog").dialog('open');
                hideloading();
             } 
         
         
     });
	
	function init (holidayArray){
	    jfcalplugin = $("#mycal").jFrontierCal({
		    date: new Date(),
		    dayClickCallback: myDayClickHandler,
		    agendaClickCallback: myAgendaClickHandler,
		    agendaDropCallback: myAgendaDropHandler,
		    agendaMouseoverCallback: myAgendaMouseoverHandler,
		    applyAgendaTooltipCallback: myApplyTooltip,
		    agendaDragStartCallback : myAgendaDragStart,
		    agendaDragStopCallback : myAgendaDragStop,
		    dragAndDropEnabled: false,
		    holiday:holidayArray
	    }).data("plugin");
		jfcalplugin.setAspectRatio("#mycal",1);
		    //初始化数据
	    var currentDate = calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay;
	    flushDate(currentDate);
	   showloading();
	};
	
	/**
	 * Initializes calendar with current year & month
	 * specifies the callbacks for day click & agenda item click events
	 * then returns instance of plugin object
	 */
	
	
	/*******************************
	 * Do something when dragging starts on agenda div
	 */
	 
    function myAgendaDragStart(eventObj,divElm,agendaItem){
		// destroy our qtip tooltip
		/*******************************
		if(divElm.data("qtip")){
			divElm.qtip("destroy");
		}	
		*******************************/
	};
	
	/**
	 * Do something when dragging stops on agenda div
	 */
	 
	function myAgendaDragStop(eventObj,divElm,agendaItem){
		//alert("drag stop");
	};
	
	/**
	 * Custom tooltip - use any tooltip library you want to display the agenda data.
	 * for this example we use qTip - http://craigsworks.com/projects/qtip/
	 *
	 * @param divElm - jquery object for agenda div element
	 * @param agendaItem - javascript object containing agenda data.
	 */
	function myApplyTooltip(divElm,agendaItem){

		// Destroy currrent tooltip if present
		if(divElm.data("qtip")){
			divElm.qtip("destroy");
		}
		
		var displayData = "contents";
		
		var title = agendaItem.title;
		var startDate = agendaItem.startDate;
		//var endDate = agendaItem.endDate;
		//var allDay = agendaItem.allDay;
		var data = agendaItem.data;
		
		displayData += "<br><b>" + title+ "</b><br><br>";
		
		if(startDate){
		    var dayStr =  startDate.getFullYear() + year + (startDate.getMonth()+1) + month + startDate.getDate()+lblday;
			displayData += "<b>"+datelabel+"&nbsp;&nbsp;&nbsp;&nbsp;</b> " + dayStr + "<br><br>";			
		}else{
			displayData += "Error time!<br><br>";
		}
		
		for (var propertyName in data) {
			if(propertyName == "manhourID" || propertyName == "ProjectID" || propertyName == "TaskID"){

			}else if(propertyName == worktime){
			    
			    displayData += "<b>" + propertyName + ":</b> " + data[propertyName] + hour+"<br>"
			}else if(propertyName == process){
			    displayData += "<b>" + propertyName + ":</b> " + data[propertyName] + "%<br>"
			}else{
				displayData += "<b>" + propertyName + ":</b> " + data[propertyName] + "<br>"
			}
			
		}
		
		// use the user specified colors from the agenda item.
		var backgroundColor = agendaItem.displayProp.backgroundColor;
		var foregroundColor = agendaItem.displayProp.foregroundColor;
		var myStyle = {
			border: {
				width: 5,
				radius: 10
			},
			padding: 10, 
			textAlign: "left",
			tip: true,
			name: "blue" // other style properties are inherited from dark theme		
		};
		
		if(backgroundColor != null && backgroundColor != ""){
			myStyle["backgroundColor"] = backgroundColor;
		}
		if(foregroundColor != null && foregroundColor != ""){
			myStyle["color"] = foregroundColor;
		}
		
		// apply tooltip
		divElm.qtip({
			content: displayData,
			position: {
				corner: {
					tooltip: "bottomMiddle",
					target: "topMiddle"			
				},
				adjust: { 
					mouse: true,
					x: 0,
					y: -15
				},
				target: "mouse"
			},
			show: { 
				when: { 
					event: 'mouseover'
				}
			},
			style: myStyle
		});

	};

	/**
	 * Make the day cells roughly 3/4th as tall as they are wide. this makes our calendar wider than it is tall. 
	 */
//	jfcalplugin.setAspectRatio("#mycal",1);

	/**
	 * Called when user clicks day cell
	 * use reference to plugin object to add agenda item
	 */
	var myClickDate = "";
	function myDayClickHandler(eventObj){
		// Get the Date of the day that was clicked from the event object
		var date = eventObj.data.calDayDate;
		myClickDate = date;//点击框 的日期
		// store date in our global js variable for access later
		if(parseInt(date.getDate())<10){
			if(parseInt((date.getMonth()+1))<10){
			    clickDate = date.getFullYear() + "-0" + (date.getMonth()+1) + "-0" + date.getDate();
			}else{
				clickDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-0" + date.getDate();
			}
		   
		}else{
			if(parseInt((date.getMonth()+1))<10){
			    clickDate = date.getFullYear() + "-0" + (date.getMonth()+1) + "-" + date.getDate();
			}else{
				clickDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
			}
		    
		}
		
		var deadline = $("#deadline").val();
		var endArray = deadline.split("-");
		var endYear  = deadline.substring(0,4);
		var endMonth = deadline.substring(5,7);
		var endDay   = deadline.substring(8,10);
		/* var endYear  = endArray[0];
		var endMonth = endArray[1];
		var endDay   = endArray[2]; */
		if(parseInt(endMonth,10)+1 > 12){
		    endYear = parseInt(endYear)+1;
		    endMonth = "01";
		}else{
		    endMonth = parseInt(endMonth,10)+1;
		    if(parseInt(endMonth)<10){
		        endMonth = "0"+endMonth;
		    }
		}
		var endTime = endYear + "-"+endMonth +"-"+endDay;
		
		if(deadline==""||clickDate<=deadline){
		    notice = notice1;
		    $("#notice-dialog").dialog('open');
		//    alert("该月工数填写已关闭");
		}else if(clickDate > endTime){
		   notice = notice2;
		   $("#notice-dialog").dialog('open');
		}/**只能填写到当天工数
		    else if(clickDate > calToday){
		    notice = "该天工数填写还未开始";
		    $("#notice-dialog").dialog('open');
		}**/else{
		    $('#date').attr("value",clickDate);
		    $('#add-event-form').dialog('open');
		}
		// open our add event dialog

	};
	
	/**
	 * Called when user clicks and agenda item
	 * use reference to plugin object to edit agenda item
	 */
	function myAgendaClickHandler(eventObj){
		// Get ID of the agenda item from the event object
		var agendaId = eventObj.data.agendaId;		
		var date = eventObj.data.calDayDate;
		
		
		var deadline = $("#deadline").val();
		
		// pull agenda item from calendar
		var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);
		var data = agendaItem.data;
		// 点击合计时不弹出
		if (agendaItem.title.indexOf(total) > -1){
			return false
		}
		clickAgendaItem = agendaItem;
		var start = clickAgendaItem.startDate
		if(parseInt(start.getDate())<10){
			if(parseInt((start.getMonth()+1))<10){
			    clickDate = start.getFullYear() + "-0" + (start.getMonth()+1) + "-0" + start.getDate();
			}else{
				clickDate = start.getFullYear() + "-" + (start.getMonth()+1) + "-0" + start.getDate();
			}
		   
		}else{
			if(parseInt((start.getMonth()+1))<10){
			    clickDate = start.getFullYear() + "-0" + (start.getMonth()+1) + "-" + start.getDate();
			}else{
				clickDate = start.getFullYear() + "-" + (start.getMonth()+1) + "-" + start.getDate();
			}
		    
		}
		if(deadline==""||clickDate<=deadline){
		   $("#display-event-nochange-form").dialog('open');
		}else{
		   $("#display-event-form").dialog('open');
		}
		
	};
	
	/**
	 * Called when user drops an agenda item into a day cell.
	 */
	function myAgendaDropHandler(eventObj){
	/*******************************
		// Get ID of the agenda item from the event object
		var agendaId = eventObj.data.agendaId;
		// date agenda item was dropped onto
		var date = eventObj.data.calDayDate;
		// Pull agenda item from calendar
		var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);		
		alert("You dropped agenda item " + agendaItem.title + 
			" onto " + date.toString() + ". Here is where you can make an AJAX call to update your database.");
	*******************************/
	};
	
	/**
	 * Called when a user mouses over an agenda item	
	 */
	function myAgendaMouseoverHandler(eventObj){
		var agendaId = eventObj.data.agendaId;
		var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);
		//alert("You moused over agenda item " + agendaItem.title + " at location (X=" + eventObj.pageX + ", Y=" + eventObj.pageY + ")");
	};
	
	
	

	
	/**
	 * Set datepicker to current date
	 */
	//$("#dateSelect").datepicker('setDate', new Date());
	//$("#dateSelect").attr("value",calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay);
	$("#dateSelect").text(calCurrentYear+year+(calCurrentMonth+1)+month);

	
	/**
	 * Initialize previous month button
	 */
	$("#BtnPreviousMonth").button();
	$("#BtnPreviousMonth").click(function() {
	    showloading();
		jfcalplugin.showPreviousMonth("#mycal");
		// update the jqeury datepicker value
		var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
		var cyear = calDate.getFullYear();
		// Date month 0-based (0=January)
		var cmonth = calDate.getMonth();
		var cday = calDate.getDate();
		// jquery datepicker month starts at 1 (1=January) so we add 1
		//$("#dateSelect").datepicker("setDate",cyear+"-"+(cmonth+1)+"-"+cday);
		$("#dateSelect").text(cyear+year+(cmonth+1)+month);
		var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
		flushDate(currentDate)
		return false;
	});
	/**
	 * Initialize next month button
	 */
	$("#BtnNextMonth").button();
	$("#BtnNextMonth").click(function() {
	    showloading();
		jfcalplugin.showNextMonth("#mycal");
		
		// update the jqeury datepicker value
		var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
		var cyear = calDate.getFullYear();
		// Date month 0-based (0=January)
		var cmonth = calDate.getMonth();
		var cday = calDate.getDate();
		// jquery datepicker month starts at 1 (1=January) so we add 1
		//$("#dateSelect").datepicker("setDate",cyear+"-"+(cmonth+1)+"-"+cday);		
		$("#dateSelect").text(cyear+year+(cmonth+1)+month);
		var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
		flushDate(currentDate)
		return false;
	});
	
	/*Modify*/
	$("#BtnCurrentMonth").button();
	$("#BtnCurrentMonth").click(function() {
	    showloading();
	    var tempMonth = calCurrentMonth;
	    if(parseInt(calCurrentMonth)<10){
	        tempMonth ="0" + calCurrentMonth;
	    }
		jfcalplugin.showMonth("#mycal",calCurrentYear.toString(),calCurrentMonth.toString());		
		//$("#dateSelect").datepicker("setDate",calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay);	
		$("#dateSelect").text(calCurrentYear+year+(calCurrentMonth+1)+month);
		var currentDate = calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay;
		flushDate(currentDate)
		return false;
	});
 	
	/**
	 * alter event dialog
	 */
	 $("#alter-event-form").dialog({

			autoOpen: false,
			height: 400,
			width: 600,
			modal: true,
			buttons: {
				'Save': function() {
	                showloading();
					var what = jQuery.trim($("#alter_TaskTimes").val());
					//var what1 =jQuery.trim($("#alter_TaskRatio").val());
					var what2 = jQuery.trim($("#alter_TaskTimes").val());
					/**if (what1==""||what1<0||what1>100) {
						 hideloading();
						alert("请按0至100填写项目进度!");
						
					}	
					else**/
					if($("#alter_projectID").val() == null || $("#alter_projectID").val() == ""){
						hideloading();
						alert(E0097);
					} else {
						if (what2 ==""||what2<=0||what>24) {//新增
							 hideloading();
						  	alert(notice3);
						}
					    else{
					    	var data = clickAgendaItem.data;
						    var manhourID = data.manhourID;
						    var myDate = $("#alter_date").val();
							var myProjectCategory = jQuery.trim($("#alter_categoryName").val());//$("#ProjectCategory").val();
							var myProjectCategoryLocal = myProjectCategory;
							if(myProjectCategory.indexOf(developworks) != -1){					    
					            myProjectCategory = developworks;
					        }
							var myProjectName = $("#alter_projectID").val();
							var myTaskID = $("#alter_taskName").val();
							//var myTaskID = $("#alter_taskName").find("option:selected").text();
						    var myTaskType = $("#alter_taskName").find("option:selected").text();				
							var myTaskTimes = $("#alter_TaskTimes").val();
							//20140904数据合法性检查
							$.ajax({ 
		                        type: "post", 
		                        url: "calendar/checkEditBeforeSave.do", 
		                        dataType: "json", 
		                        data:{
		                        	id:manhourID,
		                            date:myDate,
		                            times:myTaskTimes	
		                        },
		                        success: function (data) {
		                            if(data.data>14&&data.data<24){
		  		                    if (confirm(notice4+data.data+notice5)) {
		  		                    //与服务器交互
		  								$.ajax({ 
		  			                        type: "post", 
		  			                        url: "calendar/saveEdit.do", 
		  			                        dataType: "json", 
		  			                        data:{
		  				                        id:manhourID,
		  			                            date:myDate,
		  			     		                categoryName:myProjectCategory,
		  			     		                projectID:myProjectName,
		  			                            taskName:myTaskType,
		  			                            taskID:myTaskID,
		  			                            times:myTaskTimes	
		  			                        },
		  			                        success: function (data) {
		  			                            if(data.result == "success"){
		  				                            var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
		  				                    		var cyear = calDate.getFullYear();
		  				                    		var cmonth = calDate.getMonth();
		  				                    		var cday = calDate.getDate();
		  				                    		var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
		  				                    		flushDate(currentDate)
		  				                    		 notice = notice6;
		  				                    		 $("#notice-dialog").dialog('open');
		  			                            }else{
		  			                                 notice = notice7
		  				                    		 $("#notice-dialog").dialog('open');
		  			                            }
		  			                           
		  			                            hideloading();
		  			                        }, 
		  			                        error: function (XMLHttpRequest, textStatus, errorThrown) {
		  			                          notice = I0002;
		  				                      $("#notice-dialog").dialog('open');
		  			                            hideloading();
		  			                        } 
		  			                    });
		  		     	   		   }
		  		                    }else if(data.data>24){
		                              notice = notice8+data.data+notice9;
				                      $("#notice-dialog").dialog('open');
		                            }
		  		                     else if(data.data<=14){
		  		                    	//与服务器交互
			  								$.ajax({ 
			  			                        type: "post", 
			  			                        url: "calendar/saveEdit.do", 
			  			                        dataType: "json", 
			  			                        data:{
			  				                        id:manhourID,
			  			                            date:myDate,
			  			     		                categoryName:myProjectCategory,
			  			     		                projectID:myProjectName,
			  			                            taskName:myTaskType,
			  			                            taskID:myTaskID,
			  			                            times:myTaskTimes	
			  			                        },
			  			                        success: function (data) {
			  			                            if(data.result == "success"){
			  				                            var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
			  				                    		var cyear = calDate.getFullYear();
			  				                    		var cmonth = calDate.getMonth();
			  				                    		var cday = calDate.getDate();
			  				                    		var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
			  				                    		flushDate(currentDate)
			  				                    		 notice = notice6;
			  				                    		 $("#notice-dialog").dialog('open');
			  			                            }else{
			  			                                 notice = notice7
			  				                    		 $("#notice-dialog").dialog('open');
			  			                            }
			  			                           
			  			                            hideloading();
			  			                        }, 
			  			                        error: function (XMLHttpRequest, textStatus, errorThrown) {
			  			                          notice = I0002;
			  				                      $("#notice-dialog").dialog('open');
			  			                            hideloading();
			  			                        } 
			  			                    });
		                            }
		  		                   else{
			                              notice = notice10;
					                      $("#notice-dialog").dialog('open');
			                            }
		                            hideloading();
		                        }, 
		                        error: function (XMLHttpRequest, textStatus, errorThrown) { 
		                            notice = I0002;
				                    $("#notice-dialog").dialog('open');
		                            hideloading();
		                        } 
		                    });
							$(this).dialog('close');
						}
					}
				},
				Cancel: function() {
					 $(this).dialog('close'); 
				}
			},
			open: function(event, ui){
			    var day =clickAgendaItem.startDate.getFullYear() + year + (clickAgendaItem.startDate.getMonth()+1) + month + clickAgendaItem.startDate.getDate()+lblday;		
				$("#AlterEventCurrentDay").html(day);
					var data = clickAgendaItem.data;
					var manhourID = data.manhourID;
					var ProjectCategory = data.项目类别;
					var ProjectID       = data.ProjectID;
					var TaskType        = data.作业类型;
					var TaskID          = data.TaskID;
					var TaskTimes       = data.工作时间;
					$("#alter_categoryName").val(ProjectCategory);
					var sel = document.getElementById("alter_categoryName");
					sel.onchange();
					var projectSel = document.getElementById("alter_projectID");
					projectSel.onchange(); 
					var altertaskNameSel = document.getElementById("alter_taskName");
					altertaskNameSel.onchange(); 
					//$("#alter_projectID").val(ProjectID);
					$("#alter_projectID").attr("value",ProjectID);
					$("#alter_taskName").attr("value",TaskID);
					$("#alter_TaskTimes").attr("value",TaskTimes);
					
				$("#ProjectCategory").focus();
			},
			close: function() {
				$("#ProjectCategory option:eq(0)").attr("selected", "selected");
				$("#ProjectName option:eq(0)").attr("selected", "selected");
				$("#TaskType").attr("value","");
				$("#TaskRatio").attr("value","");
				$("#TaskTimes").attr("value","");
			}
		});
	/**
	 * add event dialog
	 */
	$("#add-event-form").dialog({
		autoOpen: false,
		height: 400,
		width: 600,
		modal: true,
		buttons: {
			'Add': function() {
                showloading();
				var eventTitle = ">>>AddEvent<<<";
				var what = jQuery.trim($("#TaskTimes").val());
				//var what1 =jQuery.trim($("#TaskRatio").val());
				var what2 = jQuery.trim($("#TaskTimes").val());
				/**if (what1==""||what1<0||what1>100) {
					 hideloading();
					alert("请按0至100填写项目进度!");
					
				}	
				else **/
				if($("#projectID").val() == null || $("#projectID").val() == ""){
					hideloading();
					alert(E0097);
				} else {
					if (what2 ==""||what2<=0||what>24) {//新增
						 hideloading();
					  	alert(notice3);
					}
				    else{
					    //插入一条工数
					    var myDate = $("#date").val();
						var myProjectCategory = jQuery.trim($("#categoryName").val());//$("#ProjectCategory").val();
						var myProjectCategoryLocal = myProjectCategory;
						if(myProjectCategory.indexOf(developworks) != -1){					    
					        myProjectCategory = developworks;
					    }
						var myProjectName = $("#projectID").val();
						var myTaskID = $("#taskName").val();
						var myTaskType = $("#taskName").find("option:selected").text();
						//var myTaskRatio = $("#TaskRatio").val();				
						var myTaskTimes = $("#TaskTimes").val();
						//20140904数据合法性检查
						$.ajax({ 
	                        type: "post", 
	                        url: "calendar/checkBeforeSave.do", 
	                        dataType: "json", 
	                        data:{
	                            date:myDate,
	                            times:myTaskTimes	
	                        },
	                        success: function (data) {
	                            if(data.data>14&&data.data<24){
	  		                    if (confirm(notice4+data.data+notice5)) {
	  		                    	$.ajax({ 
	  		                          type: "post", 
	  		                          url: "calendar/save.do", 
	  		                          dataType: "json", 
	  		                          data:{
	  		                              date:myDate,
	  		       		                categoryName:myProjectCategory,
	  		       		                projectID:myProjectName,
	  		       		                taskID:myTaskID,
	  		                              taskName:myTaskType,
	  		                            //  taskRate:myTaskRatio,
	  		                              times:myTaskTimes	
	  		                          },
	  		                          success: function (data) {
	  		                              if(data.result == "success"){
	  		                            	  var projectNameText=$("#projectID").find("option:selected").text();  
	  		                              	  var strs = myDate.split("-");
	  		                                  var initdate = new Date(strs[0],strs[1]-1,strs[2]);
	  		                               	  //add by liuyang
	  		                                  //initCalendarWithAgendaItems(initdate,myTaskTimes,myTaskID,myTaskType,projectNameText,myProjectCategoryLocal,data.id,myProjectName);
	  		                                  var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
	 				                    	  var cyear = calDate.getFullYear();
	 				                    	  var cmonth = calDate.getMonth();
	 				                    	  var cday = calDate.getDate();
	 				                    	  var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
	 				                    	  flushDate(currentDate);
	  		                               	  //add by liuyang
	  		                              }else{
	  		                                notice = notice10;
	  		  		                      $("#notice-dialog").dialog('open');
	  		                              }
	  		                              hideloading();
	  		                          }, 
	  		                          error: function (XMLHttpRequest, textStatus, errorThrown) { 
	  		                              notice = I0002;
	  		  		                    $("#notice-dialog").dialog('open');
	  		                              hideloading();
	  		                          } 
	  		                      });
	  		     	   		   }
	  		                    }else if(data.data>24){
	                              notice = notice8+data.data+notice9;
			                      $("#notice-dialog").dialog('open');
	                            }
	  		                     else if(data.data<=14){
	  		                    	$.ajax({ 
	    		                          type: "post", 
	    		                          url: "calendar/save.do", 
	    		                          dataType: "json", 
	    		                          data:{
	    		                              date:myDate,
	    		       		                categoryName:myProjectCategory,
	    		       		                projectID:myProjectName,
	    		       		                taskID:myTaskID,
	    		                              taskName:myTaskType,
	    		                            //  taskRate:myTaskRatio,
	    		                              times:myTaskTimes	
	    		                          },
	    		                          success: function (data) {
	    		                              if(data.result == "success"){
	    		                              	 var projectNameText=$("#projectID").find("option:selected").text();  
	    		                              	 var strs = myDate.split("-");
	    		                                 var initdate = new Date(strs[0],strs[1]-1,strs[2]);	
	    		                                 //add by liuyang
	    		                                 //initCalendarWithAgendaItems(initdate,myTaskTimes,myTaskID,myTaskType,projectNameText,myProjectCategoryLocal,data.id,myProjectName);
	    		                                 var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
	 				                    		 var cyear = calDate.getFullYear();
	 				                    		 var cmonth = calDate.getMonth();
	 				                    		 var cday = calDate.getDate();
	 				                    		 var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
	 				                    		 flushDate(currentDate);
	      		                               	 //add by liuyang
	    		                              }else{
	    		                                notice = notice10;
	    		  		                      $("#notice-dialog").dialog('open');
	    		                              }
	    		                              hideloading();
	    		                          }, 
	    		                          error: function (XMLHttpRequest, textStatus, errorThrown) { 
	    		                              notice = I0002;
	    		  		                    $("#notice-dialog").dialog('open');
	    		                              hideloading();
	    		                          } 
	    		                      });
	                            }
	                            else{
	                                notice = notice10;
	  		                      $("#notice-dialog").dialog('open');
	                              }
	                            hideloading();
	                        }, 
	                        error: function (XMLHttpRequest, textStatus, errorThrown) { 
	                            notice = I0002;
			                    $("#notice-dialog").dialog('open');
	                            hideloading();
	                        }
	                    });
						$(this).dialog('close');
					}
				}
			},
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		open: function(event, ui){		
		    var day = myClickDate.getFullYear() + year + (myClickDate.getMonth()+1) + month + myClickDate.getDate()+lblday;
			$("#AddEventCurrentDay").html(day);
			//alert($('#AddEventCurrentDay').html());
			$("#ProjectCategory").focus();
		},
		close: function() {
			$("#ProjectCategory option:eq(0)").attr("selected", "selected");
			$("#ProjectName option:eq(0)").attr("selected", "selected");
			$("#TaskType option:eq(0)").attr("selected", "selected");
			$("#TaskRatio").attr("value","");
			$("#TaskTimes").attr("value","");
		}
	});
	
	/**
	 * 提示信息对话框
	 */
	$("#notice-dialog").dialog({
		autoOpen: false,
		height: 210,
		width: 300,
		modal: true,
		buttons: {		
			确定: function() {
				$(this).dialog('close');
			}			
		},
		open: function(event, ui){
			if(notice != null){
				
				$("#notice-dialog").append(
					"<br><b><p style=\"color:#e64545;font-size:14px;\"  align=\"center\" >" + notice+ "</p></b><br><br>"		
				);				
			}
		},
		close: function() {
			// clear agenda data
			$("#notice-dialog").html("");
		}
	});	 
	
	/**
	 * 只读事件信息对话框
	 */
	$("#display-event-nochange-form").dialog({
		autoOpen: false,
		height: 400,
		width: 600,
		modal: true,
		buttons: {		
			Cancel: function() {
				$(this).dialog('close');
			}			
		},
		open: function(event, ui){
			if(clickAgendaItem != null){
				var title = clickAgendaItem.title;
				var startDate = clickAgendaItem.startDate;
				var data = clickAgendaItem.data;

				// in our example add agenda modal form we put some fake data in the agenda data. we can retrieve it here.
				$("#display-event-nochange-form").append(
					"<br><b>" + title+ "</b><br><br>"		
				);				
				if(startDate){	
				var dayStr =  startDate.getFullYear() + year + (startDate.getMonth()+1) + month + startDate.getDate()+lblday;
					$("#display-event-nochange-form").append(
						"<b>"+datelabel+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> " + dayStr + "<br><br>"
					);						
				}else{
					$("#display-event-nochange-form").append(
						"(Error Day!)<br><br>"				
					);	
				}
				for (var propertyName in data) {
					if(propertyName != "manhourID" && propertyName != "ProjectID" && propertyName != "TaskID"){
					   if(propertyName == worktime){
					       $("#display-event-nochange-form").append("<b>" + propertyName + ":</b> " + data[propertyName] +hour+ "<br>");
					   }else if(propertyName == process){
			               $("#display-event-nochange-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "%<br>");
			           }else{
			               $("#display-event-nochange-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "<br>");
			           }	
					}
				}			
			}		
		},
		close: function() {
			// clear agenda data
			$("#display-event-nochange-form").html("");
		}
	});	 
	
	/**
	 * Initialize display event form.
	 */
	 
	 
	$("#display-event-form").dialog({
		
		autoOpen: false,
		height: 400,
		width: 600,
		modal: true,
		buttons: {		
			
			Cancel: function() {
			
				$(this).dialog('close');
			},
			
			'Edit': function() {
				
					 var start = clickAgendaItem.startDate;
					 var clickDate = "";
					 if(parseInt(start.getDate())<10){
						    clickDate = start.getFullYear() + "-" + (start.getMonth()+1) + "-0" + start.getDate();
					    }else{
						    clickDate = start.getFullYear() + "-" + (start.getMonth()+1) + "-" + start.getDate();
						}
					 $('#alter_date').attr("value",clickDate);
					 $("#alter-event-form").dialog('open');
					 $(this).dialog('close');
				
			},
			'Delete': function() {
				
						if(confirm(notice11)){
							if(clickAgendaItem != null){
								showloading();
								var data = clickAgendaItem.data;
								var manhourID = data.manhourID;
								 $.ajax({ 
								        type: "post", 
								        url: "calendar/delete.do?id="+manhourID, 
								        dataType: "json", 
								        success: function (data) {
			                                if(data.result == "success"){
			                                notice = notice12;
				                            $("#notice-dialog").dialog('open');
			                                //add by liuyang 
				                            //jfcalplugin.deleteAgendaItemById("#mycal",clickAgendaItem.agendaId);
				                            var calDate = jfcalplugin.getCurrentDate("#mycal"); // returns Date object
					                    	var cyear = calDate.getFullYear();
					                    	var cmonth = calDate.getMonth();
					                    	var cday = calDate.getDate();
					                    	var currentDate = cyear+"-"+(cmonth+1)+"-"+cday;
					                    	flushDate(currentDate);
			                                //add by liuyang
			                                }else{
			                                notice = notice13;
				                            $("#notice-dialog").dialog('open');
		                                    }
		                                    hideloading();
								        }, 
								        error: function (XMLHttpRequest, textStatus, errorThrown) { 
								            notice = I0002;
				                            $("#notice-dialog").dialog('open');
								            hideloading();
								        } 
								    });
							}
							$(this).dialog('close');
						}
				
			}			
		},
		open: function(event, ui){
			if(clickAgendaItem != null){
				var title = clickAgendaItem.title;
				var startDate = clickAgendaItem.startDate;
				//var endDate = clickAgendaItem.endDate;
				//var allDay = clickAgendaItem.allDay;
				var data = clickAgendaItem.data;

				// in our example add agenda modal form we put some fake data in the agenda data. we can retrieve it here.
				$("#display-event-form").append(
					"<br><b>" + title+ "</b><br><br>"		
				);				
				if(startDate){	
				var dayStr =  startDate.getFullYear() + year + (startDate.getMonth()+1) + month + startDate.getDate()+lblday;
					$("#display-event-form").append(
					    
						"<b>"+datelabel+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> " + dayStr + "<br><br>"
					);						
				}else{
					$("#display-event-form").append(
						"(Error Day!)<br><br>"				
					);	
				}
				for (var propertyName in data) {
					if(propertyName != "manhourID" && propertyName != "ProjectID" && propertyName != "TaskID"){
					   if(propertyName == worktime){
					       $("#display-event-form").append("<b>" + propertyName + ":</b> " + data[propertyName] +hour+ "<br>");
					   }else if(propertyName == process){
			               $("#display-event-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "%<br>");
			           }else{
			               $("#display-event-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "<br>");
			           }
						
					}
				}			
			}		
		},
		close: function() {
			// clear agenda data
			$("#display-event-form").html("");
		}
	});	 
	
	/**
	 * Initialize our tabs
	 */
	$("#tabs").tabs({
		/*
		 * Our calendar is initialized in a closed tab so we need to resize it when the example tab opens.
		 */
		show: function(event, ui){
			if(ui.index == 1){
				jfcalplugin.doResize("#mycal");
			}
		}	
	});
	
	
	
	//更新一条工数数据
	function initCalendarWithAgendaItems(initDate,TaskTimes,TaskID,TaskType,ProjectName,ProjectCategory,manhourID,projectID){
        if(TaskType == null){
            var TaskType = "-";
        }
        jfcalplugin.addAgendaItem(
			"#mycal",
			TaskTimes+"h           "+ProjectName,
			initDate,
			initDate,
			true,
			{
				manhourID:manhourID,
				ProjectID:projectID,
				工作时间: TaskTimes,
				TaskID: TaskID ,
				作业类型: TaskType ,
				项目名称: ProjectName,
				项目类别: ProjectCategory
			},
			{
				backgroundColor:  "#c5fcc5",//"#c1f7c7",//"#affaaf",
				foregroundColor: "#525752"
			}
		);
		
	}
	// 添加合计
	//更新一条工数数据
		function addTotalHourAgendaItems(initDate,TaskTimes){
	        jfcalplugin.addAgendaItem(
				"#mycal",
				"<B>"+total2+TaskTimes+"h</B>",
				initDate,
				initDate,
				true,
				{
					工作时间: TaskTimes,
				},
				{
					backgroundColor:  "#c5fcc5",//"#c1f7c7",//"#affaaf",
					foregroundColor: "#525752"
				}
			);
			
		}
	//显示Loading界面
	function showloading(){
	    jQuery('#tabs').showLoading(
	 	    {
			    'addClass': 'loading-indicator-bars'
			 }
		 );
	}
	
	//隐藏Loading界面
	function hideloading(){
	    jQuery('#tabs').hideLoading();
	}
	
	//更新界面工数数据
    function flushDate(currentDate){
    jfcalplugin.deleteAllAgendaItems("#mycal");
    $.ajax({ 
        type: "post", 
        url: "calendar/list.do?currentDate="+currentDate, 
        dataType: "json", 
        success: function (data) {
            $("#deadline").attr("value",data.deadline);
            //add by liu
            var totalHour = 0.0;
            var tempDate = "";
            var tempStr =  "";
            var contentArr = new Array();
            var i = 0;
            
            $.each(data.result,function(index,content) {
               var strs = content.date.split("-");
               var date = new Date(strs[0],strs[1]-1,strs[2]);
               var dateStr = content.date;

               //initCalendarWithAgendaItems(date,content.times,content.taskID,content.task,content.projectName,content.category,content.id,content.projectID);
               //add by liuyang start
               if (tempStr == "" || tempStr == dateStr){
            	   totalHour = totalHour + content.times;
            	   contentArr[i] = content;
            	   i++;
            	   tempDate = date;
            	   tempStr = dateStr;
               } else {
            	   addTotalHourAgendaItems(tempDate,totalHour);
            	   for (k = 0; k < i ;k++){
            		   var tempContent = contentArr[k];
            		   initCalendarWithAgendaItems(tempDate,tempContent.times,tempContent.taskID,tempContent.task,tempContent.projectName,tempContent.category,tempContent.id,tempContent.projectID);
            	   }
            	   i = 0;
            	   contentArr = new Array();
            	   contentArr[i] = content;
            	   i++;
            	   totalHour = content.times;
            	   tempDate = date;
            	   tempStr = dateStr;
               }
               if (index == data.result.length - 1){
            	   addTotalHourAgendaItems(tempDate,totalHour);
            	   for (k = 0; k < i ;k++){
            		   var tempContent = contentArr[k];
            		   initCalendarWithAgendaItems(tempDate,tempContent.times,tempContent.taskID,tempContent.task,tempContent.projectName,tempContent.category,tempContent.id,tempContent.projectID);
            	   }
               }
               //add by liuyang end
            });
            $.ajax({ 
                type: "post", 
                url: "project/listSelectProject.do", 
                dataType: "json", 
                success: function (data) {
                	
                	var val=["3 ->", "3_1 ->", "3_1_2"];
                	var sel=["categoryName", "projectID", "taskName"];
                	var sel1=["alter_categoryName", "alter_projectID", "alter_taskName"];
                	
                	var cs = new CascadeSelect(sel, data, { Default: val });
                	hideloading();
                	var cs1 = new CascadeSelect(sel1, data, { Default: val });
                	
                   
                   
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                   notice = I0002;
		           $("#notice-dialog").dialog('open');
                   hideloading();
                } 
            });
            
        }, 
        error: function (XMLHttpRequest, textStatus, errorThrown) { 
            notice = I0002;
		    $("#notice-dialog").dialog('open');
            hideloading(); 
        } 
    });
   
    }
    
    //判断输入框输入只能是数字和小数点
    $.fn.numeral = function () {
        $(this).css("ime-mode", "disabled");
        this.bind("keypress", function (e) {
            var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE   
            if (!$.browser.msie && (e.keyCode == 0x8))  //火狐下 不能使用退格键  
            {
                return;
            }
            return code >= 48 && code <= 57 || code == 46;

        });
        this.bind("blur", function () {

            if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
                this.value = this.value.substr(0, this.value.length - 1);
            } else if (isNaN(this.value)) {
                this.value = " ";
            }
            
            this.value = this.value.replace(/^\./g, "");            
        });
        this.bind("paste", function () {
            var s = clipboardData.getData('text');
            if (!/\D/.test(s));
            value = s.replace(/^0*/, '');
            return false;
        });
        this.bind("dragenter", function () {
            return false;
        });
        this.bind("keyup", function () {
        	if (/[^\d.]/g.test(this.value)){
            	this.value = this.value.replace(/[^\d.]/g, "");
        	}
            //必须保证第一个为数字而不是.         
            //this.value = this.value.replace(/^\./g, "");
            //保证只有出现一个.而没有多个.
            if (this.value.match(/\./g).length > 1){
            	this.value = this.value.replace(/\.{2,}/g, ".");
            	//保证.只出现一次，而不能出现两次以上
            	this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            }
        });
    };
    
    //设置输入框只能输入数字和小数
    $("#TaskRatio").numeral();
    $("#TaskTimes").numeral();
    $("#alter_TaskRatio").numeral();
    $("#alter_TaskTimes").numeral();
});

//SELECT
var changeS = function (id) {
    return "string" == typeof id ? document.getElementById(id) : id;
};

function addEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.addEventListener) {
        oTarget.addEventListener(sEventType, fnHandler, false);
    } else if (oTarget.attachEvent) {
        oTarget.attachEvent("on" + sEventType, fnHandler);
    } else {
        oTarget["on" + sEventType] = fnHandler;
    }
};

function Each(arrList, fun){
    for (var i = 0, len = arrList.length; i < len; i++) { fun(arrList[i], i); }
};

function GetOption(val, txt) {
    var op = document.createElement("OPTION");
    if(!isNaN(val)){
        op.value = val; op.innerHTML = txt;
    }else{
        op.value = val; op.innerHTML = txt;
    }
    
    return op;
};

var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}

Object.extend = function(destination, source) {
    for (var property in source) {
        destination[property] = source[property];
    }
    return destination;
}


var CascadeSelect = Class.create();
CascadeSelect.prototype = {
  //select集合，菜单对象
  initialize: function(arrSelects, arrMenu, options) {

    if(arrSelects.length <= 0 || arrMenu.lenght <= 0) return;//菜单对象
    
    var oThis = this;
    
    this.Selects = [];//select集合
    this.Menu = arrMenu;//菜单对象
    
    this.SetOptions(options);
    
    this.Default = this.options.Default || [];
    
    this.ShowEmpty = !!this.options.ShowEmpty;
    this.EmptyText = this.options.EmptyText.toString();
    //设置Selects集合和change事件
    Each(arrSelects, function(o, i){
    	(oThis.Selects[i] = changeS(o)).onchange =  function(){ oThis.Set(i); }
        //addEventHandler((oThis.Selects[i] = changeS(o)), "change", function(){ oThis.Set(i);alert("onchange!!"); });
    });
    this.ReSet();
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
        Default:    [],//默认值(按顺序)
        ShowEmpty:    false,//是否显示空值(位于第一个)
        EmptyText:    select//空值显示文本(ShowEmpty为true时有效)
    };
    Object.extend(this.options, options || {});
  },
  //初始化select
  ReSet: function() {
    this.SetSelect(this.Selects[0], this.Menu, this.Default.shift());
    this.Set(0);
  },
  //全部select设置
  Set: function(index) {
    var menu = this.Menu
    
    //第一个select不需要处理所以从1开始
    for(var i=1, len = this.Selects.length; i < len; i++){
        if(menu.length > 0){
            //获取菜单
            var value = this.Selects[i-1].value;
            if(value!=""){
                Each(menu, function(o){ if(o.val == value){ menu = o.menu || []; } });
            } else { menu = []; }
            
            //设置菜单
            if(i > index){ this.SetSelect(this.Selects[i], menu, this.Default.shift()); }
        } else {
            //没有数据
            this.SetSelect(this.Selects[i], [], "");
        }
    }
    //特意为select2 带搜索功能的下拉框清空下拉数据
    if(index == 0){
        $("#projectID").select2("val", ""); 
        $("#alter_projectID").select2("val", ""); 
    }
    //清空默认值
    this.Default.length = 0;
    
  },
  //select设置
  SetSelect: function(oSel, menu, value) {
    oSel.options.length = 0; oSel.disabled = false;
    if(this.ShowEmpty){ oSel.appendChild(GetOption("", this.EmptyText)); }
    if(menu.length <= 0){ oSel.disabled = true; return; }
    
    Each(menu, function(o){
        var op = GetOption(o.val, o.txt ? o.txt : o.val); op.selected = (value == op.value);
        oSel.appendChild(op);
    });    
  },
  //添加菜单
  Add: function(menu) {
    this.Menu[this.Menu.length] = menu;
    this.ReSet();
  },
  //删除菜单
  Delete: function(index) {
    if(index < 0 || index >= this.Menu.length) return;
    for(var i = index, len = this.Menu.length - 1; i < len; i++){ this.Menu[i] = this.Menu[i + 1]; }
    this.Menu.pop()
    this.ReSet();
  }
};





//SELECT
</script>

	<!--  <h1 style="font-size: 30px; font-weight: bold;">工数录入</h1>-->
	<p>&nbsp;</p>
	<div id="tabs">
		<input type="hidden" id="deadline" name="deadline" value=""></input>
		<div id="tabs-2">
			<div id="example" style="margin: auto; width: 80%;">

				<br>
				<table style="width: 100%; padding: 5px;"
					class="ui-widget-header ui-corner-all"
					style="padding:3px; vertical-align: middle; white-space:nowrap; overflow: hidden;">
					<td style="width: 30%;"></td>
					<td style="width: 8%;" align="center">
						<button id="BtnPreviousMonth">上个月</button>
					</td>
					<td style="width: 19%;" align="center">
						<div id="dateSelect" style="color: #086BB2; font-size: 18px;">111111</div>
					</td>
					<td style="width: 8%;" align="center">
						<button id="BtnNextMonth">下个月</button>
					</td>
					<td style="width: 8%;">
						<button id="BtnCurrentMonth">本月</button>
					</td>
					<td style="width: 27%;"></td>
				</table>

				<br>

				<div id="mycal"></div>

			</div>

			<!-- debugging-->
			<div id="calDebug"></div>

			<!-- Add event modal form -->
			<style type="text/css">
//
label,input.text,select {
	display: block;
}

fieldset {
	padding: 0;
	border: 0;
	margin-top: 25px;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
}
</style>

			<!--CCH FORM**********************************add-event-form**********************************************************-->

			<div id="add-event-form" title="新增工数">
				<form>
					<b><p name="AddEventCurrentDay" id="AddEventCurrentDay"
							class="validateTips" align="center"
							style="color: #000000; font-size: 14px;">All form fields are
							required.</p></b> <input type=hidden id="date" name="date"
						value=clickDate>

					<fieldset>
						<!--Custom dialog-->


						<table style="width: 100%; padding: 5px;">
							<tr>
								<td style="width: 20%;"><label id="customDemo_string_categoryName" >项目类别</label></td>
								<td style="width: 80%;"><select name="categoryName"
									id="categoryName" class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;">
								</select>
								<td>
							</tr>

							<tr>
								<td style="width: 20%;"><label id="customDemo_string_projectID" >项目名称</label></td>
								<td style="width: 80%;"><select name="projectID"
									id="projectID">

								</select>
								<td>
							</tr>

							<tr>
								<td style="width: 20%;"><label id="customDemo_string_taskName" >作业类型</label></td>
								<td style="width: 80%;"><select name="taskName"
									id="taskName" class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;">
								</select>
								<td>
							</tr>
							<!--  
					<tr>
						<td  style="width: 20%;">
							<label><label>作业进度(%)</label></label>
						</td>
						<td  style="width: 80%;">
							<input type="text" name="taskRate" id="TaskRatio" style="margin-bottom:12px; width:95%; padding: .4em;" />
						<td>						
					</tr>
					-->
							<tr>
								<td style="width: 20%;"><label id="customDemo_string_TaskTimes" >工作时间(小时)</label></td>
								<td style="width: 80%;"><input type="text" name="times"
									id="TaskTimes" class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;" />
								<td>
							</tr>

						</table>
				</form>
				</fieldset>
				</form>
			</div>

			<!--********************************************************************************************-->

			<div id="alter-event-form" title="修改工数">
				<form>
					<b><p name="AlterEventCurrentDay" id="AlterEventCurrentDay"
							class="validateTips" align="center"
							style="color: #e64545; font-size: 14px;">All form fields are
							required.</p></b> <input type="hidden" id="alter_date" name="alter_date"
						value=clickDate>

					<fieldset>
						<table style="width: 100%; padding: 5px;">
							<tr>
								<td style="width: 20%;"><label id="customDemo_string_altercategoryName" >项目类别</label></td>
								<td style="width: 80%;"><select name="alter_categoryName"
									id="alter_categoryName"
									class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;">
								</select>
								<td>
							</tr>

							<tr>
								<td style="width: 20%;"><label id="customDemo_string_alterprojectID" >项目名称</label></td>
								<td style="width: 80%;"><select name="alter_projectID"
									id="alter_projectID">
								</select>
								<td>
							</tr>

							<tr>
								<td style="width: 20%;"><label id="customDemo_string_altertaskName" >作业类型</label></td>
								<td style="width: 80%;"><select name="alter_taskName"
									id="alter_taskName"
									class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;">
								</select>
								<td>
							</tr>
							<!--  
					<tr>
						<td style="width: 20%;">
							<label><label>作业进度(%)</label></label>
						</td>
						<td style="width: 80%;">
							<input type="text" name="alter_taskRate" id="alter_TaskRatio" style="margin-bottom:12px; width:95%; padding: .4em;"/>
						<td>						
					</tr>
					-->
							<tr>
								<td style="width: 20%;"><label id="customDemo_string_alterTaskTimes" >工作时间(小时)</label></td>
								<td style="width: 80%;"><input type="text"
									name="alter_times" id="alter_TaskTimes"
									class="text ui-widget-content ui-corner-all"
									style="margin-bottom: 12px; width: 95%; padding: .4em;" />
								<td>
							</tr>
						</table>
				</form>
				</fieldset>
				</form>
			</div>
			<!--  工数显示Dialog -->
			<div id="display-event-form" title="工数信息"></div>

			<!--  不能修改的工数显示Dialog -->
			<div id="display-event-nochange-form" title="工数信息"></div>

			<!--  警告 Dialog -->
			<div id="notice-dialog" title="Notice"></div>

			<p>&nbsp;</p>

		</div>


	</div>


	<p>&nbsp;</p>


</body>
</html>
