package com.magustek.com.htxtpc.manager.service;

import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformationVO;
import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformation;
import org.springframework.data.domain.Page;

public interface CompanyInvoiceInformationService {
    void addOrUpdateCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation);

    void deleteCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation);

    Page<CompanyInvoiceInformation> findAllByCompanyNameOrAccountName(Long companyCode, CompanyInvoiceInformationVO vo);

    Page<CompanyInvoiceInformation> findAllByCompanyCode(Long companyCode, CompanyInvoiceInformationVO vo);
}
