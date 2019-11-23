package com.github.tnessn.couscous.lang.mail;

/**   

 */
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


// TODO: Auto-generated Javadoc
/**
 * 简单邮件（不带附件的邮件）发送器.
 *
 * @author huangjinfeng
 */
public class SimpleMailSender  {
	
	
	/**
	 * 以文本格式发送邮件.
	 *
	 * @param mailInfo the mail info
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);
		// 发送邮件
		Transport.send(mailMessage);

		return true;
	}

	
	/**
	 * 以HTML格式发送邮件.
	 *
	 * @param mailInfo 待发送的邮件信息
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	/**
	 * Send mail.
	 *
	 * @param title 标题
	 * @param content 内容
	 * @param type 1:文本格式;2:html格式
	 * @param toEmail 接收的邮箱
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendMail(String title, String content, String type, String toEmail) throws Exception {

		// 这个类主要是设置邮件

		// TODO: 改到配置文件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("jinfeng289@qq.com");
		
		// 您的邮箱密码
		mailInfo.setPassword("fnpziaguzkhcdcgh");
		mailInfo.setFromAddress("jinfeng289@qq.com");
		mailInfo.setToAddress(toEmail);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件

		SimpleMailSender sms = new SimpleMailSender();

		if ("1".equals(type)) {
			// 发送文体格式
			return sms.sendTextMail(mailInfo);
		} else if ("2".equals(type)) {
			// 发送html格式
			return sms.sendHtmlMail(mailInfo);
		}
		return false;
	}

	/**
	 * Send email.
	 *
	 * @param smtp            邮件服务器
	 * @param port            端口
	 * @param email            本邮箱账号
	 * @param password            本邮箱密码
	 * @param toEmail            对方箱账号
	 * @param title            标题
	 * @param content            内容
	 * @param type            1：文本格式;2：HTML格式
	 * @throws Exception the exception
	 */
	public static void sendEmail(String smtp, String port, String email, String password, String toEmail, String title, String content,
			String type) throws Exception {

		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setMailServerHost(smtp);
		mailInfo.setMailServerPort(port);
		mailInfo.setValidate(true);
		mailInfo.setUserName(email);
		mailInfo.setPassword(password);
		mailInfo.setFromAddress(email);
		mailInfo.setToAddress(toEmail);
		mailInfo.setSubject(title);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件

		SimpleMailSender sms = new SimpleMailSender();

		if ("1".equals(type)) {
			sms.sendTextMail(mailInfo);
		} else {
			sms.sendHtmlMail(mailInfo);
		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// 这个类主要是设置邮件
		for(int i=0;i<3;i++) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("jinfeng289@qq.com");
		// 您的邮箱密码
		mailInfo.setPassword("fnpziaguzkhcdcgh");
		mailInfo.setFromAddress("jinfeng289@qq.com");
		mailInfo.setToAddress("1005886607@qq.com");
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("<table border='1'> <tr> <th>Month</th> <th>Savings</th> </tr> <tr><td>January</td><td>$100</td></tr></table>");
		// 这个类主要来发送邮件

		 SimpleMailSender sms = new SimpleMailSender();
		//sms.sendTextMail(mailInfo);//发送文体格式
		//发送html格式
		 sms.sendHtmlMail(mailInfo);
		}
	}

}
