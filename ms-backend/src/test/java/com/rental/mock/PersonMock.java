package com.rental.mock;

import com.rental.entity.Person;
import com.rental.security.Role;

import java.util.UUID;

/**
 * The type Person mock.
 */
public class PersonMock {

    /**
     * The constant PERSON_01.
     */
    public static Person PERSON_01 = new Person(
            UUID.randomUUID(),
            "Thais Guedes",
            "thais",
            "thais@email.com",
            "password",
            Role.USER
    );

    /**
     * The constant PERSON_02.
     */
    public static Person PERSON_02 = new Person(
            UUID.randomUUID(),
            "Ana Guedes",
            "ana",
            "ana@email.com",
            "password",
            Role.USER
    );

    /**
     * The constant PERSON_CREATION.
     */
    public static Person PERSON_CREATION = new Person(
            "Rafael Guedes",
            "rafa",
            "rafa@email.com",
            "password",
            Role.MANAGER
    );
    
    /**
     * The constant PERSON_UPDATE.
     */
    public static Person PERSON_UPDATE = new Person(
            "André Guedes",
            "andre",
            "andre@email.com",
            "password",
            Role.ADMIN
    );
}
