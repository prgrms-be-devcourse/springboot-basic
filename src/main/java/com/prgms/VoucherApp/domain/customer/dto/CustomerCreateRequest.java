package com.prgms.VoucherApp.domain.customer.dto;

import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;

public record CustomerCreateRequest(
    CustomerStatus customerStatus
) {
}

