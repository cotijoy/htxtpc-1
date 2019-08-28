package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "PreRegisterLineitemDocument-用户(预)注册行项目-资料")
@Data
@Entity
public class PreRegisterLineitemDocument extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "用户名")
    @Column(nullable = false) private String username;
    @ApiModelProperty(value = "资料流水号")
    @Column(nullable = false) private String documentSerialNum;
    @ApiModelProperty(value = "资料内容")
    @Column() private String documentContent;
}
