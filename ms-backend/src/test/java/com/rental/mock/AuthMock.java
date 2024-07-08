package com.rental.mock;

import com.rental.controller.dto.auth.AuthDto;
import com.rental.entity.Person;
import com.rental.security.Role;

import java.util.UUID;

/**
 * The type Auth mock.
 */
public class AuthMock {

    public static String AUTH_URL = "/auth/login";

    public static String TOKEN = "my_token";

    public static String ERROR_MESSAGE = "Invalid email or password.";

    public static AuthDto LOGIN = new AuthDto(
            "test@test.com",
            "secret_key"
    );

    public static Person PERSON = new Person(
            UUID.randomUUID(),
            "Test User",
            "test",
            "test@test.com",
            "password",
            Role.USER
    );
}
