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
	<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="js/js_user/com.js"></script>
	<script type="text/javascript" src="js/cookie/cookie.js"></script>
	
	<style type="text/css">
       body,table{
         font-size:13px;
       }
       label{
         color:black;
       }
    </style>
	<script type="text/javascript">
	
	var language = getCookieValue("language");
	var tblNo ="";
	var tblCategory = "";
	var tblComponentName = "";
	var tblSort = "";
	var tblComponentID = "";

	var tblpagestat="";
	var tbltitle="";
	var I0004="";
	var I0005="";
	var I0006="";
	var rowtitle="";
	var tbldownloadtitle="";
	var I0007="";
	var tbluploadtitle="";
	var I0008="";
	var tbldeleteinfo1="";
	var tbldeleteinfo2="";
	var I0002="";
	var tbladdcomponent = "";
	var I0009 = "";
	var tblupdcomponent="";
	var tblcancel="";
	var tblIsVisible="";
	
    function successReload(){
	    $('#gridTable').flexOptions().flexReload();
	}
	$(document).ready(function(){
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);
		
		//U/L时的动作
		$('#upload').click( function () {
		    upload();
		});
		//D/L的动作
		$('#download').click( function () {
		    download();
		});
		/**
		* add event dialog
		*/
		$("#query").click(function() {
			var QueryRoleFlag = $("#QueryRoleFlag").val();
			if(QueryRoleFlag == 1){
				var categoryID   = $("#categoryID").val();
		        var componentSortID  = $("#componentSort").val();
		        var componentID    = $("#componentID").val();
				var isVisible = $("#isVisible").val();
				
		        var params = [{"name" : "categoryID","value" : categoryID}
		                     ,{"name" : "componentSort","value" : componentSortID}
		                     ,{"name" : "componentID","value" : componentID}
		                     ,{"name" : "isVisible","value" : isVisible}
		                     ,{"name" : "QueryRoleFlag","value" : QueryRoleFlag}
		                     ];
		        $('#gridTable').flexOptions({params : params, newp : 1}).flexReload();
			}else{
				return "";
			}
			
			
    	});
	
		//CT追加button处理
		$("#add").click(function() {
			var dg = new $.dialog({
				title:tbladdcomponent,
				id:'user_new',
				width:500,
				height:400,
				iconTitle:false,
				cover:true,
				maxBtn:false,
				xButton:true,
				resize:false,
				page:'component/add.do'
				});
			dg.ShowDialog();
		});
		//编集button处理
		$("#edit").click(function() {
			selected_count = $('#gridTable .trSelected').length;
			if (selected_count == 0) {
			    alert(I0008);
			    return;
			}
	    	if (selected_count > 1) {
				alert(I0009);
			    return;
		    }
			ids = "";
		    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
				if (i) ids += ',';
				ids += $(this).text();
		    })
		    var dg = new $.dialog({
		        title:tblupdcomponent,
		        id:'user_edit',
		        width:500,
		        height:400,
		        iconTitle:false,
		        cover:true,
		        maxBtn:false,
		        xButton:true,
		        resize:false,
		        page:'component/edit.do?id='+ids
		    });
			dg.ShowDialog();
		});
		//删除button处理
		$("#delete").click(function() {
			selected_count = $('#gridTable .trSelected').length;
		    if (selected_count == 0) {
			    alert(I0008);
			    return;
		    }
		    name = '';
		    $('#gridTable .trSelected td:nth-child(5) div').each(function(i) {
		    	if (i)  name += ',';
				name += $(this).text();
			});
		    ids = '';
		    $('#gridTable .trSelected td:nth-child(1) div').each(function(i) {
				if (i) ids += ','; 
				ids += $(this).text();
			});
		    if (confirm(tbldeleteinfo1 + name + tbldeleteinfo2)) {
				$.ajax({ 
				        type: "post", 
				        url: "component/delete.do?ids="+ids, 
				        dataType: "json", 
				        success: function (data) {
	                        if(data.result == "success"){
	                            alert("success");
	                            $('#gridTable').flexReload();
	                        }else{
	                            alert("fail");
	                        }
				        }, 
				        error: function (XMLHttpRequest, textStatus, errorThrown) { 
				            alert(I0002); 
				        } 
				});
		    }
		});
	
		$("#gridTable").flexigrid({
			url : 'component/listComponent.do',
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
				display : tblNo,
				name : 'No',
				id:'No',
				width : 50,
				sortable : false,
				align : 'center',
			}, 
			{
				display : tblIsVisible,
				name : 'isVisible',
				id:'isVisible',
				width : 80,
				sortable : false,
				align : 'center',
			},
			{
				display : tblCategory,
				name : 'categoryName',
				id:'categoryName',
				width : 200,
				sortable : false,
				align : 'left'
			}, {
				display : tblComponentName,
				name : 'componentName',
				width : 300,
				sortable : true,
				align : 'left'
			}, {
				display : tblSort,
				name : 'componentSort',
				width : 250,
				sortable : true,
				align : 'left'
			},{
				display : tblComponentID,
				name : 'componentID',
				width : 200,
				sortable : false,
				align : 'left'
			} ],
			useRp: true,
			pagestat : tblpagestat,
			sortname : "name",
			title: tbltitle,
			method : 'POST', 
			showToggleBtn : false,
			sortorder : "asc",
			autoload : false,
			usepager : true,
			query: '',
			useRp : true,
			nomsg : I0004,
			rp : 15,
			showTableToggleBtn : true,
			procmsg : I0005, 
			width : 1080,
			height : 'auto'
		});
	});
	function download() {
		if (confirm(I0006)) {
			var path = "component/downloadComponent.do"
			var dg = new $.dialog({
					title:tbldownloadtitle,
			        id:'user_download',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        cancelBtnTxt:tblcancel,
			        resize:false,	
			        loadingText:I0007,
			        page:path
			    });
			dg.ShowDialog(); 
		}
	}
		
	function upload(){
		var dg = new $.dialog({
			title:tbluploadtitle,
			id:'user_upload',
			width:500,
			height:400,
			iconTitle:false,
			cover:true,
			maxBtn:false,
			xButton:true,
			cancelBtnTxt:tblcancel,
			resize:false,
			page:'component/upload.do'
			});
   		dg.ShowDialog();
	}
						
	//国际化
	function setPageByLanguage(){
		
		$('#component_string_category').html($.i18n.prop('component_string_category'));
		$('#component_string_sort').html($.i18n.prop('component_string_sort'));
		$('#component_string_id').html($.i18n.prop('component_string_id'));
		$('#component_string_select_enavi').html($.i18n.prop('component_string_select_enavi'));
		$('#component_string_select_soft').html($.i18n.prop('component_string_select_soft'));
		$('#component_string_select_hard').html($.i18n.prop('component_string_select_hard'));
		$('#component_string_visible_1').html($.i18n.prop('component_string_visible_1'));
		$('#component_string_visible_2').html($.i18n.prop('component_string_visible_2'));
		
		$('#query').val($.i18n.prop('component_string_btnQuery'));
		$('#add').val($.i18n.prop('component_string_btnAdd'));
		$('#edit').val($.i18n.prop('component_string_btnEdit'));
		$('#delete').val($.i18n.prop('component_string_btnDelete'));
		$('#upload').val($.i18n.prop('component_string_btnUpload'));
		$('#download').val($.i18n.prop('component_string_btnDownload'));
		
		tblNo = $.i18n.prop('component_string_tbl_No');
		tblCategory = $.i18n.prop('component_string_tbl_category');
		tblComponentName = $.i18n.prop('component_string_tbl_componentName');
		tblSort = $.i18n.prop('component_string_tbl_sort');
		tblComponentID = $.i18n.prop('component_string_tbl_componentID');
		tblIsVisible = $.i18n.prop('component_string_tbl_isVisible');
		
		tblpagestat=$.i18n.prop('component_string_tblpagestat');
		tbltitle=$.i18n.prop('component_string_tbl_title');
		I0004=$.i18n.prop('I0004');
		I0005=$.i18n.prop('I0005');;
		I0006=$.i18n.prop('I0006');
		rowtitle=$.i18n.prop('component_string_tblrowtitle');
		tbldownloadtitle=$.i18n.prop('component_string_tbldownloadtitle');
		I0007=$.i18n.prop('I0007');
		tbluploadtitle=$.i18n.prop('component_string_tbluploadtitle');
		I0008=$.i18n.prop('I0008');
		tbldeleteinfo1=$.i18n.prop('component_string_tbldeleteinfo1');
		tbldeleteinfo2=$.i18n.prop('component_string_tbldeleteinfo2');
		I0002=$.i18n.prop('I0002');
		tbladdcomponent = $.i18n.prop('component_string_tbladdcomponent');
		I0009 = $.i18n.prop('I0009');
		tblupdcomponent=$.i18n.prop('component_string_tblupdcomponent');
		tblcancel=$.i18n.prop('component_string_tblcancel');

	}
	</script>
