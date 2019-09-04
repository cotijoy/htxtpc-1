package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.CaptchaConfig;
import com.magustek.com.htxtpc.register.bean.RegisterModel;

import java.util.Map;

public interface RegisterModelService {

    Map<String,Object> register(RegisterModel registerModel) ;
    String sendEmailCaptchaAndVerifyUser(String userName, String email);
    String emailCaptchaVerify(String email, String captcha);
    void updatePassword(String username, String password);
}
