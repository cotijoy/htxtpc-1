package com.magustek.com.htxtpc.register.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.magustek.com.htxtpc.register.bean.*;
import com.magustek.com.htxtpc.register.dao.*;
import com.magustek.com.htxtpc.register.service.RegisterModelService;
import com.magustek.com.htxtpc.user.dao.CompanyDAO;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import com.magustek.com.htxtpc.util.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service("RegisterModelService")
public class RegisterModelServiceImpl implements RegisterModelService {
    private RegisterModelDAO registerModelDAO;
    private PreRegisterHeaderDAO preRegisterHeaderDAO;
    private PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO;
    private RegisterHeaderDAO registerHeaderDAO;
    private RegisterLineitemDocumentDAO registerLineitemDocumentDAO;
    private CompanyDAO companyDAO;
    private RedisTemplate<String,Object> redisTemplate;
    private CaptchaConfig captchaConfig;
    private BaseResponse resp;

    public RegisterModelServiceImpl(RegisterModelDAO registerModelDAO,
                                    PreRegisterHeaderDAO preRegisterHeaderDAO,
                                    PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO,
                                    RegisterHeaderDAO registerHeaderDAO,
                                    RegisterLineitemDocumentDAO registerLineitemDocumentDAO,
                                    CompanyDAO companyDAO,
                                    RedisTemplate redisTemplate,
                                    CaptchaConfig captchaConfig) {
        this.registerModelDAO = registerModelDAO;
        this.preRegisterHeaderDAO = preRegisterHeaderDAO;
        this.preRegisterLineitemDocumentDAO = preRegisterLineitemDocumentDAO;
        this.registerHeaderDAO = registerHeaderDAO;
        this.registerLineitemDocumentDAO = registerLineitemDocumentDAO;
        this.companyDAO = companyDAO;
        this.redisTemplate = redisTemplate;
        this.captchaConfig = captchaConfig;
        resp = new BaseResponse();
    }

    @Override
    public Map<String, Object> register(RegisterModel registerModel) {
        PreRegisterHeader preRegisterHeader = new PreRegisterHeader();
        PreRegisterLineitemDocument preRegisterLineitemDocument = new PreRegisterLineitemDocument();
        Map<String, Object> registResult = new HashMap<>();
        String companyCode = companyDAO.findByCreditCode(registerModel.getCreditCode()).getCompanyCode();
        BeanUtils.copyProperties(registerModel,preRegisterHeader);
        preRegisterHeader.setCompanyCode(companyCode);
        preRegisterHeaderDAO.save(preRegisterHeader);

        try {
            List<PreRegisterLineitemDocument> preRegisterLineitemDocumentLists = (List<PreRegisterLineitemDocument>)
                    this.extractDocumentInfo(registerModel.
                            getDocumentTypes(),registerModel.getDocumentNums(),registerModel.getDocuments()).
                            get("preRegisterLineitemDocumentLists");

            for(PreRegisterLineitemDocument obj:preRegisterLineitemDocumentLists) {
                obj.setCompanyCode(companyCode);
                obj.setUsername(registerModel.getUsername());
                obj.setDocumentSerialNum(CommonUtil.getUUID());
                preRegisterLineitemDocumentDAO.save(obj);
            }
        } catch (Exception e) {
            registResult.put("status",false);
            log.info(registerModel.getUsername() + ":注册失败");
            return registResult;
        }
        registResult.put("status",true);
        return registResult;

    }

