	/**
	 * 添加Grid 标题对齐配置 注： 这里不能使用Ext.apply 方法重写，因为在 afterRender 方法中，要通过 callParent 方法
	 * 调用父类的afterRender方法，这就需要用到afterRender方法内的 “$owner”和“$name”属性，
	 * 而使用apply方法会丢失这两个属性，导致callParent 方法失败，所以必须使用ovveride方法 重写，才能保留这两个属性。
	 * 
	 * @author annson
	 * 
	 */
	if (Ext.grid.Column) {
	 Ext.grid.Column.override({
	     afterRender : function() {
	      var me = this, el = me.el;
	        
	      me.callParent(arguments);
	        
	      // el.addCls(Ext.baseCSSPrefix +
	      // 'column-header-align-' +
	      // me.align).addClsOnOver(me.overCls);
	      // me.titleAlign = me.titleAlign || me.align;
	      me.titleAlign = me.titleAlign || 'center';// 设置标题栏默认居中
	      el.addCls(Ext.baseCSSPrefix + 'column-header-align-'
	        + me.titleAlign).addClsOnOver(me.overCls);
	        
	      me.mon(el, {
	         click : me.onElClick,
	         dblclick : me.onElDblClick,
	         scope : me
	        });
	        
	      // BrowserBug: Ie8 Strict Mode, this will break
	      // the focus for this browser,
	      // must be fixed when focus management will be
	      // implemented.
	      if (!Ext.isIE8 || !Ext.isStrict) {
	       me.mon(me.getFocusEl(), {
	          focus : me.onTitleMouseOver,
	          blur : me.onTitleMouseOut,
	          scope : me
	         });
	      }
	        
	      me.mon(me.titleContainer, {
	         mouseenter : me.onTitleMouseOver,
	         mouseleave : me.onTitleMouseOut,
	         scope : me
	        });
	        
	      me.keyNav = Ext.create('Ext.util.KeyNav', el, {
	         enter : me.onEnterKey,
	         down : me.onDownKey,
	         scope : me
	        });
	     }
	 });
	}