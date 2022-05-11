package com.waterfogsw.voucher.customer.controller.dto;

import javax.validation.constraints.NotNull;

public record CustomerAddRequest(
        @NotNull
        String name
) {
}
