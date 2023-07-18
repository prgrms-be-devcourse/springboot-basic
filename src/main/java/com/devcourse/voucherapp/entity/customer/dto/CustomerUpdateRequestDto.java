package com.devcourse.voucherapp.entity.customer.dto;

import com.devcourse.voucherapp.entity.customer.CustomerType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerUpdateRequestDto {

    private UUID id;
    private CustomerType type;
    private String nickname;
}
