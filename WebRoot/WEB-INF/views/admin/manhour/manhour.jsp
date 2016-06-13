<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>工数显示</title>
		<link type="text/css" rel="stylesheet" href="css/main.css" />
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="main_table">
			<tr class="main_head">
				<th>
					序号
				</th>
				<th>
					员工
				</th>
				<th >
					时间
				</th>
				<th>
					项目类别
				</th>
				<th>
					项目名称
				</th>
				<th>
					活动类型
				</th>
				<th>
					项目进度
				</th>
				<th>
					时间
				</th>
				<td> 
				          <strong>填写时间</strong> 
				</td>
				<td> 
				          <strong>备注</strong> 
				</td>
			</tr>
			<c:choose>
				<c:when test="${not empty manHourList}">
					<c:forEach items="${manHourList}" var="ManhourShowData" varStatus="vs">
						<tr class="main_info" id="tr${ManhourShowData.id}">
							<td>
								${vs.index+1}
							</td>
							<td>
								${ManhourShowData.name }
							</td>
							<td>
								${ManhourShowData.date }
							</td>
							<td>
								${ManhourShowData.category }
							</td>
							<td>
								${ManhourShowData.projectName }
							</td>
							<td>
								${ManhourShowData.task }
							</td>
							<td>
								${ManhourShowData.taskRate }
							</td>
							<td>
								${ManhourShowData.times }
							</td>
							<td>
								${ManhourShowData.insertTime }
							</td>
							<td>
								${ManhourShowData.memo}
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

		<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript"
			src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_blue"></script>
	</body>
</html>