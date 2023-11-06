package com.weeklyMission.member.service;

import com.weeklyMission.exception.AlreadyExistsException;
import com.weeklyMission.exception.ExceptionMessage;
import com.weeklyMission.exception.NotFoundException;
import com.weeklyMission.member.domain.Member;
import com.weeklyMission.member.dto.MemberRequest;
import com.weeklyMission.member.dto.MemberResponse;
import com.weeklyMission.member.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse save(MemberRequest member){
        Member memberEntity = member.toEntity();
        if(memberRepository.checkJoinEmail(memberEntity)){
            throw new AlreadyExistsException(ExceptionMessage.ALREADY_JOIN.getMessage());
        }
        Member createMember = memberRepository.save(memberEntity);
        return MemberResponse.of(createMember);
    }

    public List<MemberResponse> findAll(){
        return memberRepository.findAll().stream()
            .map(MemberResponse::of)
            .toList();
    }

    public MemberResponse findById(String id){
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_MEMBER.getMessage()));
        return MemberResponse.of(member);
    }

    public void deleteById(String id){
        memberRepository.deleteById(id);
    }
}
