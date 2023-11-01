package com.programmers.springbootbasic.domain.voucher.domain.entity;


import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Voucher {

    private final UUID id;
    private final VoucherType voucherType;
    private final Integer benefitValue;
    private final LocalDateTime createdAt;

    public Voucher(UUID id, VoucherType voucherType, Integer benefitValue, LocalDateTime createdAt) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    // 테스트코드를 위해 재정의
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher voucher)) {
            return false;
        }
        return Objects.equals(getId(), voucher.getId()) && Objects.equals(
            getVoucherType(), voucher.getVoucherType()) && Objects.equals(getBenefitValue(),
            voucher.getBenefitValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVoucherType(), getBenefitValue());
    }
}
