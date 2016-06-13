<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<title>人员工数检索</title>
	<link rel="stylesheet" type="text/css" href="js/ext-4.0.7-gpl/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/extstyle.css" />
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
	<script type="text/javascript" src="js/ext-4.0.7-gpl/MergeCells.js"></script>
	<script type="text/javascript" src="js/Flexigrid/flexigrid.pack.js"></script>
	
	<style type="text/css">
	.x-ie11 .x-grid-header-row,.x-quirks .x-ie8 .x-grid-header-row,.x-strict .x-ie8 .x-grid-header-row{position:relative;}
    </style>
	<script type="text/javascript">
	
	var language = getCookieValue("language");
	var E00045="";
	var E00046="";
	//禁止后退键 作用于Firefox、Opera   
	document.onkeypress=banBackSpace;  
	//禁止后退键  作用于IE、Chrome   
	document.onkeydown=banBackSpace;
	var mergeols = [];
	var vdlg;
	var savePjType    = "";  //保存时候使用
	var flag_has_data = "1"; //有数据
	var flag_no_data  = "2"; //没有数据
	var PageHandle = {
		HasSearch: 0,
		WaitVlaue: "",
		ValidFlag: true,
		ValidValue: "",
		CurrentTimes: "",
		ResultTimes: "",
		//合并单元格使用
		EditoSelection: null,
		NumberRe: new RegExp("\\d+"),
		keyPress: function(text, e) {    
			    var allowed = text.keyAllowed;
			    var k = e.getKey();
			    if(!ChkKeyInByExt(allowed, k)) {
			    	e.stopEvent();
			    }
			},
		focus: function(obj){
    		if(!PageHandle.ValidFlag) {
    			obj.setValue(PageHandle.ValidValue);
    			PageHandle.ValidFlag = true;
    			PageHandle.ValidValue = "";
    		}
    	},
    	valueRendererA: function(val,e,cell){
    		mergeCells(PageHandle.grid, mergeols ,true, false);
    		/*******20140625合并单元格修改：增加判断改善性能******/
			if( PageHandle.HasSearch == 1) {
				mergeCells(PageHandle.grid, mergeols);
				PageHandle.HasSearch = 1;
			}
    		return Ext.util.Format.number(val, '00,000,000.0000');
        },
		String_i18n: {
			GridTitle: "",
			GridTitle_No:"",
			GridTitle_NoCT:"",
			GridTitle_department: "",
			GridTitle_departmentCT: "",
			GridTitle_branch: "",
			GridTitle_branchCT: "",
			GridTitle_staffName: "",
			GridTitle_staffNameCT: "",
			GridTitle_position: "",
			GridTitle_positionCT: "",
			GridTitle_category: "",
			GridTitle_sortCT: "",
			GridTitle_projectClientNo:"",
			GridTitle_jobCategoryCT: "",
			GridTitle_projectName:"",
			GridTitle_PJNoCT: "",
			GridTitle_PJNameCT: "",
			GridTitle_tempPJNo: "",
			GridTitle_projectClientName:"",
			GridTitle_carMaker:"",
			
			String_Times:"",
			GridTitle_achieve1: "",
			GridTitle_achieve2: "",
			GridTitle_achieve3: "",
			GridTitle_achieve4: "",
			GridTitle_achieve5: "",
			GridTitle_achieve6: "",
			GridTitle_achieve7: "",
			GridTitle_achieve8: "",
			GridTitle_achieve9: "",
			GridTitle_achieve10: "",
			GridTitle_achieve11: "",
			GridTitle_achieve12: "",
			//分页
			GridBbrRecords: "",
			GridBbrGridBbrDisplayMsg: "",
			GridBbrGridBbrEmptyMsg: "",
			//合计
			SumGridTitle: ""
		},
		initPageSize: 10,
		gridModelAll: Ext.define('totalHourManagerAll', {
			extend: 'Ext.data.Model',
			fields: ["No","department", "branch","staffName", "position","sort","jobCategory","PJNo"
	                 ,"PJName","tempPJNo","category","projectClientNo","projectName","projectClientName"
	                 ,"carMaker","achieve1", "achieve2", "achieve3", "achieve4", "achieve5","achieve6"
	                 ,"achieve7", "achieve8", "achieve9", "achieve10", "achieve11", "achieve12"]
	    			}),
		gridModelCXEE: Ext.define('totalHourManagerCXEE', {
				        extend: 'Ext.data.Model',
				        fields: ["No","department", "branch","staffName", "position","category","projectClientNo","PJName"
				                 ,"projectClientName","carMaker","achieve1", "achieve2", "achieve3", "achieve4", "achieve5"
				                 ,"achieve6","achieve7", "achieve8", "achieve9", "achieve10", "achieve11", "achieve12"]
				    }),
		gridModelCT: Ext.define('totalHourManagerCT', {
				        extend: 'Ext.data.Model',
				        fields: ["No","department", "branch","staffName", "position","sort","jobCategory","PJNo"
				                 ,"PJName","tempPJNo","achieve1", "achieve2", "achieve3", "achieve4", "achieve5"
				                 ,"achieve6","achieve7", "achieve8", "achieve9", "achieve10", "achieve11", "achieve12"]
				    }),
	    sumGridModel: Ext.define('totalHourManagerSum',{
					        extend: 'Ext.data.Model',
	        				fields: ["sum","achieve1", "achieve2", "achieve3", "achieve4", "achieve5", "achieve6"
	                 ,"achieve7", "achieve8", "achieve9", "achieve10", "achieve11", "achieve12"]
	    }),
		store: null,
		sumStore: null,
		
		storeCreatAll: function(){
			
			var sort = $('#select_sortAll').val();
			var tempPJNo = $('#sel_tempPJNoAll').val();
			var belong = $('#select_belongAll').val();
			var PJNo = $('#select_PJNoAll').val();
			var transferNo = $('#serach_transferNoAll').val();
			var category = $('#select_categoryAll').val();
			var carMaker = $('#select_carMakerAll').val();
			var projectFunction = $('#select_projectFunctionAll').val();
			var PJName = $('#sel_PJNameAll').val();
			
			mergeols = [1,2,3,4];
			PageHandle.store = Ext.create('Ext.data.Store', {
				model: PageHandle.gridModelAll,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listManHour.do',
					extraParams: {"sel_sort":encodeURI(sort),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
								  "serach_transferNo":encodeURI(transferNo),"sel_category":encodeURI(category),"sel_carMaker":encodeURI(carMaker),"sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
						totalProperty: 'totalCount'
					},
					timeout:700000
				},
				listeners: {
					load: function(store) {
						if (PageHandle.grid.store.data.items.length == 0){
							$("#btn_queryAll").removeAttr("disabled");
							alert($.i18n.prop('E001'));
						  	$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","none");
						  //获取焦点
						  /* if($("#DepartmentFlag").val() == "0"){
							 $('#select_branch').focus();
						  }else{
							  $('#select_department').focus();
						  } */
						}else{
							$("#btn_queryAll").removeAttr("disabled");
							$("#btn_downloadAll").removeAttr("disabled");
							$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","scroll");
						}
						mergeCells(PageHandle.grid, mergeols);
						resetGrid(store);
					}
				}

			});
		},
		storeCreatCXEE: function(){
			//var currentYearMonth = $('#spn_currentYearMonth').html();
			var staffName = $('#serach_staffNameCXEE').val();
			var position = $('#select_positionCXEE').val();
			var department = $('#select_departmentCXEE').val();
			var branch = $('#select_branchCXEE').val();
			var sort = $('#select_sortCXEE').val();
			
			//var jobCategory = $('#select_jobCategory').val();
			var projectClientNo = $('#serach_projectClientNoCXEE').val();
			//var projectNo = $('#serach_projectClientNoCXEE').val();
			var PJNo = $('#select_PJNoCXEE').val();
			var tempPJNo = $('#sel_tempPJNoCXEE').val();
			var belong = $('#select_belong').val();
			var transferNo = $('#serach_transferNoCXEE').val();
			var category = $('#select_categoryCXEE').val();
			var projectClientName = $('#select_projectClientNameCXEE').val();
			var carMaker = $('#select_carMakerCXEE').val();
			var projectFunction = $('#select_projectFunctionCXEE').val();
			var PJName = $('#select_PJNameCXEE').val();
			
			mergeols = [1,2,3,4,5,6,7];
			PageHandle.store = Ext.create('Ext.data.Store', {
				model: PageHandle.gridModelCXEE,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listManHourCXEE.do',
					extraParams: {"serach_staffName":encodeURI(staffName),"sel_position":encodeURI(position),"sel_department":department,"sel_branch":branch,"sel_sort":encodeURI(sort),
								  "serach_projectClientNo":encodeURI(projectClientNo),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
								  "serach_transferNo":encodeURI(transferNo),"sel_category":encodeURI(category),"sel_projectClientName":encodeURI(projectClientName),"sel_carMaker":encodeURI(carMaker),
								  "sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
						totalProperty: 'totalCount'
					},
					timeout:700000
				},
				listeners: {
					load: function(store) {
						
						if (PageHandle.grid.store.data.items.length == 0){
							$("#btn_query").removeAttr("disabled");
							alert($.i18n.prop('E001'));
						  	$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","none");
						  //获取焦点
						  /* if($("#DepartmentFlag").val() == "0"){
							 $('#select_branch').focus();
						  }else{
							  $('#select_department').focus();
						  } */
						}else{
							$("#btn_query").removeAttr("disabled");
							$("#btn_download").removeAttr("disabled");
							$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","scroll");
						}
						mergeCells(PageHandle.grid, mergeols);
						resetGrid(store);
					}
				}

			});
		},
		storeCreatCT: function(){
			//var currentYearMonth = $('#spn_currentYearMonth').html();
			var staffName = $('#serach_staffNameCT').val();
			var position = $('#select_positionCT').val();
			var department = $('#select_departmentCT').val();
			var branch = $('#select_branchCT').val();
			var sort = $('#select_sortCT').val();
			
			var jobCategory = $('#select_jobCategoryCT').val();
			//var projectClientNo = $('#serach_projectClientNo').val();
			var PJNo = $('#select_PJNoCT').val();
			var tempPJNo = $('#sel_tempPJNoCT').val();
			var belong = $('#select_belongCT').val();
			
			//var transferNo = $('#serach_transferNo').val();
			var category = $('#select_categoryCT').val();
			//var projectClientName = $('#select_projectClientName').val();
			var carMaker = $('#select_carMakerCT').val();
			var projectFunction = $('#select_projectFunctionCT').val();
			var PJName = $('#sel_PJNameCT').val();
			
			
			
			
			mergeols = [1,2,3,4,5,6,7,8,9,10];
			PageHandle.store = Ext.create('Ext.data.Store', {
				model: PageHandle.gridModelCT,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listManHourCT.do',
					extraParams: {"serach_staffName":encodeURI(staffName),"sel_position":encodeURI(position),"sel_department":department,"sel_branch":branch,"sel_sort":encodeURI(sort),
								  "sel_jobCategory":encodeURI(jobCategory),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
								  "sel_category":encodeURI(category),"sel_carMaker":encodeURI(carMaker),"sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
						totalProperty: 'totalCount'
					},
					timeout:700000
				},
				listeners: {
					load: function(store) {
						
						if (PageHandle.grid.store.data.items.length == 0){
							$("#btn_queryCT").removeAttr("disabled");
							alert($.i18n.prop('E001'));
						  	$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","none");
						  //获取焦点
						  /* if($("#DepartmentFlag").val() == "0"){
							 $('#select_branch').focus();
						  }else{
							  $('#select_department').focus();
						  } */
						}else{
							$("#btn_queryCT").removeAttr("disabled");
							$("#btn_downloadCT").removeAttr("disabled");
							$("div.x-grid-view.x-fit-item.x-grid-view-default.x-unselectable").first().css("overflow-x","scroll");
						}
						mergeCells(PageHandle.grid, mergeols);
						resetGrid(store);
					}
				}

			});
		},
		sumStoreCreat: function(){
			var sort = $('#select_sortAll').val();//社员区分
			var PJNo = $('#select_PJNoAll').val();
			var tempPJNo = $('#sel_tempPJNoAll').val();//tempPJNo
			var belong = $('#select_belongAll').val();//
			
			var transferNo = $('#serach_transferNoAll').val();//管理项番
			var category = $('#select_categoryAll').val();//开发阶段
			var carMaker = $('#select_carMakerAll').val();//chechang 
			var projectFunction = $('#select_projectFunctionAll').val();
			var PJName = $('#sel_PJNameAll').val();
			
			
			PageHandle.sumStore = Ext.create('Ext.data.Store', {
				model: PageHandle.sumGridModel,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listSumManHour.do',
					extraParams: {"sel_sort":encodeURI(sort),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
						  		  "serach_transferNo":encodeURI(transferNo),"sel_category":encodeURI(category),"sel_carMaker":encodeURI(carMaker),"sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
					},
					timeout:700000
				},
			});
		},
		sumStoreCreatCT: function(){
			var staffName = $('#serach_staffNameCT').val();
			var position = $('#select_positionCT').val();
			
			var department = $('#select_departmentCT').val();
			
			var branch = $('#select_branchCT').val();
			var sort = $('#select_sortCT').val();
			
			var jobCategory = $('#select_jobCategoryCT').val();
			//var projectClientNo = $('#serach_projectClientNo').val();
			var PJNo = $('#select_PJNoCT').val();
			var tempPJNo = $('#sel_tempPJNoCT').val();
			var belong = $('#select_belongCT').val();
			
			//var transferNo = $('#serach_transferNo').val();
			var category = $('#select_categoryCT').val();
			//var projectClientName = $('#select_projectClientName').val();
			var carMaker = $('#select_carMakerCT').val();
			var projectFunction = $('#select_projectFunctionCT').val();
			var PJName = $('#sel_PJNameCT').val();
			
			
			PageHandle.sumStore = Ext.create('Ext.data.Store', {
				model: PageHandle.sumGridModel,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listSumManHourCT.do',
					extraParams: {"serach_staffName":encodeURI(staffName),"sel_position":encodeURI(position),"sel_department":department,"sel_branch":branch,"sel_sort":encodeURI(sort),
						  		  "sel_jobCategory":encodeURI(jobCategory),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
						  		  "sel_category":encodeURI(category),"sel_carMaker":encodeURI(carMaker),"sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
					},
					timeout:700000
				},
			});
		},
		sumStoreCreatCXEE: function(){
			var staffName = $('#serach_staffNameCXEE').val();
			var position = $('#select_positionCXEE').val();
			
			var department = $('#select_departmentCXEE').val();
			
			var branch = $('#select_branchCXEE').val();
			var sort = $('#select_sortCXEE').val();
			
			//var jobCategory = $('#select_jobCategoryCXEE').val();
			var projectClientNo = $('#serach_projectClientNoCXEE').val();//依赖号
			//var projectNo = $('#serach_projectClientNoCXEE').val();
			var tempPJNo = $('#sel_tempPJNoCXEE').val();
			var belong = $('#select_belong').val();
			var PJNo = $("#select_PJNoCXEE").val();
			var transferNo = $('#serach_transferNoCXEE').val();
			var category = $('#select_categoryCXEE').val();
			var projectClientName = $('#select_projectClientNameCXEE').val();
			var carMaker = $('#select_carMakerCXEE').val();
			var projectFunction = $('#select_projectFunctionCXEE').val();
			var PJName = $('#select_PJNameCXEE').val();
			
			
			PageHandle.sumStore = Ext.create('Ext.data.Store', {
				model: PageHandle.sumGridModel,
				proxy: {
					type: 'ajax',
					url: 'totalHourManage/listSumManHourCXEE.do',
					extraParams: {"serach_staffName":encodeURI(staffName),"sel_position":encodeURI(position),"sel_department":department,"sel_branch":branch,"sel_sort":encodeURI(sort),
						  		  "serach_projectClientNo":encodeURI(projectClientNo),"sel_PJNo":encodeURI(PJNo),"sel_tempPJNo":encodeURI(tempPJNo),"sel_belong":belong,
						  		  "serach_transferNo":encodeURI(transferNo),"sel_category":encodeURI(category),"sel_projectClientName":encodeURI(projectClientName),"sel_carMaker":encodeURI(carMaker),"sel_projectFunction":encodeURI(projectFunction),"sel_PJName":encodeURI(PJName)},
					reader: {
						type: 'json',
						root: 'result',
					},
					timeout:700000
				},
			});
		},
	    grid: null,
	    sumGrid:null,
	    renderAll: function(){
    		PageHandle.grid = new Ext.grid.GridPanel( {
		        store: PageHandle.store,
		        stateful: true,
		        stateId: 'stateGridALL',  
		        columns: [
							{
							    text : PageHandle.String_i18n.GridTitle_NoCT,
							    width : 40,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'No',
							    align : 'center',
							    titleAlign: 'center',
							    locked: true
							},
				            {
				                text : PageHandle.String_i18n.GridTitle_departmentCT,
				                width : 80,
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'department',
				                align : 'left',
				                titleAlign: 'center',
				                locked: true
				            },
				            {
				                text : PageHandle.String_i18n.GridTitle_branchCT,
				                width : 80,
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'branch',
				                align : 'left',
				                titleAlign: 'center',
				                locked: true
				            },
				            {
				                text : PageHandle.String_i18n.GridTitle_staffNameCT,
				                width : 50,
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'staffName',
				                align : 'left',
				                titleAlign: 'center',
				                locked: true
				            },
				            {
				                header:PageHandle.String_i18n.GridTitle_positionCT,
				            	width:70,
				                
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'position',
				                align : 'right',
				                titleAlign: 'center',
				                locked: true,
				            },
				            {
				                text : PageHandle.String_i18n.GridTitle_sortCT,
				                width : 60,
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'sort',
				                align : 'left',
				                titleAlign: 'center',
				            },
							{
							    text : PageHandle.String_i18n.GridTitle_jobCategoryCT,
							    width : 60,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'jobCategory',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_PJNoCT,
							    width : 90,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'PJNo',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_PJNameCT,
							    width : 60,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'PJName',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_tempPJNoCT,
							    width : 80,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'tempPJNo',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
				                text : PageHandle.String_i18n.GridTitle_category,
				                width : 60,
				                sortable : false,
				                //menuDisabled: true,
				                dataIndex: 'category',
				                align : 'left',
				                titleAlign: 'center',
				            },
							{
							    text : PageHandle.String_i18n.GridTitle_projectClientNo,
							    width : 80,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'projectClientNo',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_projectName,
							    width : 100,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'projectName',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_projectClientName,
							    width : 50,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'projectClientName',
							    align : 'left',
							    titleAlign: 'center',
							},
							{
							    text : PageHandle.String_i18n.GridTitle_carMaker,
							    width : 70,
							    sortable : false,
							    //menuDisabled: true,
							    dataIndex: 'carMaker',
							    align : 'left',
							    titleAlign: 'center',
							},
				            //{
				            	//text: PageHandle.ResultTimes + PageHandle.String_i18n.String_Times,
				            	//menuDisabled: true,
				            	//columns: [
						           
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve4,
						                width : 80,
						                sortable : false,
						                itemId: 'achieve4',
						                //menuDisabled: true,
						                dataIndex: 'achieve4',
						                align : 'right',
						                titleAlign: 'center',
						                
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                } 
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve5,
						                width : 80,
						                sortable : false,
										itemId: 'achieve5',
						                //menuDisabled: true,
						                dataIndex: 'achieve5',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve6,
						                width : 80,
						                sortable : false,
										itemId: 'achieve6',
						                //menuDisabled: true,
						                dataIndex: 'achieve6',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve7,
						                width : 80,
						                sortable : false,
										itemId: 'achieve7',
						                //menuDisabled: true,
						                dataIndex: 'achieve7',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve8,
						                width : 80,
						                sortable : false,
										itemId: 'achieve8',
						                //menuDisabled: true,
						                dataIndex: 'achieve8',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						           
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve9,
						                width : 80,
						                sortable : false,
										itemId: 'achieve9',
						                //menuDisabled: true,
						                dataIndex: 'achieve9',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve10,
						                width : 80,
						                sortable : false,
										itemId: 'achieve10',
						                //menuDisabled: true,
						                dataIndex: 'achieve10',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve11,
						                width : 80,
						                sortable : false,
										itemId: 'achieve11',
						                //menuDisabled: true,
						                dataIndex: 'achieve11',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve12,
						                width : 80,
						                sortable : false,
										itemId: 'achieve12',
						                //menuDisabled: true,
						                dataIndex: 'achieve12',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve1,
						                width : 80,
						                sortable : false,
										itemId: 'achieve1',
						                //menuDisabled: true,
						                dataIndex: 'achieve1',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						           
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve2,
						                width : 80,
						                sortable : false,
										itemId: 'achieve2',
						                //menuDisabled: true,
						                dataIndex: 'achieve2',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            },
						            
						            {
						                text : PageHandle.String_i18n.GridTitle_achieve3,
						                width : 80,
						                sortable : false,
										itemId: 'achieve3',
						                //menuDisabled: true,
						                dataIndex: 'achieve3',
						                align : 'right',
						                titleAlign: 'center',
						                renderer : function(val){
						                	return Ext.util.Format.number(val, '00,000,000.0000');  
						                }
						            }
				            	//],
				           // }
				        ],
				        height: getBrowserHeight() -170,
				        
				        //selType: 'cellmodel',
				        selModel: Ext.create('Ext.selection.RowModel',{mode:"SIMPLE"}),
				        	//Ext.grid.RowSelectionModel({singleSelect:true}),//设置行是否可以按shift多选（true为不可以
				        width: getBrowserWidth(),
				        columnLines:true,
				        title: PageHandle.String_i18n.GridTitle,
				        plugins: [ Ext.create('Ext.grid.plugin.CellEditing', {
									            clicksToEdit: 2
									        })
								], 
						renderTo: 'gridAll',
						viewConfig : {
						stripeRows: true
						        }, 
						        bbar: new Ext.PagingToolbar({
									plugins: [new Ext.ux.plugin.PagingToolbarResizer( {options: [5, 10, 20, 30], displayText : PageHandle.String_i18n.GridBbrRecords, prependCombo: true})],
									pageSize: PageHandle.initPageSize ,
									store: PageHandle.store,
									displayInfo: true,
									heigth: 500,
									displayMsg: PageHandle.String_i18n.GridBbrDisplayMsg,
									emptyMsg: PageHandle.String_i18n.GridBbrEmptyMsg,
									listeners:{
							        	beforechange: function(pageObj, pageNumber, eOpts) {
							        	    if(PageHandle.store.currentPage * PageHandle.store.pageSize >= PageHandle.store.getTotalCount()) {
							        	    	searchClickAll(pageNumber);
							        	    	return false;
							        	    }
							        		return true;
							        	}
							        }
								})
				    	    });
						},
	    renderCXEE: function(){
    		PageHandle.grid = new Ext.grid.GridPanel( {
		        store: PageHandle.store,
		        stateful: true,
		        stateId: 'stateGridCXEE',  
		        columns: [
					{
					    text : PageHandle.String_i18n.GridTitle_No,
					    width : 40,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'No',
					    align : 'center',
					    titleAlign: 'center',
					    locked: true
					},
		            {
		                text : PageHandle.String_i18n.GridTitle_department,
		                width : 100,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'department',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_branch,
		                width : 100,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'branch',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_staffName,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'staffName',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_position,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'position',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_category,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'category',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
					{
					    text : PageHandle.String_i18n.GridTitle_projectClientNo,
					    width : 100,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'projectClientNo',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					{
					    text : PageHandle.String_i18n.GridTitle_projectName,
					    width : 100,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'PJName',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					{
					    text : PageHandle.String_i18n.GridTitle_projectClientName,
					    width : 60,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'projectClientName',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					{
					    text : PageHandle.String_i18n.GridTitle_carMaker,
					    width : 80,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'carMaker',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
		            //{
		            	//text: PageHandle.ResultTimes + PageHandle.String_i18n.String_Times,
		            	//menuDisabled: true,
		            	//columns: [
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve4,
				                width : 80,
				                sortable : false,
				                itemId: 'achieve4',
				                //menuDisabled: true,
				                dataIndex: 'achieve4',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve5,
				                width : 80,
				                sortable : false,
								itemId: 'achieve5',
				                //menuDisabled: true,
				                dataIndex: 'achieve5',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve6,
				                width : 80,
				                sortable : false,
								itemId: 'achieve6',
				                //menuDisabled: true,
				                dataIndex: 'achieve6',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve7,
				                width : 80,
				                sortable : false,
								itemId: 'achieve7',
				                //menuDisabled: true,
				                dataIndex: 'achieve7',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve8,
				                width : 80,
				                sortable : false,
								itemId: 'achieve8',
				                //menuDisabled: true,
				                dataIndex: 'achieve8',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve9,
				                width : 80,
				                sortable : false,
								itemId: 'achieve9',
				                //menuDisabled: true,
				                dataIndex: 'achieve9',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve10,
				                width : 80,
				                sortable : false,
								itemId: 'achieve10',
				                //menuDisabled: true,
				                dataIndex: 'achieve10',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve11,
				                width : 80,
				                sortable : false,
								itemId: 'achieve11',
				                //menuDisabled: true,
				                dataIndex: 'achieve11',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve12,
				                width : 80,
				                sortable : false,
								itemId: 'achieve12',
				                //menuDisabled: true,
				                dataIndex: 'achieve12',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve1,
				                width : 80,
				                sortable : false,
								itemId: 'achieve1',
				                //menuDisabled: true,
				                dataIndex: 'achieve1',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve2,
				                width : 80,
				                sortable : false,
								itemId: 'achieve2',
				                //menuDisabled: true,
				                dataIndex: 'achieve2',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve3,
				                width : 80,
				                sortable : false,
								itemId: 'achieve3',
				                //menuDisabled: true,
				                dataIndex: 'achieve3',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            }
		            	//]
		            //}
		        ],
		        height: getBrowserHeight() -200,
		        
		        //selType: 'cellmodel',
		        selModel: Ext.create('Ext.selection.RowModel',{mode:"SIMPLE"}),
		        	//Ext.grid.RowSelectionModel({singleSelect:true}),//设置行是否可以按shift多选（true为不可以
		        width: getBrowserWidth(),
		        columnLines:true,
		        title: PageHandle.String_i18n.GridTitle,
		        plugins: [ Ext.create('Ext.grid.plugin.CellEditing', {
							            clicksToEdit: 2
							        })
						],
		        renderTo: 'gridCXEE',
		        viewConfig : {
		            stripeRows: true
		        },
		        bbar: new Ext.PagingToolbar({
					plugins: [new Ext.ux.plugin.PagingToolbarResizer( {options: [5, 10, 20, 30], displayText : PageHandle.String_i18n.GridBbrRecords, prependCombo: true})],
					pageSize: PageHandle.initPageSize,
					store: PageHandle.store,
					displayInfo: true,
					heigth: 500,
					displayMsg: PageHandle.String_i18n.GridBbrDisplayMsg,
					emptyMsg: PageHandle.String_i18n.GridBbrEmptyMsg,
					listeners:{
			        	beforechange: function(pageObj, pageNumber, eOpts) {
			        	    if(PageHandle.store.currentPage * PageHandle.store.pageSize >= PageHandle.store.getTotalCount()) {
			        	    	searchClick(pageNumber);
			        	    	return false;
			        	    }
			        		return true;
			        	}
			        }
				})
    	    });
		},
		renderCT: function(){
    		PageHandle.grid = new Ext.grid.GridPanel( {
		        store: PageHandle.store,
		        stateful: true,
		        stateId: 'stateGridCT',  
		        columns: [
					{
					    text : PageHandle.String_i18n.GridTitle_NoCT,
					    width : 40,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'No',
					    align : 'center',
					    titleAlign: 'center',
					    locked: true
					},
		            {
		                text : PageHandle.String_i18n.GridTitle_departmentCT,
		                width : 100,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'department',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_branchCT,
		                width : 100,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'branch',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_staffNameCT,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'staffName',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_positionCT,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'position',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
		            {
		                text : PageHandle.String_i18n.GridTitle_sortCT,
		                width : 80,
		                sortable : false,
		                //menuDisabled: true,
		                dataIndex: 'sort',
		                align : 'left',
		                titleAlign: 'center',
		                locked: true
		            },
					{
					    text : PageHandle.String_i18n.GridTitle_jobCategoryCT,
					    width : 60,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'jobCategory',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					{
					    text : PageHandle.String_i18n.GridTitle_PJNameCT,
					    width : 100,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'PJName',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					{
					    text : PageHandle.String_i18n.GridTitle_PJNoCT,
					    width : 100,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'PJNo',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
					
					{
					    text : PageHandle.String_i18n.GridTitle_tempPJNoCT,
					    width : 80,
					    sortable : false,
					    //menuDisabled: true,
					    dataIndex: 'tempPJNo',
					    align : 'left',
					    titleAlign: 'center',
					    locked: true
					},
		            //{
		            	//text: PageHandle.ResultTimes + PageHandle.String_i18n.String_Times,
		            	//menuDisabled: true,
		            	//columns: [
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve4,
				                width : 80,
				                sortable : false,
				                itemId: 'achieve4',
				                //menuDisabled: true,
				                dataIndex: 'achieve4',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve5,
				                width : 80,
				                sortable : false,
								itemId: 'achieve5',
				                //menuDisabled: true,
				                dataIndex: 'achieve5',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve6,
				                width : 80,
				                sortable : false,
								itemId: 'achieve6',
				                //menuDisabled: true,
				                dataIndex: 'achieve6',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve7,
				                width : 80,
				                sortable : false,
								itemId: 'achieve7',
				                //menuDisabled: true,
				                dataIndex: 'achieve7',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve8,
				                width : 80,
				                sortable : false,
								itemId: 'achieve8',
				                //menuDisabled: true,
				                dataIndex: 'achieve8',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve9,
				                width : 80,
				                sortable : false,
								itemId: 'achieve9',
				                //menuDisabled: true,
				                dataIndex: 'achieve9',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve10,
				                width : 80,
				                sortable : false,
								itemId: 'achieve10',
				                //menuDisabled: true,
				                dataIndex: 'achieve10',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve11,
				                width : 80,
				                sortable : false,
								itemId: 'achieve11',
				                //menuDisabled: true,
				                dataIndex: 'achieve11',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve12,
				                width : 80,
				                sortable : false,
								itemId: 'achieve12',
				                //menuDisabled: true,
				                dataIndex: 'achieve12',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve1,
				                width : 80,
				                sortable : false,
								itemId: 'achieve1',
				                //menuDisabled: true,
				                dataIndex: 'achieve1',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				           
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve2,
				                width : 80,
				                sortable : false,
								itemId: 'achieve2',
				                //menuDisabled: true,
				                dataIndex: 'achieve2',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            },
				            
				            {
				                text : PageHandle.String_i18n.GridTitle_achieve3,
				                width : 80,
				                sortable : false,
								itemId: 'achieve3',
				                //menuDisabled: true,
				                dataIndex: 'achieve3',
				                align : 'right',
				                titleAlign: 'center',
				                renderer : function(val){
				                	return Ext.util.Format.number(val, '00,000,000.0000');  
				                }
				            }
		            	//]
		            //}
		        ],
		        height: getBrowserHeight() -200,
		        
		        //selType: 'cellmodel',
		        selModel: Ext.create('Ext.selection.RowModel',{mode:"SIMPLE"}),
		        	//Ext.grid.RowSelectionModel({singleSelect:true}),//设置行是否可以按shift多选（true为不可以
		        width: getBrowserWidth(),
		        columnLines:true,
		        title: PageHandle.String_i18n.GridTitle,
		        plugins: [ Ext.create('Ext.grid.plugin.CellEditing', {
							            clicksToEdit: 2
							        })
						],
		        renderTo: 'gridCT',
		        viewConfig : {
		            stripeRows: true
		        },
		        bbar: new Ext.PagingToolbar({
					plugins: [new Ext.ux.plugin.PagingToolbarResizer( {options: [5, 10, 20, 30], displayText : PageHandle.String_i18n.GridBbrRecords, prependCombo: true})],
					pageSize: PageHandle.initPageSize,
					store: PageHandle.store,
					displayInfo: true,
					heigth: 500,
					displayMsg: PageHandle.String_i18n.GridBbrDisplayMsg,
					emptyMsg: PageHandle.String_i18n.GridBbrEmptyMsg,
					listeners:{
			        	beforechange: function(pageObj, pageNumber, eOpts) {
			        	    if(PageHandle.store.currentPage * PageHandle.store.pageSize >= PageHandle.store.getTotalCount()) {
			        	    	searchClickCT(pageNumber);
			        	    	return false;
			        	    }
			        		return true;
			        	}
			        }
				})
    	    });
		},
		sumRender: function(){
    		PageHandle.sumGrid = new Ext.grid.GridPanel( {
		        store: PageHandle.sumStore,
		        stateful: true,
		        stateId: 'sumStateGrid',
		        columns: [
		            
		            {
		                text : PageHandle.String_i18n.GridTitle_achieve4,
		                width : 95,
		                sortable : false,
		                dataIndex: 'achieve4',
		                align : 'right',
		                titleAlign: 'center',
		                renderer : function(val){
	                		return Ext.util.Format.number(val, '00,000,000.0000');  
	                	}
				     },
		             
				     {
				         text : PageHandle.String_i18n.GridTitle_achieve5,
				         width : 95,
				         sortable : false,
				         dataIndex: 'achieve5',
				         align : 'right',
				         titleAlign: 'center',
				         renderer : function(val){
				           	return Ext.util.Format.number(val, '00,000,000.0000');  
				         }
				      },
				     
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve6,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve6',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve7,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve7',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve8,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve8',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve9,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve9',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve10,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve10',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve11,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve11',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve12,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve12',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve1,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve1',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve2,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve2',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve3,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve3',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            }
		        ],
		        height: 90,
		        width: getBrowserWidth(),
		        columnLines:true,
		        title: PageHandle.String_i18n.SumGridTitle,
		        renderTo: 'sumGridTable',
		        viewConfig : {
		            stripeRows: true
		        }
    	    });
		},
		sumRenderCT: function(){
    		PageHandle.sumGrid = new Ext.grid.GridPanel( {
		        store: PageHandle.sumStore,
		        stateful: true,
		        stateId: 'sumStateGrid',
		        columns: [
		            
		            {
		                text : PageHandle.String_i18n.GridTitle_achieve4,
		                width : 95,
		                sortable : false,
		                dataIndex: 'achieve4',
		                align : 'right',
		                titleAlign: 'center',
		                renderer : function(val){
	                		return Ext.util.Format.number(val, '00,000,000.0000');  
	                	}
				     },
		             
				     {
				         text : PageHandle.String_i18n.GridTitle_achieve5,
				         width : 95,
				         sortable : false,
				         dataIndex: 'achieve5',
				         align : 'right',
				         titleAlign: 'center',
				         renderer : function(val){
				           	return Ext.util.Format.number(val, '00,000,000.0000');  
				         }
				      },
				     
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve6,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve6',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve7,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve7',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve8,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve8',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve9,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve9',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve10,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve10',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve11,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve11',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve12,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve12',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve1,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve1',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve2,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve2',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve3,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve3',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            }
		        ],
		        height: 90,
		        width: getBrowserWidth(),
		        columnLines:true,
		        title: PageHandle.String_i18n.SumGridTitle,
		        renderTo: 'sumGridTableCT',
		        viewConfig : {
		            stripeRows: true
		        }
    	    });
		},
		sumRenderCXEE: function(){
    		PageHandle.sumGrid = new Ext.grid.GridPanel( {
		        store: PageHandle.sumStore,
		        stateful: true,
		        stateId: 'sumStateGrid',
		        columns: [
		            
		            {
		                text : PageHandle.String_i18n.GridTitle_achieve4,
		                width : 95,
		                sortable : false,
		                dataIndex: 'achieve4',
		                align : 'right',
		                titleAlign: 'center',
		                renderer : function(val){
	                		return Ext.util.Format.number(val, '00,000,000.0000');  
	                	}
				     },
		             
				     {
				         text : PageHandle.String_i18n.GridTitle_achieve5,
				         width : 95,
				         sortable : false,
				         dataIndex: 'achieve5',
				         align : 'right',
				         titleAlign: 'center',
				         renderer : function(val){
				           	return Ext.util.Format.number(val, '00,000,000.0000');  
				         }
				      },
				     
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve6,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve6',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve7,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve7',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve8,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve8',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve9,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve9',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve10,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve10',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve11,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve11',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve12,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve12',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve1,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve1',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve2,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve2',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            },
			            
			            {
			                text : PageHandle.String_i18n.GridTitle_achieve3,
			                width : 95,
			                sortable : false,
			                dataIndex: 'achieve3',
			                align : 'right',
			                titleAlign: 'center',
			                renderer : function(val){
			                	return Ext.util.Format.number(val, '00,000,000.0000');  
			                }
			            }
		        ],
		        height: 90,
		        width: getBrowserWidth(),
		        columnLines:true,
		        title: PageHandle.String_i18n.SumGridTitle,
		        renderTo: 'sumGridTableCXEE',
		        viewConfig : {
		            stripeRows: true
		        }
    	    });
		}
	
	
	};
		        
	//初始化
	Ext.onReady(function() {
		if (language!="CN" && language!="JP"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);

		/*get  CurrentYearMonth*/
		var calCurrentDate = new Date();
		var calCurrentYear = calCurrentDate.getFullYear();
		var calCurrentMonth = calCurrentDate.getMonth();
		var calCurrentDay = calCurrentDate.getDate();
		var currentDate = calCurrentYear+"-"+(calCurrentMonth+1)+"-"+calCurrentDay;
		var CurrentYearMonth = calCurrentYear+"-"+(calCurrentMonth+1);
		var currentYear = calCurrentDate.getFullYear();
		
		PageHandle.CurrentTimes = getCurrentTimes(CurrentYearMonth);
		PageHandle.ResultTimes = PageHandle.CurrentTimes;
		
		
		
		
		//PageHandle.sumStoreCreat();
		//$("#sumGridTable").html("");
		//PageHandle.sumRender();
		
	  	//PageHandle.prcHardRender();
		//权限控制
		if("${staff.email}".indexOf(".co.jp") > 0){
			$("#select_belongCT").val("1");
			$("#select_belong").val("2");
			$("#select_belongAll").val("0");
			$('#CXEE_condition').hide();
			$('#All_condition').hide();
			$('#gridCXEE').hide();
	    	$('#gridCT').show();
	    	$('#gridAll').hide();
	    	PageHandle.storeCreatCT("");
			$("#gridCT").html("");
			PageHandle.renderCT();
			$("#sumGridTableCT").show();
	    	$("#sumGridTableCXEE").hide();
	    	$("#sumGridTable").hide();
			PageHandle.sumStoreCreatCT();
			$("#sumGridTableCT").html("");
			PageHandle.sumRenderCT();
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#select_belongCT").attr("disabled", "disabled");
				$("#select_departmentCT").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#select_departmentCT").find("option[value="+departmentId+"]").attr("selected",true);
				changeDepartmentListCT();
	    	}
		} else {
			$("#select_belong").val("2");
			$("#select_belongAll").val("0");
			$("#select_belongCT").val("1");
			$('#CT_condition').hide();
			$('#All_condition').hide();
			$('#gridCXEE').show();
	    	$('#gridCT').hide();
	    	$('#gridAll').hide();
	    	PageHandle.storeCreatCXEE("");
	    	$("#gridCXEE").html("");
	    	PageHandle.renderCXEE();
	    	$("#sumGridTableCT").hide();
	    	$("#sumGridTableCXEE").show();
	    	$("#sumGridTable").hide();
	    	PageHandle.sumStoreCreatCXEE();
			$("#sumGridTableCXEE").html("");
			PageHandle.sumRenderCXEE();
	    	if ($("#DepartmentFlag").val() == "0") {
				$("#select_belong").attr("disabled", "disabled");
				$("#select_departmentCXEE").attr("disabled", "disabled");
				var departmentId = "${staff.departmentID}";
				$("#select_departmentCXEE").find("option[value="+departmentId+"]").attr("selected",true);
				changeDepartmentList();
	    	}
		} 
		
		}); 
	 
	//实现按enter键检索
	function enter_down(){
	   if(event.keyCode=="13"){//enter
		   searchClick();
	   }else{
	   }
	 }
	function enter_downCT(){
		   if(event.keyCode=="13"){//enter
			   searchClickCT();
		   }else{
		   }
		 }
	function enter_downAll(){
		   if(event.keyCode=="13"){//enter
			   searchClickAll();
		   }else{
		   }
		 }
	
	
	function downloadClick(){
		if (confirm(I0006)) {
			
			var staffNames = "";
			
			var positions = "";
			var departments = "";
			var PJNos = "";
			var branchs = "";
			var sorts = "";
			var jobCategorys = "";
			var projectClientNos = "";
			var projectNos = "";
			var tempPJNos = "";
			var PJNames = "";
			var transferNos = "";
			var categorys = "";
			var projectClientNames = "";
			var carMakers = "";
			var projectFunctions = "";	
			var belongs = "";
			//获取当前的归属值
			var belongCT = $('#select_belongCT').val();
			var belongAll = $('#select_belongAll').val();
			var belongCXEE = $('#select_belong').val();
			if("${staff.email}".indexOf(".co.jp") > 0){
				belongs = 1;
			}else{
				belongs = 2;
			}
			if(belongCT==belongCXEE || belongCT==belongAll){
				belongs=belongCT;
			}else if(belongCXEE==belongAll){
				belongs=belongCXEE;
			}
			if(belongs==1){
				staffNames = $('#serach_staffNameCT').val();//姓名
				positions = $('#select_positionCT').val();//职责
				departments = $('#select_departmentCT').val();//部
				branchs = $('#select_branchCT').val();//group 
				jobCategorys = $('#select_jobCategoryCT').val();//职种
				sorts = $('#select_sortCT').val();//社员区分
				categorys = $('#select_categoryCT').val();//开发阶段
				carMakers = $('#select_carMakerCT').val();//车厂
				projectFunctions = $('#select_projectFunctionCT').val();//机能
				tempPJNos = $('#sel_tempPJNoCT').val();//临时项目号
				PJNos = $('#select_PJNoCT').val();//项目号
				PJNames = $('#sel_PJNameCT').val();//PJName
			}else if(belongs==0){
				sorts = $('#select_sortAll').val();
				categorys = $('#select_categoryAll').val();
				transferNos = $('#serach_transferNoAll').val();
				carMakers = $('#select_carMakerAll').val();
				projectFunctions = $('#select_projectFunctionAll').val();
				tempPJNos = $('#sel_tempPJNoAll').val();
				PJNos = $('#select_PJNoAll').val();
				PJNames = $('#sel_PJNameAll').val();
			}else if(belongs==2){
				staffNames = $('#serach_staffNameCXEE').val();//姓名1
				positions = $('#select_positionCXEE').val();//职责1
				departments = $('#select_departmentCXEE').val();//部1
				branchs = $('#select_branchCXEE').val();//group 1
				projectClientNos = $('#serach_projectClientNoCXEE').val();//依赖号1
				transferNos = $('#serach_transferNoCXEE').val();//管理项番1
				projectClientNames = $('#select_projectClientNameCXEE').val();//委托方1
				sorts = $('#select_sortCXEE').val();//1
				categorys = $('#select_categoryCXEE').val();//1
				carMakers = $('#select_carMakerCXEE').val();//1
				projectFunctions = $('#select_projectFunctionCXEE').val();
				tempPJNos = $('#sel_tempPJNoCXEE').val();//1
				PJNos = $('#select_PJNoCXEE').val();//1
				PJNames = $('#select_PJNameCXEE').val();//1serach_projectClientNoCXEE
			}
			
			
			
			var path = '';
			var param1 = 'staffName='+staffNames;
			var param2 = 'position='+positions;
			var param3 = 'department='+departments;
			var param4 = 'branch='+branchs;
			var param5 = 'sort='+sorts;
			var param6 = 'jobCategory='+jobCategorys;
			var param7 = 'projectClientNo='+projectClientNos;
			var param8 = 'projectNo='+projectNos;
			var param9 = 'tempPJNo='+tempPJNos;
			var param10 = 'transferNo='+transferNos;
			var param11 = 'category='+categorys;
			var param12 = 'projectClientName='+projectClientNames;
			var param13 = 'carMaker='+carMakers;
			var param14 = 'projectFunction='+projectFunctions;
			var param15 = 'PJName='+PJNames;
			var param16 = 'PJNo='+PJNos;
			
			
			var condition = param1+'&&'+param2+'&&'+param3+'&&'+param4+'&&'+param5+'&&'+param6+'&&'+param7+'&&'+param8+'&&'+param9+'&&'+param10+'&&'+param11+'&&'+param12+'&&'+param13+'&&'+param14+'&&'+param15+'&&'+param16;
			if(belongs == 2){
				path = 'totalHourManage/downloadTotalHourCXEE.do?'+condition;
			}else if(belongs == 1){
				path = 'totalHourManage/downloadTotalHourCT.do?'+condition;
			}else if(belongs == 0){
				path = 'totalHourManage/downloadTotalHour.do?'+condition;
			}
	   		var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'totalHour_download',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,	
			        page:path
			    	});
		    dg.ShowDialog(); 
		}	
	
	}
	function downloadClickCT(){
		if (confirm(I0006)) {
			
			var staffNames = $('#serach_staffName').val();
			
			var positions = $('#select_position').val();
			var departments = $('#select_department').val();
			
			var branchs = $('#select_branch').val();
			var sorts = $('#select_sort').val();
			
			var jobCategorys = $('#select_jobCategory').val();
			var projectClientNos = $('#serach_projectClientNo').val();
			var projectNos = $('#select_PJNo').val();
			var tempProjectNos = $('#serach_tempProjectNo').val();
			var belongs = $('#select_belong').val();
			
			var transferNos = $('#serach_transferNo').val();
			var categorys = $('#select_category').val();
			var projectClientNames = $('#select_projectClientName').val();
			var carMakers = $('#select_carMaker').val();
			var projectFunctions = $('#select_projectFunction').val();
			var projectNames = $('#sel_projectName').val();
			
			var path = '';
			var param1 = 'staffName='+(staffNames);
			var param2 = 'position='+positions;
			var param3 = 'department='+departments;
			var param4 = 'branch='+branchs;
			var param5 = 'sort='+sorts;
			var param6 = 'jobCategory='+jobCategorys;
			var param7 = 'projectClientNo='+projectClientNos;
			var param8 = 'projectNo='+projectNos;
			var param9 = 'tempProjectNo='+tempProjectNos;
			var param10 = 'transferNo='+transferNos;
			var param11 = 'category='+categorys;
			var param12 = 'projectClientName='+projectClientNames;
			var param13 = 'carMaker='+carMakers;
			var param14 = 'projectFunction='+projectFunctions;
			var param15 = 'projectName='+projectNames;
			
			var condition = param1+'&&'+param2+'&&'+param3+'&&'+param4+'&&'+param5+'&&'+param6+'&&'+param7+'&&'+param8+'&&'+param9+'&&'+param10+'&&'+param11+'&&'+param12+'&&'+param13+'&&'+param14+'&&'+param15;
			if($("#select_belong").val() == '2'){
				path = 'totalHourManage/downloadTotalHourCXEE.do?'+condition;
			}else if($("#select_belong").val() == '1'){
				path = 'totalHourManage/downloadTotalHourCT.do?'+condition;
			}else if($("#select_belong").val() == '0'){
				path = 'totalHourManage/downloadTotalHour.do?'+condition;
			}
	   		var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'totalHour_download',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,	
			        page:path
			    	});
		    dg.ShowDialog(); 
		}	
	
	}
	function downloadClickAll(){
		if (confirm(I0006)) {
			
			var staffNames = $('#serach_staffName').val();
			
			var positions = $('#select_position').val();
			var departments = $('#select_department').val();
			
			var branchs = $('#select_branch').val();
			var sorts = $('#select_sort').val();
			
			var jobCategorys = $('#select_jobCategory').val();
			var projectClientNos = $('#serach_projectClientNo').val();
			var projectNos = $('#select_PJNo').val();
			var tempProjectNos = $('#serach_tempProjectNo').val();
			var belongs = $('#select_belong').val();
			
			var transferNos = $('#serach_transferNo').val();
			var categorys = $('#select_category').val();
			var projectClientNames = $('#select_projectClientName').val();
			var carMakers = $('#select_carMaker').val();
			var projectFunctions = $('#select_projectFunction').val();
			var projectNames = $('#sel_projectName').val();
			
			var path = '';
			var param1 = 'staffName='+(staffNames);
			var param2 = 'position='+positions;
			var param3 = 'department='+departments;
			var param4 = 'branch='+branchs;
			var param5 = 'sort='+sorts;
			var param6 = 'jobCategory='+jobCategorys;
			var param7 = 'projectClientNo='+projectClientNos;
			var param8 = 'projectNo='+projectNos;
			var param9 = 'tempProjectNo='+tempProjectNos;
			var param10 = 'transferNo='+transferNos;
			var param11 = 'category='+categorys;
			var param12 = 'projectClientName='+projectClientNames;
			var param13 = 'carMaker='+carMakers;
			var param14 = 'projectFunction='+projectFunctions;
			var param15 = 'projectName='+projectNames;
			
			var condition = param1+'&&'+param2+'&&'+param3+'&&'+param4+'&&'+param5+'&&'+param6+'&&'+param7+'&&'+param8+'&&'+param9+'&&'+param10+'&&'+param11+'&&'+param12+'&&'+param13+'&&'+param14+'&&'+param15;
			if($("#select_belong").val() == '2'){
				path = 'totalHourManage/downloadTotalHourCXEE.do?'+condition;
			}else if($("#select_belong").val() == '1'){
				path = 'totalHourManage/downloadTotalHourCT.do?'+condition;
			}else if($("#select_belong").val() == '0'){
				path = 'totalHourManage/downloadTotalHour.do?'+condition;
			}
	   		var dg = new $.dialog({
			        title:tbldownloadtitle,
			        id:'totalHour_download',
			        width:400,
			        height:400,
			        iconTitle:false,
			        cover:true,
			        maxBtn:false,
			        xButton:true,
			        resize:false,	
			        page:path
			    	});
		    dg.ShowDialog(); 
		}	
	
	}
		
	//检索
	function searchClick(pageIdx) {

		
		//$('#sumGridTableCXEE').hide();
		var belong               = $("#select_belong").val();
        var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
        var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
        if (belong == 1 && SpecialRole1Flag != 1) {
        	alert(E00045);
	   		return false;
        }
        if (belong == 2 && SpecialRole2Flag != 1) {
        	alert(E00046);
	   		return false;
        }

        /*******20140625合并单元格修改：增加判断改善性能******/
		PageHandle.HasSearch = 0;
		if(!pageIdx){
			pageIdx = 1;
		}
		$("#btn_query").attr("disabled","disabled");
		//$("#btn_download").removeAttr("disabled");
		//$("#gridAll").html("");
		$("#gridCXEE").html("");
		//$("#gridCT").html("");
		//$("sumGridTable").html("");
		$("#sumGridTableCXEE").html("");
		//$("sumGridTableCT").html("");

    	PageHandle.initPageSize = PageHandle.store.pageSize;
    	PageHandle.storeCreatCXEE();
		PageHandle.renderCXEE();
		PageHandle.sumStoreCreatCXEE();
		PageHandle.sumRenderCXEE();
		PageHandle.store.loadPage(pageIdx);
		PageHandle.HaSearch = 1;
		PageHandle.sumStore.loadPage();
    	//if($("#select_belong").val() == '2'){
    		
    	/* }else if($("#select_belong").val() == '1'){
    		PageHandle.storeCreatCT();
    		PageHandle.renderCT();
    		PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    	}else{
    		PageHandle.storeCreatAll();
    		PageHandle.renderAll();
    		PageHandle.sumStoreCreat();
    		PageHandle.sumRender();
    	} */
		
		
		//if($("#select_belong").val() == '2'){
    		/* $("#sumGridTableCXEE").html("");
    		PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE(); */
    	/* }else if($("#select_belong").val() == '1'){
    		$("#sumGridTableCT").html("");
    		PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    	}else{
    		$("#sumGridTable").html("");
    		PageHandle.sumStoreCreat();
    		PageHandle.sumRender();
    	} */
		
	}
	
	function searchClickCT(pageIdx) {

		var belong               = $("#select_belongCT").val();
        var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
        var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
        if (belong == 1 && SpecialRole1Flag != 1) {
        	alert(E00045);
	   		return false;
        }
        if (belong == 2 && SpecialRole2Flag != 1) {
        	alert(E00046);
	   		return false;
        }

        /*******20140625合并单元格修改：增加判断改善性能******/
		PageHandle.HasSearch = 0;
		if(!pageIdx){
			pageIdx = 1;
		}
		$("#btn_queryCT").attr("disabled","disabled");
		//$("#btn_downloadCT").removeAttr("disabled");
		//$("#gridAll").html("");
		//$("#gridCXEE").html("");
		$("#gridCT").html("");
		//$("#sumGridTable").html("");
		//$("sumGridTableCXEE").html("");
		$("#sumGridTableCT").html("");

    	PageHandle.initPageSize = PageHandle.store.pageSize;
   		PageHandle.storeCreatCT();
   		PageHandle.renderCT();
   		PageHandle.sumStoreCreatCT();
   		PageHandle.sumRenderCT();
		PageHandle.store.loadPage(pageIdx);
		PageHandle.HaSearch = 1;
		PageHandle.sumStore.loadPage();
		
   		/* $("#sumGridTableCT").html("");
   		PageHandle.sumStoreCreatCT();
   		PageHandle.sumRenderCT(); */
    	
		
	}
	
	function searchClickAll(pageIdx) {

		var belong               = $("#select_belongAll").val();
        var SpecialRole1Flag     = $("#SpecialRole1Flag").val();
        var SpecialRole2Flag     = $("#SpecialRole2Flag").val();
        if (belong == 1 && SpecialRole1Flag != 1) {
        	alert(E00045);
	   		return false;
        }
        if (belong == 2 && SpecialRole2Flag != 1) {
        	alert(E00046);
	   		return false;
        }

        /*******20140625合并单元格修改：增加判断改善性能******/
		PageHandle.HasSearch = 0;
		if(!pageIdx){
			pageIdx = 1;
		}
		$("#btn_queryAll").attr("disabled","disabled");
		//$("#btn_downloadAll").removeAttr("disabled");
		
		
		
		
		
		

    	PageHandle.initPageSize = PageHandle.store.pageSize;
		//if(SpecialRole1Flag==1 && SpecialRole2Flag==1){
			$("#gridAll").html("");
			PageHandle.storeCreatAll();
    		PageHandle.renderAll();
    		$("#sumGridTable").html("");
    		PageHandle.sumStoreCreat();
    		PageHandle.sumRender();
		/* }else if(SpecialRole1Flag==1){
			$("#gridCT").html("");
			PageHandle.storeCreatCT();
    		PageHandle.renderCT();
    		$("sumGridTableCT").html("");
    		PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
		}else if(SpecialRole2Flag==1){
			$("#gridCXEE").html("");
			PageHandle.storeCreatCXEE();
    		PageHandle.renderCXEE();
    		$("sumGridTableCXEE").html("");
    		PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
		}else{
			alert(E00050);
			return ;
		} */
    	/* if($("#select_belong").val() == '2'){
    		PageHandle.storeCreatCXEE();
    		PageHandle.renderCXEE();
    		PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
    	}else if($("#select_belong").val() == '1'){
    		PageHandle.storeCreatCT();
    		PageHandle.renderCT();
    		PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    	}else{ */
    		
    	//}
		PageHandle.store.loadPage(pageIdx);
		PageHandle.HaSearch = 1;
		
		/* if($("#select_belong").val() == '2'){
    		$("#sumGridTableCXEE").html("");
    		PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
    	}else if($("#select_belong").val() == '1'){
    		$("#sumGridTableCT").html("");
    		PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    	}else{ */
    		/* $("#sumGridTable").html("");
    		PageHandle.sumStoreCreat();
    		PageHandle.sumRender(); */
    	//}
		PageHandle.sumStore.loadPage();
	}
	function cleanClickCT() {
		var obj = $("table tr td input[type='text'] ");
		obj.val("");
		var objSelect = $("table tr td select ");
		for(i=0 ; i<objSelect.length;i++){
			 objSelect[i].options[0].selected=true;
		}
	}
	/*****设置语言****/
	function setPageByLanguage(){
		$("#btn_query").val($.i18n.prop('outsourcingInfo_Button_Query'));
		$("#btn_download").val($.i18n.prop('workHour_Button_Download'));
		$("#label_department").html($.i18n.prop('workHour_string_department'));
		$("#label_departmentCT").html($.i18n.prop('workHour_string_departmentCT'));
		$("#label_branch").html($.i18n.prop('workHour_string_branch'));
		$("#label_branchCT").html($.i18n.prop('workHour_string_branchCT'));
		$("#label_position").html($.i18n.prop('workHour_String_position'));
		$("#label_positionCT").html($.i18n.prop('workHour_String_positionCT'));
		$("#label_staffName").html($.i18n.prop('workHour_string_StaffName'));
		$("#label_staffNameCT").html($.i18n.prop('workHour_string_StaffNameCT'));
		$("#label_jobCategoryCT").html($.i18n.prop('workHour_String_jobCategoryCT'));
		//$("#lb_projectType").html($.i18n.prop('workHour_String_projectType'));
		$("#label_PJName").html($.i18n.prop('workHour_String_PJName'));
		$("#label_PJNameAll").html($.i18n.prop('workHour_String_PJNameAll'));
		$("#label_PJNameCT").html($.i18n.prop('workHour_String_PJNameCT'));
		$("#label_manageCodeAll").html($.i18n.prop('workHour_String_manageCodeAll'));
		$("#label_manageCode").html($.i18n.prop('workHour_String_manageCode'));
		//$("#lb_periods").html($.i18n.prop('workHour_String_periods'));
		$("#label_sort").html($.i18n.prop('workHour_String_label_sort'));
		$("#label_sortAll").html($.i18n.prop('workHour_String_label_sortAll'));
		$("#label_sortCT").html($.i18n.prop('workHour_String_label_sortCT'));
		$("#label_projectClientNo").html($.i18n.prop('workHour_String_projectClientNo'));
		$("#label_PJNo").html($.i18n.prop('workHour_String_label_PJNo'));
		$("#label_PJNoCT").html($.i18n.prop('workHour_String_label_PJNoCT'));
		$("#label_PJNoAll").html($.i18n.prop('workHour_String_label_PJNoAll'));
		$("#label_tempPJNoAll").html($.i18n.prop('workHour_String_label_tempPJNoAll'));
		$("#label_tempPJNoCT").html($.i18n.prop('workHour_String_label_tempPJNoCT'));
		$("#label_tempPJNo").html($.i18n.prop('workHour_String_label_tempPJNo'));
		$("#label_belong").html($.i18n.prop('workHour_String_label_belong'));
		$("#label_belongCT").html($.i18n.prop('workHour_String_label_belongCT'));
		$("#label_belongAll").html($.i18n.prop('workHour_String_label_belongAll'));
		$("#label_task").html($.i18n.prop('workHour_String_label_task'));
		$("#label_taskAll").html($.i18n.prop('workHour_String_label_taskAll'));
		$("#label_taskCT").html($.i18n.prop('workHour_String_label_taskCT'));
		$("#label_maker").html($.i18n.prop('workHour_String_label_maker'));
		$("#label_makerAll").html($.i18n.prop('workHour_String_label_makerAll'));
		$("#label_makerCT").html($.i18n.prop('workHour_String_label_makerCT'));
		$("#lb_function").html($.i18n.prop('workHour_String_lb_function'));
		$("#lb_functionCT").html($.i18n.prop('workHour_String_lb_functionCT'));
		$("#lb_functionAll").html($.i18n.prop('workHour_String_lb_functionAll'));
		
		$("#label_projectClientName").html($.i18n.prop('workHour_String_label_projectClientName'));
		tbldownloadtitle=$.i18n.prop('workHour_string_tblinfo');
		tblcancel = $.i18n.prop('workHour_string_tblcancel');
		PageHandle.String_i18n.GridTitle = $.i18n.prop('workHour_String_GridTitle');
		PageHandle.String_i18n.GridTitle_No = $.i18n.prop('workHour_String_GridTitle_No');
		PageHandle.String_i18n.GridTitle_NoCT = $.i18n.prop('workHour_String_GridTitle_NoCT');
		PageHandle.String_i18n.GridTitle_department = $.i18n.prop('workHour_String_GridTitle_department');
		PageHandle.String_i18n.GridTitle_departmentCT = $.i18n.prop('workHour_String_GridTitle_departmentCT');
		PageHandle.String_i18n.GridTitle_branch = $.i18n.prop('workHour_String_GridTitle_branch');
		PageHandle.String_i18n.GridTitle_branchCT = $.i18n.prop('workHour_String_GridTitle_branchCT');
		PageHandle.String_i18n.GridTitle_staffName = $.i18n.prop('workHour_String_GridTitle_staffName');
		PageHandle.String_i18n.GridTitle_staffNameCT = $.i18n.prop('workHour_String_GridTitle_staffNameCT');
		PageHandle.String_i18n.GridTitle_positionCT = $.i18n.prop('workHour_String_GridTitle_positionCT');
		PageHandle.String_i18n.GridTitle_position = $.i18n.prop('workHour_String_GridTitle_position');
		PageHandle.String_i18n.GridTitle_jobCategoryCT = $.i18n.prop('workHour_String_GridTitle_jobCategoryCT');
		PageHandle.String_i18n.GridTitle_PJNoCT = $.i18n.prop('workHour_String_GridTitle_PJNoCT');
		PageHandle.String_i18n.GridTitle_tempPJNoCT = $.i18n.prop('workHour_String_GridTitle_tempPJNoCT');
		PageHandle.String_i18n.GridTitle_PJNameCT = $.i18n.prop('workHour_String_GridTitle_PJNameCT');
		
		PageHandle.String_i18n.GridTitle_sortCT = $.i18n.prop('workHour_String_GridTitle_sortCT');
		PageHandle.String_i18n.GridTitle_category = $.i18n.prop('workHour_String_GridTitle_category');
		PageHandle.String_i18n.GridTitle_projectClientNo = $.i18n.prop('workHour_String_GridTitle_projectClientNo');
		PageHandle.String_i18n.GridTitle_projectName = $.i18n.prop('workHour_String_GridTitle_projectName');
		PageHandle.String_i18n.GridTitle_projectClientName = $.i18n.prop('workHour_String_GridTitle_projectClientName');
		PageHandle.String_i18n.GridTitle_carMaker = $.i18n.prop('workHour_String_GridTitle_carMaker');
		

		PageHandle.String_i18n.GridTitle_achieve1 = $.i18n.prop('workHour_String_GridTitle_achieve1');
		PageHandle.String_i18n.GridTitle_achieve2 = $.i18n.prop('workHour_String_GridTitle_achieve2');
		PageHandle.String_i18n.GridTitle_achieve3 = $.i18n.prop('workHour_String_GridTitle_achieve3');
		PageHandle.String_i18n.GridTitle_achieve4 = $.i18n.prop('workHour_String_GridTitle_achieve4');
		PageHandle.String_i18n.GridTitle_achieve5 = $.i18n.prop('workHour_String_GridTitle_achieve5');
		PageHandle.String_i18n.GridTitle_achieve6 = $.i18n.prop('workHour_String_GridTitle_achieve6');
		PageHandle.String_i18n.GridTitle_achieve7 = $.i18n.prop('workHour_String_GridTitle_achieve7');
		PageHandle.String_i18n.GridTitle_achieve8 = $.i18n.prop('workHour_String_GridTitle_achieve8');
		PageHandle.String_i18n.GridTitle_achieve9 = $.i18n.prop('workHour_String_GridTitle_achieve9');
		PageHandle.String_i18n.GridTitle_achieve10 = $.i18n.prop('workHour_String_GridTitle_achieve10');
		PageHandle.String_i18n.GridTitle_achieve11 = $.i18n.prop('workHour_String_GridTitle_achieve11');
		PageHandle.String_i18n.GridTitle_achieve12 = $.i18n.prop('workHour_String_GridTitle_achieve12');

		PageHandle.String_i18n.String_Times = $.i18n.prop('workHour_String_Times');
		//分页
		PageHandle.String_i18n.GridBbrRecords = $.i18n.prop('workHour_String_GridBbrRecords');
		PageHandle.String_i18n.GridBbrDisplayMsg = $.i18n.prop('workHour_String_GridBbrDisplayMsg');
		PageHandle.String_i18n.GridBbrEmptyMsg = $.i18n.prop('workHour_String_GridBbrEmptyMsg');
		//合计
		PageHandle.String_i18n.SumGridTitle = $.i18n.prop('workHour_String_SumGridTitle');
		
		I0006=$.i18n.prop('I0006');
		I0007=$.i18n.prop('I0007');
		E00045 = $.i18n.prop('E00045');
		E00046 = $.i18n.prop('E00046');
		E00050 = $.i18n.prop('E00050');
	}
	
	
	
	
	function resetGrid(store){
		var sumAchieve1 = 0, sumAchieve2 = 0, sumAchieve3 = 0, sumAchieve4 = 0, sumAchieve5 = 0, sumAchieve6 = 0,
		    sumAchieve7 = 0, sumAchieve8 = 0, sumAchieve9 = 0, sumAchieve10 = 0, sumAchieve11 = 0, sumAchieve12 = 0;
		var rec;
		for (var i = 0; i < store.getCount(); i++){
			rec = store.getAt(i).data;
			sumAchieve1 += Number(rec.achieve1);
			sumAchieve2 += Number(rec.achieve2);
			sumAchieve3 += Number(rec.achieve3);
			sumAchieve4 += Number(rec.achieve4);
			sumAchieve5 += Number(rec.achieve5);
			sumAchieve6 += Number(rec.achieve6);
			sumAchieve7 += Number(rec.achieve7);
			sumAchieve8 += Number(rec.achieve8);
			sumAchieve9 += Number(rec.achieve9);
			sumAchieve10 += Number(rec.achieve10);
			sumAchieve11 += Number(rec.achieve11);
			sumAchieve12 += Number(rec.achieve12);
		}
		/* 
		if(sumAchieve1 == 0){
			PageHandle.grid.down('#achieve1').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve1').setVisible(true);
		}
		if (sumAchieve2 == 0) {
			PageHandle.grid.down('#achieve2').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve2').setVisible(true);
		}
		if (sumAchieve3 == 0) {
			PageHandle.grid.down('#achieve3').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve3').setVisible(true);
		}
		if (sumAchieve4 == 0) {
			PageHandle.grid.down('#achieve4').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve4').setVisible(true);
		}
		if (sumAchieve5 == 0) {
			PageHandle.grid.down('#achieve5').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve5').setVisible(true);
		}
		if (sumAchieve6 == 0) {
			PageHandle.grid.down('#achieve6').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve6').setVisible(true);
		}
		if (sumAchieve7 == 0) {
			PageHandle.grid.down('#achieve7').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve7').setVisible(true);
		}
		if (sumAchieve8 == 0) {
			PageHandle.grid.down('#achieve8').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve8').setVisible(true);
		}
		if (sumAchieve9 == 0) {
			PageHandle.grid.down('#achieve9').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve9').setVisible(true);
		}
		if (sumAchieve10 == 0) {
			PageHandle.grid.down('#achieve10').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve10').setVisible(true);
		}
		if (sumAchieve11 == 0) {
			PageHandle.grid.down('#achieve11').setVisible(false); 
		} else {
			PageHandle.grid.down('#achieve11').setVisible(true);
		}
		if (sumAchieve12 == 0) { 
			PageHandle.grid.down('#achieve12').setVisible(false);
		} else {
			PageHandle.grid.down('#achieve12').setVisible(true);
		}		 */
	}
	function changeDepartmentList(){
		var departmentnew  = $("#select_departmentCXEE").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/departmentChanged.do", 
                dataType: "json", 
                data:{"department":departmentnew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("select_branchCXEE");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.branch, content.branchID));
                            });
                	}
                	
                } 
         });
	}
	function changeDepartmentListCT(){
		var departmentnew  = $("#select_departmentCT").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/departmentChangedCT.do", 
                dataType: "json", 
                data:{"department":departmentnew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("select_branchCT");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.branch, content.branchID));
                            });
                	}
                	
                } 
         });
	}
	function changePJNoCT(){
		var PJNoNew  = $("#select_PJNoCT").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/PJNoChanged.do", 
                dataType: "json", 
                data:{"PJNo":PJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("sel_PJNameCT");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		if($("#select_PJNoCT").val()=='0'){
               	    	 changeTempPJNoCT();
               	     	}
                	}
                	
                } 
         });
	     
	}
	function changePJNoCXEE(){
		var PJNoNew  = $("#select_PJNoCXEE").val();
		
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/PJNoChanged.do", 
                dataType: "json", 
                data:{"PJNo":PJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("select_PJNameCXEE");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		if($("#select_PJNoCXEE").val()=='0'){
               	    	 changeTempPJNoCXEE();
               	     	}
                	}
                	
                } 
         });
	     
	}
	function changePJNoAll(){
		var PJNoNew  = $("#select_PJNoAll").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/PJNoChanged.do", 
                dataType: "json", 
                data:{"PJNo":PJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("sel_PJNameAll");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		if($("#select_PJNoAll").val()=='0'){
               	    	 changeTempPJNoAll();
               	     	}
                	}
                	
                } 
         });
	     
	}
	function changeTempPJNoCT(){
		var tempPJNoNew  = $("#sel_tempPJNoCT").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/tempPJNoChanged.do", 
                dataType: "json", 
                data:{"tempPJNo":tempPJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("sel_PJNameCT");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		if($("#sel_tempPJNoCT").val()=='0'){
            				changePJNoCT();
            			}
                	}
                } 
         });
	     
	}
	function changeTempPJNoCXEE(){
		var tempPJNoNew  = $("#sel_tempPJNoCXEE").val();
		
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/tempPJNoChanged.do", 
                dataType: "json", 
                data:{"tempPJNo":tempPJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("select_PJNameCXEE");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		 if($("#sel_tempPJNoCXEE").val()=='0'){
             				changePJNoCXEE();
             			}
                	}
                	
                } 
         });
	    
	}
	function changeTempPJNoAll(){
		var tempPJNoNew  = $("#sel_tempPJNoAll").val();
	     $.ajax({ 
                type: "post", 
                url: "totalHourManage/tempPJNoChanged.do", 
                dataType: "json", 
                data:{"tempPJNo":tempPJNoNew}, 
                success: function (result) {
                	{   
                		var e=document.getElementById("sel_PJNameAll");
        				e.options.length=1;	//清楚下拉框数据	
                		//$("#branch").empty();
                		$.each(result.branch,function(index,content) {
                			e.options.add(new Option(content.PJName, content.PJName));
                            });
                		 if($("#sel_tempPJNoAll").val()=='0'){
             				changePJNoAll();
             			}
                	}
                	
                } 
         });
	    
	}
	
	function changeBelong(){
		var sel_belong =  $("#select_belong").val();
		
		var objcxee = document.getElementById("gridCXEE");
		var objcxeeSum = document.getElementById("sumGridTableCXEE");
    	$("#gridCXEE").html("");
    	$("#sumGridTableCXEE").html("");
    	
		var objct = document.getElementById("gridCT");
		var objctSum = document.getElementById("sumGridTableCT");
    	$("#gridCT").html("");
    	$("#sumGridTableCT").html("");
    	
		var objall = document.getElementById("gridAll");
		var objallSum = document.getElementById("sumGridTable");
    	$("#gridAll").html("");
    	$("#sumGridTable").html("");
    	
		if(sel_belong == "2"){
			objall.style.display = "none";
			objcxee.style.display = "block";
			objct.style.display = "none";
			objallSum.style.display = "none";
			objctSum.style.display = "none";
			objcxeeSum.style.display = "block";
			/* $("#select_projectClientName").removeAttr("disabled");
	    	$("#select_projectClientName").attr({"style":"width: 70px;height:20px"});
	    	$("#select_projectClientName").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").removeAttr("readonly");
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px;" });
	    	$("#serach_projectClientNo").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").removeAttr("readonly");
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px;" });
	    	$("#serach_transferNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").attr({"onkeydown":"enter_down()"});
	    	$("#sel_projectName").removeAttr("disabled");
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; "});
	    	$("#sel_projectName").attr({"maxlength":"200 "});
	    	$("#sel_projectName").attr({"onkeydown":"enter_down(); "}); */
			$("#CXEE_condition").show();
			$("#CT_condition").hide();
			$("#All_condition").hide();
	    	PageHandle.storeCreatCXEE();
			PageHandle.renderCXEE();
			
			PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
			
		}else if (sel_belong == "1"){
			objall.style.display = "none";
			objcxee.style.display = "none";
			objct.style.display = "block";
			objallSum.style.display = "none";
			objctSum.style.display = "block";
			objcxeeSum.style.display = "none";
			/* $("#select_projectClientName").attr({"disabled":"disabled"});
	    	$("#select_projectClientName").attr({"style":"width: 70px; height:20px;IME-MODE: disabled;background: #999999"});
	    	$("#serach_projectClientNo").attr({"readonly":"readonly"});
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px; IME-MODE: disabled;background: #999999"});
	    	$("#serach_transferNo").attr({"readonly":"readonly"});
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px; IME-MODE: disabled;background: #999999"});
	    	$("#sel_projectName").attr({"disabled":"disabled"});
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; IME-MODE: disabled;background: #999999"});*/
	    	$("#CT_condition").show();
			$("#CXEE_condition").hide();
			$("#All_condition").hide(); 
	    	PageHandle.storeCreatCT();
			PageHandle.renderCT();
		    PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    		$("#select_belongCT").val(sel_belong);
		}else if( sel_belong == "0"){
			objall.style.display = "block";
			objcxee.style.display = "none";
			objct.style.display = "none";
			objallSum.style.display = "block";
			objctSum.style.display = "none";
			objcxeeSum.style.display = "none";
			/* $("#select_projectClientName").removeAttr("disabled");
	    	$("#select_projectClientName").attr({"style":"width: 70px;height:20px"});
	    	$("#select_projectClientName").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").removeAttr("readonly");
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px;" });
	    	$("#serach_projectClientNo").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").removeAttr("readonly");
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px;" });
	    	$("#serach_transferNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").attr({"onkeydown":"enter_down()"});
	    	$("#sel_projectName").removeAttr("disabled");
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; "});
	    	$("#sel_projectName").attr({"onkeydown":"enter_down(); "}); */
	    	$("#CT_condition").hide();
			$("#CXEE_condition").hide();
			$("#All_condition").show();
	    	PageHandle.storeCreatAll();
			PageHandle.renderAll();
			PageHandle.sumStoreCreat();
    		PageHandle.sumRender();
    		$("#select_belongAll").val(sel_belong);
		}
		
	}
	
	function changeBelongCT(){
		var sel_belong =  $("#select_belongCT").val();
		
		var objcxee = document.getElementById("gridCXEE");
		var objcxeeSum = document.getElementById("sumGridTableCXEE");
    	$("#gridCXEE").html("");
    	$("#sumGridTableCXEE").html("");
    	
		var objct = document.getElementById("gridCT");
		var objctSum = document.getElementById("sumGridTableCT");
    	$("#gridCT").html("");
    	$("#sumGridTableCT").html("");
    	
		var objall = document.getElementById("gridAll");
		var objallSum = document.getElementById("sumGridTable");
    	$("#gridAll").html("");
    	$("#sumGridTable").html("");
    	
		if(sel_belong == "2"){
			objall.style.display = "none";
			objcxee.style.display = "block";
			objct.style.display = "none";
			objallSum.style.display = "none";
			objctSum.style.display = "none";
			objcxeeSum.style.display = "block";
			/* $("#select_projectClientName").removeAttr("disabled");
	    	$("#select_projectClientName").attr({"style":"width: 70px;height:20px"});
	    	$("#select_projectClientName").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").removeAttr("readonly");
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px;" });
	    	$("#serach_projectClientNo").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").removeAttr("readonly");
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px;" });
	    	$("#serach_transferNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").attr({"onkeydown":"enter_down()"});
	    	$("#sel_projectName").removeAttr("disabled");
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; "});
	    	$("#sel_projectName").attr({"maxlength":"200 "});
	    	$("#sel_projectName").attr({"onkeydown":"enter_down(); "}); */
	    	$("#CT_condition").hide();
	    	$("#CXEE_condition").show();
	    	$("#All_condition").hide();
			PageHandle.storeCreatCXEE();
			PageHandle.renderCXEE();
			
			PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
    		$("#select_belong").val(sel_belong);
			
		}else if (sel_belong == "1"){
			objall.style.display = "none";
			objcxee.style.display = "none";
			objct.style.display = "block";
			objallSum.style.display = "none";
			objctSum.style.display = "block";
			objcxeeSum.style.display = "none";
			/* $("#select_projectClientName").attr({"disabled":"disabled"});
	    	$("#select_projectClientName").attr({"style":"width: 70px; height:20px;IME-MODE: disabled;background: #999999"});
	    	$("#serach_projectClientNo").attr({"readonly":"readonly"});
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px; IME-MODE: disabled;background: #999999"});
	    	$("#serach_transferNo").attr({"readonly":"readonly"});
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px; IME-MODE: disabled;background: #999999"});
	    	$("#sel_projectName").attr({"disabled":"disabled"});
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; IME-MODE: disabled;background: #999999"}); */
	    	$("#CT_condition").show();
	    	$("#CXEE_condition").hide();
	    	$("#All_condition").hide();
	    	PageHandle.storeCreatCT();
			PageHandle.renderCT();
		    PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
		}else if( sel_belong == "0"){
			objall.style.display = "block";
			objcxee.style.display = "none";
			objct.style.display = "none";
			objallSum.style.display = "block";
			objctSum.style.display = "none";
			objcxeeSum.style.display = "none";
			/* $("#select_projectClientName").removeAttr("disabled");
	    	$("#select_projectClientName").attr({"style":"width: 70px;height:20px"});
	    	$("#select_projectClientName").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").removeAttr("readonly");
	    	$("#serach_projectClientNo").attr({"style":"width: 70px;height:20px;" });
	    	$("#serach_projectClientNo").attr({"onkeydown":"enter_down()"});
	    	$("#serach_projectClientNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").removeAttr("readonly");
	    	$("#serach_transferNo").attr({"style":"width: 76px;height:20px;" });
	    	$("#serach_transferNo").attr({"maxlength":"200"});
	    	$("#serach_transferNo").attr({"onkeydown":"enter_down()"});
	    	$("#sel_projectName").removeAttr("disabled");
	    	$("#sel_projectName").attr({"style":"width: 175px;height:20px; "});
	    	$("#sel_projectName").attr({"onkeydown":"enter_down(); "}); */
	    	$("#All_condition").show();
	    	$("#CXEE_condition").hide();
	    	$("#CT_condition").hide();
	    	PageHandle.storeCreatAll();
			PageHandle.renderAll();
			PageHandle.sumStoreCreat();
    		PageHandle.sumRender();
    		$("#select_belongAll").val(sel_belong);
		}
		
	}
	
	
	function changeBelongAll(){
		var belong = $("#select_belongAll").val();
		var objcxee = document.getElementById("gridCXEE");
		var objcxeeSum = document.getElementById("sumGridTableCXEE");
    	$("#gridCXEE").html("");
    	$("#sumGridTableCXEE").html("");
    	
		var objct = document.getElementById("gridCT");
		var objctSum = document.getElementById("sumGridTableCT");
    	$("#gridCT").html("");
    	$("#sumGridTableCT").html("");
    	
		var objall = document.getElementById("gridAll");
		var objallSum = document.getElementById("sumGridTable");
    	$("#gridAll").html("");
    	$("#sumGridTable").html("");
		
		if(belong==1){
			objall.style.display = "none";
			objcxee.style.display = "none";
			objct.style.display = "block";
			objallSum.style.display = "none";
			objctSum.style.display = "block";
			objcxeeSum.style.display = "none";
			$("#gridCXEE").hide();
			$("#gridAll").hide();
			$("#gridCT").show();
			$("#All_condition").hide();
	    	$("#CXEE_condition").hide();
	    	$("#CT_condition").show();
	    	PageHandle.storeCreatCT();
			PageHandle.renderCT();
		    PageHandle.sumStoreCreatCT();
    		PageHandle.sumRenderCT();
    		$("#select_belongCT").val(belong);
		}else if(belong==2){
			objall.style.display = "none";
			objcxee.style.display = "block";
			objct.style.display = "none";
			objallSum.style.display = "none";
			objctSum.style.display = "none";
			objcxeeSum.style.display = "block";
			$("#gridCXEE").show();
			$("#gridAll").hide();
			$("#gridCT").hide();
			$("#All_condition").hide();
	    	$("#CXEE_condition").show();
	    	$("#CT_condition").hide();
	    	PageHandle.storeCreatCXEE();
			PageHandle.renderCXEE();
		    PageHandle.sumStoreCreatCXEE();
    		PageHandle.sumRenderCXEE();
    		$("#select_belong").val(belong);
		}else if(belong==0){
			$("#gridCXEE").hide();
			$("#gridAll").show();
			$("#gridCT").hide();
			$("#All_condition").show();
	    	$("#CXEE_condition").hide();
	    	$("#CT_condition").hide();
	    	PageHandle.storeCreatAll();
			PageHandle.renderAll();
		    PageHandle.sumStoreCreatAll();
    		PageHandle.sumRenderAll();
		}
		
	}
