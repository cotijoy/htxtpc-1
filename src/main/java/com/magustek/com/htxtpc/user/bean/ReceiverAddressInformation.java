package com.magustek.com.htxtpc.user.bean;

import com.magustek.com.htxtpc.util.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 收货地址信息
 */
@ApiModel(value = "ReceiverAddressInformation-收货地址信息")
@Entity
@Getter
@Setter
public class ReceiverAddressInformation extends BaseEntity {
    @ApiModelProperty(value="用户编码")
    @Column(nullable = false) private String userCode;
    @ApiModelProperty(value="国家")
    @Column() private String nationTest;
    @ApiModelProperty(value="国家编码")
    @Column() private String nation;
    @ApiModelProperty(value="省")
    @Column() private String provinceText;
    @ApiModelProperty(value="省编码")
    @Column() private String province;
    @ApiModelProperty(value="市")
    @Column() private String cityText;
    @ApiModelProperty(value="市编码")
    @Column() private String city;
    @ApiModelProperty(value="区")
    @Column() private String areaText;
    @ApiModelProperty(value="区编码")
    @Column() private String area;
    @ApiModelProperty(value="镇")
    @Column() private String townText;
    @ApiModelProperty(value="镇编码")
    @Column() private String town;
    @ApiModelProperty(value="详细地址")
    @Column() private String receiverAddress;
    @ApiModelProperty(value="收货人")
    @Column() private String receiver;
    @ApiModelProperty(value="手机号码国际区号")
    @Column() private String phoneNationalAreaCode;
    @ApiModelProperty(value="手机号码")
    @Column() private String cellphoneNum;
    @ApiModelProperty(value="默认标识")
    @Column() private String defaultFlag;   //设为默认地址时，"X",否则为空
}
