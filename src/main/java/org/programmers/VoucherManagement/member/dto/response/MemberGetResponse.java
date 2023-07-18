package org.programmers.VoucherManagement.member.dto.response;

import lombok.Builder;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;

import java.util.UUID;

@Builder
public record MemberGetResponse(
        UUID memberId,
        String name,
        MemberStatus memberStatus
) {
    public static MemberGetResponse toDto(Member member) {
        return MemberGetResponse.builder()
                .memberId(member.getMemberUUID())
                .name(member.getName())
                .memberStatus(member.getMemberStatus())
                .build();
    }
}
