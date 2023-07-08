package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerCreateRequest {

    private final String name;
    private final String email;
}
