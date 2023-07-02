package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.vouchermember.creators.VoucherMemberCreators;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcVoucherMemberRepositoryTest {
    @Autowired
    VoucherMemberRepository voucherMemberRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @Order(1)
    void save_특정_고객에게_바우처_할당_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        Member member = MemberCreators.createWhiteMember();
        VoucherMember voucherMember = VoucherMemberCreators.createVoucherMember(voucher, member);

        //when
        voucherRepository.save(voucher);
        memberRepository.save(member);
        VoucherMember actual = voucherMemberRepository.save(voucherMember);

        //then
        assertThat(actual.getVoucherMemberId()).isEqualTo(voucherMember.getVoucherMemberId());
    }
}
