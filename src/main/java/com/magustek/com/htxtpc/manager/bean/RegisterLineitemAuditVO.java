package com.magustek.com.htxtpc.manager.bean;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "RegisterLineitemAuditVO-审核信息")
public class RegisterLineitemAuditVO extends RegisterLineitemAudit {
    private String aflag; //用户标识
    private String searchStr; //搜索内容
    private String auditFlag; //审核标识
}
