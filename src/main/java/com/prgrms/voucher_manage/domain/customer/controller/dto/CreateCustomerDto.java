package com.prgrms.voucher_manage.domain.customer.controller.dto;

import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;

public record CreateCustomerDto(
        String name,
        CustomerType type) {
}

