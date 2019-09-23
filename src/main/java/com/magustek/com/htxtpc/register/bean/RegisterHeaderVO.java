package com.magustek.com.htxtpc.register.bean;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "RegisterHeaderVO-用户注册抬头")
public class RegisterHeaderVO extends RegisterHeader {
    private String captcha;
}
