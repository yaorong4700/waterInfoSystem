package com.clarion.worksys.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件工具类
 * 
 * @author guo_renpeng
 * 
 */
public class SendMail {
	private final static String PROTOCOL = "smtp";
	private final static String PORT = "25";    	                      // SMTP邮件服务器默认端口
	private final static String IS_AUTH = "true";                         // 是否要求身份认证
	private final static String IS_ENABLED_DEBUG_MOD = "true";            // 是否启用调试模式
	private static Properties props = null;                               // 初始化连接邮件服务器的会话信息
	private String host ;                                                 // SMTP邮件服务器
	private String sender;                                                // 发件人Email地址
	private String receiver;                                              // 收件人Email地址
    private String subject;                                               // 邮件主题
    private String password;                                              // 发件人Email 密码
    private String content;                                               // 邮件正文
    private Transport transport;                                          // Transport实例对象 
    private Session session;                                              // Session实例对象 
	
    /**
     * 通过构造函数设置主机地址,发件人,发件人密码,邮件主题
     * @param host
     * @param sender
     * @param password
     * @param subject
     * @throws Exception 
     */
    public  SendMail(String host,String sender,String password,String subject) throws Exception {
        this.host     = host;
        this.password = password;
        this.sender   = sender;
        this.subject  = subject;
        init();
	}
    
    /**
     * 无参构造方法,通过set()方式设置参数
     * @throws Exception 
     */
    public SendMail() throws Exception {
		init();
	}

    /**
     * 设置发件人信息
     * @param sender 发件人Email地址
     * @param password 发件人Email密码
     */
	public void setSenderInfo(String sender,String password) {
		this.sender = sender;
		this.password = password;
	}

	/**
	 * 设置收件人Email地址
	 * @param receiver 收件人Email地址
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
		
	}

	/**
	 * 设置邮件正文
	 * @param content 邮件正文
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 设置主机地址
	 * @param host 主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 设置主题
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception {
		if(host ==null){
   		 throw new Exception("the Parameter is null");
   	    }
		props = new Properties(); 
        props.setProperty("mail.transport.protocol", PROTOCOL); 
        props.setProperty("mail.smtp.host", host); 
        props.setProperty("mail.smtp.port", PORT); 
        props.setProperty("mail.smtp.auth", IS_AUTH); 
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
        session = Session.getDefaultInstance(props); 
        // 获得Transport实例对象 
        transport = session.getTransport(); 
        // 打开连接 
        transport.connect(host,sender, password); 
        
	}
	
	/**
     * 发送邮件
     */ 
    public void sendEmail() throws Exception { 
    	if(host ==null || sender == null || receiver ==null ||subject == null || password ==null || content ==null){
    		 throw new Exception("the Parameter is null");
    	}
        // 创建MimeMessage实例对象 
        MimeMessage message = new MimeMessage(session); 
        // 设置发件人 
        message.setFrom(new InternetAddress(sender)); 
        // 设置邮件主题 
        message.setSubject(subject,"utf-8"); 
        // 设置收件人 
        message.setRecipient(RecipientType.TO, new InternetAddress(receiver)); 
        
        // 设置发送时间 
        message.setSentDate(new Date()); 
        // 设置纯文本内容为邮件正文 
        message.setText(content,"utf-8"); 
        // 保存并生成最终的邮件内容 
        message.saveChanges(); 
        // 将message对象传递给transport对象，将邮件发送出去 
        transport.sendMessage(message, message.getAllRecipients()); 
        // 关闭连接 
       
    } 
    
    public void closeTransport() throws MessagingException {
	     transport.close();
    }

}
