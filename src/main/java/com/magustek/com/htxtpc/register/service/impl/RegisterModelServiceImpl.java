package com.magustek.com.htxtpc.register.service.impl;

import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.dao.*;
import com.magustek.com.htxtpc.register.service.RegisterModelService;
import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import com.magustek.com.htxtpc.register.bean.PreRegisterLineitemDocument;
import com.magustek.com.htxtpc.user.dao.CompanyDAO;
import com.magustek.com.htxtpc.util.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("PlanHeaderService")
public class RegisterModelServiceImpl implements RegisterModelService {
    private RegisterModelDAO registerModelDAO;
    private PreRegisterHeaderDAO preRegisterHeaderDAO;
    private PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO;
    private RegisterHeaderDAO registerHeaderDAO;
    private RegisterLineitemDocumentDAO registerLineitemDocumentDAO;
    private CompanyDAO companyDAO;

    public RegisterModelServiceImpl(RegisterModelDAO registerModelDAO,
                                    PreRegisterHeaderDAO preRegisterHeaderDAO,
                                    PreRegisterLineitemDocumentDAO preRegisterLineitemDocumentDAO,
                                    RegisterHeaderDAO registerHeaderDAO,
                                    RegisterLineitemDocumentDAO registerLineitemDocumentDAO,
                                    CompanyDAO companyDAO) {
        this.registerModelDAO = registerModelDAO;
        this.preRegisterHeaderDAO = preRegisterHeaderDAO;
        this.preRegisterLineitemDocumentDAO = preRegisterLineitemDocumentDAO;
        this.registerHeaderDAO = registerHeaderDAO;
        this.registerLineitemDocumentDAO = registerLineitemDocumentDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    public Map<String, Object> register(RegisterModel registerModel) {
        PreRegisterHeader preRegisterHeader = new PreRegisterHeader();
        PreRegisterLineitemDocument preRegisterLineitemDocument = new PreRegisterLineitemDocument();
        Map<String, Object> registResult = new HashMap<>();
        String companyCode = companyDAO.findByCreditCode(registerModel.getCreditCode()).getCompanyCode();
        BeanUtils.copyProperties(registerModel,preRegisterHeader);
        preRegisterHeader.setCompanyCode(companyCode);
        preRegisterHeaderDAO.save(preRegisterHeader);

        try {
            List<PreRegisterLineitemDocument> preRegisterLineitemDocumentLists = (List<PreRegisterLineitemDocument>)
                    this.extractDocumentInfo(registerModel.
                            getDocumentTypes(),registerModel.getDocumentNums(),registerModel.getDocuments()).
                            get("preRegisterLineitemDocumentLists");

            for(PreRegisterLineitemDocument obj:preRegisterLineitemDocumentLists) {
                obj.setCompanyCode(companyCode);
                obj.setUsername(registerModel.getUsername());
                obj.setDocumentSerialNum(CommonUtil.getUUID());
                preRegisterLineitemDocumentDAO.save(obj);
            }
        } catch (Exception e) {
            registResult.put("status",false);
            log.info(registerModel.getUsername() + ":注册失败");
            return registResult;
        }
        registResult.put("status",true);
        return registResult;

    }

    /**
     * 获取上传文件的信息
     * @param documentTypes，documentNums，documents
     * @return
     */
    private Map<String,Object> extractDocumentInfo(String[] documentTypes, Integer[] documentNums, MultipartFile[] documents) throws Exception{
        Map<String,Object> result = new HashMap<>();
        List<PreRegisterLineitemDocument> preRegisterLineitemDocumentLists = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < documentTypes.length; i++) {
            for (int j = index; j < index + documentNums[i]; j++) {
                //附件名
                String name = documents[j].getOriginalFilename();
                //附件后缀
                //String fileSuffix = name.substring(name.lastIndexOf(".") + 1);
                String fileSuffix = documents[j].getContentType();
                //文件大小
                String size = documents[j].getSize() + "";
                //附件数据
                byte[] data = documents[j].getBytes();
                String base64data = CommonUtil.encodeByBase64(data);
                //文件类型
                String type = documentTypes[i];
                PreRegisterLineitemDocument preRegisterLineitemDocument = new PreRegisterLineitemDocument(null,null,null,size,fileSuffix,base64data);
                preRegisterLineitemDocumentLists.add(preRegisterLineitemDocument);
            }
            index += documentNums[i];
        }
        result.put("preRegisterLineitemDocumentLists",preRegisterLineitemDocumentLists);
        return result;
    }


}
