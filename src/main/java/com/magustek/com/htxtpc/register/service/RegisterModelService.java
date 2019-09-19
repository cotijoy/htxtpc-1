package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.CaptchaConfig;
import com.magustek.com.htxtpc.register.bean.RegisterModel;

import java.util.Map;

public interface RegisterModelService {

    Map<String,Object> register(RegisterModel registerModel) throws Exception ;
    String sendEmailCaptchaAndVerifyUser(String userName, String email);
    String emailCaptchaVerify(String email, String captcha);
    void updatePassword(String username, String email, String password);
    Map<String,Object> userlogin(String username, String password) throws Exception;
    Map<String,Object> usernameCheck(String username) throws Exception;
    Map<String,Object> findRegisterModel(String username, String password) throws Exception;
    /*Map<String,Object> registerAudit(String )*/  //未完待续
}
