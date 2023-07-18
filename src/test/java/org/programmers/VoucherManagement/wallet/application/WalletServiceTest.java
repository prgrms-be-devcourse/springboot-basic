package org.programmers.VoucherManagement.wallet.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.voucher.domain.*;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponse;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.exception.WalletException;
import org.programmers.VoucherManagement.wallet.infrastructure.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
public class WalletServiceTest {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private Member member1;
    private Member member2;
    private Voucher voucher1;
    private Voucher voucher2;

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
    @DisplayName("바우처Id를 이용해 등록된 Wallet 리스트를 조회한다.  - 성공")
    void getWalletsByVoucherId_VoucherId_Success() {
        //given
        Wallet wallet1 = new Wallet(UUID.randomUUID(), voucher1, member1);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), voucher1, member2);
        List<Wallet> walletList = Arrays.asList(wallet1, wallet2);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);

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
        Wallet wallet1 = new Wallet(UUID.randomUUID(), voucher1, member2);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), voucher2, member2);
        List<Wallet> walletList = Arrays.asList(wallet1, wallet2);
        walletRepository.insert(wallet1);
        walletRepository.insert(wallet2);

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
        UUID walletId = UUID.randomUUID();
        Wallet saveWallet = new Wallet(walletId, voucher1, member1);
        walletRepository.insert(saveWallet);

        //when
        walletService.deleteWallet(walletId);

        //then
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        assertThat(optionalWallet).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("WalletId를 입력받아 Wallet을 삭제한다.  - 실패")
    void deleteWallet_walletId_ThrowWalletException() {
        //given
        UUID walletId = UUID.randomUUID();
        Wallet saveWallet = new Wallet(walletId, voucher1, member1);
        walletRepository.insert(saveWallet);
        UUID strangeWalletId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> walletService.deleteWallet(strangeWalletId))
                .isInstanceOf(WalletException.class)
                .hasMessage("데이터가 정상적으로 삭제되지 않았습니다.");
    }
}
