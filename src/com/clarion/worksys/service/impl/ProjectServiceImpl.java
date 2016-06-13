package com.clarion.worksys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clarion.worksys.entity.ExportClass;
import com.clarion.worksys.entity.Parma;
import com.clarion.worksys.entity.Project;
import com.clarion.worksys.entity.Project_category;
import com.clarion.worksys.entity.Stage;
import com.clarion.worksys.httpentity.ProjectReqParam;
import com.clarion.worksys.mapper.ProjectMapper;
import com.clarion.worksys.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 列出最近填写项目外其他项目信息
     */
    public List<Project> listSelectProject(int staffID,String departmentID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        return projectMapper.listAllProject(params);
    }

    public List<Project> getProjectByCategory(String category){
        return projectMapper.getProjectByCategory(category);
    }

    /**
     * 列出最近填写的项目信息
     */
    public List<Project> recentProject(int staffID,String departmentID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        return projectMapper.recentProject(params);
    }

    public List<Project> recentProjectOther(int staffID) {
        // TODO Auto-generated method stub
        return projectMapper.recentProjectOther(staffID);
    }

    /**
     * 根据项目ID获得项目名称
     */
    public String getProjectNameByProjectID(int projectID){
        return projectMapper.getProjectNameByProjectID(projectID);
    }

    /**
     *根据部门和项目类别列出相应的作业类型
     */
    public List<Map<String,Object>> listTask(String category, String department) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("category", category);
        params.put("department", department);
        return projectMapper.listTask(params);
    }

    /**
     * 根据页面请求参数列出项目名
     */
    public List<Project> showAll(ProjectReqParam projectReqParam)
    {
        return projectMapper.showAll(projectReqParam);
    }
    public List<Project> showAllCTCXEE(ProjectReqParam projectReqParam)
    {
        return projectMapper.showAllCTCXEE(projectReqParam);
    }
    /**
     * 根据页面请求参数列出项目名(仅对应项目部门搜索时)
     */
    public List<Project> showAllForDepartment(ProjectReqParam projectReqParam){
        return projectMapper.showAllForDepartment(projectReqParam);
    }

    /**
     * 根据页面请求得到分页页数
     */
    public int totalPageCount(ProjectReqParam projectReqParam){
        return projectMapper.totalPageCount(projectReqParam);
    }
    public int totalPageCountCTCXEE(ProjectReqParam projectReqParam){
        return projectMapper.totalPageCountCTCXEE(projectReqParam);
    }
    /**
     * 根据页面请求得到分页页数(仅对应项目部门搜索)
     */
    public int totalPageCountForDepartment(ProjectReqParam projectReqParam){
        return projectMapper.totalPageCountForDepartment(projectReqParam);
    }

    /**
     * 根据项目ID得到项目信息
     */
    public Project getProjectById(int projectID){
        return null;
    }
    
    /**
     * 根据项目ID得到项目信息
     */
    public Project getProjectByIdCXEE(int projectID){
        return projectMapper.getProjectByIdCXEE(projectID);
    }
    
    /**
     * 根据项目ID得到项目信息
     */
    public Project getProjectByIdCT(int projectID){
        return projectMapper.getProjectByIdCT(projectID);
    }

    /**
     * 插入一条项目信息
     */
    public boolean insertNewProject(Project project){
        int count = projectMapper.insertNewProject(project);
        if (count == 1) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * 插入一条项目信息
     */
    public boolean insertNewProjectCT(Project project){
        int count = projectMapper.insertNewProjectCT(project);
        if (count == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 更新一条项目信息
     */
    public void updateProject(Project project){
        projectMapper.updateProject(project);
    }
    
    /**
     * 更新一条项目信息
     */
    public void updateProjectCT(Project project){
        projectMapper.updateProjectCT(project);
    }

    public void delectProject(String[] ids){
        projectMapper.delectProject(ids);
    }
    
    public void delectProjectCT(String[] ids){
        projectMapper.delectProjectCT(ids);
    }

    public void callinsertPD(String project,String category,String departmentID){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        paramMap.put("departmentID",departmentID);
        projectMapper.callinsertPD(paramMap);
    }
    
    public void callinsertPDCXEE(String project,String category,String departmentID){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        paramMap.put("departmentID",departmentID);
        projectMapper.callinsertPDCXEE(paramMap);
    }

    public void calldeletePD(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.calldeletePD(paramMap);
    }
    
    public void calldeletePDCXEE(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.calldeletePDCXEE(paramMap);
    }

    public void callinsertDIANQI(String project,String category)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertDIANQI(paramMap);
    }

    public void callinsertJIGOU(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertJIGOU(paramMap);
    }

    public void callinsertSoft(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertSoft(paramMap);
    }

    public void callinsertQuality(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertQuality(paramMap);
    }

    public void callinsertGOUXIANG(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertGOUXIANG(paramMap);
    }

    public void callinsertGONGCHENG(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertGONGCHENG(paramMap);
    }

    public void callinsertSHANGPIN(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertSHANGPIN(paramMap);
    }

    public void callinsertKAIFAGUANLI(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertKAIFAGUANLI(paramMap);
    }

    public void callinsertYUANJIA(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertYUANJIA(paramMap);
    }

    public void callinsertSHEJIKAIFA(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertSHEJIKAIFA(paramMap);
    }

    public void callinsertSHEJIKAIFAKAIFAGUANLI(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertSHEJIKAIFAKAIFAGUANLI(paramMap);
    }

    public void callinsertKAIFATONGKUO(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertKAIFATONGKUO(paramMap);
    }

    public void callinsertCHINAZHIPINKAIFA(String project,String category){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.callinsertCHINAZHIPINKAIFA(paramMap);
    }

    public List<Project> getProjectDepartmentByProjectID(int projectID){
        return projectMapper.getProjectDepartmentByProjectID(projectID);
    }
    
    public List<Project> getProjectDepartmentByProjectIDCXEE(int projectID){
        return projectMapper.getProjectDepartmentByProjectIDCXEE(projectID);
    }

    public List<String> listCategorySelect(){
        return projectMapper.listCategorySelect();
    }

    public List<String> listProjectClientSelect(){
        return projectMapper.listProjectClientSelect();
    }

    public List<String> listFunctionListSelect(){
        return projectMapper.listFunctionListSelect();
    }
    public List<String> listFunctionListSelectCT(){
        return projectMapper.listFunctionListSelectCT();
    }
    public List<Project> listAllStartupProject(int staffID){
        return projectMapper.listAllStartupProject(staffID);
    }

    public List<Project> getProjectByDepartment(String departmentID,String category){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("departmentID", departmentID);
        paraMap.put("category", category);
        return projectMapper.getProjectByDepartment(paraMap);
    }

    public List<Project> listProject(){
        return projectMapper.listProject();
    }

    public List<Project> showAllProject(){//黑名单导出所有项目
        return projectMapper.showAllProject();
    }

    @Override
    public void calldeletePB(String project, String category) {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.calldeletePB(paramMap);
    }
    
    @Override
    public void calldeletePBCXEE(String project, String category) {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        projectMapper.calldeletePBCXEE(paramMap);
    }

    @Override
    public void callinsertProjectBranch(String project, String category,String branch) {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        paramMap.put("branch", branch);
        projectMapper.callinsertProjectBranch(paramMap);
    }
    
    @Override
    public void callinsertProjectBranchCXEE(String project, String category,String branch) {
        // TODO Auto-generated method stub
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("project", project);
        paramMap.put("category", category);
        paramMap.put("branch", branch);
        projectMapper.callinsertProjectBranchCXEE(paramMap);
    }

    public List<Project> getProjectBranchByProjectID(int projectID){
        return projectMapper.getProjectBranchByProjectID(projectID);
    }

    /**
     * 根据页面请求参数列出项目名(仅对应项目课别搜索时)
     */
    public List<Project> showAllForBranch(ProjectReqParam projectReqParam){
        return projectMapper.showAllForBranch(projectReqParam);
    }

    /**
     * 根据页面请求得到分页页数(仅对应项目课别搜索)
     */
    public int totalPageCountForBranch(ProjectReqParam projectReqParam){
        return projectMapper.totalPageCountForBranch(projectReqParam);
    }

    /**
     * 列出最近填写项目外其他项目信息
     */
    public List<Project> listSelectProjectByBranchID(int staffID,String departmentID, String branchID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        params.put("branchID", branchID);
        return projectMapper.listAllProjectByBranchID(params);
    }

    /**
     * 列出最近填写的项目信息
     */
    public List<Project> recentProjectByBranchID(int staffID,String departmentID, String branchID) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("staffID", staffID);
        params.put("departmentID", departmentID);
        params.put("branchID", branchID);
        return projectMapper.recentProjectByBranchID(params);
    }

	@Override
	public int getProjectIDByProjectName(String projectName) {
		return projectMapper.getProjectIDByProjectName(projectName);
	}
	
	public int totalPageCountCT(ProjectReqParam projectReqParam){
		return projectMapper.totalPageCountCT(projectReqParam);
	}
	
	public List<Project> showAllCT(ProjectReqParam projectReqParam){
		
		return projectMapper.showAllCT(projectReqParam);
	}
	
	@Override
	public List<Project> getAllProClientName() {
		// TODO Auto-generated method stub
		return projectMapper.getAllProClientName();
	}
	
	public void deletePDByProjectID(Integer projectID){
        projectMapper.deletePDByProjectID(projectID);
    }
	
	public void deletePDByProjectIDCT(Integer projectID){
        projectMapper.deletePDByProjectIDCT(projectID);
    }
	
	public void insertPDByProjectID(Integer projectID,Integer departmentID){
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectID", projectID);
        params.put("departmentID", departmentID);
        projectMapper.insertPDByProjectID(params);
	}
	
	public void insertPDByProjectIDCT(Integer projectID,Integer departmentID){
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectID", projectID);
        params.put("departmentID", departmentID);
        projectMapper.insertPDByProjectIDCT(params);
	}
	
	@Override
	public List<Project> getAllProFunction() {
		// TODO Auto-generated method stub
		return projectMapper.getAllProFunction();
	}
	@Override
	public List<Project> getAllProFunctionCT() {
		// TODO Auto-generated method stub
		return projectMapper.getAllProFunctionCT();
	}
	public List<Project> downLoadShowAllProject(){
		return projectMapper.downLoadShowAllProject();
	}
	
	@Override
	public List<Project> getAllProCarMaker() {
		// TODO Auto-generated method stub
		return projectMapper.getAllProCarMaker();
	}
	@Override
	public List<Project> getAllProCarMakerCT() {
		// TODO Auto-generated method stub
		return projectMapper.getAllProCarMakerCT();
	}
	@Override
	public List<Project> getAllTransferNo() {
		// TODO Auto-generated method stub
		return projectMapper.getAllTransferNo();
	}
	
	
	@Override
	public List<Project> downLoadShowAllProjectCT(){
		return projectMapper.downLoadShowAllProjectCT();
	}
	@Override
	public String downloadProject(List<Project> projectList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "ProjectListCXEE_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.PROJECT_TEMPLATE_CXEE).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "ProjectInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < projectList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				System.out.println(HSSFWorkbook.class.getProtectionDomain().getCodeSource().getLocation());
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(projectList.get(i).getProjectName());//项目名称
				row.getCell(2).setCellValue(projectList.get(i).getCategory());//开发阶段
				row.getCell(3).setCellValue(projectList.get(i).getProjectClientNo());//依赖号
				row.getCell(4).setCellValue(projectList.get(i).getProjectClientName());//依赖方
				row.getCell(5).setCellValue(projectList.get(i).getStartupDate());//启动时间
				row.getCell(6).setCellValue(projectList.get(i).getFinishDate());//结束时间
				row.getCell(7).setCellValue(projectList.get(i).getProjectState());//项目状态
				row.getCell(8).setCellValue(projectList.get(i).getCarMaker());//车厂
				row.getCell(9).setCellValue(projectList.get(i).getProjectDepartment());//受托部门
				row.getCell(10).setCellValue(projectList.get(i).getProjectBranch());//受托课别
				row.getCell(11).setCellValue(projectList.get(i).getFunction());//项目机能
				row.getCell(12).setCellValue(projectList.get(i).getModel());//机种
				row.getCell(13).setCellValue(projectList.get(i).getTransferNo());//管理项番
				row.getCell(14).setCellValue(projectList.get(i).getItemName());//3D项番名
				row.getCell(15).setCellValue(projectList.get(i).getPJNo());//PJ No.
				row.getCell(16).setCellValue(projectList.get(i).getTempPJNo());//仮PJ No.
				row.getCell(17).setCellValue(projectList.get(i).getPJName());//PJ名
				row.getCell(18).setCellValue(projectList.get(i).getMemo());//备注
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
	
	@Override
	public List<Stage> getAllStage() {
		// TODO Auto-generated method stub
		return projectMapper.getAllStage();
	}
	@Override
	public List<Stage> getAllCategoryCT() {
		// TODO Auto-generated method stub
		return projectMapper.getAllCategoryCT();
	}
	
	public String downloadProjectCT(List<Project> projectList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "ProjectListCT_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.PROJECT_TEMPLATE_CT).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "ProjectInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < projectList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				System.out.println(HSSFWorkbook.class.getProtectionDomain().getCodeSource().getLocation());
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(projectList.get(i).getPJNo());//
				row.getCell(2).setCellValue(projectList.get(i).getTempPJNo());//开发阶段
				row.getCell(3).setCellValue(projectList.get(i).getPJName());//依赖号
				row.getCell(4).setCellValue(projectList.get(i).getCategory());//依赖方
				row.getCell(5).setCellValue(projectList.get(i).getTransferNo());//启动时间
				row.getCell(6).setCellValue(projectList.get(i).getItemName());//结束时间
				row.getCell(7).setCellValue(projectList.get(i).getStartupDate());//项目状态
				row.getCell(8).setCellValue(projectList.get(i).getFinishDate());//车厂
				row.getCell(9).setCellValue(projectList.get(i).getProjectState());//受托部门
				row.getCell(10).setCellValue(projectList.get(i).getCarMaker());//受托课别
				row.getCell(11).setCellValue(projectList.get(i).getEnterpriseSeg());//项目机能
				row.getCell(12).setCellValue(projectList.get(i).getFunction());//机种
				row.getCell(13).setCellValue(projectList.get(i).getProjectDepartment());//受托部门
				row.getCell(14).setCellValue(projectList.get(i).getMemo());//备注
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
	
	public int totalPageCountCommon(ProjectReqParam projectReqParam){
		return projectMapper.totalPageCountCommon(projectReqParam);
	}
	
	public int totalPageCountCommonCT(ProjectReqParam projectReqParam){
		return projectMapper.totalPageCountCommonCT(projectReqParam);
	}
	
	public List<Project> showAllCommon(ProjectReqParam projectReqParam){
		return projectMapper.showAllCommon(projectReqParam);
	}
	public List<Project> showAllCommonCT(ProjectReqParam projectReqParam){
		return projectMapper.showAllCommonCT(projectReqParam);
	}
	public List<Project> downLoadShowAllProjectCommon(){
		return projectMapper.downLoadShowAllProjectCommon();
	}
	
	public String downloadProjectCommon(List<Project> projectList, String realpathString){
		Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = "ProjectListCommon_"
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
			file = new File(StaffServiceImpl.class.getResource(Parma.PROJECT_TEMPLATE_COMMON).getFile());
			fs = new POIFSFileSystem(new FileInputStream(file.getPath()));
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			wb.setSheetName(0, "ProjectInfo");
			HSSFRow row = null;
			
			//获取模板式样
			row = null;
			row = sheet.getRow(2);
			//style = row.getCell(1).getCellStyle();
			for(int i=0; i < projectList.size(); i++){
				if(i == 0 ){
				}else{
					ExportClass.copyRows(sheet, sheet, 2, 2, curruntRow);
				}
				row = sheet.getRow(curruntRow++);
				System.out.println(HSSFWorkbook.class.getProtectionDomain().getCodeSource().getLocation());
				row.getCell(0).setCellValue(i+1);//序号
				row.getCell(1).setCellValue(projectList.get(i).getPJNo());//PJNo
				row.getCell(2).setCellValue(projectList.get(i).getTempPJNo());//仮PJ No.
				row.getCell(3).setCellValue(projectList.get(i).getPJName());//PJ名
				row.getCell(4).setCellValue(projectList.get(i).getTransferNo());//3D
				row.getCell(5).setCellValue(projectList.get(i).getItemName());//アイテム名称
				row.getCell(6).setCellValue(projectList.get(i).getCategory());//開発段階
				row.getCell(7).setCellValue(projectList.get(i).getProjectName());//项目名称
				row.getCell(8).setCellValue(projectList.get(i).getProjectClientNo());//依赖号
				row.getCell(9).setCellValue(projectList.get(i).getProjectClientName());//依赖方
				row.getCell(10).setCellValue(projectList.get(i).getStartupDate());//開発開始日
				row.getCell(11).setCellValue(projectList.get(i).getFinishDate());//開発終了日
				row.getCell(12).setCellValue(projectList.get(i).getProjectState());//プロジェクト状態
				row.getCell(13).setCellValue(projectList.get(i).getCarMaker());//メーカー
				row.getCell(14).setCellValue(projectList.get(i).getEnterpriseSeg());//事業セグメント
				row.getCell(15).setCellValue(projectList.get(i).getFunction());//共通呼称
				row.getCell(16).setCellValue(projectList.get(i).getProjectDepartment());//受托部门
				row.getCell(17).setCellValue(projectList.get(i).getProjectBranch());//開発課名
				row.getCell(18).setCellValue(projectList.get(i).getModel());//機種名
				row.getCell(19).setCellValue(projectList.get(i).getMemo());//备注
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

	public void deleteProBranchByProjectID(Integer projectID){
		projectMapper.deleteProBranchByProjectID(projectID);
	}
	public void insertProBranchByProjectID(Integer projectID,Integer branchID){
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectID", projectID);
        params.put("branchID", branchID);
		projectMapper.insertProBranchByProjectID(params);
	}
	
	public List<Project> getProjectDepartmentByProjectIDCT(int projectID){
        return projectMapper.getProjectDepartmentByProjectIDCT(projectID);
    }
	
	public List<Project> getProjectBranchByProjectIDCT(int projectID){
		return projectMapper.getProjectBranchByProjectIDCT(projectID);
	}

	@Override
	public List<Project> getAllPJNo() {
		// TODO Auto-generated method stub
		return projectMapper.getAllPJNo();
	}

	@Override
	public List<Project> projectNameSelect(String PJNoTempPJNo) {
		// TODO Auto-generated method stub
		return projectMapper.getPJName(PJNoTempPJNo);
	}

	@Override
	public List<Project> getAllTempPJNo() {
		// TODO Auto-generated method stub
		return projectMapper.getAllTempPJNo();
	}

	@Override
	public List<Project> projectNameSelectByTempPJNo(String tempPJNo) {
		// TODO Auto-generated method stub
		return projectMapper.getPJNameByTempPJNo(tempPJNo);
	}

	@Override
	public List<Map<String, Object>> getAllBranch() {
		// TODO Auto-generated method stub
		return projectMapper.getAllBranch();
	}

	@Override
	public List<Project> projectNameSelectCXEE(String PJNo) {
		// TODO Auto-generated method stub
		return projectMapper.getPJNameCXEE(PJNo);
	}
	
}
