package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.presentation.validation.InputValidator;
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

}
