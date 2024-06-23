package com.bookstore.authenticationservices.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("com.bookstore.authenticationservices")
public class ApplicationProperties {
    private String jwtSharedSecret;
    private long jwtTtl;
    private int successStatusCode;
    private String successMessage;
}
