package org.programmers.VoucherManagement.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.programmers.VoucherManagement.member.dto.UpdateMemberRequest;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 정보(status)를 수정할 수 있다. - 성공")
    void updateMember_IdAndDto_Success() {
        //given
        UUID memberId = UUID.randomUUID();
        Member saveMember = new Member(memberId, "Kim", MemberStatus.WHITE);
        memberRepository.insert(saveMember);
        UpdateMemberRequest updateRequestDto = new UpdateMemberRequest(MemberStatus.BLACK);

        //mocking
        given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(saveMember));

        //when
        memberService.updateMember(memberId, updateRequestDto);

        //then
        Member updateMember = memberRepository.findById(memberId).get();
        assertThat(updateMember.getMemberStatus()).isEqualTo(updateRequestDto.getMemberStatus());
    }

    @Test
    @DisplayName("회원Id를 입력받아 회원을 삭제할 수 있다. - 성공")
    void deleteMember_Id_Success() {
        //given
        UUID memberId = UUID.randomUUID();
        Member saveMember = new Member(memberId, "Kim", MemberStatus.BLACK);
        memberRepository.insert(saveMember);

        //mocking
        given(memberRepository.findById(saveMember.getMemberUUID())).willReturn(Optional.ofNullable(saveMember));

        //when
        memberService.deleteMember(saveMember.getMemberUUID());

        //then
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).delete(any(Member.class));
    }

    @Test
    @DisplayName("저장되어 있는 모든 멤버를 조회할 수 있다. - 성공")
    void getAllMembers_EqualsListOfMembers() {
        //given
        Member member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
        Member member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.WHITE);
        List<Member> memberList = Arrays.asList(member1, member2);

        //mocking
        given(memberRepository.findAll()).willReturn(memberList);

        //when
        GetMemberListResponse response = memberService.getAllMembers();

        //then
        assertThat(response).isNotNull();
        List<GetMemberResponse> responseExpect = memberList.stream()
                .map(GetMemberResponse::toDto)
                .collect(Collectors.toList());
        assertThat(response.getGetMemberListRes()).containsExactlyElementsOf(responseExpect);
    }

    @Test
    @DisplayName("저장되어 있는 모든 블랙리스트 멤버를 조회할 수 있다. - 성공")
    void getAllBlackMembers_EqualsListOfMembers() {
        //given
        Member member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
        Member member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.BLACK);
        Member member3 = new Member(UUID.randomUUID(), "Lee", MemberStatus.WHITE);
        List<Member> blackMemberList = Arrays.asList(member1, member2, member3)
                .stream()
                .filter(m -> m.getMemberStatus() == MemberStatus.BLACK)
                .collect(Collectors.toList());

        //mocking
        given(memberRepository.findAllByMemberStatus()).willReturn(blackMemberList);

        //when
        GetMemberListResponse response = memberService.getAllBlackMembers();

        //then
        assertThat(response).isNotNull();
        List<GetMemberResponse> responseExpect = blackMemberList.stream()
                .map(GetMemberResponse::toDto)
                .collect(Collectors.toList());
        assertThat(response.getGetMemberListRes()).containsExactlyElementsOf(responseExpect);
    }
}
