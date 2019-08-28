package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@ApiModel(value = "Company-企业表")
@Data
@Entity
public class Department extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "部门编码")
    @Column(nullable = false) private String departmentCode;
    @ApiModelProperty(value = "部门名称")
    @Column(nullable = false) private String departmentName;
    @ApiModelProperty(value = "部门全称")
    @Column(nullable = false) private String departmentFullName;
    @ApiModelProperty(value = "部门顺序")
    @Column(nullable = false) private String departmentOrder;
}
