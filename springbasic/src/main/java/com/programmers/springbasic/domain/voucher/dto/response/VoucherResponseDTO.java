package com.programmers.springbasic.domain.voucher.dto.response;

import com.programmers.springbasic.domain.voucher.model.VoucherType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class VoucherResponseDTO {
    private UUID code;  // 각 voucher의 고유한 코드
    private double value;   // voucher의 가치
    private VoucherType voucherType;    // voucher 타입
    private LocalDate expirationDate;    // voucher의 만료일
    private boolean isActive;   // voucher의 활성화 상태
    private String customerId;    // 할당 받은 고객

    @Builder
    public VoucherResponseDTO(UUID code, double value, VoucherType voucherType, LocalDate expirationDate, boolean isActive, String customerId) {
        this.code = code;
        this.value = value;
        this.voucherType = voucherType;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
        this.customerId = customerId;
    }
}
