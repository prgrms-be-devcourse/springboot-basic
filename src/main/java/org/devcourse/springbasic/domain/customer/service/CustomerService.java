package org.devcourse.springbasic.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dao.CustomerRepository;
import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public UUID save(CustomerDto.SaveRequest saveRequest) {
        Customer customer = Customer.builder()
                .customerId(saveRequest.getCustomerId())
                .email(saveRequest.getEmail())
                .name(saveRequest.getName())
                .createdAt(saveRequest.getCreatedAt())
                .build();
        return customerRepository.save(customer);
    }

    @Transactional
    public UUID update(CustomerDto.UpdateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        customer.changeName(request.getName());
        return customerRepository.update(customer);
    }

    @Transactional
    public void deleteById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<CustomerDto.Response> findByCriteria(CustomerDto.Request request) {

        if (request.getName() != null) {
            return CustomerDto.Response.fromEntities(customerRepository.findByName(request.getName()));

        } else if (request.getEmail() != null) {
            Customer customer = customerRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
            return List.of(CustomerDto.Response.fromEntity(customer));
        }

        return CustomerDto.Response.fromEntities(customerRepository.findAll());
    }

    public CustomerDto.Response findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));
        return CustomerDto.Response.fromEntity(customer);
    }
}
