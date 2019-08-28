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
public class Position extends BaseEntity {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "部门编码")
    @Column(nullable = false) private String departmentCode;
    @ApiModelProperty(value = "岗位编码")
    @Column(nullable = false) private String positionCode;
    @ApiModelProperty(value = "岗位名称")
    @Column(nullable = false) private String positionName;
    @ApiModelProperty(value = "岗位全称")
    @Column(nullable = false) private String positionFullName;
    @ApiModelProperty(value = "岗位顺序")
    @Column(nullable = false) private String positionOrder;
}
