package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID join(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());

        this.customerRepository.insert(customer);

        return customer.getId();
    }

    public List<CustomerDto> findAll() {
        return this.customerRepository.findAll()
                .stream()
                .map(CustomerDto::of)
                .collect(Collectors.toList());
    }

    public CustomerDto findById(UUID id) {
        return CustomerDto.of(
                this.customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer를 찾을 수 없습니다."))
        );
    }
}
