package com.prosolvr.QED.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HttpHeadersConfig {
    @Bean(name = "cookie")
    @Lazy
    public String getHttpReqCookieAuth() {
        return "authz_token=GqFELbKMnnFK+LeK8RbQc0HZhIdQleDlJge/+3foK3ccDnxKRbTFsbGtq06u1gC1fo2RaWlBdYT/k21XkazcEA==";
    }

    @Bean(name = "accountId")
    @Lazy
    public String getAccId() {
        return "711893038";
    }

    @Bean(name = "applicationId")
    @Lazy
    public String appId() {
        return "497271765";
    }
}
