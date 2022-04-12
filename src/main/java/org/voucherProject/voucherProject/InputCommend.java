package org.voucherProject.voucherProject;

import java.util.Arrays;

public enum InputCommend {
    EXIT,
    CREATE,
    LIST;

    public static InputCommend is(String input) {
        return Arrays.stream(InputCommend.values())
                .filter(i -> i.equals(InputCommend.valueOf(input.toUpperCase())))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
