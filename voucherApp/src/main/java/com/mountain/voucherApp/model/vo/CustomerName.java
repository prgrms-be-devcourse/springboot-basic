package com.mountain.voucherApp.model.vo;

import java.util.Objects;

import static com.mountain.voucherApp.common.constants.ErrorMessage.NOT_BLANK;

public class CustomerName {

    private final String name;

    public CustomerName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException(NOT_BLANK.getMessage());
        }
        this.name = name;
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