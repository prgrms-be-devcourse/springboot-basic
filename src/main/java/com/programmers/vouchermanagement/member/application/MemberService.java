package com.programmers.vouchermanagement.member.application;

import com.programmers.vouchermanagement.member.domain.Member;
import com.programmers.vouchermanagement.member.dto.MemberResponseDto;
import com.programmers.vouchermanagement.member.exception.MemberNotFoundException;
import com.programmers.vouchermanagement.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponseDto> readAllBlackList() {

        List<Member> members = memberRepository.findAllBlack();

        if (members.isEmpty()) {
            throw new MemberNotFoundException();
        }

        List<MemberResponseDto> memberResponseDtos = members.stream()
                .map(member -> new MemberResponseDto(member.getMemberId(), member.getName(), member.getMemberType()))
                .toList();

        return memberResponseDtos;
    }
}
