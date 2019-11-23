package com.github.tnessn.couscous.lang.mail;

import java.util.Properties;


// TODO: Auto-generated Javadoc
/**
 *  发送邮件需要使用的基本信息.
 *
 */
public class MailSenderInfo {
	
	/** 发送邮件的服务器的IP和端口. */
	private String mailServerHost;
	
	/** The mail server port. */
	private String mailServerPort = "25";
	
	/** 邮件发送者的地址. */
	private String fromAddress;
	
	/**  邮件接收者的地址. */
	private String toAddress;
	
	/**  登陆邮件发送服务器的用户名和密码. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/** 是否需要身份验证. */
	private boolean validate = false;
	
	/** 邮件主题. */
	private String subject;
	
	/**  邮件的文本内容. */
	private String content;
	
	/**  邮件附件的文件名. */
	private String[] attachFileNames;

	
	/**
	 * 获得邮件会话属性.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		p.put("mail.smtp.starttls.enable", "true");
		return p;
	}

	/**
	 * Gets the mail server host.
	 *
	 * @return the mail server host
	 */
	public String getMailServerHost() {
		return mailServerHost;
	}

	/**
	 * Sets the mail server host.
	 *
	 * @param mailServerHost the new mail server host
	 */
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	/**
	 * Gets the mail server port.
	 *
	 * @return the mail server port
	 */
	public String getMailServerPort() {
		return mailServerPort;
	}

	/**
	 * Sets the mail server port.
	 *
	 * @param mailServerPort the new mail server port
	 */
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	/**
	 * Checks if is validate.
	 *
	 * @return true, if is validate
	 */
	public boolean isValidate() {
		return validate;
	}

	/**
	 * Sets the validate.
	 *
	 * @param validate the new validate
	 */
	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	/**
	 * Gets the attach file names.
	 *
	 * @return the attach file names
	 */
	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	/**
	 * Sets the attach file names.
	 *
	 * @param fileNames the new attach file names
	 */
	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	/**
	 * Gets the from address.
	 *
	 * @return the from address
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * Sets the from address.
	 *
	 * @param fromAddress the new from address
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the to address.
	 *
	 * @return the to address
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * Sets the to address.
	 *
	 * @param toAddress the new to address
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param textContent the new content
	 */
	public void setContent(String textContent) {
		this.content = textContent;
	}
}
