package com.clarion.worksys.controller;
/**
 * Copyright(C) 2011-2013 Clarion Corp. All rights reserved.
 * 
 * 第二阶层(コンポーネント)管理Controller
 * 
 * @author hscn liuy
 * @create: 2015-12-24
 * @histroy: 2015-12-24 新規作成
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.Component;
import com.clarion.worksys.entity.RequestObject;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UploadResponse;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.filter.DateEditor;
import com.clarion.worksys.httpentity.ComponentReqParam;
import com.clarion.worksys.httpentity.ComponentResponse;
import com.clarion.worksys.httpentity.ComponentResponseRows;
import com.clarion.worksys.service.ComponentService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/component")
public class ComponentController {
	@Autowired
	private ComponentService componentService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	/**
	 * 返回Component分页页面
	 * @param ro
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping
	public String componentList(RequestObject ro, Model model,HttpSession session) {
		Staff staff = (Staff) session.getAttribute(Const.SESSION_USER);
		UserRoleManage userRole = Tools.getRoleCompetenceByKeyCodePageId(staff.getURKeyCode(),"ComponentManage",userRoleManageService);
		List<Map<Integer, Object>> categoryList = componentService.getCategoryList();
		List<Map<String, Object>> componentSortList = componentService.getComponentSortList();
		model.addAttribute("staff", staff);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("componentSortList", componentSortList);
		model.addAttribute("QueryRoleFlag", userRole.getQueryRoleFlag());
		model.addAttribute("AlterRoleFlag", userRole.getAlterRoleFlag());
		model.addAttribute("UploadRoleFlag", userRole.getUploadRoleFlag());
		model.addAttribute("DownloadRoleFlag", userRole.getDownloadRoleFlag());		
		model.addAttribute("SpecialRole1Flag", userRole.getSpecialRole1Flag());
		return "admin/component/component";
	}
	
	/**
	 * 下载Component信息 返回下载地址
	 * @param httpSession
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/downloadComponent")
	public ModelAndView downloadComponent(HttpSession httpSession,HttpServletResponse response) {
    	List<Component> components = componentService.listAllComponent();
    	String realPathString = httpSession.getServletContext().getRealPath("temp");
		String contextpath = httpSession.getServletContext().getContextPath(); 

		String webadr=contextpath+componentService.downloadComponent(components, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}
	    
    /**
     * 请求コンポーネント信息分页数据
     * @param response
     * @param componentReqParam
     * @throws IOException
     * @throws ParseException 
     */
	@RequestMapping(value = "/listComponent")
	public void listComponent(HttpServletResponse response,ComponentReqParam componentReqParam) throws IOException, ParseException {
        if("1".equals(componentReqParam.getQueryRoleFlag())){
        	//设置数据库查询时从哪条数据开始
    		componentReqParam.setPageSeq((componentReqParam.getPage()-1)*componentReqParam.getRp());
    		//如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
            //检索部条件设定
            if (componentReqParam.getCategoryID() != null && "".equals(componentReqParam.getCategoryID().trim())){
            	componentReqParam.setCategoryID(null);
            }
            if (componentReqParam.getComponentSort() != null && "".equals(componentReqParam.getComponentSort().trim())){
            	componentReqParam.setComponentSort(null);
            }
            if (componentReqParam.getComponentID() != null && "".equals(componentReqParam.getComponentID().trim())){
            	componentReqParam.setComponentID(null);
            }
            if (componentReqParam.getIsVisible() != null && "".equals(componentReqParam.getIsVisible().trim())){
            	componentReqParam.setIsVisible(null);
            }
            
    		List<Component> componentList = componentService.listComponent(componentReqParam);		
    		int totalPageCount = componentService.totalPageCount(componentReqParam);
    		
    		List<ComponentResponseRows> componentRespongseList = new ArrayList<ComponentResponseRows>();
    		for (int i = 0; i < componentList.size(); i++) {
    			componentList.get(i).setNo((componentReqParam.getPage()-1)*componentReqParam.getRp() + i + 1);
    			ComponentResponseRows componentResponseRows = new ComponentResponseRows(i, componentList.get(i));
    			componentRespongseList.add(componentResponseRows);
    		}
    		ComponentResponse componentResponse = new ComponentResponse();
    		componentResponse.setPage(componentReqParam.getPage());        //设置
    		componentResponse.setTotal(totalPageCount);
    		componentResponse.setRows(componentRespongseList);
    		Gson gson = new Gson();
    		String resultString = gson.toJson(componentResponse);
    		
    		response.setCharacterEncoding("UTF-8");
    		PrintWriter out = response.getWriter();
    		out.write(resultString);
    		out.close();
        }else{
        	PrintWriter out = response.getWriter();
    		out.write("");
    		out.close();
        }
		
	}

	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	                              ServletRequestDataBinder binder) throws Exception {  
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理   
	    binder.registerCustomEditor(Date.class, new DateEditor());  
	}

	/**
	 * 请求新增コンポーネント页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd() {
		ModelAndView mv = new ModelAndView();
		List<Map<Integer, Object>> categoryList = componentService.getCategoryList();
		List<Map<String, Object>> componentSortList = componentService.getComponentSortList();
		mv.addObject("categoryList", categoryList);
		mv.addObject("componentSortList", componentSortList);
		mv.setViewName("admin/component/componentInfo");
		return mv;
	}
	
	/**
	 * 保存Component信息
	 * 
	 * @param component
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveComponent(Component component) {
		ModelAndView mv = new ModelAndView();
		if (component.getComponentID() == null){
			component.setComponentID("");
		}
		if (component.getComponentSortID() == null ){
			component.setComponentSortID("");
		}
		if (component.getId() == null || component.getId().intValue() == 0){
			if (componentService.insertComponent(component) == false) {
				mv.addObject("msg", "failed");
			} else {
				mv.addObject("msg", "success");
			}
		} else {
			componentService.updateComponent(component);
		}

		mv.setViewName("admin/save_result");
		return mv;
	}
	
	/**
	 * 请求编辑Component页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit(@RequestParam String id) {
		ModelAndView mv = new ModelAndView();
		Component component = componentService.getComponentById(id);
		List<Map<Integer, Object>> categoryList = componentService.getCategoryList();
		List<Map<String, Object>> componentSortList = componentService.getComponentSortList();
		
		mv.addObject("component", component);
		mv.addObject("categoryList", categoryList);
		mv.addObject("componentSortList", componentSortList);
		mv.setViewName("admin/component/componentInfo");
		return mv;
	}

	/**
	 * 删除コンポーネント
	 * 
	 * @param id
	 * @param out
	 */
	@RequestMapping(value = "/delete")
	public void deleteComponent(@RequestParam String ids, PrintWriter out) {
		String[] allId = ids.split(",");
		componentService.deleteComponents(allId);
		String msg ="{\"result\":\"success\"}";
		out.write(msg);
		out.close();
	}
	
	/**
	 * 上传页面
	 */
	@RequestMapping(value = "/upload")
	public ModelAndView toUpload(HttpSession httpSession) {
		ModelAndView mv = new ModelAndView();
		Staff staffInfo = (Staff) httpSession.getAttribute(Const.SESSION_USER);
		mv.addObject("staffInfo", staffInfo);
		mv.setViewName("admin/component/componentUpload");
		return mv;
	}

	/**
	 * Component信息文件上传
	 */
	@RequestMapping(value = "/fileUpload",method=RequestMethod.POST)
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws ParseException {
		
		HttpSession session = request.getSession();
		try {
			// 转型为MultipartHttpRequest：  
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            // 获得文件：  
            MultipartFile file = multipartRequest.getFile("file");
            
            // 获得输入流：  
            InputStream filestream = file.getInputStream();
            
            HSSFWorkbook wb = new HSSFWorkbook(filestream);
            
            HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);

            if (sheet == null ){
            	filestream.close();
				PrintWriter out = response.getWriter();
				out.write("NOSHEET");
				out.close();
            } else {
	            HSSFRow row = null;
	            Component uploadComponent = null;
	            int startRow = 2;
	            int startCell = 0;
	            List<Map<Integer, Object>> categoryList = componentService.getCategoryList();
	            List<Map<String, Object>> componentSortList = componentService.getComponentSortList();
	            List<UploadResponse> errorInfoList = new ArrayList<UploadResponse>();
	            UploadResponse errorInfo;
	            String resultString = "";

	            int totalRowCount = 0;

	            for (int i=0; ;i++){
	            	row = sheet.getRow(startRow+i);
	            	if (row == null || row.getCell(startCell) == null || row.getCell(startCell).toString().trim().isEmpty()){
	            		break;
	            	}
	            	totalRowCount++;
	            	uploadComponent = new Component();
	            	errorInfo = new UploadResponse();
	            	//表示
	            	if(row.getCell(1)!= null && !"".equals(row.getCell(1).toString().trim())) {
	            		if (row.getCell(1).toString().equals(Tools.getPropertiesValue(session, "component_string_visible_1"))){
	            			uploadComponent.setIsVisible("1");//表示
	            		} else if (row.getCell(1).toString().equals(Tools.getPropertiesValue(session, "component_string_visible_2"))){
	            			uploadComponent.setIsVisible("0");//非表示
	            		} else {
	            			uploadComponent.setIsVisible("1");//デフォルトは表示
	            		}
	            	} else {
	            		uploadComponent.setIsVisible("1");
	            	}
	            	//開発段階
					if(row.getCell(2)!= null && !"".equals(row.getCell(2).toString().trim())) {
						boolean hasCategory = false;
						for (int j = 0 ; j < categoryList.size(); j++){
	            			Map<Integer,Object> t = categoryList.get(j);	            
            				if (row.getCell(2).toString().trim().equals(t.get("categoryName"))){
            					uploadComponent.setCategoryID(Integer.parseInt((t.get("categoryID").toString())));
            					hasCategory = true;
            					break;
            				}
	            		}
						if (!hasCategory){
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_category"));
		            		errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "componentUpload_string_msg_notExist").replace("{0}", row.getCell(2).toString()));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_category"));
	            		errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "componentUpload_string_msg_noBlank"));
	            		errorInfoList.add(errorInfo);
	            		continue;
	            	}
	            	
	            	//コンポーネント名
					if(row.getCell(3) != null &&  !"".equals(row.getCell(3).toString().trim())) {
						uploadComponent.setComponentName(row.getCell(3).toString().trim());
						if(checkTextCellAndRequired(row.getCell(3).toString().trim(),50)){
							uploadComponent.setComponentName(row.getCell(3).toString().trim());
						} else {
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_componentName"));
		            		errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "componentUpload_string_msg_overLength").replace("{0}", "50"));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_componentName"));
	            		errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "componentUpload_string_msg_noBlank"));
	            		errorInfoList.add(errorInfo);
	            		continue;
	            	}
	            	//分類
					if(row.getCell(4) != null && !"".equals(row.getCell(4).toString().trim())) {						
						boolean hasComponentSort = false;
						for (int j = 0 ; j < componentSortList.size(); j++){
	            			Map<String,Object> t = componentSortList.get(j);	            
            				if (row.getCell(4).toString().trim().equals(t.get("componentSortName"))){
            					uploadComponent.setComponentSortID(t.get("componentSortID").toString());
            					hasComponentSort = true;
            					break;
            				}
	            		}
						if (!hasComponentSort){
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
							errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_componentSort"));
		            		errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "componentUpload_string_msg_notExist").replace("{0}", row.getCell(4).toString()));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		uploadComponent.setComponentSortID("");
	            	}
	            	//コンポーネントID
					if(row.getCell(5) != null &&  !"".equals(row.getCell(5).toString().trim())) {
						if(checkTextCellAndRequired(row.getCell(5).toString().trim(),10)){
							uploadComponent.setComponentID(row.getCell(5).toString().trim());
						} else {
							errorInfo.setErrNo(String.valueOf(startRow + i + 1));
		            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_componentID"));
		            		errorInfo.setErrContent(errorInfo.getErrItem()
		            				+Tools.getPropertiesValue(session, "componentUpload_string_msg_overLength").replace("{0}", "10"));
		            		errorInfoList.add(errorInfo);
		            		continue;
						}
	            	} else {
	            		uploadComponent.setComponentID("");
	            		/*//出错时处理
	            		errorInfo.setErrNo(String.valueOf(startRow + i + 1));
	            		errorInfo.setErrItem(Tools.getPropertiesValue(session, "componentUpload_string_msg_componentID"));
	            		errorInfo.setErrContent(errorInfo.getErrItem()+Tools.getPropertiesValue(session, "staffUpload_string_msg_noBlank"));
	            		errorInfoList.add(errorInfo);
	            		continue;*/
	            	}

					if ( row.getCell(6) != null &&  !"".equals(row.getCell(6).toString().trim())){
						// 存在时更新信息
						if (row.getCell(6).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
		            		DecimalFormat df = new DecimalFormat("0");
		            		uploadComponent.setId(Integer.valueOf(df.format(row.getCell(6).getNumericCellValue())));
		            	} else {
		            		uploadComponent.setId(Integer.valueOf(row.getCell(6).getStringCellValue()));
		            	}
						componentService.updateComponent(uploadComponent);
					} else {
						// 不存在时追加信息
						componentService.insertComponent(uploadComponent);
					}
	            }
	            
	            filestream.close();

	            Gson gson = new Gson();
				resultString = gson.toJson(errorInfoList);
				int totalErrorCount = errorInfoList.size();
				String result ="";

				if(0==totalErrorCount){				
					result ="{\"code\":\"S\",\"successRowCount\":"+String.valueOf(totalRowCount)+",\"result\":"+resultString+"}";
				}else{
					int successRowCount=totalRowCount-totalErrorCount;
					result ="{\"code\":\"F\",\"ngRowCount\":"+totalErrorCount+",\"successRowCount\":"+successRowCount+",\"result\":"+resultString+"}";
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
            }
		} catch (IOException e){
			e.printStackTrace();  
            PrintWriter out;
			try {
				out = response.getWriter();
				out.write("ERROR");
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private boolean checkTextCellAndRequired(String value,int length){	
		if(value == null || "".equals(value)){
			return false;
		}
		if(value.getBytes().length > length){
			return false;
		}
		return true;
	}
}
