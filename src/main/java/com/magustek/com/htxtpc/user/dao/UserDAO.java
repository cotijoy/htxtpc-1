package com.magustek.com.htxtpc.user.dao;

import com.magustek.com.htxtpc.user.bean.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User,Long> {
}
