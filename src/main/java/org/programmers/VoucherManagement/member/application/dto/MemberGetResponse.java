package org.programmers.VoucherManagement.member.application.dto;

import lombok.Builder;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

@Builder
public record MemberGetResponse(
        String memberId,
        String name,
        MemberStatus memberStatus
) {
    public static MemberGetResponse toDto(Member member) {
        return MemberGetResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .memberStatus(member.getMemberStatus())
                .build();
    }
}
