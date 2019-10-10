package com.magustek.com.htxtpc.manager.service;

import com.magustek.com.htxtpc.manager.bean.RegisterLineitemAuditVO;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface RegisterLineitemAuditService {

    Page<Map<String, Object>> findRegisterInformationBySupplier(Long companyCode, RegisterLineitemAuditVO vo);

    Page<Map<String, Object>> findAllRegisterInformationBySupplier(Long companyCode, RegisterLineitemAuditVO vo);

    Page<Map<String, Object>> findRegisterInformationByAdmin(RegisterLineitemAuditVO vo);

    Page<Map<String, Object>> findAllRegisterInformationByAdmin(RegisterLineitemAuditVO vo);
}
