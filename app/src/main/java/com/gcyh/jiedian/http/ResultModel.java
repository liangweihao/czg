package com.gcyh.jiedian.http;

/**
 * 根据返回的json格式改变
 * @author 蔡志广
 * @date: 2018/4/16
 * @time: 11:39
 */

public class ResultModel<T> {
    public int code;
    public String msg;
    public T data;
}
