package com.github.vladbaton.constraint;

import com.github.vladbaton.entity.User;
import com.github.vladbaton.repository.UserRepository;
import com.github.vladbaton.service.UserService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Documented
@Constraint(validatedBy = {FieldExists.fieldExistsConstraintValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ApplicationScoped
public @interface FieldExists {
    String message() default "Указанного атрибута нет в таблице БД: ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class fieldExistsConstraintValidator implements ConstraintValidator<FieldExists, String> {
        @Inject
        UserRepository userRepository;

        @Override
        public boolean isValid(String enteredField, ConstraintValidatorContext context) {
            return userRepository
                    .getEntityManager()
                    .createNativeQuery("SELECT column_name FROM information_schema.columns WHERE table_name = 'users'")
                    .getResultStream()
                    .anyMatch(field -> field.equals(enteredField.toLowerCase()));
        }
    }
}
