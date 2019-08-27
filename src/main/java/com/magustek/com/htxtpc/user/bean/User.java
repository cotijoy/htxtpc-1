package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "User-用户表")
@Data
@Entity
public class User extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "部门编码")
    @Column(nullable = false) private String departmentCode;
    @ApiModelProperty(value = "岗位编码")
    @Column(nullable = false) private String jobCode;
    @ApiModelProperty(value = "用户编码")
    @Column(nullable = false) private String userCode;
    @ApiModelProperty(value = "用户名称")
    @Column() private String userName;
    @ApiModelProperty(value = "用户全称")
    @Column() private String userFullName;
    @ApiModelProperty(value = "企业编码")
    @Column() private String userOrder;
    @ApiModelProperty(value = "电话号码")
    @Column() private String userTelPhone;
    @ApiModelProperty(value = "手机号码")
    @Column() private String userCellPhone;
    @ApiModelProperty(value = "邮箱地址")
    @Column() private String userEmail;
}
