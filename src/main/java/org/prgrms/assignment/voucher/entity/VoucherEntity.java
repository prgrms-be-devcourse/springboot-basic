package org.prgrms.assignment.voucher.entity;

import org.prgrms.assignment.voucher.dto.VoucherServiceRequestDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.service.VoucherService;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherEntity(
    UUID voucherId,
    VoucherType voucherType,
    LocalDateTime createdAt,
    Long benefit,
    LocalDateTime expireDate
) {

    public static VoucherEntity of(VoucherServiceRequestDTO voucher) {
        return new VoucherEntity(
            voucher.voucherId(),
            voucher.voucherType(),
            voucher.createdAt(),
            voucher.benefit(),
            voucher.expireDate()
        );
    }

}
