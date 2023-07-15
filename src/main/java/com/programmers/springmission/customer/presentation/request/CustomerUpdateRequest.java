package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomerUpdateRequest {

    private final String name;
}
