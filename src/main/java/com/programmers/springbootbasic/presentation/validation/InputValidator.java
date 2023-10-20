package com.programmers.springbootbasic.presentation.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputValidator<T> {

    protected final List<validator<T>> validators = new ArrayList<>();

    @FunctionalInterface
    public interface validator<T> {
        String getErrorMessage(T val);
    }

    public Optional<List<String>> validate(T input) {
        var errs = getError(input);
        if(errs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errs);
    }

    public void addValidator(validator<T> validator) {
        this.validators.add(validator);
    }

    private List<String> getError(T input) {
        List<String> errorMessages = new ArrayList<>();
        for (validator<T> validator : validators) {
            String errorMessage = validator.getErrorMessage(input);
            if (errorMessage != null) {
                errorMessages.add(errorMessage);
            }
        }
        return errorMessages;
    }
}
