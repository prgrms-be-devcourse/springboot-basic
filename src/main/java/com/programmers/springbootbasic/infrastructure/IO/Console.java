package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.presentation.IOManager;
import com.programmers.springbootbasic.presentation.validation.InputValidator;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Console implements IOManager {

    private final Scanner scanner;
    private final InputValidator<String> stringValidator;
    private final InputValidator<String> integerValidator;
    private final InputValidator<String> longValidator;
    private final InputValidator<String> uuidValidator;

    public Console(
        @Qualifier("getStringValidator") InputValidator<String> stringValidator,
        @Qualifier("getIntegerValidator") InputValidator<String> integerValidator,
        @Qualifier("getLongValidator") InputValidator<String> longValidator,
        @Qualifier("getUUIDValidator") InputValidator<String> uuidValidator
    ) {
        this.longValidator = longValidator;
        this.uuidValidator = uuidValidator;
        this.scanner = new Scanner(System.in);
        this.stringValidator = stringValidator;
        this.integerValidator = integerValidator;
    }

    public String collectStringInput(String message) {
        while (true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = stringValidator.validate(result);

            if (errors.isEmpty()) {
                return result;
            }
            errors.get().forEach(this::print);
        }
    }

    public int collectIntegerInput(String message) {
        while (true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = integerValidator.validate(result);

            if (errors.isEmpty()) {
                return Integer.parseInt(result);
            }
            errors.get().forEach(this::print);
        }
    }

    public Long collectLongInput(String message) {
        while (true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = longValidator.validate(result);

            if (errors.isEmpty()) {
                return Long.parseLong(result);
            }
            errors.get().forEach(this::print);
        }
    }

    public UUID collectUUIDInput(String message) {
        while (true) {
            print(message);
            var result = scanner.nextLine().trim();
            var errors = uuidValidator.validate(result);

            if (errors.isEmpty()) {
                return UUID.fromString(result);
            }
            errors.get().forEach(this::print);
        }
    }

    public void print(String message) {
        System.out.println(message);
    }

}
