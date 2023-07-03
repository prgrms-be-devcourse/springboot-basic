package me.kimihiqq.vouchermanagement.domain.voucher.dto;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

import java.util.UUID;

public record VoucherDto(String type, long discount) {
    public Voucher toVoucher() {
        VoucherTypeOption voucherTypeOption = VoucherTypeOption.valueOf(this.type.toUpperCase());
        UUID id = UUID.randomUUID();

        switch (voucherTypeOption) {
            case FIXED:
                return new FixedAmountVoucher(id, this.discount);
            case PERCENT:
                return new PercentDiscountVoucher(id, this.discount);
            default:
                throw new IllegalArgumentException("Invalid voucher type: " + this.type);
        }
    }
}