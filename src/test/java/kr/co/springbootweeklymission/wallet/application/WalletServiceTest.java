package kr.co.springbootweeklymission.wallet.application;

import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.presentation.dto.response.MemberResDTO;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import kr.co.springbootweeklymission.wallet.creators.WalletCreators;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
import kr.co.springbootweeklymission.wallet.domain.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {
    @InjectMocks
    WalletService walletService;
    @Mock
    WalletRepository walletRepository;

    @Test
    void getVouchersByMemberId_특정_회원이_가진_바우처들을_조회_SUCCESS() {
        //given
        Member member = MemberCreators.createWhiteMember();
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(WalletCreators.createWallet(member));
        wallets.add(WalletCreators.createWallet(member));
        given(walletRepository.findAllByMemberId(any())).willReturn(wallets);

        //then
        List<VoucherResDTO.READ> actual = walletService.getVouchersByMemberId(member.getMemberId());

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    void getMembersByVoucherId_특정_바우처를_가진_회원들을_조회_SUCCESS() {
        //given
        Voucher voucher = VoucherCreators.createFixedDiscount();
        Wallet wallet = WalletCreators.createWallet(voucher);
        given(walletRepository.findByVoucherId(any())).willReturn(Optional.ofNullable(wallet));

        //then
        MemberResDTO.READ actual = walletService.getMemberByVoucherId(voucher.getVoucherId());

        //then
        assertThat(actual.getMemberId()).isEqualTo(wallet.getMember().getMemberId());
    }
}
