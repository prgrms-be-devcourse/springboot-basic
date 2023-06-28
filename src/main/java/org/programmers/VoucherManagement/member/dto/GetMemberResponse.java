package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

public class GetMemberResponse {
    private UUID memberUUID;
    private String name;
    private MemberStatus memberStatus;

    public GetMemberResponse(UUID memberUUID, String name, MemberStatus memberStatus){
        this.memberUUID = memberUUID;
        this.name = name;
        this.memberStatus = memberStatus;
    }

    public static GetMemberResponse toDto(Member member){
        return new GetMemberResponse(member.getMemberUUID(), member.getName(), member.getMemberStatus());
    }
}
