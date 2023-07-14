package com.devcourse.voucherapp.entity.customer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerCreateRequestDto {

    private final String nickname;
}
