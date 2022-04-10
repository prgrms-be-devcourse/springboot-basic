package com.programmers.part1.member;

import com.programmers.part1.member.entity.MemberDto;
import com.programmers.part1.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository<UUID,MemberDto> memberRepository;

    public MemberService(MemberRepository<UUID,MemberDto> memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getAllBlackMembers() throws IOException {
        return memberRepository.findAllBlackMember();
    }

}
