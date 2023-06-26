package org.programers.vouchermanagement.member.domain;

public enum MemberStatus {
    NORMAL, BLACK;

    public boolean isBlack() {
        return this == MemberStatus.BLACK;
    }
}
