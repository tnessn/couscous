package com.github.tnessn.couscous.lang.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


// TODO: Auto-generated Javadoc
/**
 * 邮件发送验证信息.
 *
 * @author huangjinfeng
 */
public class MyAuthenticator extends Authenticator{   
    
    /** The user name. */
    String userName=null;   
    
    /** The password. */
    String password=null;   
        
    /**
     * Instantiates a new my authenticator.
     */
    public MyAuthenticator(){   
    }   
    
    /**
     * Instantiates a new my authenticator.
     *
     * @param username the username
     * @param password the password
     */
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    
    /* (non-Javadoc)
     * @see javax.mail.Authenticator#getPasswordAuthentication()
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}   
