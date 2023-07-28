package org.programmers.VoucherManagement.member.domain;

import java.util.UUID;

public class Member {

    private UUID memberId;
    private String name;
    private MemberStatus memberStatus;

    public Member(UUID memberId, String name, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void changeMemberStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}
