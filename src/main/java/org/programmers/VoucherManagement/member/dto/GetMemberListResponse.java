package org.programmers.VoucherManagement.member.dto;

import org.programmers.VoucherManagement.member.domain.Member;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetMemberListResponse {
    private final List<GetMemberResponse> getMemberResponseList;

    public GetMemberListResponse(List<Member> members) {
        this.getMemberResponseList = members
                .stream()
                .map(GetMemberResponse::toDto).collect(Collectors.toList());
    }

    public List<GetMemberResponse> getGetMemberListRes() {
        return Collections.unmodifiableList(getMemberResponseList);
    }
}
