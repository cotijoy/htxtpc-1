package com.magustek.com.htxtpc.manager.controller;

import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.user.bean.UserInfo;
import com.magustek.com.htxtpc.manager.service.SystemManageService;
import com.magustek.com.htxtpc.util.base.BasePage;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Api("用户注册")
@Slf4j
@RestController
@RequestMapping("/systemManager")
public class SystemManageController {
    private SystemManageService systemManageService;
    private BaseResponse resp;

    public SystemManageController(SystemManageService systemManageService) {
        this.systemManageService = systemManageService;
        this.resp = new BaseResponse();
    }

    /**
     * 企业管理员审核搜索
     * @param httpSession
     * @param accountStatus: 用户状态(审核通过，正式用户："X" ； 待审核的用户："")
     * @param basePage: size page
     * @param userFullName: 普通用户名
     * @return
     * @throws Exception
     */
    @ApiOperation(value="企业管理员审核搜索", notes = "参数： ")
    @RequestMapping(value = "/auditSearchByAdmin.do")
    public String auditSearchByAdmin(HttpSession httpSession, @RequestParam String accountStatus, @RequestBody BasePage basePage, @RequestParam String userFullName){
        UserInfo user = (UserInfo)httpSession.getAttribute("userInfo");
        try {
            Page<User> list = systemManageService.auditSearchByAdmin(user.getCompanyModel().getCompanyCode(), userFullName,accountStatus, basePage.getPageRequest());
            return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }

    /**
     * 运维审核：按人名搜索
     * @param accountStatus: 用户状态(审核通过，正式用户："X" ； 待审核的用户："")
     * @param basePage: size page
     * @param userFullName: 管理员用户姓名
     * @return
     */
    @ApiOperation(value="运维人员审核按人名搜索", notes = "参数： ")
    @RequestMapping(value = "/auditSearchUserByOperator.do")
    public String auditSearchUserByOperator(@RequestParam String accountStatus, @RequestBody BasePage basePage, @RequestParam String userFullName) {
        try {
            Page<Map<String, Object>> list = systemManageService.auditSearchUserByOperator("X", accountStatus, userFullName, basePage.getPageRequest());
            return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }

    /**
     * 运维审核：按公司搜索
     * @param accountStatus: 用户状态(审核通过，正式用户："X" ； 待审核的用户："")
     * @param basePage: size page
     * @param userCompany: 用户公司
     * @return
     */
    @ApiOperation(value="运维人员审核按公司搜索", notes = "参数： ")
    @RequestMapping(value = "/auditSearchCompanyByOperator.do")
    public String auditSearchCompanyByOperator(@RequestParam String accountStatus, @RequestBody BasePage basePage, @RequestParam String userCompany) {
        try {
            Page<Map<String, Object>> list = systemManageService.auditSearchCompanyByOperator("X", accountStatus, userCompany, basePage.getPageRequest());
            return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }

    /**
     * 新增企业发票信息
     */
    @ApiOperation(value="新增企业发票信息", notes = "参数：companyInvoiceInformation")
    @RequestMapping(value = "/addCompanyInvoiceInformation.do")
    public String addCompanyInvoiceInformation(@RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        try {
            systemManageService.addCompanyInvoiceInformation(companyInvoiceInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }
    /**
     * 修改企业发票信息
     */
    @ApiOperation(value="修改企业发票信息", notes = "参数：CompanyInvoiceInformation")
    @RequestMapping(value = "/addCompanyInvoiceInformation.do")
    public String updateCompanyInvoiceInformation(@RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        try {
            systemManageService.updateCompanyInvoiceInformation(companyInvoiceInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }
    /**
     * 删除企业发票信息
     */
    @ApiOperation(value="删除企业发票信息", notes = "参数：CompanyInvoiceInformation ")
    @RequestMapping(value = "/deleteCompanyInvoiceInformation.do")
    public String deleteCompanyInvoiceInformation(@RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        try {
            systemManageService.deleteCompanyInvoiceInformation(companyInvoiceInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }
    /**
     * 查询企业发票信息
     */
    @ApiOperation(value="查询企业发票信息", notes = "参数：companyName Or accountName")
    @RequestMapping(value = "/selectCompanyInvoiceInformation.do")
    public String selectCompanyInvoiceInformation(String companyNameOrAccountName){
       return null;
    }

}
