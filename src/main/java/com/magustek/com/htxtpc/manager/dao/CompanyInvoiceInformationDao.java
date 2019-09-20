package com.magustek.com.htxtpc.manager.dao;

import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

public interface CompanyInvoiceInformationDao extends CrudRepository<CompanyInvoiceInformation, Long> {
    @Query(nativeQuery = true,
            value = "",
            countQuery = "")
    Page<Map<String, Object>> findAllByCompanyNameOrAccountName();
}
