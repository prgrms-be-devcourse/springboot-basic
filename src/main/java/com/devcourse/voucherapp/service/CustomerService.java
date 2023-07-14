package com.devcourse.voucherapp.service;

import com.devcourse.voucherapp.entity.customer.Customer;
import com.devcourse.voucherapp.entity.customer.CustomerType;
import com.devcourse.voucherapp.entity.customer.dto.CustomerCreateRequestDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.entity.customer.dto.CustomerUpdateRequestDto;
import com.devcourse.voucherapp.exception.customer.ExistedCustomerException;
import com.devcourse.voucherapp.exception.customer.NotFoundCustomerException;
import com.devcourse.voucherapp.repository.customer.CustomerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

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

    public List<CustomerResponseDto> findAllCustomers() {
        return customerRepository.findAllCustomers().stream()
                .map(CustomerResponseDto::from)
                .toList();
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
        int deleteCounts = customerRepository.deleteByNickname(nickname);

        if (isEmptyDeleteResult(deleteCounts)) {
            throw new NotFoundCustomerException(nickname);
        }
    }

    public List<CustomerResponseDto> findBlackListCustomers() {
        return customerRepository.findBlackListCustomers().stream()
                .map(CustomerResponseDto::from)
                .toList();
    }

    private boolean isEmptyDeleteResult(int deleteCounts) {
        return deleteCounts == 0;
    }
}
