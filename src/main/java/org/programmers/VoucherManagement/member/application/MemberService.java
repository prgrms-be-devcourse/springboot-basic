package org.programmers.VoucherManagement.member.application;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.member.dao.MemberRepository;
import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;

    public List<GetMemberResponse> getBlackMemberList(){
        return repository.findAllByMemberStatus()
                .stream()
                .map(GetMemberResponse::toDto)
                .collect(Collectors.toList());
    }
}
