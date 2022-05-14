package org.prgrms.springbootbasic.entity.customer;

import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_NAME_FORMAT_EXP_MSG;

import java.util.Objects;
import org.prgrms.springbootbasic.exception.InvalidNameFormatException;

public class Name {

    private static final int LIMIT_LEN = 10;

    private String name;

    public Name(String name) {
        validateNameFormat(name);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private void validateNameFormat(String name) {
        if (name.length() >= LIMIT_LEN) {
            throw new InvalidNameFormatException(INVALID_NAME_FORMAT_EXP_MSG);
        }
    }
}
