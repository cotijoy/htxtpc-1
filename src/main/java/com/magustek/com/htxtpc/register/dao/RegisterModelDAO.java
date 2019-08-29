package com.magustek.com.htxtpc.register.dao;

import com.magustek.com.htxtpc.register.bean.PreRegisterHeader;
import org.springframework.data.repository.CrudRepository;

public interface RegisterModelDAO extends CrudRepository<PreRegisterHeader, Long> {

}
