package com.vilt.server.domain.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "user")
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    @Indexed(unique = true)
    private String login;

    private String password;
}
