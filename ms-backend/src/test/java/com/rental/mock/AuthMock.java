package com.rental.mock;

import com.rental.controller.dto.auth.AuthRequest;
import com.rental.entity.Person;
import com.rental.enums.Role;

import java.util.UUID;

/**
 * The type Auth mock.
 */
public class AuthMock {

    public static String AUTH_URL = "/auth/login";

    public static String TOKEN = "my_token";

    public static String ERROR_MESSAGE = "Invalid email or password.";

    public static AuthRequest LOGIN = new AuthRequest(
            "test@test.com",
            "secret_key"
    );

    public static Person PERSON = new Person(
            UUID.randomUUID(),
            "Test Person",
            "test",
            "test@test.com",
            "password",
            Role.USER
    );
}
