package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.CaptchaConfig;
import com.magustek.com.htxtpc.register.bean.RegisterModel;

import java.util.Map;

public interface RegisterModelService {

    Map<String,Object> register(RegisterModel registerModel) throws Exception ;
    Map<String,Object> sendPhoneCaptcha(CaptchaConfig captchaConfig, String phoneNum);
    Map<String,Object> sendEmailCaptcha(CaptchaConfig captchaConfig, String email);
    Map<String,Object> userlogin(String username, String password) throws Exception;
}
