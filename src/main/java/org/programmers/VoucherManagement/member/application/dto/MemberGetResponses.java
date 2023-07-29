package org.programmers.VoucherManagement.member.application.dto;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MemberGetResponses {
    private final List<MemberGetResponse> memberResponses;

    public MemberGetResponses(List<Member> members) {
        this.memberResponses = members
                .stream()
                .map(MemberGetResponse::toDto)
                .collect(Collectors.toList());
    }

    public List<MemberGetResponse> getGetMemberListRes() {
        return Collections.unmodifiableList(memberResponses);
    }
}
