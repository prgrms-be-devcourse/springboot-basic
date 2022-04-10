package com.pppp0722.vouchermanagement.member.service;

import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.member.repository.FileMemberRepository;
import com.pppp0722.vouchermanagement.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<List<Member>> getOptionalBlackList() {
        List<Member> optionalBlackList = memberRepository.getBlackList();

        if(optionalBlackList.isEmpty()) return Optional.empty();

        return Optional.of(optionalBlackList);
    }
}