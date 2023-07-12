package com.wonu606.vouchermanager.service.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddressDto;
import com.wonu606.vouchermanager.repository.customer.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = convertDtoToCustomer(customerDto);
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByEmailAddress(EmailAddressDto emailAddressDto) {
        EmailAddress emailAddress = new EmailAddress(emailAddressDto.getEmailAddress());
        return customerRepository.findByEmailAddress(emailAddress);
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    private static Customer convertDtoToCustomer(CustomerDto customerDto) {
        return new Customer(
                new EmailAddress(customerDto.getEmailAddress()),
                customerDto.getNickname());
    }
}
