package org.programmers.VoucherManagement.member.dto.response;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public class MemberGetResponse {
    private final UUID memberID;
    private final String name;
    private final MemberStatus memberStatus;

    public MemberGetResponse(UUID memberID, String name, MemberStatus memberStatus) {
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

    public static MemberGetResponse toDto(Member member) {
        return new MemberGetResponse(member.getMemberUUID(), member.getName(), member.getMemberStatus());
    }
}
