package com.github.vladbaton.constraint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NotNegative.NotNegativeConstraintValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ApplicationScoped
public @interface NotNegative {
    String message() default "Значение параметра не может быть отрицательным:";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class NotNegativeConstraintValidator implements ConstraintValidator<NotNegative, Integer> {
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value >= 0;
        }
    }
}
