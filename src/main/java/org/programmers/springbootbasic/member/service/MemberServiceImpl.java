package org.programmers.springbootbasic.member.service;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.member.domain.Member;
import org.programmers.springbootbasic.web.dto.MemberDto;
import org.programmers.springbootbasic.member.domain.SignedMember;
import org.programmers.springbootbasic.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member signUp(String name, String email) {
        return memberRepository.insert(new SignedMember(name, email));
    }

    @Override
    public List<MemberDto> getAllMembers() {
        List<MemberDto> dtoList = new ArrayList<>();
        memberRepository.findAll().forEach(
                member -> dtoList.add(new MemberDto(member.getMemberId(), member.getName(), member.getEmail(),
                        member.getLastLoginAt(), member.getSignedUpAt(), member.getVouchers()))
        );
        return dtoList;
    }
}