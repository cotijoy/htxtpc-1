package com.magustek.com.htxtpc.register.controller;

import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.service.RegisterModelService;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api("用户注册")
@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController {

    private RegisterModelService registerModelService;
    private BaseResponse resp;

    public RegisterController(RegisterModelService registerModelService) {
        this.registerModelService = registerModelService;
        resp = new BaseResponse();
    }

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

    /**
     * 发送邮箱验证码并校验用户是否存在
     * @param username
     * @param email
     * @return
     */
    @ApiOperation(value="发送邮箱验证码并校验用户是否存在", notes = "参数：username、email")
    @RequestMapping("/sendEmailCaptchaAndVerifyUser")
    public String sendEmailCaptchaAndVerifyUser(@RequestParam(value = "username") String username,
                                                @RequestParam(value = "email") String email){
        return registerModelService.sendEmailCaptchaAndVerifyUser(username,email);
    }

    /**
     * 校验邮箱验证码是否正确
     * @param email
     * @param captcha
     * @return
     */
    @ApiOperation(value="校验邮箱验证码是否正确", notes = "参数：email、code")
    @RequestMapping("/emailCaptchaVerify")
    public String emailCaptchaVerify(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "captcha") String captcha){
        return registerModelService.emailCaptchaVerify(email, captcha);
    }

    /**
     * 更新密码
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value="更新密码", notes = "参数：username、password")
    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestParam(value = "username") String username,
                                 @RequestParam(value = "password") String password){
        try {
            registerModelService.updatePassword(username, password);
            return resp.setStateCode(BaseResponse.SUCCESS).setMsg("更新用户密码成功").toJson();
        }catch (Exception e){
            log.error("更新用户密码失败："+e.getMessage());
            e.printStackTrace();
            return resp.setStateCode(BaseResponse.ERROR).setMsg("更新用户密码失败："+e.getMessage()).toJson();
        }

    }

}
