package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

import java.util.UUID;

public record CustomerUpdateRequest(
    UUID id,
    CustomerStatus status
) {
}
