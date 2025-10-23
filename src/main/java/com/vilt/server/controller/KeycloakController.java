package com.vilt.server.controller;

import com.vilt.server.domain.user.UserDTO;
import com.vilt.server.domain.user.UserLoginDTO;
import com.vilt.server.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class KeycloakController {

    private final KeycloakService service;

    @PostMapping
    public void create(@RequestBody UserDTO dto) {
        service.create(dto);
    }

    @PostMapping("/login")
    public Object login(@RequestBody UserLoginDTO dto) {
        return service.login(dto);
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('user')")
    public String test() {
        return "vai corinthians";
    }

}
