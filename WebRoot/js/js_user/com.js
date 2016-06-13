String.prototype.trim = function () {
	if(this && this != null){
		return this.replace(/(^\s*)|(\s*$)/g, "");
	}
};
String.prototype.ltrim = function () {
	if(this && this != null){
		return this.replace(/(^\s*)/g, "");
	}
};
String.prototype.rtrim = function () {
	if(this && this != null){
		return this.replace(/(\s*$)/g, "");
	}
};
//禁止后退键 作用于Firefox、Opera   
document.onkeypress=banBackSpace;  
//禁止后退键  作用于IE、Chrome   
document.onkeydown=banBackSpace;
var all_i18n = null;
var INIT_TIMES = 75;
var INIT_YEARS = 2014;
/* 设置页面语言 */
function loadProperties(strlan,callback,repath){
	if(repath == null){
		repath = "";
	}
	jQuery.i18n.properties({	// 加载资浏览器语言对应的资源文件
		name: "strings", 		// 资源文件名称
		path: repath+'resource/i18n/',	// 资源文件路径
		mode: 'map', 			// 用 Map 的方式使用资源文件中的值
		language: strlan,
		callback: callback
	}); 
}

/* 初始化界面语言 */
function reloadProperties(){
	loadProperties(jQuery.i18n.browserLang(),setPageByLanguage);
}

/* 取得浏览器窗口高度  */
function getBrowserHeight() { 
	if ($.browser.msie) { 
		return document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight : 
		document.body.clientHeight; 
	} else { 
		return self.innerHeight; 
	} 
} 

/* 取得浏览器窗口宽度  */
function getBrowserWidth() { 
	if ($.browser.msie) { 
		return document.compatMode == "CSS1Compat" ? document.documentElement.clientWidth : 
		document.body.clientWidth; 
	} else { 
		return self.innerWidth; 
	} 
}

/* 取得当前项目期数  */
function getCurrentTimes(CurrentYearMonth) {
	if(  Number((CurrentYearMonth).substring(5,7))  <4 ){
		var addTime =  Number( (CurrentYearMonth).substring(0,4) ) - INIT_YEARS -1;
		return INIT_TIMES + addTime;
	}else{
		var addTime =  Number( (CurrentYearMonth).substring(0,4) ) - INIT_YEARS;
		return INIT_TIMES + addTime;
	}
}

function TestMaxLength(testStr) {
	return;
	var regu = '[\u4e00-\u9fa5]';
	var re = new RegExp(regu);
	var terChar=testStr.split("");
	for (var i = 0; i < terChar.length; i ++){
		if (terChar[i].search(re) != -1) { 
			maxLength = maxLength + 1; 
		} else {
			maxLength = maxLength + 2; 
		}
	}
	return maxLength;
}

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外   
function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源      
      
    var t = obj.type || obj.getAttribute('type');//获取事件源类型     
      
    //获取作为判断条件的事件类型   
    var vReadOnly = obj.getAttribute('readonly');  
    var vEnabled = obj.getAttribute('enabled');  
    //处理null值情况   
    vReadOnly = (vReadOnly == null) ? false : vReadOnly;  
    vEnabled = (vEnabled == null) ? true : vEnabled;  
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，   
    //并且readonly属性为true或enabled属性为false的，则退格键失效   
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")   
                && (vReadOnly==true|| vReadOnly=='readonly' || vEnabled!=true))?true:false;  
     
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效   
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")  
                ?true:false;          
    //判断   
    if(flag2){  
        return false;  
    }  
    if(flag1){     
        return false;     
    }     
}  
//画面粘贴内容校验
function ChkPaste(ctl) {
	var value = clipboardData.getData("text");
	var reg = null;
	var Vid = false;
	switch(ctl) {
		case 1://非负浮点数（正浮点数 + 0）
			reg = /^\d+(.\d+)?$/;
			break;
		case 2://正整数
			reg = /^[0-9]$|^([1-9])([0-9])+$$/;
			break;
	}
	if(reg.test(value) == true){
		Vid = true;
	}
	if(Vid) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}
