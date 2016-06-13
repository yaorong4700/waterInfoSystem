/**
 * Kunoy 合并单元格
 * 
 * @param {}
 *            grid 要合并单元格的grid对象
 * @param {}
 *            cols 要合并哪几列 [1,2,4]
 * @param {}
 *            is_numrow_merge numrow是否需要合并true需要合并false不需要合并
 */
var arrayTr = [];

var mergeCells = function(grid, cols) {
	if(!document.getElementById(grid.getId() + "-body")){
		return;
	}
	arrayTr = document.getElementById(grid.getId() + "-body").firstChild.firstChild.lastChild
			.getElementsByTagName('tr');
	var trCount = arrayTr.length;
	//alert(trCount);
	var arrayTd;
	var td;
	var rownum = 0;
	var nowColor = null;
	var constant_color_white = "#FFFFFF";
	var constant_color_gray = "#FAFAFA";
	var merge = function(rowspanObj, removeObjs) { // 定义合并函数
		if (nowColor == null) {
			nowColor = constant_color_white;
		}
		else{
			nowColor = (nowColor == constant_color_white ? constant_color_gray : constant_color_white);
		}

			arrayTd = arrayTr[rowspanObj.tr].getElementsByTagName("td"); // 合并行
			td = arrayTd[rowspanObj.td - 1];
			td.rowSpan = rowspanObj.rowspan;
			td.vAlign = "middle";
			$(td).attr("style", "background-color:"+nowColor);
			
			Ext.each(removeObjs, function(obj) { // 隐身被合并的单元格
				arrayTd = arrayTr[obj.tr].getElementsByTagName("td");
				arrayTd[obj.td - 1].style.display = 'none';
			});
	};
	var rowspanObj = {}; // 要进行跨列操作的td对象{tr:1,td:2,rowspan:5}
	var removeObjs = []; // 要进行删除的td对象[{tr:2,td:2},{tr:3,td:2}]
	var col;

	Ext.each(cols, function(colIndex) { // 逐列去操作tr
		var rowspan = 1;
		var divHtml = null;// 单元格内的数值
		rownum = 0;
		nowColor = null;
		for (var i = 1; i < trCount; i++) { // i=0表示表头等没用的行
			arrayTd = arrayTr[i].getElementsByTagName("td");
			var cold = 0;
			col = colIndex + cold;// 跳过RowNumber列和check列
			if (!divHtml) {
				divHtml = arrayTd[col - 1].innerText.replace(/^\n+|\n+$/g,"");;
				rowspanObj = {
					tr : i,
					td : col,
					rowspan : rowspan
				}
				rownum++;
			} else {
				var cellText = arrayTd[col - 1].innerText.replace(/^\n+|\n+$/g,"");;
				var addf = function() {
					rowspanObj["rowspan"] = rowspanObj["rowspan"] + 1;
					removeObjs.push({
						tr : i,
						td : col
					});
					if (i == trCount - 1){
						merge(rowspanObj, removeObjs);// 执行合并函数
					}
				};
				var mergef = function() {
					merge(rowspanObj, removeObjs);// 执行合并函数
					divHtml = cellText;
					rowspanObj = {
						tr : i,
						td : col,
						rowspan : rowspan
					}
					removeObjs = [];
				};
				if (cellText == divHtml) {
					if (colIndex != cols[0]) {
						var leftDisplay = arrayTd[col - 2].style.display;// 判断左边单元格值是否已display
						if (leftDisplay == 'none') {
							addf();
						} else {
							mergef();
						}
					} else {
						addf();
					}
				} else {
					mergef();
				}
			}
		}
	});
	
}
