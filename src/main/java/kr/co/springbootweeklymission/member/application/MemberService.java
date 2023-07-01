package kr.co.springbootweeklymission.member.application;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.api.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.member.api.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberReqDTO.CREATE create) {
        final Member saveMember = Member.toMember(create);
        memberRepository.save(saveMember);
    }

    public MemberResDTO.READ getMemberById(UUID memberId) {
        final Member readMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        return MemberResDTO.READ.toMemberReadDto(readMember);
    }

    public List<MemberResDTO.READ> getMembersByBlack() {
        final List<Member> members = memberRepository.findMembersByBlack();

        return members.stream()
                .map(MemberResDTO.READ::toMemberReadDto)
                .toList();
    }

    @Transactional
    public void updateMemberById(UUID memberId,
                                 MemberReqDTO.UPDATE update) {
        final Member updateMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        updateMember.updateMemberInformation(update);
        memberRepository.update(updateMember);
    }

    public void deleteMemberById(UUID memberId) {
        final Member deleteMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        memberRepository.delete(deleteMember);
    }
}
