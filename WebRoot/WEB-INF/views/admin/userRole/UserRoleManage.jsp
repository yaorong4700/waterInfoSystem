<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<title>欢迎</title>
	<link rel="stylesheet" type="text/css" href="js/ext-4.0.7-gpl/resources/css/ext-all.css" />
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/lhgdialog/lhgdialog.min.js?t=self&s=areo_gray"></script>
	<!-- 以下2个javascript为国际化接口 -->
	<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
	<script type="text/javascript" src="js/js_user/com.js"></script>
	<script type="text/javascript" src="js/cookie/cookie.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/ext-all.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/CellEditing.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/ComboPageSize.js"></script>
	<script type="text/javascript" src="js/ext-4.0.7-gpl/GridTitleAlign.js"></script>
	<style type="text/css">
       body,table{
         	font-size:13px;
       }
    </style>
    <script type="text/javascript">
  	//禁止后退键 作用于Firefox、Opera   
	document.onkeypress=banBackSpace;  
	//禁止后退键  作用于IE、Chrome   
	document.onkeydown=banBackSpace;
	var language = getCookieValue("language");
	var PageHandle = {
			HasSearch: 0,
			String_i18n: {
				GridTitle_PageName: "",
				GridTitle_QueryRoleFlag: "",
				GridTitle_AlterRoleFlag: "",
				GridTitle_SpecialRole1Flag: "",
				GridTitle_SpecialRole2Flag: "",
				GridTitle_SpecialRole3Flag: "",
				GridTitle_SpecialRole4Flag: ""
			},
			gridModel: Ext.define('UserRoleManage', {
		        extend: 'Ext.data.Model',
		        fields: ["No","pageName","showRoleFlag","queryRoleFlag","alterRoleFlag","uploadRoleFlag","downloadRoleFlag","specialRole1Flag","specialRole2Flag","specialRole3Flag","specialRole1","specialRole2","specialRole3","departmentFlag","pageID","keyCode","roleName"]
		    }),
		    store: null,
			storeCreat: function(){
				PageHandle.store = Ext.create('Ext.data.Store', {
					model: PageHandle.gridModel,
					proxy: {
						type: 'ajax',
						url: 'UserRoleManage/init.do',
						reader: {
							type: 'json',
							root: 'result'
						}	
					}
				,autoLoad: true
				});
			},
			grid: null,
		    render: function(){
		    		PageHandle.grid = new Ext.grid.GridPanel( {
			        store: PageHandle.store,
			        stateful: true,
			        stateId: 'stateGrid',
			        columns: [
						new Ext.grid.RowNumberer({ header: PageHandle.String_i18n.GridTitle_No , width: 0}),
			            {
							text :PageHandle.String_i18n.GridTitle_PageName,
			                width : 200,
			                sortable : false,
			                dataIndex: 'pageName',
			                titleAlign: 'center'
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_ShowRoleFlag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex: 'showRoleFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(record.get('pageID') == "DepartFileExport"){
			                		str = "";
			                	} else {
			                		if(value == 1){
			                			str ="<input  type='checkbox' disable='false' checked='checked'  value='showRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                		} else {
			                			str ="<input  type='checkbox' disable='false'  value='showRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                		}
			                	}
			                    return str;
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_QueryRoleFlag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex: 'queryRoleFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(record.get('pageID') == "DepartFileExport"){
			                		str = "";
			                	} else {
			                		if(value == 1){
			                			str ="<input  type='checkbox' disable='false' checked='checked'  value='queryRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                		} else {
			                			str ="<input  type='checkbox' disable='false'  value='queryRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                		}
			                	}
			                    return str;
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_AlterRoleFlag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'alterRoleFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(record.get('pageID') == "DepartFileExport" 
			                			|| record.get('pageID') == "CTMachineTypeUpload" 
			                			|| record.get('pageID') == "SkillmapUpload"
			                			|| record.get('pageID') == "SelectStaffWNDetailHistory"){
			                		str = "";
			                	} else {
			                		if(value == 1){
				                		str ="<input  type='checkbox' disable='false' checked='checked' value='alterRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
				                	} else {
				                		str ="<input  type='checkbox' disable='false'  value='alterRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
				                	}
			                	}
			                    return str;
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_UploadRoleFlag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'uploadRoleFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
	                			if(value == 1){
	                				str ="<input  type='checkbox' disable='false' checked='checked' value='uploadRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
	                			} else {
	                				str ="<input  type='checkbox' disable='false' value='uploadRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
	                			}
	                         	return str;
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_DownloadRoleFlag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'downloadRoleFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(value == 1){
			                		str = "<input  type='checkbox' disable='false' checked='checked' value='downloadRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                	} else {
			                		str = "<input  type='checkbox' disable='false' value='downloadRoleFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                	}
			                    return str;
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_SpecialRole1Flag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'specialRole1',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(value != "")
			                       	{
			                			if(record.get('specialRole1Flag') == 1){
			                				str ="<input  type='checkbox' disable='false' checked='checked' value='specialRole1Flag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                			} else {
			                				str ="<input  type='checkbox' disable='false' value='specialRole1Flag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                			}
			                         	return str;
			    	    			} else {
			    	    				return null;
			    	    			}
			    	    	}
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_SpecialRole2Flag,
			                width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'specialRole2',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(value != "")
			                       	{
			                			if(record.get('specialRole2Flag') == 1){
			                				str ="<input  type='checkbox' disable='false' checked='checked'  value='specialRole2Flag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                			} else {
			                				str ="<input  type='checkbox' disable='false'  value='specialRole2Flag' onclick='checkBoxClick(this,"+rowIndex+")' />";
			                			}
			                         	return str;
			    	    			} else {
			    	    				return null;
			    	    			}
			    	    	}
			            },
			            /* {
							text :PageHandle.String_i18n.GridTitle_SpecialRole3Flag,
			                width : 100,
			                sortable : false,
			                align : 'left',
			                dataIndex:'specialRole3',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store)
			    	    	{
			                	var str;
			                	if(value != "")
			                       	{
			                			if(record.get('specialRole3Flag') == 1){
			                				str ="<input  type='checkbox' disable='false' checked='checked'  value='specialRole3Flag' onclick='checkBoxClick(this,"+rowIndex+")' />" + value;
			                			} else {
			                				str ="<input  type='checkbox' disable='false'  value='specialRole3Flag' onclick='checkBoxClick(this,"+rowIndex+")' />" + value;
			                			}
			                         	return str;
			    	    			} else {
			    	    				return null;
			    	    			}
			    	    	}
			            }, */
			            {
							text :PageHandle.String_i18n.GridTitle_Department,
							width : 100,
			                sortable : false,
			                align : 'center',
			                dataIndex:'departmentFlag',
			                titleAlign: 'center',
			                renderer:function(value,cellmeta,record,rowIndex,colIndex,store){
			                	var str;
			                	if(record.get('pageID') == "UserRoleManage" 
		                			|| record.get('pageID') == "Default"){
		                			str = "";
			                	} else {
			                		if(value == 1){
		                				str ="<input  type='checkbox' disable='false' checked='checked'  value='departmentFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
		                			} else {
		                				str ="<input  type='checkbox' disable='false'  value='departmentFlag' onclick='checkBoxClick(this,"+rowIndex+")' />";
		                			}
			                	}
			                	return str;
			                }
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_PageId,
			                width : 0,
			                sortable : false,
			                dataIndex: 'pageID',
			                align : 'left',
			                hidden : true
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_KeyCode,
			                width : 0,
			                sortable : false,
			                dataIndex: 'keyCode',
			                align : 'left',
			                hidden : true
			            },
			            {
							text :PageHandle.String_i18n.GridTitle_RoleName,
			                width : 0,
			                sortable : false,
			                dataIndex: 'roleName',
			                align : 'left',
			                hidden : true
			            }
			        ],
			        width: 1010,
			        height: getBrowserHeight() -50,
			        columnLines: true,
			        title: PageHandle.String_i18n.GridTitle,
			        viewConfig : {
			            stripeRows: true
			        },
			        renderTo: 'gridTable'
			    });
			}
		};
    
    //初始化
    Ext.onReady(function() {
    	if (language!="CN" && language!="JP" && language!="EN"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);
		
		Ext.QuickTips.init();
		
		$("#gridTable").html("");
	  	PageHandle.render();
	  	$("#select_role").focus();
	  	if($("#AlterRoleFlag").val() == 1){
	  		$("#btn_add").attr("disabled", false);
	  		$("#btn_delete").attr("disabled", true);
	  		$("#btn_save").attr("disabled", true);
	  	} else {
	  		$("#btn_add").attr("disabled", true);
	  		$("#btn_delete").attr("disabled", true);
	  		$("#btn_save").attr("disabled", true);
	  	}
	  	
    });
    
  //实现按enter键检索
	function enter_down(){
	   if(event.keyCode=="13"){//enter
		   doSearch();
	   }else{
	   }
	 }
    
	//检索
	function doSearch() {
		var keyCode = document.getElementById("select_role").value;
		var roleName = $("#select_role option:selected").text();
		document.getElementById("text_roleName").value = roleName;
		PageHandle.storeCreat();
		PageHandle.initPageSize = PageHandle.store.pageSize;
		PageHandle.store = Ext.create('Ext.data.Store', {
			model: 'UserRoleManage',
			proxy: {
				type: 'ajax',
				url: 'UserRoleManage/searchClick.do',
				extraParams: {"keyCode":keyCode},
				reader: {
					type: 'json',
					root: 'result'
				}
			}
		});
		$("#gridTable").html("");
		PageHandle.render();
		PageHandle.store.loadPage(1);
		PageHandle.HasSearch = 1;
		if($("#AlterRoleFlag").val() == 1){
			$("#btn_add").attr("disabled", false);
	  		$("#btn_delete").attr("disabled", false);
	  		$("#btn_save").attr("disabled", false);
		} else {
			$("#btn_add").attr("disabled", true);
	  		$("#btn_delete").attr("disabled", true);
	  		$("#btn_save").attr("disabled", true);
		}
	};
	
	//新增权限按钮点击事件
	function doAdd() {
		document.getElementById("text_roleName").value = "";
		PageHandle.storeCreat();
		PageHandle.initPageSize = PageHandle.store.pageSize;
	    $("#gridTable").html("");
	    PageHandle.render();
		PageHandle.store.loadPage(1);
		PageHandle.HasSearch = 0;
		$("#btn_add").attr("disabled", false);
  		$("#btn_delete").attr("disabled", true);
  		$("#btn_save").attr("disabled", false);
	};
	
	//删除权限按钮点击事件
	function doDelete() {
		var record = PageHandle.grid.getStore().getAt(0);
		var keyCode = record.get("keyCode");
		var roleName = record.get("roleName");
		
		if(confirm($.i18n.prop("E0026"))){
			 $.ajax({ 
			        type: "post", 
			        url: "UserRoleManage/deleteClick.do", 
			        dataType: "text",
			        data: {keyCode: keyCode,roleName: roleName},
			        success: function (data) {
			        	alert($.i18n.prop(data));
			        	if(data == "E0035") {
			        		window.location.reload();
			        	} else if(data == "E0036") {
	                   		window.location.href("errorPage.do"); 
	                   	} else if(data.substr(0,5) == "E0054") {
		                		alert($.i18n.prop(data));
		                		return;
	                   	}
			        }
			    });
		} else {
			return;
		}
		$("#btn_add").attr("disabled", false);
  		$("#btn_delete").attr("disabled", true);
  		$("#btn_save").attr("disabled", true);
	}
	
	//保存按钮点击事件
	function doSave() {
		var roleName = document.getElementById("text_roleName").value.trim();
		if (!checkMysqlKey(roleName)) {
			$('#text_roleName').focus();
			return false;
		}
		document.getElementById("text_roleName").value = roleName;
		if(roleName == "" || roleName == null){
			alert(PageHandle.String_i18n.Msg_NoRoleName.replace("{0}",$.i18n.prop('UserRoleManage_string_RoleName')));
			return;
		}
		if(roleName.length > 50){
			alert(PageHandle.String_i18n.Msg_MaxLength.replace('{0}', "50"));
			document.getElementById("text_roleName").value="";
			return;
		}
		var aToStr = "";
		var tempKeyCode = "";
		//遍历所有数据
		for(var i = 0; i < PageHandle.grid.store.data.items.length; i++){
			if(PageHandle.HasSearch == "1" && roleName == $("#select_role option:selected").text()){
				var obj = PageHandle.grid.store.data.items[i].modified;
				//更新并且角色名没有修改
				for(p in obj){
					if (aToStr != "") {
						aToStr += "@@";
					}
					var record = PageHandle.grid.getStore().getAt(i);
					record.set('roleName',roleName);
					if(record.get('keyCode') == ""){
						if(tempKeyCode == ""){
							for(var j = 0; j < PageHandle.grid.store.data.items.length; j++){
								tempKeyCode = PageHandle.grid.getStore().getAt(j).get('keyCode');
								break;
							}
						}
						record.set('keyCode',tempKeyCode);
						record.set('flag',1);
					}
					aToStr += JSON.stringify(PageHandle.grid.store.data.items[i].data);
					break;
				}
			} else if(PageHandle.HasSearch == "0" || roleName != $("#select_role option:selected").text()){
				//插入或改了角色名
					if (aToStr != "") {
						aToStr += "@@";
					}
					var record = PageHandle.grid.getStore().getAt(i);
					record.set('roleName',roleName);
					if(record.get('keyCode') == "" && PageHandle.HasSearch == "1"){
						if(tempKeyCode == ""){
							for(var j = 0; j < PageHandle.grid.store.data.items.length; j++){
								tempKeyCode = PageHandle.grid.getStore().getAt(j).get('keyCode');
								break;
							}
						}
						record.set('keyCode',tempKeyCode);
						record.set('flag',1);
					}
					aToStr += JSON.stringify(PageHandle.grid.store.data.items[i].data);
			}
		}
		
		if(aToStr == ""){
			alert(PageHandle.String_i18n.Msg_NoSaveData);
			return;
		}
		
		if (confirm($.i18n.prop("E0032")) == false) {
			return;
		}
		
		$.ajax({
                   type: "post",
                   url: "UserRoleManage/save.do",
                   data: {jsonStr: aToStr,hasSearch: PageHandle.HasSearch},
                   dataType: "text",
                   success: function(data) {
                	   var messageId = data.substring(0,5);
                	   var keyCode = data.substr(5);
                	   if(messageId == "E0041") {
                      		var exitMessage = $.i18n.prop(messageId).replace("{0}",$.i18n.prop('UserRoleManage_string_RoleName'));
                      		$("#text_roleName").focus();
                      		alert(exitMessage);
                      	} else if(messageId == "E0084" || messageId == "E0085"){
                   			alert($.i18n.prop(messageId));
                   			var errRow = data.substr(6,data.length-1);
                   			PageHandle.grid.getSelectionModel().select(errRow,false,false);
                   			return;
                   		} else if (messageId == "E0025"){
                      		window.location.href("errorPage.do"); 
                      	} else if(messageId == "E0030"){
                      		alert($.i18n.prop(messageId));
                      		doGridReflash(keyCode);
                      	}
                   		//按钮制御
                	   if(messageId == "E0030") {
							$("#btn_add").attr("disabled", false);
               	  			$("#btn_delete").attr("disabled", false);
               	  			$("#btn_save").attr("disabled", false);
                   		} else {
                   			$("#btn_add").attr("disabled", false);
                	  		$("#btn_delete").attr("disabled", true);
                	  		$("#btn_save").attr("disabled", false);
                   		}
					}
			});
	}
	
	//刷新明细列表
	function doGridReflash(keyCode){
		var roleName = document.getElementById("text_roleName").value.trim();
		//是否是新增权限
		if(PageHandle.HasSearch == "0"){
			document.getElementById("select_role").options.add(new Option(roleName,keyCode));
		} else if (PageHandle.HasSearch == "1" && roleName != $("#select_role option:selected").text()){
			var obj = document.getElementById('select_role');
			var index = obj.selectedIndex;
			obj.options.remove(index);
			document.getElementById("select_role").options.add(new Option(roleName,keyCode));
		}
		document.getElementById("select_role").value = keyCode;
		PageHandle.initPageSize = PageHandle.store.pageSize;
		
		PageHandle.store = Ext.create('Ext.data.Store', {
			model: 'UserRoleManage',
			proxy: {
				type: 'ajax',
				url: 'UserRoleManage/searchClick.do',
				extraParams: {"keyCode":keyCode},
				reader: {
					type: 'json',
					root: 'result'
				}
			}
		});
		$("#gridTable").html("");
		PageHandle.render();
		PageHandle.store.loadPage(1);
		PageHandle.HasSearch = 1;
	}
	
	//checkbox点击事件
	function checkBoxClick(element,rowIndex){
		var arg=element.value;
		var rec = PageHandle.grid.getStore().getAt(rowIndex);
		if(element.checked){
			rec.set(arg,1);
		} else {
			rec.set(arg,0);
		}
	}
	
    /*****设置语言****/
	function setPageByLanguage(){
		$("#label_roleName").html($.i18n.prop('UserRoleManage_string_RoleName'));
		$("#label_roleNm").html($.i18n.prop('UserRoleManage_string_RoleName'));
		$("#btn_query").val($.i18n.prop('UserRoleManage_string_queryBtn'));
		$("#btn_add").val($.i18n.prop('UserRoleManage_string_addBtn'));
		$("#btn_delete").val($.i18n.prop('UserRoleManage_string_deleteBtn'));
		$("#btn_save").val($.i18n.prop('UserRoleManage_string_saveBtn'));
    	
		PageHandle.String_i18n.GridTitle_No = $.i18n.prop('UserRoleManage_string_GridTitle_No');
		PageHandle.String_i18n.GridTitle=$.i18n.prop('UserRoleManage_string_GridTitle');
		PageHandle.String_i18n.GridTitle_PageName=$.i18n.prop('UserRoleManage_string_GridTitle_PageName');
		PageHandle.String_i18n.GridTitle_ShowRoleFlag=$.i18n.prop('UserRoleManage_string_GridTitle_ShowRoleFlag');
		PageHandle.String_i18n.GridTitle_QueryRoleFlag=$.i18n.prop('UserRoleManage_string_GridTitle_QueryRoleFlag');
		PageHandle.String_i18n.GridTitle_AlterRoleFlag=$.i18n.prop('UserRoleManage_string_GridTitle_AlterRoleFlag');
		PageHandle.String_i18n.GridTitle_UploadRoleFlag=$.i18n.prop('UserRoleManage_string_GridTitle_UploadRoleFlag');
		PageHandle.String_i18n.GridTitle_DownloadRoleFlag=$.i18n.prop('UserRoleManage_string_GridTitle_DownloadRoleFlag');
		PageHandle.String_i18n.GridTitle_SpecialRole1Flag=$.i18n.prop('UserRoleManage_string_GridTitle_SpecialRole1Flag');
		PageHandle.String_i18n.GridTitle_SpecialRole2Flag=$.i18n.prop('UserRoleManage_string_GridTitle_SpecialRole2Flag');
		PageHandle.String_i18n.GridTitle_SpecialRole3Flag=$.i18n.prop('UserRoleManage_string_GridTitle_SpecialRole3Flag');
		PageHandle.String_i18n.GridTitle_Department=$.i18n.prop('UserRoleManage_string_GridTitle_Department');
		PageHandle.String_i18n.GridTitle_KeyCode=$.i18n.prop('UserRoleManage_string_GridTitle_KeyCode');
		PageHandle.String_i18n.GridTitle_PageId=$.i18n.prop('UserRoleManage_string_GridTitle_PageId');
		PageHandle.String_i18n.GridTitle_RoleName=$.i18n.prop('UserRoleManage_string_RoleName');
		
		PageHandle.String_i18n.Msg_NoRoleName=$.i18n.prop('E0021');
		PageHandle.String_i18n.Msg_NoSaveData=$.i18n.prop('E0029');
		PageHandle.String_i18n.Msg_MaxLength=$.i18n.prop('E0033');
    }
    </script>
