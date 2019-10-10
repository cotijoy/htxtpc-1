package com.magustek.com.htxtpc.manager.service.impl;

import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformationVO;
import com.magustek.com.htxtpc.manager.dao.CompanyInvoiceInformationDAO;
import com.magustek.com.htxtpc.manager.service.CompanyInvoiceInformationService;
import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformation;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service("CompanyInvoiceInformationServiceImpl")
public class CompanyInvoiceInformationServiceImpl implements CompanyInvoiceInformationService {

    private CompanyInvoiceInformationDAO companyInvoiceInformationDAO;

    public CompanyInvoiceInformationServiceImpl(CompanyInvoiceInformationDAO companyInvoiceInformationDAO) {
        this.companyInvoiceInformationDAO = companyInvoiceInformationDAO;
    }

    /**
     * 新增或修改发票信息
     * @param companyInvoiceInformation
     */
    @Override
    public void addOrUpdateCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation) {
        companyInvoiceInformationDAO.save(companyInvoiceInformation);
    }

    /**
     * 删除发票信息
     */
    @Override
    public void deleteCompanyInvoiceInformation(CompanyInvoiceInformation companyInvoiceInformation) {
        companyInvoiceInformationDAO.deleteById(companyInvoiceInformation.getId());
    }

    /**
     * 查询企业发票信息
     * @param companyCode
     * @param vo
     * @return
     */
    @Override
    public Page<CompanyInvoiceInformation> findAllByCompanyNameOrAccountName(Long companyCode, CompanyInvoiceInformationVO vo) {
        return companyInvoiceInformationDAO.findAllByCompanyNameOrAccountName(vo.getSearchStr(), companyCode, vo.getPageRequest());
    }

    /**
     * 根据企业编号查询所有信息
     * @param companyCode
     * @param vo
     * @return
     */
    @Override
    public Page<CompanyInvoiceInformation> findAllByCompanyCode(Long companyCode, CompanyInvoiceInformationVO vo) {
        return companyInvoiceInformationDAO.findAllByCompanyCode(companyCode, vo.getPageRequest());
    }
}
