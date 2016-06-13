package com.clarion.worksys.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**   
 * CSV导出
 *
 * @author HSCN liuy
 * @version 1.0 
 */
public class DataToCSV {
    
    /**
     * 导出
     * @param user 出力者
     * @param realpath csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param csvTitle csv数据的标题
     * @param dataList csv数据
     * @return
     */
    public static String exportCsv(String user, String realpath,String[] csvTitle, List<String> dataList){
    	
    	Calendar c1 = Calendar.getInstance();//获得系统当前日期
    	Date date = c1.getTime();
        String filenameString = "CSV_"
        					  + String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String file = realpath + "\\" + filenameString + ".csv";
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"UTF-8");
            osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF },"UTF-8"));
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
            	bw.append("工数実績データ").append("\r");//标题行
            	bw.append("\r");//空白行
            	bw.append("出力者："+user+"  出力日："+df.format(date)).append("\r");//出力者和出力时间
            	String tempTitle = "";
            	for(int i = 0; i < csvTitle.length; i++){
            		if (i == 0){
            			tempTitle = '"' + csvTitle[0] + '"';
            		} else {
            			tempTitle = tempTitle + "," + '"' + csvTitle[i] + '"';
            		}
            	}
            	bw.append(tempTitle).append("\r");
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        String webaddrString="/temp/"+filenameString+".csv";
        return webaddrString;
    }

    /**
     * 导出_ForCt
     * @param user 出力者
     * @param realpath csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param csvTitle csv数据的标题
     * @param dataList csv数据
     * @return
     */
    public static String exportCsv_ForCt(String user, String realpath,String[] csvTitle, List<String> dataList){
    	
    	Calendar c1 = Calendar.getInstance();//获得系统当前日期
    	Date date = c1.getTime();
        String filenameString = "CSV_"
        					  + String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String file = realpath + "\\" + filenameString + ".csv";
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"UTF-8");
            osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF },"UTF-8"));
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
            	bw.append("工数実績データ").append("\r");//标题行
            	bw.append("\r");//空白行
            	bw.append("出力者："+user+"  出力日："+df.format(date)).append("\r");//出力者和出力时间
            	String tempTitle = "";
            	for(int i = 0; i < csvTitle.length; i++){
            		if (i == 0){
            			tempTitle = '"' + csvTitle[0] + '"';
            		} else {
            			tempTitle = tempTitle + "," + '"' + csvTitle[i] + '"';
            		}
            	}
            	bw.append(tempTitle).append("\r");
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        String webaddrString="/temp/"+filenameString+".csv";
        return webaddrString;
    }
}