package com.weeklyMission.member.service;

import com.weeklyMission.member.domain.Member;
import com.weeklyMission.member.repository.FileMemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final FileMemberRepository memberRepository;

    public MemberService(FileMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getBlackList(){
        return memberRepository.getBlackList();
    }
}
