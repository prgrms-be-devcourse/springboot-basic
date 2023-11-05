package com.prgrms.voucher_manage.domain.customer.controller.dto;

import javax.validation.constraints.NotBlank;

public record UpdateCustomerReq(
        @NotBlank(message = "빈 입력값은 허용되지 않습니다.")
        String name
)
{ }
