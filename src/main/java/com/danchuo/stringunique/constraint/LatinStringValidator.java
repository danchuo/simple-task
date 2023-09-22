package com.danchuo.stringunique.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class LatinStringValidator implements ConstraintValidator<LatinStringConstraint, String> {

    private static final Pattern LATIN_AND_NUMBERS = Pattern.compile("^[a-zA-Z0-9]*$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null &&
                !value.isEmpty() &&
                value.length() <= 100 &&
                LATIN_AND_NUMBERS.matcher(value).matches();
    }
}

