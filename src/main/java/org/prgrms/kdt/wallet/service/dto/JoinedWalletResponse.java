package org.prgrms.kdt.wallet.service.dto;

import org.prgrms.kdt.wallet.domain.JoinedWallet;

import java.util.UUID;

public record JoinedWalletResponse(UUID walletId, String memberName, String voucherType, double voucherAmount) {
    public JoinedWalletResponse(JoinedWallet joinedWallet) {
        this(joinedWallet.getWalletId(),
                joinedWallet.getMember().getMemberName(),
                joinedWallet.getVoucher().getVoucherType().getDescripton(),
                joinedWallet.getVoucher().getDiscountPolicy().getAmount());
    }
}
