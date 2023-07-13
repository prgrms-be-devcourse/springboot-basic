package com.prgrms.spring.service.customer;

import com.prgrms.spring.controller.dto.request.CustomerCreateRequestDto;
import com.prgrms.spring.domain.customer.Customer;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.model.NotFoundException;
import com.prgrms.spring.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
        Customer newCustomer = Customer.newInstance(
                UUID.randomUUID(),
                customerCreateRequestDto.getName(),
                customerCreateRequestDto.getEmail(),
                LocalDateTime.now());
        customerRepository.insert(newCustomer);
        return newCustomer;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_CUSTOMER_EXCEPTION, Error.NOT_FOUND_CUSTOMER_EXCEPTION.getMessage()));
    }

    @Transactional(readOnly = true)
    public List<String> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<String> outputList = new ArrayList<>();
        customerList.forEach(customer -> outputList.add(String.format("Name : %s \nEmail : %s\n", customer.getName(), customer.getEmail())));
        return outputList;
    }
}
