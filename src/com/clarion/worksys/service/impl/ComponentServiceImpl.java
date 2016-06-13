package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.Component;
import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.httpentity.ComponentReqParam;
import com.clarion.worksys.mapper.ComponentMapper;
import com.clarion.worksys.service.ComponentService;


@Service
public class ComponentServiceImpl implements ComponentService{
	
	@Autowired
	private ComponentMapper componentMapper;
	
	public List<Map<Integer, Object>> getCategoryList() {
		return componentMapper.getCategoryList();
	}
	
	public List<Map<String, Object>> getComponentSortList() {
		return componentMapper.getComponentSortList();
	}
	
	public List<Component> listComponent(ComponentReqParam componentReqParam){
		return componentMapper.listComponent(componentReqParam);
	}
	public List<Component> listAllComponent(){
		return componentMapper.listAllComponent();
	}
	public int totalPageCount(ComponentReqParam componentReqParam) {
		return componentMapper.totalPageCount(componentReqParam);
	}
	public void deleteComponents(String[] ids) {
		componentMapper.deleteComponents(ids);
	}
	public Component getComponentById(String id){
		return componentMapper.getComponentById(id);
	}
	public int getComponentCount(Component component){
		return componentMapper.getComponentCount(component);
	}
	public boolean insertComponent(Component component){
		int count = componentMapper.insertComponent(component);		
		if(count == 1){
			return true;
		}else{
			return false;
		}
	}
	public void updateComponent(Component component){
		componentMapper.updateComponent(component);
	}
	
	public String downloadComponent(List<Component> componentList,String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "ComponentList_"
        					  + String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String strnameString2 = realpathString + "\\" + filenameString + ".xls";

        //返回文件路径
        String webaddrString  = "/temp/" + filenameString + ".xls";

        try{
			File file;
			POIFSFileSystem fs;
			HSSFWorkbook wb;
			HSSFSheet sheet;
			int curruntRow=2;

			//读取模板
			file = new File(ComponentServiceImpl.class.getResource(Parma.COMPONENT_TEMPLATE).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "ComponentInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < componentList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				row.getCell(0).setCellValue(i+1);//順番
				row.getCell(1).setCellValue(componentList.get(i).getIsVisible());//表示
				row.getCell(2).setCellValue(componentList.get(i).getCategoryName());//開発段階
				row.getCell(3).setCellValue(componentList.get(i).getComponentName());//コンポーネント名
				row.getCell(4).setCellValue(componentList.get(i).getComponentSort());//分類
				row.getCell(5).setCellValue(componentList.get(i).getComponentID());//コンポーネントID
				row.getCell(6).setCellValue(componentList.get(i).getId()); //id
			}

			OutputStream out = new FileOutputStream(strnameString2);
			wb.write(out);
	    	out.close();
			return webaddrString;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
