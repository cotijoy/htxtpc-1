package com.magustek.com.htxtpc.manager.service;

import com.magustek.com.htxtpc.manager.bean.UserVO;
import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.user.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface RegisterLineitemAuditService {

    Page<User> auditSearchByAdmin(Long companyCode, UserVO userVO);

    Page<User> auditAllSearchByAdmin(Long companyCode, UserVO userVO);

    Page<Map<String, Object>> auditSearchUserByOperator(UserVO vo);

    Page<Map<String, Object>> auditAllSearchUserByOperator(UserVO vo);

    Page<Map<String, Object>> auditSearchCompanyByOperator(UserVO vo);

    Page<Map<String, Object>> auditAllSearchCompanyByOperator(UserVO vo);

}
