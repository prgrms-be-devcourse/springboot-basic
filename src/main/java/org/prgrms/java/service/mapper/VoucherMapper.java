package org.prgrms.java.service.mapper;

import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.domain.voucher.VoucherType;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherMapper {
    private VoucherMapper() {
    }

    public static Voucher mapToVoucher(String line) {
        String[] fields = line.split(",");
        if (fields.length != 7) {
            throw new VoucherBadRequestException("손상된 바우처 데이터입니다.");
        }

        UUID voucherId = UUID.fromString(fields[0].trim());
        UUID ownerId = (fields[1].trim().equals("null"))? null: UUID.fromString(fields[1].trim());
        String amount = fields[2].trim();
        VoucherType type = VoucherType.of(fields[3].trim());
        LocalDateTime createdAt = LocalDateTime.parse(fields[4].trim());
        LocalDateTime expiredAt = LocalDateTime.parse(fields[5].trim());
        boolean used = Boolean.parseBoolean(fields[6].trim());

        return mapToVoucher(type, voucherId, ownerId, Long.parseLong(amount), createdAt, expiredAt, used);
    }

    public static Voucher mapToVoucher(VoucherType type, UUID voucherId, UUID ownerId, long amount, LocalDateTime createdAt, LocalDateTime expiredAt, boolean used) {
        switch (type) {
            case PERCENT -> {
                return PercentDiscountVoucher.builder()
                        .voucherId(voucherId)
                        .ownerId(ownerId)
                        .amount(amount)
                        .isUsed(used)
                        .createdAt(createdAt)
                        .expiredAt(expiredAt)
                        .build();
            }
            case FIXED -> {
                return FixedAmountVoucher.builder()
                        .voucherId(voucherId)
                        .ownerId(ownerId)
                        .amount(amount)
                        .isUsed(used)
                        .createdAt(createdAt)
                        .expiredAt(expiredAt)
                        .build();
            }
            default -> throw new VoucherBadRequestException("잘못된 바우처 타입입니다.");
        }
    }
}
