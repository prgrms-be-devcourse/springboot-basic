package kr.co.springbootweeklymission.member.application;

import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
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
