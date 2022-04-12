package org.prgms.kdt.application.Member.service;

import lombok.RequiredArgsConstructor;
import org.prgms.kdt.application.Member.domain.Member;
import org.prgms.kdt.application.Member.repository.MemberRepository;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findBlacklist () {
        return memberRepository.getBlacklist();
    }
}