</script>
</head>
<body style="width:100%;">
<input type="hidden" id ="StaffDepartment" value = "${StaffDepartment}">
<input type="hidden" id ="DepartmentFlag" value = "${DepartmentFlag}">
<input type="hidden" id ="QueryRoleFlag" value = "${QueryRoleFlag}">
<input type="hidden" id ="DownloadRoleFlag" value = "${DownloadRoleFlag}">
<input type="hidden" id ="SpecialRole1Flag" value = "${SpecialRole1Flag}">
<input type="hidden" id ="SpecialRole2Flag" value = "${SpecialRole2Flag}">
<input type="hidden" id ="DepartmentFlag" value = "${DepartmentFlag}">
 <div style="width:100%;height:60px;" id="CXEE_condition">
 	<div style="width:100%;height:23px;">
 	<table style="float:left;">
	 	<tr style="height:30px;">
	 	
	 		<th style="padding-left:25px;">
				<label id="label_staffName" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<input type="text" id="serach_staffNameCXEE"  style="width: 100px;height:20px;" maxlength="50" onkeydown="enter_down();"/>
			</td>
			<th style="padding-left:25px;">
				<label id="label_position" style="color:black"></label>
			</th>
			<td style="padding-left:10px;">
				<select name="selectname_position" id="select_positionCXEE" style="width: 95px" onkeydown="enter_down();">
				<option value="" selected></option>
				<c:forEach items="${staffposition}" var="position" varStatus="vs">
                    	<option value="${position.position}" >${position.position}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_department" style="color:black"></label>
			</th>
			<td style="padding-left:10px;">
				<select name="selectname_department" id="select_departmentCXEE" style="width: 150px" onChange="changeDepartmentList()" onkeydown="enter_down();">
				<option value="" selected></option>
				<c:forEach items="${departList}" var="departList" varStatus="vs">
				<option value="${departList.departmentID}" >${departList.department}</option>
				</c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_branch" style="color:black"></label>
			</th>
			<td style="padding-left:25px;">
				<select name="selectname_branch" id="select_branchCXEE"  style="width: 150px" onkeydown="enter_down();" >
				<option value="" selected></option>
				<c:forEach items="${branchSelect}" var="branchSelect" varStatus="bs">
                <option value="${branchSelect.branchID}" >${branchSelect.branch}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
					<label id="label_sort" style="color:black"></label>
			</th>
			<td style="padding-left:23px;vertical-align: middle;">
				<select name="selectname_sort" id="select_sortCXEE"  style="width: 80px" onkeydown="enter_down();" >
				<option value="" selected></option>
				<c:forEach items="${staffSort}" var="staffSort" varStatus="bs">
                <option value="${staffSort.sort}" >${staffSort.sort}</option>
                </c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<table style="float:right;">
				<tr style="height:30px;">
					<td>
			            <c:if test="${QueryRoleFlag == '1'}">
		    			<input id="btn_query" type="button"   style="width: 50px" onclick = "searchClick()"/>
			            </c:if>
					</td>
					<td>
			            <c:if test="${DownloadRoleFlag == '1'}">
			            <c:if test="${SpecialRole2Flag == '1'}">
		    			<input id="btn_download" type="button"   style="width: 50px" onclick = "downloadClick()" disabled value="D/L"/>
						</c:if>
						</c:if>
					</td>
				</tr>
	</table>
	</div>
	<div style="width:100%;height:23px;">
	
	<table style="float:left;">
			<tr style="height:30px;">
				
				<%-- <th style="padding-left:25px;">
				<label id=label_jobCategory style="color:black">職種</label>
			</th>
			<td style="padding-left:10px;">
				<select name="selectname_jobCategory" id="select_jobCategory" style="width: 100px" onkeydown="enter_down();">
				<option value="" selected></option>
				<c:forEach items="${jobCategory}" var="jobCategory" varStatus="jc">
                <option value="${jobCategory.jobCategory}" >${jobCategory.jobCategory}</option>
                </c:forEach>
				</select>
			</td> --%>
			
	 		<th style="padding-left:25px;">
				<label id="label_projectClientNo" style="color:black"></label>
			</th>
			<td style="padding-left:16px;vertical-align: middle;">
					<input type="text" id="serach_projectClientNoCXEE"  style="width: 75px;height:20px;" maxlength="200" onkeydown="enter_down();"/>
			</td>
			<th style="padding-left:25px;">
				<label id="label_PJNo" style="color:black"></label>
			</th>
			<td style="padding-left:5px;vertical-align: middle;">
				<select name="selectname_PJNoCXEE" id="select_PJNoCXEE" style="width: 92px" onkeydown="enter_down();" onChange="changePJNoCXEE()">
				<option value="0" selected></option>
				<c:forEach items="${PJNo}" var="PJNo" varStatus="vs">
                <option value="${PJNo.PJNo}" >${PJNo.PJNo}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_tempPJNo" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<!-- <input type="text" id="serach_tempProjectNoCT"  style="width: 150px;height:20px;" maxlength="200" onkeydown="enter_downCT();"/> -->
				<select name="selectname_tempPJNoCXEE" id="sel_tempPJNoCXEE" style="width: 128px" onkeydown="enter_downCT();" onChange="changeTempPJNoCXEE()">
				<option value="0" selected></option>
				<c:forEach items="${TempPJNo}" var="TempPJNo" varStatus="vs">
                <option value="${TempPJNo.tempPJNo}" >${TempPJNo.tempPJNo}</option>
                </c:forEach>
				</select>
			</td> 
			<!-- <th style="padding-left:25px;">
					<label id=label_tempProjectNo style="color:black"></label>
				</th>
				<td style="padding-left:25px;vertical-align: middle;">
					<input type="text" id="serach_tempProjectNo"  style="width: 150px;height:20px;" maxlength="200" onkeydown="enter_down();"/>
				</td>  -->
				
			<th style="padding-left:25px;">
				<label id="label_manageCode" style="color:black"></label>
			</th>
			<td style="padding-left:20px;vertical-align: middle;">
				<input type="text" id="serach_transferNoCXEE"  style="width: 132px;height:20px;" maxlength="200" onkeydown="enter_down();"/>
			</td>	
			<th style="padding-left:25px;">
				<label id="label_belong" style="color:black"></label>
			</th>
			<td style="padding-left:47px;vertical-align: middle;">
					<select name="selectname_belong" id="select_belong"  style="width: 80px" onkeydown="enter_down();" onChange="changeBelong();" >
					<option value="0" selected></option>
					<option value="1" >CT</option>
					<option value="2" >CHI</option>
					</select>				
			</td>
			</tr>
		</table>
		<table style="float:left;">
			<tr style="height:30px;">
				
				
				<th style="padding-left:25px;">
					<label id="label_task" style="color:black"></label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
					<select name="selectname_category" id="select_categoryCXEE"  style="width: 75px" onkeydown="enter_down();" >
					<option value="" selected></option>
					<c:forEach items="${category}" var="category" varStatus="ca">
                	<option value="${category.category}" >${category.category}</option>
                	</c:forEach>
					</select>
				</td>
				
				<th style="padding-left:25px;">
					<label id="label_projectClientName" style="color:black"></label>
				</th>
				<td style="padding-left:13px;vertical-align: middle;">
					<select name="selectname_projectClientName" id="select_projectClientNameCXEE"  style="width: 83px;height:20px" onkeydown="enter_down();" >
					<option value="" selected></option>
					<c:forEach items="${projectClientName}" var="pcName" varStatus="pcN">
                	<option value="${pcName.projectClientName}" >${pcName.projectClientName}</option>
                	</c:forEach>
					</select>
				</td>
				
				<th style="padding-left:25px;">
					<label id="label_maker" style="color:black"></label>
				</th>
				<td style="padding-left:12px;vertical-align: middle;">
					<select name="selectname_maker" id="select_carMakerCXEE"  style="width: 146px" onkeydown="enter_down();" >
					<option value="" selected></option>
					<c:forEach items="${projectCarMaker}" var="maker" varStatus="mk">
                	<option value="${maker.carMaker}" >${maker.carMaker}</option>
                	</c:forEach>
					</select>
				</td>
			<th style="padding-left:25px;">
				<label id="lb_function"></label>
			</th>
			<td style="padding-left:28px;">
				<select name="selectname_function" id="select_projectFunctionCXEE"  style="width: 150px" onkeydown="enter_down();" >
				<option value="" selected></option>
				<c:forEach items="${projectFunction}" var="pcFunction" varStatus="pcF">
                <option value="${pcFunction.function}" >${pcFunction.function}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:26px;">
				<label id="label_PJName" style="color:black"></label>
			</th>
			<td style="padding-left:45px;vertical-align: middle;">
				<!-- <input type="text" id="serach_projectName"  style="width: 175px;height:20px;" maxlength="200" onkeydown="enter_down();"/> -->
				<select name="selectname_PJNameCXEE" id="select_PJNameCXEE"  style="width: 185px" onkeydown="enter_downCT();" >
				<option value="" selected></option>
					<c:forEach items="${PJName}" var="projectName" varStatus="vs">
	               		<option value="${projectName.PJNameCT}" >${projectName.PJNameCT}</option>
	            	</c:forEach>
				</select>
			</td>
				<!-- <th style="padding-left:26px;">
					<label id=label_pFPJName style="color:black">PJ名</label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
					 <input type="text" id="serach_projectName"  style="width: 175px;height:20px;" maxlength="200" onkeydown="enter_down();"/>
				</td> -->
				
			</tr>
		</table>
	</div>
 </div>
 <div style="width:100%;height:60px;" id="CT_condition">
 	<div style="width:100%;height:23px;">
 	<table style="float:left;">
	 	<tr style="height:30px;">
	 	
	 		<th style="padding-left:25px;">
				<label id="label_staffNameCT" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<input type="text" id="serach_staffNameCT"  style="width: 100px;height:20px;" maxlength="50" onkeydown="enter_downCT();"/>
			</td>
			<th style="padding-left:25px;">
				<label id="label_positionCT" style="color:black"></label>
			</th>
			<td style="padding-left:10px;">
				
				<select name="selectname_positionCT" id="select_positionCT" style="width: 98px" onkeydown="enter_downCT();">
				<option value="" selected></option>
				<c:forEach items="${staffpositionCT}" var="position" varStatus="vs">
                    	<option value="${position.position}" >${position.position}</option>
                </c:forEach>
				</select> 
			</td>
			<th style="padding-left:25px;">
				<label id="label_departmentCT" style="color:black"></label>
			</th>
			<td style="padding-left:10px;">
				<select name="selectname_departmentCT" id="select_departmentCT" style="width: 150px" onChange="changeDepartmentListCT()" onkeydown="enter_downCT();">
				<option value="" selected></option>
				<c:forEach items="${departListCT}" var="departList" varStatus="vs">
				<option value="${departList.departmentID}" >${departList.department}</option>
				</c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_branchCT" style="color:black"></label>
			</th>
			<td style="padding-left:25px;">
				<select name="selectname_branchCT" id="select_branchCT"  style="width: 150px" onkeydown="enter_downCT();" >
				<option value="" selected></option>
				<c:forEach items="${branchSelectCT}" var="branchSelect" varStatus="bs">
                <option value="${branchSelect.branchID}" >${branchSelect.branch}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
					<label id="label_sortCT" style="color:black"></label>
			</th>
			<td style="padding-left:23px;vertical-align: middle;">
				<select name="selectname_sortCT" id="select_sortCT" style="width: 80px" onkeydown="enter_downCT();" >
				<option value="" selected></option>
				<c:forEach items="${staffSortCT}" var="staffSort" varStatus="bs">
                <option value="${staffSort.sort}" >${staffSort.sort}</option>
                </c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<table style="float:right;">
		<tr style="height:30px;">
			<td>
	            <c:if test="${QueryRoleFlag == '1'}">
    			<input id="btn_queryCT" type="button"   style="width: 50px" onclick = "searchClickCT()" value="检索"/>
	            </c:if>
			</td>
			<td>
	            <c:if test="${DownloadRoleFlag == '1'}">
	            <c:if test="${SpecialRole1Flag == '1'}">
    			<input id="btn_downloadCT" type="button"   style="width: 50px" onclick = "downloadClick()" disabled value="D/L"/>
	            </c:if>
	            </c:if>
			</td>
		</tr>
	</table>
	</div>
	<div style="width:100%;height:23px;">
	
	<table style="float:left;">
			<tr style="height:30px;">
				<th style="padding-left:25px;">
				<label id="label_jobCategoryCT" style="color:black"></label>
			</th>
			<td style="padding-left:10px;">
				<select name="selectname_jobCategoryCT" id="select_jobCategoryCT" style="width: 100px" onkeydown="enter_downCT();">
				<option value="" selected></option>
				<c:forEach items="${jobCategoryCT}" var="jobCategory" varStatus="jc">
                <option value="${jobCategory.jobCategory}" >${jobCategory.jobCategory}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
					<label id="label_PJNoCT" style="color:black"></label>
			</th>
			<td style="padding-left:5px;vertical-align: middle;">
					<!-- <input type="text" id="serach_projectNo"  style="width: 126px;height:20px;" maxlength="200" onkeydown="enter_down();"/> -->
				<select name="selectname_PJNoCT" id="select_PJNoCT" style="width: 93px" onkeydown="enter_downCT();" onChange="changePJNoCT()">
				<option value="0" selected></option>
				<c:forEach items="${PJNo}" var="PJNo" varStatus="vs">
                <option value="${PJNo.PJNo}" >${PJNo.PJNo}</option>
                </c:forEach>
				</select>
			</td>
			
			<th style="padding-left:25px;">
				<label id="label_tempPJNoCT" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<!-- <input type="text" id="serach_tempProjectNoCT"  style="width: 150px;height:20px;" maxlength="200" onkeydown="enter_downCT();"/> -->
				<select name="selectname_tempPJNoCT" id="sel_tempPJNoCT" style="width: 118px" onkeydown="enter_downCT();" onChange="changeTempPJNoCT()">
				<option value="0" selected></option>
				<c:forEach items="${TempPJNo}" var="TempPJNo" varStatus="vs">
                <c:if test="${TempPJNo.tempPJNo!=''}"><option value="${TempPJNo.tempPJNo}" >${TempPJNo.tempPJNo}</option></c:if>
                </c:forEach>
				</select>
			</td> 
			<th style="padding-left:26px;">
				<label id="label_PJNameCT" style="color:black">PJ名</label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<!-- <input type="text" id="serach_projectName"  style="width: 175px;height:20px;" maxlength="200" onkeydown="enter_down();"/> -->
				<select name="selectname_PJNameCT" id="sel_PJNameCT"  style="width: 185px" onkeydown="enter_downCT();" >
				<option value="" selected></option>
					<c:forEach items="${PJName}" var="projectName" varStatus="vs">
	               		<option value="${projectName.PJNameCT}" >${projectName.PJNameCT}</option>
	            	</c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_belongCT" style="color:black"></label>
			</th>
			<td style="padding-left:46px;vertical-align: middle;">
					<select name="selectname_belongCT" id="select_belongCT"  style="width: 80px" onkeydown="enter_downCT();" onChange="changeBelongCT();" >
					<option value="0" selected></option>
					<option value="1" >CT</option>
					<option value="2" >CHI</option>
					</select>				
			</td>
			</tr>
		</table>
		<table style="float:left;">
			<tr style="height:30px;">
				<th style="padding-left:25px;">
					<label id=label_taskCT style="color:black"></label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
					<select name="selectname_categoryCT" id="select_categoryCT"  style="width: 75px" onkeydown="enter_downCT();" >
					<option value="" selected></option>
					<c:forEach items="${categoryCT}" var="category" varStatus="ca">
                	<option value="${category.category}" >${category.category}</option>
                	</c:forEach>
					</select>
				</td>
				<th style="padding-left:25px;">
					<label id=label_makerCT style="color:black"></label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
					<select name="selectname_makerCT" id="select_carMakerCT"  style="width: 80px" onkeydown="enter_downCT();" >
					<option value="" selected></option>
					<c:forEach items="${projectCarMakerCT}" var="maker" varStatus="mk">
                	<option value="${maker.carMaker}" >${maker.carMaker}</option>
                	</c:forEach>
					</select>
				</td>
				<th style="padding-left:25px;">
					<label id="lb_functionCT"></label>
				</th>
				<td style="padding-left:10px;">
					<select name="selectname_functionCT" id="select_projectFunctionCT"  style="width: 115px" onkeydown="enter_downCT();" >
					<option value="" selected></option>
					<c:forEach items="${projectFunctionCT}" var="pcFunction" varStatus="pcF">
	                <option value="${pcFunction.function}" >${pcFunction.function}</option>
	                </c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table style="float:right">
		<tr>
		<td>
   			<input id="cleanCT" type="button"   style="width: 150px;height:25px;color:black" onclick = "cleanClickCT()"  value="清除搜索条件"/>
		</td>
		</tr>
		</table>
	</div>
 </div>
 <div style="width:100%;height:60px;" id="All_condition">
 	<div style="width:100%;height:23px;">
 	<table style="float:left;">
	 	<tr style="height:30px;">
			<th style="padding-left:25px;">
					<label id="label_sortAll" style="color:black"></label>
			</th>
			<td style="padding-left:23px;vertical-align: middle;">
				<select name="selectname_sortCT" id="select_sortAll" style="width: 80px" onkeydown="enter_downAll();" >
				<option value="" selected></option>
				<c:forEach items="${staffSortCT}" var="staffSort" varStatus="bs">
                <option value="${staffSort.sort}" >${staffSort.sort}</option>
                </c:forEach>
				</select>
			</td>
			<th style="padding-left:25px;">
					<label id="label_PJNoAll" style="color:black"></label>
			</th>
			<td style="padding-left:5px;vertical-align: middle;">
					<!-- <input type="text" id="serach_projectNo"  style="width: 126px;height:20px;" maxlength="200" onkeydown="enter_down();"/> -->
				<select name="selectname_PJNoAll" id="select_PJNoAll" style="width: 95px" onkeydown="enter_downAll();" onChange="changePJNoAll()">
				<option value="0" selected></option>
				<c:forEach items="${PJNo}" var="PJNo" varStatus="vs">
                <option value="${PJNo.PJNo}" >${PJNo.PJNo}</option>
                </c:forEach>
				</select>
			</td>
			
			<th style="padding-left:25px;">
				<label id="label_tempPJNoAll" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<!-- <input type="text" id="serach_tempProjectNoCT"  style="width: 150px;height:20px;" maxlength="200" onkeydown="enter_downCT();"/> -->
				<select name="selectname_tempPJNoAll" id="sel_tempPJNoAll" style="width: 118px" onkeydown="enter_downAll();" onChange="changeTempPJNoAll()">
				<option value="0" selected></option>
				<c:forEach items="${TempPJNo}" var="TempPJNo" varStatus="vs">
                <option value="${TempPJNo.tempPJNo}" >${TempPJNo.tempPJNo}</option>
                </c:forEach>
				</select>
			</td> 
			<th style="padding-left:26px;">
				<label id="label_PJNameAll" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<!-- <input type="text" id="serach_projectName"  style="width: 175px;height:20px;" maxlength="200" onkeydown="enter_down();"/> -->
			<select name="selectname_PJNameAll" id="sel_PJNameAll"  style="width: 185px" onkeydown="enter_downAll();" >
			<option value="" selected></option>
			<c:forEach items="${PJName}" var="projectName" varStatus="vs">
               <option value="${projectName.PJNameCT}" >${projectName.PJNameCT}</option>
               </c:forEach>
			</select>
			</td>
			<th style="padding-left:25px;">
				<label id="label_taskAll" style="color:black"></label>
			</th>
			<td style="padding-left:10px;vertical-align: middle;">
				<select name="selectname_categoryAll" id="select_categoryAll"  style="width: 75px" onkeydown="enter_downAll();" >
				<option value="" selected></option>
				<c:forEach items="${categoryCT}" var="category" varStatus="ca">
               	<option value="${category.category}" >${category.category}</option>
               	</c:forEach>
				</select>
			</td>
			
		 </tr>
	</table>
	<table style="float:right;">
			<tr style="height:30px;"> 
				<td>
		            <c:if test="${QueryRoleFlag == '1'}">
	    			<input id="btn_queryAll" type="button"   style="width: 50px" onclick = "searchClickAll()" value="检索"/>
		            </c:if>
				</td>
				<td>
		            <c:if test="${DownloadRoleFlag == '1'}">
		            <c:if test="${SpecialRole1Flag == '1'}">
	    			<input id="btn_downloadAll" type="button"   style="width: 50px" onclick = "downloadClick()" disabled value="D/L"/>
		            </c:if>
		            </c:if>
				</td>
			</tr>
	</table>
	</div>
	<div style="width:100%;height:23px;">
	
	<!-- <table style="float:left;">
			<tr style="height:30px;">
			</tr>
		</table> -->
		<table style="float:left;">
			<tr style="height:30px;">
				
				<th style="padding-left:25px;">
					<label id="label_makerAll" style="color:black"></label>
				</th>
				<td style="padding-left:30px;vertical-align: middle;">
					<select name="selectname_makerAll" id="select_carMakerAll"  style="width: 98px" onkeydown="enter_downAll();" >
					<option value="" selected></option>
					<c:forEach items="${projectCarMakerCT}" var="maker" varStatus="mk">
                	<option value="${maker.carMaker}" >${maker.carMaker}</option>
                	</c:forEach>
					</select>
				</td>
				<th style="padding-left:25px;">
					<label id="label_manageCodeAll" style="color:black"></label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
					<input type="text" id="serach_transferNoAll"  style="width: 76px;height:20px;" maxlength="200" onkeydown="enter_downAll();"/>
				</td>
				<th style="padding-left:25px;">
					<label id="lb_functionAll"></label>
				</th>
				<td style="padding-left:10px;">
					<select name="selectname_functionAll" id="select_projectFunctionAll"  style="width: 140px" onkeydown="enter_downAll();" >
					<option value="" selected></option>
					<c:forEach items="${projectFunctionCT}" var="pcFunction" varStatus="pcF">
	                <option value="${pcFunction.function}" >${pcFunction.function}</option>
	                </c:forEach>
					</select>
				</td>
				<th style="padding-left:25px;">
					<label id="label_belongAll" style="color:black"></label>
				</th>
				<td style="padding-left:10px;vertical-align: middle;">
						<select name="selectname_belongAll" id="select_belongAll"  style="width: 80px" onkeydown="enter_downAll();" onChange="changeBelongAll();" >
						<option value="0" selected></option>
						<option value="1" >CT</option>
						<option value="2" >CHI</option>
						</select>				
				</td>
			</tr>
		</table>
	</div>
 </div>
<div id="gridAll" style="width:50%; display:none" ></div>
<div id="gridCXEE" style="width:50%; display:none" ></div>
<div id="gridCT" style="width:50%; display:none" ></div>
<p style="height: 3px; color:#000000"></p>
<div id="sumGridTable" style="width:50%; display:none"></div>
<div id="sumGridTableCXEE" style="width:50%; display:none"></div>
<div id="sumGridTableCT" style="width:50%; display:none"></div>
</body>
</html>