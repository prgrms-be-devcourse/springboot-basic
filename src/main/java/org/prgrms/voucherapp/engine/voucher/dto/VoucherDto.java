package org.prgrms.voucherapp.engine.voucher.dto;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.exception.UnknownVoucherTypeException;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(
        UUID voucherId,
        String type,
        long amount,
        LocalDateTime createdAt
) {
    public static VoucherDto of(Voucher voucher){
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getType(),
                voucher.getAmount(),
                voucher.getCreatedAt()
        );
    }

    public static Voucher to(VoucherDto dto){
        VoucherType dtoVoucherType = VoucherType.getType(dto.type)
                .orElseThrow(UnknownVoucherTypeException::new);
        return dtoVoucherType.createVoucher(dto.voucherId, dto.amount);
    }
}
