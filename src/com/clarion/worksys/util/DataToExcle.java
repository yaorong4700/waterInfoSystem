package com.clarion.worksys.util;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DataToExcle {

    public String toExcel(List list,String realpath) {

        Calendar c1=Calendar.getInstance();//获得系统当前日期
        String filenameString = String.valueOf(c1.get(Calendar.YEAR))
                              + String.valueOf(c1.get(Calendar.MONTH) + 1)  //系统日期从0开始算起
                              + String.valueOf(c1.get(Calendar.DAY_OF_MONTH))
                              + String.valueOf(c1.get(Calendar.HOUR_OF_DAY))
                              + String.valueOf(c1.get(Calendar.MINUTE))
                              + String.valueOf(c1.get(Calendar.SECOND));
        String strnameString2 = realpath + "\\" + filenameString + ".xls";

        //1.新建一个excel表格
        //从TransDate 转化List 得到的是String数组的List
        //将Title数组和String数组写入Excel
        //返回文件路径
        String webaddrString  = "/temp/" + filenameString + ".xls";

        try {

            TransDate transDate = new TransDate();
            try {
                //从TransDate 转化List 得到的是String数组的List
                List<String[]> listdata = transDate.getDateList(list);
                WritableWorkbook book   = Workbook.createWorkbook(new File(strnameString2));
                WritableSheet sheet     = book.createSheet("首页", 0);

                    for (int j = 0; j < listdata.get(0).length; j++) {
                        int maxlength=0;
                        for (int i = 0; i < listdata.size(); i++) {
                            WritableFont font1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
                            WritableCellFormat format1=new WritableCellFormat(font1);
                            format1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                            format1.setAlignment(jxl.format.Alignment.CENTRE);

                            boolean isNum = isNumeric(listdata.get(i)[j]);
                            if (isNum==true&&(!listdata.get(i)[j].equals(""))) {
                                /* jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");    //设置数字格式
                                jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); //设置表单格式
                                jxl.write.Number labelNF = new jxl.write.Number(j, i, Double.valueOf(listdata.get(i)[j]),wcfN); //格式化数值
                                sheet.addCell(labelNF); */
                                sheet.addCell(new Number(j, i, Double.valueOf(listdata.get(i)[j]), format1));
                            }
                            else {
                                Label label1 = new Label(j, i, listdata.get(i)[j],format1);
                                sheet.addCell(label1);
                            }
                            //System.out.println(j+"hahahahah"+listdata.get(i)[j]+"++"+i);
                            if (listdata.get(i)[j].length()*2>maxlength) {
                                maxlength=listdata.get(i)[j].length()*2;
                            }

                        }
                        sheet.setColumnView(j,maxlength);
                    }
                    //写Excel表格一行
                    book.write();
                    //close file case.
                    book.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webaddrString;
    }

    public String toExcel(String[] title, List list,String realpath){
        Calendar c1=Calendar.getInstance();//获得系统当前日期
        int yearsys=c1.get(Calendar.YEAR);
        int monthsys=c1.get(Calendar.MONTH)+1;//系统日期从0开始算起
        int daysys=c1.get(Calendar.DAY_OF_MONTH);
        int hoursys=c1.get(Calendar.HOUR_OF_DAY);
        int minutessys=c1.get(Calendar.MINUTE);
        int secondsys=c1.get(Calendar.SECOND);
        String filenameString=String.valueOf(yearsys)+String.valueOf(monthsys)
        +String.valueOf(daysys)+String.valueOf(hoursys)+String.valueOf(minutessys)+String.valueOf(secondsys);
        String strnameString2=realpath+"/"+filenameString+".xls";
        try {

            TransDate transDate = new TransDate();
            try {
                //从TransDate 转化List 得到的是String数组的List
                List<String[]> listdata =  transDate.getDateList(title,list);
                WritableWorkbook book = Workbook.createWorkbook(new File(strnameString2));
                WritableSheet sheet = book.createSheet("首页", 0);

                    //将Title数组和String数组写入Excel
                    for (int i = 0; i < title.length; i++) {

                        WritableFont font1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
                        WritableCellFormat format1=new WritableCellFormat(font1);
                        format1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                        format1.setAlignment(jxl.format.Alignment.CENTRE);
                        Label label1 = new Label(i, 0, title[i],format1);
                        sheet.addCell(label1);

                        //System.out.println(title[i]);
                    }
                    for (int j = 0; j < title.length; j++) {
                        int maxlength=title[j].length()*2;
                        for (int i = 0; i < listdata.size(); i++) {
                            WritableFont font1= new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
                            WritableCellFormat format1=new WritableCellFormat(font1);
                            format1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
                            format1.setAlignment(jxl.format.Alignment.CENTRE);
                            /*Label label1 = new Label(j, i+1, listdata.get(i)[j],format1);
                            sheet.addCell(label1);*/
                            boolean isNum = isNumeric(listdata.get(i)[j]);
                            if (isNum==true) {
                                Number label2=new Number(j, i, Double.valueOf(listdata.get(i)[j]),format1);
                                sheet.addCell(label2);
                            }
                            else {
                                Label label1 = new Label(j, i, listdata.get(i)[j],format1);
                                sheet.addCell(label1);
                            }

                            if (listdata.get(i)[j].length()*2>maxlength) {
                                maxlength=listdata.get(i)[j].length()*2;
                            }

                        }
                        sheet.setColumnView(j,maxlength);
                    }
                        //写Excel表格一行
                    book.write();
                    //close file case.
                    book.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //1.新建一个excel表格
        //从TransDate 转化List 得到的是String数组的List
        //将Title数组和String数组写入Excel
        //返回文件路径
        String webaddrString="/temp/"+filenameString+".xls";
        return webaddrString;
    }

    public static boolean isNumeric(String str){
        String temp=str.replace(".", "");
        for(int i=temp.length();i>0;i--) {
           int chr=temp.charAt(i-1) ;
           if(chr<48 || chr>57)
              return false;
        }
        return true;
    }
}
