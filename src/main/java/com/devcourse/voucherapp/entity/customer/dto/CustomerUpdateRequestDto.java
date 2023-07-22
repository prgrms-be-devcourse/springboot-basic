package com.devcourse.voucherapp.entity.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerUpdateRequestDto {

    private String typeOption;
    private String nickname;
}
