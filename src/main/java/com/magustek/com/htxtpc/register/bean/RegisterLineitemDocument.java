package com.magustek.com.htxtpc.register.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户(预)注册行项目-资料
 */
@ApiModel(value = "RegisterLineitemDocument-用户(预)注册行项目-资料")
@Entity
@Data
@AllArgsConstructor
public class RegisterLineitemDocument extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private Long companyCode;
    @ApiModelProperty(value = "用户名")
    @Column(nullable = false) private String username;
    @ApiModelProperty(value = "资料流水号")
    @Column(nullable = false) private String documentSerialNum;
    @ApiModelProperty(value = "资料大小")
    @Column(nullable = false) private String documentSize;
    @ApiModelProperty(value = "资料后缀")
    @Column(nullable = false) private String documentSuffix;
    @ApiModelProperty(value = "资料内容")
    @Column() private String documentContent;
}
