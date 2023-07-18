package org.programmers.VoucherManagement.wallet.dto.response;

import lombok.Builder;
import org.programmers.VoucherManagement.member.domain.Member;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.wallet.domain.Wallet;

import java.util.UUID;

public class WalletGetResponse {
    private final UUID walletId;
    private final UUID memberId;
    private final UUID voucherId;
    private final String memberName;
    private final DiscountType discountType;
    private final int discountValue;

    @Builder
    public WalletGetResponse(UUID walletId, UUID memberId, UUID voucherId, String memberName, DiscountType discountType, int discountValue) {
        this.walletId = walletId;
        this.memberId = memberId;
        this.voucherId = voucherId;
        this.memberName = memberName;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getMemberName() {
        return memberName;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public static WalletGetResponse toDto(Wallet wallet) {
        Voucher voucher = wallet.getVoucher();
        Member member = wallet.getMember();

        return WalletGetResponse.builder()
                .walletId(wallet.getWalletId())
                .memberId(member.getMemberUUID())
                .voucherId(voucher.getVoucherId())
                .memberName(member.getName())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue().getValue())
                .build();
    }
}

