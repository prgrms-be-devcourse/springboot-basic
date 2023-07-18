package com.wonu606.vouchermanager.service.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.customer.email.EmailDto;
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
        return convertDtoToCustomer(customerDto);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByEmailAddress(EmailDto emailDto) {
        Email email = new Email(emailDto.getEmailAddress());
        return customerRepository.findByEmailAddress(email);
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomerList(List<Email> emails) {
        return customerRepository.findAllByEmailAddresses(emails);
    }

    private static Customer convertDtoToCustomer(CustomerDto customerDto) {
        return new Customer(
                new Email(customerDto.getEmailAddress()),
                customerDto.getNickname());
    }
}
