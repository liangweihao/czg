package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/6/5.
 */

public class DefaultLogin {


    /**
     * responseParams : eyJhbGciOiJIUzI1NiIsInVzZXJpZCI6OCwidHlwIjoiSldUIiwiZGF0ZSI6MTUzMDc1ODY4Nzk0NX0.eyJpYXQiOjE1MjgxNjY2ODd9.XRNdtOED9rIxrhxRsp3odf22-iy3VOG5Fvn14FhPrhQ
     * rntCode : OK
     * rntCodeValue : 1
     * rntMsg : 成功
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
