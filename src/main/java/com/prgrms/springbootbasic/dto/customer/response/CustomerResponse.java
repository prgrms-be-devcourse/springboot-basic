package com.prgrms.springbootbasic.dto.customer.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerResponse {

    private UUID customerId;
    private String customerName;
    private String customerEmail;
    private LocalDateTime createAt;
}
