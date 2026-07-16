package com.abc.jobportal.security.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CorsPropertiesBindingTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(CorsPropertiesTestConfig.class);

    @Test
    void bindsAllowCredentialsFromRelaxedPropertyName() {
        contextRunner
                .withPropertyValues(
                        "app.cors.allowed-origins=http://localhost:5173",
                        "app.cors.allowed-methods=*",
                        "app.cors.allowed-headers=*",
                        "app.cors.allow-credentials=true",
                        "app.cors.max-age=3600")
                .run(context -> {
                    CorsProperties corsProperties = context.getBean(CorsProperties.class);
                    assertThat(corsProperties.getAllowCredentials()).isTrue();
                    assertThat(corsProperties.getAllowedOrigins()).containsExactly("http://localhost:5173");
                    assertThat(corsProperties.getMaxAge()).isEqualTo(3600L);
                });
    }

    @Test
    void doesNotBindMisspelledAllowedCredentialsKey() {
        contextRunner
                .withPropertyValues("app.cors.allowed-credentials=true")
                .run(context -> {
                    CorsProperties corsProperties = context.getBean(CorsProperties.class);
                    assertThat(corsProperties.getAllowCredentials()).isNull();
                });
    }

    @EnableConfigurationProperties(CorsProperties.class)
    static class CorsPropertiesTestConfig {
    }
}
