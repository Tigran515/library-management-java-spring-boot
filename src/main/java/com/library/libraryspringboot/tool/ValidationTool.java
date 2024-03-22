package com.library.libraryspringboot.tool;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationTool {

    private final Validator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationTool.class);

    public ValidationTool() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> void validateByGroup(T objectToValidate, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate, groups);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {
                String errorMsg = "Validation error: " + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage() + ", ";
                sb.append(errorMsg);
                LOGGER.error(errorMsg);
            }
            throw new IllegalArgumentException(String.valueOf(sb));

        }
    }

    public <T> void validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {
                String errorMsg = constraintViolation.getPropertyPath() + " (" + constraintViolation.getMessage() + "), ";
                sb.append(errorMsg);
                LOGGER.error(errorMsg);
            }
            throw new IllegalArgumentException("Validation failed for the following field(s): " + sb);
        }

    }

}

