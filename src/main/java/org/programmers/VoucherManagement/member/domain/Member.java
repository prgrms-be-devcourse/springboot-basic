package org.programmers.VoucherManagement.member.domain;

import java.util.UUID;

public class Member {
    private UUID memberUUID;
    private String name;
    private MemberStatus memberStatus;

    public Member(UUID memberUUID, String name, MemberStatus memberStatus) {
        this.memberUUID = memberUUID;
        this.name = name;
        this.memberStatus = memberStatus;
    }
}
