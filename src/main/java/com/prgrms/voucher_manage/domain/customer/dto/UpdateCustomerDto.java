package com.prgrms.voucher_manage.domain.customer.dto;

import java.util.UUID;

public record UpdateCustomerDto(
        UUID id,
        String name
)
{ }
