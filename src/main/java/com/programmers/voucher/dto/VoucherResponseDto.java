package com.programmers.voucher.dto;

import com.programmers.voucher.domain.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponseDto {
    private UUID voucherID;
    private Discount discount;
    private LocalDateTime createdDate;

    public VoucherResponseDto(UUID voucherID, Discount discount, LocalDateTime createdDate) {
        this.voucherID = voucherID;
        this.discount = discount;
        this.createdDate = createdDate;
    }

    public UUID getVoucherID() {
        return voucherID;
    }

    public Discount getDiscount() {
        return discount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return MessageFormat.format("""
                        voucherId = {0}
                        voucherType = {1}
                        discountValue = {2}
                        createdDate = {3}
                        """,
                voucherID, discount.getVoucherType(), discount.getValue(), createdDate
        );
    }
}
