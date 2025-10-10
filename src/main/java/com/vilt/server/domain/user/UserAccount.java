package com.vilt.server.domain.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user_account")
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
}
