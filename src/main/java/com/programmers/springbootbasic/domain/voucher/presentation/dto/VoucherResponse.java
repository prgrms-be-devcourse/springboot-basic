package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherResponse {

    private final UUID id;
    private final String voucherType;
    private final Integer benefitValue;
    private final LocalDateTime createdAt;

    public VoucherResponse(UUID id, String voucherType, Integer benefitValue,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
        this.createdAt = createdAt;
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue(), voucher.getCreatedAt());
    }

    @Override
    public String toString() {
        return """
            Voucher id = %s
            Type = %s
            benefitValue = %s
            ===============================
            """.formatted(id, voucherType, benefitValue);
    }

    public UUID getId() {
        return id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
