package org.programers.vouchermanagement.wallet.domain;

import org.junit.jupiter.api.*;
import org.programers.vouchermanagement.global.exception.NoSuchDomainException;
import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.member.domain.MemberRepository;
import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherRepository;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class JdbcWalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Wallet wallet;

    private Voucher voucher;

    private Member member;

    @BeforeAll
    void setup() {
        voucher = new Voucher(new FixedAmountPolicy(100), VoucherType.FIXED_AMOUNT);
        member = new Member(MemberStatus.NORMAL);
        voucherRepository.save(voucher);
        memberRepository.save(member);
    }

    @Order(1)
    @Test
    void 지갑을_저장한다() {
        // given
        wallet = new Wallet(voucher, member);

        // when
        Wallet result = walletRepository.save(wallet);

        // then
        assertThat(result.getId()).isEqualTo(wallet.getId());
    }

    @Order(2)
    @Test
    void 지갑을_아이디로_조회한다() {
        // given & when
        Wallet result = walletRepository.getById(wallet.getId());

        // then
        assertThat(result.getId()).isEqualTo(wallet.getId());
    }

    @Order(3)
    @Test
    void 지갑을_바우처_아이디로_조회한다() {
        // given & when
        List<Wallet> result = walletRepository.findAllByVoucherId(wallet.getVoucher().getId());

        // then
        assertThat(result.get(0).getId()).isEqualTo(wallet.getId());
    }

    @Order(4)
    @Test
    void 지갑을_회원_아이디로_조회한다() {
        // given & when
        List<Wallet> result = walletRepository.findAllByMemberId(wallet.getMember().getId());

        // then
        assertThat(result.get(0).getId()).isEqualTo(wallet.getId());
    }

    @Order(5)
    @Test
    void 지갑을_바우처_아이디로_삭제한다() {
        // given & when
        walletRepository.deleteById(wallet.getId());

        // then
        assertThatThrownBy(() -> walletRepository.getById(wallet.getId()))
                .isInstanceOf(NoSuchDomainException.class)
                .hasMessage("존재하지 않는 지갑입니다.");
    }
}
