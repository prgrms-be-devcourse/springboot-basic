package com.programmers.springbootbasic.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllocateVoucherRequest {

    private String voucherId;
    private String customerId;

}
