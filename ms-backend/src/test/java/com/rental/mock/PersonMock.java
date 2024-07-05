package com.rental.mock;

import com.rental.entity.Person;
import com.rental.security.Role;

import java.util.UUID;

public class PersonMock {
    public static Person PERSON_CREATION = new Person(
            "Rafael Guedes",
            "rafa",
            "rafa@email.com",
            "password",
            Role.MANAGER
    );

    public static Person PERSON_WITH_ID_1 = new Person(
            UUID.randomUUID(),
            "Thais Guedes",
            "thais",
            "thais@email.com",
            "password",
            Role.USER
    );

    public static Person PERSON_WITH_ID_2 = new Person(
            UUID.randomUUID(),
            "Ana Guedes",
            "ana",
            "ana@email.com",
            "password",
            Role.USER
    );

    public static Person PERSON_UPDATE = new Person(
            "André Guedes",
            "andre",
            "andre@email.com",
            "password",
            Role.ADMIN
    );
}
