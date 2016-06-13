jQuery.cookie=function(name,value,options){if(typeof value!='undefined'){options=options||{};if(value===null){value='';options.expires=-1};var expires='';if(options.expires&&(typeof options.expires=='number'||options.expires.toUTCString)){var date;if(typeof options.expires=='number'){date=new Date();date.setTime(date.getTime()+(options.expires*24*60*60*1000))}else{date=options.expires};expires='; expires='+date.toUTCString()};var path=options.path?'; path='+(options.path):'';var domain=options.domain?'; domain='+(options.domain):'';var secure=options.secure?'; secure':'';document.cookie=[name,'=',encodeURIComponent(value),expires,path,domain,secure].join('')}else{var cookieValue=null;if(document.cookie&&document.cookie!=''){var cookies=document.cookie.split(';');for(var i=0;i<cookies.length;i++){var cookie=jQuery.trim(cookies[i]);if(cookie.substring(0,name.length+1)==(name+'=')){cookieValue=decodeURIComponent(cookie.substring(name.length+1));break}}};return cookieValue}};
(function($) {
	$.fn.jsadsense = function(setting) {
		var opts = $.extend( {}, $.fn.jsadsense.defaults, setting); 
		if($.cookie(opts.key)=='1'){return;}
		var date = new Date();  
    date.setTime(date.getTime() + opts.cookietime);  
    $.cookie(opts.key, '1', { path: '/', expires: date });   
		var isie=!!(window.attachEvent&&navigator.userAgent.indexOf('Opera')===-1); 
		var adsense = $('<div class="'+opts.key+'case '+opts.key+'case_s"><div class="'+opts.key+'tit"><h3></h3><span>X</span></div><div class="'+opts.key+'con"></div></div>').appendTo($(this));
		var ffnn =  {closediv : function(){var btop = parseInt($('.'+opts.key+'case').css('top').replace("px",""));$('.'+opts.key+'case').animate({height:0,top:btop+parseInt($('.'+opts.key+'case').css('height').replace("px",""))},500);setTimeout("$('.'+opts.key+'case').remove()",500);}}
		adsense.find('span:eq(0)').click(function(){ffnn.closediv()});
		$('.'+opts.key+'case h3').html(opts.title);
		$('.'+opts.key+'case .'+opts.key+'con').append(opts.content);
		var btop=document.documentElement.clientHeight; 
		var height222=opts.height;
		if(isie){ adsense.css('top',btop+'px').css('height','0px').show().animate({height:height222,top:btop-height222});
			$(window).scroll(function(){var btop=position=$(window).scrollTop()+document.documentElement.clientHeight;  
				adsense.css("top",btop-height222)});$(window).scroll();
				}else{ adsense.css('top',btop+'px').css('position','fixed').css('height','0px').show().animate({height:height222,top:btop-height222});}
		
	}
	$.fn.jsadsense.defaults = {
		title : '通知',
		content : '<div style="background:#eee;height:200px;text-align:center;line-height:160px;"></div>',
		height : 200,
		key : 'jsadsense_',
		cookietime : 1000
	};
})(jQuery);