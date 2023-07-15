package com.programmers.springmission.voucher.presentation.response;

import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherResponse {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long voucherAmount;
    private final UUID customerId;

    public VoucherResponse(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherType = voucher.getVoucherPolicy().getVoucherType();
        this.voucherAmount = voucher.getVoucherAmount();
        this.customerId = voucher.getCustomerId();
    }

    @Override
    public String toString() {
        return "Voucher {" +
                "voucherId=" + voucherId +
                ", voucherPolicy=" + voucherType +
                ", voucherAmount=" + voucherAmount +
                ", customerId=" + customerId +
                '}';
    }
}

