package com.magustek.com.htxtpc.manager.service.impl;

import com.magustek.com.htxtpc.manager.bean.UserVO;
import com.magustek.com.htxtpc.manager.dao.CompanyInvoiceInformationDao;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.manager.dao.UserDao;
import com.magustek.com.htxtpc.manager.service.RegisterLineitemAuditService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

@Service("RegisterLineitemAuditServiceImpl")
public class RegisterLineitemAuditServiceImpl implements RegisterLineitemAuditService {

    private UserDao userDao;

    public RegisterLineitemAuditServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     *
     * 企业管理员审核搜索
     * @param vo
     * @return
     */
    @Override
    public Page<User> auditSearchByAdmin(Long companyCode, UserVO vo) {
        return userDao.findAllByCompanyCodeAndUserFullNameLikeAndAccountStatus(companyCode,"%"+vo.getUserFullName()+"%", vo.getAccountStatus(), vo.getPageRequest());
    }

    /**
     * 企业管理员审核默认搜索
     * @param vo
     * @return
     */
    @Override
    public Page<User> auditAllSearchByAdmin(Long companyCode, UserVO vo) {
        return userDao.findAllByCompanyCodeAndAccountStatus(companyCode, vo.getAccountStatus(), vo.getPageRequest());
    }

    /**
     * 运维审核：按人名搜索
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditSearchUserByOperator(UserVO vo) {
        return userDao.findByAdminFlagAccountStatusUserFullName("X", vo.getAccountStatus(), "%"+vo.getUserFullName()+"%", vo.getPageRequest());
    }

    /**
     * 运维审核：按人名默认搜索
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditAllSearchUserByOperator(UserVO vo) {
        return userDao.findAllByAdminFlagAccountStatusUserFullName("X", vo.getAccountStatus(), vo.getPageRequest());
    }

    /**
     * 运维审核：按公司名搜索
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditSearchCompanyByOperator(UserVO vo) {
        return userDao.findByAdminFlagAccountStatusUserCompany("X", vo.getAccountStatus(), "%"+vo.getUserCompany()+"%", vo.getPageRequest());
    }

    /**
     * 运维审核：按公司名默认搜索
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> auditAllSearchCompanyByOperator(UserVO vo) {
        return userDao.findAllByAdminFlagAccountStatusUserCompany("X", vo.getAccountStatus(), vo.getPageRequest());
    }

}
