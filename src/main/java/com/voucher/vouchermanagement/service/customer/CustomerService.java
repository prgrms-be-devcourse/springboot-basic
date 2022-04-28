package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.dto.customer.CustomerJoinRequest;
import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public UUID join(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());

        this.customerRepository.insert(customer);

        return customer.getId();
    }

    @Transactional
    public List<UUID> multiJoin(List<CustomerJoinRequest> customerJoinRequests) {
        List<UUID> uuids = new ArrayList<>();

        customerJoinRequests.stream()
                .map(customer -> new Customer(UUID.randomUUID(), customer.getName(), customer.getEmail(), LocalDateTime.now(), LocalDateTime.now()))
                .forEach(customer -> {
                    uuids.add(customerRepository.insert(customer).getId());
                });

        return uuids;
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

    public CustomerDto findByName(String name) {
        return CustomerDto.of(
                this.customerRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Customer를 찾을 수 없습니다."))
        );
    }

    public CustomerDto findByEmail(String email) {
        return CustomerDto.of(
                this.customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Customer를 찾을 수 없습니다."))
        );
    }
}
