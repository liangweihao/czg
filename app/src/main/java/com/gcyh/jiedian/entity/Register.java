package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/5/22.
 */

public class Register {


    /**
     * responseParams : null
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 注册成功！
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
