package org.prgms.kdt.application.Member.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Member {
    private UUID memberId;
    private String name;

    public Member(UUID memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                '}';
    }
}
