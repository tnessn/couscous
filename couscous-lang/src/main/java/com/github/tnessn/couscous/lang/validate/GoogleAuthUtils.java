package com.github.tnessn.couscous.lang.validate;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;


/**
 * 
 * @author huangjinfeng
 */
public class GoogleAuthUtils {
	
	private static  GoogleAuthenticator gAuth = new GoogleAuthenticator();
    
    /**
     * 生成google auth key
     * @return google auth key
     */
    public static String genGoogleAuthKey() {
        GoogleAuthenticatorKey credentials  =  gAuth.createCredentials();
        return credentials.getKey();
    }
    
    
    public static boolean isCodeValid(String key,int code) {
    	return gAuth.authorize(key, code);
    }
    
    public static void main(String[] args) {
    	//生成google auth key
		String key=GoogleAuthUtils.genGoogleAuthKey();
		System.out.println(key);
		
		//生成6位数密码
		int code = gAuth.getTotpPassword(key);
		System.out.println(code);
		
		//根据key验证6位数密码是否有效
		boolean isCodeValid = gAuth.authorize(key, code);
		System.out.println(isCodeValid);
		
	}

}
