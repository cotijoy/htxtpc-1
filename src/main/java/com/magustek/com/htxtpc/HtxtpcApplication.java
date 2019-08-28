package com.magustek.com.htxtpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class HtxtpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtxtpcApplication.class, args);
    }

}
