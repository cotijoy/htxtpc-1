package com.magustek.com.htxtpc.manager.controller;

import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformationVO;
import com.magustek.com.htxtpc.manager.bean.ReceiverAddressInformationVO;
import com.magustek.com.htxtpc.manager.bean.RegisterLineitemAuditVO;
import com.magustek.com.htxtpc.manager.service.CompanyInvoiceInformationService;
import com.magustek.com.htxtpc.manager.service.ReceiverAddressInformationService;
import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import com.magustek.com.htxtpc.manager.bean.CompanyInvoiceInformation;
import com.magustek.com.htxtpc.manager.bean.ReceiverAddressInformation;
import com.magustek.com.htxtpc.manager.service.RegisterLineitemAuditService;
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
                                  CompanyInvoiceInformationService companyInvoiceInformationService,
                                  ReceiverAddressInformationService receiverAddressInformationService) {
        this.registerLineitemAuditService = registerLineitemAuditService;
        this.companyInvoiceInformationService = companyInvoiceInformationService;
        this.receiverAddressInformationService = receiverAddressInformationService;
        this.resp = new BaseResponse();
    }

    /**
     * 注册信息查询
     * @param httpSession
     * @param vo
     * @return
     */
    @ApiOperation(value="注册信息查询", notes = "参数：auditFlag、aflag、searchStr、size、page ")
    @RequestMapping(value = "/selectRegisterInformation")
    public String selectRegisterInformation(HttpSession httpSession, @RequestBody RegisterLineitemAuditVO vo){
        PreRegisterHeader userInfo = (PreRegisterHeader) httpSession.getAttribute("userInfo");
        if (vo.getAuditFlag().equals("DSH")){
            vo.setAuditStatus("待审核");
        }else if (vo.getAuditFlag().equals("YSH")){
            vo.setAuditStatus("已审核");
        }
        if (vo.getAflag().equals("admin")){
            //管理员查询 运维管理员
            vo.setAflag("X");
            if (vo.getSearchStr() == null || vo.getSearchStr().equals("")){
                try {
                    Page<Map<String, Object>> list = registerLineitemAuditService.findAllRegisterInformationByAdmin(vo);
                    return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
                }catch (Exception e){
                    return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
                }
            }else {
                try {
                    Page<Map<String, Object>> list = registerLineitemAuditService.findRegisterInformationByAdmin(vo);
                    return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
                }catch (Exception e){
                    return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
                }
            }
        }else if (vo.getAflag().equals("supplier")){
            //供应商查询 企业管理员
            vo.setAflag("");
            if (vo.getSearchStr() == null || vo.getSearchStr().equals("")){
                try {
                    Page<Map<String, Object>> list = registerLineitemAuditService.findAllRegisterInformationBySupplier(userInfo.getCompanyCode(), vo);
                    return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
                }catch (Exception e){
                    return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
                }
            }else {
                try {
                    Page<Map<String, Object>> list = registerLineitemAuditService.findRegisterInformationBySupplier(userInfo.getCompanyCode(), vo);
                    return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
                }catch (Exception e){
                    return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
                }
            }
        }
        return resp.setStateCode(BaseResponse.ERROR).setMsg("查询失败").toJson();

    }

    /**
     * 新增或修改企业发票信息
     * @param companyInvoiceInformation
     * @return
     */
    @ApiOperation(value="新增或修改企业发票信息", notes = "参数：CompanyInvoiceInformation")
    @RequestMapping(value = "/saveCompanyInvoiceInformation")
    public String saveCompanyInvoiceInformation(HttpSession httpSession, @RequestBody CompanyInvoiceInformation companyInvoiceInformation){
        PreRegisterHeader userInfo = (PreRegisterHeader)httpSession.getAttribute("userInfo");
        companyInvoiceInformation.setCompanyCode(userInfo.getCompanyCode());
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
    @RequestMapping(value = "/deleteCompanyInvoiceInformation")
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
    @RequestMapping(value = "/selectCompanyInvoiceInformation")
    public String selectCompanyInvoiceInformation(HttpSession httpSession, @RequestBody CompanyInvoiceInformationVO vo){

        PreRegisterHeader userInfo = (PreRegisterHeader)httpSession.getAttribute("userInfo");
        if (vo.getSearchStr() == null || vo.getSearchStr().equals("")){
            try {
                Page<CompanyInvoiceInformation> list = companyInvoiceInformationService.findAllByCompanyCode(userInfo.getCompanyCode(), vo);
                return resp.setStateCode(BaseResponse.SUCCESS).setData(list).toJson();
            }catch (Exception e){
                return resp.setStateCode(BaseResponse.ERROR).setMsg(e.getMessage()).toJson();
            }
        }else{
            try {
                Page<CompanyInvoiceInformation> list = companyInvoiceInformationService.findAllByCompanyNameOrAccountName(userInfo.getCompanyCode(), vo);
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
    @RequestMapping(value = "/saveReceiverAddressInformation")
    public String saveReceiverAddressInformation(@RequestBody ReceiverAddressInformation receiverAddressInformation,HttpServletRequest request){
        HttpSession session = request.getSession();
        PreRegisterHeader userInfo = (PreRegisterHeader)session.getAttribute("userInfo");
        try {
            receiverAddressInformation = receiverAddressInformationService.addOrUpdateReceiverAddressInformation(receiverAddressInformation, userInfo.getUsername());
            //receiverAddressInformation = receiverAddressInformationService.addOrUpdateReceiverAddressInformation(receiverAddressInformation, "Jamie");
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
    @ApiOperation(value="查询收件地址", notes = "ReceiverAddressInformationVO")
    @RequestMapping(value = "/selectReceiverAddressInformation")
    public String selectReceiverAddressInformation(HttpServletRequest request, @RequestBody ReceiverAddressInformationVO vo){
        HttpSession session = request.getSession();
        PreRegisterHeader userInfo = (PreRegisterHeader)session.getAttribute("userInfo");
        try {
            Page<ReceiverAddressInformation> page = receiverAddressInformationService.searchReceiverAddressInformationBysearchingContent(vo, userInfo.getUsername());
            //Page<ReceiverAddressInformation> page = receiverAddressInformationService.searchReceiverAddressInformationBysearchingContent(vo, "Jamie");
            return resp.setStateCode(BaseResponse.SUCCESS).setData(page).setMsg("查询成功").toJson();
        } catch (Exception e) {
            return resp.setStateCode(BaseResponse.ERROR).setMsg("查询失败").toJson();
        }
    }



}
