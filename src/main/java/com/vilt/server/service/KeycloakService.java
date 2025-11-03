package com.vilt.server.service;

import com.vilt.server.config.KeycloakConfig;
import com.vilt.server.domain.user.UserDTO;
import com.vilt.server.domain.user.UserLoginDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final WebClient webCLient;

    @Value("${spring.security.oauth2.resourceserver.jwt.token-uri}")
    private String tokenUri;

    public KeycloakService() {
        this.keycloak = KeycloakConfig.getInstance();
        this.webCLient = WebClient.builder().build();
    }

    private RealmResource realmResource() {
        return keycloak.realm("realm-demo");
    }

    public void create(UserDTO dto) {

        CredentialRepresentation credential = createPasswordCredentials(dto.password());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(dto.emailId());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.emailId());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        realmResource().users().create(user);

    }

    public Object login(UserLoginDTO dto) {
        return webCLient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + "demo-client" +
                        "&client_secret=" + System.getenv("KEYCLOAK_CLIENT_SECRET") +
                        "&grant_type=password" +
                        "&username=" + dto.username() +
                        "&password=" + dto.password())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
