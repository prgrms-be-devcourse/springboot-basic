package org.prgrms.springbootbasic.mapper;

import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.dto.CustomerUpdateDto;
import org.prgrms.springbootbasic.entity.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDtoMapper {

    public static Customer inputDtoToCustomer(CustomerInputDto customerInputDto) {
        return Customer.builder()
                .customerId(UUID.randomUUID())
                .name(customerInputDto.getName())
                .email(customerInputDto.getEmail())
                .createdAt(LocalDateTime.now())
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    public static Customer updateDtoToCustomer(CustomerUpdateDto updateDto) {
        return Customer.builder()
                .customerId(UUID.fromString(updateDto.getCustomerId()))
                .name(updateDto.getName())
                .email(updateDto.getEmail())
                .lastLoginAt(LocalDateTime.now())
                .build();
    }
}
