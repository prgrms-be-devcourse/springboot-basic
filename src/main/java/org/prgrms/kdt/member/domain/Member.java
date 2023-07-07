package org.prgrms.kdt.member.domain;

import java.util.UUID;

public class Member {
    private final UUID memberId;
    private final MemberName name;
    private final MemberStatus status;

    public Member(UUID memberId, String name, MemberStatus status) {
        this.memberId = memberId;
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
}
