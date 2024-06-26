package com.rental.controller.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The type Auth dto.
 */
public record AuthDto(
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username must be less than or equal to 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,255}$", message = "Username must be between 3 and 255 characters long and can only contain letters, numbers, periods, underscores, and hyphens")
    String username,

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,255}$", message = "Password must be between 8 and 255 characters long, and must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    String password
) { }

