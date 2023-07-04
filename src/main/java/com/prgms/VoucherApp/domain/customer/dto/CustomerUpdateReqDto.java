package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.CustomerStatus;

import java.util.UUID;

public class CustomerUpdateReqDto {

    private final UUID id;
    private final CustomerStatus status;

    public CustomerUpdateReqDto(UUID id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
