package com.github.vladbaton.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {Username.UsernameValidator.class})
@Target({ElementType.FIELD}) // указать, на что применяется
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "Имя пользователя должно содержать только буквы";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class UsernameValidator implements ConstraintValidator<Username, String> {
        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            return s.matches("^[a-zA-Z]+$");
        }
    }

}