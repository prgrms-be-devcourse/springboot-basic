package com.prgrms.springbootbasic.dto.customer.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerUpdateRequest {

    private UUID customerId;
    private String customerName;
    private String customerEmail;
}
