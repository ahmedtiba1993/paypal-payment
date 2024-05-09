package com.ahmedtiba.paypal.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    // Injecting the 'paypal.client-id' property into this variable
    @Value("${paypal.client-id}")
    public String clientId;

    // Injecting the 'paypal.client-secret' property into this variable
    @Value("${paypal.client-secret}")
    private String clientSecret;

    // Injecting the 'paypal.mode' property into this variable
    @Value("${paypal.mode}")
    private String mode;

    // Bean configuration method to create an APIContext object
    @Bean
    public APIContext getAPIContext() {
        // Creating a new APIContext object with the injected credentials and configuration information
        return new APIContext(clientId, clientSecret, mode);
    }
}
