package com.github.tnessn.couscous.lang;


// TODO: Auto-generated Javadoc
/**
 * 业务异常.
 *
 * @author huangjinfeng
 */
public class BusinessException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The code. */
    private String code;
    
    /** The msg. */
    private String msg;
    
    /**
     * Instantiates a new business exception.
     *
     * @param msg the msg
     */
    public BusinessException(String msg) {
        super();
        this.msg = msg;
    }

    /**
     * Instantiates a new business exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public BusinessException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code the new code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the msg.
     *
     * @param msg the new msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}