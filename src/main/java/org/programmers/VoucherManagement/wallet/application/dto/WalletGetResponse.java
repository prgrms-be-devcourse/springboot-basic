package org.programmers.VoucherManagement.wallet.application.dto;

import lombok.Builder;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.UUID;

@Builder
public record WalletGetResponse(UUID walletId,
                                String memberId,
                                String voucherId,
                                String memberName,
                                DiscountType discountType,
                                int discountValue) {

    public static WalletGetResponse toDto(Wallet wallet) {
        Voucher voucher = wallet.getVoucher();
        Member member = wallet.getMember();

        return WalletGetResponse.builder()
                .walletId(wallet.getWalletId())
                .memberId(member.getMemberId())
                .voucherId(voucher.getVoucherId())
                .memberName(member.getName())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue().getValue())
                .build();
    }
}
