package com.example.springbootbasic.dto.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;

import java.time.LocalDateTime;

public class VoucherDto {

    private Long voucherId;
    private Long discountValue;
    private VoucherType voucherType;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public VoucherDto() {
    }

    public VoucherDto(Long discountValue, VoucherType voucherType, LocalDateTime createdAt) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = createdAt;

    }

    public VoucherDto(Long voucherId,
                      Long discountValue,
                      VoucherType voucherType,
                      LocalDateTime createdAt,
                      LocalDateTime startAt,
                      LocalDateTime endAt
    ) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static VoucherDto newInstance(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(),
                voucher.getDiscountValue(),
                voucher.getVoucherType(),
                voucher.getCreatedAt(),
                voucher.getStartAt(),
                voucher.getEndAt());
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public boolean isEmpty() {
        return voucherId == 0L || voucherType == null;
    }
}
