package com.devcourse.voucherapp.entity.customer.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerUpdateRequestDto {

    private final String typeNumber;
    private final String nickname;
}
