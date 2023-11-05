package com.prgrms.vouchermanagement.core.customer.service;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import com.prgrms.vouchermanagement.core.customer.utils.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * 고객 추가
     */
    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = customerRepository.save(Mapper.toCustomer(customerDto));
        return CustomerDto.toCustomerDto(customer);
    }

    /**
     * 고객 단건 조회
     */
    public CustomerDto findById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("고객이 존재하지 않습니다.");
        }
        Customer customer = optionalCustomer.get();
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
    }

    /**
     * 전체 고객 조회
     */
    public List<CustomerDto> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(it -> new CustomerDto(it.getId(), it.getName(), it.getEmail()))
                .collect(Collectors.toList());
    }
}
