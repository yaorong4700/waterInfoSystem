package com.clarion.worksys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.clarion.worksys.entity.DepartmentUploadResponse;
import com.clarion.worksys.entity.DevelopDepartment;
import com.clarion.worksys.entity.Staff;
import com.clarion.worksys.entity.UserRole;
import com.clarion.worksys.entity.UserRoleManage;
import com.clarion.worksys.entity.WaterInfo;
import com.clarion.worksys.httpentity.DevelopDepartmentReqParam;
import com.clarion.worksys.httpentity.DevelopDepartmentResponse;
import com.clarion.worksys.httpentity.DevelopDepartmentResponseRows;
import com.clarion.worksys.httpentity.RegionPicReqParam;
import com.clarion.worksys.httpentity.RegionPicResponse;
import com.clarion.worksys.httpentity.RegionPicResponseRows;
import com.clarion.worksys.httpentity.RegionSumReqParam;
import com.clarion.worksys.httpentity.RegionSumResponse;
import com.clarion.worksys.httpentity.RegionSumResponseRows;
import com.clarion.worksys.service.DevelopDepartmentService;
import com.clarion.worksys.service.RegionPicService;
import com.clarion.worksys.service.RegionSumService;
import com.clarion.worksys.service.UserRoleManageService;
import com.clarion.worksys.util.Const;
import com.clarion.worksys.util.Tools;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/regionPic")
public class RegionPicController {

	@Autowired
	private DevelopDepartmentService developDepartmentService;
	@Autowired
	private UserRoleManageService userRoleManageService;
	@Autowired
	private RegionPicService regionPicService;
	
	@RequestMapping
	public ModelAndView showDefault(HttpSession session) {

		UserRole user = (UserRole) session.getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/regionPic/regionPic");
		return mv;
	}

