package com.devcourse.voucherapp.entity.dto;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.CustomerType;
import com.devcourse.voucherapp.entity.customer.Customer;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomerResponseDto {

    private final UUID id;
    private final CustomerType type;
    private final String nickname;

    public static CustomerResponseDto from(Customer customer) {
        return new CustomerResponseDto(customer.getId(), customer.getType(), customer.getNickname());
    }

    @Override
    public String toString() {
        return format("{0} | {1} | {2}", id, type.getName(), nickname);
    }
}
