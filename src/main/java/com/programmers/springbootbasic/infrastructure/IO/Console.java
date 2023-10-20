package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.presentation.IOManager;
import com.programmers.springbootbasic.presentation.validation.InputValidator;
import jakarta.annotation.PostConstruct;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Console implements IOManager {
    private final Scanner scanner = new Scanner(System.in);
    private InputValidator<String> stringValidator;
    private InputValidator<String> integerValidator;

    @PostConstruct
    private void setValidator() {
        InputValidator<String> stringValidator = new InputValidator<>();
        stringValidator.addValidator(input -> {
            if(input == null || input.isEmpty()) {
                return "입력 값이 없습니다.";
            }
            return null;
        });
        this.stringValidator = stringValidator;

        InputValidator<String> integerValidator = new InputValidator<>();
        integerValidator.addValidator(input -> {
            if(input == null || input.isEmpty()) {
                return "입력 값이 없습니다.";
            }
            return null;
        });
        integerValidator.addValidator(input -> {
            try {
                Integer.parseInt(input);
                return null;
            } catch (NumberFormatException e) {
                return "숫자가 아닙니다.";
            }
        });
        this.integerValidator = integerValidator;
    }

    public String collectStringInput(String message) {
        while(true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = stringValidator.validate(result);

            if(errors.isEmpty()) {
                return result;
            }
            errors.get().forEach(this::print);
        }
    }

    public int collectIntegerInput(String message) {
        while(true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = integerValidator.validate(result);

            if(errors.isEmpty()) {
                return Integer.parseInt(result);
            }
            errors.get().forEach(this::print);
        }
    }

    public void print(String message) {
        System.out.println(message);
    }

}
