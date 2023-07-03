package com.programmers.springbasic.domain.customer.service;

import com.programmers.springbasic.domain.customer.entity.Customer;
import com.programmers.springbasic.domain.customer.repository.CustomerRepository;
import com.programmers.springbasic.domain.customer.validator.CustomerCreateRequestValidator;
import com.programmers.springbasic.domain.customer.validator.CustomerIdValidator;
import com.programmers.springbasic.domain.customer.view.CustomerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerCreateRequestValidator customerCreateRequestValidator) {
        String name = customerCreateRequestValidator.getName();
        String email = customerCreateRequestValidator.getEmail();

        Customer customer = new Customer(name, email);
        customerRepository.save(customer);
    }

    public List<String> getAllInfo() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public String findCustomer(CustomerIdValidator customerIdValidator) {
        String validCustomerId = customerIdValidator.getInputCustomerId();
        UUID customerId = UUID.fromString(validCustomerId);

        Customer customer = customerRepository.findById(customerId).orElseThrow();

        return getInfo(customer);
    }

    public void updateCustomer(CustomerIdValidator customerIdValidator) {   // TODO: update customer
        String validCustomerId = customerIdValidator.getInputCustomerId();
        UUID customerId = UUID.fromString(validCustomerId);

        Customer customer = customerRepository.findById(customerId).orElseThrow();

        // update customer

        customerRepository.update(customer);
    }

    public void removeCustomer(CustomerIdValidator customerIdValidator) {
        String validCustomerId = customerIdValidator.getInputCustomerId();
        UUID customerId = UUID.fromString(validCustomerId);

        customerRepository.delete(customerId);
    }

    private String getInfo(Customer customer) {
        StringBuilder sb = new StringBuilder();

        sb.append(CustomerInfo.CUSTOMER_ID_INFO_MESSAGE.getInfoMessage() + customer.getCustomerId() + "\n")
                .append(CustomerInfo.CUSTOMER_NAME_INFO_MESSAGE.getInfoMessage() + customer.getName() + "\n")
                .append(CustomerInfo.CUSTOMER_EMAIL_INFO_MESSAGE.getInfoMessage() + customer.getEmail() + "\n");

        return sb.toString();
    }
}
