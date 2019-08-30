package com.magustek.com.htxtpc.register.service;

import com.magustek.com.htxtpc.register.bean.RegisterModel;

import java.util.Map;

public interface RegisterModelService {

    Map<String,Object> register(RegisterModel registerModel) throws Exception;
}
