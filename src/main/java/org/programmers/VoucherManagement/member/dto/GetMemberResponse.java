package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public class GetMemberResponse {
    private UUID memberID;
    private String name;
    private MemberStatus memberStatus;

    public GetMemberResponse(UUID memberID, String name, MemberStatus memberStatus) {
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

    public static GetMemberResponse toDto(Member member) {
        return new GetMemberResponse(member.getMemberUUID(), member.getName(), member.getMemberStatus());
    }
}
