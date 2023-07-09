package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.entity.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.CustomerResponseDto;
import com.devcourse.voucherapp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponseDto create(CustomerCreateRequestDto request) {
        String nickname = request.getNickname();
        Customer newCustomer = Customer.from(nickname);
        Customer customer = customerRepository.create(newCustomer);

        return CustomerResponseDto.from(customer);
    }
}
