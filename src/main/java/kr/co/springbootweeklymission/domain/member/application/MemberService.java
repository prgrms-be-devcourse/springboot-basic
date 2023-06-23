package kr.co.springbootweeklymission.domain.member.application;

import kr.co.springbootweeklymission.domain.member.dao.MemberRepository;
import kr.co.springbootweeklymission.domain.member.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberResDTO.READ> getMembersByBlack() {
        List<Member> members = memberRepository.findMembersByBlack();

        return members.stream()
                .map(Member::toMemberReadDto)
                .collect(Collectors.toList());
    }
}
