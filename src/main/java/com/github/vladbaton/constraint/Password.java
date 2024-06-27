package com.github.vladbaton.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {Password.PasswordValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Пароль должен содержать не меньше 8 символов и содержать хотя бы одну букву верхнего регистра, одну букву нижнего регистра и одну цифру";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class PasswordValidator implements ConstraintValidator<Password, String> {
        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            return s.length() >= 8 && s.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$");
        }
    }
}