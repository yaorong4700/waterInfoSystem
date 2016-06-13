<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/flexigrid.pack.css" />
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/Flexigrid/flexigrid.pack.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
	<style type="text/css">
       body,table{
         font-size:13px;
       }
    </style>
	<script type="text/javascript">
	$(document).ready(function(){	
		/**
	 * add event dialog
	 */
	
	
	$("#gridTable").flexigrid({
			url : 'message/listMessage.do',
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
				display : '提示信息',
				name : 'message',
				id:'message',
				width : 1000,
				sortable : false,
				align : 'left'
			}, {
				display : '状态',
				name : 'state',
				width : 100,
				sortable : true,
				align : 'left'
			} ],
			buttons : [ {
				name : 'Add',
				bclass : 'add',
				onpress : test
			}, {
				name : 'Delete',
				bclass : 'delete',
				onpress : test
			},{
				name : 'Edit',
				bclass : 'edit',
				onpress : test
			}, {
				separator : true
			} ],
			useRp: true,
			pagestat : '显示第 {from} 条到 {to} 条,共 {total} 条数据',
			sortname : "name",
			title: '信息设置',
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			usepager : true,
			query: '',
			useRp : true,
			nomsg : '没有数据存在!',
			rp : 15,
			showTableToggleBtn : true,
			onRowDblclick : rowDblclick,  
			procmsg : '正在处理,请稍候 ...', 
			searchitems : [  {display : '提示信息',name : 'messahe',isdefault : true}, {display : '状态',name : 'state'} ],
			width : 1400,
			height : 'auto'
		});
});
		 function rowDblclick(rowData){
		    var id = $(rowData).data("id").toString(); 
		     var dg = new $.dialog({
				        title:'详细信息',
				        id:'user_more',
				        width:500,
				        height:200,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        page:'message/more.do?id='+id
				    });
    		dg.ShowDialog();
         }

		
		function test(com, grid) {
			if (com == 'Delete') {
			 selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert('请选择一条记录!');
					    return;
				    }
				    name = '';
				    $('.trSelected td:nth-child(2) div', grid).each(function(i) {
					        if (i)
						        name += ',';
					        name += $(this).text();
				        });
				    ids = '';
				    $('.trSelected td:nth-child(1) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    if (confirm("确定删除[" + name + "]?")) {
					     $.ajax({ 
						        type: "post", 
						        url: "message/messageDelete.do?ids="+ids, 
						        dataType: "json", 
						        success: function (data) {
	                                if(data.result == "success"){
	                                     $('#gridTable').flexReload();
	                                }else{
	                                    alert("fail");
                                    }
						        }, 
						        error: function (XMLHttpRequest, textStatus, errorThrown) { 
						            alert("网络传输错误,请刷新或稍后重试!!"); 
						        } 
						    });
				    }
			} else if (com == 'Add') {
				 var dg = new $.dialog({
				title:'新增提示信息',
				id:'user_new',
				width:500,
				height:200,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'message/add.do'
				});
    		dg.ShowDialog();
			}else if(com == 'Edit'){
			     selected_count = $('.trSelected', grid).length;
				    if (selected_count == 0) {
					    alert('请选择一条记录!');
					    return;
				    }
				    if (selected_count > 1) {
					    alert('抱歉只能同时修改一条记录!');
					    return;
				    }
				    ids = "";
				     $('.trSelected td:nth-child(1) div', grid).each(function(i) {
					        if (i)
						        ids += ',';
					        ids += $(this).text();
				        })
				    
				    var dg = new $.dialog({
				        title:'修改提示信息',
				        id:'user_edit',
				        width:500,
				        height:200,
				        iconTitle:false,
				        cover:true,
				        maxBtn:false,
				        xButton:true,
				        resize:false,
				        page:'message/edit.do?id='+ids
				    });
    		dg.ShowDialog();
			}
		}
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