package com.magustek.com.htxtpc.register.controller;


import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.service.impl.RegisterModelServiceImpl;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api("用户注册")
@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterModelServiceImpl registerModelService;


    public Map<String, Object> register (HttpServletRequest request, RegisterModel registerModel) {
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

}
