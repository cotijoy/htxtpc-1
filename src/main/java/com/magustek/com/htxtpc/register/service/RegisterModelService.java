package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface RegisterModelService extends UserDetailsService {

    Map<String,Object> register(RegisterModel registerModel) throws Exception ;
    String sendEmailCaptchaAndVerifyUser(RegisterHeader registerHeader);
    String emailCaptchaVerify(RegisterHeaderVO vo);
    void updatePassword(RegisterHeader registerHeader);
    Map<String,Object> userlogin(String username, String password) throws Exception;
    Map<String,Object> usernameCheck(String username) throws Exception;
    Map<String,Object> findRegisterModel(String username, String password) throws Exception;
    PreRegisterHeader userLogin(String username, String password);
    /*Map<String,Object> registerAudit(String )*/  //未完待续
}
