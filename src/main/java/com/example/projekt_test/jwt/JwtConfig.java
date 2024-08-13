package com.example.projekt_test.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpiration;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public long getTokenExpirationAfterDays() {
        return 16L * 24L * 60L * 60L * 1000L;
    }
}
