package com.programmers.VoucherManagementApplication.dto;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

import java.util.UUID;

public class VoucherResponse {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Amount amount;

    public VoucherResponse(UUID voucherId, VoucherType voucherType, Amount amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }
}
