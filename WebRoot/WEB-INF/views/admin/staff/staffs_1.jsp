<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
		<link type="text/css" rel="stylesheet" href="css/main.css" />
	</head>
	<body>
	<div class="page_and_btn">
	    <span >员工姓名</span><input id = "nameSearch" value="${requestInfo.staffName }">
	            <a href="javascript:getPage(1);" class="myBtn"><em>搜索</em>
				</a>
	</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table" >
			<tr class="main_head">
				<th style="width: 10%;">
					序号
				</th>
				<th style="width: 20%;">
					员工姓名
				</th>
				<th style="width: 10%;">
					性别
				</th>
				<th style="width: 10%;">
					职位
				</th>
				<th style="width: 10%;">
					部门
				</th>
				<th style="width: 20%;">
					操作
				</th>
			</tr>
			<c:choose>
				<c:when test="${not empty staffList}">
					<c:forEach items="${staffList}" var="staff" varStatus="vs">
						<tr class="main_info" id="tr${staff.staffID }">
							<td>
								${vs.index+1}
							</td>
							<td>
								${staff.name }
							</td>
							<td>
								${staff.gender>0?"男":"女" }
							</td>
							<td>
								${staff.position}
							</td>
							<td>
								${staff.department}
							</td>
							<td>
								<a href="###" onclick="editUser(${staff.staffID  })">修改</a> |
								<a href="###" onclick="delUser(${staff.staffID  })">删除</a> |
								<a href="###" onclick="more(${staff.staffID })">详细信息</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="4">
							没有相关数据
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>

		<div class="page_and_btn">
			<div>
				<a href="javascript:addUser();" class="myBtn"><em>新增</em>
				</a>
				
			</div>
		</div>
<c:choose>
	<c:when test="${results.pageNow < 2}">
		<< 前页 1 
	</c:when>
	<c:otherwise>
		<a   href="javascript:getPage(${(results.pageNow-1)});"><< 前页</a> 
		<a   href="javascript:getPage(1);">1</a> 
	</c:otherwise>
</c:choose>
<c:if test="${results.startPage > 2}">
	...
</c:if>
<c:forEach var="i" begin="${results.startPage}" end="${results.endPage}" step="1">
	<c:choose>
		<c:when test="${i == 1 || i == results.pageCount}"></c:when>
		<c:when test="${i == results.pageNow}">
			  ${i} 
		</c:when>
		<c:otherwise>
			 <a href="javascript:getPage(${(i)});">${i}</a> 
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${(results.pageNow+5) < results.pageCount}">
	...
</c:if>
<c:if test="${results.pageCount > 1}">
	<c:choose>
		<c:when test="${results.pageNow > (results.pageCount-1)}">
			${results.pageCount}  后页>>
		</c:when>
		<c:otherwise>
			<a  href="javascript:getPage(${(results.pageCount)});" >${results.pageCount}</a> 
			 <a href="javascript:getPage(${results.pageNow+1});"  >后页>></a>
		</c:otherwise>
	</c:choose>
</c:if>
<hr>
		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript"
			src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$(".main_info:even").addClass("main_table_even");
		});
		
		  //跳转

      function getPage(pageNow){
       
         location.href="staff.do?pageNow="+pageNow+"&staffName="+$("#nameSearch").attr("value");
        
      }
 
		
		function addUser(){
			var dg = new $.dialog({
				title:'新增用户',
				id:'user_new',
				width:500,
				height:600,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/add.do'
				});
    		dg.ShowDialog();
		}
		
		function editUser(staffId){
			var dg = new $.dialog({
				title:'修改用户',
				id:'user_edit',
				width:500,
				height:600,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/edit.do?id='+staffId
				});
    		dg.ShowDialog();
		}
		
		function more(staffId){
			var dg = new $.dialog({
				title:'详细信息',
				id:'user_more',
				width:500,
				height:600,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'staff/more.do?id='+staffId
				});
    		dg.ShowDialog();
		}
		
		function delUser(staffId){
			if(confirm("确定要删除该用户吗？")){
					flag = true;
			}
			if(flag){
			var url = "staff/delete.do?id="+staffId;
				$.get(url,function(data){
					document.location.reload();
				});
			}
		}

	</script>
	</body>
</html>