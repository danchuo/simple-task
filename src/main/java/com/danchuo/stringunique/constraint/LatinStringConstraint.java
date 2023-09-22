package com.danchuo.stringunique.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = LatinStringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface LatinStringConstraint {
    String message() default "Строка должна состоять только из латинских букв и цифр и иметь длину от 1 до 100 символов.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
