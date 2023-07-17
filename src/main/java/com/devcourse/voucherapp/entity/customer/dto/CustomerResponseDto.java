package com.devcourse.voucherapp.entity.customer.dto;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.entity.customer.CustomerType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerResponseDto {

    private UUID id;
    private CustomerType type;
    private String nickname;

    public static CustomerResponseDto from(Customer customer) {
        return new CustomerResponseDto(customer.getId(), customer.getType(), customer.getNickname());
    }

    @Override
    public String toString() {
        return format("{0} | {1} | {2}", id, type.getName(), nickname);
    }
}
