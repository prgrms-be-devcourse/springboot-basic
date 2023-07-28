package org.programmers.VoucherManagement.wallet.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.infrastructure.JdbcMemberReaderRepository;
import org.programmers.VoucherManagement.member.infrastructure.JdbcMemberStoreRepository;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.infrastructure.JdbcVoucherReaderRepository;
import org.programmers.VoucherManagement.voucher.infrastructure.JdbcVoucherStoreRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({JdbcWalletReaderRepository.class
        , JdbcWalletStoreRepository.class
        , JdbcMemberStoreRepository.class
        , JdbcVoucherStoreRepository.class
        , JdbcVoucherReaderRepository.class
        , JdbcMemberReaderRepository.class})
public class JdbcWalletRepositoryTest {
    @Autowired
    private JdbcWalletReaderRepository walletReaderRepository;
    @Autowired
    private JdbcWalletStoreRepository walletStoreRepository;
    @Autowired
    private JdbcMemberStoreRepository memberStoreRepository;
    @Autowired
    private JdbcVoucherStoreRepository voucherStoreRepository;

    private Member member1, member2;
    private Voucher voucher1, voucher2;

    @BeforeEach
    void initVoucher() {
        member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
        member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.BLACK);
        voucher1 = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));
        voucher2 = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(10000));
        memberStoreRepository.insert(member1);
        memberStoreRepository.insert(member2);
        voucherStoreRepository.insert(voucher1);
        voucherStoreRepository.insert(voucher2);
    }

    @Test
    @DisplayName("Wallet을 생성할 수 있다. - 성공")
    void insert_Wallet_EqualsNewWallet() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);

        //when
        walletStoreRepository.insert(wallet);

        //then
        Wallet walletExpect = walletReaderRepository.findById(wallet.getWalletId()).get();
        assertThat(walletExpect).usingRecursiveComparison().isEqualTo(wallet);
    }


    @Test
    @DisplayName("id를 이용해 Wallet을 조회할 수 있다. - 성공")
    void findById_WalletID_EqualsFindWallet() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);
        walletStoreRepository.insert(wallet);

        //when
        Wallet walletExpect = walletReaderRepository.findById(wallet.getWalletId()).get();

        //then
        assertThat(walletExpect).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @DisplayName("회원Id를 이용하여 가지고 있는 Wallet 리스트를 반환할 수 있다. - 성공")
    void findAllByMemberId_MemberId_Success() {
        //given
        Wallet wallet1 = new Wallet(UUID.randomUUID(), voucher1, member1);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), voucher2, member1);
        walletStoreRepository.insert(wallet1);
        walletStoreRepository.insert(wallet2);

        //when
        List<Wallet> walletList = walletReaderRepository.findAllByMemberId(member1.getMemberId());

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
        walletStoreRepository.insert(wallet1);
        walletStoreRepository.insert(wallet2);

        //when
        List<Wallet> walletList = walletReaderRepository.findAllByVoucherId(voucher1.getVoucherId());

        //then
        assertThat(walletList.size()).isEqualTo(1);
        assertThat(walletList).usingRecursiveComparison().isEqualTo(List.of(wallet1));
    }

    @Test
    @DisplayName("등록된 Wallet을 삭제할 수 있다. - 성공")
    void delete_VoucherId_Success() {
        //given
        Wallet wallet = new Wallet(UUID.randomUUID(), voucher1, member1);
        walletStoreRepository.insert(wallet);

        //when
        walletStoreRepository.delete(wallet.getWalletId());

        //then
        Optional<Wallet> optionalWallet = walletReaderRepository.findById(wallet.getWalletId());
        assertThat(optionalWallet).isEqualTo(Optional.empty());
    }
}
