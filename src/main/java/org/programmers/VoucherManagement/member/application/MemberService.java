package org.programmers.VoucherManagement.member.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public GetMemberListResponse getBlackMemberList() {
        List<GetMemberResponse> getMemberList = repository.findAllByMemberStatus()
                .stream()
                .map(GetMemberResponse::toDto)
                .collect(Collectors.toList());

        return new GetMemberListResponse(getMemberList);
    }
}
