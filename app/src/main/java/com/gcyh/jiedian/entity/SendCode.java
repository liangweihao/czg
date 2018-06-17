package com.gcyh.jiedian.entity;

/**
 * Created by caizhiguang on 2018/5/21.
 */

public class SendCode {
    /**
     * responseParams : null
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 成功
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
    //短信验证码实体类

}
