package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.presentation.validation.InputValidator;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsoleValidatorConfig {

    @Bean
    public InputValidator<String> getStringValidator() {
        InputValidator<String> stringValidator = new InputValidator<>();
        stringValidator.addValidator(this::validateString);
        return stringValidator;
    }

    @Bean
    public InputValidator<String> getIntegerValidator() {
        InputValidator<String> integerValidator = new InputValidator<>();
        integerValidator.addValidator(this::validateString);
        integerValidator.addValidator(this::validateInteger);
        return integerValidator;
    }

    @Bean
    public InputValidator<String> getLongValidator() {
        InputValidator<String> longValidator = new InputValidator<>();
        longValidator.addValidator(this::validateString);
        longValidator.addValidator(this::validateInteger);
        return longValidator;
    }

    @Bean
    public InputValidator<String> getUUIDValidator() {
        InputValidator<String> uuidValidator = new InputValidator<>();
        uuidValidator.addValidator(this::validateString);
        uuidValidator.addValidator(this::validateUUID);
        return uuidValidator;
    }

    private String validateString(String input) {
        if (input == null || input.isEmpty()) {
            return "입력 값이 없습니다.";
        }
        return null;
    }

    private String validateInteger(String input) {
        try {
            Integer.parseInt(input);
            return null;
        } catch (NumberFormatException e) {
            return "숫자가 아닙니다.";
        }
    }

    private String validateLong(String input) {
        try {
            Long.parseLong(input);
            return null;
        } catch (NumberFormatException e) {
            return "Long 형태의 숫자가 아닙니다.";
        }
    }

    private String validateUUID(String input) {
        try {
            UUID.fromString(input);
            return null;
        } catch (IllegalArgumentException e) {
            return "UUID 형태의 문자가 아닙니다.";
        }
    }

}
