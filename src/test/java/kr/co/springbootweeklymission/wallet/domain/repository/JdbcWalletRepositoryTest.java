package kr.co.springbootweeklymission.wallet.domain.repository;

import kr.co.springbootweeklymission.infrastructure.error.exception.NotFoundException;
import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;
import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.MemberRepository;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.VoucherRepository;
import kr.co.springbootweeklymission.wallet.creators.WalletCreators;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
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
public class JdbcWalletRepositoryTest {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    Member member;
    Member member2;
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
        Wallet wallet = WalletCreators.createWallet(voucher1, member);

        //when
        Wallet actual = walletRepository.save(wallet);

        //then
        assertThat(actual.getWalletId()).isEqualTo(wallet.getWalletId());
    }

    @Test
    @Order(2)
    void findAllByMemberId_특정_고객의_바우처들을_조회_SUCCESS() {
        //given
        Wallet wallet = WalletCreators.createWallet(voucher2, member);
        walletRepository.save(wallet);

        //when
        List<Wallet> actual = walletRepository.findAllByMemberId(member.getMemberId());

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    @Order(3)
    void findByVoucherId_특정_바우처의_특정_고객을_조회_SUCCESS() {
        //given & when
        Wallet actual = walletRepository.findByVoucherId(voucher1.getVoucherId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND_WALLET));

        //then
        assertThat(actual.getMember()).isEqualTo(member);
    }

    @Test
    @Order(4)
    void deleteWalletByVoucherAndMember_특정_회원의_특정_바우처를_삭제_SUCCESS() {
        //given & then
        walletRepository.deleteByVoucherIdAndMemberId(voucher1.getVoucherId(), member.getMemberId());

        //then
        assertThat(walletRepository.findAllByMemberId(member.getMemberId())).hasSize(1);
    }
}
