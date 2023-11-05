package com.prgrms.voucher_manage.domain.customer.controller.dto;

import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;

import javax.validation.constraints.NotBlank;

public record CreateCustomerReq(
        @NotBlank(message = "빈 입력값은 허용되지 않습니다.")
        String name,
        CustomerType type) {
}

