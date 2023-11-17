package org.prgms.springbootbasic.controller.voucher.dto;

import org.prgms.springbootbasic.domain.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDto(UUID voucherId,
                                 long discountDegree,
                                 String voucherPolicy,
                                 LocalDateTime createdAt) {

    public static VoucherResponseDto convertVoucherToVoucherResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(),
                voucher.getDiscountDegree(),
                voucher.getVoucherPolicy().getClass().getSimpleName(),
                voucher.getCreatedAt());
    } // Dto -> voucher. 1. dto에서 정적 팩토리 메서드로. 2. 매퍼 클래스를 만드는거. 클래스 역할과 책임에 따라. 의존관계에 따른 고민.
    // 이 애플리케이션은 간단하니 Mapper 클래스를 따로 정하지 않기로 결정.
}
