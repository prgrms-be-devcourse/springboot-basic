package me.kimihiqq.vouchermanagement.domain.voucher.dto;

import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.option.VoucherTypeOption;

public record VoucherDto(String type, long discount) {
    public Voucher toVoucher() {
        VoucherTypeOption voucherTypeOption = VoucherTypeOption.valueOf(this.type.toUpperCase());
        return voucherTypeOption.createVoucher(this.discount);
    }
}