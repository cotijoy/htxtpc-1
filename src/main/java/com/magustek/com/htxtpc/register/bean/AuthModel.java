package com.magustek.com.htxtpc.register.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "AuthModel-用户权限模型")
@Data
//@Entity
@AllArgsConstructor
public class AuthModel extends BaseEntity {
    @ApiModelProperty(value = "权限编码")
    @Column(nullable = false) private String authCode;
    @ApiModelProperty(value = "权限名称")
    @Column(nullable = false) private String authName;
    @ApiModelProperty(value = "权限类型")
    @Column(nullable = false) private String authType;

    private String url;		 //请求URL

    private String loginName;
    private String userSource;
    private String orgCode;
    private String deptCode;
    private String actorCode;
}
