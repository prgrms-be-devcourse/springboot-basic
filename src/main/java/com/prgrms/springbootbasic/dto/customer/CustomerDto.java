package com.prgrms.springbootbasic.dto.customer;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerDto {

    private UUID customerId;
    private String name;
    private String email;

}