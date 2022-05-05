package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.engine.util.GlobalUtil;
import org.prgrms.springbootbasic.exception.InvalidInputFormatException;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class VoucherCreateRequestDto {
    private final VoucherType voucherType;
    private final Integer value;
    private final Optional<UUID> customerId;

    public Voucher toEntity() {
        try {
            Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), value, LocalDateTime.now().withNano(0));
            if (this.customerId.isPresent()) {
                voucher.changeOwnerById(customerId.get());
            }
            return voucher;
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputFormatException("Invalid Voucher Creation Type.", ErrorCode.INVALID_VOUCHER_TYPE);
        }
    }

    public VoucherCreateRequestDto(VoucherType voucherType, Integer value, String customerId) {
        this.voucherType = voucherType;
        this.value = value;
        UUID id = null;
        try {
            if (customerId != null) {
                id = UUID.fromString(customerId);
            }
        } catch (IllegalArgumentException ignored) {
        }
        this.customerId = Optional.ofNullable(id);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getValue() {
        return value;
    }

    public Optional<UUID> getCustomerId() {
        return customerId;
    }
}
