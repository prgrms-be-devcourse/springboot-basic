package org.prgrms.kdt.service.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllByCustomerType(CustomerType customerType) {
        checkArgument(customerType != null, "customerType must be provided.");

        return customerRepository.findAllByCustomerType(customerType);
    }
}
