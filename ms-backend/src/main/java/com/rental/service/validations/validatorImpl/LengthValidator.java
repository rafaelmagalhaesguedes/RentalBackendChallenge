package com.rental.service.validations.validatorImpl;

import org.springframework.stereotype.Component;

@Component
public class LengthValidator implements IValidator {

    @Override
    public String validate(String pass) {
        if (pass != null && pass.length() >= 8) {
            return null;
        }

        return "Password must have at least 8 characters";
    }
}