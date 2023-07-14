package org.prgrms.kdt.member.dto;

import org.prgrms.kdt.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public record MembersResponse(List<MemberResponse> members) {

    public static MembersResponse of(List<Member> members) {
        List<MemberResponse> membersResponse = members.stream().map(MemberResponse::new).collect(Collectors.toList());
        return new MembersResponse(membersResponse);
    }

    @Override
    public List<MemberResponse> members() {
        return List.copyOf(members);
    }

}
