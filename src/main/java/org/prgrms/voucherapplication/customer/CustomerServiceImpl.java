package org.prgrms.voucherapplication.customer;

import org.prgrms.voucherapplication.dto.ResponseBlacklist;
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
