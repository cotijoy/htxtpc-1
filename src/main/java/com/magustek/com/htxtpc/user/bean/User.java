package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(value = "User-用户表")
@Data
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {
    @ApiModelProperty(value = "企业编码")
    @Column(nullable = false) private String companyCode;
    @ApiModelProperty(value = "部门编码")
    @Column(nullable = false) private String departmentCode;
    @ApiModelProperty(value = "岗位编码")
    @Column(nullable = false) private String positionCode;
    @ApiModelProperty(value = "用户编码")
    @Column(nullable = false) private String userCode;
    @ApiModelProperty(value = "用户名称")
    @Column() private String username;
    @ApiModelProperty(value = "用户全称")
    @Column() private String userFullName;
    @ApiModelProperty(value = "用户顺序")
    @Column() private String userOrder;
    @ApiModelProperty(value = "电话号码")
    @Column() private String userTelphone;
    @ApiModelProperty(value = "手机号码")
    @Column() private String userCellphone;
    @ApiModelProperty(value = "邮箱地址")
    @Column() private String userEmail;
    @ApiModelProperty(value = "用户状态，审核通过:X")
    @Column() private String accountStatus;

    @OneToOne(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "username",referencedColumnName = "username",insertable = false,updatable = false)
    private RegisterLineitemAudit userRegisterAudit;
}
