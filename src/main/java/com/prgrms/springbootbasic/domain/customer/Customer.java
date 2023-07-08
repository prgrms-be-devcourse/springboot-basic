package com.prgrms.springbootbasic.domain.customer;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
}