</head>
<body>
<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
<input type="hidden" id ="AlterRoleFlag" value = "${AlterRoleFlag}">
<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
<div style="width:100%;">
	<table style="width:100%;">
	 	<tr style="height: 30px">
	 		<td style="width:80px;">
				<label id=label_roleName style="color:black"></label>
			</td>
			<td style="width:200px;">
				<select name="selectname_role" id="select_role" style="width: 150px" onkeydown="enter_down();">
				<c:forEach items="${roleList}" var="roleList" varStatus="vs">
				<option value="${roleList.keyCode}" >${roleList.roleName}</option>
				</c:forEach>
				</select>
			</td>
			<td colspan="2">
            <c:if test="${QueryRoleFlag == '1'}">
				<c:if test="${staff.email.endsWith('.co.jp') && SpecialRole1Flag == '1' || !staff.email.endsWith('.co.jp') && SpecialRole2Flag == '1'}">
				<input id="btn_query" type="button" style="width: 50px;" onclick = "doSearch()"/>
	            </c:if>
            </c:if>
            <c:if test="${AlterRoleFlag == '1'}">
				<input id="btn_delete" type="button" style="width: 50px;" onclick = "doDelete()"/>
            </c:if>
			</td>
			<td style="width: 80px;align:left">
				<label id=label_roleNm style="color:black"></label>
			</td>
			<td style="width:200px;">
				<input type="text" id="text_roleName" style="width: 150px" maxLength=50>
			</td>
			<td colspan="2">
            <c:if test="${AlterRoleFlag == '1'}">
				<input id="btn_add" type="button" style="width: 50px" onclick = "doAdd()"/>
				<input id="btn_save" type="button" style="width: 50px" onclick = "doSave()"/>
            </c:if>
			</td>
		</tr>
	</table>
	</div>
	<hr>
<div id="gridTable"></div>
</body>
</html>