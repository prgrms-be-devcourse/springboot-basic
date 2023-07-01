package com.programmers.springmission.voucher.presentation.response;

import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherResponse {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public VoucherResponse(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.amount = voucher.getVoucherPolicy().getAmount();
        this.voucherType = voucher.getVoucherType();
    }

    @Override
    public String toString() {
        return "Created Voucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                '}';
    }
}

