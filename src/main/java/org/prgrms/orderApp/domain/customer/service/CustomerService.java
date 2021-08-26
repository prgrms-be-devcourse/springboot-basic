package org.prgrms.orderApp.domain.customer.service;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.domain.customer.model.Customer;
import org.prgrms.orderApp.domain.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllBlackLIstCustomers() {
        return customerRepository.findAll();
    }
}
