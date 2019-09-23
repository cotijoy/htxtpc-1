package com.magustek.com.htxtpc.manager.controller;

import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformationVO;
import com.magustek.com.htxtpc.manager.bean.ReceiverAddressInformationVO;
import com.magustek.com.htxtpc.manager.bean.UserVO;
import com.magustek.com.htxtpc.manager.service.CompanyInvoiceInformationService;
import com.magustek.com.htxtpc.manager.service.ReceiverAddressInformationService;
import com.magustek.com.htxtpc.user.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.user.bean.ReceiverAddressInformation;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.manager.service.RegisterLineitemAuditService;
import com.magustek.com.htxtpc.util.base.BaseEntity;
import com.magustek.com.htxtpc.util.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Api("后台管理")
@Slf4j
@RestController
@RequestMapping("/mro/systemManager")
public class SystemManageController {
    private RegisterLineitemAuditService registerLineitemAuditService;
    private CompanyInvoiceInformationService companyInvoiceInformationService;
    private ReceiverAddressInformationService receiverAddressInformationService;
    private BaseResponse resp;

    public SystemManageController(RegisterLineitemAuditService registerLineitemAuditService,
                                  CompanyInvoiceInformationService companyInvoiceInformationService) {
        this.registerLineitemAuditService = registerLineitemAuditService;
        this.companyInvoiceInformationService = companyInvoiceInformationService;
        this.resp = new BaseResponse();
    }

