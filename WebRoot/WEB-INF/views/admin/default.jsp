<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
<link type="text/css" rel="stylesheet" href="css/main.css"/>
<style>
	body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td{margin:0;padding:0;}
	h3{color:#555;font-weight:normal;margin:10px 0px; }
	body{text-align:center;margin-top:5px;}
	h1,h3{font-family: microsoft yahei}
	a{color:#1F497D;margin:0px 3px;font-size:10pt;text-decoration:underline;}
	.default_topButton{float:left;width:100%;height:10%;text-align:left;margin:5px}
	.default_right{float:right;width:50%;height:100%;text-align:left;}
	.default_left{float:left;width:49%;height:100%;text-align:left;border-right: 1px solid #ccc;}
	.default_title{margin-left:10px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 24px;height:70px;line-height: 36px;color: #666666;font-weight: bold;}
	.default_mark{margin-left:10px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 24px;height:70px;line-height: 36px;color: #2e75b6;font-weight: bold;}
	.default_label{margin-left:10px;font-family:PMingLiu, Helvetica, sans-serif;font-size:16px;height:40px;font-weight:bold;}
	.ehdel_upload_show input {float: left;margin-left: 10px;}
	.ehdel_upload {float: left;	margin-top: -20px;*margin-top: -40px;filter: alpha(opacity = 0);-moz-opacity: 0;opacity: 0;	width: 450px}	
</style>

<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/lhgdialog-4.20/lhgdialog.min.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/jsadsense/jsadsense.js"></script>
<script type="text/javascript" src="js/js_user/com.js"></script>
<script type="text/javascript" src="js/nicEdit/nicEdit.js"></script>
<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/cookie/cookie.js"></script>
<script>
	var vdlg;
	var contentIndex = 0;
	var contentIndexCT = 0;
	var editCount = 1;
	var editCountCT = 1;
	var changeCommonFileCount = 1;
	var changeCommonFileCountCT = 1;
	var changeReportFileCount = 1;
	var changeReportFileCountCT = 1;
	var noticeEditor;
	var noticeEditor_CT;
	var btn_delete;
	var lbl_select_file;
	var btn_select;
	var string_loading;
	var string_download;
	var commonFilePath;
	var reportFilePath;
	var commonFilePathCT;
	var reportFilePathCT;
	var fileCount = 1;
	var fileCountCT = 1;
	var commonRowNum = 1;
	var reportRowNum = 1;
	var commonRowNumCT = 1;
	var reportRowNumCT = 1;	
	var language = getCookieValue("language");
	var uploadType;
	var E0096 = "";
	var contentLst;
	var contentLstCT;
	
	$(document).ready(function(){
		if (language!="CN" && language!="JP" && language!="EN"){
			language = "CN";
		}
		loadProperties(language,setPageByLanguage);
		$("#div_notice").show();
		$("#div_notice_CT").show();
		$("#div_fileList").hide();
		$("#div_fileList_CT").hide();
		bkLib.onDomLoaded(function() {
			 contentLst = eval('${ContentList}');
	    	$("#div_content").css("display","none");
	        $("#btn_save").css("display","none");
	        $("#div_look").css("display","");
	        $("#div_content_CT").css("display","none");
	        $("#btn_save_CT").css("display","none");
	        $("#div_look_CT").css("display","");
	        $("P[class=MsoNormal]").css("background-color","#daeef3");
	        noticeEditor = new nicEditor({fullPanel : true}).panelInstance('wl_content');
	        noticeEditor_CT = new nicEditor({fullPanel : true}).panelInstance('wl_content_CT');
	        $("#btn_add").css("display","");
	        $("#btn_add_CT").css("display","");
	        
	        for(var i = 0; i < contentLst.length; i++){
	        	obj = contentLst[i];
	        	addTR_look(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	        	addTR_edit(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	        }
	        for (var i = 0; i < contentLstCT.length; i++){
	        	obj = contentLstCT[i];
	        	addTR_look_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	        	addTR_edit_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	        }
	    });

	});
	function addTR_look(index, keyCode, contentText, contentLink,contentType){
		var tdID = "td_file" + index +"_look";
    	$str='';
    	$str+='<tr>';
    	$str+="<td id='"+tdID+"' keycode='"+keyCode+"' style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
    	if(contentType == "2"){
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"2\",\"cxee\") >"+ commonRowNum + ". " + contentLink+"</a>";
    	} else {
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"3\",\"cxee\") >"+ reportRowNum + ". " + contentLink+"</a>";
    	}
    	$str+='</td>';
    	$str+='</tr>';
    	if(contentType == "2"){
    		$("#tab_fileList_common").append($str);
    	} else {
    		$("#tab_fileList_report").append($str);
    	}
    }
	function addTR_look_CT(index, keyCode, contentText, contentLink,contentType){
		var tdID = "td_file_CT" + index +"_look";
    	$str='';
    	$str+='<tr>';
    	$str+="<td id='"+tdID+"' keycode='"+keyCode+"' style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
    	if(contentType == "2"){
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"2\",\"ct\") >"+ commonRowNumCT + ". " + contentLink+"</a>";
    	} else {
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"3\",\"ct\") >"+ reportRowNumCT + ". " + contentLink+"</a>";
    	}
    	$str+='</td>';
    	$str+='</tr>';
    	if(contentType == "2"){
    		$("#tab_fileList_common_CT").append($str);
    	} else {
    		$("#tab_fileList_report_CT").append($str);
    	}
    }
    function addTR_edit(index, keyCode, contentText, contentLink,contentType){
    	var tdID = "td_file" + index;
    	var textInputID = "txt_text" + index;
    	var linkInputID = "txt_link" + index;
    	$str='';
    	$str+='<tr>';
    	$str+="<td id='"+tdID+"' keycode='"+keyCode+"' style='padding-left:10px;width:500px;height:30px;vertical-align:top;'>";
    	if(contentType == "2"){
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"2\",\"cxee\") >"+ commonRowNum + ". " + contentLink+"</a>";
    		commonRowNum++;
    	} else {
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"3\",\"cxee\") >"+ reportRowNum + ". " + contentLink+"</a>";
    		reportRowNum++;
    	}
    	$str+="<input type='button' style='padding-right:5px;' onclick='getDel(\""+tdID+"\",\""+contentType+"\",\""+contentText+"\",\"cxee\")' value='"+btn_delete+"'>";
    	$str+='</td>';
    	$str+='</tr>';
    	if(contentType == "2"){
    		$("#tab_fileList_common_edit").append($str);
    	} else {
    		$("#tab_fileList_report_edit").append($str);
    	}
    	contentIndex++;
    }
    function addTR_edit_CT(index, keyCode, contentText, contentLink,contentType){
    	var tdID = "td_file_CT" + index;
    	var textInputID = "txt_text_CT" + index;
    	var linkInputID = "txt_link_CT" + index;
    	$str='';
    	$str+='<tr>';
    	$str+="<td id='"+tdID+"' keycode='"+keyCode+"' style='padding-left:10px;width:500px;height:30px;vertical-align:top;'>";
    	if(contentType == "2"){
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"2\",\"ct\") >"+ commonRowNumCT + ". " + contentLink+"</a>";
    		commonRowNumCT++;
    	} else {
    		$str+="<a href=\"javascript:void(0)\" onclick=openFile(\"" + contentLink +"\",\"3\",\"ct\") >" + reportRowNumCT + ". " + contentLink+"</a>";
    		reportRowNumCT++;
    	}
    	$str+="<input type='button' style='padding-right:5px;' onclick='getDel(\""+tdID+"\",\""+contentType+"\",\""+contentText+"\",\"ct\")' value='"+btn_delete+"'>";
    	$str+='</td>';
    	$str+='</tr>';
    	if(contentType == "2"){
    		$("#tab_fileList_common_edit_CT").append($str);
    	} else {
    		$("#tab_fileList_report_edit_CT").append($str);
    	}
    	contentIndexCT++;
    }
    function addCommon(){
    	var blankFlag = false;
    	$("#tab_fileList_common_edit :text").each(function(){
			if ($(this).val() != ""){
			} else {
				alert(E0096);
				blankFlag = true;
			}
		});
    	if (blankFlag){
    		return false;
    	}
    	$str='';
    	$str+='<tr>';
    	$str+="<td style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
		$str+='<div class="ehdel_upload_show">';
		$str+='<p align="left" style="padding-left:10px; padding-bottom:0px; border-bottom:0px; margin-bottom:0px">'+ lbl_select_file +'</p><input id="ehdel_upload_text_'+fileCount+'" type="text" name="txt" style="width: 360px;" />';
		$str+='<input id="ehdel_upload_btn_'+fileCount+'" style="width:50px; height:22px" type="button" value="'+ btn_select+'">';
		$str+='</div>';
		$str+='<input id="fileInfo_'+fileCount+'" type="file" name="file" onchange="ehdel_upload_text_'+fileCount+'.value=this.value" class="ehdel_upload" />';
    	$str+='</td>';
    	$str+='</tr>';
    	$("#tab_fileList_common_edit").append($str);
    	fileCount++;
    }
    function addCommonCT(){
    	var blankFlag = false;
    	$("#tab_fileList_common_edit_CT :text").each(function(){
			if ($(this).val() != ""){
			} else {
				alert(E0096);
				blankFlag = true;
			}
		});
    	if (blankFlag){
    		return false;
    	}
    	$str='';
    	$str+='<tr>';
    	$str+="<td style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
		$str+='<div class="ehdel_upload_show">';
		$str+='<p align="left" style="padding-left:10px; padding-bottom:0px; border-bottom:0px; margin-bottom:0px">'+ lbl_select_file +'</p><input id="ehdel_upload_text_CT_'+fileCountCT+'" type="text" name="txt" style="width: 360px;" />';
		$str+='<input id="ehdel_upload_btn_CT_'+fileCountCT+'" style="width:50px; height:22px" type="button" value="'+ btn_select+'">';
		$str+='</div>';
		$str+='<input id="fileInfo_CT_'+fileCountCT+'" type="file" name="file" onchange="ehdel_upload_text_CT_'+fileCountCT+'.value=this.value" class="ehdel_upload" />';
    	$str+='</td>';
    	$str+='</tr>';
    	$("#tab_fileList_common_edit_CT").append($str);
    	fileCountCT++;
    }
    function addReport(){
    	var blankFlag = false;
    	$("#tab_fileList_report_edit :text").each(function(){
			if ($(this).val() != ""){
			} else {
				alert(E0096);
				blankFlag = true;
			}
		});
    	if (blankFlag){
    		return false;
    	}
    	$str='';
    	$str+='<tr>';
    	$str+="<td style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
		$str+='<div class="ehdel_upload_show">';
		$str+='<p align="left" style="padding-left:10px; padding-bottom:0px; border-bottom:0px; margin-bottom:0px">'+ lbl_select_file +'</p><input id="ehdel_upload_text_'+fileCount+'" type="text" name="txt" style="width: 360px;" />';
		$str+='<input id="ehdel_upload_btn_'+fileCount+'" style="width:50px; height:22px" type="button" value="'+ btn_select+'">';
		$str+='</div>';
		$str+='<input id="fileInfo_'+fileCount+'" type="file" name="file" onchange="ehdel_upload_text_'+fileCount+'.value=this.value" class="ehdel_upload" />';
    	$str+='</td>';
    	$str+='</tr>';
    	$("#tab_fileList_report_edit").append($str);
    	fileCount++;
    }
    function addReportCT(){
    	var blankFlag = false;
    	$("#tab_fileList_report_edit_CT :text").each(function(){
			if ($(this).val() != ""){
			} else {
				alert(E0096);
				blankFlag = true;
			}
		});
    	if (blankFlag){
    		return false;
    	}
    	$str='';
    	$str+='<tr>';
    	$str+="<td style='padding-left:10px;height:30px;vertical-align:top;text-align:left;'>";
		$str+='<div class="ehdel_upload_show">';
		$str+='<p align="left" style="padding-left:10px; padding-bottom:0px; border-bottom:0px; margin-bottom:0px">'+ lbl_select_file +'</p><input id="ehdel_upload_text_CT_'+fileCountCT+'" type="text" name="txt" style="width: 360px;" />';
		$str+='<input id="ehdel_upload_btn_CT_'+fileCountCT+'" style="width:50px; height:22px" type="button" value="'+ btn_select+'">';
		$str+='</div>';
		$str+='<input id="fileInfo_CT_'+fileCountCT+'" type="file" name="file" onchange="ehdel_upload_text_CT_'+fileCountCT+'.value=this.value" class="ehdel_upload" />';
    	$str+='</td>';
    	$str+='</tr>';
    	$("#tab_fileList_report_edit_CT").append($str);
    	fileCountCT++;
    }
	function edit(){
		if (editCount % 2 == 0){
			$("#div_content").css("display","none");
	        $("#btn_save").css("display","none");
	        $("#div_look").css("display","");
	        $("#btn_edit").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#div_content").css("display","");
	        $("#btn_save").css("display","");
	        $("#div_look").css("display","none");
	        $("#btn_edit").html($.i18n.prop('default_string_cancelBtn'));
		}
		editCount++;
	}
	function editCT(){
		if (editCountCT % 2 == 0){
			$("#div_content_CT").css("display","none");
	        $("#btn_save_CT").css("display","none");
	        $("#div_look_CT").css("display","");
	        $("#btn_edit_CT").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#div_content_CT").css("display","");
	        $("#btn_save_CT").css("display","");
	        $("#div_look_CT").css("display","none");
	        $("#btn_edit_CT").html($.i18n.prop('default_string_cancelBtn'));
		}
		editCountCT++;
	}
	function changeToDocs(){
		$("#div_notice").hide();
		$("#div_notice_CT").hide();
		$("#div_fileList").show();
		$("#div_fileList_CT").show();
		//如果处于编辑状态，改回初期状态。
		if ($("#btn_common_edit").html() == $.i18n.prop('default_string_cancelBtn')){
			changeCommonFile();
		}
		if ($("#btn_common_edit_CT").html() == $.i18n.prop('default_string_cancelBtn')){
			changeCommonFileCT();
		}
		if ($("#btn_report_edit").html() == $.i18n.prop('default_string_cancelBtn')){
			changeReportFile();
		}
		if ($("#btn_report_edit_CT").html() == $.i18n.prop('default_string_cancelBtn')){
			changeReportFileCT();
		}
	}
	function changeToNotice(){
		$("#div_notice").show();
		$("#div_notice_CT").show();
		$("#div_fileList").hide();
		$("#div_fileList_CT").hide();
	}
	function changeCommonFile(){
		if ($("#btn_common_edit").html() == $.i18n.prop('default_string_cancelBtn')){
			$("#tab_fileList_common_edit :file").each(function(){
				$(this).parent().parent().remove();
			})
		}
		if (changeCommonFileCount % 2 == 0){
			$("#tab_fileList_common").show();
			$("#tab_fileList_common_edit").hide();
			$("#btn_common_add").hide();
			$("#btn_common_upload").hide();
			$("#btn_common_edit").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#tab_fileList_common").hide();
			$("#tab_fileList_common_edit").show();
			$("#btn_common_add").show();
			$("#btn_common_upload").show();
			$("#btn_common_edit").html($.i18n.prop('default_string_cancelBtn'));
		}
		changeCommonFileCount++;
	}
	function changeCommonFileCT(){
		if ($("#btn_common_edit_CT").html() == $.i18n.prop('default_string_cancelBtn')){
			$("#tab_fileList_common_edit_CT :file").each(function(){
				$(this).parent().parent().remove();
			})
		}
		if (changeCommonFileCountCT % 2 == 0){
			$("#tab_fileList_common_CT").show();
			$("#tab_fileList_common_edit_CT").hide();
			$("#btn_common_add_CT").hide();
			$("#btn_common_upload_CT").hide();
			$("#btn_common_edit_CT").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#tab_fileList_common_CT").hide();
			$("#tab_fileList_common_edit_CT").show();
			$("#btn_common_add_CT").show();
			$("#btn_common_upload_CT").show();
			$("#btn_common_edit_CT").html($.i18n.prop('default_string_cancelBtn'));
		}
		changeCommonFileCountCT++;
	}
	function changeReportFile(){
		if ($("#btn_report_edit").html() == $.i18n.prop('default_string_cancelBtn')){
			$("#tab_fileList_report_edit :file").each(function(){
				$(this).parent().parent().remove();
			})
		}
		if (changeReportFileCount % 2 == 0){
			$("#tab_fileList_report").show();
			$("#tab_fileList_report_edit").hide();
			$("#btn_report_add").hide();
			$("#btn_report_upload").hide();
			$("#btn_report_edit").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#tab_fileList_report").hide();
			$("#tab_fileList_report_edit").show();
			$("#btn_report_add").show();
			$("#btn_report_upload").show();
			$("#btn_report_edit").html($.i18n.prop('default_string_cancelBtn'));
		}
		changeReportFileCount++;
	}
	function changeReportFileCT(){
		if ($("#btn_report_edit_CT").html() == $.i18n.prop('default_string_cancelBtn')){
			$("#tab_fileList_report_edit_CT :file").each(function(){
				$(this).parent().parent().remove();
			})
		}
		if (changeReportFileCountCT % 2 == 0){
			$("#tab_fileList_report_CT").show();
			$("#tab_fileList_report_edit_CT").hide();
			$("#btn_report_add_CT").hide();
			$("#btn_report_upload_CT").hide();
			$("#btn_report_edit_CT").html($.i18n.prop('default_string_editBtn'));
		} else {
			$("#tab_fileList_report_CT").hide();
			$("#tab_fileList_report_edit_CT").show();
			$("#btn_report_add_CT").show();
			$("#btn_report_upload_CT").show();
			$("#btn_report_edit_CT").html($.i18n.prop('default_string_cancelBtn'));
		}
		changeReportFileCountCT++;
	}
	function saveContent(index){
    	var strKeyCode = "CI999999999";
    	var strType = 1;
    	var strContent = "";
    	var strLink = "";
    	var tdID = "";
    	
    	if (index == "cxee"){
    		strContent = noticeEditor.instanceById('wl_content').getContent();
    	} else if (index == "ct") {
    		strContent = noticeEditor_CT.instanceById('wl_content_CT').getContent();
    	}    	

    	$.ajax({
			type: "post",
			url: "saveContent.do",
			data: {"keyCode": strKeyCode,"type": strType,"content": strContent,"link": strLink,"index":index},
            dataType: "text",
			success: function(data) {
				var lstStr = data.split('@@');
				alert($.i18n.prop(lstStr[0]));
				if (index == "cxee"){
					$('#div_look').html(lstStr[2]);
					$('#div_look P[class=MsoNormal]').css("background-color","#daeef3");
					edit();
				} else if (index == "ct"){
					$('#div_look_CT').html(lstStr[2]);
					$('#div_look_CT P[class=MsoNormal]').css("background-color","#daeef3");
					editCT();
				}
   			}
   		});
    }
	
	function getDel(tdObj,type,contentText,index){
    	var strKeyCode = $("#"+tdObj).attr("keycode");
    	if(strKeyCode == ""){
      		$("#"+tdObj).parent().remove();
    	} else {
    		if(confirm($.i18n.prop("E0026"))){
		   		$.ajax({
	                type: "post",
	                url: "deleteContent.do",
	                data: {"keyCode": $("#"+tdObj).attr("keycode"),"type":type,"fileName":contentText,"index":index},
	                dataType: "json",
	                success: function(data) {
	              		alert($.i18n.prop(data.result));
	              		$("#"+tdObj).parent().remove();
	              		$("#"+tdObj+"_look").parent().remove();
	              		
	              	
	              		if (index == "cxee"){
	              			 contentLst = eval(data.contentlst);
	    	                $("#tab_fileList_common").empty();
	    	                $("#tab_fileList_common_edit").empty();
	    	                $("#tab_fileList_report").empty();
	    	                $("#tab_fileList_report_edit").empty();
	    	                commonRowNum = 1;
	    	                reportRowNum = 1;
	    	                for(var i = 0; i < contentLst.length; i++){
	    	    	        	obj = contentLst[i];
	    	    	        	addTR_look(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	    	        	addTR_edit(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	    	        }
	    	                changeCommonFile();
	    	                changeReportFile();
	                    } else if (index == "ct"){
	                    	contentLstCT=eval(data.contentlst);
	                    	$("#tab_fileList_common_CT").empty();
	    	                $("#tab_fileList_common_edit_CT").empty();
	    	                $("#tab_fileList_report_CT").empty();
	    	                $("#tab_fileList_report_edit_CT").empty();
	    	                commonRowNumCT = 1;
	    	                reportRowNumCT = 1;
	    	                for(var i = 0; i < contentLstCT.length; i++){
	    	    	        	obj = contentLstCT[i];
	    	    	        	addTR_look_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	    	        	addTR_edit_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	    	        }
	    	                changeCommonFileCT();
	    	                changeReportFileCT();
	                    }
	 				}
				});
    		}
   		}
    }
	function doUpload(type,index){
		var uploadType = type;
		var formName;
		var hasFile = false;
		if (index == "cxee"){
			if (uploadType == "2") {
				formName = "commonFileForm";
				$("#tab_fileList_common_edit :text").each(function(){
					if ($(this).val() != ""){
						hasFile = true;
					}
				})
			} else if (uploadType == "3"){
				formName = "reportFileForm";
				$("#tab_fileList_report_edit :text").each(function(){
					if ($(this).val() != ""){
						hasFile = true;
					}
				})
			}
		} else if (index == "ct"){
			if (uploadType == "2") {
				formName = "commonFileFormCT";
				$("#tab_fileList_common_edit_CT :text").each(function(){
					if ($(this).val() != ""){
						hasFile = true;
					}
				})
			} else if (uploadType == "3"){
				formName = "reportFileFormCT";
				$("#tab_fileList_report_edit_CT :text").each(function(){
					if ($(this).val() != ""){
						hasFile = true;
					}
				})
			}
		}
		
		if (!hasFile){
			alert($.i18n.prop('E0086'));
			return;
		}
		var commCheck=false;
		
		if (index == "cxee"){
			if (uploadType == "2") {
				$("#tab_fileList_common_edit :text").each(function (){
					var path=$(this).val();
					var arr=path.split("\\");
					var filename=arr[arr.length-1];
					
					 //contentLst = eval('${ContentList}');
			        for (var i = 0; i < contentLst.length; i++){
			        	var obj = contentLst[i];
			        	if(obj.contentType==type && obj.contentText==filename){
			        		if(!confirm($.i18n.prop("E0027"))){
			        			commCheck=true;
			        		}
			        	}
			        }
				});
			} else if (uploadType == "3"){
				$("#tab_fileList_report_edit :text").each(function (){
					var path=$(this).val();
					var arr=path.split("\\");
					var filename=arr[arr.length-1];
					
					 //contentLst = eval('${ContentList}');
			        for (var i = 0; i < contentLst.length; i++){
			        	var obj = contentLst[i];
			        	if(obj.contentType==type && obj.contentText==filename){
			        		if(!confirm($.i18n.prop("E0027"))){
			        			commCheck=true;
			        		}
			        	}
			        }
				});
			}
		} else if (index == "ct"){
			if (uploadType == "2") {
				$("#tab_fileList_common_edit_CT :text").each(function (){
					var path=$(this).val();
					var arr=path.split("\\");
					var filename=arr[arr.length-1];
					
					// contentLstCT = eval('${ContentListCT}');
			        for (var i = 0; i < contentLstCT.length; i++){
			        	var obj = contentLstCT[i];
			        	if(obj.contentType==type && obj.contentText==filename){
			        		if(!confirm($.i18n.prop("E0027"))){
			        			commCheck=true;
			        		}
			        	}
			        }
				});
			} else if (uploadType == "3"){
				$("#tab_fileList_report_edit_CT :text").each(function (){
					var path=$(this).val();
					var arr=path.split("\\");
					var filename=arr[arr.length-1];
					
					// contentLstCT = eval('${ContentListCT}');
			        for (var i = 0; i < contentLstCT.length; i++){
			        	var obj = contentLstCT[i];
			        	if(obj.contentType==type && obj.contentText==filename){
			        		if(!confirm($.i18n.prop("E0027"))){
			        			commCheck=true;
			        		}
			        	}
			        }
				});
			}
		}
		
		if(commCheck){
			return;
		}
		$('#'+formName).ajaxForm({
	        url:'docsUpload.do',
	        type:'post',
	        dataType: 'text',
	        data:{"type":uploadType,"index":index},
	        success:function (data){
                alert($.i18n.prop('E0087'));
                 
                if (index == "cxee"){
                	 contentLst = eval(data);
	                $("#tab_fileList_common").empty();
	                $("#tab_fileList_common_edit").empty();
	                $("#tab_fileList_report").empty();
	                $("#tab_fileList_report_edit").empty();
	                commonRowNum = 1;
	                reportRowNum = 1;
	                for(var i = 0; i < contentLst.length; i++){
	    	        	obj = contentLst[i];
	    	        	addTR_look(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	        	addTR_edit(contentIndex, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	        }
	                changeCommonFile();
	                changeReportFile();
                } else if (index == "ct"){
                	contentLstCT = eval(data);
                	$("#tab_fileList_common_CT").empty();
	                $("#tab_fileList_common_edit_CT").empty();
	                $("#tab_fileList_report_CT").empty();
	                $("#tab_fileList_report_edit_CT").empty();
	                commonRowNumCT = 1;
	                reportRowNumCT = 1;
	                for(var i = 0; i < contentLstCT.length; i++){
	    	        	obj = contentLst[i];
	    	        	addTR_look_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	        	addTR_edit_CT(contentIndexCT, obj.keyCode, obj.contentText, obj.contentLink,obj.contentType);
	    	        }
	                changeCommonFileCT();
	                changeReportFileCT();
                }
	        },
	        error:function(data){
	            alert($.i18n.prop('E0088'));
	        }
	    });	
		$('#'+formName).submit();
	}
	
	function openFile(fileName,index,company){
		var filePath = "";
		if ("cxee" == company){
			if ("2" == index){
				filePath = commonFilePath;
			} else {
				filePath = reportFilePath;
			}
		} else {
			if ("2" == index){
				filePath = commonFilePathCT;
			} else {
				filePath = reportFilePathCT;
			}
		}
		var path = filePath + fileName;
		vdlg=new $.dialog({
   		    title: string_loading,
			content: '<img src="images/loading7.gif" width="100" height="100" />',
   			max: false,
   		    min: false,
   		    fixed: true,
   		    lock:true,
			width: '400px',
      		height: '300px',
   		    esc:false
   		});
		$.ajax({ 
   	        type: "post", 
   	        url: "downSmbLoad.do",
   	        dataType: "text", 
   	        data:{"path":encodeURI(path)}, 
   	        success: function (retstr) {
   	        	    vdlg.close();
   	        	    if(retstr!=""){
   	        	    	if("E0089" == retstr){
   	        	    		alert($.i18n.prop(retstr));
   	        	    	}else{
   	        	    		downloadexcel(filePath+escape(encodeURIComponent(fileName)));
   	        	    	}
   	        	    }
   	            } 
   	        });
	}
	function downloadexcel(fileName){
		var dg = new $.dialog({
			title: string_download,
			id:'downLoad',
			width: '400px',
	  		height: '300px',
			lock: true,
			content:'url:downLoadExcel.do?fileName='+fileName+'&type='+3,
			max: false,
		    min: false
		});
	}
	
	function setPageByLanguage(){
		
		document.title = $.i18n.prop('default_string_welcome');
		
		$("#default_string_CT_title").html($.i18n.prop('default_string_CT_title'));
		$("#default_string_CXEE_title").html($.i18n.prop('default_string_CXEE_title'));
		$("#default_string_mark").html($.i18n.prop('default_string_mark'));
		$("#default_string_mark_CT").html($.i18n.prop('default_string_mark'));
		$("#default_string_title_en_CT").html($.i18n.prop('default_string_title_en'));
		$("#default_string_title_en_CXEE").html($.i18n.prop('default_string_title_en'));
		
		$("#btn_notice").html($.i18n.prop('default_string_noticeBtn'));
		$("#btn_docs").html($.i18n.prop('default_string_docsBtn'));
		
		$("#btn_edit").html($.i18n.prop('default_string_editBtn'));
		$("#btn_save").html($.i18n.prop('default_string_saveBtn'));
		$("#btn_add").html($.i18n.prop('default_string_addBtn'));
		$("#btn_common_edit").html($.i18n.prop('default_string_editBtn'));
		$("#btn_common_add").html($.i18n.prop('default_string_addBtn'));
		$("#btn_common_upload").html($.i18n.prop('default_string_uploadBtn'));
		$("#lbl_common_files").html($.i18n.prop('default_string_common_files'));
		$("#btn_report_edit").html($.i18n.prop('default_string_editBtn'));
		$("#btn_report_add").html($.i18n.prop('default_string_addBtn'));
		$("#btn_report_upload").html($.i18n.prop('default_string_uploadBtn'));
		$("#lbl_report_files").html($.i18n.prop('default_string_report_files'));
		

		$("#btn_edit_CT").html($.i18n.prop('default_string_editBtn'));
		$("#btn_save_CT").html($.i18n.prop('default_string_saveBtn'));
		$("#btn_add_CT").html($.i18n.prop('default_string_addBtn'));
		$("#btn_common_edit_CT").html($.i18n.prop('default_string_editBtn'));
		$("#btn_common_add_CT").html($.i18n.prop('default_string_addBtn'));
		$("#btn_common_upload_CT").html($.i18n.prop('default_string_uploadBtn'));
		$("#lbl_common_files_CT").html($.i18n.prop('default_string_common_files'));
		$("#btn_report_edit_CT").html($.i18n.prop('default_string_editBtn'));
		$("#btn_report_add_CT").html($.i18n.prop('default_string_addBtn'));
		$("#btn_report_upload_CT").html($.i18n.prop('default_string_uploadBtn'));
		$("#lbl_report_files_CT").html($.i18n.prop('default_string_report_files'));
		
		btn_delete = $.i18n.prop('default_string_deleteBtn');
		lbl_select_file = $.i18n.prop('default_string_select_file');
		btn_select = $.i18n.prop('default_string_selectBtn');
		string_loading = $.i18n.prop('default_string_loading');
		string_download = $.i18n.prop('default_string_download');
		
		commonFilePath = $.i18n.prop('default_string_CXEE_common_file_path');
		reportFilePath = $.i18n.prop('default_string_CXEE_report_file_path');
		
		commonFilePathCT = $.i18n.prop('default_string_CT_common_file_path');
		reportFilePathCT = $.i18n.prop('default_string_CT_report_file_path');
		
		E0096 = $.i18n.prop('E0096');
	}
</script>
</head>
<body style="height: 100%;background-color:#daeef3">
<input type="hidden" id ="userRole" value = "${userRole}">
    <div class="default_topButton">
    	<button id="btn_notice"  onclick="changeToNotice()"></button>
    	<button id="btn_docs"  onclick="changeToDocs()"></button>
    </div>
    <div class="default_left">
    	<p><label class="default_mark" id="default_string_mark_CT"></label><label class="default_title" id="default_string_CT_title"></label></p>
    	<p><label class="default_title" id="default_string_title_en_CT"></label></p>
		<div id = "div_fileList_CT" style="background-color:#daeef3;min-height: 500px;">
		   	<table id="tab_docs_CT" style="margin-top:30px;width:100%;height:400px;">
		   		<tr style="width:100%;height:200px;">
		   			<td style="text-align:left;vertical-align:top;">
		   				<label class="default_label" id="lbl_common_files_CT"></label>
		   				<div style="text-align:right;width:93%">
		   					<button id="btn_common_add_CT" onclick="addCommonCT()" style="display:none;"></button>
		   					<button id="btn_common_upload_CT" onclick="doUpload('2','ct')" style="display:none;"></button>
		   					<button id="btn_common_edit_CT" onclick="changeCommonFileCT()" ></button>
		   				</div>
		   				<table id="tab_fileList_common_CT"></table>
		   				<form id="commonFileFormCT" method="post" enctype="multipart/form-data">
		   					<table id="tab_fileList_common_edit_CT" style="display:none;"></table>
		   				</form>
		   			</td>
		   		</tr>
		   		<tr style="width:100%;height:200px;">
		   			<td style="text-align:left;vertical-align:top;">
		   				<label class="default_label" id="lbl_report_files_CT"></label>
		   				<div style="text-align:right;width:93%">
		   					<button id="btn_report_add_CT" onclick="addReportCT()" style="display:none;"></button>
		   					<button id="btn_report_upload_CT" onclick="doUpload('3','ct')" style="display:none;"></button>
		   					<button id="btn_report_edit_CT" onclick="changeReportFileCT()" ></button>
		   				</div>
		   				<table id="tab_fileList_report_CT"></table>
		   				<form id="reportFileFormCT" method="post" enctype="multipart/form-data">
		   					<table id="tab_fileList_report_edit_CT" style="display:none;"></table>
		   				</form>
		   			</td>
		   		</tr>
		   	</table>
		</div>
    </div>
    <div class="default_right">
    	<p><label class="default_mark" id="default_string_mark"></label><label class="default_title" id="default_string_CXEE_title"></label></p>
    	<p><label class="default_title" id="default_string_title_en_CXEE"></label></p>
	    <div id = "div_notice" style="background-color:#daeef3;min-height: 500px;">
			<table id="tab_contentMain" style="margin-top:10px;width:100%;height:400px;">
		   		<tr style="width:100%;height:100%;">
		   			<td style="width:450px;text-align:right;vertical-align:top;">
		   				<button id="btn_edit" onclick="edit(-1)"></button>
					    <div id="div_content" style="width:450px;background-color:white;text-align:left;">
							<textarea id="wl_content" style="width:450px;" cols="50" rows="20">${ContentText}</textarea>
						</div>
						<button id="btn_save" style="display:none;" onclick="saveContent('cxee')">save</button>
						<div id="div_look" style="word-break:break-all;word-wrap:break-word;width:450px;display:none;margin-top:10px;margin-left:20px;text-align:left;vertical-align:top;overflow-x:auto;overflow-y:auto;">
							${ContentText}
						</div>
		   			</td>
		   		</tr>
		   	</table>
		</div>
		<div id = "div_fileList" style="background-color:#daeef3;min-height: 500px;">
		   	<table id="tab_docs" style="margin-top:30px;width:100%;height:400px;">
		   		<tr style="width:100%;height:200px;">
		   			<td style="text-align:left;vertical-align:top;">
		   				<label class="default_label" id="lbl_common_files"></label>
		   				<div style="text-align:right;width:93%">
		   					<button id="btn_common_add" onclick="addCommon()" style="display:none;"></button>
		   					<button id="btn_common_upload" onclick="doUpload('2','cxee')" style="display:none;"></button>
		   					<button id="btn_common_edit" onclick="changeCommonFile()" ></button>
		   				</div>
		   				<table id="tab_fileList_common"></table>
		   				<form id="commonFileForm" method="post" enctype="multipart/form-data">
		   					<table id="tab_fileList_common_edit" style="display:none;"></table>
		   				</form>
		   			</td>
		   		</tr>
		   		<tr style="width:100%;height:200px;">
		   			<td style="text-align:left;vertical-align:top;">
		   				<label class="default_label" id="lbl_report_files"></label>
		   				<div style="text-align:right;width:93%">
		   					<button id="btn_report_add" onclick="addReport()" style="display:none;"></button>
		   					<button id="btn_report_upload" onclick="doUpload('3','cxee')" style="display:none;"></button>
		   					<button id="btn_report_edit" onclick="changeReportFile()" ></button>
		   				</div>
		   				<table id="tab_fileList_report"></table>
		   				<form id="reportFileForm" method="post" enctype="multipart/form-data">
		   					<table id="tab_fileList_report_edit" style="display:none;"></table>
		   				</form>
		   			</td>
		   		</tr>
		   	</table>
		</div>
	</div>
</body>
</html>
