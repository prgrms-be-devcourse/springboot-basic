package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Discount;
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
        this.type = voucher.getType();
    }

    @Override
    public String toString() {
        return  type + "{" +
                "voucherId = " + voucherID +
                ", discountAmount = " + discount +
                ", createdDate = " + createdDate +
                ", expirationDate = " + expirationDate +
                "}\n";
    }
}
