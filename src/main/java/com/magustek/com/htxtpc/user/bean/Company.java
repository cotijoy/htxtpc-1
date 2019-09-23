package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@ApiModel(value = "Company-企业表")
@Data
@Entity
public class Company implements Serializable {
    @TableGenerator(
            name = "companyCode_generator",
            table = "jpa_companycode_generator",
            pkColumnName = "pk_name",
            pkColumnValue = "company_code",
            valueColumnName = "pk_value",
            initialValue = 600001,
            allocationSize = 1)

    @ApiModelProperty(value = "企业编码")
    @GeneratedValue(generator = "companyCode_generator")
    @Column(nullable = false) @Id Long companyCode;
    @ApiModelProperty(value = "企业名称")
    @Column(nullable = false) private String companyName;
    @ApiModelProperty(value = "企业全称")
    @Column(nullable = false) private String companyFullName;
    @ApiModelProperty(value = "企业顺序")
    @Column(nullable = false) private String companyOrder;
    @ApiModelProperty(value = "企业上级")
    @Column(nullable = false) private String superiorCompany;
    @ApiModelProperty(value = "统一社会信用代码")
    @Column(nullable = false) private String creditCode;
    @ApiModelProperty(value = "企业地址")
    @Column(nullable = false) private String companyAddress;
    @ApiModelProperty(value = "组织类型")
    @Column(nullable = false) private String organizationType;
    @ApiModelProperty(value = "法人代表")
    @Column(nullable = false) private String legalPerson;
}
