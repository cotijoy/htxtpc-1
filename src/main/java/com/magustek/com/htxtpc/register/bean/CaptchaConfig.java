package com.magustek.com.htxtpc.register.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ApiModel(value = "CaptchaConfig-短信+验证码配置")
@Component
@ConfigurationProperties(prefix = "captcha")
@Getter
@Setter
@ToString
public class CaptchaConfig {
    @ApiModelProperty(value = "区域Id")
    private String regionId;
    @ApiModelProperty(value = "秘钥")
    private String accessKeyId;
    @ApiModelProperty(value = "密码")
    private String accessSecret;
    @ApiModelProperty(value = "邮箱服务器")
    private String mailHost;
    @ApiModelProperty(value = "邮箱用户名")
    private String username;
    @ApiModelProperty(value = "邮箱授权码")
    private String password;
}
