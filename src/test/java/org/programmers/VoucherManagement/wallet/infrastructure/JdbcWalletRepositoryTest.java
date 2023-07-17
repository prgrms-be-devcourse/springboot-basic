package org.programmers.VoucherManagement.wallet.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.infrastructure.JdbcMemberRepository;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.infrastructure.JdbcVoucherRepository;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcWalletRepository.class, JdbcMemberRepository.class, JdbcVoucherRepository.class})
public class JdbcWalletRepositoryTest {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    VoucherRepository voucherRepository;

    private Member member1, member2;
    private Voucher voucher1, voucher2;

    @BeforeEach
    void initVoucher() {
        member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
        member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.BLACK);
        voucher1 = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));
        voucher2 = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(10000));
        memberRepository.insert(member1);
        memberRepository.insert(member2);
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
    }

    @Test
    @DisplayName("Wallet을 생성할 수 있다. - 성공")
    void insert_Wallet_EqualsNewWallet() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);

        //when
        walletRepository.insert(wallet);

        //then
        Wallet walletExpect = walletRepository.findById(wallet.getWalletId()).get();
        assertThat(walletExpect).usingRecursiveComparison().isEqualTo(wallet);
    }


    @Test
    @DisplayName("id를 이용해 Wallet을 조회할 수 있다. - 성공")
    void findById_WalletID_EqualsFindWallet() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);
        walletRepository.insert(wallet);

        //when
        Wallet walletExpect = walletRepository.findById(wallet.getWalletId()).get();

        //then
        assertThat(walletExpect).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @DisplayName("회원Id를 이용하여 가지고 있는 Wallet 리스트를 반환할 수 있다. - 성공")
    void findAllByMemberId_MemberId_Success() {
        //given
        Wallet wallet1 = new Wallet(UUID.randomUUID(), voucher1, member1);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), voucher2, member1);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);

        //when
        List<Wallet> walletList = walletRepository.findAllByMemberId(member1.getMemberUUID());

        //then
        assertThat(walletList.size()).isEqualTo(2);
        assertThat(walletList).usingRecursiveComparison().isEqualTo(List.of(wallet1, wallet2));
    }

    @Test
    @DisplayName("바우처Id를 이용하여 가지고 있는 Wallet 리스트를 반환할 수 있다. - 성공")
    void findAllByVoucherId_VoucherId_Success() {
        //given
        Wallet wallet1 = new Wallet(UUID.randomUUID(), voucher1, member1);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), voucher2, member1);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);

        //when
        List<Wallet> walletList = walletRepository.findAllByVoucherId(voucher1.getVoucherId());

        //then
        assertThat(walletList.size()).isEqualTo(1);
        assertThat(walletList).usingRecursiveComparison().isEqualTo(List.of(wallet1));
    }

    @Test
    @DisplayName("등록된 Wallet을 삭제할 수 있다. - 성공")
    void delete_VoucherId_Success() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);
        walletRepository.insert(wallet);

        //when
        walletRepository.delete(wallet.getWalletId());

        //then
        Optional<Wallet> optionalWallet = walletRepository.findById(wallet.getWalletId());
        assertThat(optionalWallet).isEqualTo(Optional.empty());
    }

}
