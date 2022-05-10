package com.prgrms.voucher_manager.voucher.controller;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.voucher.Voucher;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

import static com.prgrms.voucher_manager.infra.Utils.createModelMapper;

public class VoucherDto {

    private UUID voucherId;
    private String type;
    private long value;
    private LocalDate createdAt;

    private static final ModelMapper modelMapper = createModelMapper();

    public VoucherDto() {}

    public VoucherDto(UUID voucherId, String type, long value, LocalDate createdAt) {
        this.voucherId = voucherId;
        this.type = type;
        this.value = value;
        this.createdAt = createdAt;
    }

    public static VoucherDto of(Voucher voucher) {
        return modelMapper.map(voucher, VoucherDto.class);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public long getValue() {
        return value;
    }
}
