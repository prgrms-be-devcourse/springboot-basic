package com.devcourse.voucherapp.entity.customer.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerCreateRequestDto {

    private final String nickname;
}
