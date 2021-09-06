package org.prgrms.kdt.customer.domain.vo;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;

public enum Role {
    GUEST("손님"),
    USER("사용자"),
    ADMIN("관리자"),
    BLACKLIST("블랙리스트");

    private String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

    public static Role findByRoleType(String inputType) {
        return Arrays.stream(Role.values())
                .filter(b -> b.role.equals(inputType))
                .findAny()
                .orElseThrow(() -> new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE));
    }
}
