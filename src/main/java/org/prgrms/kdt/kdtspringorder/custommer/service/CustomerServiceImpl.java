package org.prgrms.kdt.kdtspringorder.custommer.service;

import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.prgrms.kdt.kdtspringorder.custommer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerReposistory;

    public CustomerServiceImpl(CustomerRepository customerReposistory) {
        this.customerReposistory = customerReposistory;
    }

    @Override
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerReposistory::insert);
    }

}
