package prgms.vouchermanagementapp.model.dto;

import prgms.vouchermanagementapp.model.Voucher;
import prgms.vouchermanagementapp.model.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDto(
        VoucherType voucherType,
        UUID voucherId,
        long discountLevel,
        LocalDateTime createdAt
) {

    public VoucherResponseDto(Voucher voucher) {
        this(
                VoucherType.from(voucher.getVoucherType()),
                voucher.getVoucherId(),
                voucher.getDiscountLevel(),
                voucher.getCreatedDateTime()
        );
    }
}