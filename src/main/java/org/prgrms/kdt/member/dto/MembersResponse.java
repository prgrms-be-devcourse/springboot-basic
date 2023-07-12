package org.prgrms.kdt.member.dto;

import org.prgrms.kdt.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public class MembersResponse {
    private final List<MemberResponse> members;

    public MembersResponse(List<MemberResponse> members) {
        this.members = members;
    }

    public static MembersResponse of(List<Member> members) {
        List<MemberResponse> membersResponse = members.stream().map(MemberResponse::new).collect(Collectors.toList());
        return new MembersResponse(membersResponse);
    }

    public List<MemberResponse> getMembers(){
        return List.copyOf(members);
    }

}
