package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public class GetMemberRes {
    private final UUID memberID;
    private final String name;
    private final MemberStatus memberStatus;

    public GetMemberRes(UUID memberID, String name, MemberStatus memberStatus) {
        this.memberID = memberID;
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public UUID getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public static GetMemberRes toDto(Member member) {
        return new GetMemberRes(member.getMemberUUID(), member.getName(), member.getMemberStatus());
    }
}
