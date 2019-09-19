package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.RegisterLineitemDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegisterLineitemDocumentDAO extends CrudRepository<RegisterLineitemDocument,Long> {
    List<RegisterLineitemDocument> findByCompanyCodeAndUsername(Long companyCode, String username);
}
