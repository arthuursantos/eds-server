package com.vilt.server.service;

import com.vilt.server.config.KeycloakConfig;
import com.vilt.server.domain.user.UserDTO;
import jakarta.ws.rs.core.Response;
import org.apache.catalina.Realm;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    public KeycloakService() {
        this.keycloak = KeycloakConfig.getInstance();
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
        Response response = realmResource().users().create(user);

        if (response.getStatus() == 201) {
            assignRole(user.getUsername(), "user");
        }

    }

    public void assignRole(String userName, String role) {

        String clientUuid = realmResource()
                .clients()
                .findByClientId("demo-client")
                .getFirst()
                .getId();

        String userId = realmResource()
                .users()
                .searchByUsername(userName, true)
                .getFirst()
                .getId();

        RoleRepresentation roleRepresentation = realmResource()
                .clients()
                .get(clientUuid)
                .roles()
                .get(role)
                .toRepresentation();

        realmResource()
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientUuid)
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
