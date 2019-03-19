package com.magustek.com.htxtpc.util.test;

import com.magustek.com.htxtpc.util.OdataUtils;


public interface TestService {

    TestContractHeaderSet getContractMainInfo(String loginName, String userSource, String orgCode,
                                                      String deptCode, String posCode, String Htnum) throws Exception;
}
