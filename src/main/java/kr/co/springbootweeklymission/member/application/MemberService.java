package kr.co.springbootweeklymission.member.application;

import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.response.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
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
    public Member createMember(MemberReqDTO.CREATE create) {
        final Member saveMember = Member.toMember(create);
        return memberRepository.save(saveMember);
    }

    public MemberResDTO.READ getMemberById(UUID memberId) {
        final Member readMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        return MemberResDTO.READ.toMemberReadDto(readMember);
    }

    public List<MemberResDTO.READ> getBlackMembers() {
        final List<Member> members = memberRepository.findBlackMembers();

        return members.stream()
                .map(MemberResDTO.READ::toMemberReadDto)
                .toList();
    }

    public List<MemberResDTO.READ> getMembersAll() {
        final List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberResDTO.READ::toMemberReadDto)
                .toList();
    }

    @Transactional
    public UUID updateMemberById(UUID memberId,
                                 MemberReqDTO.UPDATE update) {
        final Member updateMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        updateMember.updateMemberInformation(update);
        memberRepository.update(updateMember);
        return memberId;
    }

    @Transactional
    public UUID deleteMemberById(UUID memberId) {
        final Member deleteMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_MEMBER));
        memberRepository.delete(deleteMember);
        return memberId;
    }
}
