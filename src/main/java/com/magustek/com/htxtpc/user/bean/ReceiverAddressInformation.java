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
    @Column() private String nation;
    @ApiModelProperty(value="省")
    @Column() private String province;
    @ApiModelProperty(value="市")
    @Column() private String city;
    @ApiModelProperty(value="区")
    @Column() private String district;
    @ApiModelProperty(value="详细地址")
    @Column() private String receiverAddress;
    @ApiModelProperty(value="收货人")
    @Column() private String receiver;
    @ApiModelProperty(value="手机号码")
    @Column() private String cellphoneNum;
}
