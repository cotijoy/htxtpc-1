package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "RegisterHeader-用户注册抬头")
@Data
@Entity
public class RegisterHeader extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "用户名")
    @Column(nullable = false) private String userName;
    @ApiModelProperty(value = "管理员标识")
    @Column(nullable = false) private String adminFlag;
    @ApiModelProperty(value = "电话号码区号")
    @Column(nullable = false) private String telphoneAreaCode;
    @ApiModelProperty(value = "电话号码")
    @Column(nullable = false) private String telphone;
    @ApiModelProperty(value = "电话号码分机号")
    @Column(nullable = false) private String telphoneExtensionNum;
    @ApiModelProperty(value = "手机号码国际区号")
    @Column(nullable = false) private String phoneNationalAreaCode;
    @ApiModelProperty(value = "手机号码")
    @Column(nullable = false) private String phoneNum;
    @ApiModelProperty(value = "邮箱")
    @Column(nullable = false) private String roleCode;
    @ApiModelProperty(value = "注册密码")
    @Column(nullable = false) private String password;
    @ApiModelProperty(value = "主营业务")
    @Column(nullable = false) private String mainBusiness;
}
