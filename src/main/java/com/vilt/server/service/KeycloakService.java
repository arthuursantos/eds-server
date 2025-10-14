package com.vilt.server.service;

import com.vilt.server.config.KeycloakConfig;
import com.vilt.server.domain.user.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    public KeycloakService() {
        this.keycloak = KeycloakConfig.getInstance();
    }

    private UsersResource usersResource() {
        return keycloak.realm("realm-demo").users();
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

        usersResource().create(user);

    }

    public void assignRole(String userId, String roleName) {

        var roleRepresentation = keycloak.realm("realm-demo")
                .clients()
                .get("demo-client")
                .roles()
                .get(roleName)
                .toRepresentation();

        usersResource()
                .get(userId)
                .roles()
                .clientLevel("demo-client")
                .add(Collections.singletonList(roleRepresentation));
    }

    public CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


}
