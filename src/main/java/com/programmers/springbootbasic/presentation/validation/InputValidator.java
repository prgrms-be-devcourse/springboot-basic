package com.programmers.springbootbasic.presentation.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InputValidator<T> {

    protected final List<Validator<T>> validators = new ArrayList<>();

    @FunctionalInterface
    public interface Validator<T> {

        String getErrorMessage(T val);
    }

    public Optional<List<String>> validate(T input) {
        var errs = getError(input);
        if (errs.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errs);
    }

    public void addValidator(Validator<T> validator) {
        this.validators.add(validator);
    }

    private List<String> getError(T input) {
        return validators.stream()
            .map(validator -> validator.getErrorMessage(input))
            .filter(Objects::nonNull)
            .toList();
    }
}
