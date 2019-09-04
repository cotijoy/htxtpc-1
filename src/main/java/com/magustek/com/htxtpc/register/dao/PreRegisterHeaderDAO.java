package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import org.springframework.data.repository.CrudRepository;

public interface PreRegisterHeaderDAO extends CrudRepository<PreRegisterHeader,Long> {
    PreRegisterHeader findByPhoneNum (String phoneNum);
    PreRegisterHeader findByUsernameAndPassword (String username, String password);
}
