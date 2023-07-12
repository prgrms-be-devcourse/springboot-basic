package kr.co.springbootweeklymission.member.application;

import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.response.ResponseStatus;
import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.member.presentation.dto.request.MemberReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    void getMemberById_특정_회원을_조회_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when & then
        assertThat(memberService.getMemberById(member.getMemberId()).getMemberId())
                .isEqualTo(member.getMemberId());
    }

    @Test
    void getMemberById_특정_회원을_조회_NotFoundException() {
        //given
        given(memberRepository.findById(any())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> memberService.getMemberById(UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ResponseStatus.FAIL_NOT_FOUND_MEMBER.getMessage());
    }

    @Test
    void 특정_회원의_정보를_변경_SUCCESS() {
        //given
        Member member = MemberCreators.createBlackMember();
        MemberReqDTO.UPDATE update = MemberCreators.updateMemberInformation(MemberStatus.WHITE);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        memberService.updateMemberById(member.getMemberId(), update);

        //then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.WHITE);
    }
}
