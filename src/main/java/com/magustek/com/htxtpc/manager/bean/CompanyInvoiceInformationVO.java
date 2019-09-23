package com.magustek.com.htxtpc.manager.bean;

import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "CompanyInvoiceInformationVO-企业发票信息")
public class CompanyInvoiceInformationVO extends CompanyInvoiceInformation {
    String searchStr; //企业名称或开户行
}
