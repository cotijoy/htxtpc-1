package com.magustek.com.htxtpc.register.controller;


import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import com.magustek.com.htxtpc.register.bean.RegisterHeader;
import com.magustek.com.htxtpc.register.bean.RegisterHeaderVO;
import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.service.RegisterModelService;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import com.magustek.com.htxtpc.util.common.util.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/mro/register")
public class RegisterController {

    private RegisterModelService registerModelService;
    private BaseResponse resp;

    public RegisterController(RegisterModelService registerModelService) {
        this.registerModelService = registerModelService;
        resp = new BaseResponse();
    }

    @ApiOperation(value="用户注册", notes = "参数：HttpServletRequest, RegisterModel")
    @RequestMapping(value = "/register")
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

    @ApiOperation(value="用户注册时用户名检测唯一性", notes = "参数：username")
    @RequestMapping(value = "/usernameCheck")
    public Map<String, Object> usernameCheck (String username) throws Exception {
        Map<String, Object> registResult = new HashMap<>();
        registResult = registerModelService.usernameCheck(username);
        return registResult;
    }

    @ApiOperation(value="用户登录", notes = "参数：HttpServletRequest, HttpServletResponse, username, password")
    @RequestMapping(value = "/userLogin")
    public Map<String, Object> login (HttpServletRequest request, HttpServletResponse response, @RequestBody PreRegisterHeader preRegisterHeader) throws Exception {
        HttpSession session = request.getSession();
        Map<String, Object> registResult = new HashMap<>();
        if (session.getAttribute("user") == null) {  //用户未登录或session已经过期
            registResult = registerModelService.userlogin(preRegisterHeader.getUsername(), preRegisterHeader.getPassword());
            if (registResult.get("user") == null) {
                request.getRequestDispatcher("").forward(request, response);  //跳转到注册页面 具体路径未知
            } else {
                Integer status = (Integer) registResult.get("status");
                if (status == ResultObject.accountStatus_2) {
                   //涉及权限
                } else {

                }
                session.setAttribute("user",registResult.get("user"));
                return registResult;
            }
        }
        return null;
    }

    /**
     * 发送邮箱验证码并校验用户是否存在
     * @param registerHeader
     * @return
     */
    @ApiOperation(value="发送邮箱验证码并校验用户是否存在", notes = "参数：username、email")
    @RequestMapping("/sendEmailCaptchaAndVerifyUser")
    public String sendEmailCaptchaAndVerifyUser(@RequestBody RegisterHeader registerHeader){
        return registerModelService.sendEmailCaptchaAndVerifyUser(registerHeader);
    }

    /**
     * 校验邮箱验证码是否正确
     * @param vo
     * @return
     */
    @ApiOperation(value="校验邮箱验证码是否正确", notes = "参数：email、captcha")
    @RequestMapping("/emailCaptchaVerify")
    public String emailCaptchaVerify(@RequestBody RegisterHeaderVO vo){
        return registerModelService.emailCaptchaVerify(vo);
    }

    /**
     * 更新密码
     * @param registerHeader
     * @return
     */
    @ApiOperation(value="更新密码", notes = "参数：username、password、email")
    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestBody RegisterHeader registerHeader){
        try {
            registerModelService.updatePassword(registerHeader);
            return resp.setStateCode(BaseResponse.SUCCESS).setMsg("更新用户密码成功").toJson();
        }catch (Exception e){
            log.error("更新用户密码失败："+e.getMessage());
            e.printStackTrace();
            return resp.setStateCode(BaseResponse.ERROR).setMsg("更新用户密码失败："+e.getMessage()).toJson();
        }

    }

}
