package com.magustek.com.htxtpc.register.controller;


import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.service.impl.RegisterModelServiceImpl;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import com.magustek.com.htxtpc.util.common.util.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户注册")
@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterModelServiceImpl registerModelService;

    @RequestMapping(value = "/register.do")
    public Map<String, Object> register (HttpServletRequest request, RegisterModel registerModel) throws Exception {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver
                (request.getSession().getServletContext());
        Map<String, Object> result = null;
        if(commonsMultipartResolver.isMultipart(request)) {
           /* MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> list = multipartHttpServletRequest.getFiles("file");*/
            result =  registerModelService.register(registerModel);

        }else {
            log.info("手机号码为：" + registerModel.getPhoneNum() + "注册时缺少附件");
        }
        return result;
    }

    @RequestMapping(value = "/userLogin.do")
    public Map<String, Object> login (HttpServletRequest request, HttpServletResponse response, String username, String password) throws Exception {
        HttpSession session = request.getSession();
        Map<String, Object> registResult = new HashMap<>();
        if (session.getAttribute("user") == null) {  //用户未登录或session已经过期
            registResult = registerModelService.userlogin(username, password);
            if (registResult.get("user") == null) {
                request.getRequestDispatcher("").forward(request, response);  //跳转到注册页面 具体路径未知
            } else {
                Integer status = (Integer) registResult.get("status");
                /*if (status == ResultObject.)) {

                }*/
            }
        }

    }

}
