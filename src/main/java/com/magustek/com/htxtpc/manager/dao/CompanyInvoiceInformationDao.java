package com.magustek.com.htxtpc.manager.dao;

import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CompanyInvoiceInformationDao extends CrudRepository<CompanyInvoiceInformation, Long> {
    @Query(value = "select distinct cI from CompanyInvoiceInformation cI where (cI.companyName like %?1% or cI.accountName like %?1%) and cI.companyCode = ?2")
    Page<CompanyInvoiceInformation> findAllByCompanyNameOrAccountName(String companyNameOrAccountName, Long companyCode, Pageable pageable);

    Page<CompanyInvoiceInformation> findAllByCompanyCode(Long companyCode, Pageable pageable);

}
