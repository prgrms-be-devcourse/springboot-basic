package org.prgrms.kdt.domain.customer;

import org.prgrms.kdt.exception.Message;
import org.prgrms.kdt.exception.ValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Name {
    private static final String PATTERN = "^[가-힣a-zA-Z]*$";

    private String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    public static Name valueOf(String name) {
        return new Name(name);
    }

    public String name() {
        return name;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void validate(String name) {
        if (!Pattern.matches(PATTERN, name)) {
            throw new ValidationException(Message.WRONG_FORMAT_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name(), name1.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name());
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }
}
