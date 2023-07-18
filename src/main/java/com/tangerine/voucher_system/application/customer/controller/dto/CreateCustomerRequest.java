package com.tangerine.voucher_system.application.customer.controller.dto;

import com.tangerine.voucher_system.application.customer.model.Name;

public record CreateCustomerRequest(
        Name name,
        boolean isBlack
) {}