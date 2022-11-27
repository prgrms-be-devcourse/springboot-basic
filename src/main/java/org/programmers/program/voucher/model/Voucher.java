package org.programmers.program.voucher.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.programmers.program.voucher.controller.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
public abstract class Voucher {
    protected final UUID id;
    protected Long discountAmount;
    @Builder.Default protected final LocalDateTime createdAt = LocalDateTime.now();
    protected LocalDateTime expirationDate;

    protected VoucherType voucherType = null;
    protected boolean isUsed;

    protected Voucher(UUID id, Long discountAmount){
        this.id = id;
        this.discountAmount = discountAmount;
        expirationDate = LocalDateTime.now().plusWeeks(1);
        isUsed = false;
    }
    protected Voucher(UUID id, Long discountAmount, LocalDateTime expirationDate) {
        this.id = id;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
        isUsed = false;
    }

    protected Voucher(VoucherDto dto){
        this.id = dto.getId();
        this.discountAmount = dto.getDiscountAmount();
        this.voucherType = dto.getVoucherType();
        this.expirationDate = dto.getExpirationDate();
    }

//    public UUID getVoucherId(){
//        return this.id;
//    }
//
//    public LocalDateTime getExpirationDate() {
//        return expirationDate;
//    }
//
//    public Long getDiscountAmount(){
//        return this.discountAmount;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
    abstract Long discount(Long price);
//
//    public VoucherType getVoucherType() {
//        return voucherType;
//    }
//
//    public boolean getIsUsed(){
//        return isUsed;
//    }


    @Override
    public String toString() {
        return "ID : " + id +  ", Voucher Type : " + this.voucherType + ", " + "Discount Amount : " + discountAmount
                 + ", Expiration Date : " + expirationDate.toString();
    }
}
