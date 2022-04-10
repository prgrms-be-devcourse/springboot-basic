package org.prgrms.kdt.domain.member.model;

import java.util.UUID;

public class Member {
    private final UUID customerId;
    private final String name;

    public Member(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                '}';
    }
}
