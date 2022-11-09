package com.example.springbootbasic.service;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.util.CharacterUnit.ENTER;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

@Service
@Profile("dev")
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String findAllBlackCustomers() {
        return buildResponseBodyBy(customerRepository.findAllCustomers());
    }

    private String buildResponseBodyBy(List<Customer> customers) {
        StringBuilder responseBodyBuilder = new StringBuilder();
        customers.forEach(customer -> {
            CustomerStatus customerStatus = customer.getStatus();
            if (customerStatus == BLACK) {
                responseBodyBuilder
                        .append(customer.getId())
                        .append(SPACE.getUnit())
                        .append(customerStatus.getType())
                        .append(ENTER.getUnit());
            }
        });

        return responseBodyBuilder.toString();
    }
}
