package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.Voucher;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherResponseDto {
    private UUID voucherID;
    private Discount discount;
    private LocalDate createdDate;
    private LocalDate expirationDate;
    private String type;

    public VoucherResponseDto(Voucher voucher) {
        this.voucherID = voucher.getVoucherId();
        this.discount = voucher.getDiscount();
        this.createdDate = voucher.getCreatedDate();
        this.expirationDate = voucher.getExpirationDate();

        if (voucher instanceof FixedAmountVoucher) {
            type = voucher.getClass().getSimpleName();
            return;
        }
        if (voucher instanceof FixedAmountVoucher) {
            type = voucher.getClass().getSimpleName();
            return;
        }
    }

    public UUID getVoucherID() {
        return voucherID;
    }

    public Discount getDiscount() {
        return discount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getType() {
        return type;
    }
}
