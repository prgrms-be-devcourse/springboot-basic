package com.prgrms.springbootbasic.dto.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerDto {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime localDateTime;

}