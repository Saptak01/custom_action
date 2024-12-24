package com.prosolvr.QED.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HttpHeadersConfig {
    @Bean(name = "cookie")
    @Lazy
    public String getHttpReqCookieAuth() {
        return "authz_token=ewEYrDrBWclHrEIN60yaaVWaQXwF9IHxqN4CWyRErOXIVN8pRl8lOU+1M6mwx5oRZrXxhp1ObyB1DiyUy3+qXQ==";
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
