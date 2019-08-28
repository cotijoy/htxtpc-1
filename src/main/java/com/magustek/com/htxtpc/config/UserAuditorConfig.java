package com.magustek.com.htxtpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import java.util.Optional;

/**
 * 继承BaseEntity的实体类自动填充创建人、最后修改人字段
 * */

@Configuration
public class UserAuditorConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return Optional.of("system");
        }
        if (ctx.getAuthentication() == null) {
            return Optional.of("system");
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return Optional.of("system");
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(User.class)) {
            return Optional.of(((User) principal).getUsername());
        } else {
            return Optional.of("system");
        }
    }
}
