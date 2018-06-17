package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/5/30.
 */

public class ForgetPassword {


    /**
     * responseParams : null
     * rntCode : ERROR
     * rntCodeValue : 2
     * rntMsg : 短信验证码错误
     */

    private Object responseParams;
    private String rntCode;
    private int rntCodeValue;
    private String rntMsg;

    public Object getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(Object responseParams) {
        this.responseParams = responseParams;
    }

    public String getRntCode() {
        return rntCode;
    }

    public void setRntCode(String rntCode) {
        this.rntCode = rntCode;
    }

    public int getRntCodeValue() {
        return rntCodeValue;
    }

    public void setRntCodeValue(int rntCodeValue) {
        this.rntCodeValue = rntCodeValue;
    }

    public String getRntMsg() {
        return rntMsg;
    }

    public void setRntMsg(String rntMsg) {
        this.rntMsg = rntMsg;
    }
}
