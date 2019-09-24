package com.magustek.com.htxtpc.register.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@ApiModel(value = "PreRegisterHeader-用户(预)注册抬头")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PreRegisterHeader extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private Long companyCode;
    @ApiModelProperty(value = "用户名")
    @Column(nullable = false) private String username;
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
    @Column(nullable = false) private String email;
    @ApiModelProperty(value = "注册密码")
    @Column(nullable = false) private String password;
    @ApiModelProperty(value = "主营业务")
    @Column(nullable = false) private String mainBusiness;
}
