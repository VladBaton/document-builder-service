package com.github.vladbaton.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DoxcFileExtension.FileExtensionValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoxcFileExtension {
    String message() default "Неверное имя файла";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class FileExtensionValidator implements ConstraintValidator<DoxcFileExtension, String> {
        @Override
        public boolean isValid(String filename, ConstraintValidatorContext context) {
            return filename != null && filename.endsWith(".docx");
        }
    }
}
