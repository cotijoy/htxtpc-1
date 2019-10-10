package com.magustek.com.htxtpc.manager.service.impl;

import com.magustek.com.htxtpc.manager.bean.RegisterLineitemAuditVO;
import com.magustek.com.htxtpc.manager.dao.RegisterLineitemAuditDAO;
import com.magustek.com.htxtpc.manager.service.RegisterLineitemAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("RegisterLineitemAuditServiceImpl")
public class RegisterLineitemAuditServiceImpl implements RegisterLineitemAuditService {

    private RegisterLineitemAuditDAO registerLineitemAuditDAO;

    public RegisterLineitemAuditServiceImpl(RegisterLineitemAuditDAO registerLineitemAuditDAO) {
        this.registerLineitemAuditDAO = registerLineitemAuditDAO;
    }

    /**
     * 注册信息条件查询 supplier
     * @param companyCode
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> findRegisterInformationBySupplier(Long companyCode, RegisterLineitemAuditVO vo) {
        /*Page<Map<String, Object>> registerInformationBySupplier = registerLineitemAuditDAO.findRegisterInformationBySupplier(vo.getAflag(), vo.getAuditStatus(), "%" + vo.getSearchStr() + "%", companyCode, vo.getPageRequest());
        List<Map<String, Object>> list = registerInformationBySupplier.getContent();
        Map<String, Object> contents = list.get(0);
        for (Map<String, Object> content : contents){
            //(String)content.get("auditStatus")
        }
        return registerInformationBySupplier;*/
        return registerLineitemAuditDAO.findRegisterInformationBySupplier(vo.getAflag(), vo.getAuditStatus(), "%" + vo.getSearchStr() + "%", companyCode, vo.getPageRequest());
    }

    /**
     * 注册信息默认查询 supplier
     * @param companyCode
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> findAllRegisterInformationBySupplier(Long companyCode, RegisterLineitemAuditVO vo) {
        return registerLineitemAuditDAO.findAllRegisterInformationBySupplier(vo.getAflag(), vo.getAuditStatus(), companyCode, vo.getPageRequest());
    }

    /**
     * 注册信息条件查询 admin
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> findRegisterInformationByAdmin(RegisterLineitemAuditVO vo) {
        return registerLineitemAuditDAO.findRegisterInformationByAdmin(vo.getAflag(), vo.getAuditStatus(), vo.getSearchStr(), vo.getPageRequest());
    }

    /**
     * 注册信息默认查询 admin
     * @param vo
     * @return
     */
    @Override
    public Page<Map<String, Object>> findAllRegisterInformationByAdmin(RegisterLineitemAuditVO vo) {
        /*Map<String, Object> contentMap = new HashMap<>();
        Page<Map<String, Object>> registerInformationPage = registerLineitemAuditDAO.findAllRegisterInformationByAdmin(vo.getAflag(), vo.getAuditStatus(), vo.getPageRequest());
        List<Map<String, Object>> contents = registerInformationPage.getContent();
        for (Map<String, Object> content : contents){
            if (content.get("auditStatus").toString().equalsIgnoreCase("DSH")){
                content.put("auditStatusText", "待审核");
            }else {
                content.put("auditStatusText", "已审核");
            }
        }
        contentMap.put("content", contents);
        contentMap.put("totalPages", registerInformationPage.getTotalPages());
        return contentMap;*/
        return registerLineitemAuditDAO.findAllRegisterInformationByAdmin(vo.getAflag(), vo.getAuditStatus(), vo.getPageRequest());
    }
}
