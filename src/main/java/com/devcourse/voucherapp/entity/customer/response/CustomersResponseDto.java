package com.devcourse.voucherapp.entity.customer.response;

import com.devcourse.voucherapp.entity.customer.Customer;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomersResponseDto {

    private final List<CustomerResponseDto> customers;

    public static CustomersResponseDto from(List<Customer> customers) {

        return new CustomersResponseDto(customers.stream()
                .map(CustomerResponseDto::from)
                .toList());
    }
}
