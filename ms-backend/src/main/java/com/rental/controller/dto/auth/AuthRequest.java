package com.rental.controller.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * The type Auth dto.
 */
public record AuthRequest(
    @NotBlank(message = "Email cannot be blank")
    @Email
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password
) { }

