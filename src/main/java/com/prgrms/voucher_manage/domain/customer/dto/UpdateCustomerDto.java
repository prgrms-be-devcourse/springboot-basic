package com.prgrms.voucher_manage.domain.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
@RequiredArgsConstructor @Getter
public class UpdateCustomerDto {
    private final UUID id;
    private final String name;
}
