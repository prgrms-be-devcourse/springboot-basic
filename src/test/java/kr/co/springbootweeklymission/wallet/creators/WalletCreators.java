package kr.co.springbootweeklymission.wallet.creators;

import kr.co.springbootweeklymission.member.creators.MemberCreators;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;

import java.util.UUID;

public class WalletCreators {
    private WalletCreators() {
    }

    public static Wallet createWallet(Voucher voucher,
                                      Member member) {
        return Wallet.builder()
                .walletId(UUID.randomUUID())
                .voucher(voucher)
                .member(member)
                .build();
    }

    public static Wallet createWallet(Voucher voucher) {
        return Wallet.builder()
                .walletId(UUID.randomUUID())
                .voucher(voucher)
                .member(MemberCreators.createWhiteMember())
                .build();
    }

    public static Wallet createWallet(Member member) {
        return Wallet.builder()
                .walletId(UUID.randomUUID())
                .voucher(VoucherCreators.createFixedDiscount())
                .member(member)
                .build();
    }
}
