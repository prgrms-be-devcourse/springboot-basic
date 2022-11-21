package org.prgrms.voucherapplication.customer.service;

import org.prgrms.voucherapplication.customer.entity.Customer;
import org.prgrms.voucherapplication.customer.repository.BlackListRepository;
import org.prgrms.voucherapplication.customer.repository.CustomerRepository;
import org.prgrms.voucherapplication.customer.dto.ResponseBlacklist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BlackListRepository blackListRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, BlackListRepository blackListRepository) {
        this.customerRepository = customerRepository;
        this.blackListRepository = blackListRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }

    @Override
    public List<ResponseBlacklist> findBlacklist() {
        return blackListRepository.findAll();
    }
}
