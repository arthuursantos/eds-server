package com.vilt.server.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String emailId,
                      @NotBlank String username,
                      @NotBlank String password,
                      @NotBlank String firstName,
                      @NotBlank String lastName) {
}
