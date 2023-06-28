package org.programmers.VoucherManagement.member.domain;

import ch.qos.logback.core.pattern.color.BlackCompositeConverter;

public enum MemberStatus {
    BLACK,
    WHITE;

    public boolean isBlack() {
        return this.equals(BLACK);
    }

    public boolean isWhite() {
        return this.equals(WHITE);
    }
}
