package org.prgrms.kdt.member.domain;

import java.util.UUID;

public class Member {
    private final UUID memberId;
    private final String memberName;
    private final MemberStatus status;

    public Member(UUID memberId, String memberName, MemberStatus status) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.status = status;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
