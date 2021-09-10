package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.common.exception.CustomerNotFoundException;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.prgrms.kdt.kdtspringorder.custommer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() ->  new CustomerNotFoundException());
    }

    @Override
    public UUID createCustomer(Customer customer) {
        return customerRepository.insert(customer);
    }

    @Override
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public UUID updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public int deleteCustomer(UUID customerId) {
        return customerRepository.delete(customerId);
    }

}
