package com.prosolvr.QED.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HttpHeadersConfig {
    @Bean(name = "cookie")
    @Lazy
    public String getHttpReqCookieAuth() {
        return "authz_token=c2ObocL1vN/Ak4yKoWaIHDxTpoA9/Fy/h9Nr9rrZXIRgGoq7VR9sF9x7AZ02yslc7131y1oHCUX61gnYSnIgjw==";
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
