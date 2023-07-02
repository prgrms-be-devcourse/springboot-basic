package kr.co.springbootweeklymission.vouchermember.application;

import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.api.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.vouchermember.creators.VoucherMemberCreators;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import kr.co.springbootweeklymission.vouchermember.domain.repository.VoucherMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VoucherMemberServiceTest {
    @InjectMocks
    VoucherMemberService voucherMemberService;

    @Mock
    VoucherMemberRepository voucherMemberRepository;

    @Test
    void findVouchersByMemberId_특정_회원이_가진_바우처들을_조회_SUCCESS() {
        //given
        Member member = MemberCreators.createWhiteMember();
        List<VoucherMember> voucherMembers = new ArrayList<>();
        voucherMembers.add(VoucherMemberCreators.createVoucherMember(member));
        voucherMembers.add(VoucherMemberCreators.createVoucherMember(member));
        given(voucherMemberRepository.findVouchersMembersByMemberId(any())).willReturn(voucherMembers);

        //then
        List<VoucherResDTO.READ> actual = voucherMemberService.getVouchersByMemberId(member.getMemberId());

        //then
        assertThat(actual).hasSize(2);
    }
}
