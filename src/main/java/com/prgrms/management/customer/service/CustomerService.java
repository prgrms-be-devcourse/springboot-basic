package com.prgrms.management.customer.service;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findBlackList() {
        return customerRepository.findBlackList();
    }

    public void createCustomer() {
    }

    public void deleteCustomer() {
    }

    public void createVoucher() {
    }

    public void deleteVoucher() {
    }

    public void findCustomers() {
    }

    public void findCustomersByVoucherType() {
    }
}
