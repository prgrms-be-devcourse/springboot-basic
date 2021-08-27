package org.prgrms.kdt.service;

import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private Map<UUID, Customer> blacklists;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    private void loadAllBlackListCustomer() {
        blacklists = customerRepository.findAllBlackListCustomer();
        System.out.println("blacklists: " + blacklists);
    }

}
