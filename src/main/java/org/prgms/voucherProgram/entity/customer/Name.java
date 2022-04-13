package org.prgms.voucherProgram.entity.customer;

import java.util.Objects;

public class Name {
    private static final int MAX_NAME_LENGTH = 20;

    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름이 비어있습니다.");
        }

        if (MAX_NAME_LENGTH < name.length()) {
            throw new IllegalArgumentException("[ERROR] 이름은 20자 이상을 넘을 수 없습니다.");
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
}
