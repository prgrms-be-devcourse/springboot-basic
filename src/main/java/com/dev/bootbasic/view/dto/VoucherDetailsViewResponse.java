package com.dev.bootbasic.view.dto;

import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;

import java.util.UUID;

public record VoucherDetailsViewResponse(UUID id, String voucherName, int discountAmount) {

    public static VoucherDetailsViewResponse from(VoucherDetailsResponse voucher) {
        return new VoucherDetailsViewResponse(voucher.id(), voucher.voucherType().name(), voucher.discountAmount());
    }

    @Override
    public String toString() {
        return "Voucher Id= " + id +
                " Type= " + voucherName +
                " Amount= " + discountAmount;
    }

}
