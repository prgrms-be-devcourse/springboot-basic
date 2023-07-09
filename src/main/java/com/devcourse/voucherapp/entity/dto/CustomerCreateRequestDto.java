package com.devcourse.voucherapp.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerCreateRequestDto {

    private final String nickname;
}
