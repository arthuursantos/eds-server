package com.vilt.server.controller;

import com.vilt.server.domain.user.UserDTO;
import com.vilt.server.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class KeycloakController {

    private final KeycloakService service;

    @PostMapping
    public void create(@RequestBody UserDTO dto) {
        service.create(dto);
    }

}
