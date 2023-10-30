package com.programmers.vouchermanagement.dto.customer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomersRequestDto {
    private Boolean blacklisted;
}
