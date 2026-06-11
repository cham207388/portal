package com.abc.jobportal.security.config;

// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.stereotype.Component;

import java.time.Duration;

// @Component
// @ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Secret key used to sign JWTs.
     */
    private String secret;

    /**
     * Token expiration as a Duration (supports values like "24h", "30m", "PT24H").
     */
    private Duration expiration = Duration.ofHours(24);

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Duration getExpiration() {
        return expiration;
    }

    public void setExpiration(Duration expiration) {
        this.expiration = expiration;
    }
}
