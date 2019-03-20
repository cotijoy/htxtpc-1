package com.magustek.com.htxtpc.util.test;

import com.magustek.com.htxtpc.util.OdataUtils;
import com.magustek.com.htxtpc.util.httpUtil.HttpUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service("TestService")
@Cacheable(value = "contractMn")
public class TestServiceImpl implements TestService {
    private HttpUtils httpUtils;

    public TestServiceImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
    public TestContractHeaderSet getContractMainInfo(String loginName, String userSource, String orgCode, String deptCode, String posCode, String contractMn) throws Exception {
        //String select = OdataUtils.extractODataField(TestContractHeaderSet.class);
        // 调用odata服务
        contractMn = "000000" + contractMn;
        String url = OdataUtils.ContractHeaderSet + "(Loginname='" + loginName
                + "',Usersource='" + userSource + "',Orgcode='" + orgCode + "',Deptcode='" + deptCode
                + "',Actorcode='" + posCode + "',Htnum='" + contractMn + "')?$select=*";
        String result = httpUtils.getResultByUrl(url,null, HttpMethod.GET);

        // 解析日期
        result = OdataUtils.formatDateByPattern(result, new String[] { "Esdat", "Efdat", "Qddat" }, "yyyy-MM-dd");

        return OdataUtils.getSingleWithEntity(result, TestContractHeaderSet.class);
    }
}