	@RequestMapping(value = "/showAll")
	public void showAll(HttpSession session, HttpServletResponse response,RegionPicReqParam regionPicReqParam) throws IOException {
			// 设置数据库查询时从哪条数据开始
			regionPicReqParam.setPageSeq((regionPicReqParam.getPage() - 1) * regionPicReqParam.getRp());
			// 如果搜索词为空,则将值设为NULL,便于Mybatis mapper使用
			if ("".equals(regionPicReqParam.getRegionId())) {
				regionPicReqParam.setRegionId(null);
			}

			if ("".equals(regionPicReqParam.getCollectTimeStart())) {
				regionPicReqParam.setCollectTimeStart(null);
			}
			
			if ("".equals(regionPicReqParam.getCollectTimeEnd())) {
				regionPicReqParam.setCollectTimeEnd(null);
			}

			List<WaterInfo> searchList = regionPicService.searchRegionPicList(regionPicReqParam);
			
			int totalPageCount = regionPicService.totalPageCount(regionPicReqParam);

			List<RegionPicResponseRows> regionPicResponseList = new ArrayList<RegionPicResponseRows>();
			for(int j = 0; j < searchList.size(); j++){
				RegionPicResponseRows regionPicResponseRows = new RegionPicResponseRows(j,searchList.get(j));
				if (regionPicResponseRows.getCell().getRegionID() == null) {
					regionPicResponseRows.getCell().setRegionID("");
				}
				
				if (regionPicResponseRows.getCell().getDeviceID() == null) {
					regionPicResponseRows.getCell().setDeviceID("");
				}
				if (regionPicResponseRows.getCell().getCollectTime() == null) {
					regionPicResponseRows.getCell().setCollectTime("");
				}
				if (regionPicResponseRows.getCell().getWaterNumber() == null) {
					regionPicResponseRows.getCell().setWaterNumber("");
				}
				if (regionPicResponseRows.getCell().getIdentifyFlag() == null) {
					regionPicResponseRows.getCell().setIdentifyFlag("");
				}
				if (regionPicResponseRows.getCell().getCodeValue() == null) {
					regionPicResponseRows.getCell().setCodeValue("");
				}
				if (regionPicResponseRows.getCell().getRegionSummary() == null) {
					regionPicResponseRows.getCell().setRegionSummary("");
				}
				if (regionPicResponseRows.getCell().getDeviceAddress() == null) {
					regionPicResponseRows.getCell().setDeviceAddress("");
				}
				if (regionPicResponseRows.getCell().getWaterPic() == null) {
					regionPicResponseRows.getCell().setWaterPic("");
				}
				regionPicResponseRows.getCell().setTotal(totalPageCount);
				UserRole user = (UserRole) session.getAttribute(Const.SESSION_USER);
				regionPicResponseList.add(regionPicResponseRows);
			}
			RegionPicResponse regionPicResponse = new RegionPicResponse();
			regionPicResponse.setPage(regionPicReqParam.getPage());
			regionPicResponse.setTotal(totalPageCount);
			regionPicResponse.setRows(regionPicResponseList);
			Gson gson = new Gson();
			String resultString = gson.toJson(regionPicResponse);
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resultString);
			out.close();
	}
	
	@RequestMapping(value = "/editNum", method = RequestMethod.POST)
	public void delete(@RequestParam String regionId,@RequestParam String deviceId, @RequestParam String collectTime,  @RequestParam String waterNum,PrintWriter out) throws UnsupportedEncodingException {
		//String[] belongCodes = belongCode.split(",");
		String msg = null;
		RegionPicReqParam regionPicReqParam = new RegionPicReqParam();
		regionPicReqParam.setRegionId(regionId);
		regionPicReqParam.setDeviceId(deviceId);
		regionPicReqParam.setCollectTime(collectTime);
		regionPicReqParam.setWaterNum(waterNum);
		regionPicService.editNumByRegionDeviceCollectTime(regionPicReqParam);
		msg ="{\"result\":\"success\"}";
		
		out.write(msg);
		out.close();
		
	}
	/*
	@RequestMapping(value = "/deleteWaterMax", method = RequestMethod.POST)
	public void delete(@RequestParam String regionID,@RequestParam String deviceID, @RequestParam String collectTime, PrintWriter out) throws UnsupportedEncodingException {
		//String[] belongCodes = belongCode.split(",");
		String[] regionIDNew = regionID.split(",");
		String[] deviceIDNew = deviceID.split(",");
		String[] collectTimeNew = collectTime.split(",");
		String msg = null;
		if(regionIDNew.length != deviceIDNew.length || deviceIDNew.length != collectTimeNew.length || regionIDNew.length != collectTimeNew.length){
			msg ="{\"result\":\"failed\"}";
		}else{
			for(int j=0; j<regionIDNew.length;j++){
				RegionSumReqParam regionSumReqParam = new RegionSumReqParam();
				regionSumReqParam.setRegionID(regionIDNew[j]);
				regionSumReqParam.setDeviceID(deviceIDNew[j]);
				regionSumReqParam.setCollectTime(collectTimeNew[j]);
				regionSumService.deleteByRegionDeviceCollectTime(regionSumReqParam);
			}
			msg ="{\"result\":\"success\"}";
		}
		
		out.write(msg);
		out.close();
		
	}
	
	@RequestMapping(value = "/downloadRegionSum")
	public ModelAndView outPutDepartment(HttpSession session,HttpServletResponse response) throws IOException {
		//DataToExcle newClass = new DataToExcle();
		List<WaterInfo> regionSumList= regionSumService.downloadregionSumShow();
		String realPathString = session.getServletContext().getRealPath("temp");
		String contextpath = session.getServletContext().getContextPath(); 
		//String webadr=contextpath+newClass.toExcel(projectList,realPathString);
		String webadr=contextpath+regionSumService.downloadRegionSum(regionSumList, realPathString);
		ModelAndView mv = new ModelAndView();
		mv.addObject("webadr", webadr);
		mv.setViewName("admin/manhour/download");
		response.setCharacterEncoding("UTF-8");
		return mv;
	}

	@RequestMapping(value = "/add")
	public ModelAndView addDevelopDepartment(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/developDepartment/developDepartmentAdd");
		return mv;
	}
	@RequestMapping(value = "/addCT")
	public ModelAndView addDevelopDepartmentCT(){
		
		
		ModelAndView mv = new ModelAndView();
		List<Integer> deploymentList = developDepartmentService.getDeploymentList();
		List<String> deploymentStr = new ArrayList<String>();
		for(int i = 0 ; i < deploymentList.size(); i++){
			Integer deployment = deploymentList.get(i);
			if(deployment == 1){
				deploymentStr .add("現");
			}else{
				deploymentStr .add("旧");
			}
		}
		
		mv.addObject("deployment", deploymentStr);
		mv.setViewName("admin/developDepartment/developDepartmentAddCT");
		return mv;
		
	}
	
	private boolean checkTextCellAndRequired(String value,int length){	
		boolean bl=true;
		if(value == null ||  "".equals(value)){
			bl=false;
		}
		if(value.length() > length){
			bl=false;
		}
		return bl;
	}
	*/
	
}