    /**
     * 企业管理员审核搜索
     * @param httpSession
     * @param vo
     * @return
     */
    @ApiOperation(value="企业管理员审核搜索", notes = "参数：accountStatus、userFullName、size、page ")
    @RequestMapping(value = "/auditSearchByAdmin.do")
    public String auditSearchByAdmin(HttpSession httpSession, @RequestBody UserVO vo){
        User user = (User)httpSession.getAttribute("user");
        if (vo.getUserFullName() == null || vo.getUserFullName().equals("")){
            try {
                Page<User> list = registerLineitemAuditService.auditAllSearchByAdmin(user.getCompanyCode(), vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }else {
            try {
                Page<User> list = registerLineitemAuditService.auditSearchByAdmin(user.getCompanyCode(), vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }
    }

    /**
     * 运维审核：按人名搜索
     * @param vo
     * @return
     */
    @ApiOperation(value="运维人员审核按人名搜索", notes = "参数：accountStatus、userFullName、page、size ")
    @RequestMapping(value = "/auditSearchUserByOperator.do")
    public String auditSearchUserByOperator(@RequestBody UserVO vo) {
        if (vo.getUserFullName() == null || vo.getUserFullName().equals("")){
            //默认搜索
            try {
                Page<Map<String, Object>> list = registerLineitemAuditService.auditAllSearchUserByOperator(vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }else {
            try {
                Page<Map<String, Object>> list = registerLineitemAuditService.auditSearchUserByOperator(vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }
    }

    /**
     * 运维审核：按公司搜索 accountStatus: 用户状态(审核通过，正式用户："X" ； 待审核的用户："")
     * @param vo
     * @return
     */
    @ApiOperation(value="运维人员审核按公司搜索", notes = "参数：accountStatus、userCompany、size、page")
    @RequestMapping(value = "/auditSearchCompanyByOperator.do")
    public String auditSearchCompanyByOperator(@RequestBody UserVO vo) {
        if (vo.getUserCompany() == null || vo.getUserCompany().equals("")){
            try {
                Page<Map<String, Object>> list = registerLineitemAuditService.auditAllSearchCompanyByOperator(vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }else{
            try {
                Page<Map<String, Object>> list = registerLineitemAuditService.auditSearchCompanyByOperator(vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }
    }

    /**
     * 新增或修改企业发票信息
     * @param companyInvoiceInformation
     * @return
     */
    @ApiOperation(value="修改企业发票信息", notes = "参数：CompanyInvoiceInformation")
    @RequestMapping(value = "/addOrUpdateCompanyInvoiceInformation.do")
    public String addOrUpdateCompanyInvoiceInformation(@RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        try {
            companyInvoiceInformationService.addOrUpdateCompanyInvoiceInformation(companyInvoiceInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }

    /**
     * 删除企业发票信息
     * @param companyInvoiceInformation
     * @return
     */
    @ApiOperation(value="删除企业发票信息", notes = "参数：主键 id ")
    @RequestMapping(value = "/deleteCompanyInvoiceInformation.do")
    public String deleteCompanyInvoiceInformation(@RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        try {
            companyInvoiceInformationService.deleteCompanyInvoiceInformation(companyInvoiceInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
        }
    }

    /**
     * 查询企业发票信息
     * @param httpSession
     * @param vo
     * @return
     */
    @ApiOperation(value="查询企业发票信息", notes = "参数：searchStr、page、size")
    @RequestMapping(value = "/selectCompanyInvoiceInformation.do")
    public String selectCompanyInvoiceInformation(HttpSession httpSession, @RequestBody CompanyInvoiceInformationVO vo){

        User user = (User)httpSession.getAttribute("user");
        if (vo.getSearchStr() == null || vo.getSearchStr().equals("")){
            try {
                Page<CompanyInvoiceInformation> list = companyInvoiceInformationService.findAllByCompanyCode(user.getCompanyCode(), vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }else{
            try {
                Page<CompanyInvoiceInformation> list = companyInvoiceInformationService.findAllByCompanyNameOrAccountName(user.getCompanyCode(), vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }
    }

    /**
     * 新增或修改收件地址
     * @param receiverAddressInformation
     * @return
     */
    @ApiOperation(value="新增或修改收件地址", notes = "参数：ReceiverAddressInformation")
    @RequestMapping(value = "/addOrUpdateReceiverAddressInformation")
    public String addOrUpdateReceiverAddressInformation(@RequestBody ReceiverAddressInformation receiverAddressInformation, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        try {
            receiverAddressInformation = receiverAddressInformationService.addOrUpdateReceiverAddressInformation(receiverAddressInformation, user.getUsername());
            return resp.setStateCode(BaseResponse.SUCCESS).setData(receiverAddressInformation).setMsg("成功").toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg("失败，原因是:"+e.getMessage()).toJson();
        }
    }

    /**
     * 删除收件地址
     * @param receiverAddressInformation
     * @return
     */
    @ApiOperation(value="删除收件地址", notes = "receiverAddressInformation")
    @RequestMapping(value = "/deleteReceiverAddressInformation")
    public String deleteReceiverAddressInformation(@RequestBody ReceiverAddressInformation receiverAddressInformation){
        try {
            receiverAddressInformationService.deleteReceiverAddressInformation(receiverAddressInformation);
            return resp.setStateCode(BaseResponse.SUCCESS).setMsg("删除成功").toJson();
        }catch (Exception e){
            return resp.setStateCode(BaseResponse.ERROR).setMsg("删除失败"+e.getMessage()).toJson();
        }
    }

    /**
     * 查询收件地址
     * @param vo
     * @return
     */
    @ApiOperation(value="查询企业发票信息", notes = "参数：searchStr、page、size")
    @RequestMapping(value = "/selectReceiverAddressInformation")
    public String selectReceiverAddressInformation(HttpServletRequest request, @RequestBody ReceiverAddressInformationVO vo){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        try {
            Page<ReceiverAddressInformation> page = receiverAddressInformationService.searchReceiverAddressInformationBysearchingContent(vo, user.getUsername());
            return resp.setStateCode(BaseResponse.SUCCESS).setData(page).setMsg("查询成功").toJson();
        } catch (Exception e) {
            return resp.setStateCode(BaseResponse.ERROR).setMsg("查询失败").toJson();
        }
    }



}
