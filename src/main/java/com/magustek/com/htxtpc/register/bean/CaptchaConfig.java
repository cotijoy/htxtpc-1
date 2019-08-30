package com.magustek.com.htxtpc.register.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "captcha")
@Getter
@Setter
@ToString
public class CaptchaConfig {
    private String regionId;
    private String accessKeyId;
    private String accessSecret;
    private String mailHost;
    private String userName;
    private String password;
}
