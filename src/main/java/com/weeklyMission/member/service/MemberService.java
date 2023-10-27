package com.weeklyMission.member.service;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.repository.MemberRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse save(MemberRequest member){
        Member createMember = memberRepository.save(member.toEntity());
        return MemberResponse.of(createMember);
    }

    public List<MemberResponse> findAll(){
        return memberRepository.findAll().stream()
            .map(MemberResponse::of)
            .toList();
    }

    public MemberResponse findById(UUID id){
        Member member = memberRepository.findById(id)
            .orElseThrow();
        return MemberResponse.of(member);
    }

    public void deleteById(UUID id){
        memberRepository.deleteById(id);
    }
}
