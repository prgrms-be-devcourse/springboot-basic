package org.prgrms.springbootbasic.engine.controller.dto;

import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class VoucherCreateRequestDto {
    private VoucherType voucherType;
    private Integer value;
    private Optional<UUID> customerId;

    public Voucher toEntity() {
        try {
            return voucherType.createVoucher(UUID.randomUUID(), value, LocalDateTime.now().withNano(0));
        } catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Voucher Creation Type.");
        }
    }

    public VoucherCreateRequestDto(VoucherType voucherType, Integer value, String customerId) {
        this.voucherType = voucherType;
        this.value = value;
        try {
            this.customerId = Optional.of(UUID.fromString(customerId));
        } catch (IllegalArgumentException ex) {
            this.customerId = Optional.empty();
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
