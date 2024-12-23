package com.prosolvr.QED.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HttpHeadersConfig {
    @Bean(name = "cookie")
    @Lazy
    public String getHttpReqCookieAuth() {
        return "authz_token=bLxdzBSp+0LZ46qrXriQ6RdZbiEq6SVWfs6F6vXz/ca0EsLQ37VeUbYNW312PFIQ4QF/6DfkrYfrhGMuBsxmGw==";
    }

    @Bean(name = "accountId")
    @Lazy
    public String getAccId() {
        return "145888242";
    }

    @Bean(name = "applicationId")
    @Lazy
    public String appId() {
        return "565105925";
    }
}