//画面仅输入
function ChkKeyIn(ctl) {
	//1 数字（数字）,2 大小写字符及数字,3 数字
	var Vid = false;
	switch(ctl) {
		case 1://仅数字和小数点
			if(((event.keyCode>=48)&&(event.keyCode <=57)||(event.keyCode ==46))) {
				Vid = true;
			}
			break;
		case 2://大小写字符及数字
			if(((event.keyCode>=48)&&(event.keyCode <=57))||((event.keyCode>=97)&&(event.keyCode <=122))||((event.keyCode>=65)&&(event.keyCode <=95))){
				Vid = true;
			}
			break;
		case 3://仅数字
        if(((event.keyCode>=48)&&(event.keyCode <=57))) {
        	Vid = true;
        }
		case 4://仅正负数字数字
			if(((event.keyCode>=48)&&(event.keyCode <=57)||(event.keyCode ==45))) {
	        	Vid = true;
	        }
        break;
	}
	if(Vid) {
		event.returnValue=true;
	} else {
		event.returnValue=false;
	}
}
//画面仅输入(EXT grid用)
function ChkKeyInByExt(ctl, key) {
	//1 数字（数字）,2 大小写字符及数字,3 数字
	var Vid = false;
	switch(ctl) {
		case 1://仅数字和小数点
			if(((event.keyCode>=48)&&(event.keyCode <=57)||(event.keyCode ==46))) {
				Vid = true;
			}
			break;
		case 2://大小写字符及数字
			if(((event.keyCode>=48)&&(event.keyCode <=57))||((event.keyCode>=97)&&(event.keyCode <=122))||((event.keyCode>=65)&&(event.keyCode <=95))){
				Vid = true;
			}
			break;
		case 3://仅数字
        if(((event.keyCode>=48)&&(event.keyCode <=57))) {
        	Vid = true;
        }
		case 4://仅正负数字数字
			if(((event.keyCode>=48)&&(event.keyCode <=57)||(event.keyCode ==45))) {
	        	Vid = true;
	        }
        break;
	}
  return Vid;
}
//特殊字符check
function checkMysqlKey(value)
{
		var sp = ['<','>','\''];
		
		for(var i=0;i<sp.length;i++)
		{
			if(value.indexOf(sp[i])>=0)
			{
			    alert($.i18n.prop('E0048')+"['][<][>]。");
				return false;
			}
		}
		return true;
}
//text输入框三位一逗
function  format_number(obj)
{
 hidden_obj=obj.value.replace(/,/g,"");
 if(hidden_obj=="" || hidden_obj=="NaN"||isNaN(parseFloat(hidden_obj)))
  {
     obj.value= "0.00";
     return;}
 if(parseFloat(hidden_obj) == "0"){//金额为零
		obj.value= "0.00";
	}else if((!isNaN(hidden_obj)) && parseFloat(hidden_obj) < 1&& parseFloat(hidden_obj) >0){//金额小于1大于0
		obj.value= "0"+ parseFloat(hidden_obj).toLocaleString();
	}else if((!isNaN(hidden_obj)) && parseFloat(hidden_obj) < 0&& parseFloat(hidden_obj) >-1){//金额小于0大于-1
		obj.value= "-0"+ parseFloat(Math.abs(hidden_obj)).toLocaleString();
	}else if((!isNaN(hidden_obj)) && parseFloat(hidden_obj) < 0&& parseFloat(hidden_obj) >-1){//金额小于-1
		obj.value= "-"+ parseFloat(Math.abs(hidden_obj)).toLocaleString();
	}else if(hidden_obj != "" && hidden_obj != "0"){//不为空，也不为零，
		obj.value= parseFloat(hidden_obj).toLocaleString();
	}
}
/**
 * 去掉金额想显示中的逗号
 * @param Object 需要验证的文本框对象
 */
 function dropComma(obj){
 	if(!isNaN(obj.value.replace(/,/g, ""))){
 		obj.value = obj.value.replace(/,/g, "");
 	}
 }
