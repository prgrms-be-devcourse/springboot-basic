package org.prgrms.kdt.member.domain;

import java.util.UUID;

public class Member {
    private final UUID memberId;
    private MemberName name;
    private MemberStatus status;

    public Member(UUID memberId, String name, MemberStatus status) {
        this.memberId = memberId;
        this.name = new MemberName(name);
        this.status = status;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return name.getName();
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setName(MemberName name) {
        this.name = name;
    }
}