    /**
     * 获取上传文件的信息
     * @param documentTypes，documentNums，documents
     * @return
     */
    private Map<String,Object> extractDocumentInfo(String[] documentTypes, Integer[] documentNums, MultipartFile[] documents) throws Exception{
        Map<String,Object> result = new HashMap<>();
        List<PreRegisterLineitemDocument> preRegisterLineitemDocumentLists = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < documentTypes.length; i++) {
            for (int j = index; j < index + documentNums[i]; j++) {
                //附件名
                String name = documents[j].getOriginalFilename();
                //附件后缀
                //String fileSuffix = name.substring(name.lastIndexOf(".") + 1);
                String fileSuffix = documents[j].getContentType();
                //文件大小
                String size = documents[j].getSize() + "";
                //附件数据
                byte[] data = documents[j].getBytes();
                String base64data = CommonUtil.encodeByBase64(data);
                //文件类型
                String type = documentTypes[i];
                PreRegisterLineitemDocument preRegisterLineitemDocument = new PreRegisterLineitemDocument(null,null,null,size,fileSuffix,base64data);
                preRegisterLineitemDocumentLists.add(preRegisterLineitemDocument);
            }
            index += documentNums[i];
        }
        result.put("preRegisterLineitemDocumentLists",preRegisterLineitemDocumentLists);
        return result;
    }
    /**
     * 发送邮箱验证码并校验用户是否存在
     */
    @Override
    public String sendEmailCaptchaAndVerifyUser(String username, String email){
        if(!email.matches("^\\w+@(\\w+\\.)+\\w+$")){
            return resp.setStateCode(BaseResponse.ERROR).setMsg("邮箱格式不正确").toJson();
        }
        if (registerHeaderDAO.findByUsername(username)==null){
            return resp.setStateCode(BaseResponse.ERROR).setMsg("用户不存在").toJson();
        } else if (!registerHeaderDAO.findByUsername(username).getEmail().equals(email)){
            return resp.setStateCode(BaseResponse.ERROR).setMsg("用户邮箱不正确").toJson();
        }
        this.sendEmailCaptcha(email);
        return resp.setStateCode(BaseResponse.SUCCESS).setMsg("邮箱验证码发送成功").toJson();
    }
    /**
     * 校验验证码是否正确
     */
    @Override
    public String emailCaptchaVerify(String email, String captcha){
        if (captcha.equals(redisTemplate.boundHashOps("emailCaptcha").get(email))){
            redisTemplate.boundHashOps("emailCaptcha").delete(email);
            return resp.setStateCode(BaseResponse.SUCCESS).setMsg("验证码正确").toJson();
        }
        return resp.setStateCode(BaseResponse.ERROR).setMsg("验证码错误").toJson();
    }
    /**
     * 更新用户密码
     */
    @Override
    public void updatePassword(String username, String password){
        RegisterHeader header = registerHeaderDAO.findByUsername(username);
        header.setPassword(password);
        registerHeaderDAO.save(header);
    }
    /**
     * 发送短信验证码
     * @param phoneNum
     * @return
     */
    private void sendPhoneCaptcha(String phoneNum) {
        // 创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(captchaConfig.getRegionId(), captchaConfig.getAccessKeyId(), captchaConfig.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        // 发送短信，创建API请求并设置参数
        CommonRequest request = new CommonRequest();
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        //调用SendSms发送短信
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "阿里云");
        request.putQueryParameter("TemplateCode", "SMS_153055065");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //查询发送信息
        CommonRequest requestQuery = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    private void sendEmailCaptcha(String email){
        String code = UUID.randomUUID().toString().substring(0,4);
        //获得一个Session对象
        Properties props = new Properties();
        props.setProperty("mail.protocol","smtp");
        props.setProperty("mail.host",captchaConfig.getMailHost());
        props.setProperty("mail.smtp.auth","true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(captchaConfig.getUsername(), captchaConfig.getPassword());
            }
        });
        //创建一个代表邮件的对象Message
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(captchaConfig.getUsername()));
            //设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //设置标题
            message.setSubject("测试邮件，请注意验证码");
            //设置邮件正文
            message.setContent("<h1>商城激活邮件，验证码为"+code+"<h1>","text/html;charset=UTF-8");
            //发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        //将验证码存入redis缓存
        redisTemplate.boundHashOps("emailCaptcha").put(email, code);
    }


}
