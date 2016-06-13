package com.clarion.worksys.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import com.clarion.worksys.entity.Blacklist;
import com.clarion.worksys.entity.BlacklistCT;

/**
 * 给黑名单人员发送邮件
 * 
 * @author guo_renpeng
 * 
 */
public class BlackListMail {
	private List<Blacklist> blacklist;
	private List<BlacklistCT> blacklistCT;                                         // 黑名单List
	private final String HOST     = "133.144.234.6";                           // 邮箱主机地址
	private final String SENDER   = "CXEE_MAN_HOUR_SYSTEM@clarion.com.cn";     // 邮件发送人Email
	private final String SENDERCT = "ct_man_hour_system@clarion.co.jp";        // CT侧邮件发送人Email
	//private final String PASSWORD = "70704688";                                // 邮件发送人Email 密码
	private final String PASSWORD = "";                                        // 邮件发送人Email 密码
	private final String SUBJECT  = "工数填写异常!";
	private final String SUBJECTCT  = "工数管理システム　入力依頼";                            // 邮件主题
	private SendMail sendMail;                                                 // 发送邮件工具类
	private Map<String, String> contentMap;                                    // 存放收件人Email和正文Map
	private final String START    = "亲,辛苦了! 您的工数填写未完整,系统显示异常,请确认! 具体如下:\n";                                   //邮件正文开头
	private final String STARTCT    = "いつも工数管理にご協力頂き、誠にありがとうございます。\n このメールを受信した方は、今月の実績工数が";
	private final String END      = "\n\n您的填写工数要大于等于8小时.(请假出差加班等也要填)"
			+ "\n\n请收到此通知后，尽快填写工数。如有疑义，请与开发管理部工数担当确认！"
			+ "\n\n注：该邮件为系统自动发送，不支持接收任何邮件，请勿直接回复。"
			+ "\n工数系统地址：http://10.96.232.22:8088/login.do。"
			+ "\n本メールは自動転送になっており、全てのメールの受け取り対応ができないため、返信しないようお願いいたします。";
	
	private final String ENDCT      = "\n※計算方法：（実稼働日）＊（7.75時間）" +
			                          "\n※休暇、時短分等も必ず工数をご入力ください。"+
			                          "\n\nお早めに実績工数の入力をお願いいたします。"+
			                          "\n\n本件に関するお問い合わせは、技術開発推進部　工数管理担当者までお願いいたします。"+
			                          "\n\n【本メールへの返信禁止】"+
			                          "\n本メールはシステムより自動配信されています。本メールには返信しないようお願いいたします。"+
			                          "\n\n工数集計システムのURL: http://10.96.232.22:8088/login.do";
	                                
	public BlackListMail() {
		contentMap = new HashMap<String, String>();   
	}
	
	/**
	 * 设置黑名单List
	 * @param blacklist 黑名单List
	 */
	public void setBlacklist(List<Blacklist> blacklist) {
		this.blacklist = blacklist;
	}
	
	public void setBlacklistCT(List<BlacklistCT> blacklistCT) {
		this.blacklistCT = blacklistCT;
	}
	
	/**
	 * 给黑名单发送邮件
	 * @return
	 */
	public int sendMailToBlackList(){
		for (Blacklist black : blacklist) {
			if(!contentMap.containsKey(black.getStaffEmail())){
				float totalTime;
				if(black.getTotalTimes() == null){
					totalTime = 0;
				}else{
					totalTime = Float.parseFloat(black.getTotalTimes());
				}
				String content = START+black.getDate()+"工数填写"+totalTime+"小时";
				contentMap.put(black.getStaffEmail(),content );
			}else{
				float totalTime;
				if(black.getTotalTimes() == null){
					totalTime = 0;
				}else{
					totalTime = Float.parseFloat(black.getTotalTimes());
				}
				String content = contentMap.get(black.getStaffEmail())+"\n"+black.getDate()+"工数填写"+totalTime+"小时";
				contentMap.put(black.getStaffEmail(), content);
			}
		}
		Set set = contentMap.keySet();
		try {
			sendMail = new SendMail(HOST, SENDER, PASSWORD,SUBJECT);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Object object : set) {
			if(object != null){
				System.out.println("******************************************************");
				System.out.println(object);
				System.out.println(contentMap.get(object));
				System.out.println("******************************************************");
				sendMail.setContent(contentMap.get(object)+END);
				sendMail.setReceiver(object.toString());
				try {
					sendMail.sendEmail();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		try {
			sendMail.closeTransport();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set.size();
	}
	
	/**
	 * 给黑名单发送邮件
	 * @return
	 */
	public int sendMailToBlackListCT(double totalworkhour){
		for (BlacklistCT black : blacklistCT) {
			double temp;
			if(black.getTotalTimes() == null){
				temp = 0;
			}else{
				temp =  Double.parseDouble(black.getTotalTimes());
			}
			
			if (temp<totalworkhour ){
				//if(!contentMap.containsKey(black.getStaffEmail())){
				float totalTime;
				if(black.getTotalTimes() == null){
					totalTime = 0;
				}else{
					totalTime = Float.parseFloat(black.getTotalTimes());
				}
				String content = STARTCT+totalworkhour+"時間未満になっています。";
				contentMap.put(black.getStaffEmail(),content );
				//}else{
					//float totalTime;
					//if(black.getTotalTimes() == null){
					//	totalTime = 0;
				//	}else{
						//totalTime = Float.parseFloat(black.getTotalTimes());
					//}
				//	String content = contentMap.get(black.getStaffEmail())+"\n"+black.getDate()+"工数填写"+totalTime+"小时";
				//	contentMap.put(black.getStaffEmail(), content);
				//}
			}
		}
		Set set = contentMap.keySet();
		try {
			sendMail = new SendMail(HOST, SENDERCT, PASSWORD,SUBJECTCT);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Object object : set) {
			if(object != null){
				System.out.println("******************************************************");
				System.out.println(object);
				System.out.println(contentMap.get(object));
				System.out.println("******************************************************");
				sendMail.setContent(contentMap.get(object)+ENDCT);
				sendMail.setReceiver(object.toString());
				try {
					sendMail.sendEmail();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		try {
			sendMail.closeTransport();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set.size();
	}

}