</head>
<body style="width:1100px;" >
<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
<input type="hidden" id ="AlterRoleFlag" value = "${AlterRoleFlag}">
<input type="hidden" id ="UploadRoleFlag" value = "${UploadRoleFlag}">
<input type="hidden" id ="DownloadRoleFlag" value = "${DownloadRoleFlag}">
<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
	<p style="height:15px;"></p>
 	<table id="detailTable" style="width:950px;" >
 		<tr>
 			<td style="width:100px;">
                <label id="component_string_visible">表示</label>
                <select name="isVisible" id="isVisible" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:80px; padding: .2em;" >
					<OPTION VALUE="" ></OPTION>
					<OPTION id="component_string_visible_1" VALUE="1">表示</OPTION>
					<OPTION id="component_string_visible_2" VALUE="0">非表示</OPTION>
                </select>
            </td>
			<td style="width:150px;">
                <label id="component_string_category">段階</label>
                <select name="categoryID" id="categoryID" class="text ui-widget-content ui-corner-all"
                        style="margin-bottom:5px; width:140px; padding: .2em;" onChange="changeList()">
                    <option value="" SELECTED></option>
                    <c:forEach items="${categoryList}" var="category" varStatus="vs">
                    <option value="${category.categoryID}" >${category.categoryName}</OPTION>
                    </c:forEach>
                </select>
            </td>
			<td style="width:100px;">
				<label id="component_string_sort">分類</label>
				<select name="componentSort" id="componentSort"  style="margin-bottom:5px; width:70px; padding: .2em;">
					<option value="" SELECTED></option>
                    <c:forEach items="${componentSortList}" var="csort" varStatus="vs">
                    	<option value="${csort.componentSortID}" >${csort.componentSortName}</OPTION>
                    </c:forEach>
				</select>
			</td>		
            <td style="width:50px;">
                <label id="component_string_id">ID</label>
			    <input type="text" id="componentID" style="margin-bottom:5px; width:68px; padding: .2em;" maxlength="20"/>
			</td>
 			<td style="width:30px;">
            <c:if test="${QueryRoleFlag == '1'}">
	            <c:if test="${SpecialRole1Flag == '1'}">
                <input id="query" type="button" value="查询">
				</c:if>
			</c:if>
            </td>
            <c:if test="${AlterRoleFlag == '1'}">
            <c:if test="${SpecialRole1Flag == '1'}">
            <td style="width:30px;">
                <input id="add" type="button" value="追加">
            </td>
            <td style="width:30px;">
                <input id="edit" type="button" value="编集">
            </td>
            <td style="width:30px;">
                <input id="delete" type="button" value="删除">
            </td>
            </c:if>
            </c:if>
            <c:if test="${UploadRoleFlag == '1'}">
            <c:if test="${SpecialRole1Flag == '1'}">
            <td style="width:30px;">
				<input id="upload" type="button" value="U/L">
			</td>
			</c:if>
			</c:if>
			<c:if test="${DownloadRoleFlag == '1'}">
            <c:if test="${SpecialRole1Flag == '1'}">
            <td style="width:30px;">
                <input id="download" type="button" value="D/L">
            </td>
            </c:if>
            </c:if>
 		</tr>
 	</table> 	
	<p style="height:10px;"></p>
	<table id="gridTable" style="width:1080px;"></table>
</body>
</html>