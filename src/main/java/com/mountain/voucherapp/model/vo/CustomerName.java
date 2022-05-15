package com.mountain.voucherapp.model.vo;

import java.security.InvalidParameterException;
import java.util.Objects;

import static com.mountain.voucherapp.common.constants.ErrorMessage.NOT_BLANK;

public class CustomerName {

    private final String name;

    public CustomerName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new InvalidParameterException(NOT_BLANK.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerName customerName = (CustomerName) o;
        return Objects.equals(name, customerName.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}