package com.vilt.server.controller;

import com.vilt.server.domain.user.UserAccount;
import com.vilt.server.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public record CreateUserRequest(@NotBlank String login, @NotBlank String password) {}
    public record UserResponse(Long id, String login) {}

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest req) {
        UserAccount entity = new UserAccount();
        entity.setLogin(req.login());
        entity.setPassword(req.password());
        UserAccount saved = userRepository.save(entity);
        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(new UserResponse(saved.getId(), saved.getLogin()));
    }

    @GetMapping
    public List<UserResponse> list() {
        return userRepository.findAll()
                .stream().map(u -> new UserResponse(u.getId(), u.getLogin())).toList();
    }
}
