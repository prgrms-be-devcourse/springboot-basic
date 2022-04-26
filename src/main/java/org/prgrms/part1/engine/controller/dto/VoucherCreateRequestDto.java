package org.prgrms.part1.engine.controller.dto;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherCreateRequestDto {
    private VoucherType voucherType;
    private Integer value;

    public Voucher toEntity() {
        try {
            System.out.println(voucherType);
            System.out.println(value);
            return voucherType.createVoucher(UUID.randomUUID(), value, LocalDateTime.now().withNano(0));
        } catch (IllegalArgumentException ex) {
            throw new VoucherException("Invalid Voucher Creation Type.");
        }
    }

    public VoucherCreateRequestDto(VoucherType voucherType, Integer value) {
        this.voucherType = voucherType;
        this.value = value;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getValue() {
        return value;
    }
}
