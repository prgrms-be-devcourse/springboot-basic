package org.prgrms.kdt.member.service.dto;

import org.prgrms.kdt.member.domain.Member;

import java.util.UUID;

public record MemberResponse(UUID memberId, String memberName, String memberStatus) {
    public MemberResponse(Member member) {
        this(member.getMemberId(), member.getMemberName(), member.getStatus().getDescripton());
    }
}
