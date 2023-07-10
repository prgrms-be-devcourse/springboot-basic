package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.CustomerType;
import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.entity.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.CustomerResponseDto;
import com.devcourse.voucherapp.entity.dto.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.entity.dto.CustomersResponseDto;
import com.devcourse.voucherapp.exception.ExistedCustomerException;
import com.devcourse.voucherapp.exception.NotFoundCustomerException;
import com.devcourse.voucherapp.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final int ZERO = 0;

    private final CustomerRepository customerRepository;

    public CustomerResponseDto create(CustomerCreateRequestDto request) {
        String nickname = request.getNickname();

        customerRepository.findCustomerByNickname(nickname)
                .ifPresent(customer -> {
                    throw new ExistedCustomerException(nickname);
                });

        Customer newCustomer = Customer.from(nickname);
        Customer customer = customerRepository.create(newCustomer);

        return CustomerResponseDto.from(customer);
    }

    public CustomersResponseDto findAllCustomers() {
        List<Customer> customers = customerRepository.findAllCustomers();

        return CustomersResponseDto.from(customers);
    }

    public CustomerResponseDto update(CustomerUpdateRequestDto request) {
        String typeNumber = request.getTypeNumber();
        String nickname = request.getNickname();

        UUID id = customerRepository.findCustomerByNickname(nickname)
                .orElseThrow(() -> new NotFoundCustomerException(nickname))
                .getId();

        CustomerType customerType = CustomerType.from(typeNumber);

        Customer customer = Customer.from(id, customerType, nickname);
        Customer updatedCustomer = customerRepository.update(customer);

        return CustomerResponseDto.from(updatedCustomer);
    }

    public void deleteByNickname(String nickname) {
        int deletionCounts = customerRepository.deleteByNickname(nickname);

        if (deletionCounts == ZERO) {
            throw new NotFoundCustomerException(nickname);
        }
    }

    public CustomersResponseDto findBlackListCustomers() {
        List<Customer> blackListCustomers = customerRepository.findBlackListCustomers();

        return CustomersResponseDto.from(blackListCustomers);
    }
}
