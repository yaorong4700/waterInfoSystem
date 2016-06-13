Ext.namespace('Ext.ux.plugin');
Ext.ux.plugin.PagingToolbarResizer = Ext.extend(Object, {
  options: [5, 10],
  mode: 'remote',
  displayText: '记录数',
  prependCombo: false,
  constructor: function(config){
    Ext.apply(this, config);
    Ext.ux.plugin.PagingToolbarResizer.superclass.constructor.call(this, config);
  },


  init : function(pagingToolbar) {
var comboStore = this.options;
 
    var combo = new Ext.form.ComboBox({
      typeAhead: false,
      triggerAction: 'all',
      forceSelection: true,
      selectOnFocus:true,
      lazyRender:true,
      editable: false,
      mode: this.mode,
      value: pagingToolbar.pageSize,
      width:50,
      store: comboStore,
      listeners: {
        select: function(combo, records, eOpts){
        	var newSize = records[0].data.field1;
        	var currentPage = pagingToolbar.store.currentPage;
        	var totalCount = pagingToolbar.store.totalCount;
        	if (totalCount < currentPage * newSize) {
        		currentPage = Math.ceil(totalCount/newSize);
        	}
        	pagingToolbar.pageSize = newSize;
        	pagingToolbar.store.pageSize = newSize;
        	var lr = pagingToolbar.store.data.length;
          
        	if (lr > 0) {
    			pagingToolbar.store.loadPage(currentPage);
        	}
          //pagingToolbar.doLoad(Math.floor(pagingToolbar.cursor/pagingToolbar.pageSize)*pagingToolbar.pageSize);
        }
      }
    });


    var index = 0;
    if (this.prependCombo){
    	index = pagingToolbar.items.indexOf(pagingToolbar.first);
    	if (index < 0) {
    		index = 0;
    	}
    	index--;
    } else {
    	index = pagingToolbar.items.indexOf(pagingToolbar.refresh);
        pagingToolbar.insert(++index,'-');
    }
    
    pagingToolbar.insert(++index, this.displayText);
    pagingToolbar.insert(++index, combo);
    
    if (this.prependCombo){
    	pagingToolbar.insert(++index,'-');
    }
    
    //destroy combobox before destroying the paging toolbar
    pagingToolbar.on({
    	beforedestroy: function(){
    		combo.destroy();
    	}
    });

    pagingToolbar.store.pageSize = pagingToolbar.pageSize;

  }
});
