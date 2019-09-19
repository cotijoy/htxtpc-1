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
import com.magustek.com.htxtpc.user.bean.Company;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.user.dao.CompanyDAO;
import com.magustek.com.htxtpc.user.dao.UserDAO;
import com.magustek.com.htxtpc.util.common.util.AESOperator;
import com.magustek.com.htxtpc.util.common.util.CommonUtil;
import com.magustek.com.htxtpc.util.common.util.ResultObject;
import com.magustek.com.htxtpc.util.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service()
public class RegisterModelServiceImpl implements RegisterModelService {
    private RegisterModelDAO registerModelDAO;
    private PreRegisterHeaderDAO preRegisterHeaderDAO;
    private PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO;
    private RegisterHeaderDAO registerHeaderDAO;
    private RegisterLineitemDocumentDAO registerLineitemDocumentDAO;
    private CompanyDAO companyDAO;
    private UserDAO userDAO;

    public RegisterModelServiceImpl(RegisterModelDAO registerModelDAO,
                                    PreRegisterHeaderDAO preRegisterHeaderDAO,
                                    PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO,
                                    RegisterHeaderDAO registerHeaderDAO,
                                    RegisterLineitemDocumentDAO registerLineitemDocumentDAO,
                                    CompanyDAO companyDAO, UserDAO userDAO) {
        this.registerModelDAO = registerModelDAO;
        this.preRegisterHeaderDAO = preRegisterHeaderDAO;
        this.preRegisterLineitemDocumentDAO = preRegisterLineitemDocumentDAO;
        this.registerHeaderDAO = registerHeaderDAO;
        this.registerLineitemDocumentDAO = registerLineitemDocumentDAO;
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
    }

    /**
     * 验证该用户名是否可以被注册
     */
    @Override
    public Map<String, Object> usernameCheck(String username) throws Exception {
        Map<String, Object> registResult = new HashMap<>();
        PreRegisterHeader preRegisterHeader = preRegisterHeaderDAO.findByUsername(username.trim());
        /*RegisterHeader registerHeader = registerHeaderDAO.findByUsername(username);*/
        if (preRegisterHeader != null) {
            registResult.put("status","该用户名已经被注册");
            return registResult;
        }
        return null;
    }


    /**
     * 用户注册
     */
    @Transactional
    @Override
    public Map<String, Object> register(RegisterModel registerModel) throws Exception{
        Map<String, Object> registResult = new HashMap<>();
        String phoneNum = registerModel.getPhoneNum();
        PreRegisterHeader preRegisterHeader = preRegisterHeaderDAO.findByPhoneNum(phoneNum);
        RegisterHeader registerHeader = registerHeaderDAO.findByPhoneNum(phoneNum);
        if (registerHeader != null) {  //审核通过的正式用户
            registResult.put("accountStatus",ResultObject.accountStatus_3);
        } else if (preRegisterHeader != null ) {  //待审核用户
            registResult.put("accountStatus",ResultObject.accountStatus_2);
        } else {    //当前正在注册的用户
            preRegisterHeader = new PreRegisterHeader();
            Long companyCode = companyDAO.findByCreditCode(registerModel.getCreditCode()).getCompanyCode();
            registerModel.setPassword(AESOperator.encrypt(registerModel.getPassword()));  //对密码进行加密
            BeanUtils.copyProperties(registerModel,preRegisterHeader);
            preRegisterHeader.setCompanyCode(companyCode);
            preRegisterHeaderDAO.save(preRegisterHeader);
            User user = new User();
            BeanUtils.copyProperties(preRegisterHeader,user);
            user.setUserCellphone(preRegisterHeader.getPhoneNum());
            user.setUserTelphone(preRegisterHeader.getTelphone());
            user.setUserEmail(preRegisterHeader.getEmail());
            userDAO.save(user);
            try {  //保存预注册的文件信息
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
                registResult.put("accountStatus",ResultObject.accountStatus_0);
                log.info(registerModel.getUsername() + ":注册失败");
                return registResult;
            }
            registResult.put("accountStatus",ResultObject.accountStatus_1);
        }

        return registResult;

    }




    /**
     * 用户登录
     */
    @Override
    public Map<String, Object> userlogin(String username, String password) throws Exception {
            Map<String, Object> registResult = new HashMap<>();
            if ( !StringUtil.isEmpty(username) && !StringUtil.isEmpty(password)) {
                password = AESOperator.encrypt(password);  //对输入的密码进行加密，再查找
                RegisterHeader registerHeader = registerHeaderDAO.findByUsernameAndPassword(username,password);
                if (registerHeader != null) {
                    registResult.put("accountStatus",ResultObject.accountStatus_3);
                    registResult.put("user", registerHeader);
                } else {
                    PreRegisterHeader preRegisterHeader = preRegisterHeaderDAO.findByUsernameAndPassword(username,password);
                    if (preRegisterHeader != null) {
                        registResult.put("accountStatus",ResultObject.accountStatus_2);
                        registResult.put("user", preRegisterHeader);
                    } else {
                        registResult.put("user", null);
                        return null;
                    }
                }
            }
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
     * 用户查询注册情况
     */
    @Override
    public Map<String, Object> findRegisterModel(String username, String password) throws Exception {
        password = AESOperator.encrypt(password);  //对输入的密码进行加密
        Map<String, Object> registResult = new HashMap<>();
        // RegisterModel registerModel = new RegisterModel();  有问题
        RegisterHeader registerHeader = registerHeaderDAO.findByUsernameAndPassword(username,password);
        if (registerHeader == null) {
            PreRegisterHeader preRegisterHeader = preRegisterHeaderDAO.findByUsernameAndPassword(username,password);
        } else {
            Long companyCode = registerHeader.getCompanyCode();
            Company company = companyDAO.findByCompanyCode(companyCode);
            List<RegisterLineitemDocument> registerLineitemDocument = registerLineitemDocumentDAO.
                    findByCompanyCodeAndUsername(companyCode,username);
            //BeanUtils.copyProperties(); //未完待续
        }



        return null;
    }

    /**
     * 发送短信验证码
     * @param captchaConfig
     * @param phoneNum
     * @return
     */
    @Override
    public Map<String, Object> sendPhoneCaptcha(CaptchaConfig captchaConfig, String phoneNum) {
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
        return null;
    }

    /**
     * 发送邮箱验证码
     * @param captchaConfig
     * @param email
     * @return
     */
    @Override
    public Map<String,Object> sendEmailCaptcha(CaptchaConfig captchaConfig, String email){
        String code = UUID.randomUUID().toString().substring(0,4);
        //获得一个Session对象
        Properties props = new Properties();
        props.setProperty("mail.protocol","smtp");
        props.setProperty("mail.host",captchaConfig.getMailHost());
        props.setProperty("mail.smtp.auth","true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(captchaConfig.getUserName(), captchaConfig.getPassword());
            }
        });
        //创建一个代表邮件的对象Message
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(captchaConfig.getUserName()));
            //设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //设置标题
            message.setSubject("测试邮件，请注意验证码");
            //设置邮件正文
            message.setContent("<h1>商城激活邮件，验证码为"+code+"<h1>","text/html;charset=UTF-8");
            //发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //将验证码存入redis缓存



        return null;
    }


}
