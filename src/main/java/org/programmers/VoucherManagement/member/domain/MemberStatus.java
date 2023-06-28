package org.programmers.VoucherManagement.member.domain;

import java.util.Arrays;

public enum MemberStatus {
    BLACK,
    WHITE;

    public boolean isBlack() {
        return this.equals(BLACK);
    }

    public boolean isWhite() {
        return this.equals(WHITE);
    }

    public static MemberStatus from(String status) {
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
