package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.RegisterHeader;
import org.springframework.data.repository.CrudRepository;

public interface RegisterHeaderDAO extends CrudRepository<RegisterHeader,Long> {
    RegisterHeader findByPhoneNum (String phoneNum);
    RegisterHeader findByUsernameAndPassword (String username, String password);
}
