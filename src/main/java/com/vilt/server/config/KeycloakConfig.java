package com.vilt.server.config;

import lombok.NoArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Configuration
public class KeycloakConfig {

    static Keycloak keycloak = null;

    @Bean
    public static Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:9090")
                    .realm("master")
                    .username(System.getenv("KEYCLOAK_ADMIN"))
                    .password(System.getenv("KEYCLOAK_ADMIN_PASSWORD"))
                    .clientId("admin-cli")
                    .build();
        }
        return keycloak;
    }

}
