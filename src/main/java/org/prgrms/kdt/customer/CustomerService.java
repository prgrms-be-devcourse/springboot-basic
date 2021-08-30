package org.prgrms.kdt.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getBlackList() {
        return customerRepository.getBlackList();
    }
}
