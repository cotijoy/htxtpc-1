package com.magustek.com.htxtpc.manager.service.impl;

import com.magustek.com.htxtpc.manager.dao.CompanyInvoiceInformationDao;
import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.manager.dao.UserDao;
import com.magustek.com.htxtpc.manager.service.SystemManageService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

@Service
public class SystemManageServiceImpl implements SystemManageService {

    private UserDao userDao;
    private CompanyInvoiceInformationDao companyInvoiceInformationDao;

    public SystemManageServiceImpl(UserDao userDao,
                                   CompanyInvoiceInformationDao companyInvoiceInformationDao) {
        this.userDao = userDao;
        this.companyInvoiceInformationDao = companyInvoiceInformationDao;
    }

    /**
     * 企业管理员审核搜索
     * @param companyCode
     * @param userFullName
     * @param accountStatus
     * @param pageable
     * @return
     * @throws Exception
     */
    @Override
    public Page<User> auditSearchByAdmin(String companyCode, String userFullName, String accountStatus, Pageable pageable) {
        return userDao.findAllByCompanyCodeAndUserFullNameLikeAndAccountStatus(companyCode,"%"+userFullName+"%", accountStatus, pageable);
    }

    /**
     * 运维审核：按人名搜索
     * @param adminFlag
     * @param accountStatus
     * @param userFullName
     * @param pageable
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditSearchUserByOperator(String adminFlag, String accountStatus, String userFullName, Pageable pageable) {
        return userDao.findAllByAdminFlagAccountStatusUserFullName(adminFlag, accountStatus, "%"+userFullName+"%", pageable);
    }

    /**
     * 运维审核：按公司名搜索
     * @param adminFlag
     * @param accountStatus
     * @param userCompany
     * @param pageable
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditSearchCompanyByOperator(String adminFlag, String accountStatus, String userCompany, Pageable pageable) {
        return userDao.findAllByAdminFlagAccountStatusUserCompany(adminFlag, accountStatus, "%"+userCompany+"%", pageable);
    }

    /**
     * 新增发票信息
     * @param companyInvoiceInformation
     */
    @Override
    public void addCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation) {
        companyInvoiceInformationDao.save(companyInvoiceInformation);
    }

    /**
     * 修改发票信息
     * @param companyInvoiceInformation
     */
    @Override
    public void updateCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation) {
        companyInvoiceInformationDao.save(companyInvoiceInformation);
    }

    /**
     * 删除发票信息
     */
    @Override
    public void deleteCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation) {
        companyInvoiceInformationDao.delete(companyInvoiceInformation);
}

}
