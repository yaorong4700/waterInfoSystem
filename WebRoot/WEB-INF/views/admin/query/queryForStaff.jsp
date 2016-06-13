<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryForStaff.jsp' starting page</title>
    
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
    <form id="form1" name="form1" method="post" action="list2.jsp">
  
  <table width="1201" height="20" border="1" style="height: 119px; width: 582px">
    <tr>
      <td colspan="2" style="height: 78px">
      <label></label><p style="width: 471px">
          <label>起始月份
          <select name="select1">
          </select>
          <select name="select2">
          </select>
          </label>
          <label>截止月份
          <select name="select3">
          </select>
          <select name="select4">
          </select>
          </label>
          <label> </label>
                
        </p>
      
        <p style="width: 567px">
          <label>
          选择查询选项：
          <input type="radio" name="radiobutton1" value="radiobutton" />
	所有项目</label>
          <label>
          <input type="radio" name="radiobutton1" value="radiobutton" />
          作业类型</label>
          <label>
          <input type="radio" name="radiobutton1" value="radiobutton" />
          Username</label>
          <label></label><input type="submit" name="Submit" value="查询">
          </p>
          </td>
          </tr>
          </table>
          </form>
          <table>
          </table>
          
  </body>
</html>
