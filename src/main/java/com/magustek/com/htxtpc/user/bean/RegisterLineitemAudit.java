package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户(预)注册行项目-审核
 */
@ApiModel(value = "RegisterLineitemAudit-用户(预)注册行项目-审核")
@Entity
@Getter
@Setter
public class RegisterLineitemAudit extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "用户名")
    @Column(nullable = false) private String username;
    @ApiModelProperty(value = "审核流水号")
    @Column(nullable = false) private String auditSerialNum;
    @ApiModelProperty(value = "审核状态")
    @Column() private String auditStatus;
    @ApiModelProperty(value = "审核意见")
    @Column() private String auditAdvice;
    @ApiModelProperty(value = "审核人企业编码")
    @Column() private String auditorCompanyCode;
    @ApiModelProperty(value = "审核人")
    @Column() private String auditor;
    @ApiModelProperty(value = "审核日期（yyyy-MM-dd）")
    @Column() private String auditDate;
    @ApiModelProperty(value = "审核时间（HH:mm:ss ）")
    @Column() private String auditTime;
}
