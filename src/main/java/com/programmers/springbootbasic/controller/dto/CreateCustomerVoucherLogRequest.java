package com.programmers.springbootbasic.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCustomerVoucherLogRequest {

    private String customerId;
    private String voucherId;

}
