package org.prgrms.kdt.member.service.dto;

import org.prgrms.kdt.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public record MemberResponses(List<MemberResponse> members) {

    public static MemberResponses of(List<Member> members) {
        List<MemberResponse> membersResponse = members.stream().map(MemberResponse::new).collect(Collectors.toList());
        return new MemberResponses(membersResponse);
    }

    @Override
    public List<MemberResponse> members() {
        return List.copyOf(members);
    }

}
