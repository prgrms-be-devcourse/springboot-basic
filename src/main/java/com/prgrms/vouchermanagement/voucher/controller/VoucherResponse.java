package com.prgrms.vouchermanagement.voucher.controller;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoucherResponse {
    private UUID voucherId;
    private long amount;
    private VoucherType voucherType;
    private LocalDateTime createdAt;

    public VoucherResponse(UUID voucherId, long amount, VoucherType voucherType, LocalDateTime createdAt) {
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

    public UUID getVoucherId() {
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
