package com.magustek.com.htxtpc.manager.service;

import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.user.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SystemManageService {

    Page<User> auditSearchByAdmin(String companyCode, String userFullName, String accountStatus, Pageable pageable);

    Page<Map<String, Object>> auditSearchUserByOperator(String adminFlag, String accountStatus, String userFullName, Pageable pageable);

    Page<Map<String, Object>> auditSearchCompanyByOperator(String adminFlag, String accountStatus, String userCompany, Pageable pageable);

    void addCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation);

    void updateCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation);

    void deleteCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation);
}
