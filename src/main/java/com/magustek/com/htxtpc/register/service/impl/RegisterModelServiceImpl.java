package com.magustek.com.htxtpc.register.service.impl;

import com.magustek.com.htxtpc.register.bean.RegisterModel;
import com.magustek.com.htxtpc.register.dao.RegisterModelDAO;
import com.magustek.com.htxtpc.register.service.RegisterModelService;
import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import com.magustek.com.htxtpc.register.bean.PreRegisterLineitemDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service("PlanHeaderService")
public class RegisterModelServiceImpl implements RegisterModelService {
    private RegisterModelDAO registerModelDAO;

    public RegisterModelServiceImpl(RegisterModelDAO registerModelDAO){
        this.registerModelDAO = registerModelDAO;
    }

    @Override
    public Map<String, Object> register(RegisterModel registerModel) {
        PreRegisterHeader preRegisterHeader = new PreRegisterHeader();
        PreRegisterLineitemDocument preRegisterLineitemDocument = new PreRegisterLineitemDocument();
        preRegisterHeader.setUsername(registerModel.getUsername());
        preRegisterHeader.setAdminFlag(registerModel.getAdminFlag());

        return null;
    }

    private PreRegisterHeader savePreRegisterHeader(PreRegisterHeader preRegisterHeader) throws Exception {

        return null;
    }


    private PreRegisterLineitemDocument saveRegisterLineitemDocument(PreRegisterLineitemDocument preRegisterLineitemDocument) throws Exception {
        return null;
    }


}
