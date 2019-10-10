package com.magustek.com.htxtpc.manager.dao;

import com.magustek.com.htxtpc.user.bean.RegisterLineitemAudit;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface RegisterLineitemAuditDAO extends CrudRepository<RegisterLineitemAudit, Long> {


    @Query(nativeQuery = true,
            value = "SELECT prh.company_full_name as userCompany, prh.user_full_name as userFullName, rla.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND prh.user_full_name LIKE :userFullName AND prh.company_code = :companyCode",
            countQuery = "SELECT count(*) FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND prh.user_full_name LIKE :userFullName AND prh.company_code = :companyCode")
    Page<Map<String, Object>> findRegisterInformationBySupplier(@Param("adminFlag") String adminFlag, @Param("auditStatus") String auditStatus, @Param("userFullName") String userFullName, @Param("companyCode") Long companyCode, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT prh.company_full_name as userCompany, prh.user_full_name as userFullName, rla.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND prh.company_code = :companyCode",
            countQuery = "SELECT count(*) FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND prh.company_code = :companyCode")
    Page<Map<String, Object>> findAllRegisterInformationBySupplier(@Param("adminFlag") String adminFlag, @Param("auditStatus") String auditStatus, @Param("companyCode") Long companyCode, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT prh.company_full_name as userCompany, prh.user_full_name as userFullName, rla.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND (prh.user_full_name LIKE :searchStr OR prh.company_full_name LIKE :searchStr) AND prh.company_code = :companyCode",
            countQuery = "SELECT count(*) FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus AND (prh.user_full_name LIKE :searchStr OR prh.company_full_name LIKE :searchStr) AND prh.company_code = :companyCode")
    Page<Map<String, Object>> findRegisterInformationByAdmin(@Param("adminFlag") String adminFlag, @Param("auditStatus") String auditStatus, @Param("searchStr") String searchStr, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT prh.company_full_name as userCompany, prh.user_full_name as userFullName, rla.chdate, rla.audit_status as auditStatus, rla.auditor, rla.audit_advice as auditAdvice, rla.audit_date as auditDate, rla.audit_time as auditTime, ac.company_full_name as auditorCompany FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus",
            countQuery = "SELECT count(*) FROM pre_register_header prh JOIN register_lineitem_audit rla ON prh.username = rla.username JOIN company ac ON ac.company_code = rla.auditor_company_code WHERE prh.admin_flag = :adminFlag AND rla.audit_status = :auditStatus")
    Page<Map<String, Object>> findAllRegisterInformationByAdmin(@Param("adminFlag") String adminFlag, @Param("auditStatus") String auditStatus, Pageable pageable);
}
