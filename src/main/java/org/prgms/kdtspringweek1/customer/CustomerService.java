package org.prgms.kdtspringweek1.customer;

import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> searchAllBlackCustomers() {
        return customerRepository.findAllBlackConsumer();
    }
}
