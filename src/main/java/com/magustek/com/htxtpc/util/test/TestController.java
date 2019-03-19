package com.magustek.com.htxtpc.util.test;

import com.alibaba.fastjson.JSON;
import com.magustek.com.htxtpc.user.bean.UserInfo;
import com.magustek.com.htxtpc.util.ClassUtils;
import com.magustek.com.htxtpc.util.ContextUtils;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置表
 * */
@Api("测试")
@Slf4j
@RestController
@RequestMapping(value = "/test", method = RequestMethod.POST, produces = ClassUtils.HTTP_HEADER)
public class TestController {
    private BaseResponse resp;
    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
        resp = new BaseResponse();
        log.info("初始化 TestController");
    }

    @ApiOperation(value="获取经营指标分类")
    @RequestMapping("/getContractMainInfo")
    public String getContractMainInfo(@RequestBody TestContractHeaderSet contractMainInfo) throws Exception{
        UserInfo userInfo = ContextUtils.getUserInfo();
        contractMainInfo = testService.getContractMainInfo(userInfo.getLoginname(),
                userInfo.getUsersource(),
                userInfo.getCompanyModel().getOrgcode(),
                userInfo.getCompanyModel().getDeptcode(),
                userInfo.getCompanyModel().getActorcode(),
                contractMainInfo.getHtnum());
        log.warn("从Odata获取经营指标分类：{}", JSON.toJSONString(contractMainInfo));
        return resp.setStateCode(BaseResponse.SUCCESS).setData(contractMainInfo).setMsg("成功！").toJson();
    }
}
