package org.programmers.VoucherManagement.member.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.member.dao.MemberRepository;
import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.member.dto.GetMemberRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public GetMemberListRes getBlackMemberList() {
        List<GetMemberRes> getMemberList = repository.findAllByMemberStatus()
                .stream()
                .map(GetMemberRes::toDto)
                .collect(Collectors.toList());

        return new GetMemberListRes(getMemberList);
    }
}
