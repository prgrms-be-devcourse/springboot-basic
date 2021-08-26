package org.prgrms.kdt.member.domain;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Name {

    private static final String NAME_VALIDATOR = "^[가-힣]{2,10}$";

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    public void validate(String name) {
        if (!Pattern.matches(NAME_VALIDATOR, name)) {
            throw new InvalidArgumentException(ErrorMessage.NAME);
        }
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String name() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
