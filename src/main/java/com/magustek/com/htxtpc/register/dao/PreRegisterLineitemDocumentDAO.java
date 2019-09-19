package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.PreRegisterLineitemDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreRegisterLineitemDocumentDAO extends CrudRepository<PreRegisterLineitemDocument,Long> {
    List<PreRegisterLineitemDocument>  findByCompanyCodeAndUsername(Long companyCode, String username);
}
