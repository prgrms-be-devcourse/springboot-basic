package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.InvalidInputFormatException;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class VoucherCreateRequestDto {
    private VoucherType voucherType;
    private Integer value;
    private Optional<UUID> customerId;

    public Voucher toEntity() {
        try {
            Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), value, LocalDateTime.now().withNano(0));
            if (this.customerId.isPresent()) {
                voucher.changeOwnerById(customerId.get());
                System.out.println(customerId.get());
            }
            return voucher;
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputFormatException("Invalid Voucher Creation Type.", ErrorCode.INVALID_VOUCHER_TYPE);
        }
    }

    public VoucherCreateRequestDto(VoucherType voucherType, Integer value, String customerId) {
        this.voucherType = voucherType;
        this.value = value;
        try {
            this.customerId = Optional.empty();
            if (customerId != null) {
                this.customerId = Optional.of(UUID.fromString(customerId));
            }
        } catch (IllegalArgumentException ex) {
        }
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
