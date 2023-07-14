package org.prgrms.kdt.wallet.dto.response;

import org.prgrms.kdt.wallet.domain.JoinedWallet;

import java.util.UUID;

public record JoinedWalletResponse(UUID walletId, String memberName, String voucherType, double voucherAmount) {
    public JoinedWalletResponse(JoinedWallet joinedWallet) {
        this(joinedWallet.getWalletId(),
                joinedWallet.getMember().getMemberName().getName(),
                joinedWallet.getVoucher().getVoucherType().getDescripton(),
                joinedWallet.getVoucher().getDiscountPolicy().getAmount());
    }
}
