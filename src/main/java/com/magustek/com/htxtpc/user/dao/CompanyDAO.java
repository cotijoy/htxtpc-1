package com.magustek.com.htxtpc.user.dao;

import com.magustek.com.htxtpc.user.bean.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyDAO extends CrudRepository<Company,Long> {
    Company findByCreditCode(String creditCode);
    Company findByCompanyCode(Long companyCode);
}
