<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'staffQuery.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div id="add-event-form" title="工数查询">
		
			
				<table style="width:100%; padding:5px;">
					<tr>
						<td style="width: 20%;">
							<label><label>起始月份</label></label>
						</td>
						<td  style="width: 20%;" width="10%" height="10px">
							<select name="startMonth" id="startMonth" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
							</select>				
						<td>					
					</tr>
					
					<tr>
						<td  style="width: 20%;">
							<label><label>项目名称</label></label>
						</td>
						<td  style="width: 80%;">
							<select name="projectID" id="projectID" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">

							</select>				
						<td>						
					</tr>
					
					<tr>
						<td  style="width: 20%;">
							<label><label>作业类型</label></label>
						</td>
						<td  style="width: 80%;">
							<select name="taskName" id="taskName" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
							</select>				
						<td>						
					</tr>
					
					<tr>
						<td  style="width: 20%;">
							<label><label>作业进度(%)</label></label>
						</td>
						<td  style="width: 80%;">
							<input type="text" name="taskRate" id="TaskRatio" style="margin-bottom:12px; width:95%; padding: .4em;" />
						<td>						
					</tr>
					
					<tr>
						<td  style="width: 20%;">
							<label><label>工作时间(小时)</label></label>
						</td>
						<td  style="width: 80%;">
							<input type="text" name="times" id="TaskTimes" style="margin-bottom:12px; width:95%; padding: .4em;" />
						<td>						
					</tr>
					
				</table>
  </body>
</html>
