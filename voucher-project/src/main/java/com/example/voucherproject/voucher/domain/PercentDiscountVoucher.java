package com.example.voucherproject.voucher.domain;
import com.example.voucherproject.common.enums.VoucherType;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{
    public PercentDiscountVoucher(UUID id, VoucherType type) {
        super(id, type);
    }
}
