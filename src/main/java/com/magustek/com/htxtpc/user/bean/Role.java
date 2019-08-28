package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "Position-岗位表")
@Data
@Entity
public class Role extends BaseEntity {
    @ApiModelProperty(value = "角色编码")
    @Column(nullable = false) private String roleCode;
    @ApiModelProperty(value = "角色描述")
    @Column(nullable = false) private String roleDescription;
}
