package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse save(CustomerCreateRequest customerCreateRequest) {
        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName(customerCreateRequest.getCustomerName())
                .customerEmail(customerCreateRequest.getCustomerEmail())
                .customerType(customerCreateRequest.getCustomerType())
                .build();

        return new CustomerResponse(customerRepository.save(customer));
    }

    public void update(CustomerUpdateRequest customerUpdateRequest) {
        customerRepository.update(
                Customer.builder()
                        .customerId(customerUpdateRequest.getCustomerId())
                        .customerName(customerUpdateRequest.getCustomerName())
                        .customerEmail(customerUpdateRequest.getCustomerEmail())
                        .customerType(customerUpdateRequest.getCustomerType())
                        .build()
        );
    }

    public CustomerResponse findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("찾는 고객이 없습니다, 고객이 저장되어있는지 확인해보세요."));

        return new CustomerResponse(customer);
    }

    public CustomerListResponse findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return new CustomerListResponse(customerList.stream().map(CustomerResponse::new).toList());
    }

    public CustomerListResponse getBlackList() {
        List<Customer> customerList = customerRepository.getBlackList();
        return new CustomerListResponse(customerList.stream().map(CustomerResponse::new).toList());
    }

    public int deleteById(UUID customerId) {
        if (!customerRepository.existById(customerId)) {
            throw new NoSuchElementException("삭제하려고 입력한 고객의 ID는 존재하지 않는 ID입니다. 다시 입력해주세요");
        }

        return customerRepository.deleteById(customerId);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

    public boolean existById(UUID customerId) {
        return customerRepository.existById(customerId);
    }

}
