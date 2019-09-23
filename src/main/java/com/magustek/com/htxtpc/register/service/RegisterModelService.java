package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.CaptchaConfig;
import com.magustek.com.htxtpc.register.bean.RegisterHeader;
import com.magustek.com.htxtpc.register.bean.RegisterHeaderVO;
import com.magustek.com.htxtpc.register.bean.RegisterModel;

import java.util.Map;

public interface RegisterModelService {

    Map<String,Object> register(RegisterModel registerModel) throws Exception ;
    String sendEmailCaptchaAndVerifyUser(RegisterHeader registerHeader);
    String emailCaptchaVerify(RegisterHeaderVO vo);
    void updatePassword(RegisterHeader registerHeader);
    Map<String,Object> userlogin(String username, String password) throws Exception;
    Map<String,Object> usernameCheck(String username) throws Exception;
    Map<String,Object> findRegisterModel(String username, String password) throws Exception;
    /*Map<String,Object> registerAudit(String )*/  //未完待续
}
