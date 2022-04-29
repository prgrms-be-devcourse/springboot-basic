package com.pppp0722.vouchermanagement.member.model;

import java.util.UUID;

public class Member {

    private final int MAX_NAME_LENGTH = 20;

    private final UUID memberId;
    private final String name;

    public Member(UUID memberId, String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("The name must be 20 characters or less.");
        }

        this.memberId = memberId;
        this.name = name;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{" + "memberId=" + memberId
            + ", name='" + name + '\''
            + '}';
    }
}
