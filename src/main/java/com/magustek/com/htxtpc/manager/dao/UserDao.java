package com.magustek.com.htxtpc.manager.dao;

import com.magustek.com.htxtpc.user.bean.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface UserDao extends CrudRepository<User, Long> {

    Page<User> findAllByCompanyCodeAndUserFullNameLikeAndAccountStatus(String companyCode, String userFullName, String accountStatus, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT uc.company_full_name as userCompany, u.user_full_name as userFullName, u.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM user u JOIN pre_register_header prh ON u.username = prh.username JOIN register_lineitem_audit rla ON u.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code JOIN company uc ON uc.company_code = rla.company_code WHERE prh.admin_flag = :adminFlag AND u.account_status = :accountStatus AND u.user_full_name LIKE :userFullName",
            countQuery = "SELECT count(*) FROM user u JOIN pre_register_header prh ON u.username = prh.username JOIN register_lineitem_audit rla ON u.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code JOIN company uc ON uc.company_code = rla.company_code WHERE prh.admin_flag = :adminFlag AND u.account_status = :accountStatus AND u.user_full_name LIKE :userFullName")
    Page<Map<String, Object>> findAllByAdminFlagAccountStatusUserFullName(@Param("adminFlag")String adminFlag, @Param("accountStatus")String accountStatus, @Param("userFullName")String userFullName, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT uc.company_full_name as userCompany, u.user_full_name as userFullName, u.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM user u JOIN pre_register_header prh ON u.username = prh.username JOIN register_lineitem_audit rla ON u.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code JOIN company uc ON uc.company_code = rla.company_code WHERE prh.admin_flag = :adminFlag AND u.account_status = :accountStatus AND uc.company_full_name LIKE :userCompany",
            countQuery = "SELECT count(*) FROM user u JOIN pre_register_header prh ON u.username = prh.username JOIN register_lineitem_audit rla ON u.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code JOIN company uc ON uc.company_code = rla.company_code WHERE prh.admin_flag = :adminFlag AND u.account_status = :accountStatus AND uc.company_full_name LIKE :userCompany")
    Page<Map<String, Object>> findAllByAdminFlagAccountStatusUserCompany(@Param("adminFlag")String adminFlag, @Param("accountStatus")String accountStatus, @Param("userCompany")String userCompany, Pageable pageable);

}
