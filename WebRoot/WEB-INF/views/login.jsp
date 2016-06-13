
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工数系统后台</title>
<style type="text/css">
	body{margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #1B3142;}
	.header{width:100%;height:41px;background: url(images/login-top-bg.gif) repeat-x;}
	.center{width:100%;height:532px;background: url(images/login_bg.jpg) repeat-x;}
	.login_right{float:right;width:100%;height:100%;background: url(images/login-wel.gif) bottom no-repeat;}
	.login_left{float:right;width:40%;height:100%;}
	.login_titletop{margin-left:435px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 24px;height:70px;line-height: 36px;color: #666666;font-weight: bold;}
	.login_titletop .f{color: #086BB2;}
	.login_title{margin-left:435px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 14px;height:36px;line-height: 36px;color: #666666;font-weight: bold;}
	.login_info{margin-left:435px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 12px;height:36px;line-height: 36px;color: #333333;}
	.login_btn{margin-left:550px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 12px;height:36px;line-height: 36px;color: #333333;}
    .login_cookie{margin-left:435px;font-family: PMingLiu, Helvetica, sans-serif;font-size: 12px;height:20px;line-height: 20px;color: #333333;}
	.login_input{width:188px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.login_select{width:150px;height:20px;border:1px solid #7F9DB9;vertical-align: middle;}
	.login_code{width:70px;height:20px;margin-left:30px;border:1px solid #7F9DB9;vertical-align: middle;}
	.btn{width:60px;height:25px;border-width:0px;background-image: url(images/btn-bg2.gif);letter-spacing: 3px;margin-right:70px;cursor: pointer;}
	.login_info img{vertical-align: middle;cursor: pointer;}
	.width_lbl{width:520px;}
	.errInfo{display:none;color:red;}
	
	.logo{width:100%;height:50px;background: url(images/logo2.png) no-repeat;_background:none;_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='images/logo2.png';)}
	.left_txt{font-family: Arial, Helvetica, sans-serif;font-size: 12px;line-height: 25px;color: #666666;}
	
	.bottom{width:100%;height:auto;text-align:center;font-family: Arial, Helvetica, sans-serif;font-size: 10px;color: #ABCAD3;text-decoration: none;line-height: 20px;}
</style>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/cookie/cookie.js"></script>
<script type="text/javascript" src="js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/js_user/com.js"></script>
</head>
<body> 
<div style="width:100%;min-width:1280px;height:645px;position: absolute;top:50%;left:50%;margin-top:-320px;margin-left:-50%;">
	<div class="header"></div>
	<form action="login.do" method="post" name="loginForm" onsubmit="return check();">
	<input type="hidden" name="username" id="username" value=""/>
	<input type="hidden" name="address" id="address" value=""/>
	<input type="hidden" name="password" id="password" value=""/>
	<input type="hidden" name="type" id="password" value=""/>
	<div class="center"> 
		<div class="login_right">
			<div style="width:100%;height:auto;margin-top:100px;">
			    <div class="login_titletop">
					<label class="f">■  </label><label id="login_string">&nbsp;&nbsp;智&nbsp;能&nbsp;水&nbsp;表&nbsp;管&nbsp;理&nbsp;系&nbsp;统</label><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label id="login_string_sysname_right">Water-Info System</label>
				</div>
				<div class="login_title">
					<label id="login_string_systemlogin_right">系统登录</label>
				</div>
				<div class="login_info">
				    <table><tr><td width=80px;>
					<label id="login_string_username_right" style="font-size: 13px">用户名：</label></td>
					<td><input type="text" name="username_right" id="username_right" class="login_input" value="${username_right}"/>
					</td>
					</tr></table>
				</div>
				<div class="login_info">
				    <table><tr><td width=80px;>
					<label id="login_string_password_right" style="font-size: 13px" >密　码：</label></td>
					<td><input type="password" name="password_right" id="password_right" class="login_input" value="${password_right}"/>
					</td>
					</tr></table>
				</div>
				<!-- <div class="login_info">
					<label>验证码：</label><input type="text" name="code" id="code" class="login_code"/>&nbsp;&nbsp;
					<img id="codeImg" alt="点击更换" title="点击更换" src=""/>
					&nbsp;<span id="codeerr" class="errInfo"></span>
				</div>
				 -->
				<div class="login_cookie">
				        <table><tr><td width=97px;>
                        <span style="font-size:12px; color:black;"><label id="login_string_rememberPassword_right" style="font-size: 13px"></label></span></td>  
                        <td>&nbsp;&nbsp;&nbsp;<input id="saveCookie_right" type="checkbox" value="" /></td>
                        </tr></table>
				</div>
				<br>
				<div class="login_btn">
					<input id="login_string_loginBtn_right" type="submit" name="loginBtn_right" value="登录" class="btn" onclick="javascript:checkright()"/>
					<input id="login_string_cancelBtn_right" type="reset" name="cancelBtn_right" value="取消" class="btn"/>
				</div>
				<br>
				<br>
				<div class="login_info">
				    <table><tr><td width=580px;>
					<!--<label id="login_string_memo_right" style="font-size: 13px" >※CT以外的人员(CHI、DCOE、CXEE)请从右侧登录系统，谢谢配合！</label></td>  -->
					</tr></table>
				</div>
			</div>
		</div>
	</div>
	</form>
</div>
	<script type="text/javascript">
if (window != top) 
top.location.href = location.href; 
	
		var errInfo = "${errInfo}";
		var nameerrInfo = "";
		var passworderrInfo = "";
		//用户名或密码错误，请重新输入。
		var E0003 = "";
		var E0004 = "";
		var E00061 = "";
		var language = getCookieValue("language");
		
		var type = "";
		
		$(document).ready(function(){			
			if (language!="CN" && language!="JP"){
				language = "CN";
			}
			loadProperties(language,setPageByLanguage);
			
			//changeCode();
			//$("#codeImg").bind("click",changeCode);
			if(errInfo!=""){
				
				if(errInfo == "E0003"){
					alert(E0003);
				} else if(errInfo == "E0004"){
					alert(E0004);
				}else if(errInfo == "E0005"){
					alert(E0005);
				}
				/**if(errInfo.indexOf("验证码")>-1){
					$("#codeerr").show();
					$("#codeerr").html(errInfo);
					$("#code").focus();
				}else{
					$("#nameerr").show();
					$("#nameerr").html(errInfo);
				}**/
				//$("#nameerr").show();
				//$("#nameerr").html(errInfo);
			}else{
			        var userNameValue_right = getCookieValue("userName_right");  
                    $("#username_right").val(userNameValue_right); 
                    var passwordValue_right = getCookieValue("password_right");  
                    $("#password_right").val(passwordValue_right);
                    if(userNameValue_right != ""){
                        $("#saveCookie_right").attr("checked", true);
                    } 
                    var userNameValue_left = getCookieValue("userName_left");  
                    $("#username_left").val(userNameValue_left); 
                    var passwordValue_left = getCookieValue("password_left");  
                    $("#password_left").val(passwordValue_left);
                    if(userNameValue_left != ""){
                        $("#saveCookie_left").attr("checked", true);
                    } 
			}
			$("#username_left").focus();
		});
		
		function checkright(){
			type ="right";
		}
		
		function checkleft(){
			type ="left";
		}
	
		function genTimestamp(){
			var time = new Date();
			return time.getTime();
		}
	 
		//function changeCode(){
			//$("#codeImg").attr("src","code.do?t="+genTimestamp());
		//}
		
		function resetErr(){
			//$("#nameerr_right").hide();
			//$("#nameerr_right").html("");
			//$("#pwderr_right").hide();
			//$("#pwderr_right").html("");
			/** 取消验证码
			$("#codeerr").hide();
			$("#codeerr").html("");
			**/
		}
		
		function check(){
			if (type =="right") {
				return check_right();
			} if (type =="left") {
				return check_left();
			}
		}
		
		function check_left(){
			resetErr();			
			setCookie("language",language,365*24,"/");
			if($("#username_left").val()==""){		
				//$("#nameerr").html("用户名不得为空！");
				alert(nameerrInfo);
				$("#username_left").focus();
				return false;
			}
			if($("#password_left").val()==""){
				//$("#pwderr").html("密码不得为空！");
				alert(passworderrInfo);
				$("#password_left").focus();
				return false;
			}
			/** 取消验证码
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			**/
			if( $("#saveCookie_left").attr("checked")){
                setCookie("userName_left",$("#username_left").val(),365*24,"/");  
                setCookie("password_left",$("#password_left").val(),365*24,"/");  
            }else{
                deleteCookie("userName_left","/");
                deleteCookie("password_left","/");
            } 
			
			$("#username").val($("#username_left").val()); 
			 $("#address").val($("#address_left").val()); 
			 $("#password").val($("#password_left").val());
			 $("#type").val(type);
			return true;
		}
		
		function check_right(){
			resetErr();			
			setCookie("language",language,365*24,"/");
			if($("#username_right").val()==""){
				$("#nameerr_right").show();
				//$("#nameerr").html("用户名不得为空！");
				alert(nameerrInfo);
				$("#username_right").focus();
				return false;
			}
		//	if($("#password_right").val()==""){
			//	$("#pwderr_right").show();
				//$("#pwderr").html("密码不得为空！");
				//alert(passworderrInfo);
				//$("#password_right").focus();
				//return false;
			//}
			/** 取消验证码
			if($("#code").val()==""){
				$("#codeerr").show();
				$("#codeerr").html("验证码不得为空！");
				$("#code").focus();
				return false;
			}
			**/
			if( $("#saveCookie_right").attr("checked")){
                setCookie("userName_right",$("#username_right").val(),365*24,"/");  
                setCookie("password_right",$("#password_right").val(),365*24,"/");  
            }else{
                deleteCookie("userName_right","/");
                deleteCookie("password_right","/");
            }
			 $("#username").val($("#username_right").val()); 
			 $("#address").val($("#address_right").val()); 
			 $("#password").val($("#password_right").val());
			 $("#type").val(type);
			
			return true;
		}
		//ADD BY WEI 20151029 START
		//国际化
		function setPageByLanguage(){
			//左边CT登录
			$('#login_string_CT_left').html($.i18n.prop('login_string_CT_left'));
			$('#login_string_sysname_left').html($.i18n.prop('login_string_sysname_left'));
			$('#login_string_systemlogin_left').html($.i18n.prop('login_string_systemlogin_left'));
			$('#login_string_username_left').html($.i18n.prop('login_string_username_left'));
			$('#login_string_password_left').html($.i18n.prop('login_string_password_left')); 
			$('#login_string_rememberPassword_left').html($.i18n.prop('login_string_rememberPassword_left')); 
			$('#login_string_loginBtn_left').val($.i18n.prop('login_string_loginBtn_left'));
			$('#login_string_cancelBtn_left').val($.i18n.prop('login_string_cancelBtn_left'));
			$('#login_string_memo_left').html($.i18n.prop('login_string_memo_left'));		
			
			$('#login_string_CXEE_right').html($.i18n.prop('login_string_CXEE_right'));
			$('#login_string_sysname_right').html($.i18n.prop('login_string_sysname_right'));
			$('#login_string_systemlogin_right').html($.i18n.prop('login_string_systemlogin_right'));
			$('#login_string_username_right').html($.i18n.prop('login_string_username_right'));
			$('#login_string_password_right').html($.i18n.prop('login_string_password_right')); 
			$('#login_string_rememberPassword_right').html($.i18n.prop('login_string_rememberPassword_right')); 
			$('#login_string_loginBtn_right').val($.i18n.prop('login_string_loginBtn_right'));
			$('#login_string_cancelBtn_right').val($.i18n.prop('login_string_cancelBtn_right'));
			$('#login_string_memo_right').html($.i18n.prop('login_string_memo_right'));		

			nameerrInfo = $.i18n.prop('E0003');
			//passworderrInfo = $.i18n.prop('E0004');
			E0003 = $.i18n.prop('E0003');
			E0004 = $.i18n.prop('E0004');
			E0005 = $.i18n.prop('E0005');
			document.title =$.i18n.prop('login_string_title');
		}
		//ADD BY WEI 20151029 END
	</script>
</body>
</html>