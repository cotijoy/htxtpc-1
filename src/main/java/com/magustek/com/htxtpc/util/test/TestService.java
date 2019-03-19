package com.magustek.com.htxtpc.util.test;

public interface TestService {

    TestContractHeaderSet getContractMainInfo(String loginName, String userSource, String orgCode,
                                                      String deptCode, String posCode, String Htnum) throws Exception;
}
