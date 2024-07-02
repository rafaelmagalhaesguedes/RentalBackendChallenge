package com.rental.controller.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The type Auth dto.
 */
public record AuthDto(
    @NotBlank(message = "Email cannot be blank")
    @Email
    String email,

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,255}$", message = "Password must be between 8 and 255 characters long, and must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    String password
) { }

