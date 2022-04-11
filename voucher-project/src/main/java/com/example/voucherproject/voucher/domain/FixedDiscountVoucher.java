package com.example.voucherproject.voucher.domain;
import com.example.voucherproject.common.enums.VoucherType;
import java.util.UUID;

public class FixedDiscountVoucher extends Voucher{
    public FixedDiscountVoucher(UUID id, VoucherType type) {
        super(id, type);
    }
}
