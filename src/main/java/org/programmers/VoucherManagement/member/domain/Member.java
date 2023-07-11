package org.programmers.VoucherManagement.member.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class Member {

    private UUID memberUUID;
    private String name;
    private MemberStatus memberStatus;

    public Member(UUID memberUUID, String name, MemberStatus memberStatus) {
        this.memberUUID = memberUUID;
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public UUID getMemberUUID() {
        return memberUUID;
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
