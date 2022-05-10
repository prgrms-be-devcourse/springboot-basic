package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VoucherResponse {
    private Long voucherId;
    private long amount;
    private VoucherType voucherType;
    private LocalDateTime createdAt;

    public VoucherResponse(Long voucherId, long amount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getAmount(), VoucherType.getVoucherType(voucher), voucher.getCreatedAt());
    }

    public static List<VoucherResponse> fromList(List<Voucher> vouchers) {
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        vouchers.forEach(voucher -> voucherResponses.add(VoucherResponse.from(voucher)));
        return voucherResponses;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
