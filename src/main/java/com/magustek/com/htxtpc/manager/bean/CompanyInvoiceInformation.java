package com.magustek.com.htxtpc.manager.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 企业发票信息
 */
@ApiModel(value = "CompanyInvoiceInformation-企业发票信息")
@Entity
@Setter
@Getter
public class CompanyInvoiceInformation extends BaseEntity {
    @ApiModelProperty(value="企业编码")
    @Column(nullable = false) private Long companyCode;
    @ApiModelProperty(value="企业名称")
    @Column(nullable = false) private String companyName;
    @ApiModelProperty(value="统一信用代码")
    @Column(nullable = false) private String creditCode;
    @ApiModelProperty(value="企业地址")
    @Column(nullable = false) private String companyAddress;
    @ApiModelProperty(value="企业电话")
    @Column(nullable = false) private String companyPhone;
    @ApiModelProperty(value="开户行")
    @Column(nullable = false) private String accountName;
    @ApiModelProperty(value="开户行账户")
    @Column(nullable = false) private String account;
}
