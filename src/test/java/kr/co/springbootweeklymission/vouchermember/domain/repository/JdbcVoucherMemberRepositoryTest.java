package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.vouchermember.creators.VoucherMemberCreators;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JdbcVoucherMemberRepositoryTest {
    @Autowired
    VoucherMemberRepository voucherMemberRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    Member member;
    Voucher voucher1;
    Voucher voucher2;

    @BeforeAll
    void beforeAll() {
        member = MemberCreators.createWhiteMember();
        voucher1 = VoucherCreators.createFixedDiscount();
        voucher2 = VoucherCreators.createPercentDiscount();
        memberRepository.save(member);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
    }

    @Test
    @Order(1)
    void save_특정_고객에게_바우처_할당_SUCCESS() {
        //given
        VoucherMember voucherMember = VoucherMemberCreators.createVoucherMember(voucher1, member);

        //when
        VoucherMember actual = voucherMemberRepository.save(voucherMember);

        //then
        assertThat(actual.getVoucherMemberId()).isEqualTo(voucherMember.getVoucherMemberId());
    }

    @Test
    @Order(2)
    void findVouchersMembersByMemberId_특정_고객의_바우처들을_조회_SUCCESS() {
        //given
        VoucherMember voucherMember = VoucherMemberCreators.createVoucherMember(voucher2, member);
        voucherMemberRepository.save(voucherMember);

        //when
        List<VoucherMember> actual = voucherMemberRepository.findVouchersMembersByMemberId(member.getMemberId());

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    @Order(3)
    void deleteVoucherMemberByVoucherAndMember_특정_회원의_특정_바우처를_삭제_SUCCESS() {
        //given & then
        voucherMemberRepository.deleteByVoucherIdAndMemberId(voucher1.getVoucherId(), member.getMemberId());

        //then
        assertThat(voucherMemberRepository.findVouchersMembersByMemberId(member.getMemberId())).hasSize(1);
    }
}
