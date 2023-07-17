package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public void deleteByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

}
