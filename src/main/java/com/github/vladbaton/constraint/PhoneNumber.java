package com.github.vladbaton.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PhoneNumber.PhoneNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Номер телефона может состоять только из 11 цифр";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Long> {
        @Override
        public boolean isValid(Long number, ConstraintValidatorContext constraintValidatorContext) {
            return number >= 10000000000L && number <= 99999999999L;
        }
    }
}