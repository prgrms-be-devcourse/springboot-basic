package org.prgrms.kdt.customer.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class Name {

    private static final String NAME_VALIDATOR = "^[가-힣]{2,10}$";

    private String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    public void validateName(String name) {
        if (!StringUtils.hasText(name) || !Pattern.matches(NAME_VALIDATOR, name)) {
            throw new InvalidArgumentException(ErrorMessage.NAME);
        }
    }

    @Override
    public String toString() {
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
