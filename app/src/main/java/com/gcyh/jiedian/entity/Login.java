package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/5/22.
 */

public class Login {


    /**
     * responseParams : eyJhbGciOiJIUzI1NiIsInVzZXJpZCI6NCwidHlwIjoiSldUIn0.eyJpYXQiOjE1MjY5ODI4ODF9.MXrVreUvtxrzmW6R759E4MgDIUUP5ArTD2hFkhiHqd4
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 登录成功！
     */

    private String responseParams;
    private String rntCode;
    private int rntCodeValue;
    private String rntMsg;

    public String getResponseParams() {
        return responseParams;
    }

    public void setResponseParams(String responseParams) {
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
