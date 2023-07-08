package com.programmers.springmission.customer.presentation.response;

import com.programmers.springmission.customer.domain.Wallet;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletResponse {

    private final UUID customerId;
    private final UUID voucherId;
    private final String voucherPolicy;
    private final long voucherAmount;

    public WalletResponse(Wallet wallet) {
        this.customerId = wallet.getCustomerId();
        this.voucherId = wallet.getVoucherId();
        this.voucherPolicy = wallet.getVoucherPolicy();
        this.voucherAmount = wallet.getVoucherAmount();
    }

    @Override
    public String toString() {
        return "Customer has Voucher {" +
                "customerId=" + customerId +
                ", voucherId=" + voucherId +
                ", voucherPolicy='" + voucherPolicy + '\'' +
                ", voucherAmount=" + voucherAmount +
                '}';
    }
}
