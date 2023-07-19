package org.programmers.VoucherManagement.wallet.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.infrastructure.MemberStoreRepository;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherStoreRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponse;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.exception.WalletException;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletReaderRepository;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletStoreRepository walletStoreRepository;
    @Autowired
    private WalletReaderRepository walletReaderRepository;
    @Autowired
    private WalletService walletService;
    @Autowired
    private MemberStoreRepository memberStoreRepository;
    @Autowired
    private VoucherStoreRepository voucherStoreRepository;

    private Member member1;
    private Member member2;
    private Voucher voucher1;
    private Voucher voucher2;
    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void init() {
        setUpWalletServiceTest();
    }

    //@Test
    @Test
    @DisplayName("바우처Id를 이용해 등록된 Wallet 리스트를 조회한다.  - 성공")
    void getWalletsByVoucherId_VoucherId_Success() {
        //given
        List<Wallet> walletList = Arrays.asList(wallet1, wallet2);
        walletStoreRepository.insert(wallet1);
        walletStoreRepository.insert(wallet2);

        //when
        WalletGetResponses walletListResponse = walletService.getWalletsByVoucherId(voucher1.getVoucherId());

        //then
        List<WalletGetResponse> responseExpect = walletList.stream()
                .map(WalletGetResponse::toDto)
                .collect(Collectors.toList());
        assertThat(walletListResponse.getGetWalletListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
    }

    @Test
    @DisplayName("회원 Id를 이용해 등록된 Wallet 리스트를 조회한다.  - 성공")
    void getWalletsByMemberId_MemberId_Success() {
        //given
        List<Wallet> walletList = Arrays.asList(wallet2);
        walletStoreRepository.insert(wallet1);
        walletStoreRepository.insert(wallet2);

        //when
        WalletGetResponses walletListResponse = walletService.getWalletsByMemberId(member2.getMemberUUID());

        //then
        List<WalletGetResponse> responseExpect = walletList.stream()
                .map(WalletGetResponse::toDto)
                .collect(Collectors.toList());
        assertThat(walletListResponse.getGetWalletListRes()).usingRecursiveComparison().isEqualTo(responseExpect);
    }

    @Test
    @DisplayName("WalletId를 입력받아 Wallet을 삭제한다.  - 성공")
    void deleteWallet_walletId_Success() {
        //given
        walletStoreRepository.insert(wallet1);

        //when
        walletService.deleteWallet(wallet1.getWalletId());

        //then
        Optional<Wallet> optionalWallet = walletReaderRepository.findById(wallet1.getWalletId());
        assertThat(optionalWallet).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("WalletId를 입력받아 Wallet을 삭제한다.  - 실패")
    void deleteWallet_walletId_ThrowWalletException() {
        //given
        walletStoreRepository.insert(wallet1);

        //when & then
        Assertions.assertThatThrownBy
                        (() -> walletService.deleteWallet(wallet2.getWalletId()))
                .isInstanceOf(WalletException.class)
                .hasMessage("데이터가 정상적으로 삭제되지 않았습니다.");
    }

    private void setUpWalletServiceTest() {
        member1 = new Member(UUID.randomUUID(), "Kim", MemberStatus.BLACK);
        member2 = new Member(UUID.randomUUID(), "Park", MemberStatus.BLACK);
        voucher1 = new PercentAmountVoucher(UUID.randomUUID(), DiscountType.PERCENT, new DiscountValue(10));
        voucher2 = new FixedAmountVoucher(UUID.randomUUID(), DiscountType.FIXED, new DiscountValue(10000));
        wallet1 = new Wallet(UUID.randomUUID(), voucher1, member1);
        wallet2 = new Wallet(UUID.randomUUID(), voucher1, member2);
        memberStoreRepository.insert(member1);
        memberStoreRepository.insert(member2);
        voucherStoreRepository.insert(voucher1);
        voucherStoreRepository.insert(voucher2);
    }
}
