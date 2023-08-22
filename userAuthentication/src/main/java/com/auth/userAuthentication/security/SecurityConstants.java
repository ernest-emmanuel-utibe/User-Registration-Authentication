package com.auth.userAuthentication.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ERNEST EMMANUEL UTIBE
 * */

@Component
@Data
@ConfigurationProperties(prefix = "security.constants")
public class SecurityConstants {
    private Long jwtExpTime;
    private String jwtSecret;
    private String allowedOrigins;
}
