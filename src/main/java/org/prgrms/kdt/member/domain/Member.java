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

    public Member(String name, MemberStatus status) {
        this.memberId = UUID.randomUUID();
        this.name = new MemberName(name);
        this.status = status;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public MemberName getMemberName() {
        return name;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public void setName(MemberName name) {
        this.name = name;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }
}
