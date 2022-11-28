package org.programmers.program.voucher.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.programmers.program.voucher.controller.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
public abstract class Voucher {
    protected final UUID id;
    protected Long discountAmount;
    protected final LocalDateTime createdAt;
    protected LocalDateTime expirationDate;

    protected VoucherType voucherType = null;
    protected boolean isUsed;

    protected Voucher(UUID id, Long discountAmount, LocalDateTime createdAt){
        this.id = id;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
        expirationDate = createdAt.plusWeeks(1);
        isUsed = false;
    }
    protected Voucher(UUID id, Long discountAmount, LocalDateTime createdAt, LocalDateTime expirationDate) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
        isUsed = false;
    }

    protected Voucher(VoucherDto dto){
        this.id = dto.getId();
        this.discountAmount = dto.getDiscountAmount();
        this.voucherType = dto.getVoucherType();
        this.expirationDate = dto.getExpirationDate();
        createdAt = LocalDateTime.now();
    }

    abstract Long discount(Long price);


    @Override
    public String toString() {
        return "ID : " + id +  ", Voucher Type : " + this.voucherType + ", " + "Discount Amount : " + discountAmount
                 + ", Expiration Date : " + expirationDate.toString();
    }
}
