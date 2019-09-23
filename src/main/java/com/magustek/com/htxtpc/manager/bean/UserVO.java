package com.magustek.com.htxtpc.manager.bean;

import com.magustek.com.htxtpc.user.bean.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "UserVO-用户信息")
public class UserVO extends User {
    private String userCompany; //用户公司
}
