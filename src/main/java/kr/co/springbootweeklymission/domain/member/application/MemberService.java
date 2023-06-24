package kr.co.springbootweeklymission.domain.member.application;

import kr.co.springbootweeklymission.domain.member.dao.MemberRepository;
import kr.co.springbootweeklymission.domain.member.api.response.MemberResDTO;
import kr.co.springbootweeklymission.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberResDTO.READ> getMembersByBlack() {
        final List<Member> members = memberRepository.findMembersByBlack();

        return members.stream()
                .map(Member::toMemberReadDto)
                .toList();
    }
}
