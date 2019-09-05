package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "Permission-权限表")
@Data
@Entity
@AllArgsConstructor
public class Permission extends BaseEntity {
    @ApiModelProperty(value = "权限id")
    @Column(nullable = false) private String permissionId;
    @ApiModelProperty(value = "父权限id")
    @Column(nullable = false) private String parentPermissionId;
    @ApiModelProperty(value = "权限名称")
    @Column(nullable = false) private String permissionName;
    @ApiModelProperty(value = "权限描述")
    @Column(nullable = false) private String permissionDescription;
}
