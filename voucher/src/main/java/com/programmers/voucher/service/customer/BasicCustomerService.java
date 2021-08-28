package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasicCustomerService implements CustomerVoucherService {

    private final CustomerRepository customerRepository;

    public BasicCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByVoucher(long voucherId) {
        return customerRepository.findByVoucher(voucherId);
    }

    @Override
    public Customer create(String username, String alias) {
        Customer customer = new Customer(username, alias);
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void openStorage() {
        customerRepository.loadCustomers();
    }

    @Override
    public void closeStorage() {
        customerRepository.persistCustomers();
    }

    @Override
    public List<Customer> listAll() {
        return customerRepository.listAll();
    }

}
