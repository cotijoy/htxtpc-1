package com.magustek.com.htxtpc.register.bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(value = "注册模型——前端注册时表单传过来的数据模型")
@Data
public class RegisterModel {
    private String companyName;                                 //企业名称
    private String creditCode;                                  //统一社会信用代码
    private String companyAddress;                              //企业地址
    private String organizationType;                            //组织类型
    private String legalPerson;                                 //企业法定代表人
    private String username;                                    //用户名
    private String telphoneAreaCode;                            //区号
    private String telphone;                                    //电话号码
    private String telphoneExtensionNum;                        //分机号
    private String phoneNum;                                    //手机号码
    private String email;                                       //邮箱
    private String password;                                    //密码
    private String mainBusiness;                                //主营业务
    private String adminFlag;                                   //管理员标识 是：Y，否:N
    private String[] attachmenTypes;                            //附件类型
    private Integer[] attachmentNums;                           //附件个数
    private MultipartFile[] attachments;                        //附件数据（图片、文件）
}