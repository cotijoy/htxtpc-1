package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import com.magustek.com.htxtpc.register.bean.RegisterHeader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RegisterHeaderDAO extends CrudRepository<RegisterHeader,Long> {
    RegisterHeader findByUsername(String username);
}
