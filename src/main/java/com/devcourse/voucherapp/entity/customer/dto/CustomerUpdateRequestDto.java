package com.devcourse.voucherapp.entity.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerUpdateRequestDto {

    private final String typeNumber;
    private final String nickname;
}
