package org.prgms.voucherProgram.domain.customer.domain;

import java.util.Objects;

import org.prgms.voucherProgram.domain.customer.exception.WrongNameException;

public class Name {
    public static final String ERROR_BLANK_NAME_MESSAGE = "[ERROR] 이름이 비어있습니다.";
    public static final String ERROR_NAME_LENGTH_OVER_MAX_LENGTH = "[ERROR] 이름은 20자 이상을 넘을 수 없습니다.";
    private static final int MAX_NAME_LENGTH = 20;
    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new WrongNameException(ERROR_BLANK_NAME_MESSAGE);
        }

        if (MAX_NAME_LENGTH < name.length()) {
            throw new WrongNameException(ERROR_NAME_LENGTH_OVER_MAX_LENGTH);
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
        Name otherName = (Name)o;
        return this.name.equals(otherName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
