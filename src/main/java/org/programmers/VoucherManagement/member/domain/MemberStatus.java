package org.programmers.VoucherManagement.member.domain;

import java.util.Arrays;

import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_EXIST_MEMBER_STATUS;

public enum MemberStatus {
    BLACK,
    WHITE;

    public static MemberStatus from(String status) {
        try {
            return Arrays.stream(values())
                    .filter(enumValue -> enumValue.name().equalsIgnoreCase(status))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_MEMBER_STATUS.getMessage()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_EXIST_MEMBER_STATUS.getMessage(), e);
        }
    }
}
