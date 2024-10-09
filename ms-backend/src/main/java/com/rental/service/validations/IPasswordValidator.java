package com.rental.service.validations;

import java.util.List;

public interface IPasswordValidator {
    List<String> validate(String pass);
}
