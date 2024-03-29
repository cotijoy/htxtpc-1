package com.magustek.com.htxtpc.util.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * REST api 接口（controller）专用包装类
 *
 * */
public class BaseResponse {

    public static final int SUCCESS = 200;      //成功
    public static final int ONEMORECOMPANY = 260;    //有多个公司
    public static final int ERROR = 600;        //出错
    public static final int REDIRECT = 601;     //跳转
    public static final int EMPTY = 602;        //无数据
    public static final int NOTLOGIN = 603;    //未登录
    public static final int FORBIDDEN = 604;    //无权限


    private Object data;
    private int stateCode = SUCCESS;
    private String msg = "";

    public String toJson(){
        return JSONObject.toJSONString(this);
    }

    //禁止检测循环引用
    public String toJSONUncheck(){
        return JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    public Object getData() {
        return data;
    }

    public BaseResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public int getStateCode() {
        return stateCode;
    }

    public BaseResponse setStateCode(int stateCode) {
        this.stateCode = stateCode;
        /**
         * 由于springMVC的controller 使用单例模式，response会一直存在内存中，
         * 因此在设置baseResponse时需要先设置stateCode，并同时清空data、msg变量。
         * */
        this.data = null;
        this.msg = "";
        return this;
    }

    public BaseResponse changeStateCode(int stateCode){
        this.stateCode = stateCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
